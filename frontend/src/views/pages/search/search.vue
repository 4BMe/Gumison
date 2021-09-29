<template>
  <div class="height-full">
    <div class="px-4 pt-4">
      <h4 class="mb-0">거미손</h4>
    </div>

    <div class="p-4" v-bind:class="[searchBar]">
      <div class="search-box chat-search-box">
        <div class="input-group bg-light input-group-lg rounded-lg">
          <b-dropdown
              variant="white-outline"
              toggle-class="btn-sm dropdown-toggle w-sm"
            >
              <template v-slot:button-content>
                {{ typeName }}
                <i class="mdi mdi-chevron-down"></i>
              </template>
              <b-dropdown-item @click="selectSearchType('climbing', '클라이밍')">클라이밍</b-dropdown-item>
              <b-dropdown-item @click="selectSearchType('users', '사용자')">사용자</b-dropdown-item>
            </b-dropdown>
          <input
            type="text"
            class="form-control bg-light"
            placeholder="검색어를 입력하세요"
            v-model="keyword"
            @keyup.enter="searchKeyword()"
          />
          <div class="input-group-prepend">
            
            <button
              class="btn btn-link text-decoration-none text-muted"
              type="button"
              @click="searchKeyword()"
            >
              <i class="ri-search-line search-icon font-size-18"></i>
            </button>
          </div>
        </div>
      </div>
      <!-- end search-box -->
    </div>

    <UserList v-show="showUserList"
       :userList = "list"
       :word = "word" />
  </div>
</template>

<script>
import axios from "axios";
import { BASE_URL } from "@/constant/index"
import UserList from "./userlist"

/**
 * Setting tab component
 */
export default { 
  components: {
    UserList
  },
  data() {
    return {
      typeName: "클라이밍",
      type:"climbing",
      keyword: "",
      searchBar: "align-middle",
      pageNumber: 0,
      showUserList: false,
      showClimbingList: "none",
      list: [],
      word: "",
    };
  },
  mounted() {},
  methods: {
    selectSearchType(type, typeName){
      this.type = type;
      this.typeName = typeName;
    },
    searchKeyword(){
      if(!this.keyword){
        this.keyword = " ";
      }
      axios
        .get(`${BASE_URL}/${this.type}/search/${this.keyword}/${this.pageNumber}`)
        .then(({ data }) => {
          this.list = data.data.users;
          this.searchBar = "";
          this.word = this.keyword;
          if(this.type == "users"){
            this.showClimbingList = false;
            this.showUserList = true;

          }else{
            this.showClimbingList = true;
            this.showUserList = false;
          }

        })
        .catch((err) => {
          console.log("에러: " + err);
        });
    }

  },
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