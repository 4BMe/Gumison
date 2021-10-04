<template>
  <div class="px-3" v-if="isData">
    <div>
      <h5 class="pl-2">Solution</h5>
    </div>
    <div class="chat-message-list" id="chat-list" ref="current">
      <ul
        class="list-unstyled chat-list chat-user-list"
        v-if="!solutionList.length"
      >
        <li>
          <a href="javascript:void(0);">
            <div class="media">
              <div class="media-body overflow-hidden">
                <p class="chat-user-message text-truncate mb-0">
                  해당 solution이 없습니다.
                </p>
              </div>
            </div>
          </a>
        </li>
      </ul>
      <table
        class="table table-hover text-center align-self-center font-size-13"
        v-else
      >
        <thead>
          <tr>
            <th scope="col">티어</th>
            <th scope="col">닉네임</th>
            <th scope="col">색</th>
            <th scope="col">횟수</th>
            <th scope="col">날짜</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="(item, index) in solutionList"
            :key="index"
            @click="searchHistory(item, index)"
            class="mb-2"
          >
            <td>
              <img
                :src="require(`@/assets/images/tier/` + item.tier + `.png`)"
                class="sm-tier-img m-0"
              />
            </td>
            <td>
                <div class="mt-1">{{ item.nickname }}</div>
            </td>
            <td>
              <div
                class="border border-dark rounded-circle mx-auto"
                style="height: 30px; width: 30px"
                :style="{ background: color[item.level] }"
              ></div>
            </td>
            <td>
                <div class="mt-1">{{ item.count }}</div>
            </td>
            <td>
                <div class="mt-1">{{ item.date }}</div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { BASE_URL } from "@/constant/index"
import { getClimingSolution } from "@/api/climbing";
import Colors from "@/constant/colors.js";

export default {
  name: "climbingSolution",
  components: {
  },
  data() {
    return {
      levelTiers: [],
      solutionList: [],
      pageNumber: 0,
      isData: false,
      color: {},
    };
  },
  created() {
    this.color = Colors.colors;
    this.climbingId = this.$route.params.climbingId;
    this.solutionId = this.$route.params.solutionId;
    console.log("climbingId: " + this.climbingId);
    console.log("solutionId: " + this.solutionId);
    this.getSolution();
  },
  methods: {
    getSolution() {
      // 클라이밍장 영업시간까지 가져오기
      getClimingSolution(this.climbingId, this.solutionId, this.pageNumber)
        .then(({ data }) => {
          this.solutionList = data.data;
          console.log(this.solutionList[0]);
          this.isData = true;
        })
        .catch((err) => {
          console.log("에러: " + err);
        });
    },

    searchHistory(solution,index) {
      axios
        .get(`${BASE_URL}/history/detail/`+solution.id)
        .then(({ data }) => {
          this.$router.push({name: 'solution', params: {data: data.data,color:this.colors[index], isData:true}})
        })
        .catch((err) => {
          console.log("에러: " + err);
        });
      
    },
  },
};
</script>

<style scoped>
.sm-tier-img {
  width: 30px;
}
</style>
