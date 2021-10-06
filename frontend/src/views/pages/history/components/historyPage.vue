<template>
  <div>
    <b-pagination
      v-model="page"
      :total-rows="solutionLastPage"
      pills
      per-page="1"
      align="center"
      size="sm"
      class="mt-2"
      v-if="isData"
    ></b-pagination>
  </div>
</template>
<script>
export default {
  name: "history-pagination",

  props: {
    isData: {
      default: false,
    },
    solutionPage: {
      default: 1,
    },
    solutionLastPage: {
      default: 1,
    },
  },

  data() {
    return {
      pageList: [],
      page: 1,
    };
  },

  watch: {
    page: function() {
      this.movePage();
    },
  },

  methods: {
    movePage() {
      this.$emit("movePage", this.page);
    },
    generatePageList() {
      const PAGE_COUNT = 3;

      const startOffset =
        this.solutionPage - PAGE_COUNT < 1 ? 1 : this.solutionPage - PAGE_COUNT;
      const endOffset =
        this.solutionPage + PAGE_COUNT > this.solutionLastPage
          ? this.solutionLastPage
          : this.solutionPage + PAGE_COUNT;

      this.pageList = [];
      if (startOffset != 1) {
        this.pageList.push(1);
      }
      let i = startOffset;
      for (; i <= endOffset; i++) {
        this.pageList.push(i);
      }
      if (endOffset != this.solutionLastPage) {
        this.pageList.push(this.solutionLastPage);
      }
    },
  },
};
</script>
