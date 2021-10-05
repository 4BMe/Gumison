<template>
  <div>
    <simplebar
      class="chat-group-list"
      v-if="dataLoaded"
      id="chat-list"
      ref="current"
    >
      <ul
        class="list-unstyled chat-list chat-user-list"
        v-if="userRankList.length === 0"
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
        </li>
      </ul>

      <ul class="list-unstyled chat-list chat-user-list" v-else>
        <li>
          <UserRank />
        </li>
        <li
          v-for="(item, index) in userRankList"
          :key="index"
          @click="searchHistory(item.nickname)"
        >
          <a href="javascript:void(0);">
            <div class="media">
              <div class="avatar-xs overflow-hidden mr-3 align-self-center">
                <span
                  class=" avatar-title text-primary rounded-circle bg-transparent align-center"
                  >{{ item.rank }}
                </span>
              </div>
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
                    :src="require(`@/assets/images/tier/` + item.tier + `.png`)"
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
import simplebar from "simplebar-vue";
import UserRank from "./user-rank";

import {
  getUserRankListByKeywordAndPage,
  getUserRankListByPage,
} from "@/api/rank";

export default {
  name: "rank-list",
  components: {
    simplebar,
    UserRank,
  },

  props: {
    rankKeyword: {
      type: String,
      default: "",
    },

    rankPage: {
      default: 1,
    },
  },

  data() {
    return {
      userRankList: [],
      dataLoaded: false,
    };
  },

  computed: {
    keyword: {
      get() {
        return this.rankKeyword;
      },
    },
    page: {
      get() {
        return this.rankPage;
      },
    },
  },
  watch: {
    keyword: function(val, oldVal) {
      console.log("[rank-list] watch - keyword", val + " " + oldVal);
      this.getList();
    },
    page: function(val, oldVal) {
      console.log("[rank-list] watch - page", val + " " + oldVal);
      this.getList();
    },
  },
  created() {
    this.getList();
  },
  mounted() {},
  methods: {
    async getList() {
      this.dataLoaded = false;
      this.$emit("setDataLoaded", false);
      console.log("[get-list]", this.keyword, this.page, this.keyword.length);

      const ret = await this.getListResponse();
      this.userRankList = ret.userRankList;
      this.dataLoaded = true;
      this.setLastPage(ret.lastPageNumber);
      this.$emit("setDataLoaded", true);
    },

    async getListResponse() {
      if (this.keyword == null || this.keyword.replace(" ", "").length === 0) {
        return await getUserRankListByPage(this.page);
      }
      return await getUserRankListByKeywordAndPage(this.keyword, this.page);
    },

    setLastPage(lastPage) {
      this.$emit("setLastPage", lastPage);
    },
  },
};
</script>

<style>
.sm-tier-img {
  width: 23px;
}
</style>
