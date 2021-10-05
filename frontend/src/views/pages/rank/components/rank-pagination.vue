<template>
  <div>
    <b-pagination
      v-model="page"
      :total-rows="rankLastPage"
      pills
      per-page="1"
      align="center"
      size="sm"
      class="mt-2"
      v-if="dataLoaded"
    ></b-pagination>
  </div>
</template>
<script>
export default {
  name: "rank-pagination",

  props: {
    dataLoaded: {
      default: false,
    },
    rankPage: {
      default: 1,
    },
    rankLastPage: {
      default: 1,
    },
    rankKeyword: {
      type: String,
      default: "",
    },
  },

  data() {
    return {
      pageList: [],
      page: 1,
    };
  },

  watch: {
    rankKeyword: function() {
      this.page = 1;
      this.movePage();
    },
    /*
    rankLastPage: function() {
      console.log("lastPage change");
      this.generatePageList();
    },
    */
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
        this.rankPage - PAGE_COUNT < 1 ? 1 : this.rankPage - PAGE_COUNT;
      const endOffset =
        this.rankPage + PAGE_COUNT > this.rankLastPage
          ? this.rankLastPage
          : this.rankPage + PAGE_COUNT;

      this.pageList = [];
      if (startOffset != 1) {
        this.pageList.push(1);
      }
      let i = startOffset;
      for (; i <= endOffset; i++) {
        this.pageList.push(i);
      }
      if (endOffset != this.rankLastPage) {
        this.pageList.push(this.rankLastPage);
      }
    },
  },
};
</script>
