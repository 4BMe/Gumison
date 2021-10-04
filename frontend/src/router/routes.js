import store from '@/store'

export default [{
    path: '/',
    name: 'home',
    component: () => import('../views/pages/search/search')
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
    path: '/climbing/:id',
    name: 'climbing',
    component: () => import('../views/pages/climbing/climbing'),
    props: true,
    // children:[
    //     {
    //         path: '',
    //         name: 'level',
    //         component: () => import('../views/pages/climbing/level'),
    //     },

    // ]
},
{
    path: '/profile',
    name: 'profile',
    component: () => import('../views/pages/account/profile'),
    meta: {
        beforeResolve(routeTo, routeFrom, next) {
            /**
             * tocken이 이미 있으면 profile로 가고,
             * 없으면 로그인 화면으로 가기
             */
            const user = store.getters['users/getUser'];
            console.log('[router profile] user: ', user);
            if (user.tocken) {
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
    path: '/level-record',
    name: 'level-record',
    component: () => import('../views/pages/level-record/level-record'),
    props: true
},
{
    path: '/temp',
    name: 'temp',
    component: () => import('../views/pages/level-record/temp'),
},
{
    path: '/level-contribution',
    name: 'level-contribution',
    component: () => import('../views/pages/level-contribution/level-contribution'),
    props: true
},
{
    path: '/login',
    name: 'login',
    component: () => import('../views/pages/account/login'),
    meta: {
        beforeResolve(routeTo, routeFrom, next) {

            const user = store.getters['users/getUser'];
            console.log('[router profile] user: ', user);
            if (user.tocken) {
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
},

{
    path: '/oauth2/redirect',
    name: 'oauth2-redirect',
    component: () => import('../views/pages/account/oauth2-redirect'),

},
{
    path: '/register',
    name: 'register',
    component: () => import('../views/pages/account/register'),
    meta: {
        beforeResolve(routeTo, routeFrom, next) {
            // If the user is already logged in
            if (store.getters['auth/loggedIn']) {
                // Redirect to the home page instead
                next({
                    name: 'home'
                })
            } else {
                // Continue to the login page
                next()
            }
        },
    },
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
{
    path: '/myhistory',
    name: 'myhistory',
    component: () => import('../views/pages/history/myhistory'),
    props: true,
},
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