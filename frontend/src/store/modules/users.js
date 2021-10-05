export default {
  namespaced: true,
  state: () => ({
    user: {
      nickname: "",
      description: "",
      profile: "",
      oauthId: "",
      tocken: "",
    },
  }),
  mutations: {
    SET_USER(state, payload) {
      state.user.nickname = payload.nickname;
      state.user.description = payload.description;
      state.user.profile = payload.profile;
      state.user.oauthId = payload.oauthId;
      state.user.tocken = payload.tocken;
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
      state.user.tocken = "";
    },
  },
  actions: {},
  getters: {
    getUser(state) {
      console.log("[getter getUser]", state.user);
      return state.user;
    },
    getToken(state) {
      console.log("[getter getToken]", state.user.tocken);
      return state.user.tocken;
    },
    getNickname(state) {
      console.log("[getter getNickname]", state.user.nickname);
      return state.user.nickname;
    },
  },
};
