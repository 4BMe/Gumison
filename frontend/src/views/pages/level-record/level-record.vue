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
            <div v-for="(level, index) in levels" :key="index">
              <LevelRecordLine :level=levels[index] :color=colors[index] :index=index />
              <br>
            </div>
          </b-card-body>
      </b-card>
          <div>
                <input type="file" value="동영상 업로드" ref="solutionVideos" class="btn btn-outline-success" accept="video/*" multiple @change="handleFileUpload()">
                <br><br>
                <input type="button" value="등록" class="btn btn-outline-primary" @click="submit()">
          </div>
  </div>
</template>

<script>
import LevelRecordLine from './components/level-record-line';
// import { uploadFile } from '@/api/level-record.js';
import Vue from 'vue';
import VueAlertify from 'vue-alertify';

Vue.use(VueAlertify);
export default {
    components: {
        LevelRecordLine,

    },
    data(){
        return {
            colors: ['red', 'blue', 'green'],
            levels: ['빨강', '파랑', '초록'],
            solutionVideos: '',
        }
    },
    methods: {
        handleFileUpload() {
            this.solutionVideos = this.$refs.solutionVideos.files;
        },
        submit() {
            console.log('this.solutionVideos!!');
            let formData = new FormData();
            for (var i = 0; i < this.solutionVideos.length; i++) {
                let solutionVideo = this.solutionVideos[i];

                console.log('solutionVideo[' + i + '] : ' + solutionVideo);
                formData.append('solutionVideo[' + i + ']', solutionVideo);
            }
            // uploadFile(formData)
            //     .then(({status})=> {
            //         if(status!=200) {
            //             return;
            //         } else {
            //             this.$alertify.success('동영상 업로드에 성공하였습니다.')
            //         }
            //     })
            //     .catch(() => {
            //         this.$alertify.error('동영상 업로드에 실패하였습니다.')
            //     })
        }
    },
}
</script>

<style>

</style>