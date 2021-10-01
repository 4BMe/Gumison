import axios from 'axios';
import { BASE_URL } from "@/constant/index"

const instance = axios.create({
    baseURL: BASE_URL + '/contribution',
    headers: {
      'Content-type': 'application/json',
    },
    withCredentials: true,
});

function submit(contributionData) {
    return instance.post('/', contributionData);
}

function update(contributionData) {
    return instance.put('/', contributionData);
}

export {
    submit,
    update,
}