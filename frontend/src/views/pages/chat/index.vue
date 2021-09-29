<template>
  <div>
    <div class="layout-wrapper d-lg-flex">
      <!-- start chat-leftsidebar -->
      <div class="chat-leftsidebar mr-lg-1">
        <div class="tab-content">
          <!-- Start Profile tab-pane -->
          <div
            class="tab-pane"
            id="pills-user"
            role="tabpanel"
            aria-labelledby="pills-user-tab"
            v-if="activetab === 1"
            v-bind:class="[activetab === 1 ? 'active' : '']"
          >
            <!-- Start profile content -->
            <Profile />
            <!-- End profile content -->
          </div>

          <!-- Start chats tab-pane -->
          <div
            class="tab-pane fade show active"
            id="pills-chat"
            role="tabpanel"
            aria-labelledby="pills-chat-tab"
            v-if="activetab === 2"
            v-bind:class="[activetab === 2 ? 'active' : '']"
          >
            <!-- Start chats content -->

            <RoomsList
              v-if="!singleRoom"
              :currentUserId="currentUserId"
              :rooms="orderedRooms"
              :loadingRooms="loadingRooms"
              :room="room"
              :textMessages="t"
              :showAddRoom="showAddRoom"
              :showRoomsList="showRoomsList"
              :textFormatting="textFormatting"
              :isMobile="isMobile"
              @fetchRoom="fetchRoom"
            />
          </div>
          <!-- End chats tab-pane -->

          <!-- Start groups tab-pane -->
          <div
            class="tab-pane"
            id="pills-groups"
            role="tabpanel"
            aria-labelledby="pills-groups-tab"
            v-if="activetab === 3"
            v-bind:class="[activetab === 3 ? 'active' : '']"
          >
            <!-- Start Groups content -->
            <Groups />
            <!-- End Groups content -->
          </div>
          <!-- End groups tab-pane -->

          <!-- Start contacts tab-pane -->
          <div
            class="tab-pane"
            id="pills-contacts"
            role="tabpanel"
            aria-labelledby="pills-contacts-tab"
            v-if="activetab === 4"
            v-bind:class="[activetab === 4 ? 'active' : '']"
          >
            <!-- Start Contact content -->
            <Contacts />
            <!-- Start Contact content -->
          </div>
          <!-- End contacts tab-pane -->

          <!-- Start settings tab-pane -->
          <div
            class="tab-pane"
            id="pills-setting"
            role="tabpanel"
            aria-labelledby="pills-setting-tab"
            v-if="activetab === 5"
            v-bind:class="[activetab === 5 ? 'active' : '']"
          >
            <!-- Start Settings content -->
            <Setting />
            <!-- Start Settings content -->
          </div>
          <!-- End settings tab-pane -->

          <!-- Start search tab-pane -->
          <div
            class="tab-pane"
            id="pills-setting"
            role="tabpanel"
            aria-labelledby="pills-setting-tab"
            v-if="activetab === 8"
            v-bind:class="[activetab === 8 ? 'active' : '']"
          >
            <!-- Start search content -->
            <Search />
            <!-- End search content -->
          </div>
         <!-- End search tab-pane -->


           <!-- Start 사용자 페이지, 로그인 페이지 tab-pane -->
          <div
            class="tab-pane"
            id="pills-user"
            role="tabpanel"
            aria-labelledby="pills-user-tab"
            v-if="activetab === 9"
            v-bind:class="[activetab === 9 ? 'active' : '']"
          >
            <!-- Start profile content -->
            <Profile />
           
          </div>
           <!-- End profile content -->
        </div>
      </div>

      <Room
        ref="chatContainer"
        :currentUserId="currentUserId"
        :rooms="rooms"
        :id="room.id || ''"
        :messages="chatMessages"
        :roomMessage="roomMessage"
        :messagesLoaded="messagesLoaded"
        :menuActions="menuActions"
        :messageActions="messageActions"
        :showFiles="showFiles"
        :showEmojis="showEmojis"
        :showReactionEmojis="showReactionEmojis"
        :textMessages="t"
        :singleRoom="singleRoom"
        :showRoomsList="showRoomsList"
        :textFormatting="textFormatting"
        :isMobile="isMobile"
        :loadingRooms="loadingRooms"
        :roomInfo="$listeners.roomInfo"
        @roomInfo="roomInfo"
        @fetchMessages="fetchMessages"
        @sendMessage="sendMessage"
        @editMessage="editMessage"
        @deleteMessage="deleteMessage"
        @openFile="openFile"
        @menuActionHandler="menuActionHandler"
        @messageActionHandler="messageActionHandler"
        @sendMessageReaction="sendMessageReaction"
        @typingMessage="typingMessage"
      />
    </div>
  </div>
</template>

<script>
import Room from "./main";
import locales from "../locale";
import Setting from "./tabs/setting";
import Contacts from "./tabs/contacts";
import Profile from "./tabs/profile";
import RoomsList from "./tabs/userlist";
import Groups from "./tabs/groups";
import Search from "@/views/pages/search/search";

import { chatMemberList, chatMessages } from "./data";

export default {
  components: {
    Profile,
    Setting,
    Groups,
    Room,
    RoomsList,
    Contacts,
    Search
  },
  props: {
    height: {
      type: String,
      default: "600px",
    },
    theme: {
      type: String,
      default: "light",
    },
    styles: {
      type: Object,
      default: () => ({}),
    },
    responsiveBreakpoint: {
      type: Number,
      default: 900,
    },
    singleRoom: {
      type: Boolean,
      default: false,
    },
    textMessages: {
      type: Object,
      default: null,
    },
    currentUserId: {
      type: [String, Number],
      default: "",
    },
    rooms: {
      type: Array,
      default: () => [],
    },
    loadingRooms: {
      type: Boolean,
      default: false,
    },
    id: {
      type: [String, Number],
      default: null,
    },
    messages: {
      type: Array,
      default: () => [],
    },
    messagesLoaded: {
      type: Boolean,
      default: false,
    },
    menuActions: {
      type: Array,
      default: () => [],
    },
    messageActions: {
      type: Array,
      default: () => [
        {
          name: "Reply",
          title: "Reply",
        },
        {
          name: "Edit",
          title: "Edit Message",
          onlyMe: true,
        },
        {
          name: "Delete",
          title: "Delete Message",
          onlyMe: true,
        },
      ],
    },
    showAddRoom: {
      type: Boolean,
      default: true,
    },
    showFiles: {
      type: Boolean,
      default: true,
    },
    showEmojis: {
      type: Boolean,
      default: true,
    },
    showReactionEmojis: {
      type: Boolean,
      default: true,
    },
    textFormatting: {
      type: Boolean,
      default: true,
    },
    newMessage: {
      type: Object,
      default: null,
    },
    roomMessage: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      chatMemberList: chatMemberList,
      chatMessages: chatMessages,
      message: "",
      input: "",
      search: "",
      activetab: 2,
      emojiPickerHeight: 320,
      emojiPickerTop: 0,
      editedMessage: {},
      emojiPickerRight: "",
      emojiOpened: false,
      emojisList: {},
      imageDimensions: null,
      file: null,
      room: {},
      showRoomsList: true,
      isMobile: false,
    };
  },
  watch: {
    rooms(newVal, oldVal) {
      if (newVal[0] && newVal.length !== oldVal.length) {
        if (this.id) {
          const room = newVal.find((r) => r.id === this.id);
          this.fetchRoom({
            room,
          });
        } else if (!this.isMobile) {
          this.fetchRoom({
            room: this.orderedRooms[0],
          });
        } else {
          this.showRoomsList = true;
        }
      }
    },
    id: {
      immediate: true,
      handler(val) {
        if (val && !this.loadingRooms && this.rooms.length) {
          const room = this.rooms.find((r) => r.id === val);
          this.fetchRoom({
            room,
          });
        }
      },
    },
    room(val) {
      if (!val) return;
      if (Object.entries(val).length === 0) return;
    },
    newMessage(val) {
      this.$set(this.messages, val.index, val.message);
    },
  },
  mounted() {
    this.updateResponsive();
    window.addEventListener("resize", (ev) => {
      if (ev.isTrusted) this.updateResponsive();
    });
  },
  computed: {
    t() {
      return {
        ...locales,
        ...this.textMessages,
      };
    },
    orderedRooms() {
      return this.rooms.slice().sort((a, b) => {
        const aVal = a.lastMessage || {
          date: 0,
        };
        const bVal = b.lastMessage || {
          date: 0,
        };
        return aVal.date > bVal.date ? -1 : bVal.date > aVal.date ? 1 : 0;
      });
    },
  },
  methods: {
    updateResponsive() {
      this.isMobile = window.innerWidth < this.responsiveBreakpoint;
    },
    fetchRoom({ room }) {
      this.room = room;
      this.fetchMessages({
        reset: true,
      });
      if (this.isMobile) this.showRoomsList = false;
    },
    roomInfo() {
      this.$emit("roomInfo", this.room);
    },
    fetchMessages(options) {
      this.$emit("fetchMessages", {
        room: this.room,
        options,
      });
    },
    sendMessage(message) {
      this.$emit("sendMessage", {
        ...message,
        id: this.room.id,
      });
      this.chatMessages.push(message);
    },
    editMessage(message) {
      this.$emit("editMessage", {
        message,
        id: this.room.id,
      });
      let msgInfo = message;
      let data = this.chatMessages.find((x) => x.id === msgInfo.messageId);
      data.content = msgInfo.newContent;
    },
    deleteMessage(messageId) {
      this.$emit("deleteMessage", {
        messageId,
        id: this.room.id,
      });

      let data = this.chatMessages.find((x) => x.id === messageId);
      data.content = "";
      data.file = null;
      this.deleted = true;
    },
    openFile(message) {
      this.$emit("openFile", message);
    },
    menuActionHandler(ev) {
      this.$emit("menuActionHandler", {
        action: ev,
        id: this.room.id,
      });
    },
    messageActionHandler(ev) {
      this.$emit("messageActionHandler", {
        ...ev,
        id: this.room.id,
      });
    },
    sendMessageReaction(messageReaction) {
      this.$emit("sendMessageReaction", {
        ...messageReaction,
        id: this.room.id,
      });
    },
    typingMessage(message) {
      this.$emit("typingMessage", {
        message,
        id: this.room.id,
      });
    },
  },
};
</script>


<style lang="scss" scoped>
</style>
