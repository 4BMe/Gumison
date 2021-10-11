<template>
  <b-modal
    id="modal-delete"
    v-model="showModal"
    title="문제 해결 영상 삭제"
    hide-footer
  >
    <div
      class="modal-dialog modal-danger modal-dialog-centered modal-"
      role="document"
    >
      <div class="modal-content">
        <div class="modal-body">
          <div class="py-3 text-center">
            <h5>영상이 삭제되며 복구할 수 없습니다.</h5>
            <h5 class="text-gradient text-danger mt-4">
              정말 삭제하시겠습니까?
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
            @click="clickDelete"
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
import { BASE_URL } from "@/constant/index";
import axios from "axios";
export default {
  name: "deleteModal",
  components: {},
  props: {
    data: {
      type: Object,
      require: true,
    },
  },
  data() {
    return {
      showModal: false,
    };
  },
  computed: {},
  methods: {
    async clickDelete() {
      await axios
        .delete(`${BASE_URL}/history/${this.data.uploadId}`)
        .then(() => {
          let nickname = this.data.nickname;
          this.$router.push({
            name: "myhistory",
            params: { nickname: nickname },
          });
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>
