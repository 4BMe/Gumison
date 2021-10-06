<template>
  <div class="height-full">
    <div class="px-4 pt-4">
      <h4 class="mb-0">거미손</h4>
    </div>

    <div class="text-center h-100">
      <img src="@/assets/images/logo.svg"
      height="45%"/>
    </div>

    <SearchBar class="align-middle"
      currType = 'climbinglist'
      currName = '클라이밍'
      currKeyword = ''
    />

  </div>
</template>

<script>
import SearchBar from "./searchBar"

export default { 
  components: {
    SearchBar,
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