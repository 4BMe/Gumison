import axios from 'axios';
import { BASE_URL } from "@/constant/index"

const instance = axios.create({
    baseURL: BASE_URL + '/video',
    headers: {
      'Content-type': 'multipart/form-data',
    },
    withCredentials: true,
});

function upload(formData) {
    return instance.post('/', formData);
}

export {
    upload,
}