//import { mapState, mapGetters, mapActions } from 'vuex'
import { mapState, mapGetters } from 'vuex'
export const authComputed = {
    ...mapState('auth', {
        currentUser: (state) => state.currentUser,
    }),
    ...mapGetters('auth', ['loggedIn']),
}

// export const authMethods = mapActions('auth', ['logIn', 'logOut', 'register', 'resetPassword'])

// export const authFackMethods = mapActions('authfack', ['login', 'registeruser', 'logout'])

// export const notificationMethods = mapActions('notification', ['success', 'error', 'clear'])
