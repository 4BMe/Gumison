<template>
  <div class="height-full">
    <div class="px-4 pt-4">
      <h4 class="mb-0">랭킹</h4>
    </div>
    <RankSearchBar
      :rankKeyword="rankKeyword"
      @searchKeyword="searchKeyword"
    />
    <infinite-loading
      v-if="!dataLoaded"
      class="align-middle"
    ></infinite-loading>
    <RankList
      :rankKeyword="rankKeyword"
      :rankPage="rankPage"
      @setLastPage="setLastPage"
      @setDataLoaded="setDataLoaded"
    >
    </RankList>
    <RankPagination
      :rankPage="rankPage"
      :rankKeyword="rankKeyword"
      :rankLastPage="rankLastPage"
      :dataLoaded="dataLoaded"
      @movePage="movePage"
    />
  </div>
</template>

<script>
import RankSearchBar from "./components/rank-search-bar";
import RankList from "./components/rank-list.vue";
import RankPagination from "./components/rank-pagination";
import InfiniteLoading from "vue-infinite-loading";

export default {
  components: {
    RankSearchBar,
    RankList,
    RankPagination,
    InfiniteLoading,
  },
  data() {
    return {
      rankKeyword: "",
      rankPage: 1,
      rankLastPage: 1,
      dataLoaded: false,
    };
  },

  methods: {
    searchKeyword(searchParams) {
      this.rankKeyword = searchParams.keyword;
      this.rankPage = searchParams.page;
    },
    setLastPage(lastPage) {
      this.rankLastPage = lastPage;
    },
    movePage(page) {
      this.rankPage = page;
    },

    setDataLoaded(dataLoaded) {
      this.dataLoaded = dataLoaded;
    },
  },
};
</script>

<style scoped>
.height-full {
  position: relative;
  height: 100vh;
}

.align-middle {
  position: absolute;
  width: 100%;
  top: 40%;
}
</style>
