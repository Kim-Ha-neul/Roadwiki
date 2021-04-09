<template>
  <div class="p-2 nanum-bold">
    <hr class="my-2" v-if="idx != 0" />
    <b-row>
      <b-col cols="8" align-self="center">
        <b-img :src="profileUrl" height="50px" width="50px;" rounded="circle"> </b-img> {{ comment.userName }}
      </b-col>
      <b-col v-if="!isUpdate">
        <h5>
          {{ comment.createDate }}
        </h5>
      </b-col>
    </b-row>
    <b-row v-if="!isUpdate">
      <b-col cols="8">
        <p class="px-3" style="white-space: pre-line">
          {{ comment.content }}
        </p>
      </b-col>
    </b-row>
    <b-row v-if="isUpdate">
      <base-input class="mx-3">
        <textarea
          class="form-control mt-2"
          rows="3"
          v-model="comment.content"
          autofocus
          cols="200"
        >
        </textarea>
        <b-row align-h="end">
          <b-button
            variant="classic-blue"
            class="mt-2 mr-3"
            @click="sendComment"
            >수정 완료</b-button
          >
        </b-row>
      </base-input>
    </b-row>
    <b-row v-if="!isUpdate">
      <b-col cols="8" class="mb-2">
        <b-button variant="stucco" size="sm" @click="makeRecomment">
          <i class="fas fa-reply"></i>
          답글
        </b-button>
      </b-col>
      <!-- 아이콘 가운데정렬 -->
      <b-col
        align-h="end"
        class="my-2"
        v-if="comment.uid === $store.getters.getUid"
      >
        <i
          class="far fa-trash-alt fa-lg mr-3"
          style="color: tomato"
          @click="deleteComment"
        ></i>
        <i
          class="far fa-edit fa-lg mr-3"
          style="color: Dodgerblue"
          @click="updateComment"
        ></i>
      </b-col>
    </b-row>
    <b-container v-for="(recomment, idx) in recomments" :key="idx">
      <RecommentList :recomment="recomment" @sendRecomment="sendRecomment" />
    </b-container>
    <RecommentForm
      v-show="recomment"
      :cid="comment.cid"
      @sendRecomment="sendRecomment"
    />
  </div>
</template>

<script>
import RecommentForm from "@/components/Board/RecommentForm.vue";
import RecommentList from "@/components/Board/RecommentList.vue";

export default {
  name: "commentList",
  components: {
    RecommentForm,
    RecommentList
  },
  data() {
    return {
      recomment: false,
      like: false,
      uid: "",
      nickname: "",
      // likeCnt: "",
      isUpdate: false,
      profileUrl: "",
    };
  },
  props: ["comment", "recomments", "idx"],
  created() {
    // 댓글 이미지
    // 해당 계정의 프로필 사진 가져오는 걸로 해야함
    axios
      .get(`${this.$store.getters.getUserServer}/user/image/${this.comment.uid}`)
      .then(res => {
        this.profileUrl = res.data.path;
      });


    axios
      .get(`${this.$store.getters.getUserServer}/user/name/${this.comment.uid}`)
      .then(res => {
        this.nickname = res.data.name;
      })
      .catch(err => {
        alert("죄송합니다. 문제가 생겼습니다.")
        // console.log(err);
      });
  },
  methods: {
    // 대댓글 작성 이후 recomment다시 false로 바꿔주기
    makeRecomment() {
      
      if (this.recomment) {
        this.recomment = false;
        // 하하하 하이 하하이 하늘 하이라는뜻ヾ(•ω•`)o
      } else {
        this.recomment = true;
      }
    },
    // clickLike() {
    //   this.like = true;
    //   this.comment.likeCnt++;
    // },
    // cancelLike() {
    //   this.like = false;
    //   this.comment.likeCnt--;
    // },
    sendRecomment() {
        if(this.$store.getters.getAccessToken == null){
         alert("로그인 하셔야 해요");
         return;
       }
      this.$emit("sendRecomment");
    },
    deleteComment() {
        if(this.$store.getters.getAccessToken == null){
         alert("로그인 하셔야 해요");
         return;
       }
      axios
        .delete(
          `${this.$store.getters.getBoardServer}/freeboard/comment/${this.comment.cid}`
        )
        .then(() => {
          alert("댓글이 삭제되었습니다.");
          this.$emit("sendRecomment");
        });
    },
    sendComment() {
        if(this.$store.getters.getAccessToken == null){
         alert("로그인 하셔야 해요");
         return;
       }
      let comment = {
        cid: this.comment.cid,
        content: this.comment.content
      };
      axios
        .put(`${this.$store.getters.getBoardServer}/freeboard/comment`, comment)
        .then(() => {
          alert("수정되었습니다.");
          this.$emit("sendRecomment");
          this.isUpdate = false;
        });
    },
    updateComment() {
      this.isUpdate = true;
    }
  }
};
</script>

<style></style>
