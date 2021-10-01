<template>
  <div class="container-fluid row">
        <img
          :src="require(`@/assets/images/tier/`+item.tier+`.png`)"
          id="solution-tier-img"
          class="col-2 img-fluid rounded-circle sm-tier-img m-0"
        />
      <h5 class="col-4 text-center align-self-center font-size-13 m-0">
        {{ item.climbingName }}
      </h5>
      <div class="col-1 rounded-circle level-record-color m-0 p-0" :id="'color-'+index"></div>
      <!-- <h5 class="col-1 rounded-circle level-record-color align-self-center m-0" :id="'color-'+index">
      </h5> -->
      <h5 class="col-1 text-center align-self-center font-size-13 m-0">
        {{ item.cnt }}
      </h5>
      <h5 class="col-4 text-center align-self-center font-size-13 m-0">
        {{ item.date }}
      </h5>
  </div>
</template>

<script>

/**
 * Profile component
 */
export default {
  props: {
    item: {
      type: Object,
      require: true
    },
    color:{
      type: String,
      require: true
    },
    index:{

    }
  },
  data() {
    return {
    };
  },
  mounted() {
    this.$nextTick(function (){
      document.getElementById('color-'+this.index).style="border: 1px solid; background-color: " + this.color;
    })
  },
  watch: {
  },
  methods: {
  },
};
</script>
<style>
#solution-tier-img {
  width: 10%;
  height: 10%;
}
.h5 {
  width: 10%;
  height: 10%;
}
</style>