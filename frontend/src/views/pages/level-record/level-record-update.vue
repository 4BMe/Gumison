<template>
  <div v-if="isData">
    <simplebar class="climbing">
      <ClimbingDetail 
        :climbing="climbingDetail.climbing"
        :hours="climbingDetail.hours" />
      <LevelRecord :climbingId="climbingId" :levelTiers="levelTiers"/>
    </simplebar>
  </div>
</template>

<script>
import simplebar from "simplebar-vue";
import { getClimingDetail } from "@/api/climbing";
import ClimbingDetail from "@/views/pages/climbing/climbingDetail";
import LevelRecord from "@/views/pages/level-record/level-record";

export default {
  name: "level-record-update",
  components: {
    simplebar,
    ClimbingDetail,
    LevelRecord,
  },
  props: {
    climbingId: {},
    levelTiers: {},
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
    console.log("level-record-update.vue")
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
