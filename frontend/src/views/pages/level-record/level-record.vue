<template>
  <div class="height-full">
      <b-card no-body class="border custom-accordion">
        <b-card-header>
          <div class="d-flex flex-row">
            <a href="javascript: void(0);" @click="goBack()">
              <i class="ri-arrow-left-line mr-2"></i>
            </a>
            <h5 class="font-size-15 m-0">
              <i class="ri-user-2-line mr-2 align-middle d-inline-block"></i>
                레벨 기록하기
            </h5>
          </div>
        </b-card-header>
          <b-card-body>
            <div class="mb-3">
              <input type="date" id="input-date" v-model="solutionDate" class="form-control"/>
            </div>
            <div v-for="(level, index) in levels" :key="index">
              <LevelRecordLine :level=levels[index] :color=colors[index] :index=index class="mb-3 ml-3" v-model.number="solutionCounts[index]" />
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
import { submit } from '@/api/level-record.js';
import Colors from '@/constant/colors.js';

var today = new Date();

export default {
  props: {
    levelTierIds: [],
    levels: [],
    solutionDate: {
      default: today.toISOString().slice(0,10),
    },
    solutionCounts: {
      default(){
        return [];
      },
    },
    climbingId: {
      default: 0
    },
    userId:{
      default: 0
    },
  },
  components: {
      LevelRecordLine,
  },
  data(){
      return {
          solutionVideos: '',
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
            if(this.solutionCounts[i] > 0)
            recordData.push({
              count: this.solutionCounts[i],
              date: this.solutionDate,
              climbingId: this.climbingId,
              levelTierId: this.levelTierIds[i],
              userId: this.userId
            })
          }
          for (i = 0; i < recordData.length; i++) {
            console.log(JSON.stringify(recordData[i]));
          }

          await submit(recordData)
          .then(({data}) => {
            console.log(data);
          })
          .catch(error => {
            console.log(error);
          })
      },
  },
  mounted() {
    for (var i = 0; i < this.levels.length; i++) {
      this.colors.push(Colors.colors[this.levels[i]]);
    }
  },
}
</script>

<style>

</style>