<template>
  <div class="container-fluid row">
    <div class="col-2 text-center align-self-center">
    <img
      :src="require(`@/assets/images/tier/`+item.tier+`.png`)"
      id="solution-tier-img"
      class="img-fluid rounded-circle sm-tier-img m-0"
    />
    </div>
    <h5 class="col-4 text-center align-self-center font-size-13 m-0">
      {{ item.climbingName }}
    </h5>
    <div class="col-1 m-0 p-0 d-flex justify-content-center">
      <div
        class="m-0 p-0 rounded-circle text-center align-self-center color-round"
        :id="'color-'+index"
      />
    </div>
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
export default {
  name: "historyList",
  props: {
    item: {
      type: Object,
      require: true,
    },
    color: {
      type: String,
      require: true,
    },
    index: {},
  },
  data() {
    return {};
  },
  mounted() {
    this.$nextTick(function () {
      document.getElementById("color-" + this.index).style =
        "border: 1px solid; background-color: " + this.color;
    });
  },
  watch: {},
  methods: {},
};
</script>
<style>
#solution-tier-img {
  width: 35px;
  height: 35px;
}
.color-round {
  width: 30px;
  height: 30px;
}
</style>