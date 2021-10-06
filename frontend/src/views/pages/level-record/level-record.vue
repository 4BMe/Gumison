<template>
  <div class="height-full">
    <b-card
      no-body
      class="border custom-accordion"
    >
      <b-card-body>
        <h5>해결한 문제를 기록하세요.</h5>
        <div class="mb-3">
          날짜
          <input
            type="date"
            id="input-date"
            v-model="solutionDate"
            class="form-control"
          />
        </div>
        <div
          v-for="(levelTier, index) in levelTiers"
          :key="index"
        >
          <LevelRecordLine
            :level="levelTier.level"
            :color="colors[index]"
            :index="index"
            :solutionCount="levelTier.solutionCount"
            class="mb-3 ml-3"
            v-model.number="recordSolutionCounts[index]"
          />
        </div>
      </b-card-body>
    </b-card>
    <b-card class="border custom-accordion">
      <div class="d-flex justify-content-center">
        <label class="btn btn-primary">
          영상 등록
          <input
            type="file"
            value="동영상 업로드"
            ref="solutionVideos"
            class="btn btn-outline-success ml-3"
            accept="video/*"
            @change="handleFileUpload()"
            style="display:none;"
            multiple
          />
          <i class="ri-video-fill"></i>
        </label>
      </div>
    </b-card>
    <div>

      <input
        type="button"
        value="완료"
        class="btn btn-primary float-right mt-3 mr-4 ml-1"
        @click="submitClick()"
      />
      <input
        type="button"
        value="취소"
        class="btn btn-outline-secondary float-right mt-3 mr-1 ml-3"
        @click="goToBack()"
      />
    </div>

  </div>
</template>

<script>
import LevelRecordLine from "./components/level-record-line";
import { submit, update } from "@/api/level-record.js";
import Colors from "@/constant/colors.js";

var today = new Date().toISOString().slice(0, 10);

export default {
  props: {
    climbingId: {},
    levelTiers: {},
  },
  components: {
    LevelRecordLine,
  },
  data() {
    return {
      oauthId: this.$store.state.users.user.oauthId,
      recordSolutionCounts: [],
      solutionIds: [],
      solutionVideos: "",
      solutionDate: today,
      colors: [],
    };
  },

  methods: {
    handleFileUpload() {
      this.solutionVideos = this.$refs.solutionVideos.files;
    },
    goToBack() {
      this.$router.go(-1);
    },
    async submitClick() {
      const formData = new FormData();
      formData.append("oauthId", this.oauthId);
      formData.append("climbingId", this.climbingId);
      formData.append("date", this.solutionDate);
      for (let i = 0; i < this.colors.length; i++) {
        if (this.recordSolutionCounts[i] > 0) {
          if (this.solutionIds[i] > 0) {
            formData.append("solutionIds", this.solutionIds[i]);
          }
          formData.append("levelTierIds", this.levelTiers[i].id);
          formData.append("counts", this.recordSolutionCounts[i]);
        }
      }
      for (let video of this.solutionVideos) {
        formData.append("videos", video);
      }

      if (this.solutionIds[0] > 0) {
        await update(formData)
          .then(({ data }) => {
            console.log(data);
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        await submit(formData)
          .then(({ data }) => {
            console.log(data);
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
  },
  mounted() {
    for (var i = 0; i < this.levelTiers.length; i++) {
      this.colors.push(Colors.colors[this.levelTiers[i].level]);
      if (this.levelTiers[i].solutionCount > 0)
        this.recordSolutionCounts.push(this.levelTiers[i].solutionCount);
      else this.recordSolutionCounts.push(0);

      if (this.levelTiers[i].solutionId > 0)
        this.solutionIds.push(this.levelTiers[i].solutionId);

      if (this.levelTiers[i].solutionDate)
        this.solutionDate = this.levelTiers[i].solutionDate;
    }
  },
};
</script>

<style>
input {
  background-image: "@/assets/images/icon/video-fill.png";

  background-position: top right;

  background-repeat: no-repeat;
}
</style>
