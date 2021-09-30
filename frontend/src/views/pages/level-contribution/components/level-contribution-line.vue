<template>
    <div class="row">
        <div class="col-2 mt-1">{{ level }}</div>
        <div class="col-1 rounded-circle level-record-color" :id="'color-'+index"></div>
        <div class="col-2"></div>
        <div class="col-6 row">
            <div class="col-4"><i class="ri-arrow-left-s-fill" @click="tierDown()"></i></div>
            <div class="col-4"><img :src="require(`@/assets/images/tier/` + this.lineTierImage + `.png`)" alt="tier" class="img-fluid rounded-circle sm-tier-img"></div>
            <div class="col-4"><i class="ri-arrow-right-s-fill" @click="tierUp()"></i></div>
        </div>
    </div>
</template>

<script>
import Tiers from '@/constant/tiers.js';

export default {
    name: 'level-contribution-line',
    props: {
        level: {}, 
        color: {},
        tier: {},
        tierImage: {},
        index: {},
    },
    data() {
        return {
            lineTier: '브론즈4',
            lineTierImage: 'bronze4',
        }
    },
    methods: {
        tierDown() {
            if(this.lineTier != '브론즈4')
                this.lineTier = Tiers.tierDown(this.lineTier);
        },
        tierUp() {
            if(this.lineTier != '마스터1')
                this.lineTier = Tiers.tierUp(this.lineTier);
        },
    },
    mounted() {
        this.$nextTick(function (){
            document.getElementById('color-'+this.index).style="border: 1px solid; background-color: " + this.color;
            this.lineTier = this.tier;
            this.lineTierImage = this.tierImage;
        })
    },
    watch: {
        lineTier: function(){
            this.lineTierImage = Tiers.tiers[this.lineTier];
            this.$emit('input', this.lineTier);
        },
    }
}
</script>

<style>

</style>