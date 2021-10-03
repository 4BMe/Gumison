<template>
  <div class="px-3" v-if="isData">
    <div>
        <h5 class="border-bottom border-light pl-2 pb-2"> Level </h5>
    </div>
    <ul class="list-unstyled chat-list chat-user-list">
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
  </div>
</template>

<script>
import { getClimingDetail } from "@/api/climbing";
import LevelLine from './levelLine.vue';

export default {
  components: { 
    LevelLine 
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
      this.$router.push({ name: 'climbingSolution', params: {solutionId: solutionId}});
    }
  },
}
</script>

<style>

</style>