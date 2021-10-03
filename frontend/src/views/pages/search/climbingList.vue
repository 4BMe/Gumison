<template>
  <div>
    <SearchBar 
      currName = '클라이밍'
      currType = 'climbinglist'
      :currKeyword = 'keyword'
    />
    <div class="px-2">
      <div class="chat-message-list" id="chat-list" ref="current">
        <!-- 해당 keyword에 맞는 클라이밍장이 없을 때 -->
        <ul
          class="list-unstyled chat-list chat-user-list"
          v-if="climbingList.length == 0"
        >
          <li>
            <a href="javascript:void(0);">
              <div class="media">
                <div class="media-body overflow-hidden">
                  <p class="chat-user-message text-truncate mb-0 pt-">
                    "{{ keyword }}"을/를 찾을 수 없습니다.
                  </p>
                </div>
              </div>
            </a>
            <div class="media">
              <div class="chat-user-img online align-self-center mr-3"></div>
            </div>
          </li>
        </ul>

        <!-- keyword에 맞는 클라이밍장 있을 때 -->
        <ul class="list-unstyled chat-list chat-user-list" v-else>
          <li
            v-for="(item, index) in climbingList"
            :key="index"
            @click="searchHistory(item.id)"
          >
            <a href="javascript:void(0);">
              <ClimbingCard 
                :item="item" />
            </a>
          </li>
        </ul>
        <!-- <infinite-loading @infinite="infiniteHandler" spinner="waveDots"></infinite-loading> -->
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { BASE_URL } from "@/constant/index"
import SearchBar from "./searchBar";
import ClimbingCard from "@/views/pages/climbing/climbingCard";
// import InfiniteLoading from "vue-infinite-loading";

export default {
  components: {
    SearchBar,
    ClimbingCard,
    // InfiniteLoading,
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
    };
  },
  created() {
    this.getList();
  },
  watch:{
    $route(to, from) { 
      if (to.name == from.name) {
        this.getList();
      }
    }
  },
  methods: {
    searchHistory(id) {
      console.log(id)
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

    // infiniteHandler($state) {
    //  무한 스크롤
    //   this.pageNumber++;
    //   console.log(this.pageNumber)
    //   axios
    //     .get(`${BASE_URL}/climbing/search/${this.keyword}/${this.pageNumber}`)
    //     .then(({ data }) => {
    //       console.log(data)
    //       setTimeout(() => {  // 로딩스피너를 위해 1초의 지연시간을 설정했다.
    //         if(data.data.length) {
    //           this.climbingList = this.climbingList.concat(data.data)
    //           $state.loaded()
    //             // 끝 지정(No more data) - 데이터가 n개 미만이면 
    //           if(data.data.length / 20 < 1) {  //종료조건
    //             $state.complete()
    //           }
    //         } else {
    //             // 끝 지정(No more data)
    //           $state.complete()
    //         }
    //       }, 1000)
    //     })
    //     .catch((err) => {
    //       console.log("에러: " + err);
    //     });
    // }
  },
};
</script>

<style>
.sm-tier-img {
  width: 7%;
  height: 7%;
}
</style>
