<template>
  <div class="container">
      <b-card no-body class="border custom-accordion">
        <b-card-header>
          <a href="javascript: void(0);">
            <h5 class="font-size-15 m-0">
              <i class="ri-user-2-line mr-2 align-middle d-inline-block"></i>
                레벨 기록하기
            </h5>
          </a>
        </b-card-header>
          <b-card-body>
            <div class="mb-3">
              <input type="date" id="input-date" v-model="solutionDate" class="form-control" />
            </div>
            <div v-for="(level, index) in levels" :key="index">
              <LevelRecordLine :level=levels[index] :color=colors[index] :index=index class="mb-3" v-model.number="solutionCount[index]" />
            </div>
          </b-card-body>
      </b-card>
          <div>
                <input type="file" value="동영상 업로드" ref="solutionVideos" class="btn btn-outline-success" accept="video/*" multiple @change="handleFileUpload()">
                <br><br>
                <input type="button" value="등록" class="btn btn-outline-primary float-right mr-3" @click="submitClick()">
          </div>
  </div>
</template>

<script>
import LevelRecordLine from './components/level-record-line';
import { submit } from '@/api/level-record.js';

var today = new Date();

export default {
    components: {
        LevelRecordLine,
    },
    data(){
        return {
            colors: ['red', 'blue', 'green'],
            levels: ['빨강', '파랑', '초록'],
            solutionVideos: '',
            solutionDate: '',
            solutionCount: [0, 0, 0],
        }
    },
    methods: {
        handleFileUpload() {
            this.solutionVideos = this.$refs.solutionVideos.files;
        },
        async submitClick() {
            let solutionRequests = [];
            for (var i = 0; i < this.colors.length; i++) {
              if(this.solutionCount[i] > 0)
              solutionRequests.push({
                count: this.solutionCount[i],
                date: this.solutionDate,
                climbingId: 2,
                levelTierId: 17,
                userId: 2
              })
            }
            for (i = 0; i < solutionRequests.length; i++) {
              console.log(JSON.stringify(solutionRequests[i]));
            }

            await submit(solutionRequests)
            .then(({data}) => {
              console.log(data);
            })
            .catch(error => {
              console.log(error);
            })
        },
    },
    mounted() {
      this.solutionDate = today.toISOString().slice(0,10);
    },
    watch: {
      solutionCount: function(){
        console.log(this.solutionCount);
      },
      solutionDate: function(){
        console.log('solutionDate is ' + this.solutionDate);
      },
    },
}
</script>

<style>

</style>