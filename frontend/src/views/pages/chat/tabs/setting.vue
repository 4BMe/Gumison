<script>
import axios from "axios";
import simplebar from "simplebar-vue";
import { required, email } from "vuelidate/lib/validators";

/**
 * Setting tab component
 */
export default {
  components: {
    simplebar,
  },
  data() {
    return {
      showModal: false,
      submitted: false,
      form: {
        file: null,
      },
      allowableTypes: ["jpg", "jpeg", "png", "gif"],
      maximumSize: 5000000,
      selectedImage: null,
       username: "Patricia Smith",
      profile: require("@/assets/images/users/avatar-1.jpg"),
      useremail: "adc@123.com",
      userlocation: "California, USA",
      options: {
        url: "https://httpbin.org/post",
        type: "POST",
      },
      profileDetail: {
        image: null,
        name: "",
        email: "",
        location: "",
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
  mounted() {},
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

<template>
  <div>
    <div class="px-4 pt-4">
      <h4 class="mb-0">{{ $t("chat.tabs.settings.title") }}</h4>
    </div>

    <div class="text-center border-bottom p-4">
      <div class="mb-4 profile-user">
        <img
          :src="profile"
          class="rounded-circle avatar-lg img-thumbnail"
          alt
        />
        <button
          type="button"
          class="btn bg-light avatar-xs p-0 rounded-circle profile-photo-edit"
          @click="showModal = true"
        >
          <i class="ri-pencil-fill"></i>
        </button>
      </div>

      <b-modal
        v-model="showModal"
        :title="$t('chat.tabs.settings.modal.title')"
        hide-footer
      >
        <form @submit.prevent="updateprofile">
          <div class="form-group mb-4">
            <label for="updateProfile-input">{{
              $t("chat.tabs.settings.modal.form.profile.label")
            }}</label>
            <b-form-file
              id="file-small"
              placeholder="Choose profile"
              v-on:change="changeImage"
              :class="{
                'is-invalid': submitted && $v.profileDetail.image.$error,
              }"
            ></b-form-file>
            <input
              v-if="selectedImage"
              v-model="selectedImage.name"
              type="hidden"
              name="photo"
            />
              <div
              v-if="submitted && $v.profileDetail.image.$error"
              class="invalid-feedback"
            >
              <span v-if="!$v.profileDetail.image.required">{{
                $t("chat.tabs.settings.modal.form.profile.validation.required")
              }}</span>
            </div>
          </div>
          <div class="form-group mb-4">
            <label for="updateName-input">{{
              $t("chat.tabs.settings.modal.form.name.label")
            }}</label>
            <input
              type="text"
              class="form-control"
              id="updateName-input"
              :placeholder="
                $t('chat.tabs.settings.modal.form.name.placeholder')
              "
              v-model="profileDetail.name"
              :class="{
                'is-invalid': submitted && $v.profileDetail.name.$error,
              }"
            />
            <div
              v-if="submitted && $v.profileDetail.name.$error"
              class="invalid-feedback"
            >
              <span v-if="!$v.profileDetail.name.required">{{
                $t("chat.tabs.settings.modal.form.name.validation.required")
              }}</span>
            </div>
          </div>

          <div class="form-group mb-4">
            <label for="updateEmail-input">{{
              $t("chat.tabs.settings.modal.form.email.label")
            }}</label>
            <input
              type="text"
              class="form-control"
              id="updateEmail-input"
              :placeholder="
                $t('chat.tabs.settings.modal.form.email.placeholder')
              "
              v-model="profileDetail.email"
              :class="{
                'is-invalid': submitted && $v.profileDetail.email.$error,
              }"
            />
            <div
              v-if="submitted && $v.profileDetail.email.$error"
              class="invalid-feedback"
            >
              <span v-if="!$v.profileDetail.email.required">{{
                $t("chat.tabs.settings.modal.form.email.validation.required")
              }}</span>
              <span v-if="!$v.profileDetail.email.email">{{
                $t("chat.tabs.settings.modal.form.email.validation.invalid")
              }}</span>
            </div>
          </div>

          <div class="form-group mb-4">
            <label for="updateLocation-input">{{
              $t("chat.tabs.settings.modal.form.location.label")
            }}</label>
            <input
              type="text"
              class="form-control"
              id="updateLocation-input"
              :placeholder="
                $t('chat.tabs.settings.modal.form.location.placeholder')
              "
              v-model="profileDetail.location"
              :class="{
                'is-invalid': submitted && $v.profileDetail.location.$error,
              }"
            />
            <div
              v-if="submitted && $v.profileDetail.location.$error"
              class="invalid-feedback"
            >
              <span v-if="!$v.profileDetail.location.required">{{
                $t("chat.tabs.settings.modal.form.location.validation.required")
              }}</span>
            </div>
          </div>

          <div class="text-right pt-5 mt-3">
            <b-button variant="link" @click="showModal = false">{{
              $t("chat.tabs.settings.modal.form.button.close")
            }}</b-button>
            <b-button type="submit" variant="primary" class="ml-1">{{
              $t("chat.tabs.settings.modal.form.button.save")
            }}</b-button>
          </div>
        </form>
      </b-modal>

      <h5 class="font-size-16 mb-1 text-truncate">{{ username }}</h5>
      <b-dropdown
        class="d-inline-block mb-1"
        variant="white"
        toggle-class="text-muted pb-1 d-block"
      >
        <template v-slot:button-content>
          {{ $t("chat.tabs.settings.status.text") }}
          <i class="mdi mdi-chevron-down"></i>
        </template>

        <a class="dropdown-item" href="javascript:void(0);">{{
          $t("chat.tabs.settings.status.dropdown.available")
        }}</a>
        <a class="dropdown-item" href="javascript:void(0);">{{
          $t("chat.tabs.settings.status.dropdown.busy")
        }}</a>
      </b-dropdown>
    </div>
    <!-- End profile user -->

    <!-- Start User profile description -->
    <simplebar class="p-4 user-profile-desc">
      <b-card no-body class="custom-accordion border">
        <b-card-header header-tag="div" role="tab">
          <a
            v-b-toggle.accordion-1
            class="text-dark"
            href="javascript: void(0);"
          >
            <h5 class="font-size-14 m-0">
              {{ $t("chat.tabs.settings.info.title") }}
              <i class="mdi mdi-chevron-up float-right accor-plus-icon"></i>
            </h5>
          </a>
        </b-card-header>
        <b-collapse id="accordion-1" visible accordion="my-accordion">
          <b-card-body>
            <div class="float-right">
              <button type="button" class="btn btn-light btn-sm">
                <i class="ri-edit-fill mr-1 align-middle"></i>
                {{ $t("chat.tabs.settings.info.edit") }}
              </button>
            </div>
            <div>
              <p class="text-muted mb-1">
                {{ $t("chat.tabs.settings.info.name.text") }}
              </p>
              <h5 class="font-size-14">{{ username }}</h5>
            </div>

            <div class="mt-4">
              <p class="text-muted mb-1">
                {{ $t("chat.tabs.settings.info.email.text") }}
              </p>
              <h5 class="font-size-14">{{ useremail }}</h5>
            </div>

            <div class="mt-4">
              <p class="text-muted mb-1">
                {{ $t("chat.tabs.settings.info.time.text") }}
              </p>
              <h5 class="font-size-14">
                {{ $t("chat.tabs.settings.info.time.value") }}
              </h5>
            </div>

            <div class="mt-4">
              <p class="text-muted mb-1">
                {{ $t("chat.tabs.settings.info.location.text") }}
              </p>
              <h5 class="font-size-14 mb-0">{{ userlocation }}</h5>
            </div>
          </b-card-body>
        </b-collapse>
      </b-card>

      <b-card no-body class="custom-accordion border">
        <b-card-header header-tag="div" role="tab">
          <a
            v-b-toggle.accordion-2
            class="text-dark"
            href="javascript: void(0);"
          >
            <h5 class="font-size-14 m-0">
              {{ $t("chat.tabs.settings.privacy.title") }}
              <i class="mdi mdi-chevron-up float-right accor-plus-icon"></i>
            </h5>
          </a>
        </b-card-header>
        <b-collapse id="accordion-2" accordion="my-accordion">
          <b-card-body>
            <div class="py-3">
              <div class="media align-items-center">
                <div class="media-body overflow-hidden">
                  <h5 class="font-size-13 mb-0 text-truncate">
                    {{ $t("chat.tabs.settings.privacy.photo") }}
                  </h5>
                </div>
                <b-dropdown
                  variant="light"
                  class="ml-2"
                  toggle-class="btn-sm dropdown-toggle w-sm"
                  right
                >
                  <template v-slot:button-content>
                    {{ $t("chat.tabs.settings.privacy.dropdowntext") }}
                    <i class="mdi mdi-chevron-down"></i>
                  </template>
                  <b-dropdown-item>{{
                    $t("chat.tabs.settings.privacy.dropdown.everyone")
                  }}</b-dropdown-item>
                  <b-dropdown-item>{{
                    $t("chat.tabs.settings.privacy.dropdown.selected")
                  }}</b-dropdown-item>
                  <b-dropdown-item>{{
                    $t("chat.tabs.settings.privacy.dropdown.nobody")
                  }}</b-dropdown-item>
                </b-dropdown>
              </div>
            </div>
            <div class="py-3 border-top">
              <div class="media align-items-center">
                <div class="media-body overflow-hidden">
                  <h5 class="font-size-13 mb-0 text-truncate">
                    {{ $t("chat.tabs.settings.privacy.seen") }}
                  </h5>
                </div>
                <div class="ml-2">
                  <div class="custom-control custom-switch">
                    <input
                      type="checkbox"
                      class="custom-control-input"
                      id="privacy-lastseenSwitch"
                      checked
                    />
                    <label
                      class="custom-control-label"
                      for="privacy-lastseenSwitch"
                    ></label>
                  </div>
                </div>
              </div>
            </div>

            <div class="py-3 border-top">
              <div class="media align-items-center">
                <div class="media-body overflow-hidden">
                  <h5 class="font-size-13 mb-0 text-truncate">
                    {{ $t("chat.tabs.settings.privacy.status") }}
                  </h5>
                </div>
                <b-dropdown
                  variant="light"
                  class="ml-2"
                  toggle-class="btn-sm dropdown-toggle w-sm"
                  right
                >
                  <template v-slot:button-content>
                    {{ $t("chat.tabs.settings.privacy.dropdowntext") }}
                    <i class="mdi mdi-chevron-down"></i>
                  </template>
                  <b-dropdown-item>{{
                    $t("chat.tabs.settings.privacy.dropdown.everyone")
                  }}</b-dropdown-item>
                  <b-dropdown-item>{{
                    $t("chat.tabs.settings.privacy.dropdown.selected")
                  }}</b-dropdown-item>
                  <b-dropdown-item>{{
                    $t("chat.tabs.settings.privacy.dropdown.nobody")
                  }}</b-dropdown-item>
                </b-dropdown>
              </div>
            </div>

            <div class="py-3 border-top">
              <div class="media align-items-center">
                <div class="media-body overflow-hidden">
                  <h5 class="font-size-13 mb-0 text-truncate">
                    {{ $t("chat.tabs.settings.privacy.receipt") }}
                  </h5>
                </div>
                <div class="ml-2">
                  <div class="custom-control custom-switch">
                    <input
                      type="checkbox"
                      class="custom-control-input"
                      id="privacy-readreceiptSwitch"
                      checked
                    />
                    <label
                      class="custom-control-label"
                      for="privacy-readreceiptSwitch"
                    ></label>
                  </div>
                </div>
              </div>
            </div>

            <div class="py-3 border-top">
              <div class="media align-items-center">
                <div class="media-body overflow-hidden">
                  <h5 class="font-size-13 mb-0 text-truncate">
                    {{ $t("chat.tabs.settings.privacy.groups") }}
                  </h5>
                </div>
                <b-dropdown
                  variant="light"
                  class="ml-2"
                  toggle-class="btn-sm dropdown-toggle w-sm"
                  right
                >
                  <template v-slot:button-content>
                    {{ $t("chat.tabs.settings.privacy.dropdowntext") }}
                    <i class="mdi mdi-chevron-down"></i>
                  </template>
                  <b-dropdown-item>{{
                    $t("chat.tabs.settings.privacy.dropdown.everyone")
                  }}</b-dropdown-item>
                  <b-dropdown-item>{{
                    $t("chat.tabs.settings.privacy.dropdown.selected")
                  }}</b-dropdown-item>
                  <b-dropdown-item>{{
                    $t("chat.tabs.settings.privacy.dropdown.nobody")
                  }}</b-dropdown-item>
                </b-dropdown>
              </div>
            </div>
          </b-card-body>
        </b-collapse>
      </b-card>

      <b-card no-body class="custom-accordion border">
        <b-card-header header-tag="div" role="tab">
          <a
            v-b-toggle.accordion-3
            class="text-dark"
            href="javascript: void(0);"
          >
            <h5 class="font-size-14 m-0">
              {{ $t("chat.tabs.settings.security.title") }}
              <i class="mdi mdi-chevron-up float-right accor-plus-icon"></i>
            </h5>
          </a>
        </b-card-header>
        <b-collapse id="accordion-3" accordion="my-accordion">
          <b-card-body>
            <div>
              <div class="media align-items-center">
                <div class="media-body overflow-hidden">
                  <h5 class="font-size-13 mb-0 text-truncate">
                    {{ $t("chat.tabs.settings.security.text") }}
                  </h5>
                </div>
                <div class="ml-2">
                  <div class="custom-control custom-switch">
                    <input
                      type="checkbox"
                      class="custom-control-input"
                      id="security-notificationswitch"
                    />
                    <label
                      class="custom-control-label"
                      for="security-notificationswitch"
                    ></label>
                  </div>
                </div>
              </div>
            </div>
          </b-card-body>
        </b-collapse>
      </b-card>

      <b-card no-body class="custom-accordion border">
        <b-card-header header-tag="div" role="tab">
          <a
            v-b-toggle.accordion-4
            class="text-dark"
            href="javascript: void(0);"
          >
            <h5 class="font-size-14 m-0">
              {{ $t("chat.tabs.settings.help.title") }}
              <i class="mdi mdi-chevron-up float-right accor-plus-icon"></i>
            </h5>
          </a>
        </b-card-header>
        <b-collapse id="accordion-4" accordion="my-accordion">
          <b-card-body>
            <div>
              <div class="py-3">
                <h5 class="font-size-13 mb-0">
                  <a href="javascript:void(0);" class="text-body d-block">{{
                    $t("chat.tabs.settings.help.faqs")
                  }}</a>
                </h5>
              </div>
              <div class="py-3 border-top">
                <h5 class="font-size-13 mb-0">
                  <a href="javascript:void(0);" class="text-body d-block">{{
                    $t("chat.tabs.settings.help.contact")
                  }}</a>
                </h5>
              </div>
              <div class="py-3 border-top">
                <h5 class="font-size-13 mb-0">
                  <a href="javascript:void(0);" class="text-body d-block">{{
                    $t("chat.tabs.settings.help.policy")
                  }}</a>
                </h5>
              </div>
            </div>
          </b-card-body>
        </b-collapse>
      </b-card>

      <!-- end profile-setting-accordion -->
    </simplebar>
    <!-- End User profile description -->
  </div>
</template>