import axios from "axios";
import Vue from "vue";
import Vuex from "vuex";
// import axios from "axios";

Vue.use(Vuex);

// 서버
// const SERVER_URL = [
//   process.env.VUE_APP_SERVER_URL,
//   process.env.VUE_APP_SERVER_URL,
//   process.env.VUE_APP_SERVER_URL,
//   process.env.VUE_APP_SERVER_URL,
// ]
// 로컬
const SERVER_URL = [
  process.env.VUE_APP_SERVER_URL_BOARD,
  process.env.VUE_APP_SERVER_URL_CHATTING,
  process.env.VUE_APP_SERVER_URL_ROADMAP,
  process.env.VUE_APP_SERVER_URL_USER,
]

export default new Vuex.Store({
  state: {
    server: SERVER_URL,
    accessToken: null,
    user: {
      uid: "",
      email: "",
      name: "",
      createDate: ""
    },
    code: "",
    pid: ""
  },
  getters: {
    getBoardServer(state) {
      return state.server[0];
    },
    getChattingServer(state) {
      return state.server[1];
    },
    getRoadmapServer(state) {
      return state.server[2];
    },
    getUserServer(state) {
      return state.server[3];
    },
    getAccessToken(state) {
      return state.accessToken;
    },
    getUid(state) {
      return state.user.uid;
    },
    getEmail(state) {
      return state.user.email;
    },
    getName(state) {
      return state.user.name;
    },
    getCreateDate(state) {
      return state.user.createDate;
    },
    getCode(state) {
      return state.code;
    },
    getUser(state) {
      return state.user;
    },
    getPid(state) {
      return state.pid;
    }
  },
  mutations: {
    LOADUSERTOKEN(state) {
      state.accessToken = sessionStorage.getItem("auth-token");
      state.user = JSON.parse(sessionStorage.getItem("user"));
      axios.defaults.headers.common["auth-token"] = state.accessToken;
    },
    LOGIN(state, payload) {
      state.accessToken = payload["authorizationToken"];
      state.user.uid = payload["uid"];
      state.user.email = payload["email"];
      state.user.name = payload["name"];
      state.user.createDate = payload["createDate"];
    },
    LOGOUT(state) {
      state.accessToken = null;
      state.user.uid = "";
      state.user.email = "";
      state.user.name = "";
      state.user.createDate = "";
    },
    SETINFO(state, payload) {
      state.user.name = payload["name"];
      state.user.email = payload["email"];
    },
    SETEMAIL(state, payload) {
      state.user.email = payload;
    },
    SETCODE(state, payload) {
      state.code = payload;
    },
    SETPID(state, payload) {
      state.pid = payload;
    }
  },
  actions: {
    LOGIN(context, user) {
      return axios
        .post(`${SERVER_URL[3]}/user/login`, user)
        .then(response => {
          context.commit("LOGIN", response.data);
          if (`${response.data["authorizationToken"]}` == "undefined")
          axios.defaults.headers.common[
            "auth-token"
          ] = `${response.data["authorizationToken"]}`;
          sessionStorage.setItem(
            "auth-token",
            `${response.data["authorizationToken"]}`
          );
          sessionStorage.setItem("user", JSON.stringify(this.getters.getUser));
        })
        .catch((err) => {
          alert("죄송합니다. 문제가 생겼습니다.")
          //console.error(err)
        });
    },
    LOGOUT(context) {
      context.commit("LOGOUT");
      axios.defaults.headers.common["auth-token"] = null;
      sessionStorage.removeItem("auth-token");
    },
    SETINFO(context, user) {
      context.commit("SETINFO", user);
    },
    SETCODE(context, code) {
      context.commit("SETCODE", code);
    },
    SETEMAIL(context, email) {
      context.commit("SETEMAIL", email);
    },
    SETPID(context, pid) {
      context.commit("SETPID", pid);
    }
  },
  modules: {}
});
