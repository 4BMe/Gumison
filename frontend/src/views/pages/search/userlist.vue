<template>
  <div>
    <!-- Start chat-message-list -->
    <div class="px-2">
      <!-- <h5 class="mb-3 px-3 font-size-16">
        {{ $t("chat.tabs.chats.recent.title") }}
      </h5> -->
      <simplebar class="chat-message-list" id="chat-list" ref="current">
        <ul class="list-unstyled chat-list chat-user-list" v-if="userList.length == 0">
          <li>
            <a href="javascript:void(0);">
              <div class="media">
                <div class="media-body overflow-hidden">
                  <p class="chat-user-message text-truncate mb-0 pt-">
                    "{{ word }}"을/를 찾을 수 없습니다.
                  </p>
                </div>
              </div>
            </a>
            <div class="media">
              <div class="chat-user-img online align-self-center mr-3">
              </div>
            </div>
          </li>
        </ul>
        <ul class="list-unstyled chat-list chat-user-list" v-else>
          <li
            v-for="(item, index) in userList"
            :key="index"
            :class="{ active: selectedUserId === item.nickname }"
            @click="searchHistory(item)"
          >
            <a href="javascript:void(0);">
              <div class="media">
                <div class="chat-user-img online align-self-center mr-3">
                  <div v-if="item.profile">
                    <img
                      :src="item.profile"
                      class="rounded-circle avatar-xs"
                      alt
                    />
                  </div>
                  <div class="avatar-xs" v-if="!item.profile">
                    <span
                      class="avatar-title rounded-circle bg-soft-primary text-primary"
                      >{{ item.nickname.charAt(0) }}
                    </span>
                  </div>
                </div>

                <div class="media-body overflow-hidden">
                  <h5 class="text-truncate font-size-15 mb-1">
                    <img
                      :src="require(`@/assets/images/tier/`+item.tier+`.png`)"
                      alt="user-img"
                      class="img-fluid rounded-circle sm-tier-img"
                    /> 
                    {{ item.nickname }}
                  </h5>
                  <table  style="width:100%">
                    <tbody>
                      <tr>
                        <td style="width:30%">
                          <p class="chat-user-message text-truncate mb-0">
                            {{ item.tier }}
                          </p>
                        </td>
                        <td>
                          <p class="chat-user-message text-truncate mb-0">
                            <b>{{ item.solCnt }}</b> 문제 성공
                          </p>
                        </td>
                      </tr>
                    </tbody>
                  </table>  
                </div>
              </div>
            </a>
          </li>
        </ul>
      </simplebar>
      <!-- End chat-message-list -->
    </div>
  </div>
</template>

<script>
import simplebar from "simplebar-vue";
// import { required } from "vuelidate/lib/validators";

export default {
  components: {
    simplebar,
  },
  props: {
    userList: { type: Array, required: true },
    word: { type: String, required: true },
  },
  data() {
    return {
      selectedUserId: "",
    };
  },
  watch: {
    room(val) {
      if (val) this.selectedUserId = val.id;
    },
  },
  methods: {
    // eslint-disable-next-line no-unused-vars
    searchHistory(user) {
      console.log(user);
    },
  },
};
</script>

<style lang="scss" scoped>
.VueCarousel-slide > div {
  padding-left: 5px;
  padding-right: 5px;
}
</style>

<style>
.sm-tier-img {
  width: 7%;
  height: 7%;
}
</style>
