<template>
  <div>
    Oauth2 Redirect
    <ChangeProfileModal></ChangeProfileModal>
  </div>
</template>

<script>
import ChangeProfileModal from "./components/change-profile-modal.vue";
export default {
  name: "oauth2-redirect",
  data() {
    return {};
  },
  components: { ChangeProfileModal },
  methods: {},
};
</script>

