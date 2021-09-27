<template>
  <div>
    <div class="px-4 pt-4">
      <h4 class="mb-0">거미손</h4>
    </div>

    <div class="p-4 align-middle">
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
              <b-dropdown-item @click="selectSearchType('user', '사용자')">사용자</b-dropdown-item>
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
  </div>
</template>

<script>
import axios from "axios";
import { BASE_URL } from "@/constant/index"

/**
 * Setting tab component
 */
export default {
  data() {
    return {
      typeName: "클라이밍",
      type:"climbing",
      keyword: ""
    };
  },
  mounted() {},
  methods: {
    selectSearchType(type, typeName){
      this.type = type;
      this.typeName = typeName;
      console.log(type);
    },
    searchKeyword(){
      console.log(this.type + ": " + this.keyword);
      axios
        .get(`${BASE_URL}/${this.type}/${this.keyword}/1`)
        .then(({ data }) => {
          console.log(data);
          // TODO: 목록 창으로 data 전달
        })
        .catch((err) => {
          console.log(err);
        });
    }

  },
};
</script>

<style scoped>
.align-middle{
  position: absolute;
  width: 100%;
  top: 40%;
}
</style>