<script>
import vClickOutside from "v-click-outside";
import EmojiPicker from "./emojipicker";
import FormatMessage from "./format-message";

/**
 * Message component
 */
export default {
  components: {
    EmojiPicker,
    FormatMessage,
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
    message: {
      type: Object,
      required: true,
    },
    messages: {
      type: Array,
      required: true,
    },
    editedMessage: {
      type: Object,
      required: true,
    },
    roomUsers: {
      type: Array,
      default: () => [],
    },
    messageActions: {
      type: Array,
      required: true,
    },
    roomFooterRef: {
      type: HTMLDivElement,
    },
    showReactionEmojis: {
      type: Boolean,
      required: true,
    },
    textFormatting: {
      type: Boolean,
      required: true,
    },
    emojisList: {
      type: Object,
      required: true,
    },
    hideOptions: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      hoverMessageId: null,
      imageLoading: false,
      imageHover: false,
      messageHover: false,
      optionsOpened: false,
      optionsClosing: false,
      menuOptionsTop: 0,
      messageReaction: [],
      newMessage: {},
      emojiOpened: false,
      imageResponsive: "",
    };
  },
  watch: {
    message: {
      immediate: true,
      handler() {
        this.checkImgLoad();
      },
    },
    emojiOpened(val) {
      if (val) this.optionsOpened = false;
    },
    hideOptions(val) {
      if (val) {
        this.closeEmoji();
        this.closeOptions();
      }
    },
  },
  mounted() {
    if (!this.message.seen && this.message.sender_id !== this.currentUserId) {
      this.$emit("addNewMessage", {
        _id: this.message._id,
        index: this.index,
      });
    }
    this.imageResponsive = {
      maxHeight: this.$refs.imageRef.clientWidth - 18,
      loaderTop: this.$refs.imageRef.clientWidth / 2,
    };
  },
  computed: {
    showDate() {
      return (
        this.index > 0 &&
        this.message.date !== this.messages[this.index - 1].date
      );
    },
    messageOffset() {
      return (
        this.index > 0 &&
        this.message.sender_id !== this.messages[this.index - 1].sender_id
      );
    },
    isImage() {
      return this.checkImageFile();
    },
    isImageReply() {
      return this.checkImageReplyFile();
    },
    isImageLoading() {
      return (
        this.message.file.localUrl.indexOf("blob:http") !== -1 ||
        this.imageLoading
      );
    },
    replyUsername() {
      const { sender_id } = this.message.replyMessage;
      const replyUser = this.roomUsers.find((user) => user._id === sender_id);
      return replyUser ? replyUser.username : "";
    },
    isMessageActions() {
      return (
        this.filteredMessageActions.length &&
        this.messageHover &&
        !this.message.deleted &&
        !this.message.disable_actions
      );
    },
    isMessageReactions() {
      return (
        this.showReactionEmojis &&
        this.messageHover &&
        !this.message.deleted &&
        !this.message.disable_reactions
      );
    },
    filteredMessageActions() {
      return this.message.sender_id === this.currentUserId
        ? this.messageActions
        : this.messageActions.filter((message) => !message.onlyMe);
    },
  },
  methods: {
    /**
     * Message hover
     */
    isMessageHover() {
      return (
        this.editedMessage._id === this.message._id ||
        this.hoverMessageId === this.message._id
      );
    },
    /**
     * Message hover
     */
    onHoverMessage() {
      this.imageHover = true;
      this.messageHover = true;
      if (this.canEditMessage()) this.hoverMessageId = this.message._id;
    },
    /**
     * Edit message check
     */
    canEditMessage() {
      return !this.message.deleted;
    },
    onLeaveMessage() {
      this.imageHover = false;
      if (!this.optionsOpened && !this.emojiOpened) this.messageHover = false;
      this.hoverMessageId = null;
    },
    openFile() {
      this.$emit("openFile", this.message);
    },
    /**
     * Message action hanlder
     */
    messageActionHandler(action) {
      this.closeOptions();
      this.messageHover = false;
      this.hoverMessageId = null;
      setTimeout(() => {
        this.$emit("messageActionHandler", {
          action,
          message: this.message,
        });
      }, 300);
    },
    checkImageFile() {
      return this.checkImageType(this.message.file);
    },
    checkImageReplyFile() {
      return this.checkImageType(this.message.replyMessage.file);
    },
    checkImageType(file) {
      if (!file) return;
      const imageTypes = ["png", "jpg", "jpeg", "svg"];
      const { type } = file;
      return imageTypes.some((t) => type.includes(t));
    },
    checkImgLoad() {
      if (!this.checkImageFile()) return;
      this.imageLoading = true;
      const image = new Image();
      image.src = this.message.file.url;
      image.addEventListener("load", () => (this.imageLoading = false));
    },
    closeOptions() {
      this.optionsOpened = false;
      this.optionsClosing = true;
      setTimeout(() => (this.optionsClosing = false), 100);
      if (this.hoverMessageId !== this.message._id) this.messageHover = false;
    },
    openEmoji() {
      this.emojiOpened = !this.emojiOpened;
      this.$emit("hideOptions", false);
    },
    closeEmoji() {
      this.emojiOpened = false;
      if (this.hoverMessageId !== this.message._id) this.messageHover = false;
    },
    getEmojiByName(emojiName) {
      return this.emojisList[emojiName];
    },
    sendMessageReaction(emoji, reaction) {
      if (typeof this.message["reaction"] === "undefined") {
        this.message["reaction"] = [];
      }
      this.message["reaction"].push(emoji);
      this.$emit("sendMessageReaction", {
        messageId: this.message.id,
        reaction: this.message["reaction"],
        remove: reaction && reaction.indexOf(this.currentUserId) !== -1,
      });
      this.closeEmoji();
      this.messageHover = false;
    },
  },
};
</script>

<template>
  <ul
    class="list-unstyled mb-0"
    v-if="message.content !== '' || message.file !== null"
  >
    <li :class="{ right: message.align === 'right' }">
      <div
        class="conversation-list"
        ref="imageRef"
        @mouseover="onHoverMessage(message)"
        @mouseleave="onLeaveMessage"
        :class="{
          'message-highlight': isMessageHover(message),
        }"
      >
        <div class="chat-avatar">
          <img :src="message.image" alt />
        </div>
        <div class="user-chat-content">
          <div class="ctext-wrap">
            <div class="ctext-wrap-content">
              <div
                v-if="
                  roomUsers.length > 2 && message.sender_id !== currentUserId
                "
                class="text-username"
                :class="{
                  'username-reply': !message.deleted && message.replyMessage,
                }"
              >
                <span>yu{{ message.username }}</span>
              </div>

              <div
                class="reply-message bg-white"
                v-if="!message.deleted && message.replyMessage"
              >
                <div class="reply-username">{{ replyUsername }}</div>

                <div class="image-reply-container" v-if="isImageReply">
                  <div
                    class="message-image message-image-reply"
                    :style="{
                      'background-image': `url('${message.replyMessage.file.localUrl}')`,
                    }"
                  ></div>
                </div>
                <div class="reply-content">
                  {{ message.replyMessage.content }}
                </div>
              </div>
              <div v-if="message.deleted">
                <i class="icon-deleted ri-chat-delete-fill"></i>
                <span>{{ textMessages.MESSAGE_DELETED }}</span>
              </div>
              <div v-else-if="!message.file">
                <format-message
                  :content="this.message.content"
                  :textFormatting="textFormatting"
                ></format-message>
              </div>

              <div class="image-container" v-else-if="isImage">
                <div
                  class="message-image"
                  :class="{
                    'image-loading':
                      isImageLoading && message.sender_id === currentUserId,
                  }"
                  :style="{
                    'background-image': `url('${message.file.localUrl}')`,
                    'max-height': `${imageResponsive.maxHeight}px`,
                  }"
                >
                  <transition name="fade-image">
                    <div
                      class="image-buttons"
                      v-if="imageHover && !isImageLoading"
                    >
                      <div
                        class="svg-button button-view"
                        @click.stop="openFile(message.file)"
                      >
                        <i class="ri-eye-2-fill"></i>
                      </div>
                      <div
                        class="svg-button button-download"
                        @click.stop="openFile(message.file)"
                      >
                        <i class="ri-file-add-fill"></i>
                      </div>
                    </div>
                  </transition>
                </div>
                <format-message
                  :content="message.content"
                  :textFormatting="textFormatting"
                ></format-message>
              </div>

              <div v-else class="file-message">
                <div class="card p-2 mb-2">
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
                        <h5 class="font-size-14 mb-1">{{ message.content }}</h5>
                        <p class="text-muted font-size-13 mb-0">
                          {{ message.file.size }}
                        </p>
                      </div>
                    </div>

                    <div class="ml-4">
                      <ul class="list-inline mb-0 font-size-20">
                        <li class="list-inline-item">
                          <a href="javascript:void(0);" class="text-muted">
                            <i class="ri-download-2-line"></i>
                          </a>
                        </li>
                        <li class="list-inline-item dropdown">
                          <a
                            class="dropdown-toggle text-muted"
                            href="javascript:void(0);"
                            role="button"
                            data-toggle="dropdown"
                            aria-haspopup="true"
                            aria-expanded="false"
                          >
                            <i class="ri-more-fill"></i>
                          </a>
                          <div class="dropdown-menu dropdown-menu-right">
                            <a class="dropdown-item" href="javascript:void(0);">
                              Share
                              <i
                                class="ri-share-line float-right text-muted"
                              ></i>
                            </a>
                            <a class="dropdown-item" href="javascript:void(0);">
                              Delete
                              <i
                                class="ri-delete-bin-line float-right text-muted"
                              ></i>
                            </a>
                          </div>
                        </li>
                      </ul>
                    </div>
                  </div>
                </div>
              </div>

              <p class="chat-time mb-0">
                <i class="ri-time-line align-middle"></i>
                <span class="align-middle">{{ message.time }}</span>
              </p>
            </div>
            <div class="text-timestamp">
              <div
                class="icon-edited"
                v-if="message.edited && !message.deleted"
              >
                <i class="ri-pencil-fill"></i>
              </div>
              <span>{{ message.timestamp }}</span>
            </div>

            <b-dropdown variant="white" class="align-self-start">
              <template v-slot:button-content>
                <i class="ri-more-2-fill"></i>
              </template>

              <b-dropdown-item
                v-for="action in messageActions"
                :key="action.name"
                @click="messageActionHandler(action)"
                >{{ action.name }}</b-dropdown-item
              >
            </b-dropdown>
            <div
              class="options-container"
              :class="{ 'options-image': isImage && !message.replyMessage }"
              :style="{
                width:
                  filteredMessageActions.length && showReactionEmojis
                    ? '70px'
                    : '45px',
              }"
            >
              <transition-group name="slide-left">
                <div
                  key="1"
                  class="blur-container"
                  :class="{
                    'options-me': message.sender_id === currentUserId,
                  }"
                  v-if="isMessageActions || isMessageReactions"
                ></div>
                {{ isMessageReactions }}
                <emoji-picker
                  key="3"
                  class="message-reactions"
                  :style="{ right: isMessageActions ? '30px' : '5px' }"
                  v-if="isMessageReactions"
                  v-click-outside="closeEmoji"
                  :emojiOpened="emojiOpened"
                  :emojiReaction="true"
                  :roomFooterRef="roomFooterRef"
                  :positionRight="message.sender_id === currentUserId"
                  @addEmoji="sendMessageReaction"
                  @openEmoji="openEmoji"
                ></emoji-picker>
              </transition-group>
            </div>
          </div>
          <div class="conversation-name">{{ message.name }}</div>
          <transition-group v-if="!message.deleted">
            <button
              v-for="(reaction, key) in message.reaction"
              :key="key + 0"
              class="button-reaction"
              
            >
              {{ reaction.icon }}
            </button>
          </transition-group>
        </div>
      </div>

      <transition
        :name="
          message.sender_id === currentUserId ? 'slide-left' : 'slide-right'
        "
        v-if="filteredMessageActions.length"
      >
        <div
          ref="menuOptions"
          v-if="optionsOpened"
          v-click-outside="closeOptions"
          class="menu-options"
          :class="{ 'menu-left': message.sender_id !== currentUserId }"
          :style="{ top: `${menuOptionsTop}px` }"
        >
          <div class="menu-list">
            <div v-for="action in messageActions" :key="action.name">
              <div class="menu-item" @click="messageActionHandler(action)">
                {{ action.title }}
              </div>
            </div>
          </div>
        </div>
      </transition>
    </li>
  </ul>
</template>

<style lang="scss" scoped>
.image-container {
  width: 250px;
  max-width: 100%;
}

.image-reply-container {
  width: 70px;
}

.message-image {
  position: relative;
  background-color: var(--chat-message-bg-color-image) !important;
  background-size: cover !important;
  background-position: center center !important;
  background-repeat: no-repeat !important;
  height: 250px;
  width: 250px;
  max-width: 100%;
  border-radius: 4px;
  margin: 4px auto 5px;
  transition: 0.4s filter linear;
}

.message-image-reply {
  height: 70px;
  width: 70px;
  margin: 4px auto 3px;
}

.image-loading {
  filter: blur(3px);
}

.reply-message {
  background: var(--chat-message-bg-color-reply);
  border-radius: 4px;
  margin: -1px -5px 8px;
  padding: 8px 10px;

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
}

.text-username {
  font-size: 13px;
  color: var(--chat-message-color-username);
  margin-bottom: 2px;
}

.username-reply {
  margin-bottom: 5px;
}

.text-timestamp {
  font-size: 10px;
  color: var(--chat-message-color-timestamp);
  text-align: right;
}

.file-message {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  margin-top: 3px;

  span {
    max-width: 100%;
  }

  .icon-file svg {
    margin-right: 5px;
  }
}

.right {
  .options-container {
    right: -40px;
    .wrapper.message-reactions {
      right: -10px;
    }
  }
}
.options-me {
  background: var(--chat-message-bg-color-me);
}

.options-image .blur-container {
  background: rgba(255, 255, 255, 0.6);
  border-bottom-left-radius: 15px;
}

.image-buttons {
  position: absolute;
  width: 100%;
  height: 100%;
  border-radius: 4px;
  background: linear-gradient(
    to bottom,
    rgba(0, 0, 0, 0) 55%,
    rgba(0, 0, 0, 0.02) 60%,
    rgba(0, 0, 0, 0.05) 65%,
    rgba(0, 0, 0, 0.1) 70%,
    rgba(0, 0, 0, 0.2) 75%,
    rgba(0, 0, 0, 0.3) 80%,
    rgba(0, 0, 0, 0.5) 85%,
    rgba(0, 0, 0, 0.6) 90%,
    rgba(0, 0, 0, 0.7) 95%,
    rgba(0, 0, 0, 0.8) 100%
  );

  svg {
    height: 26px;
    width: 26px;
  }

  .button-view,
  .button-download {
    position: absolute;
    bottom: 6px;
    left: 7px;
  }

  :first-child {
    left: 40px;
  }

  .button-view {
    max-width: 18px;
    bottom: 8px;
  }
}

.message-reactions {
  position: relative;
  top: 10px !important;
  right: 0 !important;
}

.menu-options {
  right: 15px;
}

.menu-left {
  right: -118px;
}

.button-reaction {
  display: inline-flex;
  align-items: center;
  border: var(--chat-message-border-style-reaction);
  outline: none;
  background: var(--chat-message-bg-color-reaction);
  border-radius: 4px;
  margin: 4px 2px 0;
  transition: 0.3s;
  padding: 0;
  font-size: 18px;
  line-height: 23px;

  span {
    font-size: 11px;
    font-weight: 500;
    min-width: 7px;
    color: var(--chat-message-color-reaction-counter);
  }

  &:hover {
    border: var(--chat-message-border-style-reaction-hover);
    background: var(--chat-message-bg-color-reaction-hover);
    cursor: pointer;
  }
}

.btn:focus,
.btn.focus {
  box-shadow: none !important;
}

@media only screen and (max-width: 768px) {
  .options-container {
    right: 3px;
  }

  .menu-left {
    right: -50px;
  }
}
</style>
