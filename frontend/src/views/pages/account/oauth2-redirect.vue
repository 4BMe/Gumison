<template>
  <div>
    Oauth2 Redirect
    <div></div>
    <ChangeProfileModal></ChangeProfileModal>
  </div>

</template>

<script>
import ChangeProfileModal from "./components/change-profile-modal.vue";
import { getUser } from "@/api/users.js";
import store from "@/store";
export default {
  name: "oauth2-redirect",
  data() {
    return {};
  },
  components: { ChangeProfileModal },
  methods: {},
  mounted() {
    const url = this.$route.fullPath;
    const token = url.split("token=")[1];
    console.log("url: ", url);
    console.log("token: ", token);

    if (token) {
      getUser()
        .then(({ status, data }) => {
          console.log("로그인 정보 가져오기: ", status);
          console.log("로그인 정보 가져오기: ", data);
          if (status == 200) {
            let user = {
              nickname: data.data.nickname,
              description: data.data.description,
              profile: data.data.profile,
              tocken: token,
            };

            if (!user.nickname || !user.description) {
              console.log(
                "data.nickname, data.description",
                user.nickname,
                user.description
              );

              // this.$router.push({ name: "change-profile" });
            } else {
              store.commit("users/SET_USER", user);
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

