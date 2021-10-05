<template>
  <div>
    <div class="px-4 pt-4">
      <h4 class="mb-3">My History</h4>
    </div>
    <div v-if="isData">
      <UserInfo :user=user />
    </div>
    <div class="">
      <div
        class="chat-message-list"
        id="chat-list"
        ref="current"
      >
        <ul
          class="list-unstyled chat-list chat-user-list"
          v-if="isData&&solutionList.length == 0"
        >
          <li>
            <a href="javascript:void(0);">
              <div class="media">
                <div class="media-body overflow-hidden">
                  <p class="chat-user-message text-truncate mb-0 pt-">
                    "{{ user.nickname }}"님은 뉴비입니다.
                  </p>
                </div>
              </div>
            </a>
          </li>
        </ul>
        <ul
          class="list-unstyled chat-list chat-user-list"
          v-else
        >
          <li>
            <div class="container-fluid row">
              <h5 class="col-2 text-center align-self-center font-size-13 m-0">
                티어
              </h5>
              <h5 class="col-4 text-center align-self-center font-size-13 m-0">
                장소
              </h5>
              <h5 class="col-1 text-center align-self-center font-size-13 m-0 p-0">
                색
              </h5>
              <h5 class="col-1 text-center align-self-center font-size-13 m-0">
                회수
              </h5>
              <h5 class="col-4 text-center align-self-center font-size-13 m-0">
                날짜
              </h5>
            </div>
          </li>
          <li
            v-for="(item, index) in solutionList"
            :key="index"
            @click="searchHistory(item,index)"
            class="mb-2"
          >
            <!-- <a href="javascript:void(0);"> -->
            <HistoryList
              :item="item"
              :color="colors[index]"
              :index="index"
            />
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { BASE_URL } from "@/constant/index";
import Colors from "@/constant/colors.js";
import UserInfo from "./components/userInfo";
import HistoryList from "./components/historyList";

export default {
  name: "myhistory",
  components: {
    UserInfo,
    HistoryList,
  },
  props: {
    nickname: String,
  },
  data() {
    return {
      isData: false,
      user: {
        profile: null,
        nickname: null,
        description: null,
        tier: null,
        exp: null,
        nextExp: null,
      },
      solutionList: [],
      colors: [],
      pageNumber: 0,
    };
  },
  computed: {},
  async mounted() {
    console.log("myhistory");
    await axios
      .get(`${BASE_URL}/history/${this.nickname}`)
      .then(({ data }) => {
        this.user = data.data.user;
        this.solutionList = data.data.solutionList;
        for (var i = 0; i < this.solutionList.length; i++) {
          this.colors.push(Colors.colors[this.solutionList[i].level]);
        }
        this.isData = true;
      })
      .catch((err) => {
        console.log("에러: " + err);
      });
  },
  methods: {
    searchHistory(solution, index) {
      axios
        .get(`${BASE_URL}/history/detail/` + solution.id)
        .then(({ data }) => {
          this.$router.push({
            name: "solution",
            params: {
              data: data.data,
              color: this.colors[index],
              isData: true,
            },
          });
        })
        .catch((err) => {
          console.log("에러: " + err);
        });
    },
  },
};
</script>
<style>
#tier-img {
  width: 23px;
  height: 23px;
}
#profile {
  width: 110%;
  height: 110%;
}
</style>