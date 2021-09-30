<template>
  <div>
    <div class="px-4 pt-4">
      <div class="user-chat-nav float-right">
        <b-dropdown
          right
          variant="white"
          toggle-class="font-size-18 text-muted p-0"
        >
          <template v-slot:button-content>
            <i class="ri-more-2-fill"></i>
          </template>
          <b-dropdown-item href="javascript:void(0);">{{
            $t("chat.tabs.profile.dropdown.edit")
          }}</b-dropdown-item>
          <b-dropdown-item href="javascript:void(0);">{{
            $t("chat.tabs.profile.dropdown.action")
          }}</b-dropdown-item>
          <b-dropdown-divider></b-dropdown-divider>
          <b-dropdown-item href="javascript:void(0);">{{
            $t("chat.tabs.profile.dropdown.another")
          }}</b-dropdown-item>
        </b-dropdown>
      </div>
      <h4 class="mb-0">{{ $t("chat.tabs.profile.title") }}</h4>
    </div>

    <div class="text-center p-4 border-bottom">
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
      </b-modal>
      <h5 class="font-size-16 mb-1 text-truncate">
        {{ username }}
      </h5>
      <p class="text-muted text-truncate mb-1">
        <i class="ri-record-circle-fill font-size-10 text-success mr-1 d-inline-block"></i>
        {{ $t("chat.tabs.profile.status") }}
      </p>
    </div>
    <!-- End profile user -->

    <!-- Start user-profile-desc -->

    <simplebar class="p-4 user-profile-desc">
      <div class="text-muted">
        <p class="mb-4">{{ $t("chat.tabs.profile.message") }}</p>
      </div>
      <b-card
        no-body
        class="border custom-accordion"
      >
        <b-card-header>
          <a
            href="javascript: void(0);"
            v-b-toggle.profileaccordion-1
          >
            <h5 class="font-size-15 m-0">
              <i class="ri-user-2-line mr-2 align-middle d-inline-block"></i>
              {{ $t("chat.tabs.profile.about.text") }}
              <i class="mdi mdi-chevron-up float-right accor-plus-icon"></i>
            </h5>
          </a>
        </b-card-header>
        <b-collapse
          id="profileaccordion-1"
          visible
          accordion="accordion"
        >
          <b-card-body>
            <div>
              <p class="text-muted mb-1">
                {{ $t("chat.tabs.profile.about.name.text") }}
              </p>
              <h5 class="font-size-14">
                {{ username }}
              </h5>
            </div>

            <div class="mt-4">
              <p class="text-muted mb-1">
                {{ $t("chat.tabs.profile.about.email.text") }}
              </p>
              <h5 class="font-size-14">
                {{ useremail }}
              </h5>
            </div>

            <div class="mt-4">
              <p class="text-muted mb-1">
                {{ $t("chat.tabs.profile.about.time.text") }}
              </p>
              <h5 class="font-size-14">
                {{ $t("chat.tabs.profile.about.time.value") }}
              </h5>
            </div>

            <div class="mt-4">
              <p class="text-muted mb-1">
                {{ $t("chat.tabs.profile.about.location.text") }}
              </p>
              <h5 class="font-size-14 mb-0">
                {{ userlocation }}
              </h5>
            </div>
          </b-card-body>
        </b-collapse>
      </b-card>

      <b-card
        no-body
        class="border custom-accordion"
      >
        <b-card-header>
          <a
            href="javascript: void(0);"
            v-b-toggle.profileaccordion-2
          >
            <h5 class="font-size-14 m-0">
              <i class="ri-attachment-line mr-2 align-middle d-inline-block"></i>
              {{ $t("chat.tabs.profile.attachedfile.text") }}
              <i class="mdi mdi-chevron-up float-right accor-plus-icon"></i>
            </h5>
          </a>
        </b-card-header>
        <b-collapse
          id="profileaccordion-2"
          accordion="accordion"
        >
          <b-card-body>
            <div class="card p-2 border mb-2">
              <div class="media align-items-center">
                <div class="avatar-sm mr-3">
                  <div class="avatar-title bg-soft-primary text-primary rounded font-size-20">
                    <i class="ri-file-text-fill"></i>
                  </div>
                </div>
                <div class="media-body">
                  <div class="text-left">
                    <h5 class="font-size-14 mb-1">
                      {{ $t("chat.tabs.profile.attachedfile.file1.name") }}
                    </h5>
                    <p class="text-muted font-size-13 mb-0">
                      {{ $t("chat.tabs.profile.attachedfile.file1.size") }}
                    </p>
                  </div>
                </div>

                <div class="ml-4">
                  <ul class="list-inline mb-0 font-size-18">
                    <li class="list-inline-item">
                      <a
                        href="javascript:void(0);"
                        class="text-muted px-1"
                      >
                        <i class="ri-download-2-line"></i>
                      </a>
                    </li>
                    <b-dropdown
                      class="list-inline-item"
                      toggle-class="text-muted px-1"
                      right
                      variant="white"
                    >
                      <template v-slot:button-content>
                        <i class="ri-more-fill"></i>
                      </template>
                      <a
                        class="dropdown-item"
                        href="javascript:void(0);"
                      >{{
                        $t("chat.tabs.profile.attachedfile.dropdown.action")
                      }}</a>
                      <a
                        class="dropdown-item"
                        href="javascript:void(0);"
                      >{{
                        $t("chat.tabs.profile.attachedfile.dropdown.another")
                      }}</a>
                      <div class="dropdown-divider"></div>
                      <a
                        class="dropdown-item"
                        href="javascript:void(0);"
                      >{{
                        $t("chat.tabs.profile.attachedfile.dropdown.delete")
                      }}</a>
                    </b-dropdown>
                  </ul>
                </div>
              </div>
            </div>
            <!-- end card -->

            <div class="card p-2 border mb-2">
              <div class="media align-items-center">
                <div class="avatar-sm mr-3">
                  <div class="avatar-title bg-soft-primary text-primary rounded font-size-20">
                    <i class="ri-image-fill"></i>
                  </div>
                </div>
                <div class="media-body">
                  <div class="text-left">
                    <h5 class="font-size-14 mb-1">
                      {{ $t("chat.tabs.profile.attachedfile.file2.name") }}
                    </h5>
                    <p class="text-muted font-size-13 mb-0">
                      {{ $t("chat.tabs.profile.attachedfile.file2.size") }}
                    </p>
                  </div>
                </div>

                <div class="ml-4">
                  <ul class="list-inline mb-0 font-size-18">
                    <li class="list-inline-item">
                      <a
                        href="javascript:void(0);"
                        class="text-muted px-1"
                      >
                        <i class="ri-download-2-line"></i>
                      </a>
                    </li>
                    <b-dropdown
                      class="list-inline-item"
                      toggle-class="text-muted px-1"
                      right
                      variant="white"
                    >
                      <template v-slot:button-content>
                        <i class="ri-more-fill"></i>
                      </template>
                      <a
                        class="dropdown-item"
                        href="javascript:void(0);"
                      >{{
                        $t("chat.tabs.profile.attachedfile.dropdown.action")
                      }}</a>
                      <a
                        class="dropdown-item"
                        href="javascript:void(0);"
                      >{{
                        $t("chat.tabs.profile.attachedfile.dropdown.another")
                      }}</a>
                      <div class="dropdown-divider"></div>
                      <a
                        class="dropdown-item"
                        href="javascript:void(0);"
                      >{{
                        $t("chat.tabs.profile.attachedfile.dropdown.delete")
                      }}</a>
                    </b-dropdown>
                  </ul>
                </div>
              </div>
            </div>
            <!-- end card -->

            <div class="card p-2 border mb-2">
              <div class="media align-items-center">
                <div class="avatar-sm mr-3">
                  <div class="avatar-title bg-soft-primary text-primary rounded font-size-20">
                    <i class="ri-image-fill"></i>
                  </div>
                </div>
                <div class="media-body">
                  <div class="text-left">
                    <h5 class="font-size-14 mb-1">
                      {{ $t("chat.tabs.profile.attachedfile.file3.name") }}
                    </h5>
                    <p class="text-muted font-size-13 mb-0">
                      {{ $t("chat.tabs.profile.attachedfile.file3.size") }}
                    </p>
                  </div>
                </div>

                <div class="ml-4">
                  <ul class="list-inline mb-0 font-size-18">
                    <li class="list-inline-item">
                      <a
                        href="javascript:void(0);"
                        class="text-muted px-1"
                      >
                        <i class="ri-download-2-line"></i>
                      </a>
                    </li>
                    <b-dropdown
                      class="list-inline-item"
                      toggle-class="text-muted px-1"
                      right
                      variant="white"
                    >
                      <template v-slot:button-content>
                        <i class="ri-more-fill"></i>
                      </template>
                      <a
                        class="dropdown-item"
                        href="javascript:void(0);"
                      >{{
                        $t("chat.tabs.profile.attachedfile.dropdown.action")
                      }}</a>
                      <a
                        class="dropdown-item"
                        href="javascript:void(0);"
                      >{{
                        $t("chat.tabs.profile.attachedfile.dropdown.another")
                      }}</a>
                      <div class="dropdown-divider"></div>
                      <a
                        class="dropdown-item"
                        href="javascript:void(0);"
                      >{{
                        $t("chat.tabs.profile.attachedfile.dropdown.delete")
                      }}</a>
                    </b-dropdown>
                  </ul>
                </div>
              </div>
            </div>
            <!-- end card -->

            <div class="card p-2 border mb-2">
              <div class="media align-items-center">
                <div class="avatar-sm mr-3">
                  <div class="avatar-title bg-soft-primary text-primary rounded font-size-20">
                    <i class="ri-file-text-fill"></i>
                  </div>
                </div>
                <div class="media-body">
                  <div class="text-left">
                    <h5 class="font-size-14 mb-1">
                      {{ $t("chat.tabs.profile.attachedfile.file4.name") }}
                    </h5>
                    <p class="text-muted font-size-13 mb-0">
                      {{ $t("chat.tabs.profile.attachedfile.file4.size") }}
                    </p>
                  </div>
                </div>

                <div class="ml-4">
                  <ul class="list-inline mb-0 font-size-18">
                    <li class="list-inline-item">
                      <a
                        href="javascript:void(0);"
                        class="text-muted px-1"
                      >
                        <i class="ri-download-2-line"></i>
                      </a>
                    </li>
                    <b-dropdown
                      class="list-inline-item"
                      toggle-class="text-muted px-1"
                      right
                      variant="white"
                    >
                      <template v-slot:button-content>
                        <i class="ri-more-fill"></i>
                      </template>
                      <a
                        class="dropdown-item"
                        href="javascript:void(0);"
                      >{{
                        $t("chat.tabs.profile.attachedfile.dropdown.action")
                      }}</a>
                      <a
                        class="dropdown-item"
                        href="javascript:void(0);"
                      >{{
                        $t("chat.tabs.profile.attachedfile.dropdown.another")
                      }}</a>
                      <div class="dropdown-divider"></div>
                      <a
                        class="dropdown-item"
                        href="javascript:void(0);"
                      >{{
                        $t("chat.tabs.profile.attachedfile.dropdown.delete")
                      }}</a>
                    </b-dropdown>
                  </ul>
                </div>
              </div>
            </div>
            <!-- end card -->
          </b-card-body>
        </b-collapse>
      </b-card>

      <!-- end profile-user-accordion -->
    </simplebar>

    <!-- end user-profile-desc -->
  </div>
</template>

<script>
import axios from "axios";
import simplebar from "simplebar-vue";

import { required, email } from "vuelidate/lib/validators";

/**
 * Profile component
 */
export default {
  data() {
    return {
      showModal: false,
      submitted: false,
      form: {
        file: null,
      },
      username: "Patricia Smith",
      profile: require("@/assets/images/users/avatar-1.jpg"),
      useremail: "adc@123.com",
      userlocation: "California, USA",
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
  components: { simplebar },
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

