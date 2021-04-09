<template>
  <div class="source-han-serif-k-medium nanum-bold">
    <base-header class="pt-md-5 pb-2 bg-baby-blue" style="height: 350px;">
      <b-container>	
        <b-row class="pt-1">
          <i class="fas fa-arrow-left fa-2x text-classic-blue cursor-event" @click="goToBack"></i>
        </b-row>
        <!-- <b-row class="py-2">
          <h1 class="display-3 text-white nanum-extra-bold">{{ title }}</h1>
        </b-row> -->
        <b-container class="px-9 py-3">
          <b-row class="py-5">
            <h1 class="display-3 text-white nanum-extra-bold">{{ title }}</h1>
          </b-row>
          <b-row>
            <p class="text-white nanum-bold">{{ createDate }}</p>
          </b-row>
          <b-row v-if="modifyDate">
            <p class="text-white nanum-bold">{{ modifyDate }}</p>
          </b-row>
        </b-container>
      </b-container>
    </base-header>

    <b-container class="py-4 mt-4">
      <b-row></b-row>
        <b-container class="mx-9">
          <b-row align-v="center">
            <b-col cols="1" class="pr-0 mb-2">
              <b-img :src="profileUrl" rounded="circle" width="50px" heigth="50px" />
            </b-col>
            <b-col class="ml--3">
              <h3 class="nanum-extra-bold">{{ name }}</h3>
              <h5 class="nanum-bold">{{ oneline }}</h5>
            </b-col>
          </b-row>
          <hr class="my-3 mr-9 ml--3" />
          <Viewer v-if="content != null" :initialValue="content" class="nanum-regular" />
          <b-row class="ml-1 mr-1 my-3">
            <b-button
              size="sm"
              variant="cornhusk"
              pill
              class="mr-2"
              v-for="(tag, idx) in tags"
              :key="idx"
            >
              <!-- class="ml-1 mt-2 mr-1 bg-cornhusk rounded-pill py-1 px-2" -->
              <span class="p"># {{ tag }}</span>
            </b-button>
          </b-row>
          <b-row align-h="end" class="my-2" v-if="isWritter">
            <!-- 게시물의 uid와 현재 uid가 동일 할 때 보여줄 내용 -->
            <i
              class="far fa-trash-alt fa-lg mr-3 cursor-event"
              style="color: tomato"
              @click="deleteBoard"
            ></i>
            <i
              class="far fa-edit fa-lg mr-3 cursor-event"
              style="color: Dodgerblue"
              @click="updateBoard"
            ></i>
          </b-row>
          <b-row>
            <i
              class="far fa-thumbs-up fa-2x ml-3 text-traffic-blue cursor-event"
              v-if="!like"
              @click="clickLike"
              ><span class="h3 ml-1 nanum-bold">{{ likeCnt }}</span></i
            >
            <i
              class="fas fa-thumbs-up fa-2x ml-3 text-traffic-blue cursor-event"
              v-if="like"
              @click="cancelLike"
              ><span class="h3 ml-1 nanum-bold">{{ likeCnt }}</span></i
            >
            <i
              class="far fa-thumbs-down fa-2x ml-3 text-traffic-red cursor-event"
              v-if="!dislike"
              @click="clickDislike"
              ><span class="h3 ml-1 nanum-bold">{{ dislikeCnt }}</span></i
            >
            <i
              class="fas fa-thumbs-down fa-2x ml-3 text-traffic-red cursor-event"
              v-if="dislike"
              @click="cancelDislike"
              ><span class="h3 ml-1 nanum-bold">{{ dislikeCnt }}</span></i
            >
          </b-row>
          <!-- <hr class="my-2" /> -->
        </b-container>
      <!-- </b-row> -->
    </b-container>

    <b-container class="py-4 mt-4">
      <b-container class="px-9 py-3">
        <b-row>
          <h2 class="ml-3 mb-2 nanum-bold">{{ comments.length }}개의 댓글</h2>
        </b-row>
        <!-- 댓글 폼 필요 -->
        <CommentForm :pid="pid" @sendComment="getPostingInfo" />
        <!-- <CommentList :comments="comments" :recomments="recomments"/>       -->
        <b-container v-for="(comment, idx) in comments" :key="idx">
          <CommentList
            :comment="comment"
            :recomments="recomments[idx]"
            :idx="idx"
            @sendRecomment="getPostingInfo"
          />
        </b-container>
      </b-container>
    </b-container>
  </div>
</template>

<script>
import CommentForm from "@/components/Board/CommentForm.vue";
import CommentList from "@/components/Board/CommentList.vue";
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Viewer } from "@toast-ui/vue-editor";

export default {
  name: "",
  components: {
    CommentForm,
    CommentList,
    Viewer
  },
  data() {
    return {
      name: "",
      oneline: "",
      content: null,
      title: "",
      tags: [],
      createDate: "",
      modifyDate: "",
      uid: "",
      classifier: "",
      like: false,
      dislike: false,
      likeCnt: 0,
      dislikeCnt: 0,
      pid: "",
      isWritter: false,
      comments: [],
      recomments: [],
      profileUrl: ""
    };
  },
  created() {
    axios
      .get(
        `${this.$store.getters.getBoardServer}/freeboard/posting/${this.$route.query.pid}`
      )
      .then(res => {
        this.uid = res.data.posting.uid;

        // 해당 계정의 프로필 사진 가져오는 걸로 해야함
        axios
          .get(`${this.$store.getters.getUserServer}/user/image/${this.uid}`)
          .then(res => {
            this.profileUrl = res.data.path;
          });

        this.tags = res.data.posting.tags;
        if (this.uid === this.$store.getters.getUid) {
          this.isWritter = true;
        } else {
          this.isWritter = false;
        }
      });
    if(this.$store.getters.getAccessToken){
    axios
      .get(
        `${this.$store.getters.getBoardServer}/freeboard/postinglike/${this.$route.query.pid}`
      )
      .then(res => {
        if (res.data.like !== null) {
          this.like = true;
        }
        this.likeCnt = res.data.likeCnt;
      });

    axios
      .get(
        `${this.$store.getters.getBoardServer}/freeboard/postinghate/${this.$route.query.pid}`
      )
      .then(res => {
        if (res.data.like !== null) {
          this.dislike = true;
        }
        this.dislikeCnt = res.data.dislikeCnt;
      });
    }
  },
  mounted() {
    this.getPostingInfo();
  },
  methods: {
    getPostingInfo() {
      axios
        .get(
          `${this.$store.getters.getBoardServer}/freeboard/posting/${this.$route.query.pid}`
        )
        .then(res => {
          this.name = res.data.user.name;
          this.oneline = res.data.user.oneline;
          this.classifier = res.data.posting.classifier;
          this.title = res.data.posting.title;
          this.content = res.data.posting.content;
          this.createDate = res.data.posting.createDate;
          this.modifyDate = res.data.posting.modifyDate;
          this.uid = res.data.posting.uid;
          this.pid = res.data.posting.pid;
          this.comments = res.data.comments;
          this.recomments = res.data.recomments;
          this.tags = res.data.posting.tags;
        })
        .catch(err => {});
    },
    updateBoard() {
      const pid = this.$route.query.pid;
      this.$router.push({ name: "글 수정하기", query: { pid } });
    },
    deleteBoard() {
      axios
        .delete(
          `${this.$store.getters.getBoardServer}/freeboard/posting/${this.$route.query.pid}`
        )
        .then(() => {
          alert("삭제가 정상적으로 완료되었습니다.");
          this.$router.push("/tmp_board");
        });
    },
    clickLike() {

       if(this.$store.getters.getAccessToken == null){
         alert("로그인 하셔야 해요");
         return;
       }
      const postingLikeUser = {
        pid: this.$route.query.pid
      };
      axios
        .post(
          `${this.$store.getters.getBoardServer}/freeboard/postinglike`,
          postingLikeUser
        )
        .then(res => {
          this.likeCnt++;
          axios
            .get(
              `${this.$store.getters.getBoardServer}/freeboard/postinghate/${this.$route.query.pid}`
            )
            .then(res => {
              if (res.data.like !== null) {
                axios.delete(
                  `${this.$store.getters.getBoardServer}/freeboard/postinghatecancel/${this.$route.query.pid}`
                );
                this.dislikeCnt--;
              }
            });
        });

      if (this.dislike) {
        this.cancelDislike();
      }
      this.like = true;
    },
    cancelLike() {
      if(this.$store.getters.getAccessToken == null){
         alert("로그인 하셔야 해요");
         return;
       }
      axios
        .delete(
          `${this.$store.getters.getBoardServer}/freeboard/postinglikecancel/${this.$route.query.pid}`
        )
        .then(() => {});

      this.like = false;
      this.likeCnt--;
    },
    clickDislike() {
      if(this.$store.getters.getAccessToken == null){
         alert("로그인 하셔야 해요");
         return;
       }
      const postingLikeUser = {
        pid: this.$route.query.pid
      };
      axios
        .post(
          `${this.$store.getters.getBoardServer}/freeboard/postinghate`,
          postingLikeUser
        )
        .then(res => {
          this.dislikeCnt++;
          axios
            .get(
              `${this.$store.getters.getBoardServer}/freeboard/postinglike/${this.$route.query.pid}`
            )
            .then(res => {
              if (res.data.like !== null) {
                axios.delete(
                  `${this.$store.getters.getBoardServer}/freeboard/postinglikecancel/${this.$route.query.pid}`
                );
              }
            });
        });

      if (this.like) {
        this.cancelLike();
      }
      this.dislike = true;
    },
    cancelDislike() {
      if(this.$store.getters.getAccessToken == null){
         alert("로그인 하셔야 해요");
         return;
       }
      axios
        .delete(
          `${this.$store.getters.getBoardServer}/freeboard/postinghatecancel/${this.$route.query.pid}`
        )
        .then(() => {});
      this.dislike = false;
      this.dislikeCnt--;
    },
    goToBack() {
      this.$router.push({ name: "게시판" });
    },
  }
};
</script>

<style></style>
