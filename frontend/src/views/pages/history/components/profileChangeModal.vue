<template>
  <b-modal
    id="modal-change-profile"
    v-model="showModal"
    title="회원 정보 변경"
    hide-footer
  >
    <ProfileItem
      :profileDetail='profileDetail'
      :profile='profile'
      :user='user'
      :nickname='nickname'
      :description='description'
    ></ProfileItem>
    <div class="text-right">
      <b-button
        type="button"
        variant="link"
        @click="close()"
      >{{
              $t("chat.tabs.settings.modal.form.button.close")
            }}</b-button>
      <b-button
        type="submit"
        variant="primary"
        class="ml-1"
        @click="saveUser()"
      >{{
              $t("chat.tabs.settings.modal.form.button.save")
            }}</b-button>
    </div>
  </b-modal>
</template>

<script>
import { required } from "vuelidate/lib/validators";
import store from "@/store";
import { updateUserByOauthId } from "@/api/users.js";
import ProfileItem from "./profileItem.vue";
export default {
  name: "profileChangeModal",
  components: { ProfileItem },
  data() {
    return {
      showModal: false,
      submitted: false,

      profile: this.$store.state.users.user.profile
        ? this.$store.state.users.user.profile
        : "프로필 이미지를 선택하세요.",
      profileDetail: {
        profile: null,
        nickname: "",
        description: "",
      },
    };
  },

  computed: {
    user() {
      return store.getters["users/getUser"];
    },
    nickname() {
      return this.user.nickname ? this.user.nickname : "닉네임을 입력하세요.";
    },
    description() {
      return this.user.description
        ? this.user.description
        : "소개글을 입력하세요.";
    },
  },

  validations: {
    profileDetail: {
      nickname: { required },
    },
  },
  methods: {
    close() {
      this.showModal = false;
    },
    saveUser() {
      console.log("saveUser");
      let userData = {
        profile: this.profileDetail.profile
          ? this.profileDetail.profile.name
          : this.profile,
        nickname: this.profileDetail.nickname
          ? this.profileDetail.nickname
          : this.nickname,
        description: this.profileDetail.description
          ? this.profileDetail.description
          : this.description,
        oauthId: this.user.oauthId,
      };
      console.log("[update user, saveUser] userData: ", userData);
      updateUserByOauthId(this.user.oauthId, userData)
        .then(({ data }) => {
          console.log(data);
          let updateUserData = {
            nickname: data.data.nickname,
            description: data.data.description,
            profile: data.data.profile,
            oauthId: data.data.oauthId,
          };
          store.commit("users/UPDATE_USER", updateUserData);
          this.showModal = false;
          //this.$router.go();
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>