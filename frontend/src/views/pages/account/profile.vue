<template>
  <div>
    <div class="px-4 pt-4">
      <h4 class="mb-0">프로필</h4>
    </div>

    <div class="text-center p-4 border-bottom">
      <div class="mb-4 profile-user">
        <img
          :src="profile"
          class="rounded-circle avatar-lg img-thumbnail"
          alt
        />
        <b-button
          type="button"
          class="btn bg-dark avatar-xs p-0 rounded-circle profile-photo-edit"
          v-b-modal="'modal-change-profile'"
        >
          <i class="ri-pencil-fill"></i>
        </b-button>
      </div>

      <h5 class="font-size-16 mb-1 text-truncate">
        {{ nickname }}
      </h5>
      <div class="text-muted">
        <p class="mb-4">{{description}}</p>
      </div>
    </div>
    <ChangeProfileModal></ChangeProfileModal>
    <!-- End profile user -->

    <!-- Start user-profile-desc -->

    <simplebar class="p-4 user-profile-desc">

    </simplebar>

    <!-- end user-profile-desc -->
  </div>
</template>

<script>
import axios from "axios";
import simplebar from "simplebar-vue";
import { required, email } from "vuelidate/lib/validators";

import ChangeProfileModal from "./components/change-profile-modal.vue";

export default {
  name: "profile",
  components: { simplebar, ChangeProfileModal },
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

