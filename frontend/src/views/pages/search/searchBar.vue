<template>
    <div class="p-4">
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
            <b-dropdown-item @click="selectSearchType('climbinglist', '클라이밍')">클라이밍</b-dropdown-item>
            <b-dropdown-item @click="selectSearchType('userlist', '사용자')">사용자</b-dropdown-item>
          </b-dropdown>
          <input
            type="text"
            class="form-control bg-light pl-0"
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
</template>

<script>
export default { 
  props:{
    currKeyword: {
      type: String,
      default: ''
    },
    currType: {
      type: String,
      require: true,
    },
    currName:{
      type: String,
      require: true,
    }
  },
  data() {
    return {
      type: this.currType,
      typeName: this.currName,
      keyword: this.currKeyword
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
        this.keyword = ' '
      }

      this.$router.push({ name: this.type, params: {keyword: this.keyword}}).catch({});
    }

  },
};
</script>

<style scoped>
.align-middle{
  position:absolute;
  width: 100%;
  top: 40%;
}
</style>