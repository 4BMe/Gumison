<template>
  <div class="account-pages my-5 pt-sm-5">
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6 col-xl-5">
          <div class="text-center mb-4">
            <router-link to="/" class="auth-logo mb-5 d-block">
              <!-- <img src="@/assets/images/logo-dark.png" alt height="30" class="logo logo-dark" />
              <img src="@/assets/images/logo-light.png" alt height="30" class="logo logo-light" /> -->
               <img src="@/assets/images/logo-gumison.png" alt height="80" class="logo" />
            </router-link>

            <!-- <h4>{{ $t('login.title') }}</h4> -->
            <h4>소셜 로그인</h4>
            <!-- <p class="text-muted mb-4">{{ $t('login.message') }}</p> -->
            <p class="text-muted mb-4">거미손을 계속 이용하시려면 로그인해주세요.</p>
          </div>
          <div class="card">
            <div class="card-body p-4">
              <b-alert
                :variant="notification.type"
                class="mt-3"
                v-if="notification.message"
                :show="notificationAutoCloseDuration"
                dismissible
              >{{notification.message}}</b-alert>
              <div class="p-3">
                 <div class="form-group">
                  <!-- 구글 로그인 -->
                   <button
                      class="btn btn-info btn-block"
                      type="submit"
                      v-on:click="tryToLogIn"
                    > <img src="@/assets/images/logo-google-thumbnail.png" alt height="30" class="logo" style="margin-right:10%" />구글 로그인</button>
                  <!-- 카카오 로그인 -->
                   <button
                      class="btn btn-warning btn-block"
                      type="submit"
                    ><img src="@/assets/images/logo-kakao-thumbnail.png" alt height="30" class="logo" style="margin-right:10%"/>카카오 로그인</button>
                 </div>
               
               

                <!-- <form @submit.prevent="tryToLogIn">
                  <div class="form-group">
                    <label>{{ $t('login.form.email.label') }}</label>
                    <div class="input-group mb-3 bg-soft-light input-group-lg rounded-lg">
                      <div class="input-group-prepend">
                        <span class="input-group-text border-light text-muted">
                          <i class="ri-mail-line"></i>
                        </span>
                      </div>
                      <input
                        v-model="email"
                        type="email"
                        class="form-control bg-soft-light border-light"
                        :class="{ 'is-invalid': submitted && $v.email.$error }"
                        :placeholder="$t('login.form.email.placeholder')"
                      />
                      <div v-if="submitted && $v.email.$error" class="invalid-feedback">
                        <span
                          v-if="!$v.email.required"
                        >{{ $t('login.form.email.validation.required') }}</span>
                        <span v-if="!$v.email.email">{{ $t('login.form.email.validation.invalid') }}</span>
                      </div>
                    </div>
                  </div>

                  <div class="form-group mb-4">
                    <div class="float-right">
                      <router-link
                        tag="a"
                        to="/forgot-password"
                        class="text-muted font-size-13"
                      >{{ $t('login.form.forgotlink') }}</router-link>
                    </div>
                    <label>{{$t('login.form.password.label')}}</label>
                    <div class="input-group mb-3 bg-soft-light input-group-lg rounded-lg">
                      <div class="input-group-prepend">
                        <span class="input-group-text border-light text-muted">
                          <i class="ri-lock-2-line"></i>
                        </span>
                      </div>
                      <input
                        v-model="password"
                        type="password"
                        class="form-control bg-soft-light border-light"
                        :placeholder="$t('login.form.password.placeholder')"
                        :class="{ 'is-invalid': submitted && $v.password.$error }"
                      />
                      <div
                        v-if="submitted && !$v.password.required"
                        class="invalid-feedback"
                      >{{ $t('login.form.password.validation.required') }}</div>
                    </div>
                  </div>

                  <div>
                    <button
                      class="btn btn-primary btn-block"
                      type="submit"
                    >{{ $t('login.form.button.text') }}</button>
                  </div>
                </form> -->
              </div>
            </div>
            
          </div>

          <!-- <div class="mt-5 text-center">
            <p>
              {{ $t('login.signuptext')}}
              <router-link
                tag="a"
                to="/register"
                class="font-weight-medium text-primary"
              >{{ $t('login.signuplink')}}</router-link>
            </p>
            <p>
              © {{new Date().getFullYear() }} {{ $t('footer.name') }}
              <i
                class="mdi mdi-heart text-danger"
              ></i>
              {{ $t('footer.text') }}
            </p>
          </div> -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { required, email } from "vuelidate/lib/validators";
import {
  authMethods,
  authFackMethods,
  notificationMethods,
} from "@/state/helpers";

/**
 * Register component
 */
export default {
  data() {
    return {
      email: "chatvia@themesbrand.com",
      password: "123456",
      submitted: false,
    };
  },
  validations: {
    email: { required, email },
    password: { required },
  },
  computed: {
    notification() {
      return this.$store ? this.$store.state.notification : null;
    },
    notificationAutoCloseDuration() {
      return this.$store && this.$store.state.notification ? 5 : 0;
    },
  },
  methods: {
    ...authMethods,
    ...authFackMethods,
    ...notificationMethods,
    // Try to log the user in with the username
    // and password they provided.
    tryToLogIn() {
      this.submitted = true;
      // stop here if form is invalid
      this.$v.$touch();

      if (this.$v.$invalid) {
        return;
      } else {
        if (process.env.VUE_APP_DEFAULT_AUTH === "firebase") {
          this.tryingToLogIn = true;
          // Reset the authError if it existed.
          this.authError = null;
          return (
            this.logIn({
              email: this.email,
              password: this.password,
            })
              // eslint-disable-next-line no-unused-vars
              .then((token) => {
                this.tryingToLogIn = false;
                this.isAuthError = false;
                // Redirect to the originally requested page, or to the home page
                this.$router.push(
                  this.$route.query.redirectFrom || { name: "default" }
                );
              })
              .catch((error) => {
                this.tryingToLogIn = false;
                this.authError = error ? error : "";
                this.isAuthError = true;
              })
          );
        } else if (process.env.VUE_APP_DEFAULT_AUTH === "fakebackend") {
          const { email, password } = this;
          if (email && password) {
            this.login({ email, password });
          }
        }
      }
    },
  },
};
</script>

