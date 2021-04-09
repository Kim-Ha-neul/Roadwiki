<template>
  <b-container 
    id="chatting" 
    class="rounded border border-0 nanum-bold" 
  >
    <header>
      <b-container>
        <b-row class="mt-3">
          <b-col cols="6" v-if="selected !== null && selected !== '선택하세요'">
            <h2>{{ selected }}</h2>
          </b-col>
          <b-col cols="6" v-if="selected === null">
            <h3>채팅방을 선택해주세요</h3>
          </b-col>
          <b-col cols="6" v-if="selected === '선택하세요'">
            <h3>채팅방을 선택해주세요</h3>
          </b-col>
          <b-col cols="5">
            <chatting-list
              @selectRoom="changeRoom"
              class="mb-2 cursor-event"
            />
          </b-col>
          <b-col cols="1">
            <i 
              class="fas fa-times cursor-event"
              @click="close"
            >
            </i>
          </b-col>
        </b-row>
      </b-container>
    </header>

    <b-container
      id="content"
      class="mt-2"
    >
      <div
        v-for="(message, idx) in messages"
        v-bind:key="idx"
        class="mx-2"
      >
        <!-- join이라면 -->
        <b-container v-if="message.type === 'JOIN'">
          <b-row align-v="center" align-h="center">
            <b-badge 
              variant="traffic-blue"
              class="rounded text-classic-blue px-7">
              <strong>
                <!-- {{message.sender}} -->
                {{message.message}}
              </strong>
            </b-badge>
          </b-row>
        </b-container>
        <!-- chat, 타인 -->
        <b-container v-if="message.type === 'TALK' && message.isMe === false" class="mt-2">
          <b-row class="h5 mb-0">
            {{message.sender}}
          </b-row>
          <b-row align-v="center">
            <b-badge 
              pill
              variant="traffic-yellow" 
              size="md"
              class="p-2 mt-0">
              {{message.message}}
            </b-badge>
            <h6 class="m-0">
                {{message.time}}
            </h6>
          </b-row>
        </b-container>

        <!-- chat, 나 -->
        <b-container 
          v-if="message.type === 'TALK' && message.isMe === true" 
          class="mt-2"
        >
          <b-row align-h="end" align-v="center">
            <h6 class="mr-2 mb-0">
              {{message.time}}
            </h6>
            <b-badge 
              pill
              variant="traffic-green" 
              size="md"
              class="p-2 mt-0">
              {{message.message}}
            </b-badge>
          </b-row>
        </b-container>
      </div>
    </b-container>

    <footer>
      <b-container>
        <b-row>
          <b-col cols="9">
            <b-form-input
              placeholder="채팅을 입력해주세요"
              v-model="message"
              @keypress.enter="sendMsg"
              size="sm"
            >
            </b-form-input>
          </b-col>
          <b-col cols="3">
            <b-button 
              @click="sendMsg"
              variant="provence"
              class="py-1 px-3"
            >
              전송
            </b-button>
          </b-col>
        </b-row>
      </b-container>
    </footer>
  </b-container>
</template>
<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import store from "@/store";
import ChattingList from "@/components/Chatting/ChattingList";

const SERVER_URL = store.getters.getChattingServer;

export default {
  components: { ChattingList },
  props: {
    ChattingList,
  },
  data() {
    return {
      sender: store.getters.getName,
      message: "",
      messages: [],
      roomid: null,
      selected: null,
      img: '',
    };
  },
  created() {
    this.sender = store.getters.getName;
  },
  destroyed() {
    this.disconnect();
  },
  methods: {
    sendMsg() {
      if(!this.sender || this.sender.length < 1){
        alert("로그인 후에 이용해주세요");
      } else if (this.roomid == null || this.roomid.length < 1) {
        alert("채팅방을 선택해주세요");
      } else if (this.message.length < 1) {
        alert("메시지를 입력해주세요");
      } else {
        var msg = {
          type: "TALK",
          roomid: this.roomid,
          sender: this.sender,
          msg: this.message,
        };
        this.stompClient.send("/pub/chat/message", JSON.stringify(msg), {});
        this.message = "";
      }
    },
    disconnect() {
      if (this.stompClient !== undefined && this.stompClient !== null) {
        this.stompClient.disconnect();
      }
    },
    connect() {
      if (this.roomid == null || this.roomid.length < 1) return;
      var reconnect = 0;
      let socket = new SockJS(SERVER_URL + "/ws");
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect(
        {},
        (frame) => {
          this.stompClient.subscribe("/sub/chat/room/" + this.roomid, (res) => {
            let jsonBody = JSON.parse(res.body);
            let time = new Date();
            
            let m = {
              type: jsonBody.type,
              sender: jsonBody.sender,
              message: jsonBody.msg,
              isMe: (jsonBody.sender==this.sender) ? true : false,
              time: this.nowTime(time),
            };
            this.messages.push(m);

            var container = this.$el.querySelector("#content");

            setTimeout(() => {
              container.scrollTop = container.scrollHeight;
            }, 1);
          });
          var msg = {
            type: "JOIN",
            roomid: this.roomid,
            sender: this.sender,
            msg: this.message,
          };
          if(this.sender && this.sender.length > 1){
            this.stompClient.send("/pub/chat/message", JSON.stringify(msg), {});
          }
        },
        (error) => {
          if (reconnect++ <= 5) {
            setTimeout(function () {
              console.log("connection reconnect");
              let sock = new SockJS(SERVER_URL + "/ws");
              this.stompClient = Stomp.over(function () {
                return sock;
              });
              connect();
            }, 10 * 1000);
          }
        }
      );
    },
    close() {
      this.disconnect();
      this.$emit("remove");
    },
    changeRoom(roomid, selected, rooms) {
      this.roomid = roomid;
      rooms.forEach(room => {
        if (room.value === selected) {
          this.selected = room.text
        }
      });
      this.disconnect();
      this.message = "";
      this.messages = [];
      this.connect();
    },
    nowTime(time) {
           return (
             (time.getHours() < 10)?"0":"") + 
             time.getHours() +
             ":"+ 
             ((time.getMinutes() < 10)?"0":"") + 
             time.getMinutes() 
            //  +
            //  ":"+
            // ((time.getSeconds() < 10)?"0":"") +
            // time.getSeconds();
    },
  },
};
</script>
<style scoped>
#chatting {
  position: fixed;
  bottom: 5px;
  right: 5px;
  width: 450px;
  height: 500px;
  background-color: rgba(256, 256, 256, 0.8);
  z-index: 1000;
}

#content {
  height: 78%; 
  /* border: black 1px solid;  */
  overflow-y: scroll
}
</style>
