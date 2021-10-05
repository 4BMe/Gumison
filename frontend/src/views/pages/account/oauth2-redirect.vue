<template>
  <div>
    로그인중입니다.
    <div></div>
  </div>

</template>

<script>
import { getUser } from "@/api/users.js";
import store from "@/store";
export default {
  name: "oauth2-redirect",
  data() {
    return {};
  },
  components: {},
  methods: {},
  mounted() {
    const url = this.$route.fullPath;
    const token = url.split("token=")[1];

    if (token) {
      getUser()
        .then(({ status, data }) => {
          if (status == 200) {
            let user = {
              nickname: data.data.nickname,
              description: data.data.description,
              profile: data.data.profile,
              oauthId: data.data.oauthId,
              token: token,
            };
            console.log("[setUser] user: ", user);
            store.commit("users/SET_USER", user);
            if (!user.nickname || !user.description) {
              this.$router.push({ name: "change-profile" });
            } else {
              this.$router.push({ name: "home" });
            }
          }
        })
        .catch((error) => {
          console.log(error);
        });
    }
  },
};
</script>

