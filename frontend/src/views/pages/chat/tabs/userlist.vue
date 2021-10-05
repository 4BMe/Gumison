<script>
import simplebar from "simplebar-vue";
import { Carousel, Slide } from "vue-carousel";
import { required } from "vuelidate/lib/validators";

import { chatMemberList } from "../data";

import filteredUsers from "../../../utils/filterItems";

export default {
  components: {
    simplebar,
    Carousel,
    Slide,
  },
  props: {
    currentUserId: { type: [String, Number], required: true },
    textMessages: { type: Object, required: true },
    showAddRoom: { type: Boolean, required: true },
    textFormatting: { type: Boolean, required: true },
    rooms: { type: Array, required: true },
    loadingRooms: { type: Boolean, required: true },
    room: { type: Object, required: true },
  },
  data() {
    return {
      chatMemberList: chatMemberList,
      filteredRooms: chatMemberList || [],
      selectedRoomId: "",
      submitted: false,
      form: {
        name: "",
      },
      showModal: false,
    };
  },
  mounted() {
    var container1 = document.querySelector(
      "#chat-list .simplebar-content-wrapper"
    );
    container1.scrollTo({ top: 500, behavior: "smooth" });
  },
  watch: {
    rooms(val) {
      this.filteredRooms = val;
    },
    room(val) {
      if (val) this.selectedRoomId = val.id;
    },
  },
  validations: {
    form: {
      name: { required },
    },
  },
  methods: {
    // eslint-disable-next-line no-unused-vars
    searchRoom(ev) {
      this.filteredRooms = filteredUsers(
        this.chatMemberList,
        "name",
        ev.target.value
      );
    },
    openRoom(room) {
      this.selectedRoomId = room.id;
      this.$emit("fetchRoom", { room });
      var userChat = document.getElementsByClassName("user-chat");
      if (userChat) {
        userChat[0].classList.add("user-chat-show");
      }
    },
    save() {
      this.submitted = true;
      // stop here if form is invalid
      this.$v.$touch();
      if (this.$v.$invalid) {
        return;
      } else {
        const name = this.form.name;
        const currentDate = new Date();
        this.chatMemberList.push({
          id: this.chatMemberList.length + 1,
          align: "right",
          name,
          message: "",
          time: currentDate.getHours() + ":" + currentDate.getMinutes(),
        });
        this.handleScroll();
        this.showModal = false;
      }
      this.submitted = false;
      this.form = {};
    },
    handleScroll() {
      if (this.$refs.current.$el) {
        setTimeout(() => {
          this.$refs.current.SimpleBar.getScrollElement().scrollTop =
            this.$refs.current.SimpleBar.getScrollElement().scrollHeight + 1500;
        }, 500);
      }
    },
  },
};
</script>

<template>
  <div>
    <div class="px-4 pt-4">
      <h4 class="mb-4">{{ $t("chat.tabs.chats.title") }}</h4>
      <div class="search-box chat-search-box">
        <div class="input-group mb-3 bg-light input-group-lg rounded-lg">
          <div class="input-group-prepend">
            <button
              class="btn btn-link text-muted pr-1 text-decoration-none"
              type="button"
            >
              <i class="ri-search-line search-icon font-size-18"></i>
            </button>
          </div>
          <input
            type="text"
            class="form-control bg-light"
            :placeholder="$t('chat.tabs.chats.search.placeholder')"
            @input="searchRoom"
          />
          <b-button
            class="text-decoration-none text-muted font-size-18 py-0"
            variant="link"
            @click="showModal = true"
            v-b-tooltip.hover
            title="Create Chat"
          >
            <i class="ri-user-add-line"></i>
          </b-button>
          <b-modal
            v-model="showModal"
            :title="$t('chat.tabs.chats.modal.title')"
            hide-footer
          >
            <form @submit.prevent="save">
              <div class="form-group mb-4">
                <label for="addcontactName-input">{{
                  $t("chat.tabs.chats.modal.form.name.label")
                }}</label>
                <input
                  type="text"
                  class="form-control"
                  id="addcontactName-input"
                  :placeholder="
                    $t('chat.tabs.chats.modal.form.name.placeholder')
                  "
                  v-model="form.name"
                  :class="{ 'is-invalid': submitted && $v.form.name.$error }"
                />
                <div
                  v-if="submitted && $v.form.name.$error"
                  class="invalid-feedback"
                >
                  <span v-if="!$v.form.name.required">{{
                    $t("chat.tabs.chats.modal.form.name.validation.required")
                  }}</span>
                </div>
              </div>

              <div class="text-right pt-5 mt-3">
                <b-button variant="link" @click="showModal = false">{{
                  $t("chat.tabs.chats.modal.form.buttons.close")
                }}</b-button>
                <b-button type="submit" variant="primary" class="ml-1">{{
                  $t("chat.tabs.chats.modal.form.buttons.save")
                }}</b-button>
              </div>
            </form>
          </b-modal>
        </div>
      </div>
      <!-- Search Box-->
    </div>
    <!-- .p-4 -->

    <!-- Start user status -->
    <div class="px-4 pb-4" dir="ltr">
      <carousel
        :per-page="4"
        :mouse-drag="true"
        :pagination-enabled="false"
        class="owl-carousel"
        :space-padding="5"
      >
        <slide class="owl-item">
          <div class="item">
            <a href="javascript:void(0);" class="user-status-box">
              <div class="avatar-xs mx-auto d-block chat-user-img online">
                <img
                  src="@/assets/images/users/avatar-2.jpg"
                  alt="user-img"
                  class="img-fluid rounded-circle"
                />
                <span class="user-status"></span>
              </div>

              <h5 class="font-size-13 text-truncate mt-3 mb-1">
                {{ $t("chat.tabs.chats.users.1") }}
              </h5>
            </a>
          </div>
        </slide>
        <slide>
          <div class="item">
            <a href="javascript:void(0);" class="user-status-box">
              <div class="avatar-xs mx-auto d-block chat-user-img online">
                <img
                  src="@/assets/images/users/avatar-4.jpg"
                  alt="user-img"
                  class="img-fluid rounded-circle"
                />
                <span class="user-status"></span>
              </div>

              <h5 class="font-size-13 text-truncate mt-3 mb-1">
                {{ $t("chat.tabs.chats.users.2") }}
              </h5>
            </a>
          </div>
        </slide>
        <slide>
          <div class="item">
            <a href="javascript:void(0);" class="user-status-box">
              <div class="avatar-xs mx-auto d-block chat-user-img online">
                <img
                  src="@/assets/images/users/avatar-5.jpg"
                  alt="user-img"
                  class="img-fluid rounded-circle"
                />
                <span class="user-status"></span>
              </div>
              <h5 class="font-size-13 text-truncate mt-3 mb-1">
                {{ $t("chat.tabs.chats.users.3") }}
              </h5>
            </a>
          </div>
        </slide>
        <slide>
          <div class="item">
            <a href="javascript:void(0);" class="user-status-box">
              <div class="avatar-xs mx-auto d-block chat-user-img online">
                <img
                  src="@/assets/images/users/avatar-6.jpg"
                  alt="user-img"
                  class="img-fluid rounded-circle"
                />
                <span class="user-status"></span>
              </div>

              <h5 class="font-size-13 text-truncate mt-3 mb-1">
                {{ $t("chat.tabs.chats.users.4") }}
              </h5>
            </a>
          </div>
        </slide>
        <slide>
          <div class="item">
            <a href="javascript:void(0);" class="user-status-box">
              <div class="avatar-xs mx-auto d-block chat-user-img online">
                <span
                  class="avatar-title rounded-circle bg-soft-primary text-primary"
                  >T</span
                >
                <span class="user-status"></span>
              </div>
              <h5 class="font-size-13 text-truncate mt-3 mb-1">
                {{ $t("chat.tabs.chats.users.5") }}
              </h5>
            </a>
          </div>
        </slide>
      </carousel>

      <!-- end user status carousel -->
    </div>
    <!-- end user status -->

    <!-- Start chat-message-list -->
    <div class="px-2">
      <h5 class="mb-3 px-3 font-size-16">
        {{ $t("chat.tabs.chats.recent.title") }}
      </h5>
      <simplebar class="chat-message-list" id="chat-list" ref="current">
        <ul class="list-unstyled chat-list chat-user-list">
          <li
            v-for="(item, index) in filteredRooms"
            :key="index"
            :class="{ active: selectedRoomId === item.id }"
            @click="openRoom(item)"
          >
            <a href="javascript:void(0);">
              <div class="media">
                <div
                  class="chat-user-img online align-self-center mr-3"
                  :class="{
                    online: item.status === 'online',
                    away: item.status === 'away',
                  }"
                >
                  <div v-if="item.profile">
                    <img
                      :src="item.profile"
                      class="rounded-circle avatar-xs"
                      alt
                    />
                    <span class="user-status"></span>
                  </div>
                  <div class="avatar-xs" v-if="!item.profile">
                    <span
                      class="avatar-title rounded-circle bg-soft-primary text-primary"
                      >{{ $t(item.name).charAt(0) }}</span
                    >
                    <span class="user-status" v-if="item.status"></span>
                  </div>
                </div>

                <div class="media-body overflow-hidden">
                  <h5 class="text-truncate font-size-15 mb-1">
                    {{ $t(item.name) }}
                  </h5>
                  <p class="chat-user-message text-truncate mb-0">
                    {{ $t(item.message) }}
                  </p>
                </div>
                <div class="font-size-11">{{ $t(item.time) }}</div>
              </div>
            </a>
          </li>
        </ul>
      </simplebar>
      <!-- End chat-message-list -->
    </div>
  </div>
</template>

<style lang="scss" scoped>
.VueCarousel-slide > div {
  padding-left: 5px;
  padding-right: 5px;
}
</style>
