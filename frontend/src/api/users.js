//import axios from 'axios';
import { GOOGLE_OAUTH_URL } from "@/constant/index";

// const instance = axios.create({
//     baseURL: `${BASE_URL}/users/`,
//     headers: {
//         'Content-type': 'application/json',
//     },
//     withCredentials: true,
// });

function googleLoginUser() {
    return window.location.href = `${GOOGLE_OAUTH_URL}`;
}

export {
    googleLoginUser,
}
