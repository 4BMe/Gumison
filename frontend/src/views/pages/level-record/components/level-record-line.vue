<template>
  <div>
      <div class="row">
            <div class="col-2 mt-1">{{level}}</div>
            <div class="col-1 rounded-circle level-record-color" :id="'color-'+index"></div>
            <div class="col-6">
                <input type="number" min="0" id="solutionNum" v-model="solutionNum" @change="emitEvent()">
            </div>
      </div>
  </div>
</template>

<script>
export default {
    props: ['level', 'color', 'index'],
    data() {
        return {
            solutionNum: 0,
        }
    },
    methods: {
        emitEvent() {
            this.$emit('input', this.solutionNum);
        }
    },
    mounted() {
        this.$nextTick(function (){
            document.getElementById('color-'+this.index).style="border: 1px solid; background-color: " + this.color;
        })
    }
}
</script>

<style>
</style>