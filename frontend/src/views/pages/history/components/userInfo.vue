<template>
  <div class="px-2">
    <div class="container-fluid row m-0 p-0">
      <div
        class="col-2 chat-user-img online align-self-center"
        id="userCard"
      >
        <div v-if="user.profile">
          <img
            :src="user.profile"
            class="rounded-circle avatar-xs"
            id="profile"
            alt="profile"
          />
        </div>
        <div
          class="avatar-xs"
          v-else
        >
          <span class="avatar-title rounded-circle bg-soft-primary text-primary">{{ user.nickname.charAt(0) }}
          </span>
        </div>
      </div>
      <div class="col-6 container-fluid row m-0 p-0">
        <h5 class="align-self-center m-0 p-0">
          <img
            :src="require(`@/assets/images/tier/`+user.tier+`.png`)"
            alt="tier-img"
            id="tier-img"
            class="img-fluid rounded-circle sm-tier-img"
          />
          {{ user.nickname }}
        </h5>
      </div>
      <div
        class="col-4 pr-3 container-fluid row m-0 p-0 align-self-center"
        v-if="user.nickname == currNickname"
      >
        <div class="col m-0 p-0  text-right">
          <div class="row">
            <b-button
              type="button"
              class="btn col-4 bg-primary avatar-xs p-0 rounded-circle"
              v-b-modal="'modal-change-profile'"
            >
              <i class="ri-pencil-fill"></i>
            </b-button>
            <div class="col-1"></div>
            <b-button
              type="button"
              class="btn col-4 bg-danger avatar-xs p-0 rounded-circle"
              v-b-modal="'modal-withdraw'"
            >
              <i class="ri-delete-bin-fill"></i>
            </b-button>
          </div>

        </div>

      </div>
      <WithdrawModal></WithdrawModal>
      <ProfileChangeModal></ProfileChangeModal>

    </div>
    <div
      class="chat-user-img online align-self-center m-1"
      id="description"
    >
      <p class="chat-user-message align-self-center ml-2 mb-0">
        {{ user.description }}
      </p>
    </div>
    <div
      class="m-0 p-0 "
      id="exp"
    >
      <div class="container-fluid align-self-center row m-0 p-0">
        <div class="col-1 m-0 p-0 align-self-center text-right">
          <img
            :src="require(`@/assets/images/tier/`+user.tier+`.png`)"
            alt="prog-tier-img"
            id="prog-tier-img"
            class="m-0 p-0 align-self-center rounded-circle"
          />
        </div>
        <h5 class="col-3 m-0 pl-1 p-0 align-self-center">
          {{user.tier}}
        </h5>
        <div class="col-3"></div>
        <h6 class="col-5 m-0 pr-3 text-right align-self-center">
          {{user.exp}}/{{user.nextExp}}
        </h6>
      </div>
      <div
        class="progress mt-2 mx-3 mb-3"
        id="progress-bar"
      >
        <div
          class="progress-bar bg-info"
          role="progressbar"
          :style="'width:'+expPercent+'%'"
          aria-valuenow="50"
          aria-valuemin="0"
          aria-valuemax="100"
        ></div>
      </div>
    </div>
  </div>
</template>

<script>
import ProfileChangeModal from "./profileChangeModal.vue";
import WithdrawModal from "./withdrawModal.vue";
import store from "@/store";
export default {
  name: "userInfo",
  components: { ProfileChangeModal, WithdrawModal },
  props: {
    user: {
      type: Object,
      require: true,
    },
  },
  data() {
    return {
      expPercent: 0,
      // profile: this.user.profile,
    };
  },
  computed: {
    currNickname() {
      return store.getters["users/getUser"].nickname;
    },
  },
  created() {
    this.expPercent = parseInt((this.user.exp / this.user.nextExp) * 100);
  },
  methods: {
    searchHistory(user) {
      console.log(user);
    },
  },
};
</script>
<style>
#prog-tier-img {
  width: 23px;
  height: 23px;
}
#progress-bar {
  height: 25px;
}
</style>