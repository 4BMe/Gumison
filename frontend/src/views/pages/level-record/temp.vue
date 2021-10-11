<template>
    <div class="height-full">
        <div class="d-flex justify-content-center align-items-center border">
            <input type="button" value="등록!!!!!" class="btn btn-outline-primary" @click="clickButton()">
            <input type="button" value="수정@@@@@" class="btn btn-outline-warning" @click="clickUpdate()">
            <input type="button" value="기여~~~~~" class="btn btn-outline-success" @click="clickContribution()">
        </div>
    </div>
</template>

<script>
// var today = new Date().toISOString().slice(0,10);

export default {
    data(){
        return{
            userId: 2,
            climbingId: 3,
            levelTiers: [
                {
                    id: 19,
                    level: '흰색',
                    tier: 'Bronze4',
                },
                {
                    id: 20,
                    level: '노랑',
                    tier: 'Bronze2',
                },
                {
                    id: 21,
                    level: '주황',
                    tier: 'Silver4',
                },
                {
                    id: 22,
                    level: '초록',
                    tier: 'Silver1',
                },
                {
                    id: 23,
                    level: '파랑',
                    tier: 'Gold3',
                },
                {
                    id: 24,
                    level: '빨강',
                    tier: 'Platinum4',
                },
                {
                    id: 25,
                    level: '보라',
                    tier: 'Platinum1',
                },
                {
                    id: 26,
                    level: '회색',
                    tier: 'Diamond2',
                },
                {
                    id: 27,
                    level: '갈색',
                    tier: 'Master4',
                },
                {
                    id: 28,
                    level: '검정',
                    tier: 'Master1',
                },
            ],
            solutionIds: [],
            levelTiersUpdate: [
                {
                    id: 22,
                    solutionId: 60,
                    level: '초록',
                    tier: 'Silver1',
                    solutionCount: 3,
                    solutionDate: '2021-10-01'
                },
                {
                    id: 23,
                    solutionId: 61,
                    level: '초록',
                    tier: 'Silver1',
                    solutionCount: 2,
                    solutionDate: '2021-10-01'
                },
                {
                    id: 24,
                    solutionId: 62,
                    level: '초록',
                    tier: 'Silver1',
                    solutionCount: 5,
                    solutionDate: '2021-10-01'
                },
            ],
            solutionVideos: [
                
            ]
        }
    },
    methods: {
        clickButton() {
            this.$router.push({
                name: 'level-record',
                params: {
                    userId: this.userId,
                    climbingId: this.climbingId,
                    levelTiers: this.levelTiers,
                }
            })
        },
        clickUpdate() {
            this.$router.push({
                name: 'level-record',
                params: {
                    userId: this.userId,
                    climbingId: this.climbingId,
                    solutionIds: [],
                    levelTiers: this.levelTiersUpdate,
                }
            })
        },
        clickContribution(){
            this.$router.push({
                name: 'level-contribution',
                params: {
                    userId: this.userId,
                    climbingId: this.climbingId,
                    levelTiers: this.levelTiersUpdate,
                }
            })
        }
    }
}
</script>

<style>

</style>