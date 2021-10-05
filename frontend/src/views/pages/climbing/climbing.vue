<template>
  <div v-if="isData">
    <ClimbingDetail 
      :climbing="climbingDetail.climbing"
      :hours="climbingDetail.hours" />
    <router-view></router-view>
  </div>
</template>

<script>
import { getClimingDetail } from "@/api/climbing";
import ClimbingDetail from "@/views/pages/climbing/climbingDetail";

export default {
  name: "climbing",
  components: {
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

<style></style>
