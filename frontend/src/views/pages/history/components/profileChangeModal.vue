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
import { getUserByNickname, updateUserByOauthId } from "@/api/users.js";
import ProfileItem from "./profileItem.vue";
export default {
  name: "profileItem",
  components: { ProfileItem },
  data() {
    return {
      showModal: false,
      submitted: false,
      form: {
        file: null,
      },
      profile: this.$store.state.users.user.profile
        ? this.$store.state.users.user.profile
        : "프로필 이미지를 선택하세요.",
      profileDetail: {
        profile: null,
        nickname: "",
        description: "",
      },
      allowableTypes: ["jpg", "jpeg", "png", "gif"],
      maximumSize: 5000000,
      selectedImage: null,
      image: null,
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
    onBlur($event) {
      console.log("blur event occur ", $event.target.value);
      getUserByNickname(this.profileDetail.nickname)
        .then(({ data }) => {
          console.log(data);
          if (!data.data) {
            this.$alertify.error("이미 사용중인 아이디입니다.");
          }
        })
        .catch((error) => {
          console.log("error: ", error);
        });
    },

    validate(image) {
      if (
        !this.allowableTypes.includes(image.name.split(".").pop().toLowerCase())
      ) {
        alert(
          `Sorry you can only upload ${this.allowableTypes
            .join("|")
            .toUpperCase()} files.`
        );
        return false;
      }
      if (image.size > this.maximumSize) {
        alert("Sorry File size exceeding from 5 Mb");
        return false;
      }
      return true;
    },
    onImageError(err) {
      console.log(err, "do something with error");
    },
    changeImage($event) {
      this.selectedImage = $event.target.files[0];
      this.profileDetail.profile = $event.target.files[0];

      //validate the image
      if (!this.validate(this.selectedImage)) return;
      // create a form
      const form = new FormData();
      form.append("file", this.selectedImage);
      // axios
      //   .post("https://httpbin.org/post", { data: form })
      //   .then(this.createImage)
      //   .catch(this.onImageError);
    },
    createImage() {
      const reader = new FileReader();
      reader.onload = (e) => {
        this.image = e.target.result;
      };
    },
    close() {
      this.showModal = false;
    },
    saveUser() {
      let userData = {
        profile: this.profileDetail.profile.name
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
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>