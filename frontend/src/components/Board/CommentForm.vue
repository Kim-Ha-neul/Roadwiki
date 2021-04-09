<template>
  <div class="nanum-bold">
    <base-input>
      <textarea
        class="form-control nanum-bold"
        rows="3"
        placeholder="댓글을 입력해주세요"
        v-model="content"
      >
      </textarea>
      <b-row align-h="end" class="mt-1">
        <b-button variant="provence" class="mt-2 mr-3 nanum-bold" @click="sendComment"
          >댓글 작성</b-button
        >
      </b-row>
    </base-input>
  </div>
</template>

<script>
export default {
  name: "commentform",
  data() {
    return {
      content: ""
    };
  },
  props: ["pid"],
  methods: {
    sendComment() {
      if(this.$store.getters.getAccessToken == null){
         alert("로그인 하셔야 해요");
         return;
       }

      let posting = {
        pid: this.pid,
        uid: this.$store.getters.getUid,
        content: this.content
      };
      if (posting.content.length > 0) {
        axios
          .post(
            `${this.$store.getters.getBoardServer}/freeboard/comment`,
            posting
          )
          .then(res => {
            this.$emit("sendComment");
            this.content = "";
          })
          .catch(err => {
            alert("죄송합니다. 문제가 생겼습니다.")
            //console.log(err);
          });
      } else {
        alert("댓글을 작성해주세요");
      }
    }
  }
};
</script>

<style></style>
