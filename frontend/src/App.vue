<template>
  <div id="app">
    <div class="layout-wrapper d-lg-flex">
      <div class="side-menu flex-lg-column mr-lg-1">
        <!-- LOGO -->
        <div class="navbar-brand-box">
          <router-link
            to="/"
            tag="a"
            class="logo logo-dark"
          >
            <span class="logo-sm">
              <img
                src="@/assets/images/logo.svg"
                alt
                height="30"
              />
            </span>
          </router-link>

          <router-link
            tag="a"
            to="/"
            class="logo logo-light"
          >
            <span class="logo-sm">
              <img
                src="@/assets/images/logo.svg"
                alt
                height="30"
              />
            </span>
          </router-link>
        </div>
        <!-- end navbar-brand-box -->

        <!-- Start side-menu nav -->
        <div class="flex-lg-column my-auto">
          <ul
            class="nav nav-pills side-menu-nav justify-content-center"
            role="tablist"
          >
            <!-- 1번탭: 사용자, 클라이밍장 검색 페이지 -->
            <li
              class="nav-item"
              v-b-tooltip.hover
            >
              <router-link
                to="/"
                class="nav-link"
                id="pills-search-tab"
                v-on:click.native="activetab = 1"
                v-bind:class="[activetab === 1 ? 'active' : '']"
              >
                <i class="ri-search-2-line"></i>
              </router-link>
            </li>

            <!-- 2번탭: 랭크 페이지 -->
            <li
              class="nav-item"
              v-b-tooltip.hover
            >
              <router-link
                to="/rank-list"
                class="nav-link"
                id="pills-rank-tab"
                v-on:click.native="activetab = 2"
                v-bind:class="[activetab === 2 ? 'active' : '']"
              >
                <i class="ri-medal-line"></i>
              </router-link>
            </li>

            <!-- 3번탭: 사용자, 클라이밍장 검색 페이지 -->
            <!-- <li
              class="nav-item"
              v-b-tooltip.hover
            >
              <router-link
                to="/temp"
                class="nav-link"
                id="pills-level-record-tab"
                v-on:click.native="activetab = 3"
                v-bind:class="[activetab === 3 ? 'active' : '']"
              >
                <i class="ri-add-box-line"></i>
              </router-link>
            </li> -->

            <!-- 4번탭: 사용자 계정, 로그인 필요 페이지
            <li
              class="nav-item"
              v-b-tooltip.hover
            >
              <router-link
                to="/profile"
                class="nav-link"
                id="pills-user-tab"
                v-on:click.native="activetab = 4"
                v-bind:class="[activetab === 4 ? 'active' : '']"
              >
                <i class="ri-user-2-line"></i>
              </router-link>
            </li> -->

            <b-dropdown
              class="nav-item profile-user-dropdown d-inline-block d-lg-none"
              toggle-class="nav-link"
              variant="white"
              dropup
            >
              <template v-slot:button-content>
                <img
                  :src="profile"
                  alt
                  class="profile-user rounded-circle"
                />
              </template>
              <!-- 4번탭: 사용자 계정, 로그인 필요 페이지 -->

              <b-dropdown-item>
                <router-link
                  :to="goToMypage()"
                  v-on:click.native="activetab = 4"
                  v-bind:class="[activetab === 4 ? 'active' : '']"
                >
                  프로필
                  <i class="ri-profile-line float-right text-muted"></i>
                </router-link>
              </b-dropdown-item>

              <b-dropdown-item
                href="/logout"
                v-if="user.nickname"
              >
                <b-dropdown-divider></b-dropdown-divider>
                로그아웃
                <i class="ri-logout-circle-r-line float-right text-muted"></i>
              </b-dropdown-item>
              <b-dropdown-item
                href="/login"
                v-if="!user.nickname"
              >
                <b-dropdown-divider></b-dropdown-divider>
                로그인
                <i class="ri-logout-circle-r-line float-right text-muted"></i>
              </b-dropdown-item>
            </b-dropdown>
          </ul>
        </div>
        <!-- end side-menu nav -->

        <div class="flex-lg-column d-none d-lg-block">
          <ul class="nav side-menu-nav justify-content-center">
            <b-dropdown
              class="nav-item btn-group dropup profile-user-dropdown"
              variant="white"
            >
              <template v-slot:button-content>
                <img
                  :src="profile"
                  alt
                  class="profile-user rounded-circle"
                />
              </template>
              <b-dropdown-item>
                <router-link
                  :to="goToMypage()"
                  v-on:click.native="activetab = 4"
                  v-bind:class="[activetab === 4 ? 'active' : '']"
                >
                  프로필
                  <i class="ri-profile-line float-right text-muted"></i>
                </router-link>
              </b-dropdown-item>

              <b-dropdown-item
                href="/logout"
                v-if="user.nickname"
              >
                <b-dropdown-divider></b-dropdown-divider>
                로그아웃
                <i class="ri-logout-circle-r-line float-right text-muted"></i>
              </b-dropdown-item>
              <b-dropdown-item
                href="/login"
                v-if="!user.nickname"
              >
                <b-dropdown-divider></b-dropdown-divider>
                로그인
                <i class="ri-logout-circle-r-line float-right text-muted"></i>
              </b-dropdown-item>
            </b-dropdown>
          </ul>
        </div>
        <!-- Side menu user -->
      </div>
      <router-view
        :key="$route.fullPath"
        class="mr-lg-1 flex-grow-1"
      />
    </div>
  </div>
</template>

<script>
import store from "@/store";
export default {
  data() {
    return {
      activetab: 1,
    };
  },
  computed: {
    user() {
      return store.getters["users/getUser"];
    },
    profile() {
      return this.user.profile
        ? this.user.profile
        : require("@/assets/images/logo-gumison.png");
    },
  },
  mounted() {
    var curPage = document.location.href;
    var routeUrl = curPage.split("/");
    switch (routeUrl[3]) {
      case "":
      case "level-record":
      case "level-contribution":
        this.activetab = 1;
        break;
      case "profile":
        this.activtab = 4;
        break;
    }
  },
  methods: {
    goToMypage() {
      if (this.user.nickname) {
        return { name: "myhistory", params: { nickname: this.user.nickname } };
      } else {
        return "/login";
      }
    },
  },
};
</script>
