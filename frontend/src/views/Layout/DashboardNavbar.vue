<template>
  <div class="nanum-bold">
    <base-nav
      id="mainNav"
      container-classes="container-fluid"
      class="navbar-top navbar-expand"
      :class="{ 'navbar-dark': type === 'default' }"
      v-if="isHeader"
    >
    <b-col>
      <a
        @click="goToMain(url)"
        class="h1 mb-0 mr-2 text-black text-uppercase d-none d-lg-inline-block active router-link-active nanum-extra-bold cursor-event"
      >
        <img src="/img/brand/logo_word.png" alt="roadwiki" width="200rem;" />
      </a>

      <a
        href="#"
        aria-current="page"
        class="h4 mb-0 mt-2 d-none d-lg-inline-block active router-link-active nanum-extra-bold"
        id="routeName"
      >
        {{ $route.name }}
      </a>
    </b-col>

    <!-- 나누기 -->
    <b-navbar-nav class="ml-auto ml-md-0">
      <div v-if="isSearch" id="menu">
        <b-row align-v="center">
          <!-- 로드맵으로 가기 -->
          <b-col 
            cols="2"
            @click="goToMyRoadmap(url)"
            align-h="center"
          >
            <b-row class="justify-content-center">
              <i
                class="btn ni ni-map-big text-black nav-btn"
                variant="link"
              ></i>
            </b-row>
            <b-row class="justify-content-center">
              <h6 class="mb-0 nanum-extra-bold">로드맵</h6>
            </b-row>
          </b-col>

          <!-- 오피셜 로드맵으로 가기 -->
          <b-col
            cols="3"
            @click="goToOfficial(url)"
          >
            <b-row class="justify-content-center">
              <i class="btn ni ni-paper-diploma text-black nav-btn"></i>
            </b-row>
            <b-row class="justify-content-center">
              <h6 class="mb-0 nanum-extra-bold">공식로드맵</h6>
            </b-row>
          </b-col>

          <!-- 게시판으로 가기 -->
          <b-col
            cols="2"
            @click="goToBoard(url)"
          >
            <b-row class="justify-content-center">
              <i
                class="btn ni ni-bullet-list-67 text-black nav-btn"
              ></i>
            </b-row>
            <b-row class="justify-content-center">
              <h6 class="mb-0 nanum-extra-bold">게시판</h6>
            </b-row>
          </b-col>

          <!-- 로드맵 쉐어 게시판으로 가기 -->
          <b-col
            cols="3"
            @click="goToShare(url)"
          >
            <b-row class="justify-content-center">
              <i
                class="btn fas fa-share-alt nav-btn"
              ></i>
            </b-row>
            <b-row class="justify-content-center">
              <h6 class="mb-0 nanum-extra-bold">로드맵 공유</h6>
            </b-row>
          </b-col>

          <b-col
            cols="2"
          >
            <b-row class="justify-content-center">
              <i @click="activeSearch" class="btn fas fa-search text-black border-0"></i>
            </b-row>
            <b-row class="justify-content-center">
              <h6 class="mb-0 nanum-extra-bold">검색</h6>
            </b-row>
          </b-col>
          <!-- 채팅 게시판으로 가기 -->
          <!-- <div
            @click="goToChat(url)"  
          >
            <i class="btn far fa-comments nav-btn">
            </i>
            <h6></h6>
          </div> -->
        </b-row>
      </div>


      <div v-else>
        <b-form
          class="navbar-search form-inline mr-sm-3"
          :class="{
            'navbar-search-dark': type === 'default',
            'navbar-search-light': type === 'light'
          }"
          id="navbar-search-main"
        >
          <b-form-group class="my-1">
            <b-input-group
              class="input-group-alternative input-group-merge"
              id="searchBlock"
            >
              <b-form-input
                placeholder="Search"
                type="text"
                class="text-default"
                v-model="searchQuery"
                @keydown.enter.prevent="clickSearch"
              >
              </b-form-input>

              <div class="input-group-append">
                <span class="input-group-text" @click="clickSearch">
                </span>
              </div>
            </b-input-group>
          </b-form-group>
        <b-col 
          style="margin-right: -33.5px; margin-left: 10px;"
        >
          <b-row class="justify-content-center">
            <i @click="clickSearch" class="btn fas fa-search text-black border-0"></i>
          </b-row>
          <b-row class="justify-content-center">
            <h6 class="mb-0 nanum-extra-bold">검색</h6>
          </b-row>
        </b-col>
        </b-form>

      </div>
      <!-- 여기부터 이미지, 드롭다운 리스트 -->

      <base-dropdown
        menu-on-right
        class="nav-item"
        tag="li"
        title-tag="a"
        title-classes="nav-link pr-0"
        v-if="this.$store.getters.getAccessToken"
      >
        <a
          href="#"
          class="nav-link pr-5"
          @click.prevent
          slot="title-container"
        >
          <b-media no-body class="align-items-center">
            <span class="avatar avatar-sm rounded-circle ml-2">
              <b-avatar
                alt="Image placeholder"
                :src="profileUrl"
                v-model="profileUrl"
              ></b-avatar>
            </span>
            <b-row>
              <b-media-body class="ml-2 d-none d-lg-block">
                <h6 class="mb-0 text-sm font-weight-bold">
                  <!-- {{ this.$store.getters.getName }} -->
                </h6>
              </b-media-body>
            </b-row>
          </b-media>
        </a>

        <!-- 프로필 이미지 눌렀을 때 -->
        <template >
          <b-dropdown-header class="noti-title">
            <h6 class="text-overflow m-0">Welcome!</h6>
          </b-dropdown-header>
          <b-dropdown-item @click="myProfile">
            <i class="ni ni-single-02"></i>
            <span>나의 프로필</span>
          </b-dropdown-item>
          <b-dropdown-item @click="goToCalendar">
            <i class="ni ni-calendar-grid-58"></i>
            <span>캘린더</span>
          </b-dropdown-item>
          <div class="dropdown-divider"></div>
          <b-dropdown-item @click="logOut">
            <i class="ni ni-user-run"></i>
            <span>Logout</span>
          </b-dropdown-item>
        </template>
      </base-dropdown>
      
      <Login-content 
        v-else/>
    </b-navbar-nav>
  </base-nav>

    <!-- 여기까지가 nav 입니다. -->

    <div class="main-content">
      <!-- <dashboard-navbar :type="$route.meta.navbarType"></dashboard-navbar> -->

      <div @click="$sidebar.displaySidebar(false)">
        <fade-transition :duration="200" origin="center top" mode="out-in">
          <!-- your content here -->
          <router-view></router-view>
        </fade-transition>
      </div>

      <content-footer v-if="!$route.meta.hideFooter"></content-footer>
    </div>
    <div>
      <chatting v-on:remove="removeChatting" v-if="chattingOn" />
      <b-button
        id="chat"
        @click="createChatting"
        pill
        v-else
        size="lg"
        class="px-3"
      >
        <!-- chatting -->
        <i class="far fa-comment-dots fa-2x cursor-event"></i>
      </b-button>
    </div>
  </div>
</template>
<script>
import PerfectScrollbar from "perfect-scrollbar";
import "perfect-scrollbar/css/perfect-scrollbar.css";
import LoginContent from "@/components/Login/LoginContent.vue";
import LogoutContent from "@/components/Logout/LogoutContent.vue";
import Chatting from "@/components/Chatting/Chatting";

function hasElement(className) {
  return document.getElementsByClassName(className).length > 0;
}

function initScrollbar(className) {
  if (hasElement(className)) {
    new PerfectScrollbar(`.${className}`);
  } else {
    // try to init it later in case this component is loaded async
    setTimeout(() => {
      initScrollbar(className);
    }, 100);
  }
}

import ContentFooter from "./ContentFooter.vue";
import DashboardContent from "./Content.vue";
import { FadeTransition } from "vue2-transitions";
import { mapGetters } from "vuex";
import { CollapseTransition } from "vue2-transitions";
import { BaseNav, Modal } from "@/components";

export default {
  components: {
    CollapseTransition,
    BaseNav,
    Modal,
    ContentFooter,
    DashboardContent,
    FadeTransition,
    LoginContent,
    LogoutContent,
    Chatting
  },
  props: {
    type: {
      type: String,
      default: "default", // default|light
      description:
        "Look of the dashboard navbar. Default (Green) or light (gray)"
    }
  },
  data() {
    return {
      activeNotifications: false,
      showMenu: false,
      searchModalVisible: false,
      searchQuery: "",
      isHeader: true,
      uid: "",
      profileUrl: "",
      chattingOn: false,
      urlNow: "",
      isHoverM: true,
      isHoverO: true,
      isHoverB: true,
      isHoverS: true,
      isHoverC: true,
      isSearch: true,
      url: "",
      
    }; 
  },
  created() {
    this.uid = this.$store.getters.getUid;
    let url = this.$route.name;
    this.url = url;
    this.checkUrl(url);
    if(this.uid){
    axios
      .get(`${this.$store.getters.getUserServer}/user/image/${this.uid}`)
      .then(res => {
        this.profileUrl = res.data.path;
      });
    }
  },
  computed: {
    routeName: function() {
      const { name } = this.$route;
      return this.capitalizeFirstLetter(name);
    },
  },
  watch: {
    $route(to) {
      this.checkUrl(to.name);
    }
  },
  mounted() {
    this.initScrollbar();
  },
  methods: {
    initScrollbar() {
      let isWindows = navigator.platform.startsWith("Win");
      if (isWindows) {
        initScrollbar("sidenav");
      }
    },
    removeChatting() {
      this.chattingOn = false;
    },
    createChatting() {
      this.chattingOn = true;
    },
    capitalizeFirstLetter(string) {
      return string.charAt(0).toUpperCase() + string.slice(1);
    },
    toggleNotificationDropDown() {
      this.activeNotifications = !this.activeNotifications;
    },
    closeDropDown() {
      this.activeNotifications = false;
    },
    checkUrl(url) {
      let array = ["로드맵 생성하기", "로드맵 수정하기", "roadback"];
      let isHeader = true;
      array.map(path => {
        if (url === path) isHeader = false;
      });
      this.isHeader = isHeader;
      
    },
    myProfile() {
      this.$router.push({
        name: "프로필",
        query: { profileId: this.$store.getters.getUid }
      });
    },
    logOut() {
      this.$store
        .dispatch("LOGOUT")
        .then(() => {
          if(this.url != 'MAINPAGE')
            this.$router.push("MAINPAGE");
          
        })
        .catch(() => {
          alert("로그아웃에 실패했습니다.");
        });
    },
    goToMain(url) {
      if (url != "MAINPAGE") {
        this.$router.push({ name: "MAINPAGE" });
      }
    },
    goToMyRoadmap(url) {
      if (url != "나의 로드맵") {
        this.$router.push({ name: "나의 로드맵" });
      }
    },
    clickSearch() {
      this.activeSearch();
      if(this.searchQuery == "")
        return;
      this.$router.push({
        name: "검색결과",
        query: { searchKeyword: `${this.searchQuery}` }
      });
    },
    checkHoverM() {
      if (this.isHoverM) {
        this.isHoverM = false;
      } else {
        this.isHoverM = true;
      }
    },
    checkHoverO() {
      if (this.isHoverO) {
        this.isHoverO = false;
      } else {
        this.isHoverO = true;
      }
    },
    checkHoverB() {
      if (this.isHoverB) {
        this.isHoverB = false;
      } else {
        this.isHoverB = true;
      }
    },
    checkHoverS() {
      if (this.isHoverS) {
        this.isHoverS = false;
      } else {
        this.isHoverS = true;
      }
    },
    checkHoverC() {
      if (this.isHoverC) {
        this.isHoverC = false;
      } else {
        this.isHoverC = true;
      }
    },
    goToOfficial(url) {
      if (url != "공식 로드맵") {
        this.$router.push({ name: "공식 로드맵" });
      }
    },
    goToBoard(url) {
      if (url != "게시판") {
        this.$router.push({ name: "게시판" });
      }
    },
    goToShare(url) {
      if (url != "공유로드맵's") {
        this.$router.push({ name: "공유로드맵's" });
      }
    },
    goToCalendar(url) {
      if (url != "캘린더") {
        this.$router.push({ name: "캘린더" });
      }
    },
    goToChat(url) {
      if (url != "채팅카테고리") {
        this.$router.push({ name: "채팅카테고리" });
      }
    },
    activeSearch() {
      if (this.isSearch) {
        this.isSearch = false;
      } else {
        this.isSearch = true;
      }
    }
  },
  computed: {
    ...mapGetters(["getAccessToken"])
  }
};
</script>

<style>
#chat {
  position: fixed;
  right: 30px;
  bottom: 30px;
  background-color: skyblue;
}
#mainNav {
  background-color: white;
  padding: 10px;
  box-shadow: 2px 2px blur;
}
#routeName {
  color: #84898c;
}
#searchBlock {
  color: black;
  text-decoration-color: black;
}
#menu {
  margin: auto;
}
.nav-btn {
  height: 40px;
  width: 60px;
}
/* #testdiv {
  position: fixed;
  right: 0;
  top: 0;
  width: 30%;
  height: 100%;
  overflow: hidden;
  background-color: darkred;
} */
@font-face {
  font-family: "San Francisco";
  font-weight: 400;
  src: url("https://applesocial.s3.amazonaws.com/assets/styles/fonts/sanfrancisco/sanfranciscodisplay-regular-webfont.woff");
}
</style>
