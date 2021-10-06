<template>
  <simplebar class="solution-list mb-2">
    <table
      class="table table-hover text-center align-self-center font-size-13 m-0"
      id="list-top"
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
      <tbody  v-if="!solutionList.length">
        <tr>
          <td colspan="5"> 해당 solution이 없습니다. </td>
        </tr>
      </tbody>
      <tbody v-else>
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
  </simplebar>
</template>

<script>
import axios from "axios";
import { BASE_URL } from "@/constant/index";
import simplebar from "simplebar-vue";
import Colors from "@/constant/colors.js";

export default {
  components: {
    simplebar,
  },
  props: {
    solutionList: Array,
  },
  watch: {
    solutionList() {
      location.href = '#list-top';
    },
  },
  data() {
    return {
      color: {},
    };
  },
  mounted() {
    this.color = Colors.colors;
  },
  methods: {
    searchHistory(solution) {
      axios
        .get(`${BASE_URL}/history/detail/` + solution.id)
        .then(({ data }) => {
          let colorsParam = [];
          for (let i = 0; i < data.data.solution.level.length; i++) {
            colorsParam.push(Colors.colors[data.data.solution.level[i]]);
          }
          this.$router.push({
            name: "solution",
            params: {
              data: data.data,
              colors: colorsParam,
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
<style scoped>
.sm-tier-img {
  width: 30px;
}

.h5 {
  width: 10%;
  height: 10%;
}
</style>
