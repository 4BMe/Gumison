import axios from 'axios';
import { BASE_URL } from "@/constant/index";
import { GOOGLE_OAUTH_URL } from "@/constant/index";
import store from '@/store'

const instance = axios.create({
    baseURL: `${BASE_URL}/users/`,
    headers: {
        'Content-type': 'application/json',
    },
    withCredentials: true,
});

const oauth = axios.create({
    baseURL: `${BASE_URL}/users/`,
    headers: {
        'Content-type': 'application/json',
        'Authorization': `Bearer ${store.getters["users/getToken"]}`,
    },
    withCredentials: true,
})

function googleLoginUser() {
    return window.location.href = `${GOOGLE_OAUTH_URL}`;
}

function getUser() {
    return instance.get('oauth2/login')
}

function getUserByNickname(nickname) {
    return oauth.get(nickname)
}

function updateUserByOauthId(oauthId, updateUserData) {
    return oauth.put(oauthId, updateUserData)
}

function deleteUserByOauthId(oauthId) {
    return oauth.delete(oauthId)
}

export {
    googleLoginUser,
    getUser,
    getUserByNickname,
    updateUserByOauthId,
    deleteUserByOauthId
}
