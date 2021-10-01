import axios from 'axios';
import { BASE_URL } from "@/constant/index"

const instance = axios.create({
    baseURL: BASE_URL + '/history',
    headers: {
      'Content-type': 'multipart/form-data',
    },
    withCredentials: true,
});

function submit(formData) {
    return instance.post('/', formData);
}

function update(recordData) {
    return instance.put('/', recordData);
}
export {
    submit,
    update,
}