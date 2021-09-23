<script>
export default {
    data() {
        return {

        }
    }
}
</script>

<template>
    <div class="account-pages my-5 pt-sm-5">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-8 col-lg-6 col-xl-5">
                    <div class="text-center mb-4">
                        <router-link to="/" class="auth-logo mb-5 d-block">
                            <img src="@/assets/images/logo-dark.png" alt="" height="30" class="logo logo-dark">
                            <img src="@/assets/images/logo-light.png" alt="" height="30" class="logo logo-light">
                        </router-link>

                        <h4>Lock screen</h4>
                        <p class="text-muted mb-4">Enter your password to unlock the screen!</p>
                        
                    </div>

                    <div class="card">
                        <div class="card-body p-4">
                            <div class="p-3">
                                <div class="user-thumb text-center mb-4">
                                    <img src="@/assets/images/users/avatar-1.jpg" class="rounded-circle img-thumbnail avatar-lg" alt="thumbnail">
                                    <h5 class="font-size-15 mt-3">Patricia Smith</h5>
                                </div>
                                <form action="index.html">

                                    <div class="form-group mb-4">
                                        <label>Password</label>
                                        <div class="input-group mb-3 bg-soft-light input-group-lg rounded-lg">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text border-light text-muted">
                                                    <i class="ri-lock-2-line"></i>
                                                </span>
                                            </div>
                                            <input type="password" class="form-control bg-soft-light border-light" placeholder="Enter Password">
                                            
                                        </div>
                                    </div>

                                    <div>
                                        <button class="btn btn-primary btn-block waves-effect waves-light" type="submit">Unlock</button>
                                    </div>

                                </form>
                            </div>
                        </div>
                    </div>

                    <div class="mt-5 text-center">
                        <p>Not you ? return <router-link to="/login" class="font-weight-medium text-primary"> Signin </router-link> </p>
                        <p>© {{new Date().getFullYear()}} Chatvia. Crafted with <i class="mdi mdi-heart text-danger"></i> by Themesbrand</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>