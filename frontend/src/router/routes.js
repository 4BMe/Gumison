import store from '@/store'

export default [
{
    path: '/',
    name: 'home',
    component: () => import('../views/pages/search/search')
},
{
    path: '/temp',
    name: 'temp',
    component: () => import('../views/pages/level-record/temp'),
},
{
    path: '/myhistory',
    name: 'myhistory',
    component: () => import('../views/pages/history/myhistory'),
    meta: {
        beforeResolve(routeTo, routeFrom, next) {
            /**
             * token이 이미 있으면 myhistory 로 가고,
             * 없으면 로그인 화면으로 가기
             */
            const token = store.getters['users/getToken'];
            console.log('[route myhistory click] token: ', token)
            if (token) {
                console.log('[route myhistory click] token token : ', token)
                next()
            } else {
                next({
                    name: 'login'
                })
            }
        },
    },
},
{
    path: '/userlist/:keyword',
    name: 'userlist',
    component: () => import('../views/pages/search/userlist'),
    props: true,
},
{
    path: '/climbinglist/:keyword',
    name: 'climbinglist',
    component: () => import('../views/pages/search/climbingList'),
    props: true,
},
{
    path: '/climbing/:climbingId',
    name: 'climbing',
    component: () => import('../views/pages/climbing/climbing'),
    props: true,
    children:[
        {
            path: '',
            name: 'level',
            component: () => import('../views/pages/climbing/level'),
        },
        {
            path: ':solutionId',
            name: 'climbingSolution',
            component: () => import('../views/pages/climbing/solution/climbingSolution'),
            props: '',
        },
        {
            path: '/level-record',
            name: 'level-record',
            component: () => import('../views/pages/level-record/level-record'),
            meta: {
                beforeResolve(routeTo, routeFrom, next) {
                    /**
                     * token이 이미 있으면 myhistory 로 가고,
                     * 없으면 로그인 화면으로 가기
                     */
                    const token = store.getters['users/getToken'];
                    console.log('[route myhistory click] token: ', token)
                    if (token) {
                        console.log('[route myhistory click] token token : ', token)
                        next()
                    } else {
                        next({
                            name: 'login'
                        })
                    }
                },
            },
            props: true,
        },
        {
            path: '/level-contribution',
            name: 'level-contribution',
            component: () => import('../views/pages/level-contribution/level-contribution'),
            meta: {
                beforeResolve(routeTo, routeFrom, next) {
                    /**
                     * token이 이미 있으면 myhistory 로 가고,
                     * 없으면 로그인 화면으로 가기
                     */
                    const token = store.getters['users/getToken'];
                    console.log('[route myhistory click] token: ', token)
                    if (token) {
                        console.log('[route myhistory click] token token : ', token)
                        next()
                    } else {
                        next({
                            name: 'login'
                        })
                    }
                },
            },
            props: true,
        },
    ]
},
{
    path: '/login',
    name: 'login',
    component: () => import('../views/pages/account/login'),
    meta: {
        beforeResolve(routeTo, routeFrom, next) {

            const user = store.getters['users/getUser'];
            console.log('[router profile] user: ', user);
            if (user.token) {
                // If the user is already logged in
                next({
                    name: 'home'
                })
            } else {
                // Continue to the login page
                next()
            }
        },
    },
    props: true,
},

{
    path: '/oauth2/redirect',
    name: 'oauth2-redirect',
    component: () => import('../views/pages/account/oauth2-redirect'),

},

{
    path: '/change-profile',
    name: 'change-profile',
    component: () => import('../views/pages/account/components/change-profile-item'),

},

{
    path: '/logout',
    name: 'logout',
    meta: {
        authRequired: true,
        beforeResolve(routeTo, routeFrom, next) {
            store.commit('users/LOGOUT')
            const authRequiredOnPreviousRoute = routeFrom.matched.some(
                (route) => route.push('/login')
            )
            // Navigate back to previous page, or home as a fallback
            next(authRequiredOnPreviousRoute ? {
                name: 'home'
            } : {
                ...routeFrom
            })
        },
    },
},
// {
//     path: '/myhistory',
//     name: 'myhistory',
//     component: () => import('../views/pages/history/myhistory'),
//     props: true,
// },
{
    path: '/solution',
    name: 'solution',
    component: () => import('../views/pages/solution/solution'),
    props: true,
},
{
    path: '/rank-list',
    name: 'rank-list',
    component: () => import('../views/pages/rank/rank'),
},
]