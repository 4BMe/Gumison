export default {
  namespaced: true,
  state: () => ({
    user: {
      nickname: "",
      description: "",
      profile: "",
      oauthId: "",
      token: "",
    },
    isValidNickname: '',
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
      state.user.nickname = "";
      state.user.description = "";
      state.user.profile = "";
      state.user.oauthId = "";
      state.user.token = "";
    },
    SET_IS_VALID_NICKNAME(state, payload) {
      state.isValidNickname = payload;
    }
  },
  actions: {},
  getters: {
    getUser(state) {
      return state.user;
    },
    getToken(state) {
      return state.user.token;
    },

    getNickname(state) {
      return state.user.nickname;
    },
    getIsValidNickname(state) {
      return state.isValidNickname;
    }
  },
};
