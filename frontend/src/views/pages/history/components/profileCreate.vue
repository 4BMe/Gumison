<template>
  <div class="account-pages my-5 pt-sm-5">
    <div class="container">

      <ProfileItem
        :profileDetail='profileDetail'
        :profile='profile'
        :user='user'
        :nickname='nickname'
        :description='description'
        :isValidNickname='isValidNickname'
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
    isValidNickname() {
      return store.getters["users/getIsValidNickname"];
    },
  },
  validations: {
    profileDetail: {
      nickname: { required },
    },
  },
  methods: {
    // onImageError(err) {
    //   console.log(err, "do something with error");
    // },

    // createImage() {
    //   // create a form
    //   const form = new FormData();
    //   form.append("name", form.values().name);
    //   form.append("file", this.profileDetail.profile);

    //   updateProfileByOauthId(this.user.oauthId, form)
    //     .then(({ data }) => {
    //       console.log("create image data:", data);
    //       this.saveUser();
    //     })
    //     .catch(this.onImageError);
    //   const reader = new FileReader();
    //   reader.onload = (e) => {
    //     this.image = e.target.result;
    //     console.log("create image: ", this.image);
    //   };
    // },

    saveUser() {
      if (!this.isValidNickname) {
        this.$alertify.error("이미 사용중인 아이디입니다.");
        return;
      } else {
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
        updateUserByOauthId(this.user.oauthId, userData)
          .then(({ data }) => {
            let updateUserData = {
              nickname: data.data.nickname,
              description: data.data.description,
              profile: data.data.profile,
              oauthId: data.data.oauthId,
            };
            store.commit("users/UPDATE_USER", updateUserData);
            store.commit("users/SET_IS_VALID_NICKNAME", false);
            this.$router.push({
              name: "myhistory",
              params: { nickname: updateUserData.nickname },
            });
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
  },
};
</script>

