import Vue from 'vue';
import Vuex from 'vuex';
import users from './modules/users.js';
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex);

const store = new Vuex.Store({
    state: {},
    mutations: {},
    actions: {},
    getters: {},
    modules: {
        users,
    },
    plugins: [
        createPersistedState({
            paths: ['users'],
        }),
    ],
});

export default store;
