<template>
  <div>
    <SearchBar
      currName="클라이밍"
      currType="climbinglist"
      :currKeyword="currKeyword"
    />
    <simplebar class="chat-group-list" id="chat-list" ref="current">
      <ul class="list-unstyled chat-list chat-user-list">
        <li
          v-for="(item, index) in climbingList"
          :key="index"
          @click="searchHistory(item.id)"
        >
          <a href="javascript:void(0);">
            <ClimbingCard :item="item" />
          </a>
        </li>
      </ul>
      <infinite-loading @infinite="infiniteHandler" spinner="waveDots">
        <div slot="no-more"></div>
        <div slot="no-results">"{{ keyword }}"을/를 찾을 수 없습니다.</div>
      </infinite-loading>
    </simplebar>
  </div>
</template>

<script>
import axios from "axios";
import { BASE_URL } from "@/constant/index";
import simplebar from "simplebar-vue";
import SearchBar from "./searchBar";
import ClimbingCard from "@/views/pages/climbing/climbingCard";
import InfiniteLoading from "vue-infinite-loading";

export default {
  components: {
    simplebar,
    SearchBar,
    ClimbingCard,
    InfiniteLoading,
  },
  props: {
    keyword: {
      type: String,
      default: " ",
    },
  },
  data() {
    return {
      climbingList: [],
      pageNumber: 0,
      currKeyword: this.keyword,
    };
  },
  created() {
    if(this.currKeyword == ' '){
      this.currKeyword = '';
    }
  },
  methods: {
    searchHistory(id) {
      console.log("climbingList - id : " + id);
      this.$router.push({path:`/climbing/${id}`})
    },

    getList(){
      axios
        .get(`${BASE_URL}/climbing/search/${this.keyword}/${this.pageNumber}`)
        .then(({ data }) => {
          this.climbingList = data.data;
        })
        .catch((err) => {
          console.log("에러: " + err);
        });
    },

    infiniteHandler($state) {
    //  무한 스크롤
      axios
        .get(`${BASE_URL}/climbing/search/${this.keyword}/${this.pageNumber}`)
        .then(({ data }) => {
          // 로딩스피너를 위해 0.1초의 지연시간을 설정했다.
          setTimeout(() => { 
            if(data.data.length) {
              this.climbingList = this.climbingList.concat(data.data)
              this.pageNumber++;
              $state.loaded()
                // 끝 지정(No more data) - 데이터가 1개 미만이면
              if(data.data.length < 1) {  //종료조건
                $state.complete()
              }
            } else {
                // 끝 지정(No more data)
              $state.complete()
            }
          }, 100)
        })
        .catch((err) => {
          console.log("에러: " + err);
        });
    }
  },
};
</script>

<style>
.sm-tier-img {
  width: 7%;
  height: 7%;
}
</style>
