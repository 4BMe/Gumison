<template>
  <b-modal
    id="modal-withdraw"
    v-model="showModal"
    title="회원 탈퇴"
    hide-footer
  >
    <div
      class="modal-dialog modal-danger modal-dialog-centered modal-"
      role="document"
    >
      <div class="modal-content">
        <div class="modal-body">
          <div class="py-3 text-center">
            <h5>회원님의 정보가 모두 삭제되며 복구할 수 없습니다.</h5>
            <h5 class="text-gradient text-danger mt-4">
              정말 탈퇴하시겠습니까?
            </h5>
          </div>
        </div>
        <div class="modal-footer">
          <b-button
            type="button"
            @click="showModal= false"
          >
            No
          </b-button>
          <b-button
            type="button"
            class="btn bg-danger"
            @click="withdraw"
            data-bs-dismiss="modal"
          >
            Yes
          </b-button>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import { deleteUserByOauthId } from "@/api/users.js";
import store from "@/store";
export default {
  name: "withdrawModal",
  components: {},
  props: {},
  data() {
    return {
      showModal: false,
    };
  },
  computed: {
    oauthId() {
      return store.getters["users/getUser"].oauthId;
    },
  },
  methods: {
    withdraw() {
      deleteUserByOauthId(this.oauthId).then(({ status }) => {
        if (status != 200) {
          this.$alertify.error("탈퇴에 실패했습니다.");
        } else {
          this.$alertify.success("회원탈퇴했습니다.");
          store.commit("users/LOGOUT");
          this.$router.push({ name: "home" });
        }
      });
    },
  },
};
</script>
