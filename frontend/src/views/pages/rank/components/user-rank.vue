<template>
  <div class="bg-light" v-if="user">
    <router-link to="/myhistory">
      <div class="media">
        <div class="avatar-xs overflow-hidden mr-3 align-self-center">
          <span
            class=" avatar-title text-primary rounded-circle bg-soft-light align-center"
            >{{ user.rank }}
          </span>
        </div>
        <div class="chat-user-img online align-self-center mr-3">
          <div v-if="user.profile">
            <img :src="user.profile" class="rounded-circle avatar-xs" alt />
          </div>
          <div class="avatar-xs" v-if="!user.profile">
            <span
              class="avatar-title rounded-circle bg-soft-primary text-primary"
              >{{ $t(user.nickname).charAt(0) }}
            </span>
          </div>
        </div>

        <div class="media-body overflow-hidden">
          <h5 class="text-truncate font-size-15 mb-1">
            <img
              :src="require(`@/assets/images/tier/` + user.tier + `.png`)"
              alt="user-img"
              class="img-fluid rounded-circle sm-tier-img"
            />
            {{ user.nickname }}
          </h5>
          <table style="width:100%">
            <tbody>
              <tr>
                <td style="width:0px">
                  <p class="chat-user-message text-truncate mb-0">
                    {{ user.tier }}
                  </p>
                </td>
                <td>
                  <p class="chat-user-message text-truncate mb-0">
                    <b>{{ user.solCnt }}</b> 문제 성공
                  </p>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <!-- End chat-message-list -->
    </router-link>
  </div>
</template>

<script>
import { getUserRankByNickname } from "@/api/rank";

export default {
  name: "user-rank",

  data() {
    return {
      user: null,
    };
  },

  created() {
    this.getUser(this.$store.getters["users/getNickname"]);
  },
  mounted() {
    console.log(this.userNickname);
  },
  methods: {
    async getUser(nickname) {
      if (nickname == null || nickname.length == 0) {
        this.user = null;
        return;
      }
      let ret = await getUserRankByNickname(nickname);
      console.log(ret);
      this.user = ret.userRank;
    },
  },
};
</script>

<style>
.sm-tier-img {
  width: 23px;
}
</style>
