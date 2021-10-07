<template>
  <div v-if="isData">
    <simplebar class="climbing">
      <ClimbingDetail 
        :climbing="climbingDetail.climbing"
        :hours="climbingDetail.hours" />
      <LevelRecord :climbingId="climbingId" :levelTiers="levelTiersUpdate" :uploadId="uploadId" :nickname="nickname"/>
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
    nickname: {},
    climbingId: {},
    levelTiers: {},
    uploadId: {},
  },
  data() {
    return {
      climbingDetail: { },
      levelTiersUpdate: [],
      isData: false,
    };
  },
  created() {
  },
  mounted() {
    // console.log("level-record-update.vue")
    this.getClimbing();
  },
  methods: {
    getClimbing(){
      // 클라이밍장 영업시간까지 가져오기
      getClimingDetail(this.climbingId)
      .then(({ data }) => {
        this.climbingDetail = data.data;
        // console.log("this.climbingDetail", this.climbingDetail);
        this.isData = true;
        let date = this.levelTiers[0].date;
        console.log("this.levelTiers", this.levelTiers);
        for(let i = 0; i < this.climbingDetail.levelTiers.length; i++) {
          let foundFlag = false;
          let updateIdx = 0;
          for(let j = 0; j < this.levelTiers.length; j++) {
            if(this.climbingDetail.levelTiers[i].level == this.levelTiers[j].level) {
              foundFlag = true;
              updateIdx = j;
              break;
            }
          }
          if(foundFlag) {
            this.levelTiersUpdate.push({
              id:this.climbingDetail.levelTiers[i].id,
              level: this.levelTiers[updateIdx].level,
              solutionCount: this.levelTiers[updateIdx].solutionCount,
              solutionId: this.levelTiers[updateIdx].solutionIds,
              solutionDate: date,
            })
            updateIdx++;
          } else {
            this.levelTiersUpdate.push({
              id:this.climbingDetail.levelTiers[i].id,
              level: this.climbingDetail.levelTiers[i].level,
              solutionCount: 0,
              solutionDate: date,
            })
          }
        }
      })
      .catch((err) => {
        console.log("에러: " + err);
      });
    },
  },
};
</script>

<style scoped>
.climbing{
  height: calc(100vh - 70px);
}
</style>
