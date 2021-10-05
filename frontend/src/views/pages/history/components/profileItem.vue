<template>
  <form>
    <div class="form-group mb-4">
      <label for="updateProfile-input">
        프로필
      </label>
      <b-form-file
        id="file-small"
        :placeholder="profile"
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

  </form>
</template>

<script>
import { required } from "vuelidate/lib/validators";
import { getUserByNickname } from "@/api/users.js";
export default {
  name: "profileItem",
  components: {},
  props: ["profileDetail", "profile", "user", "nickname", "description"],
  data() {
    return {
      submitted: false,
      form: {
        file: null,
      },
      allowableTypes: ["jpg", "jpeg", "png", "gif"],
      maximumSize: 5000000,
      selectedImage: null,
      image: null,
    };
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
  },
};
</script>