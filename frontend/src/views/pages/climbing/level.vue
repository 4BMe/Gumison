<template>
  <div class="px-3" v-if="isData">
    <div class="row">
        <h5 class="col-9 border-bottom border-light pl-2 pb-2"> Level </h5>
        <div class="col-3">
          <input type="button" value="기록하기" class="btn btn-outline-info btn-sm mb-3" @click="levelRecord()">
        </div>
    </div>
    <simplebar class="level-list" id="chat-list" ref="current">
      <ul class="list-unstyled chat-list chat-user-list">
        <li class="border-bottom border-light">
          <div class="row m-0">
            <div class="col-5 p-1">
              <h5 class="text-center font-size-15"
              >색</h5>
            </div>
            <h5 class="col-4 p-1 text-center font-size-15">
                티어
            </h5>
          </div>
        </li>
        <li
          v-for="(levelTier, index) in levelTiers"
          :key="index"
          @click="searchSolutions(levelTier.id)"
        >
        <a  href="javascript:void(0);">
          <LevelLine
            :levelTier="levelTier"
          />
        </a>

        </li>
      </ul>
    </simplebar>
  </div>
</template>

<script>
import { getClimingDetail } from "@/api/climbing";
import simplebar from "simplebar-vue"; 
import LevelLine from './levelLine.vue';

export default {
  components: { 
    LevelLine,
    simplebar 
  },
  props: {
  },
  data() {
    return {
      levelTiers: [],
      isData: false,
    }
  },
  created() {
    },
  mounted() {
    this.getClimbing();
  },
  methods: {
    getClimbing(){
      // 클라이밍장 영업시간까지 가져오기
      getClimingDetail(this.$route.params.climbingId)
      .then(({ data }) => {
        this.levelTiers = data.data.levelTiers;
        this.isData = true;
      })
      .catch((err) => {
        console.log("에러: " + err);
      });
    },

    searchSolutions(solutionId){
      this.$router.push({
        name: 'climbingSolution',
        params: {
          climbingId: this.$route.params.climbingId,
          solutionId: solutionId
          },
        hash: '#list-top'
        });
    },

    levelRecord(){
      var climbingId = this.$route.params.climbingId;
      this.$router.push({
        name: 'level-record',
        params: {
          climbingId: climbingId,
          levelTiers: this.levelTiers,
        }
      }).catch();
    }
  },
}
</script>

<style scoped>
.level-list{
    height: calc(100vh - 410px);
}
</style>