<template>
  <div class="media">
    <div class="overflow-hidden mr-3 align-self-center">
      <span class="text-primary">{{ item.rank }} </span>
    </div>
    <div class="chat-user-img online align-self-center mr-3">
      <div v-if="item.profile">
        <img :src="item.profile" class="rounded-circle avatar-xs" alt />
      </div>
      <div class="avatar-xs" v-if="!item.profile">
        <span class="avatar-title rounded-circle bg-soft-primary text-primary"
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
  <!-- End chat-message-list -->
</template>

<script>
// import { getUserRankListByNickname
// } from "@/api/rank";
import { mapGetters } from "vuex";

export default {
  name: "user-rank",

  data() {
    return {
      userRank: 0
    };
  },

  computed: {
      ...mapGetters(['user'])
  }

  mounted() {},
  methods: {},
};
</script>

<style>
.sm-tier-img {
  width: 23px;
}
</style>
