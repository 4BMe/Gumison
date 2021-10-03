<template>
    <div class="px-4 pt-4">
        <div class="text-align">
            <h3 class="mb-0 text-center">
                {{ climbing.climbingName }}
            </h3>
        </div>

        <div class="media pt-4 align-items-end">
            <div class="chat-user-img online align-self-center mr-3">
                <div v-if="climbing.imgUrl">
                    <img
                    :src="require(`@/assets/images/climbing/${climbing.imgUrl}`)"
                    class="rounded avatar-xl"
                    alt="climbing-img"
                    />
                </div>
                <div class="avatar-xl" v-else>
                    <span
                    class="avatar-title rounded bg-soft-primary text-primary"
                    >{{ climbing.climbingName }}
                    </span>
                </div>
            </div>

            <div>
                <p>
                    {{climbing.address}}
                </p>
                <p>
                    {{climbing.phoneNumber}}
                </p>

            </div>
        </div>
        <!-- 영업시간 dropdown -->
        <b-card no-body class="border custom-accordion mt-2">
            <b-card-header class="px-4 py-3 border-bottom border-light">
                <a href="javascript: void(0);" v-b-toggle.hours>
                    <h5 class="font-size-15 m-0">
                    영업시간
                    <i class="mdi mdi-chevron-up float-right accor-plus-icon"></i>
                    </h5>
                </a>
            </b-card-header>
            <b-collapse id="hours" accordion="accordion">
                <b-card-body>
                    <div 
                        v-for="(hour, index) in hours"
                        :key="index"
                    >
                        <p class="text-muted mb-1">
                            {{ hour.day }} &nbsp;  {{ hour.startTime }} ~ {{ hour.endTime }}
                        </p>
                    </div>
                </b-card-body>
            </b-collapse>
        </b-card>
    </div>
</template>

<script>

export default {
    components:{
    },
    props:{
        climbing:{
            type: Object,
            require: true,
        },
        hours:{
            type: Array,
            require: true,
        }
    },
    mounted() {
        
    },

}
</script>

<style scoped>
</style>