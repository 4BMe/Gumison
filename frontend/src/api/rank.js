import axios from 'axios';
import { BASE_URL } from "@/constant/index"

const instance = axios.create({
    baseURL: BASE_URL + '/rank',
    headers: {
      'Content-type': 'application/json',
    },
    withCredentials: true,
});

async function getUserRankList(path){
    const response = await instance.get(path);

    if(response.status != 200){
        console.log("[getUserRankList] fail, code : " + response.status);
        console.log("fail message : " + response.data.message);
        return null;
    }
    console.log("[getUserRankList] success");
    return response.data.data.userRankList;
}

async function getUserRankListByKeywordAndPage(keyword, page) {
    const ret = await getUserRankList('/search/' + keyword + "/" + page);
    return ret;
}

async function getUserRankListByPage(page) {
    const ret = await getUserRankList('/' + page);
    return ret;
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