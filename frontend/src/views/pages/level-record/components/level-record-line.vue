<template>
  <div class="row d-flex justify-content-center">
    <div class="col-2 mt-1">{{level}}</div>
    <div
      class="col-1 rounded-circle level-record-color"
      :id="'color-'+index"
    ></div>
    <div class="col-2 ml-3">
      <input
        type="number"
        min="0"
        max="1000"
        id="solutionNum"
        v-model="lineSolutionCount"
        @change="emitEvent()"
      >
    </div>
    <div class="col-4 ml-2 mt-1">
      문제 해결
    </div>
  </div>
</template>

<script>
export default {
  props: {
    level: {},
    color: {},
    index: {},
    solutionCount: {
      default: 0,
    },
  },
  data() {
    return {
      lineSolutionCount: 0,
    };
  },
  methods: {
    emitEvent() {
      this.$emit("input", this.lineSolutionCount);
    },
  },
  mounted() {
    this.lineSolutionCount = this.solutionCount;
    this.$nextTick(function () {
      document.getElementById("color-" + this.index).style =
        "border: 1px solid; background-color: " + this.color;
    });
  },
};
</script>

<style>
</style>