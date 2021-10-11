<template>
  <div class="height-full">
    <div class="px-4 pt-4 d-flex row container-fluid">
      <h4 class="mb-0 col-5">거미손</h4>
      <div class="col-6"></div>
      <a
        class="col p-1 justify-content-end"
        href="https://forms.gle/fpCGG6si42ARSUYf9"
        target="_blank"
      >
        <i class="ri-questionnaire-fill"></i
      ></a>
    </div>

    <div class="text-center h-75">
      <img src="@/assets/images/logo.svg" height="60%" />
    </div>

    <SearchBar
      class="align-middle"
      currType="climbinglist"
      currName="클라이밍"
      currKeyword=""
    />
  </div>
</template>

<script>
import SearchBar from "./searchBar";

export default {
  components: {
    SearchBar,
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
