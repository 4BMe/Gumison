<template>
  <div class="row">
    <div class="col-2">
      <div
        class="rounded-circle level-record-color border border-dark color-round"
        :style="{ background: color[levelTier.level] }"
        style=""
      ></div>
    </div>
    <div class="col-3 pl-2 mt-1">{{ levelTier.level }}</div>
    <div class="col-4 mt-1">
        {{levelTier.tier}}
      <!-- <input type="number" min="0" id="solutionNum" v-model="lineSolutionCount" @change="emitEvent()"> -->
    </div>
  </div>
</template>

<script>
import Colors from "@/constant/colors.js";
export default {
  props: {
    levelTier: {},
  },
  data() {
    return {
      color: {},
    };
  },
  methods: {},
  mounted() {
    this.color = Colors.colors;
  },
};
</script>

<style scoped>
.color-round {
  width: 30px;
  height: 30px;
}
</style>
