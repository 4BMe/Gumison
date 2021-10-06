<template>
  <div class="px-2">
    <SearchBar 
      currName = '사용자'
      currType = 'userlist'
      :currKeyword = 'currKeyword'
    />
    <simplebar class="chat-group-list" id="chat-list" ref="current">
      <ul class="list-unstyled chat-list chat-user-list">
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
                    >{{ item.nickname.charAt(0) }}
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
                      <td style="width:100px">
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
      <infinite-loading @infinite="infiniteHandler" spinner="waveDots">
        <div slot="no-more"></div>
        <div slot="no-results">"{{ keyword }}"을/를 찾을 수 없습니다.</div>
      </infinite-loading>
    </simplebar>
    <!-- End chat-message-list -->
  </div>
</template>

<script>
import axios from "axios";
import { BASE_URL } from "@/constant/index"
import simplebar from "simplebar-vue";
import SearchBar from "./searchBar";
import InfiniteLoading from "vue-infinite-loading";

export default {
  name: 'userlist',
  components: {
    simplebar,
    SearchBar,
    InfiniteLoading
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
      currKeyword: this.keyword
    };
  },
  created() {
    if(this.currKeyword == ' '){
      this.currKeyword = '';
    }
  },
  mounted() {
  },
  methods: {
    searchHistory(user) {
      this.$router.push({name: 'myhistory', params:{nickname: user.nickname}})
    },
    
    infiniteHandler($state) {
    //  무한 스크롤
      axios
        .get(`${BASE_URL}/users/search/${this.keyword}/${this.pageNumber}`)
        .then(({ data }) => {
          // 로딩스피너를 위해 0.1초의 지연시간을 설정했다.
          setTimeout(() => {  
            if(data.data.users.length) {
              this.userList = this.userList.concat(data.data.users)
              this.pageNumber++;
              $state.loaded()
                // 끝 지정(No more data) - 데이터가 1개 미만이면
              if(data.data.users.length < 10) {  //종료조건
                $state.complete()
              }
            } else {
                // 끝 지정(No more data)
              $state.complete()
            }
          }, 100)
        })
        .catch((err) => {
          console.log("에러: " + err);
        });
    }
  },
};
</script>

<style scoped>
.sm-tier-img {
  width: 23px;
}
</style>
