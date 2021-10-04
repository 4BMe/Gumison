<template>
  <div>
    <ClimbingCard 
        :item="climbingInfo" />

    <router-view></router-view>
  </div>
</template>

<script>
import axios from "axios";
import { BASE_URL } from "@/constant/index"
import ClimbingCard from "@/views/pages/climbing/climbingCard";

export default {
  name: "climbing",
  components: {
    ClimbingCard,
  },
  props: {
    id:{
        require: true,
    }
  },
  data() {
      return {
          climbingInfo: {},
      }
  },
  mounted() {
    console.log(this.id)
    // 클라이밍장 영업시간까지 가져오기
    axios
      .get(`${BASE_URL}/climbing/detail/${this.id}`)
      .then(({ data }) => {
        this.climbingInfo = data.data;
        console.log(data)
      })
      .catch((err) => {
        console.log("에러: " + err);
      });
  },
};
</script>

<style></style>
