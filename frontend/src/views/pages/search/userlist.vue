<template>
  <div class="px-2">
    <div class="px-4 pt-4">
      <h4 class="mb-0">거미손</h4>
    </div>
    <SearchBar 
      currName = '사용자'
      currType = 'userlist'
      :currKeyword = 'keyword'
    />
    <simplebar class="chat-message-list" id="chat-list" ref="current">
      <ul
        class="list-unstyled chat-list chat-user-list"
        v-if="userList.length == 0"
      >
        <li>
          <a href="javascript:void(0);">
            <div class="media">
              <div class="media-body overflow-hidden">
                <p class="chat-user-message text-truncate mb-0 pt-">
                  "{{ keyword }}"을/를 찾을 수 없습니다.
                </p>
              </div>
            </div>
          </a>
          <div class="media">
            <div class="chat-user-img online align-self-center mr-3"></div>
          </div>
        </li>
      </ul>
      <ul class="list-unstyled chat-list chat-user-list" v-else>
        <li
          v-for="(item, index) in userList"
          :key="index"
          @click="searchHistory(item)"
        >
          <a href="javascript:void(0);">
            <div class="media">
              <div class="chat-user-img online align-self-center mr-3">
                <div v-if="item.profile">
                  <img
                    :src="item.profile"
                    class="rounded-circle avatar-xs"
                    alt
                  />
                </div>
                <div class="avatar-xs" v-if="!item.profile">
                  <span
                    class="avatar-title rounded-circle bg-soft-primary text-primary"
                    >{{ $t(item.nickname).charAt(0) }}
                  </span>
                </div>
              </div>

              <div class="media-body overflow-hidden">
                <h5 class="text-truncate font-size-15 mb-1">
                  <img
                    :src="
                      require(`@/assets/images/tier/` + item.tier + `.png`)
                    "
                    alt="user-img"
                    class="img-fluid rounded-circle sm-tier-img"
                  />
                  {{ item.nickname }}
                </h5>
                <table style="width:100%">
                  <tbody>
                    <tr>
                      <td style="width:0px">
                        <p class="chat-user-message text-truncate mb-0">
                          {{ item.tier }}
                        </p>
                      </td>
                      <td>
                        <p class="chat-user-message text-truncate mb-0">
                          <b>{{ item.solCnt }}</b> 문제 성공
                        </p>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </a>
        </li>
      </ul>
    </simplebar>
    <!-- End chat-message-list -->
  </div>
</template>

<script>
import axios from "axios";
import { BASE_URL } from "@/constant/index"
import simplebar from "simplebar-vue";
import SearchBar from "./searchBar";

export default {
  name: 'userlist',
  components: {
    simplebar,
    SearchBar,
  },

  props: {
    keyword: {
      type: String,
      default: " ",
    },
  },
  data() {
    return {
      userList: [],
      pageNumber: 0,
    };
  },
  watch:{
    $route(to, from) { 
      console.log(to);
      if (to.name == from.name) {
        this.getList();
      }
    }
  },
  created() {
    this.getList();
  },
  mounted() {
  },
  methods: {
    searchHistory(user) {
      console.log(user);
    },
    getList(){
      axios
        .get(`${BASE_URL}/users/search/${this.keyword}/${this.pageNumber}`)
        .then(({ data }) => {
          this.userList = data.data.users;
        })
        .catch((err) => {
          console.log("에러: " + err);
        });
    }
  },
};
</script>

<style>
.sm-tier-img {
  width: 23px;
}
</style>
