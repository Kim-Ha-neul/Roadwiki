<template>
  <div class="p-4 mx-3 nanum-bold">
    <!-- <hr class="my-2"> -->
    <base-input>
      <textarea
        class="form-control mt-2"
        rows="2"
        placeholder="댓글을 입력해주세요"
        v-model="content"
      >
      </textarea>
      <b-row align-h="end">
        <b-button
          variant="provence"
          class="mt-2 mr-3"
          @click="sendRecomment"
          size="sm"
        >
          등록
        </b-button>
      </b-row>
    </base-input>
  </div>
</template>

<script>
export default {
  name: "recommentform",
  data() {
    return {
      content: ""
    };
  },
  props: ["cid"],
  methods: {
    sendRecomment() {
      if(this.$store.getters.getAccessToken == null){
         alert("로그인 하셔야 해요");
         return;
       }

      let recomment = {
        cid: this.cid,
        content: this.content
      };
      if (recomment.content.length > 0) {
        axios
          .post(
            `${this.$store.getters.getBoardServer}/freeboard/recomment`,
            recomment
          )
          .then(res => {
            // alert('대댓글이 작성되었습니다.')
            this.content = "";
            this.$emit("sendRecomment");
          })
          .catch(err => {
            alert("죄송합니다. 문제가 생겼습니다.")
            // console.log(err);
          });
      } else {
        alert("대댓글을 작성해주세요");
      }
    }
  }
};
</script>

<style></style>
