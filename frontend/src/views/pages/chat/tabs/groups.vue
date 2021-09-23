<script>
import simplebar from "simplebar-vue";
import { groups } from "../data";
import { required } from "vuelidate/lib/validators";

export default {
  components: {
    simplebar,
  },
  data() {
    return {
      groups: groups,
      form: {
        name: "",
      },
      submitted: false,
      showModal: false,
    };
  },
  validations: {
    form: {
      name: {
        required,
      },
    },
  },
  methods: {
    save() {
      this.submitted = true;
      // stop here if form is invalid
      this.$v.$touch();
      if (this.$v.$invalid) {
        return;
      } else {
        this.groups.push({
          name: this.form.name,
        });
        this.showModal = false;
      }
      this.submitted = false;
      this.form = {};
    },
  },
};
</script>

<template>
  <div>
    <div class="p-4">
      <div class="user-chat-nav float-right">
        <div v-b-tooltip.hover title="Create group">
          <!-- Button trigger modal -->
          <button
            type="button"
            class="btn btn-link text-decoration-none text-muted font-size-18 py-0"
            @click="showModal = true"
            v-b-tooltip.hover
            title="Create Group"
          >
            <i class="ri-group-line mr-1"></i>
          </button>
        </div>
      </div>
      <h4 class="mb-4">{{ $t("chat.tabs.groups.title") }}</h4>
      <!-- Start add group Modal -->
      <b-modal
        v-model="showModal"
        centered
        hide-footer
        id="modal-1"
        :title="$t('chat.tabs.groups.modal.title')"
      >
        <div class="modal-body p-4">
          <form @submit.prevent="save">
            <div class="form-group mb-4">
              <label for="addgroupname-input">{{
                $t("chat.tabs.groups.modal.form.name.label")
              }}</label>
              <input
                type="text"
                class="form-control"
                id="addgroupname-input"
                v-model="form.name"
                :placeholder="
                  $t('chat.tabs.groups.modal.form.name.placeholder')
                "
                :class="{ 'is-invalid': submitted && $v.form.name.$error }"
              />
              <div
                v-if="submitted && $v.form.name.$error"
                class="invalid-feedback"
              >
                <span v-if="!$v.form.name.required">{{
                  $t("chat.tabs.groups.modal.form.name.validaion.required")
                }}</span>
              </div>
            </div>
            <div class="form-group mb-4">
              <label>{{
                $t("chat.tabs.groups.modal.form.members.label")
              }}</label>
              <div class="mb-3">
                <button
                  class="btn btn-light btn-sm"
                  type="button"
                  v-b-toggle.collapse-1
                >
                  {{ $t("chat.tabs.groups.modal.form.members.button.text") }}
                </button>
              </div>

              <b-collapse id="collapse-1">
                <div class="card border">
                  <div class="card-header">
                    <h5 class="font-size-15 mb-0">Contacts</h5>
                  </div>
                  <div class="card-body p-2">
                    <simplebar style="max-height: 150px; min-height: 150px">
                      <div>
                        <div>
                          <div class="p-3 font-weight-bold text-primary">A</div>

                          <ul class="list-unstyled contact-list">
                            <li>
                              <b-form-checkbox
                                id="checkbox-1"
                                name="checkbox-1"
                                value="accepted"
                                unchecked-value="not_accepted"
                                >{{
                                  $t("chat.tabs.contacts.list.A.text")
                                }}</b-form-checkbox
                              >
                            </li>

                            <li>
                              <b-form-checkbox
                                id="checkbox-2"
                                name="checkbox-2"
                                value="accepted"
                                unchecked-value="not_accepted"
                                >{{
                                  $t("chat.tabs.contacts.list.A.name")
                                }}</b-form-checkbox
                              >
                            </li>
                          </ul>

                          <div>
                            <div class="p-3 font-weight-bold text-primary">
                              {{ $t("chat.tabs.contacts.list.C.text") }}
                            </div>

                            <ul class="list-unstyled contact-list">
                              <li>
                                <b-form-checkbox
                                  id="checkbox-3"
                                  name="checkbox-3"
                                  value="accepted"
                                  unchecked-value="not_accepted"
                                  >{{
                                    $t("chat.tabs.contacts.list.C.name")
                                  }}</b-form-checkbox
                                >
                              </li>
                            </ul>
                          </div>

                          <div>
                            <div class="p-3 font-weight-bold text-primary">
                              {{ $t("chat.tabs.contacts.list.D.text") }}
                            </div>

                            <ul class="list-unstyled contact-list">
                              <li>
                                <b-form-checkbox
                                  id="checkbox-4"
                                  name="checkbox-4"
                                  value="accepted"
                                  unchecked-value="not_accepted"
                                  >{{
                                    $t("chat.tabs.contacts.list.D.name")
                                  }}</b-form-checkbox
                                >
                              </li>
                            </ul>
                          </div>

                          <div>
                            <div class="p-3 font-weight-bold text-primary">
                              {{ $t("chat.tabs.contacts.list.I.text") }}
                            </div>

                            <ul class="list-unstyled contact-list">
                              <li>
                                <b-form-checkbox
                                  id="checkbox-5"
                                  name="checkbox-5"
                                  value="accepted"
                                  unchecked-value="not_accepted"
                                  >{{
                                    $t("chat.tabs.contacts.list.I.name")
                                  }}</b-form-checkbox
                                >
                              </li>
                            </ul>
                          </div>

                          <div>
                            <div class="p-3 font-weight-bold text-primary">
                              {{ $t("chat.tabs.contacts.list.J.text") }}
                            </div>

                            <ul class="list-unstyled contact-list">
                              <li>
                                <b-form-checkbox
                                  id="checkbox-6"
                                  name="checkbox-6"
                                  value="accepted"
                                  unchecked-value="not_accepted"
                                  >{{
                                    $t("chat.tabs.contacts.list.J.name")
                                  }}</b-form-checkbox
                                >
                              </li>

                              <li>
                                <b-form-checkbox
                                  id="checkbox-7"
                                  name="checkbox-7"
                                  value="accepted"
                                  unchecked-value="not_accepted"
                                  >{{
                                    $t("chat.tabs.contacts.list.J.name2")
                                  }}</b-form-checkbox
                                >
                              </li>

                              <li>
                                <b-form-checkbox
                                  id="checkbox-8"
                                  name="checkbox-8"
                                  value="accepted"
                                  unchecked-value="not_accepted"
                                  >{{
                                    $t("chat.tabs.contacts.list.J.name3")
                                  }}</b-form-checkbox
                                >
                              </li>
                            </ul>
                          </div>

                          <div>
                            <div class="p-3 font-weight-bold text-primary">
                              {{ $t("chat.tabs.contacts.list.M.text") }}
                            </div>

                            <ul class="list-unstyled contact-list">
                              <li>
                                <b-form-checkbox
                                  id="checkbox-9"
                                  name="checkbox-9"
                                  value="accepted"
                                  unchecked-value="not_accepted"
                                  >{{
                                    $t("chat.tabs.contacts.list.M.name")
                                  }}</b-form-checkbox
                                >
                              </li>

                              <li>
                                <b-form-checkbox
                                  id="checkbox-10"
                                  name="checkbox-10"
                                  value="accepted"
                                  unchecked-value="not_accepted"
                                  >{{
                                    $t("chat.tabs.contacts.list.M.name2")
                                  }}</b-form-checkbox
                                >
                              </li>
                            </ul>
                          </div>

                          <div>
                            <div class="p-3 font-weight-bold text-primary">
                              {{ $t("chat.tabs.contacts.list.P.text") }}
                            </div>

                            <ul class="list-unstyled contact-list">
                              <li>
                                <b-form-checkbox
                                  id="checkbox-11"
                                  name="checkbox-11"
                                  value="accepted"
                                  unchecked-value="not_accepted"
                                  >{{
                                    $t("chat.tabs.contacts.list.P.name")
                                  }}</b-form-checkbox
                                >
                              </li>
                            </ul>
                          </div>

                          <div>
                            <div class="p-3 font-weight-bold text-primary">
                              {{ $t("chat.tabs.contacts.list.R.text") }}
                            </div>

                            <ul class="list-unstyled contact-list">
                              <li>
                                <b-form-checkbox
                                  id="checkbox-12"
                                  name="checkbox-12"
                                  value="accepted"
                                  unchecked-value="not_accepted"
                                  >{{
                                    $t("chat.tabs.contacts.list.R.name")
                                  }}</b-form-checkbox
                                >
                              </li>
                            </ul>
                          </div>

                          <div>
                            <div class="p-3 font-weight-bold text-primary">
                              {{ $t("chat.tabs.contacts.list.S.text") }}
                            </div>

                            <ul class="list-unstyled contact-list">
                              <li>
                                <b-form-checkbox
                                  id="checkbox-13"
                                  name="checkbox-13"
                                  value="accepted"
                                  unchecked-value="not_accepted"
                                  >{{
                                    $t("chat.tabs.contacts.list.S.name")
                                  }}</b-form-checkbox
                                >
                              </li>
                            </ul>
                          </div>
                        </div>
                      </div>
                    </simplebar>
                  </div>
                </div>
              </b-collapse>
            </div>
            <div class="form-group">
              <label for="addgroupdescription-input">{{
                $t("chat.tabs.groups.modal.form.description.label")
              }}</label>
              <textarea
                class="form-control"
                id="addgroupdescription-input"
                rows="3"
                :placeholder="
                  $t('chat.tabs.groups.modal.form.description.placeholder')
                "
              ></textarea>
            </div>
            <div class="text-right pt-5 mt-3">
              <button
                type="button"
                class="btn btn-link"
                @click="showModal = false"
              >
                {{ $t("chat.tabs.groups.modal.form.button.close") }}
              </button>
              <button type="submit" class="btn btn-primary">
                {{ $t("chat.tabs.groups.modal.form.button.create") }}
              </button>
            </div>
          </form>
        </div>
      </b-modal>

      <!-- End add group Modal -->

      <div class="search-box chat-search-box">
        <div class="input-group bg-light input-group-lg rounded-lg">
          <div class="input-group-prepend">
            <button
              class="btn btn-link text-decoration-none text-muted pr-1"
              type="button"
            >
              <i class="ri-search-line search-icon font-size-18"></i>
            </button>
          </div>
          <input
            type="text"
            class="form-control bg-light"
            placeholder="Search groups..."
          />
        </div>
      </div>
      <!-- end search-box -->
    </div>

    <!-- Start chat-group-list -->
    <div class="p-4 chat-message-list chat-group-list">
      <simplebar data-simplebar>
        <ul class="list-unstyled chat-list">
          <li v-for="(item, index) in groups" :key="index">
            <a href="javascript:void(0);">
              <div class="media align-items-center">
                <div class="chat-user-img mr-3">
                  <div class="avatar-xs">
                    <span
                      class="avatar-title rounded-circle bg-soft-primary text-primary"
                      >{{ $t(item.name).charAt(0) }}</span
                    >
                  </div>
                </div>
                <div class="media-body overflow-hidden">
                  <h5 class="text-truncate font-size-14 mb-0">
                    #{{ $t(item.name) }}
                  </h5>
                </div>
              </div>
            </a>
          </li>
        </ul>
      </simplebar>
    </div>
    <!-- End chat-group-list -->
  </div>
</template>
