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
      <form @submit.prevent="updateprofile">
        <div class="form-group mb-4">
          <label for="updateProfile-input">
            프로필
          </label>
          <b-form-file
            id="file-small"
            placeholder="프로필 이미지를 선택하세요."
            v-on:change="changeImage"
            :class="{
                'is-invalid': submitted && $v.profileDetail.image.$error,
              }"
          ></b-form-file>

          <div
            v-if="submitted && $v.profileDetail.image.$error"
            class="invalid-feedback"
          >
            <span v-if="!$v.profileDetail.image.required">{{
                $t("chat.tabs.settings.modal.form.profile.validation.required")
              }}</span>
          </div>
          <input
            v-if="selectedImage"
            v-model="selectedImage.name"
            type="hidden"
            name="photo"
          />
        </div>
        <div class="form-group mb-4">
          <label for="updateName-input">
            닉네임
          </label>
          <input
            type="text"
            class="form-control"
            id="updateNickname-input"
            :placeholder="nickname"
            v-model="profileDetail.nickname"
            @blur="onBlur"
            :class="{
                'is-invalid': submitted && $v.profileDetail.nickname.$error,
              }"
          />
          <div
            v-if="submitted && $v.profileDetail.nickname.$error"
            class="invalid-feedback"
          >
            <span v-if="!$v.profileDetail.nickname.required">{{
                $t("chat.tabs.settings.modal.form.nickname.validation.required")
              }}</span>
          </div>
        </div>

        <div class="form-group mb-4">
          <label for="updateDescription-input">
            소개글
          </label>
          <input
            type="text"
            class="form-control"
            id="updateDescription-input"
            :placeholder="description"
            v-model="profileDetail.description"
            :class="{
                'is-invalid': submitted && $v.profileDetail.description.$error,
              }"
          />
          <div
            v-if="submitted && $v.profileDetail.description.$error"
            class="invalid-feedback"
          >
            <span v-if="!$v.profileDetail.description.required">{{
                $t("chat.tabs.settings.modal.form.description.validation.required")
              }}</span>
          </div>
        </div>

        <div class="text-right pt-5 mt-3">
          <b-button
            variant="link"
            @click="showModal = false"
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
      </form>
    </div>
  </div>
</template>

<script>
import { required } from "vuelidate/lib/validators";
import store from "@/store";
import { getUserByNickname, updateUserByOauthId } from "@/api/users.js";
export default {
  name: "ChangeProfileModal",
  components: {},
  data() {
    return {
      showModal: false,
      submitted: false,
      form: {
        file: null,
      },
      profile: this.$store.state.users.user.profile,
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
    oauthId() {
      return this.user.oauthId;
    },
  },

  validations: {
    profileDetail: {
      nickname: { required },
    },
  },
  methods: {
    updateprofile() {
      // this.submitted = true;
      // // stop here if form is invalid
      // this.$v.$touch();
      // if (this.$v.$invalid) {
      //   return;
      // } else {
      //   this.profile = this.image;
      //   this.nickname = this.profileDetail.nickname;
      //   //this.showModal = false;
      // }
      // this.submitted = false;
      // this.form = {};
    },

    onBlur($event) {
      console.log("blur event occur ", $event.target.value);
      console.log("blur event occur ", this.profileDetail.nickname);
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

    //oauthId로 사용자 정보 저장하는방식으로 변경하기
    saveUser() {
      let userData = {
        profile: this.profileDetail.profile.name,
        nickname: this.profileDetail.nickname
          ? this.profileDetail.nickname
          : this.nickname,
        description: this.profileDetail.description
          ? this.profileDetail.description
          : this.description,
        oauthId: this.oauthId,
      };
      console.log("[update user, saveUser] userData: ", userData);
      updateUserByOauthId(this.oauthId, userData)
        .then(({ data }) => {
          console.log(data);
          let updateUserData = {
            nickname: data.data.nickname,
            description: data.data.description,
            profile: data.data.profile,
            oauthId: data.data.oauthId,
          };
          store.commit("users/UPDATE_USER", updateUserData);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>