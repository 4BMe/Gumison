import axios from "axios";
import { BASE_URL } from "@/constant/index";

const instance = axios.create({
  baseURL: BASE_URL + "/rank",
  headers: {
    "Content-type": "application/json",
  },
  withCredentials: true,
});

async function getAxiosRequest(path) {
  try {
    const response = await instance.get(path);

    if (response.status != 200) {
      console.log("[userRankAxios] fail, code : " + response.status);
      console.log("fail message : " + response.data.message);
      return null;
    }
    console.log("[userRankAxios] success");
    return response.data.data;
  } catch (e) {
    console.log(e);
  }
}

async function getUserRankListByKeywordAndPage(keyword, page) {
  const ret = await getAxiosRequest("/search/" + keyword + "/" + page);
  return ret;
}

async function getUserRankListByPage(page) {
  const ret = await getAxiosRequest("/" + page);
  return ret;
}

async function getUserRankListByNickname(nickname) {
  return await getAxiosRequest("/detail/" + nickname);
}

export {
  getUserRankListByKeywordAndPage,
  getUserRankListByPage,
  getUserRankListByNickname,
};
