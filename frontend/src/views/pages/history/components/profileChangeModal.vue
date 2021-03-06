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
      :isValidNickname='isValidNickname'
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
    close() {
      this.showModal = false;
    },
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

        /**
         * 닉네임을 변환하면 this.$router.push
         * 아니면 this.$router.go
         */
        let changeNickname = false;
        if (
          this.profileDetail.nickname &&
          this.nickname != this.profileDetail.nickname
        ) {
          changeNickname = true;
        }

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
            this.showModal = false;
            if (changeNickname) {
              this.$router.push({
                name: "myhistory",
                params: { nickname: updateUserData.nickname },
              });
            } else {
              this.$router.go();
            }
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
  },
};
</script>