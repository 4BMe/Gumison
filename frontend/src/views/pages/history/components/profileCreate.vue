<template>
  <div class="account-pages my-5 pt-sm-5">
    <div class="container">
      <div class="mb-4 profile-user">
        <img
          :src="profile"
          class="rounded-circle avatar-lg img-thumbnail"
          alt
        />
      </div>
      <ProfileItem
        :profileDetail='profileDetail'
        :profile='profile'
        :user='user'
        :nickname='nickname'
        :description='description'
      ></ProfileItem>
      <div class="text-right pt-5 mt-3">
        <b-button
          type="submit"
          variant="primary"
          class="ml-1"
          @click="saveUser()"
        >{{
              $t("chat.tabs.settings.modal.form.button.save")
            }}</b-button>
      </div>
    </div>

  </div>
</template>
<script>
import { required } from "vuelidate/lib/validators";
import store from "@/store";
import { updateUserByOauthId } from "@/api/users.js";
import ProfileItem from "./profileItem.vue";
export default {
  name: "profileCreate",
  components: { ProfileItem },
  data() {
    return {
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
    saveUser() {
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
          this.$router.push({
            name: "myhistory",
            params: { nickname: updateUserData.nickname },
          });
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

