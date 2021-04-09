<template>
  <div class="nanum-bold">
    <div id="chatting">
      <header style="height:10% ; text-align: center;">
        <h1 style="display:inline-block; margin-top: 2rem;">
          {{ name }} 채팅방에 오신것을 환영합니다.
        </h1>
      </header>
      <div style="height:85%; border:grey 0.3px solid; " id="content">
        <!-- <ul style="margin:0px"> -->
        <li
          v-for="(message, idx) in messages"
          v-bind:key="idx"
          style="margin:0px; font-size:16px"
        >
          {{ message.sender }} >> {{ message.message }}
        </li>
        <!-- </ul> -->
      </div>
      <footer style="height:5%">
        <input
          id="userName"
          type="text"
          v-model="sender"
          style="width:20%; height: 100%;"
          readonly
        />
        <input
          type="textarea"
          v-model="message"
          v-on:keypress.enter="sendMsg"
          style="width:60%; height: 100%; "
        />
        <button type="button" @click="sendMsg" style="width:20%; height: 100%;">
          전송
        </button>
      </footer>
    </div>
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import store from "@/store";

const SERVER_URL = store.getters.getChattingServer;

export default {
  name: "Chatting",
  components: {},
  props: {
    roomid: String,
    name: String
  },
  data() {
    return {
      sender: "",
      message: "",
      messages: []
    };
  },
  created() {
    this.sender = this.$store.getters.getName;
    sessionStorage.setItem("roomid", this.roomid);
    this.connect();
  },
  computed: {},
  mounted() {},
  methods: {
    sendMsg: function() {
      var msg = {
        type: "TALK",
        roomid: sessionStorage.getItem("roomid"),
        sender: this.sender,
        msg: this.message
      };
      this.stompClient.send("/pub/chat/message", JSON.stringify(msg), {});
      this.message = "";
    },
    connect: function() {
      var reconnect = 0;
      let socket = new SockJS(SERVER_URL + "/ws");
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect(
        {},
        frame => {
          this.stompClient.subscribe(
            "/sub/chat/room/" + sessionStorage.getItem("roomid"),
            res => {
              let jsonBody = JSON.parse(res.body);
              let m = {
                type: jsonBody.type,
                sender: jsonBody.sender,
                message: jsonBody.msg
              };
              this.messages.push(m);

              var container = this.$el.querySelector("#content");

              setTimeout(function() {
                container.scrollTop = container.scrollHeight;
              }, 1);
            }
          );
          var msg = {
            type: "JOIN",
            roomid: sessionStorage.getItem("roomid"),
            sender: this.sender,
            msg: this.message
          };
          this.stompClient.send("/pub/chat/message", JSON.stringify(msg), {});
        },
        error => {
          if (reconnect++ <= 5) {
            setTimeout(function() {
              console.log("connection reconnect");
              let sock = new SockJS(SERVER_URL + "/ws");
              this.stompClient = Stomp.over(function() {
                return sock;
              });
              connect();
            }, 10 * 1000);
          }
        }
      );
    }
  }
};
</script>

<style>
#chatting {
  overflow: scroll;
  margin-left: 15rem;
  width: 90rem;
  height: 60rem;
  background-color: white;
  border: 0.5px rgb(0, 0, 0) solid;
  overflow-x: hidden;
  overflow-y: auto;
}
#userName {
  background-color: rgb(230, 227, 227);
}
</style>
