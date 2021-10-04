<template>
  <div class="account-pages my-5 pt-sm-5">
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6 col-xl-5">
          <div class="text-center mb-4">
            <router-link
              to="/"
              class="auth-logo mb-5 d-block"
            >
              <img
                src="@/assets/images/logo-gumison.png"
                alt
                height="80"
                class="logo"
              />
            </router-link>
            <h4>소셜 로그인</h4>
            <p class="text-muted mb-4">거미손을 계속 이용하시려면 로그인해주세요.</p>
          </div>
          <div class="card">
            <div class="card-body p-4">
              <div class="p-3">
                <div class="form-group">
                  <!-- 구글 로그인 -->
                  <button
                    class="btn btn-info btn-block"
                    type="submit"
                    v-on:click="tryToGoogleLogIn"
                  > <img
                      src="@/assets/images/logo-google-thumbnail.png"
                      alt
                      height="30"
                      class="logo"
                      style="margin-right:10%"
                    />구글 로그인</button>
                  <!-- 카카오 로그인 -->
                  <button
                    class="btn btn-warning btn-block"
                    type="submit"
                  ><img
                      src="@/assets/images/logo-kakao-thumbnail.png"
                      alt
                      height="30"
                      class="logo"
                      style="margin-right:10%"
                    />카카오 로그인</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { googleLoginUser } from "@/api/users.js";
export default {
  data() {
    return {};
  },
  computed: {},

  methods: {
    async tryToGoogleLogIn() {
      try {
        await googleLoginUser();
      } catch (error) {
        console.log(error);
      }
    },
  },
};
</script>

