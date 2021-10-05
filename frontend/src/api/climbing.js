import axios from 'axios';
import { BASE_URL } from "@/constant/index"

const instance = axios.create({
    baseURL: BASE_URL + '/climbing',
    headers: {
      'Content-type': 'application/json',
    },
    withCredentials: true,
});

function getClimingDetail(climginId){
    return instance.get(`detail/${climginId}`);
}

function getClimingSolution(climginId, solutionId, pageNumber){
    return instance.get(`detail/${climginId}/${solutionId}/${pageNumber}`);
}

export {
    getClimingDetail,
    getClimingSolution
}