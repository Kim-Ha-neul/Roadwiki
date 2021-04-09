<template>
  <div class="nanum-bold">
    <div
      class="chatting-list"
      v-for="(chatInfo, index) in chattingList"
      v-bind:key="index"
      @click="ClickchattingRoom(chatInfo)"
      @mouseover="getShadow($event)"
      @mouseout="deleteShadow($event)"
    >
      <div>
        <p style="text-align: center; margin-top: 3rem;">
          {{ chatInfo.name }} 오픈채팅방 접속하기
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import store from "@/store";

const SERVER_URL = store.getters.getChattingServer;

export default {
  name: "Chatting",
  props: {
    roomid: Number,
    name: String
  },
  data() {
    return {
      chattingList: []
    };
  },
  created() {
    this.getRooms();
  },
  methods: {
    getShadow(event) {
      event.currentTarget.style.boxShadow = "5px 5px 5px";
    },
    deleteShadow(event) {
      event.currentTarget.style.boxShadow = "";
    },
    ClickchattingRoom(chatInfo) {
      this.$emit("get-roominfo", chatInfo);
      alert(`${chatInfo.name}에 입장합니다.`);
    },
    getRooms: function() {
      axios
        .get(SERVER_URL + "/chat/open")
        .then(response => {
          this.chattingList = response.data.chatRooms;
          
        })
        .catch(response => {
        });
    }
  }
};
</script>

<style>
.chatting-list {
  display: inline-block;
  width: 240px;
  height: 150px;
  margin: 2rem;
  overflow: hidden;
  background-color: white;
  /* box-shadow: 5px 5px 5px; */
}
</style>
