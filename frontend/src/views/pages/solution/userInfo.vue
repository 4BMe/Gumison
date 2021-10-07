<template>
  <div class="px-2">
    <div class="row">
      <div
        class="col-2 chat-user-img online align-self-center ml-2"
        id="userCard"
        v-if="user!=undefined"
      >
        <div v-if="user.profile">
          <img
            :src="user.profile"
            class="rounded-circle avatar-xs"
            id="profile"
            alt="profile"
          />
        </div>
        <div
          class="avatar-xs"
          v-else
        >
          <span class="avatar-title rounded-circle bg-soft-primary text-primary">{{ user.nickname.charAt(0) }}
          </span>
        </div>
      </div>
      <div class="media-body overflow-hidden">
        <h5 class="text-truncate font-size-15 mb-1">
          <img
            :src="require(`@/assets/images/tier/`+user.tier+`.png`)"
            alt="tier-img"
            id="tier-img"
            class="img-fluid rounded-circle sm-tier-img"
          />
          {{ user.nickname }}
        </h5>
      </div>
    </div>
    <div class="chat-user-img online align-self-center ml-2">
      <p class="chat-user-message text-truncate mb-0">
        {{ user.description }}
      </p>
    </div>
    <div class="mb-0">
      <div class="media">
        <img
          :src="require(`@/assets/images/tier/`+user.tier+`.png`)"
          alt="tier-img"
          id="tier-img"
          class="img-fluid rounded-circle sm-tier-img"
        />
        <h4>
          {{user.tier}}
        </h4>
        <h4 class="float-right">
          {{user.exp}}/{{user.nextExp}}
        </h4>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    user: {
      type: Object,
      require: true,
    },
  },
};
</script>

