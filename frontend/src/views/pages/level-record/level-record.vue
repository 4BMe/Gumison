<template>
  <div class="height-full">
      <b-card no-body class="border custom-accordion">
        <b-card-header>
          <div class="d-flex flex-row">
            <a href="javascript: void(0);" @click="goBack()">
              <i class="ri-arrow-left-line mr-2"></i>
            </a>
            <h5 class="font-size-15 m-0">
              <i class="ri-user-2-line mr-2 mb-1 align-middle d-inline-block"></i>
                레벨 기록하기
            </h5>
          </div>
        </b-card-header>
          <b-card-body>
            <div class="mb-3">
              <input type="date" id="input-date" v-model="solutionDate" class="form-control"/>
            </div>
            <div v-for="(levelTier, index) in levelTiers" :key="index">
              <LevelRecordLine :level=levelTier.level :color=colors[index] :index=index :solutionCount="levelTier.solutionCount" class="mb-3 ml-3" v-model.number="recordSolutionCounts[index]" />
            </div>
          </b-card-body>
      </b-card>
          <div class="d-flex justify-content-center">
                <input type="file" value="동영상 업로드" ref="solutionVideos" class="btn btn-outline-success ml-3" accept="video/*" multiple @change="handleFileUpload()">
                <input type="button" value="등록" class="btn btn-outline-primary float-right mr-3 ml-3" @click="submitClick()">
          </div>
  </div>
</template>

<script>
import LevelRecordLine from './components/level-record-line';
import { submit, update } from '@/api/level-record.js';
import Colors from '@/constant/colors.js';

var today = new Date().toISOString().slice(0,10);

export default {
  props: {
    userId:{
      default: 0
    },
    climbingId: {
      default: 0
    },
    levelTiers: {},
  },
  components: {
      LevelRecordLine,
  },
  data(){
      return {
        recordSolutionCounts: [],
        solutionIds: [],
        solutionVideos: '',
        solutionDate: today,
        colors: [],
      }
  },
  methods: {
      handleFileUpload() {
          this.solutionVideos = this.$refs.solutionVideos.files;
      },
      goBack(){
        this.$router.go(-1);
      },
      async submitClick() {
          let recordData = [];
          for (var i = 0; i < this.colors.length; i++) {
            if(this.recordSolutionCounts[i] > 0) {
              if(this.solutionIds[i] > 0) {
                recordData.push({
                  userId: this.userId,
                  climbingId: this.climbingId,
                  solutionId: this.solutionIds[i],
                  levelTierId: this.levelTiers[i].id,
                  count: this.recordSolutionCounts[i],
                  date: this.solutionDate,
                });
              }
              else {
                recordData.push({
                  userId: this.userId,
                  climbingId: this.climbingId,
                  levelTierId: this.levelTiers[i].id,
                  count: this.recordSolutionCounts[i],
                  date: this.solutionDate,
                });
              }
            }
          }
          for (i = 0; i < recordData.length; i++) {
            console.log(JSON.stringify(recordData[i]));
          }
          if(this.solutionIds[0] > 0) {
            await update(recordData)
            .then(({ data }) => {
              console.log(data);
            })
            .catch(error => {
              console.log(error);
            })
          }
          else {
            await submit(recordData)
            .then(({ data }) => {
              console.log(data);
            })
            .catch(error => {
              console.log(error);
            })
          }
      },
  },
  mounted() {
    for (var i = 0; i < this.levelTiers.length; i++) {
      this.colors.push(Colors.colors[this.levelTiers[i].level]);
      if(this.levelTiers[i].solutionCount > 0)
        this.recordSolutionCounts.push(this.levelTiers[i].solutionCount);
      else
        this.recordSolutionCounts.push(0);

      if(this.levelTiers[i].solutionId > 0)
        this.solutionIds.push(this.levelTiers[i].solutionId);

      if(this.levelTiers[i].solutionDate) 
        this.solutionDate = this.levelTiers[i].solutionDate;
    }
    console.log(this.solutionIds);
  },
}
</script>

<style>

</style>