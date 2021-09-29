<script>
import axios from "axios";
import { BASE_URL } from "@/constant/index"
import simplebar from "simplebar-vue";

/**
 * Profile component
 */
export default {
  components: {
    simplebar,
  },
  data() {
    return {
      nickname: "Patricia Smith",
      profile: "",
      description: "adc@123.com",
      tier:"bronze4",
      ex: "",
      nextExp:"",
      solutionList:{},
    };
  },
  created() {
      axios
        .get(`${BASE_URL}/history/dummy3`)
        .then(({ data }) => {
          this.profile = data.data.profile;
          this.nickname = data.data.nickname;
          this.description = data.data.description;
          this.tier = data.data.tier;
          this.exp = data.data.exp;
          this.nextExp = data.data.nextExp;
          this.solutionList = data.data.solutionList;
        })
        .catch((err) => {
          console.log("에러: " + err);
        });
  },
  methods: {
    searchHistory(user) {
      console.log(user);
    },
  },
};
</script>

<template>
  <div>
    <div class="px-4 pt-4">
      <h4 class="mb-0">My History</h4>
    </div>
    <div class="media">
        <div class="chat-user-img online align-self-center mr-3">
          <div v-if="profile">
            <img
              :src="profile"
              class="rounded-circle avatar-xs"
              alt
            />
          </div>
          <div class="avatar-xs" v-if="!profile">
            <span
              class="avatar-title rounded-circle bg-soft-primary text-primary"
              >{{ nickname.charAt(0) }}
            </span>
          </div>
        </div>
        <div class="media-body overflow-hidden">
          <h5 class="text-truncate font-size-15 mb-1">
            <img
              :src="require(`@/assets/images/tier/`+tier+`.png`)"
              alt="user-img"
              class="img-fluid rounded-circle sm-tier-img"
            /> 
            {{ nickname }}
          </h5>
        </div>
        
      </div>
    <div class="media">
      <table  style="width:100%">
        <tbody>
          <tr>
            <td>
              <p class="chat-user-message text-truncate mb-0">
                {{ description }}
              </p>
            </td>
          </tr>
        </tbody>
      </table>  
    </div>
    <div class="mb-0">
      <div class="media">
        <img
          :src="require(`@/assets/images/tier/`+tier+`.png`)"
          alt="user-img"
          class="img-fluid rounded-circle sm-tier-img"
        />
        <h4>
          {{tier}}
        </h4>
        <h4 class="float-right">
          {{exp}}/{{nextExp}}
        </h4>
      </div>
    </div>  
    <!-- End profile user -->
    <div class="px-2">
      <simplebar class="chat-message-list" id="chat-list" ref="current">
          <ul class="list-unstyled chat-list chat-user-list" v-if="solutionList.length == 0">
            <li>
              <a href="javascript:void(0);">
                <div class="media">
                  <div class="media-body overflow-hidden">
                    <p class="chat-user-message text-truncate mb-0 pt-">
                      "{{ nickname }}"님은 뉴비입니다.
                    </p>
                  </div>
                </div>
              </a>
              <div class="media">
                <div class="chat-user-img online align-self-center mr-3">
                </div>
              </div>
            </li>
          </ul>
          <ul class="list-unstyled chat-list chat-user-list" v-else>
            <thead>
            <tr>
              <th scope="col">티어</th>
              <th scope="col">장소</th>
              <th scope="col">색</th>
              <th scope="col">회수</th>
              <th scope="col">날짜</th>
            </tr>
            </thead>
            <li
              v-for="(item, index) in solutionList"
              :key="index"
              @click="searchHistory(item)"
            >
              <a href="javascript:void(0);">
                <div class="media">
                  <div class="media-body overflow-hidden">
                    <h5 class="text-truncate font-size-15 mb-1">
                      <img
                        :src="require(`@/assets/images/tier/`+item.tier+`.png`)"
                        alt="user-img"
                        class="img-fluid rounded-circle sm-tier-img"
                      /> 
                    </h5>
                    <table  style="width:100%">
                      <tbody>
                        <tr>
                          <td style="width:30%">
                            <p class="chat-user-message text-truncate mb-0">
                              {{ item.tier }}
                            </p>
                          </td>
                          <td>
                            <p class="chat-user-message text-truncate mb-0">
                              {{ item.climbingName }}
                            </p>
                          </td>
                          <td>
                            <p class="chat-user-message text-truncate mb-0">
                              {{ item.level }}
                            </p>
                          </td>
                          <td>
                            <p class="chat-user-message text-truncate mb-0">
                              {{ item.cnt }}
                            </p>
                          </td>
                          <td>
                            <p class="chat-user-message text-truncate mb-0">
                              {{ item.date }}
                            </p>
                          </td>
                        </tr>
                      </tbody>
                    </table>  
                  </div>
                </div>
              </a>
            </li>
        </ul>
      </simplebar>
    </div>
  </div>
</template>