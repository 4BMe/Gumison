import store from '@/state/store'

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
        path: '/forgot-password',
        name: 'Forgot-password',
        component: () => import('../views/pages/account/forgot-password'),
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
                if (process.env.VUE_APP_DEFAULT_AUTH === "firebase") {
                    store.dispatch('auth/logOut')
                } else {
                    store.dispatch('authfack/logout')
                }
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
        path: '/lock-screen',
        name: 'lock-screen',
        component: () => import('../views/pages/account/lock-screen'),
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
    }
]