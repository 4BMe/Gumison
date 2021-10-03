import axios from 'axios';
import { BASE_URL } from "@/constant/index";
import { GOOGLE_OAUTH_URL } from "@/constant/index";
import store from '@/store'

const oauth = axios.create({
    baseURL: `${BASE_URL}/users/`,
    headers: {
        'Content-type': 'application/json',
    },
    withCredentials: true,
});

const instance = axios.create({
    baseURL: `${BASE_URL}/users/`,
    headers: {
        'Content-type': 'application/json',
        'Authorization': `${store.getters["token/getHeaders"]}`,
    },
    withCredentials: true,
})

function googleLoginUser() {
    return window.location.href = `${GOOGLE_OAUTH_URL}`;
}

function getUser() {
    return oauth.get('oauth2/login')
}

function getUserByNickname(nickname) {
    return instance.get(nickname)
}

function updateUser(originNicname, updateUserData) {
    return instance.put(originNicname, updateUserData)
}

export {
    googleLoginUser,
    getUser,
    getUserByNickname,
    updateUser
}
