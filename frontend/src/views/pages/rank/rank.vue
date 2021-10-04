<template>
  <div class="height-full">
    <div class="px-4 pt-4">
      <h4 class="mb-0">사용자 랭킹</h4>
    </div>

    <RankSearchBar
      :rankKeyword = "rankKeyword"
      @searchKeyword = "searchKeyword"
    />
    <RankList
      :rankKeyword = "rankKeyword"
      :rankPage = "rankPage"
      >
    </RankList>

  </div>
</template>

<script>
import RankSearchBar from "./components/rank-search-bar"
import RankList from "./components/rank-list.vue"

export default { 
  components: {
    RankSearchBar,
    RankList,
  },
  data(){
    return {
      rankKeyword : "",
      rankPage : 1,
    }
  },

  methods: {
    searchKeyword(searchParams){
      console.log("[rank] search keyword " + searchParams.keyword + ", " + searchParams.page);
      this.rankKeyword = searchParams.keyword;
      this.rankPage = searchParams.page;
    }
  }
};
</script>

<style scoped>
.height-full{
  position:relative;
  height: 100vh;
}

.align-middle{
  position:absolute;
  width: 100%;
  top: 40%;
}
</style>