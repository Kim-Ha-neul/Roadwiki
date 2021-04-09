<template>
  <div class="nanum-bold">
    <base-header class="pb-6 pb-8 pt-0 bg-baby-blue" style="height: 350px;">
      <b-container>
        <b-row class="pt-5">
          <i class="fas fa-arrow-left fa-2x text-classic-blue cursor-event" @click="goToBack"></i>
        </b-row>
      </b-container>
    </base-header>

    <b-container
      style="background: white; border-radius: 1rem;"
      class="py-4 mt-4"
    >
      <b-row>
        <h1 class="ml-3 mb-0">{{ title }}</h1>
        <b-col></b-col>
        <button
          v-if="useRoadback"
          class="btn"
          style="background-color: rgb(256, 256, 256);"
          @click="goToRoadBack"
        >
          로드백 하기
        </button>
      </b-row>
      <hr class="my-2" />
      <b-row>
        <b-col cols="1" class="pr-0 mb-2">
          <b-img :src="profileUrl" rounded width="50rem" heigth="50rem" />
        </b-col>
        <b-col>
          <h3>작성자 : {{ uname }}</h3>
          <h5>
            {{ createDate }}
          </h5>
        </b-col>
        <button
          v-if="useRoadback && !toggleFeedback"
          class="btn"
          style="background-color: rgb(256, 256, 256);"
          @click="previewRoadback"
        >
          로드백 같이 볼래요
        </button>
        <button
          v-if="useRoadback && isWriter && toggleFeedback"
          class="btn"
          style="background-color: rgb(256, 256, 256);"
          @click="deleteRoadback"
        >
          로드백 다 지울래요
        </button>
        <button
          v-if="useRoadback && toggleFeedback"
          class="btn"
          style="background-color: rgb(256, 256, 256);"
          @click="previewRoadmap"
        >
          로드백 안볼래요
        </button>
      </b-row>
      <hr class="my-2" />

      <b-row>
        <b-container>
          <b-card no-body class="border-0">
            <div class="inline-block" style="width: 100%;">
              <!-- goJS/start-->
              <Roadmap :roadmapMode="roadmapMode" :roadmapData="roadmapData" :isRoadback="isRoadback" />
              <!--goJs/end -->
            </div>
          </b-card>
        </b-container>
        <!-- <p class="px-3">
          {{ content }}
        </p> -->
      </b-row>
      <b-row align-h="end" class="my-2" v-if="isWriter && false">
        <!-- 게시물의 uid와 현재 uid가 동일 할 때 보여줄 내용 -->
        <i
          class="far fa-trash-alt fa-lg mr-3"
          style="color: tomato"
          @click="deleteBoard"
        ></i>
        <i
          class="far fa-edit fa-lg mr-3"
          style="color: Dodgerblue"
          @click="updateBoard"
        ></i>
      </b-row>
      <b-row class="mt-3">
        <i class="btn far fa-thumbs-up fa-2x ml-3" v-if="!like" @click="clickLike"
          ><span class="h3 ml-1">좋아요{{ likeCnt }}</span></i
        >
        <i class="btn fas fa-thumbs-up fa-2x ml-3" v-if="like" @click="cancelLike"
          ><span class="h3 ml-1">좋아요{{ likeCnt }}</span></i
        >
        <button
          class="btn"
          style="background-color: rgb(256, 256, 256);"
          @click="goToCreate"
        >
          내보내기
        </button>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import Roadmap from "@/components/Roadmap/Roadmap.vue";
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";

export default {
  name: "",
  props: {
    roadmap: Object,
    uname: String
  },
  components: {
    Roadmap
  },
  data() {
    return {
      roadmapMode: 0,
      roadmapData: {},
      title: "",
      createDate: "",
      uid: "",
      like: false,
      likeCnt: "",
      pid: "",
      isWriter: false,
      rmid: "",
      useRoadback: "",
      toggleFeedback: false,
      isRoadback: true,
      profileUrl: "",
    };
  },
  created() {},
  mounted() {
    if (this.roadmap) {
      this.getPostingInfo();
      this.previewRoadmap();
      this.likecheck();
      
    } else {
      this.$router.push({ name: "공유로드맵's" });
      return;
    }
  },
  methods: {
    getPostingInfo() {
      this.title = this.roadmap.title;
      this.createDate = this.roadmap.createDate;
      this.uid = this.roadmap.uid;
      this.likeCnt = this.roadmap.likecnt;
      this.pid = this.roadmap.pid;
      this.rmid = this.roadmap.rmid;
      this.useRoadback = this.roadmap.useroadback;
      if (this.uid === this.$store.getters.getUid) {
        this.isWriter = true;
      } else {
        this.isWriter = false;
      }
      axios
          .get(`${this.$store.getters.getUserServer}/user/image/${this.uid}`)
          .then(res => {
            this.profileUrl = res.data.path;
          });
    },
    previewRoadmap() {
      axios
        .get(`${this.$store.getters.getRoadmapServer}/roadmap/get/${this.rmid}`)
        .then(res => {
          if (res.data.msg == "success") {
            this.roadmapData = JSON.parse(res.data["roadmaps"].tmp);
            this.toggleFeedback = false;
          } else {
            alert("데이터 로드에 실패했습니다.");
          }
        })
        .catch(e => {
          //console.log(e);
          alert("axios 오류");
        });
    },
    previewRoadback() {
      axios
        .get(
          `${this.$store.getters.getRoadmapServer}/roadmap/get/comment/${this.rmid}`
        )
        .then(res => {
          if (res.data.msg == "success") {
            this.roadmapData = JSON.parse(res.data["roadmaps"].tmp);
            this.toggleFeedback = true;
          } else {
            alert("데이터 로드에 실패했습니다.");
          }
        })
        .catch(e => {
          //console.log(e);
          alert("axios 오류");
        });
    },
    deleteRoadback() {
      axios
        .delete(
          `${this.$store.getters.getRoadmapServer}/roadcomment/deleteall/${this.rmid}`
        )
        .then(res => {
          if (res.data.msg == "success") {
            this.previewRoadback();
          } else {
            alert("본인의 로드맵이 아니잖아요");
          }
        })
        .catch(e => {
          //console.log(e);
          alert("axios 오류");
        });
    },
    likecheck() {
        if(this.$store.getters.getAccessToken == null){
         return;
       }
      axios
        .get(
          `${this.$store.getters.getRoadmapServer}/roadmapshare/islike/${this.pid}`
        )
        .then(res => {
          if (res.data.msg == "success") {
            this.like = res.data.islike;
          } else {
            alert("데이터 로드에 실패했습니다.");
          }
        })
        .catch(e => {
          //console.log(e);
          alert("axios 오류");
        });
    },
    updateBoard() {
      // const pid = this.$route.query.pid
      // this.$router.push({name: 'update_board', query: { pid }})
    },
    deleteBoard() {
      // axios.delete(`${this.$store.getters.getRoadmapServer}/freeboard/posting/${this.$route.query.pid}`)
      // .then(() => {
      //   alert('삭제가 정상적으로 완료되었습니다.')
      //   this.$router.push('/tmp_board')
      // })
    },
    clickLike() {
        if(this.$store.getters.getAccessToken == null){
         alert("로그인 하셔야 해요");
         return;
       }
      axios
        .put(
          `${this.$store.getters.getRoadmapServer}/roadmapshare/like/${this.$store.getters.getUid}/${this.pid}`
        )
        .then(res => {
          if (res.data.msg == "success") {
          } else {
            alert("데이터 로드에 실패했습니다.");
          }
        })
        .catch(e => {
          //console.log(encodeURI);
        });
      this.like = true;
      this.likeCnt++;
    },
    cancelLike() {
      if(this.$store.getters.getAccessToken == null){
         alert("로그인 하셔야 해요");
         return;
       }

      axios
        .put(
          `${this.$store.getters.getRoadmapServer}/roadmapshare/dislike/${this.$store.getters.getUid}/${this.pid}`
        )
        .then(res => {})
        .catch(e => {
          //console.log(e);
        });

      this.like = false;
      this.likeCnt--;
    },
    goToCreate() {
      this.$router.push({
        name: "로드맵 수정하기",
        params: { rmid: this.rmid, CUMode: 0 }
      });
    },
    goToRoadBack() {
        if(this.$store.getters.getAccessToken == null){
         alert("로그인 하셔야 해요");
         return;
       }
      this.$router.push({ name: "roadback", params: { rmid: this.rmid } });
    },
    goToBack() {
      this.$router.push({ name: "공유로드맵\'s" })
    }
  }
};
</script>

<style></style>
