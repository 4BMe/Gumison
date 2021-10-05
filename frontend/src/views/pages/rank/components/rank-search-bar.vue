<template>
  <div class="p-4 ">
    <div class="search-box chat-search-box">
      <div class="input-group bg-light input-group-lg rounded-lg ">
        <input
          type="text"
          class="form-control bg-light pl-0 ml-3"
          placeholder="닉네임을 입력하세요"
          v-model="keyword"
          @keyup.enter="searchUserRank()"
        />

        <div class="input-group-prepend">
          <button
            class="btn btn-link text-decoration-none text-muted"
            type="button"
            @click="searchUserRank()"
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
  props: {
    rankKeyword: {
      type: String,
      default: "",
    },
  },

  data() {
    return {
      keyword: "",
    };
  },
  methods: {
    searchUserRank() {
      this.$emit("searchKeyword", {
        keyword: this.keyword,
        page: 1,
      });
    },
  },
};
</script>

<style scoped>
.align-middle {
  position: absolute;
  width: 100%;
  top: 40%;
}
</style>
