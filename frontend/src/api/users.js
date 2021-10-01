import axios from 'axios';
import { BASE_URL } from "@/constant/index";
import { GOOGLE_OAUTH_URL } from "@/constant/index";

const instance = axios.create({
    baseURL: `${BASE_URL}/users/`,
    headers: {
        'Content-type': 'application/json',
        //스토어에서 토큰값 가져오기
    },
    withCredentials: true,
});

function googleLoginUser() {
    return window.location.href = `${GOOGLE_OAUTH_URL}`;
}

function getUser() {
    return instance.get('oauth2/login')
}

export {
    googleLoginUser,
    getUser
}
