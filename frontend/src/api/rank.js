import axios from 'axios';
import { BASE_URL } from "@/constant/index"

const instance = axios.create({
    baseURL: BASE_URL + '/rank',
    headers: {
      'Content-type': 'application/json',
    },
    withCredentials: true,
});

function getUserRankList(path){
    instance
        .get(path)
        .then(function(response){
            console.log(response.data.data.userRankList);
            if(response.status != 200){
                console.log("[getUserRankList] fail, code : " + response.status);
                console.log("fail message : " + response.data.message);
                return null;
            }
            console.log("[getUserRankList] success");
            return response.data.data.userRankList;
        })
}

function getUserRankListByKeywordAndPage(keyword, page) {
    return getUserRankList('/search/' + keyword + "/" + page);
}

function getUserRankListByPage(page) {
    return getUserRankList('/' + page);
}

function getUserRankListByNickname(nickname) {
    instance
        .get('/detail' + nickname)
        .then(function(response){
            
            if(response.status != 200){
                console.log("[getUserRankList] fail, code : " + response.status);
                console.log("fail message : " + response.data.message);
                return null;
            }
            console.log("[getUserRankList] success");
            return response.data.data.userRank;
        })
}


export {
    getUserRankListByKeywordAndPage,
    getUserRankListByPage,
    getUserRankListByNickname,
}