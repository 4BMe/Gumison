<template>
  <div class="media">
    <div class="chat-user-img online align-self-center mr-3">
      <div v-if="item.imgUrl">
        <img
          :src="require(`@/assets/images/climbing/${item.imgUrl}`)"
          class="rounded avatar-xl"
          alt="climbing-img"
        />
      </div>
      <div class="avatar-xl" v-else>
        <span
          class="avatar-title rounded bg-soft-primary text-primary"
          >{{ item.climbingName }}
        </span>
      </div>
    </div>

    <div class="media-body overflow-hidden">
      <h5 class="text-truncate font-size-15 mb-1">
        {{ item.climbingName }}
      </h5>
      <p>
        {{item.address}}
      </p>
      <p>
        {{item.phoneNumber}}
      </p>
    </div>
  </div>
         
</template>

<script>
export default {
  props: {
    item: {
      type: Object,
      require: true
    }
  },
  data() {
    return {
    };
  },
  mounted() {
  },
  watch: {
  },
  methods: {
  },
};
</script>

<style>
.climbing-img {
  width: 7%;
  height: 7%;
}
</style>
