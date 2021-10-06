<template>
  <div class="px-3" v-if="isData" >
    <div>
      <h5 class="pl-2">Solution</h5>
    </div>
    <SolutionList 
      :solutionList ="solutionList"
    />
    <b-pagination
      v-model="pageNumber"
      :total-rows="totalRows"
      @page-click="pageClick"
      per-page="10"
      size="sm"
      align="center"
      class="m-0"
      pills
    ></b-pagination>
  </div>
</template>ㅌ

<script>
import { getClimingSolution } from "@/api/climbing";
import Colors from "@/constant/colors.js";
import SolutionList from "./solutionList"

export default {
  name: "climbingSolution",
  components: {
    SolutionList
  },
  data() {
    return {
      levelTiers: [],
      solutionList: [],
      pageNumber: 1,
      isData: false,
      color: {},
      totalRows: 0,
    };
  },
  created() {
    this.color = Colors.colors;
    this.climbingId = this.$route.params.climbingId;
    this.solutionId = this.$route.params.solutionId;
    this.getSolution();
  },
  methods: {
    getSolution() {
      // 클라이밍장 영업시간까지 가져오기
      getClimingSolution(this.climbingId, this.solutionId, this.pageNumber - 1)
        .then(({ data }) => {
          this.solutionList = data.data.climbingSolutions;
          this.totalRows = data.data.totalElements;
          this.isData = true;
        })
        .catch((err) => {
          console.log("에러: " + err);
        });
    },

    pageClick(button, page){
      this.pageNumber = page;
      getClimingSolution(this.climbingId, this.solutionId, this.pageNumber - 1)
        .then(({ data }) => {
          this.solutionList = data.data.climbingSolutions;
          window.scrollTo(0,0);
          this.isData = true;
        })
        .catch((err) => {
          console.log("에러: " + err);
        });
    }
  },
};
</script>

<style scoped>
.solution-list {
  height: calc(100vh - 420px);
}
</style>
