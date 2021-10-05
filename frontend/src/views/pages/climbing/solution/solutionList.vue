<template>
  <b-container fluid class="row">
    <img
      :src="require(`@/assets/images/tier/` + item.tier + `.png`)"
      id="solution-tier-img"
      class="col-2 img-fluid rounded-circle sm-tier-img m-0"
    />
    <h5 class="col-3 text-center align-self-center font-size-13 m-0">
      {{ item.nickname }}
    </h5>
    <div
      class="col-1 rounded-circle level-record-color m-0 p-0"
      :style="{ background: color[item.level] }"
    ></div>
    <h5 class="col-2 text-center align-self-center font-size-13 m-0">
      {{ item.count }}
    </h5>
    <h5 class="col-4 text-center align-self-center font-size-13 m-0">
      {{ item.date }}
    </h5>
  </b-container>
</template>

<script>
import Colors from '@/constant/colors.js';

export default {
  props: {
    item: {
      type: Object,
      require: true,
    },
  },
  data() {
    return {
        color:{},
    };
  },
  mounted() {
      this.color = Colors.colors;
  },
  watch: {},
  methods: {},
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
