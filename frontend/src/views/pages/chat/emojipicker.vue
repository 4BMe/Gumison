<script>
import EmojiPicker from "vue-emoji-picker";

/**
 * Emoji picker component
 */
export default {
  components: {
    EmojiPicker,
  },
  props: [
    "emojiOpened",
    "emojiReaction",
    "roomFooterRef",
    "positionTop",
    "positionRight",
  ],
  data() {
    return {
      search: "",
      emojiPickerHeight: 320,
      emojiPickerTop: 0,
      emojiPickerRight: "",
    };
  },
  methods: {
    append({ emoji, emojiName }) {
      this.$emit("addEmoji", { icon: emoji, name: emojiName });
    },
    openEmoji(ev) {
      this.$emit("openEmoji", true);
      this.setEmojiPickerPosition(
        ev.clientY,
        ev.view.innerWidth,
        ev.view.innerHeight
      );
    },
    setEmojiPickerPosition(clientY, innerWidth, innerHeight) {
      setTimeout(() => {
        const mobileSize = innerWidth < 500 || innerHeight < 700;
        if (!this.roomFooterRef) {
          if (mobileSize) this.emojiPickerRight = "0px";
          return;
        }
        if (mobileSize) {
          this.emojiPickerRight = innerWidth / 2 - 120 + "px";
          this.emojiPickerTop = 100;
          this.emojiPickerHeight = innerHeight - 200;
        } else {
          const roomFooterTop = this.roomFooterRef.getBoundingClientRect().top;
          const pickerTopPosition =
            roomFooterTop - clientY > this.emojiPickerHeight - 50;
          if (pickerTopPosition) this.emojiPickerTop = clientY + 10;
          else this.emojiPickerTop = clientY - this.emojiPickerHeight - 10;
          this.emojiPickerRight = this.positionTop
            ? "-50px"
            : this.positionRight
            ? "60px"
            : "";
        }
      }, 0);
    },
  },
};
</script>

<template>
  <div>
    <emoji-picker @emoji="append" :search="search">
      <div
        class="svg-button"
        :class="{ 'button-reaction': emojiReaction }"
        slot="emoji-invoker"
        slot-scope="{ events: { click: clickEvent } }"
        @click.stop="clickEvent"
        @click="openEmoji"
      >
        <button
          type="button"
          class="emoji-btn text-primary"
          data-toggle="tooltip"
          data-placement="top"
          title
          data-original-title="Emoji"
          :param="emojiReaction ? 'reaction' : ''"
        >
          <i class="ri-emotion-happy-line"></i>
        </button>
      </div>
      <div slot="emoji-picker" slot-scope="{ emojis, insert }" v-if="emojiOpened">
        <transition name="slide-up" appear>
          <div
            class="emoji-picker"
            :class="{ 'picker-reaction': emojiReaction }"
            :style="{
							height: `${emojiPickerHeight}px`,
							top: positionTop ? emojiPickerHeight : `${emojiPickerTop}px`,
							right: emojiPickerRight,
							display: emojiPickerTop || !emojiReaction ? 'initial' : 'none'
						}"
          >
            <div class="emoji-picker__search">
              <input type="text" v-model="search" class="form-control"/>
            </div>
            <div>
              <div v-for="(emojiGroup, category) in emojis" :key="category">
                <h5 v-if="category !== 'Frequently used'">{{ category }}</h5>
                <div class="emojis" v-if="category !== 'Frequently used'">
                  <span
                    v-for="(emoji, emojiName) in emojiGroup"
                    :key="emojiName"
                    @click="insert({ emoji, emojiName })"
                    :title="emojiName"
                  >{{ emoji }}</span>
                </div>
              </div>
            </div>
          </div>
        </transition>
      </div>
    </emoji-picker>
  </div>
</template>


<style lang="scss" scoped>

.emoji-picker {
  position: absolute;
  z-index: 9999;
  bottom: 80px;
  right: 25px;
  width: 240px;
  overflow: scroll;
  overflow-x: hidden;
  padding: 16px;
  box-sizing: border-box;
  border-radius: 0.5rem;
  background: white;
  box-shadow: 0 0 3px rgba(60, 72, 88, 0.15);
}
.picker-reaction {
  position: fixed;
  top: initial;
  right: initial;
}
.emoji-picker__search {
  display: flex;
}
.emoji-picker h5 {
  margin: 15px 0 8px;
  color: #b1b1b1;
  text-transform: uppercase;
  font-size: 0.8rem;
  cursor: default;
}
.emoji-picker .emojis {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}
.emoji-picker .emojis:after {
  content: "";
  flex: auto;
}
.emoji-picker .emojis span {
  padding: 0.2rem;
  cursor: pointer;
  border-radius: 5px;
}
.emoji-picker .emojis span:hover {
  background: var(--chat-sidemenu-bg-color-hover);
  cursor: pointer;
}
.button-reaction svg {
  height: 19px;
  width: 19px;
}
.emoji-btn {
  background-color: transparent;
  color: #7a7f9a;
  border: none;
  margin-top: -5px;
}
.icon-textarea .emoji-btn {
  font-size: 18px;
  padding: 0.47rem 0.75rem;
  margin: 0;
}
</style>