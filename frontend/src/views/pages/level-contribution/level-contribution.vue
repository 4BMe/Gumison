<template>
    <div class="height-full">
      <b-card no-body class="border custom-accordion">
        <b-card-header>
          <div class="d-flex flex-row">
            <a href="javascript: void(0);" @click="goBack()">
              <i class="ri-arrow-left-line mr-2"></i>
            </a>
            <h5 class="font-size-15 m-0">
              <i class="ri-trophy-line mr-2 mb-1 align-middle d-inline-block"></i>
                레벨 기여하기
            </h5>
          </div>
        </b-card-header>
          <b-card-body>
            <div v-for="(levelTier, index) in levelTiers" :key="index">
                <LevelContributionLine :level=levelTier.level :color=colors[index] :tier="levelTier.tier" :tierImage="tiers[index]" :index=index class="mb-3 ml-3" v-model="tiers[index]" />
            </div>
          </b-card-body>
      </b-card>
          <div class="d-flex justify-content-center">
                <input type="button" value="기여하기" class="btn btn-outline-primary float-right mr-3 ml-3" @click="submitClick()">
                <input type="button" value="취소" class="btn btn-outline-danger float-right mr-3 ml-3" @click="goBack()">
          </div>
  </div>
</template>

<script>
import LevelContributionLine from './components/level-contribution-line';
import Colors from '@/constant/colors.js';
import { submit } from '@/api/level-contribution.js';

var today = new Date().toISOString().slice(0,10);

export default {
    name: 'level-contribution',
    components: {
        LevelContributionLine,
    },
    props: {
        nickname: {},
        climbingId: {},
        levelTiers: {},
    },
    data(){
        return {
            colors: [],
            tiers: [],
            contributionDate: today,
        }
    },
    methods:{
        goBack(){
            this.$router.go(-1);
        },
        async submitClick() {
            console.log(this.tiers);
            let contributionData = [];
            for (let i = 0; i < this.levelTiers.length; i++) {
                contributionData.push({
                    levelTierId: this.levelTiers[i].id,
                    newTier: this.tiers[i],
                    oldTier: this.levelTiers[i].tier,
                })
            }
            let contributionRequest = {
                contributionData: contributionData,
                nickname: this.nickname,
                date: this.contributionDate,
            }
            console.log(contributionRequest);
            await submit(contributionRequest)
            .then(({ data }) => {
                console.log(data);
            })
            .catch(error => {
                console.log(error);
            })
        },
    },
    mounted(){
        for (var i = 0; i < this.levelTiers.length; i++) {
            this.colors.push(Colors.colors[this.levelTiers[i].level]);
            this.tiers.push(this.levelTiers[i]);
            if(this.levelTiers[i].solutionDate) {
                this.solutionDate = this.levelTiers[i].solutionDate;
            }
        }
    },
}
</script>

<style>

</style>