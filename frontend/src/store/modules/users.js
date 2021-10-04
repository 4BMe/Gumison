export default {
    namespaced: true,
    state: () => ({
        user: {
            nickname: '',
            description: '',
            profile: '',
            tocken: '',
        }
    }),
    mutations: {
        SET_USER(state, payload) {
            state.user.nickname = payload.nickname;
            state.user.description = payload.description;
            state.user.profile = payload.profile;
            state.user.tocken = payload.tocken;
        },
        LOGOUT(state) {
            state.user.nickname = '';
            state.user.description = '';
            state.user.profile = '';
            state.user.tocken = '';
        }
    },
    actions: {


    },
    getters: {
        getUser(state) {
            return state.user;
        }
    },
};
