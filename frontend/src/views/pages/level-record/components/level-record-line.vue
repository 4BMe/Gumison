<template>
  <div>
      <div class="row">
          <div class="col-2">{{level}}</div>
          <div class="col-1 rounded-circle" :id="'color-'+index"></div>
          <div class="col-6">
              <input type="number" name="clearNum" id="clearNum" v-model="clearNum">
          </div>
      </div>
  </div>
</template>

<script>
export default {
    props: ['level', 'color', 'index'],
    data() {
        return {
            clearNum: 0,
        }
    },
    mounted() {
        document.getElementById('color-'+this.index).style="background-color: " + this.color;
    }
}
</script>

<style>

</style>