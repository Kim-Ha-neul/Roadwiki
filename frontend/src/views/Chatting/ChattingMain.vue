<template>
  <div class="nanum-bold">
      <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-baby-blue"></base-header> 
          <ChattingList v-if="showChattingList === true" @get-roominfo="getRoomid"/>
          <Chatting v-else :roomid="roomid" :name="name" />   
  </div>
</template>

<script>
import Chatting from '../Chatting/Chatting'
import ChattingList from '../Chatting/ChattingList'

export default {
    name: "ChattingMain",
    components: {
        Chatting,
        ChattingList,
    },
    data() {
        return {
            showChattingList: true, 
            roomid: '',
            name: '', 
        }
    },
    created(){
        // 서버에서 roomid, name를 받아와야함(axios)
    },
    computed: {},
    mounted(){

    },
    methods: {
        // 선택한 채팅방에대한 정보 넘겨받기
        getRoomid(chatInfo){
            this.roomid = chatInfo.roomid  
            this.name = chatInfo.name 
            this.showChattingList = false
        },
    },
}
</script>

<style>

</style>