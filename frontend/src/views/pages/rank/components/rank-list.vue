<template>
  <div>
    <simplebar class="chat-message-list" id="chat-list" ref="current">
      <ul
        class="list-unstyled chat-list chat-user-list"
        v-if="userRankList.length == 0"
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
          v-for="(item, index) in userRankList"
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
// import axios from "axios";
// import { BASE_URL } from "@/constant/index"
import simplebar from "simplebar-vue";
import {getUserRankList} from "@/api/rank";

export default {
  name: 'rank-list',
  components: {
    simplebar,
  },

  props: {
    rankKeyword: {
      type: String,
      default: ""
    },

    rankPage: {
      default: 1
    },
  },

  computed: {
    keyword:{
      get(){
        console.log("[rank-list] - props keyword " + this.rankKeyword);
        return this.rankKeyword;
      }
    },
    page:{
      get(){
        return this.rankPage;
      }
    }
  },
  data() {
    return {
      userRankList: [],
    };
  },
  watch:{
    keyword : function(val, oldVal){
      console.log("[rank-list] watch - keyword", val + " " + oldVal)
    },
    page : function(val, oldVal){
      console.log("[rank-list] watch - page", val + " " + oldVal)
    }
  },
  created() {
    this.getList();
  },
  mounted() {
  },
  methods: {
    getList(){
      getUserRankList(this.keyword, this.page);
    }
  },
};
</script>

<style>
.sm-tier-img {
  width: 23px;
}
</style>
