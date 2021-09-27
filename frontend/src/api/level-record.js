import axios from 'axios';

const instance = axios.create({
    baseURL: 'https://localhost:8888/level-record/',
    headers: {
      'Content-type': 'application/json',
    },
    withCredentials: true,
});

function submit(recordData) {
    return instance.post('record', recordData);
}

function uploadFile(fileReq){
    return instance.post('upload', fileReq);
}

export {
    uploadFile,
    submit,
}