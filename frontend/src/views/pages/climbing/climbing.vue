<template>
  <div v-if="isData">
    <simplebar class="climbing">
      <ClimbingDetail 
        :climbing="climbingDetail.climbing"
        :hours="climbingDetail.hours" />
      <router-view></router-view>
    </simplebar>
  </div>
</template>

<script>
import simplebar from "simplebar-vue";
import { getClimingDetail } from "@/api/climbing";
import ClimbingDetail from "@/views/pages/climbing/climbingDetail";

export default {
  name: "climbing",
  components: {
    simplebar,
    ClimbingDetail,
  },
  props: {
    climbingId: {},
  },
  data() {
    return {
      climbingDetail: { },
      isData: false,
    };
  },
  created() {
  },
  mounted() {
    this.getClimbing();
  },
  methods: {
    getClimbing(){
      // 클라이밍장 영업시간까지 가져오기
      getClimingDetail(this.climbingId)
      .then(({ data }) => {
        this.climbingDetail = data.data;
        this.isData = true;
        // this.$emit('climbing-level', this.climbingDetail.levelTiers);
      })
      .catch((err) => {
        console.log("에러: " + err);
      });
    }
  },
};
</script>

<style scoped>
.climbing{
  height: calc(100vh - 70px);
}
</style>
