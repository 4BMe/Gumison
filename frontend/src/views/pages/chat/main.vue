<script>
import simplebar from "simplebar-vue";

import vClickOutside from "v-click-outside";
import emojis from "vue-emoji-picker/src/emojis";

import Message from "./message";
import EmojiPicker from "./emojipicker";
import { chatMemberList, chatMessages } from "./data";

const { detectMobile } = require("../../utils/mobileDetection");

export default {
  components: {
    simplebar,
    Message,
    EmojiPicker,
  },
  directives: {
    clickOutside: vClickOutside.directive,
  },
  props: {
    currentUserId: {
      type: [String, Number],
      required: true,
    },
    textMessages: {
      type: Object,
      required: true,
    },
    isMobile: {
      type: Boolean,
      required: true,
    },
    rooms: {
      type: Array,
      required: true,
    },
    id: {
      type: [String, Number],
      required: true,
    },
    messages: {
      type: Array,
      required: true,
    },
    roomMessage: {
      type: String,
    },
    menuActions: {
      type: Array,
      required: true,
    },
    messageActions: {
      type: Array,
      required: true,
    },
    showFiles: {
      type: Boolean,
      required: true,
    },
    showEmojis: {
      type: Boolean,
      required: true,
    },
    showReactionEmojis: {
      type: Boolean,
      required: true,
    },
    textFormatting: {
      type: Boolean,
      required: true,
    },
    loadingRooms: {
      type: Boolean,
      required: true,
    },
    roomInfo: {
      type: Function,
    },
  },
  data() {
    return {
      chatMemberList: chatMemberList,
      chatMessages: chatMessages,
      message: "",
      editedMessage: {},
      messageReply: null,
      infiniteState: null,
      loadingMessages: false,
      loadingMoreMessages: false,
      file: null,
      imageFile: null,
      imageDimensions: null,
      menuOpened: false,
      emojiOpened: false,
      emojisList: {},
      hideOptions: true,
      scrollIcon: false,
      newMessages: [],
    };
  },
  mounted() {
    this.newMessages = [];
    window.addEventListener("keyup", (e) => {
      if (e.keyCode === 13 && !e.shiftKey) {
        if (detectMobile()) {
          this.message = this.message + "\n";
          setTimeout(() => this.onChangeInput(), 0);
        } else {
          this.sendMessage();
        }
      }
    });
    const emojisTable = Object.keys(emojis).map((key) => emojis[key]);
    this.emojisList = Object.assign({}, ...emojisTable);
  },
  watch: {
    loadingMessages(val) {
      if (val) this.infiniteState = null;
      else this.focusTextarea(true);
    },
    room(newVal, oldVal) {
      if (newVal.id && newVal.id !== oldVal.id) {
        this.loadingMessages = true;
        this.scrollIcon = false;
        this.resetMessage(true);
        if (this.roomMessage) {
          this.message = this.roomMessage;
          setTimeout(() => this.onChangeInput(), 0);
        }
      }
    },
    messages(newVal, oldVal) {
      const element = this.$refs.scrollContainer;
      if (!element) return;
      const options = {
        top: element.scrollHeight,
      };
      if (oldVal && newVal && oldVal.length === newVal.length - 1) {
        return setTimeout(() => {
          options.behavior = "smooth";
          element.scrollTo(options);
        }, 50);
      }
      if (this.infiniteState) {
        this.infiniteState.loaded();
        setTimeout(() => (this.loadingMoreMessages = false), 0);
      } else if (newVal.length) {
        setTimeout(() => {
          element.scrollTo(options);
          this.loadingMessages = false;
        }, 0);
      }
    },
    imageFile() {
      setTimeout(() => {
        if (!this.$refs.imageFile) {
          this.imageDimensions = null;
          setTimeout(() => this.resizeTextarea(), 0);
        } else {
          let height = this.$refs.imageFile.height;
          if (height < 30) height = 30;
          this.imageDimensions = {
            height: this.$refs.imageFile.height - 10,
            width: this.$refs.imageFile.width + 26,
          };
        }
      }, 20);
    },
  },
  computed: {
    room() {
      return this.chatMemberList.find((room) => room.id === this.id) || {};
    },
    inputDisabled() {
      return this.isMessageEmpty();
    },
  },
  methods: {
    addNewMessage(message) {
      this.newMessages.push(message);
      this.handleScroll();
    },
    handleScroll() {
      if (this.$refs.current.$el) {
        setTimeout(() => {
          this.$refs.current.SimpleBar.getScrollElement().scrollTop =
            this.$refs.current.SimpleBar.getScrollElement().scrollHeight + 1500;
        }, 500);
      }
    },
    resetMessage(disableMobileFocus = null, editFile = null) {
      this.$emit("typingMessage", null);
      if (editFile) {
        this.file = null;
        this.message = "";
        return;
      }
      this.resetTextareaSize();
      this.message = "";
      this.editedMessage = {};
      this.messageReply = null;
      this.file = null;
      this.imageFile = null;
      this.emojiOpened = false;
      setTimeout(() => this.focusTextarea(disableMobileFocus), 0);
    },
    resetImageFile() {
      this.imageFile = null;
      this.editedMessage.file = null;
      this.file = null;
      this.focusTextarea();
    },
    resetTextareaSize() {
      if (!this.$refs["roomTextarea"]) return;
      this.$refs["roomTextarea"].style.height = "20px";
    },
    focusTextarea(disableMobileFocus) {
      if (detectMobile() && disableMobileFocus) return;
      this.$refs["roomTextarea"].focus();
    },
    isMessageEmpty() {
      return !this.file && !this.message.trim();
    },
    sendMessage() {
      if (!this.file && !this.message.trim()) return;
      if (this.editedMessage.id) {
        if (this.editedMessage.content !== this.message || this.file) {
          this.$emit("editMessage", {
            messageId: this.editedMessage.id,
            newContent: this.message.trim(),
            file: this.file,
            replyMessage: this.messageReply,
          });
        }
      } else {
        const currentDate = new Date();
        this.$emit("sendMessage", {
          id: this.chatMessages.length + 1,
          content: this.message.trim(),
          file: this.file,
          replyMessage: this.messageReply,
          time: currentDate.getHours() + ":" + currentDate.getMinutes(),
          align: "right",
          image: require("@/assets/images/users/avatar-1.jpg"),
          name: "Patricia Smith",
        });
      }
      this.resetMessage(true);
    },
    messageActionHandler({ action, message }) {
      switch (action.name) {
        case "Reply":
          return this.replyMessage(message);
        case "Edit":
          return this.editMessage(message);
        case "Delete":
          return this.$emit("deleteMessage", message.id);
        default:
          return this.$emit("messageActionHandler", {
            action,
            message,
          });
      }
    },
    sendMessageReaction(messageReaction) {
      this.$emit("sendMessageReaction", messageReaction);
    },
    /**
     * Reply message
     */
    replyMessage(message) {
      this.resetMessage();
      this.messageReply = message;
    },
    /**
     * Edit message
     */
    editMessage(message) {
      this.resetMessage();
      this.editedMessage = {
        ...message,
      };
      this.file = message.file;
      if (this.isImageCheck(this.file)) this.imageFile = message.file.url;
      this.message = message.content;
      setTimeout(() => this.resizeTextarea(), 0);
    },
    scrollToBottom() {
      const element = this.$refs.scrollContainer;
      element.scrollTo({
        top: element.scrollHeight,
        behavior: "smooth",
      });
    },
    onChangeInput() {
      this.resizeTextarea();
      this.$emit("typingMessage", this.message);
    },
    /**
     * Resize textarea
     */
    resizeTextarea() {
      const el = this.$refs["roomTextarea"];
      if (!el) return;
      const padding = window
        .getComputedStyle(el, null)
        .getPropertyValue("padding-top")
        .replace("px", "");
      el.style.height = 0;
      el.style.height = el.scrollHeight - padding * 2 + "px";
    },
    /**
     * Add emoji
     */
    addEmoji(emoji) {
      this.message += emoji.icon;
      this.focusTextarea(true);
    },
    launchFilePicker() {
      this.$refs.file.value = "";
      this.$refs.file.click();
    },
    /**
     * On file changes
     */
    async onFileChange(files) {
      this.resetImageFile();
      const file = files[0];
      const fileURL = URL.createObjectURL(file);
      const blobFile = await fetch(fileURL).then((res) => res.blob());
      this.file = {
        blob: blobFile,
        name: file.name.split(".")[0],
        size: file.size,
        type: file.name.split(".")[1] || file.type,
        localUrl: fileURL,
      };
      if (this.isImageCheck(this.file)) this.imageFile = fileURL;
      else this.message = file.name;
    },
    isImageCheck(file) {
      if (!file) return;
      const imageTypes = ["png", "jpg", "jpeg", "svg"];
      const { type } = file;
      return imageTypes.some((t) => type.includes(t));
    },
    openFile(message) {
      this.$emit("openFile", message);
    },
    menuActionHandler(action) {
      this.closeMenu();
      this.$emit("menuActionHandler", action);
    },
    closeMenu() {
      this.menuOpened = false;
    },
    toggleProfile() {
      document.getElementById("profile-show").style.display = "block";
    },
    closeUserChat() {
      var userChat = document.getElementsByClassName("user-chat");
      if (userChat) {
        userChat[0].classList.remove("user-chat-show");
      }
    },
    hideProfile() {
      document.getElementById("profile-show").style.display = "none";
    },
  },
};
</script>

<template>
  <div class="user-chat w-100 overflow-hidden">
    <div class="d-lg-flex">
      <div class="w-100 overflow-hidden position-relative">
        <div class="p-3 p-lg-4 border-bottom">
          <div class="row align-items-center">
            <div class="col-sm-4 col-8">
              <div class="media align-items-center">
                <div class="d-block d-lg-none mr-2">
                  <a
                    href="javascript: void(0);"
                    @click="closeUserChat()"
                    class="user-chat-remove text-muted font-size-16 p-2"
                  >
                    <i class="ri-arrow-left-s-line"></i>
                  </a>
                </div>
                <div class="mr-3">
                  <img
                    :src="
                      room.profile
                        ? room.profile
                        : require('@/assets/images/users/avatar-4.jpg')
                    "
                    class="rounded-circle avatar-xs"
                    alt
                  />
                </div>
                <div class="media-body overflow-hidden">
                  <h5 class="font-size-16 mb-0 text-truncate">
                    <a href="javascript:void(0);" class="text-reset user-profile-show">{{
                      $t(room.name) ? $t(room.name) : "Doris Brown"
                    }}</a>
                    <i
                      class="ri-record-circle-fill font-size-10 text-success d-inline-block ml-1"
                    ></i>
                  </h5>
                </div>
              </div>
            </div>
            <div class="col-sm-8 col-4">
              <ul class="list-inline user-chat-nav text-right mb-0">
                <li class="list-inline-item">
                  <b-dropdown
                    toggle-class="nav-btn"
                    variant="white"
                    menu-class="dropdown-menu-md p-0"
                    right
                  >
                    <template v-slot:button-content>
                      <i class="ri-search-line"></i>
                    </template>
                    <div class="search-box p-2">
                      <input
                        type="text"
                        class="form-control bg-light border-0"
                        placeholder="Search.."
                      />
                    </div>
                  </b-dropdown>
                </li>
                <li class="list-inline-item d-none d-lg-inline-block">
                  <button
                    type="button"
                    class="btn nav-btn"
                    v-b-modal.audiocallModal
                  >
                    <i class="ri-phone-line"></i>
                  </button>
                </li>
                <li class="list-inline-item d-none d-lg-inline-block">
                  <button
                    type="button"
                    class="btn nav-btn"
                    @click="$bvModal.show('audiocallModal')"
                  >
                    <i class="ri-vidicon-line"></i>
                  </button>
                </li>
                <!-- audiocall Modal -->
                <b-modal id="audiocallModal" centered hide-header hide-footer>
                  <div class="text-center p-4">
                    <div class="avatar-lg mx-auto mb-4">
                      <img
                        src="../../../assets/images/users/avatar-4.jpg"
                        alt=""
                        class="img-thumbnail rounded-circle"
                      />
                    </div>

                    <h5 class="text-truncate">Doris Brown</h5>
                    <p class="text-muted">Start Audio Call</p>

                    <div class="mt-5">
                      <ul class="list-inline mb-1">
                        <li class="list-inline-item px-2">
                          <button
                            type="button"
                            class="btn btn-danger avatar-sm rounded-circle"
                            data-dismiss="modal"
                            @click="$bvModal.hide('audiocallModal')"
                          >
                            <span
                              class="avatar-title bg-transparent font-size-20"
                            >
                              <i class="ri-close-fill"></i>
                            </span>
                          </button>
                        </li>
                        <li class="list-inline-item px-2">
                          <button
                            type="button"
                            class="btn btn-success avatar-sm rounded-circle"
                          >
                            <span
                              class="avatar-title bg-transparent font-size-20"
                            >
                              <i class="ri-phone-fill"></i>
                            </span>
                          </button>
                        </li>
                      </ul>
                    </div>
                  </div>
                </b-modal>
                <!-- audiocall Modal -->

                <!-- videocall Modal -->
                <b-modal
                  class="modal fade"
                  id="videocallModal"
                  centered
                  hide-header
                  hide-footer
                >
                  <div class="text-center p-4">
                    <div class="avatar-lg mx-auto mb-4">
                      <img
                        src="../../../assets/images/users/avatar-4.jpg"
                        alt=""
                        class="img-thumbnail rounded-circle"
                      />
                    </div>

                    <h5 class="text-truncate">Doris Brown</h5>
                    <p class="text-muted mb-0">Start Video Call</p>

                    <div class="mt-5">
                      <ul class="list-inline mb-1">
                        <li class="list-inline-item px-2">
                          <button
                            type="button"
                            class="btn btn-danger avatar-sm rounded-circle"
                            data-dismiss="modal"
                          >
                            <span
                              class="avatar-title bg-transparent font-size-20"
                            >
                              <i class="ri-close-fill"></i>
                            </span>
                          </button>
                        </li>
                        <li class="list-inline-item px-2">
                          <button
                            type="button"
                            class="btn btn-success avatar-sm rounded-circle"
                          >
                            <span
                              class="avatar-title bg-transparent font-size-20"
                            >
                              <i class="ri-vidicon-fill"></i>
                            </span>
                          </button>
                        </li>
                      </ul>
                    </div>
                  </div>
                </b-modal>
                <!-- end modal -->

                <li class="list-inline-item d-none d-lg-inline-block">
                  <button
                    type="button"
                    class="btn nav-btn user-profile-show"
                    @click="toggleProfile()"
                  >
                    <i class="ri-user-2-line"></i>
                  </button>
                </li>

                <li class="list-inline-item">
                  <b-dropdown toggle-class="nav-btn" variant="white" right>
                    <template v-slot:button-content>
                      <i class="ri-more-fill"></i>
                    </template>
                    <b-dropdown-item
                      class="d-block d-lg-none user-profile-show"
                      href="javascript:void(0);"
                    >
                      View profile
                      <i class="ri-user-2-line float-right text-muted"></i>
                    </b-dropdown-item>
                    <b-dropdown-item href="javascript:void(0);">
                      Archive
                      <i class="ri-archive-line float-right text-muted"></i>
                    </b-dropdown-item>
                    <b-dropdown-item href="javascript:void(0);">
                      Muted
                      <i class="ri-volume-mute-line float-right text-muted"></i>
                    </b-dropdown-item>
                    <b-dropdown-item href="javascript:void(0);">
                      Delete
                      <i class="ri-delete-bin-line float-right text-muted"></i>
                    </b-dropdown-item>
                  </b-dropdown>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <simplebar
          class="chat-conversation p-3 p-lg-4"
          id="containerElement"
          ref="current"
        >
          <div v-for="(message, index) in messages" :key="index">
            <message
              :currentUserId="currentUserId"
              :message="message"
              :messages="chatMessages"
              :editedMessage="editedMessage"
              :messageActions="messageActions"
              :roomUsers="room.users"
              :textMessages="textMessages"
              :roomFooterRef="$refs.roomFooter"
              :newMessages="newMessages"
              :showReactionEmojis="showReactionEmojis"
              :textFormatting="textFormatting"
              :emojisList="emojisList"
              :hideOptions="hideOptions"
              @messageActionHandler="messageActionHandler"
              @openFile="openFile"
              @addNewMessage="addNewMessage"
              @sendMessageReaction="sendMessageReaction"
              @hideOptions="hideOptions = $event"
            ></message>
          </div>
        </simplebar>

        <div ref="roomFooter" class="room-footer">
          <transition name="slide-up">
            <div v-if="messageReply" class="reply-container">
              <div class="reply-box bg-light">
                <img
                  v-if="isImageCheck(messageReply.file)"
                  :src="messageReply.file.localUrl"
                  class="image-reply"
                />
                <div class="reply-info">
                  <div class="reply-username">{{ messageReply.username }}</div>
                  <div class="reply-content">{{ messageReply.content }}</div>
                </div>
              </div>

              <div class="icon-reply">
                <div class="svg-button" @click="resetMessage">
                  <i class="ri-close-line"></i>
                </div>
              </div>
            </div>
          </transition>

          <div class="box-footer chat-input-section p-3 p-lg-4 border-top mb-0">
            <div class="image-container" v-if="imageFile">
              <div class="svg-button icon-image" @click="resetImageFile">
                <i class="ri-close-circle-fill"></i>
              </div>
              <div class="image-file">
                <img ref="imageFile" :src="imageFile" />
              </div>
            </div>

            <div
              v-else-if="file"
              class="file-container bg-light"
              :class="{ 'file-container-edit': editedMessage._id }"
            >
              <div class="icon-file">
                <i class="ri-file-download-line"></i>
              </div>
              <div class="file-message">{{ message }}</div>
              <div
                class="svg-button icon-remove"
                @click="resetMessage(null, true)"
              >
                <i class="ri-close-line"></i>
              </div>
            </div>

            <textarea
              v-show="!file || imageFile"
              ref="roomTextarea"
              :placeholder="textMessages.TYPE_MESSAGE"
              :style="{
                'min-height': `${
                  imageDimensions ? imageDimensions.height : 20
                }px`,
                'padding-left': `${
                  imageDimensions ? imageDimensions.width - 10 : 12
                }px`,
              }"
              class="form-control form-control-lg bg-light border-light rounded"
              v-model="message"
              @input="onChangeInput"
              @keydown.esc="resetMessage"
              @keydown.enter.exact.prevent
            ></textarea>

            <div class="icon-textarea">
              <div
                class="svg-button"
                v-if="editedMessage._id"
                @click="resetMessage"
              >
                <!-- <svg-icon name="close-outline" /> -->
              </div>

              <emoji-picker
                v-if="showEmojis && (!file || imageFile)"
                :emojiOpened="emojiOpened"
                :positionTop="true"
                @addEmoji="addEmoji"
                @openEmoji="emojiOpened = $event"
              ></emoji-picker>

              <div
                v-if="showFiles"
                class="svg-button"
                @click="launchFilePicker"
              >
                <button class="btn btn-link text-decoration-none font-size-16">
                  <i class="ri-attachment-line"></i>
                </button>
              </div>

              <input
                v-if="showFiles"
                type="file"
                ref="file"
                @change="onFileChange($event.target.files)"
                style="display: none"
              />
              <div>
                <button
                  type="submit"
                  class="btn btn-primary font-size-16 btn-lg chat-send"
                  @click="sendMessage"
                  :class="{ 'send-disabled': inputDisabled }"
                >
                  <i
                    class="ri-send-plane-2-fill"
                    :param="inputDisabled ? 'disabled' : ''"
                  ></i>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="user-profile-sidebar" id="profile-show">
        <div class="px-3 px-lg-4 pt-3 pt-lg-4">
          <div class="user-chat-nav text-right">
            <button
              type="button"
              class="btn nav-btn"
              id="user-profile-hide"
              @click="hideProfile"
            >
              <i class="ri-close-line"></i>
            </button>
          </div>
        </div>
        <div class="text-center p-4 border-bottom">
          <div class="mb-4">
            <img
              src="@/assets/images/users/avatar-4.jpg"
              class="rounded-circle avatar-lg img-thumbnail"
              alt
            />
          </div>
          <h5 class="font-size-16 mb-1 text-truncate">
            {{ $t("chat.rightpanel.name") }}
          </h5>
          <p class="text-muted text-truncate mb-1">
            <i class="ri-record-circle-fill font-size-10 text-success mr-1"></i>
            {{ $t("chat.rightpanel.status") }}
          </p>
        </div>
        <!-- End profile user -->

        <!-- Start user-profile-desc -->
        <div class="p-4 user-profile-desc" data-simplebar>
          <div class="text-muted">
            <p class="mb-4">
              If several languages coalesce, the grammar of the resulting
              language is more simple and regular than that of the individual.
            </p>
          </div>

          <div id="profile-user-accordion" class="custom-accordion">
            <b-card no-body class="border custom-accordion">
              <b-card-header>
                <a href="javascript: void(0);" v-b-toggle.profileaccordion-1>
                  <h5 class="font-size-14 m-0">
                    <i
                      class="ri-user-2-line mr-2 align-middle d-inline-block"
                    ></i>
                    {{ $t("chat.tabs.profile.about.text") }}
                    <i
                      class="mdi mdi-chevron-up float-right accor-plus-icon"
                    ></i>
                  </h5>
                </a>
              </b-card-header>
              <b-collapse id="profileaccordion-1" visible accordion="accordion">
                <b-card-body>
                  <div>
                    <p class="text-muted mb-1">
                      {{ $t("chat.tabs.profile.about.name.text") }}
                    </p>
                    <h5 class="font-size-14">
                      {{ $t("chat.rightpanel.name") }}
                    </h5>
                  </div>

                  <div class="mt-4">
                    <p class="text-muted mb-1">
                      {{ $t("chat.tabs.profile.about.email.text") }}
                    </p>
                    <h5 class="font-size-14">
                      {{ $t("chat.tabs.profile.about.email.value") }}
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
                      {{ $t("chat.tabs.profile.about.location.value") }}
                    </h5>
                  </div>
                </b-card-body>
              </b-collapse>
            </b-card>

            <b-card no-body class="border custom-accordion">
              <b-card-header>
                <a href="javascript: void(0);" v-b-toggle.profileaccordion-2>
                  <h5 class="font-size-14 m-0">
                    <i
                      class="ri-attachment-line mr-2 align-middle d-inline-block"
                    ></i>
                    {{ $t("chat.tabs.profile.attachedfile.text") }}
                    <i
                      class="mdi mdi-chevron-up float-right accor-plus-icon"
                    ></i>
                  </h5>
                </a>
              </b-card-header>
              <b-collapse id="profileaccordion-2" accordion="accordion">
                <b-card-body>
                  <div class="card p-2 border mb-2">
                    <div class="media align-items-center">
                      <div class="avatar-sm mr-3">
                        <div
                          class="avatar-title bg-soft-primary text-primary rounded font-size-20"
                        >
                          <i class="ri-file-text-fill"></i>
                        </div>
                      </div>
                      <div class="media-body">
                        <div class="text-left">
                          <h5 class="font-size-14 mb-1">
                            {{
                              $t("chat.tabs.profile.attachedfile.file1.name")
                            }}
                          </h5>
                          <p class="text-muted font-size-13 mb-0">
                            {{
                              $t("chat.tabs.profile.attachedfile.file1.size")
                            }}
                          </p>
                        </div>
                      </div>

                      <div class="ml-4">
                        <ul class="list-inline mb-0 font-size-18">
                          <li class="list-inline-item">
                            <a href="javascript:void(0);" class="text-muted px-1">
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
                            <a class="dropdown-item" href="javascript:void(0);">{{
                              $t(
                                "chat.tabs.profile.attachedfile.dropdown.action"
                              )
                            }}</a>
                            <a class="dropdown-item" href="javascript:void(0);">{{
                              $t(
                                "chat.tabs.profile.attachedfile.dropdown.another"
                              )
                            }}</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="javascript:void(0);">{{
                              $t(
                                "chat.tabs.profile.attachedfile.dropdown.delete"
                              )
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
                        <div
                          class="avatar-title bg-soft-primary text-primary rounded font-size-20"
                        >
                          <i class="ri-image-fill"></i>
                        </div>
                      </div>
                      <div class="media-body">
                        <div class="text-left">
                          <h5 class="font-size-14 mb-1">
                            {{
                              $t("chat.tabs.profile.attachedfile.file2.name")
                            }}
                          </h5>
                          <p class="text-muted font-size-13 mb-0">
                            {{
                              $t("chat.tabs.profile.attachedfile.file2.size")
                            }}
                          </p>
                        </div>
                      </div>

                      <div class="ml-4">
                        <ul class="list-inline mb-0 font-size-18">
                          <li class="list-inline-item">
                            <a href="javascript:void(0);" class="text-muted px-1">
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
                            <a class="dropdown-item" href="javascript:void(0);">{{
                              $t(
                                "chat.tabs.profile.attachedfile.dropdown.action"
                              )
                            }}</a>
                            <a class="dropdown-item" href="javascript:void(0);">{{
                              $t(
                                "chat.tabs.profile.attachedfile.dropdown.another"
                              )
                            }}</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="javascript:void(0);">{{
                              $t(
                                "chat.tabs.profile.attachedfile.dropdown.delete"
                              )
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
                        <div
                          class="avatar-title bg-soft-primary text-primary rounded font-size-20"
                        >
                          <i class="ri-image-fill"></i>
                        </div>
                      </div>
                      <div class="media-body">
                        <div class="text-left">
                          <h5 class="font-size-14 mb-1">
                            {{
                              $t("chat.tabs.profile.attachedfile.file3.name")
                            }}
                          </h5>
                          <p class="text-muted font-size-13 mb-0">
                            {{
                              $t("chat.tabs.profile.attachedfile.file3.size")
                            }}
                          </p>
                        </div>
                      </div>

                      <div class="ml-4">
                        <ul class="list-inline mb-0 font-size-18">
                          <li class="list-inline-item">
                            <a href="javascript:void(0);" class="text-muted px-1">
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
                            <a class="dropdown-item" href="javascript:void(0);">{{
                              $t(
                                "chat.tabs.profile.attachedfile.dropdown.action"
                              )
                            }}</a>
                            <a class="dropdown-item" href="javascript:void(0);">{{
                              $t(
                                "chat.tabs.profile.attachedfile.dropdown.another"
                              )
                            }}</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="javascript:void(0);">{{
                              $t(
                                "chat.tabs.profile.attachedfile.dropdown.delete"
                              )
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
                        <div
                          class="avatar-title bg-soft-primary text-primary rounded font-size-20"
                        >
                          <i class="ri-file-text-fill"></i>
                        </div>
                      </div>
                      <div class="media-body">
                        <div class="text-left">
                          <h5 class="font-size-14 mb-1">
                            {{
                              $t("chat.tabs.profile.attachedfile.file4.name")
                            }}
                          </h5>
                          <p class="text-muted font-size-13 mb-0">
                            {{
                              $t("chat.tabs.profile.attachedfile.file4.size")
                            }}
                          </p>
                        </div>
                      </div>

                      <div class="ml-4">
                        <ul class="list-inline mb-0 font-size-18">
                          <li class="list-inline-item">
                            <a href="javascript:void(0);" class="text-muted px-1">
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
                            <a class="dropdown-item" href="javascript:void(0);">{{
                              $t(
                                "chat.tabs.profile.attachedfile.dropdown.action"
                              )
                            }}</a>
                            <a class="dropdown-item" href="javascript:void(0);">{{
                              $t(
                                "chat.tabs.profile.attachedfile.dropdown.another"
                              )
                            }}</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="javascript:void(0);">{{
                              $t(
                                "chat.tabs.profile.attachedfile.dropdown.delete"
                              )
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

            <!-- End Attached Files card -->
          </div>
          <!-- end profile-user-accordion -->
        </div>
        <!-- end user-profile-desc -->
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.messages-container {
  padding: 0 5px 5px;
}

.room-footer {
  width: calc(100% - 1px);
  border-bottom-right-radius: 4px;
  z-index: 10;
}

.box-footer {
  display: flex;
  position: relative;
  background: var(--chat-footer-bg-color);
  padding: 10px 8px 10px;
}

.reply-container {
  display: flex;
  padding: 10px 10px 0 10px;
  background: var(--chat-content-bg-color);
  align-items: center;
  max-width: 100%;

  .reply-box {
    width: 100%;
    overflow: hidden;
    background: var(--chat-footer-bg-color-reply);
    border-radius: 4px;
    padding: 8px 10px;
    display: flex;
  }

  .reply-info {
    overflow: hidden;
  }

  .reply-username {
    color: var(--chat-message-color-reply-username);
    font-size: 12px;
    line-height: 15px;
    margin-bottom: 2px;
  }

  .reply-content {
    font-size: 12px;
    color: var(--chat-message-color-reply-content);
  }

  .icon-reply {
    margin-left: 10px;

    svg {
      height: 20px;
      width: 20px;
    }
  }

  .image-reply {
    max-height: 100px;
    margin-right: 10px;
  }
}

textarea {
  height: 20px;
  width: 100%;
  line-height: 20px;
  overflow: hidden;
  outline: 0;
  resize: none;
  border-radius: 20px;
  padding: 12px 16px;
  box-sizing: content-box;
  font-size: 16px;
  background: var(--chat-bg-color-input);
  color: var(--chat-color);
  caret-color: var(--chat-color-caret);
  border: var(--chat-border-style-input);

  &::placeholder {
    color: var(--chat-color-placeholder);
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
}

.icon-textarea {
  display: flex;
  margin: 0px 0 0 5px;

  svg,
  .wrapper {
    margin: 0 7px;
  }
}

.image-container {
  position: absolute;
  max-width: 25%;
  left: 16px;
  top: 18px;
}

.image-file {
  display: flex;
  justify-content: center;
  flex-direction: column;
  min-height: 30px;

  img {
    border-radius: 7px;
    width: 100%;
    max-width: 150px;
    max-height: 100%;
    margin: 12px 0 0 12px;
  }
}

.icon-image {
  position: absolute;
  z-index: 10;
}

.file-container {
  display: flex;
  align-items: center;
  width: calc(100% - 75px);
  height: 20px;
  padding: 12px 0;
  box-sizing: content-box;
  background: var(--chat-bg-color-input);
  border: var(--chat-border-style-input);
  border-radius: 20px;
}

.file-container-edit {
  width: calc(100% - 109px);
}

.file-message {
  max-width: calc(100% - 75px);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.icon-file {
  display: flex;
  margin: 0 8px 0 15px;
}

.icon-remove {
  margin-left: 8px;

  svg {
    height: 18px;
    width: 18px;
  }
}

.send-disabled,
.send-disabled svg {
  cursor: none !important;
  pointer-events: none !important;
  transform: none !important;
}

.messages-hidden {
  opacity: 0;
}

@media only screen and (max-width: 768px) {
  .box-footer {
    border-top: var(--chat-border-style-input);
    padding: 7px 2px 7px 7px;
  }

  textarea {
    padding: 7px;
    line-height: 18px;

    &::placeholder {
      color: transparent;
    }
  }

  .icon-textarea {
    margin: 6px 0 0 5px;

    svg,
    .wrapper {
      margin: 0 5px;
    }
  }

  .image-container {
    top: 10px;
    left: 10px;
  }

  .image-file img {
    transform: scale(0.97);
  }

  .room-footer {
    width: 100%;
  }

  .file-container {
    padding: 7px 0;

    .icon-file {
      margin-left: 10px;
    }
  }

  .reply-container {
    padding: 5px 8px;
  }
}
</style>
