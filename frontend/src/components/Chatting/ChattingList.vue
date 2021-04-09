<template>
  <div class="nanum-bold">
    <b-form-select v-model="selected" :options="rooms" size="sm" class="b-inline-block"></b-form-select>
    <!-- <div class="mt-3">Selected: <strong>{{ selected }}</strong></div> -->
  </div>
</template>
<script>
import store from "@/store";

const SERVER_URL = store.getters.getChattingServer;

export default {
  data() {
    return {
      fields: ["name"],
      rooms: [],
      roomid: "",
      selected: null,
    };
  },
  watch: {
    selected: function (newVal, oldVal) {
      this.sendRoom(newVal);
    },
  },
  methods: {
    getRooms: function () {
      axios
        .get(SERVER_URL + "/chat/open")
        .then((response) => {
          this.rooms = response.data.chatRooms;
          for (var i = 0; i < this.rooms.length; i++) {
            this.rooms[i].value = this.rooms[i].roomid;
            this.rooms[i].text = this.rooms[i].name;
          }
          this.rooms.unshift({ value: null, text: "선택하세요" });
        })
        .catch((response) => {
          alert("죄송합니다. 문제가 생겼습니다.")
          //console.log(response);
        });
    },
    sendRoom: function (roomid) {
      this.$emit("selectRoom", roomid, this.selected, this.rooms);
    },
  },
  created() {
    this.getRooms();
  },
};
</script>
<style scoped>
#selrow {
  background-color: skyblue;
}
</style>
