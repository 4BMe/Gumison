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
      user: { username: "", email: "", password: "" },
      submitted: false,
    };
  },
  computed: {
    notification() {
      return this.$store ? this.$store.state.notification : null;
    },
    notificationAutoCloseDuration() {
      return this.$store && this.$store.state.notification ? 5 : 0;
    },
  },
  validations: {
    user: {
      username: { required },
      email: { required, email },
      password: { required },
    },
  },
  methods: {
    ...authMethods,
    ...authFackMethods,
    ...notificationMethods,
    // Try to register the user in with the email, username
    // and password they provided.
    tryToRegisterIn() {
      this.submitted = true;
      // stop here if form is invalid
      this.$v.$touch();

      if (this.$v.$invalid) {
        return;
      } else {
        if (process.env.VUE_APP_DEFAULT_AUTH === "firebase") {
          this.tryingToRegister = true;
          // Reset the regError if it existed.
          this.regError = null;
          return (
            this.register({
              email: this.user.email,
              password: this.user.password,
            })
              // eslint-disable-next-line no-unused-vars
              .then((token) => {
                this.tryingToRegister = false;
                this.isRegisterError = false;
                this.registerSuccess = true;
                if (this.registerSuccess) {
                  this.$router.push(
                    this.$route.query.redirectFrom || { name: "home" }
                  );
                }
              })
              .catch((error) => {
                this.tryingToRegister = false;
                this.regError = error ? error : "";
                this.isRegisterError = true;
              })
          );
        } else if (process.env.VUE_APP_DEFAULT_AUTH === "fakebackend") {
          const { email, username, password } = this.user;
          if (email && username && password) {
            this.registeruser(this.user);
          }
        }
      }
    },
  },
};
</script>

<template>
  <div class="account-pages my-5 pt-sm-5">
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6 col-xl-5">
          <div class="text-center mb-4">
            <router-link to="/" class="auth-logo mb-5 d-block">
              <img src="@/assets/images/logo-dark.png" alt height="30" class="logo logo-dark" />
              <img src="@/assets/images/logo-light.png" alt height="30" class="logo logo-light" />
            </router-link>

            <h4>{{ $t('register.title') }}</h4>
            <p class="text-muted mb-4">{{ $t('register.message') }}</p>
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
                <form @submit.prevent="tryToRegisterIn">
                  <div class="form-group">
                    <label>{{ $t('register.form.username.label') }}</label>
                    <div class="input-group mb-3 bg-soft-light input-group-lg rounded-lg">
                      <div class="input-group-prepend">
                        <span class="input-group-text border-light text-muted">
                          <i class="ri-user-2-line"></i>
                        </span>
                      </div>
                      <input
                        v-model="user.username"
                        type="text"
                        class="form-control bg-soft-light border-light"
                        :placeholder="$t('register.form.username.placeholder')"
                        :class="{ 'is-invalid': submitted && $v.user.username.$error }"
                      />
                      <div
                        v-if="submitted && !$v.user.username.required"
                        class="invalid-feedback"
                      >{{ $t('register.form.username.validation.required') }}</div>
                    </div>
                  </div>

                  <div class="form-group">
                    <label>{{ $t('register.form.email.label') }}</label>
                    <div class="input-group mb-3 bg-soft-light input-group-lg rounded-lg">
                      <div class="input-group-prepend">
                        <span class="input-group-text border-light text-muted">
                          <i class="ri-mail-line"></i>
                        </span>
                      </div>
                      <input
                        v-model="user.email"
                        type="email"
                        class="form-control bg-soft-light border-light"
                        :placeholder="$t('register.form.email.placeholder')"
                        :class="{ 'is-invalid': submitted && $v.user.email.$error }"
                      />
                      <div v-if="submitted && $v.user.email.$error" class="invalid-feedback">
                        <span
                          v-if="!$v.user.email.required"
                        >{{ $t('register.form.email.validation.required') }}</span>
                        <span
                          v-if="!$v.user.email.email"
                        >{{ $t('register.form.email.validation.invalid') }}</span>
                      </div>
                    </div>
                  </div>

                  <div class="form-group mb-4">
                    <label>{{$t('register.form.password.label')}}</label>
                    <div class="input-group mb-3 bg-soft-light input-group-lg rounded-lg">
                      <div class="input-group-prepend">
                        <span class="input-group-text border-light text-muted">
                          <i class="ri-lock-2-line"></i>
                        </span>
                      </div>
                      <input
                        v-model="user.password"
                        type="password"
                        class="form-control bg-soft-light border-light"
                        :placeholder="$t('register.form.password.placeholder')"
                        :class="{ 'is-invalid': submitted && $v.user.password.$error }"
                      />
                      <div
                        v-if="submitted && !$v.user.password.required"
                        class="invalid-feedback"
                      >{{ $t('register.form.password.validation.required') }}</div>
                    </div>
                  </div>

                  <div>
                    <button
                      class="btn btn-primary btn-block"
                      type="submit"
                    >{{ $t('register.form.button.text') }}</button>
                  </div>
                </form>
              </div>
            </div>
          </div>

          <div class="mt-5 text-center">
            <p>
              {{ $t('register.form.signintext') }}
              <router-link
                tag="a"
                to="/login"
                class="font-weight-medium text-primary"
              >{{ $t('register.form.signinlink') }}</router-link>
            </p>
            <p>
              © {{new Date().getFullYear() }} {{ $t('footer.name') }}
              <i
                class="mdi mdi-heart text-danger"
              ></i>
              {{ $t('footer.text') }}
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>