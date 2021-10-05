export default {
    namespaced: true,
    state: () => ({
        activeTab: 1,
    }),
    mutations: {
        SET_ACTIVE_TAB(state, activeTab) {
            state.activeTab = activeTab;
        },
    },
    getters: {
        getActiveTab(state) {
            console.log('[getters getActiveTab]', state.activeTab);
            return state.activeTab;
        },
    },
};
