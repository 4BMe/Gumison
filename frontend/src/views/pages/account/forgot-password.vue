<script>
import { authMethods } from "@/state/helpers";
import { required, email } from "vuelidate/lib/validators";

/**
 * Forgot password component
 */
export default {
  page: {
    title: "Forgot Password",
  },
  data() {
    return {
      email: "",
      submitted: false,
      error: null,
      tryingToReset: false,
      isResetError: false,
    };
  },
  validations: {
    email: { required, email },
  },
  methods: {
    ...authMethods,
    // Try to register the user in with the email, fullname
    // and password they provided.
    tryToReset() {
      this.submitted = true;
      // stop here if form is invalid
      this.$v.$touch();

      if (this.$v.$invalid) {
        return;
      } else {
        if (process.env.VUE_APP_DEFAULT_AUTH === "firebase") {
          this.tryingToReset = true;
          // Reset the authError if it existed.
          this.error = null;
          return (
            this.resetPassword({
              email: this.email,
            })
              // eslint-disable-next-line no-unused-vars
              .then((token) => {
                this.tryingToReset = false;
                this.isResetError = false;
              })
              .catch((error) => {
                this.tryingToReset = false;
                this.error = error ? error.error : "";
                this.isResetError = true;
              })
          );
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

            <h4>{{ $t('resetpassword.title')}}</h4>
            <p class="text-muted mb-4">{{ $t('resetpassword.message')}}</p>
          </div>

          <div class="card">
            <div class="card-body p-4">
              <div class="p-3">
                <b-alert v-model="isResetError" class="mb-4" variant="danger" dismissible>{{error}}</b-alert>

                <form action="" @submit.prevent="tryToReset">
                  <div class="form-group mb-4">
                    <label>{{ $t('resetpassword.form.email.label')}}</label>
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
                        :placeholder="$t('resetpassword.form.email.placeholder')"
                      />
                      <div v-if="submitted && $v.email.$error" class="invalid-feedback">
                        <span
                          v-if="!$v.email.required"
                        >{{ $t('resetpassword.form.email.validation.required') }}</span>
                        <span
                          v-if="!$v.email.email"
                        >{{ $t('resetpassword.form.email.validation.invalid') }}</span>
                      </div>
                    </div>
                  </div>

                  <div>
                    <button
                      class="btn btn-primary btn-block"
                      type="submit"
                    >{{ $t('resetpassword.form.button.text')}}</button>
                  </div>
                </form>
              </div>
            </div>
          </div>

          <div class="mt-5 text-center">
            <p>
              {{ $t('resetpassword.signintext') }}
              <router-link
                tag="a"
                to="/login"
                class="font-weight-medium text-primary"
              >{{ $t('resetpassword.signinlink') }}</router-link>
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