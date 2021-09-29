<template>
  <div>
      <div class="row">
          <div class="col-2">{{level}}</div>
          <div class="col-1 rounded-circle" :id="'color-'+index"></div>
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
            console.log("CHILD SENT !!! : " + this.solutionNum)
            this.$emit('input', this.solutionNum);
        }
    },
    mounted() {
        document.getElementById('color-'+this.index).style="background-color: " + this.color;
    }
}
</script>

<style>

</style>