import store from '@/state/store'

export default [{
        path: '/',
        name: 'home',
        component: () => import('../views/pages/search/search')
    },
    {
        path: '/userlist/:keyword',
        name: 'userlist',
        component: () => import('../views/pages/search/userList'),
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
        children:[
            {
                path: '',
                name: 'level',
                component: () => import('../views/pages/climbing/level'),
            },

        ]
    },
    {
        path: '/profile',
        name: 'profile',
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
    }
]