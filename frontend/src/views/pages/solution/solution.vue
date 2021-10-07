<template>
  <div>
    <div class="mt-4 px-4 pb-4 pt-4 container-fluid row ">
      <h4
        class="mb-0 text-center col-1 ml-1 p-0"
        @click="goToBack()"
      ><i class="ri-arrow-left-s-line"></i></h4>
      <h4 class="mb-0 text-center col mr-3">
        {{ data.solution.climbingName }}
      </h4>
    </div>
    <div>
      <div class="px-2">
        <div class="container-fluid row">
          <div
            class="px-4 col-2 chat-user-img online align-self-center"
            id="userCard"
          >
            <div
              v-if="data.profile"
              class="avatar-xs"
            >
              <img
                :src="data.profile"
                class="rounded-circle avatar-xs"
                id="profile"
                alt="profile"
              />
            </div>
            <div
              class="avatar-xs"
              v-else
            >
              <span class="avatar-title rounded-circle bg-soft-primary text-primary">{{ data.nickname.charAt(0) }}
              </span>
            </div>
          </div>
          <div class="media-body ml-4 p-0 align-self-center">
            <h5 class="text-truncate font-size-15 mb-1">
              <img
                :src="require(`@/assets/images/tier/`+data.tier+`.png`)"
                alt="sol-tier-img"
                id="sol-tier-img"
                class="img-fluid rounded-circle sm-tier-img"
              />
              {{ data.nickname }}
            </h5>
          </div>
          <div class="col-2 p-0 no-padding chat-user-img online align-self-center">
          </div>
          <div class="col-4 chat-user-img online align-self-center">
            <h5 class="text-truncate font-size-15 mb-1 mt-3">
              {{ data.solution.date }}
            </h5>
          </div>
        </div>
      </div>
    </div>
    <div
      class="m-2 p-2"
      id="solution"
    >
      <div
        class="m-3 p-3 row"
        id="level"
      >
        <div
          v-for="(tier, index) in data.solution.tier"
          :key=index
          class="row text-center align-self-center col border border-2 mr-auto ml-auto"
        >
          <h5 class="col-5 m-0 p-0 text-center align-self-center font-size-15">
            <img
              :src="require(`@/assets/images/tier/` + tier + `.png`)"
              alt="sol-tier-img"
              id="sol-tier-img"
              class="img-fluid rounded-circle sm-tier-img"
            />
          </h5>
          <div
            class="col-5 rounded-circle level-record-color m-0 p-0"
            :id="`color-${index}`"
          ></div>
          <div class="col-2 m-0 p-0 text-center align-self-center">
            {{data.solution.counts[index]}}
          </div>
        </div>
      </div>
      <simplebar class="solution">
        <div id="video">
          <div
            v-if="data.solution.solutionVideoList && data.solution.solutionVideoList.length!=0"
            class="container row"
          >
            <!-- <div v-for="(video, index) in data.solution.solutionVideoList" :key="index"> -->
            <button
              @click="previousVideo()"
              class="col-1 btn btn-link"
            >
              <i class="ri-arrow-left-s-line"></i>
            </button>
            <video
              controls
              :src="getVideoSrc()"
              class="container col-10"
            >
            </video>
            <button
              @click="nextVideo()"
              class="col-1 btn btn-link"
            >
              <i class="ri-arrow-right-s-line"></i>
            </button>
            <!-- </div> -->
          </div>
        </div>
        <div id="buttons" class="mt-3" v-if="isMyself()">
          <div class="container-fluid row m-0 p-0">
            <div class="col-5 m-0 p-0" />
            <button class="btn btn-outline-success ml-1"
                    @click="clickContribution()">
              기여
            </button>
            <button
              class="btn btn-outline-danger ml-1"
              @click="clickUpdate()"
            >
              수정
            </button>
            <button
              class="btn btn-outline-info ml-1"
              @click="clickDelete()"
            >
              삭제
            </button>
          </div>
        </div>
      </simplebar>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import simplebar from "simplebar-vue";
import { BASE_URL } from "@/constant/index";

/**
 * Profile component
 */
export default {
  components: {
    simplebar,
  },
  name: "solution",
  props: {
    data: {
      type: Object,
      require: true,
    },
    colors: {
      require: true,
    },
  },
  data() {
    return {
      videoIdx: 0,
    };
  },
  mounted() {
    console.log("data-solution.vue", this.data);
    this.$nextTick(function () {
      for (let i = 0; i < this.colors.length; i++) {
        document.getElementById("color-" + i).style =
          "border: 1px solid; background-color: " + this.colors[i];
      }
    });
  },
  methods: {
    isMyself(){
      return this.$store.state.users.user.nickname == this.data.nickname;
    },
    getVideoSrc() {
      return `${BASE_URL}/history/videos?fileName=${
        this.data.solution.solutionVideoList[this.videoIdx].uri
      }`;
    },
    clickUpdate(){
      let levelTiers =[];
      for(let i = 0; i < this.data.solution.level.length; i++) {
        levelTiers.push({
          level: this.data.solution.level[i],
          solutionCount: this.data.solution.counts[i],
          solutionIds: this.data.solution.solutionIds[i],
          solutionDate: this.data.solution.date,
        });
      }
      this.$router.push({
        name: "level-record-update",
        params: {
          nickname: this.data.nickname,
          climbingId: this.data.solution.climbingId,
          levelTiers: levelTiers,
          uploadId: this.data.uploadId,
        },
      });
    },
    async clickDelete() {
      console.log("this.uploadId : "+this.data.uploadId);
      await axios
        .delete(`${BASE_URL}/history/${this.data.uploadId}`)
<<<<<<< HEAD
        .then(() => {
          console.log("this.data.nickname : " + this.data.nickname);
          let nickname = this.data.nickname;
          this.$router.push({ name: "myhistory", params: { nickname: nickname } });
=======
        .then(({data}) => {
          console.log(data);
          this.$router.push({
              name: 'myhistory',
              props: {
                nickname: this.data.nickname,
              }
          });
>>>>>>> develop
        })
        .catch(error => {
          console.log(error);
        })
    },
    clickContribution(){
      let levelTiers = [];
      for(let i = 0; i < this.data.solution.levelTierIds.length; i++) {
        levelTiers.push({
          id: this.data.solution.levelTierIds[i],
          level: this.data.solution.level[i],
          tier: this.data.solution.tier[i]
        })
      }
      this.$router.push({
        name: 'level-contribution',
        params: {
          nickname: this.data.nickname,
          climbingId: this.data.solution.climbingId,
          levelTiers: levelTiers,
        }
      })
    },
    previousVideo() {
      if (this.videoIdx > 0) this.videoIdx--;
    },
    nextVideo(){
      if(this.videoIdx < this.data.solution.solutionVideoList.length - 1) {
        this.videoIdx++;
      }
    },
    goToBack() {
      this.$router.go(-1);
    },
  },
};
</script>
<style scoped>
#sol-tier-img {
  width: 23px;
  height: 23px;
}
</style>