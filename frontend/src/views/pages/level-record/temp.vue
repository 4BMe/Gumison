<template>
    <div class="container">
        <div class="d-flex justify-content-center align-items-center border">
            <input type="button" value="클릭!!!!!" class="btn btn-outline-primary" @click="clickButton()">
        </div>
    </div>
</template>

<script>
export default {
    data(){
        return{
            levelTierIds: [19, 20, 21, 22, 23, 24, 25, 26, 27],
            levels: ['흰색', '노랑', '주황', '초록', '파랑', '빨강', '보라', '회색', '갈색', '검정'],
            colors: ['white', 'yellow', 'orange', 'green', 'blue', 'red', 'purple', 'grey', 'brown', 'black'],
            climbingId: 3,
            userId: 2
        }
    },
    methods: {
        clickButton() {
            this.$router.push({
                name: 'level-record',
                params: {
                    userId: this.userId,
                    levelTierIds: this.levelTierIds,
                    levels: this.levels,
                    colors: this.colors,
                    climbingId: this.climbingId
                }
                })
        }
    }
}
</script>

<style>

</style>