<template>
  <!-- <b-modal
    id="modal-change-profile"
    v-model="showModal"
    :title="$t('chat.tabs.settings.modal.title')"
    hide-footer
  > -->
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
        placeholder="닉네임을 입력하세요."
        v-model="profileDetail.nickname"
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
      <label for="updateName-input">
        소개글
      </label>
      <input
        type="text"
        class="form-control"
        id="updateDescription-input"
        placeholder="소개글을 입력하세요."
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
      >{{
              $t("chat.tabs.settings.modal.form.button.save")
            }}</b-button>
    </div>
  </form>
  <!-- </b-modal> -->
</template>

<script>
import axios from "axios";
import { required, email } from "vuelidate/lib/validators";

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
      nickname: "붕붕바라바라붕붕",
      profile: require("@/assets/images/users/avatar-1.jpg"),
      description: "hello",
      profileDetail: {
        image: null,
        name: "",
        email: "",
        location: "",
      },
      allowableTypes: ["jpg", "jpeg", "png", "gif"],
      maximumSize: 5000000,
      selectedImage: null,
      image: null,
      options: {
        url: "https://httpbin.org/post",
        type: "POST",
      },
    };
  },

  validations: {
    profileDetail: {
      image: { required },
      name: { required },
      email: { required, email },
      location: { required },
    },
  },
  methods: {
    updateprofile() {
      this.submitted = true;
      // stop here if form is invalid
      this.$v.$touch();
      if (this.$v.$invalid) {
        return;
      } else {
        this.profile = this.image;

        this.username = this.profileDetail.name;
        this.useremail = this.profileDetail.email;
        this.userlocation = this.profileDetail.location;
        this.showModal = false;
      }
      this.submitted = false;
      this.form = {};
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
      this.profileDetail.image = $event.target.files[0];

      //validate the image
      if (!this.validate(this.selectedImage)) return;
      // create a form
      const form = new FormData();
      form.append("file", this.selectedImage);
      axios
        .post("https://httpbin.org/post", { data: form })
        .then(this.createImage)
        .catch(this.onImageError);
    },
    createImage() {
      const reader = new FileReader();
      reader.onload = (e) => {
        this.image = e.target.result;
      };
      reader.readAsDataURL(this.selectedImage);
    },
  },
};
</script>