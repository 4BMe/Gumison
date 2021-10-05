export default {
    namespaced: true,
    state: () => ({
        user: {
            nickname: '',
            description: '',
            profile: '',
            oauthId: '',
            token: '',
        }
    }),
    mutations: {
        SET_USER(state, payload) {
            state.user.nickname = payload.nickname;
            state.user.description = payload.description;
            state.user.profile = payload.profile;
            state.user.oauthId = payload.oauthId;
            state.user.token = payload.token;
        },
        UPDATE_USER(state, payload) {
            state.user.nickname = payload.nickname;
            state.user.description = payload.description;
            state.user.profile = payload.profile;
            state.user.oauthId = payload.oauthId;
        },
        LOGOUT(state) {
            state.user.nickname = '';
            state.user.description = '';
            state.user.profile = '';
            state.user.oauthId = '';
            state.user.token = '';
        }
    },
    actions: {


    },
    getters: {
        getUser(state) {
            console.log('[getter getUser]', state.user);
            return state.user;
        },
        getToken(state) {
            console.log('[getter getToken]', state.user.token);
            return state.user.token;
        }
    },
};
