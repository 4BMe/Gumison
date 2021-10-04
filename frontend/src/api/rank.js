import axios from 'axios';
import { BASE_URL } from "@/constant/index"

const instance = axios.create({
    baseURL: BASE_URL + '/rank',
    headers: {
      'Content-type': 'application/json',
    },
    withCredentials: true,
});

function getUserRankList(recordData) {
    return instance.post('/', recordData);
}


export {
    getUserRankList,
}