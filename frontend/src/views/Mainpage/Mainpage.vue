<template>
  <div class="nanum-bold">
    <b-container class="mt-4">
      <b-carousel
        id="carousel-1"
        v-model="slide"
        :interval="10000"
        controls
        indicators
        background="#ababab"
        img-width="1024"
        img-height="480"
        style="text-shadow: 1px 1px 2px #333;"
        @sliding-start="onSlideStart"
        @sliding-end="onSlideEnd"
      >
        <!-- 인기 로드맵 2개 -->
        <b-carousel-slide v-if="roadmapsharesF">
          <template #img>
            <img
              class="d-block img-fluid w-100"
              width="1024"
              height="480"
              src="https://picsum.photos/1024/480/?image=183"
              alt="image slot"
              style="filter: brightness(0.5)"
              @click="getUserName(roadmapsharesF)"
            >
          </template>
          <h2 style="color: rgba(255, 255, 255, 0.8)">Highly mentioned Roadmap</h2>
          <h3 style="color: white;" class="cursor-event">
            <span style="font-size: 25px;">{{ roadmapsharesF.title }} |</span> {{ roadmapsharesF.createDate }}
          </h3>
          <h4 class="mainContent" style="color: white;">추천수: {{ roadmapsharesF.likecnt }}</h4>
        </b-carousel-slide>

        <b-carousel-slide v-if="roadmapsharesS">
          <template #img>
            <img
              class="d-block img-fluid w-100"
              width="1024"
              height="480"
              src="https://picsum.photos/1024/480/?image=17"
              alt="image slot"
              style="filter: brightness(0.5)"
              @click="getUserName(roadmapsharesS)"
            >
          </template>
          <h2 style="color: rgba(255, 255, 255, 0.8)">Highly mentioned Roadmap</h2>
          <h3 style="color: white;">
            <span style="font-size: 25px;">{{ roadmapsharesS.title }} |</span> {{ roadmapsharesS.createDate }}
          </h3>
          <h4 class="mainContent" style="color: white;">추천수: {{ roadmapsharesS.likecnt }}</h4>
        </b-carousel-slide>

        <!-- 인기 게시글 2개 -->
        <b-carousel-slide v-if="postF">
          <template #img>
            <img
              class="d-block img-fluid w-100"
              width="1024"
              height="480"
              src="https://picsum.photos/1024/480/?image=182"
              alt="image slot"
              style="filter: brightness(0.5)"
              @click="goToBoard(postF.pid)"
            >
          </template>
          <h2 style="color: rgba(255, 255, 255, 0.8);">Popular post</h2>
          <h3 style="color: white;">
            <span style="font-size: 25px;">{{ postF.title }} |</span> {{ postF.createDate }}
          </h3>
          <h4 class="mainContent" style="color: white;">{{ postF.content }}</h4>
        </b-carousel-slide>

        <b-carousel-slide v-if="postS">
          <template #img>
            <img
              class="d-block img-fluid w-100"
              width="1024"
              height="480"
              src="https://picsum.photos/1024/480/?image=1011"
              alt="image slot"
              style="filter: brightness(0.5)"
              @click="goToBoard(postS.pid)"
            >
          </template>
          <h2 style="color: rgba(255, 255, 255, 0.8);">Popular post</h2>
          <h3 style="color: white;">
            <span style="font-size: 25px;">{{ postS.title }} |</span> {{ postS.createDate }}
          </h3>
          <h4 class="mainContent" style="color: white;">{{ postS.content }}</h4>
        </b-carousel-slide>

        <!-- 오피셜 로드맵(싸피, 개발자) -->
        <b-carousel-slide v-for="(official, idx) in officials" :key="idx">
          <template #img>
            <img
              class="d-block img-fluid w-100"
              width="1024"
              height="480"
              :src=pictureList[idx]
              alt="image slot"
              style="filter: brightness(0.5)"
              @click="goToOfficial(official.rmid)"
            >
          </template>
          <h2 style="color: rgba(255, 255, 255, 0.8);">Official Roadmap</h2>
          <h3 style="color: white;">
            <span style="font-size: 25px;">{{ official.name }} |</span> {{ official.createDate }}
          </h3>
          <h4 class="mainContent" style="color: white;">-</h4>
        </b-carousel-slide>
      </b-carousel>
    </b-container>

    <br>
    <!-- 로드맵 리스트 -->
    <b-container>
      <h3 label="roadmaplist" class="ml-2">인기로드맵</h3>
      <RoadmapList 
        class="row m-0 px-0"
        id="roadmaplist"
        v-if="roadmapshareList"
        :rmlist="roadmapshareList"
        :unlist="usernameList"
      />
      
    </b-container> 
    <!-- 게시판 -->
    <b-container class="mt-4 p-1">
      
      <h3 label="popboard" class="ml-2">최신게시글</h3>
      <popular-table id="popboard" @clickRow="clickOn" />

    </b-container> 
  </div>
</template>
<script>
import PopularTable from '../Board/Tables/PopularTable.vue';
import RoadmapList from '@/components/Roadmap/ShareRoadmap/RoadmapList.vue';

export default {
  data() {
    return {
      roadmapsharesF: {},
      roadmapsharesS: {},
      postF: {},
      postS: {},
      officials: [],
      popularTables: [],
      roadmapshareList: [],
      usernameList: [],
      username: "",
      slide: 0,
      sliding: null,
      selPid: "",
      isSelected: false,
      pictureList: [
        "https://picsum.photos/1024/480/?image=361",
        "https://picsum.photos/1024/480/?image=223",
        "https://picsum.photos/1024/480/?image=244",
        "https://picsum.photos/1024/480/?image=1051",
        "https://picsum.photos/1024/480/?image=349",
        "https://picsum.photos/1024/480/?image=258",
        "https://picsum.photos/1024/480/?image=17",
        "https://picsum.photos/1024/480/?image=182",
        "https://picsum.photos/1024/480/?image=254",
      ],
    };
  },
  components: {
    PopularTable,
    RoadmapList,
  },
  methods: {
    onSlideStart(slide) {
      this.sliding = true
    },
    onSlideEnd(slide) {
      this.sliding = false
    },
    getRoadmapshares() {
      axios
        .get(`${this.$store.getters.getRoadmapServer}/roadmapshare/main`)
        .then(res => {
          this.roadmapsharesF = res.data["roadmapshares"][0]
          this.roadmapsharesS = res.data["roadmapshares"][1]
          this.roadmapshareList = res.data["roadmapshares"].slice(0, 4)
          // this.roadmapshareList.map((item) => {
          //   this.usernameList.push(getUsernameList(item.uid))
          // })
          this.getUserNameList();
          this.getPostings(); 
        })
        .catch(res => {
          //console.log(res);
        });
    },
    getPostings() {
      axios
        .get(`${this.$store.getters.getBoardServer}/freeboard/main`)
        .then(res => {
          if (res.data["postings"][0]["content"].includes("[image]")) {
            this.postF = res.data["postings"][0]
            this.postF.content = "이미지가 포함된 게시글입니다."
            } else {
              this.postF = res.data["postings"][0]
            }
          if (res.data["postings"][1]["content"].includes("[image]")) {
            this.postS = res.data["postings"][1]
            this.postS.content = "이미지가 포함된 게시글입니다."
            } else {
              this.postS = res.data["postings"][1]
            }
          this.popularTable = res.data["postings"]
        })
        .catch(res => {
          //console.log(res);
        });
    },
    getOfficial() {
      axios
      .get(`${this.$store.getters.getRoadmapServer}/roadmap/Official`)
      .then(res => {
        if (res.data.msg == "success") {
          if (res.data["roadmaps"].length) {
            this.officials = res.data["roadmaps"].slice(0, 2);
          }
        } else {
          alert("데이터 로드에 실패했습니다.");
        }
      })
      .catch(e => {
        //console.log(e);
        alert("axios 오류");
      });
    },
    // 수정 ㄴ
    getUserName(roadmap) {
      axios.get(`${this.$store.getters.getUserServer}/user/name/${roadmap.uid}`)
      .then(res => {
        const username = res.data.name
        this.goToShare(roadmap, username)
      })
      .catch(err => {
        //console.error(err)
      })
    },
    getUserNameList() {
      for(var i = 0; i < this.roadmapshareList.length; i++){
        axios.get(`${this.$store.getters.getUserServer}/user/name/${this.roadmapshareList[i].uid}`)
        .then(res => {
          this.usernameList.push(res.data.name);
        })
        .catch(err => {
          //console.error(err)
        })
      }
    },
    goToShare(roadmap, username) {
      this.$router.push({ name: "공유로드맵" , params: { "roadmap": roadmap, "uname": username }})
    },
    goToBoard(pid) {
      this.$router.push({ name: "게시글", query: { pid }})
    },
    goToOfficial(rmid) {
      this.$router.push({ name: "공식 로드맵" , params: { "mainRmid": rmid } })
    },
    clickOn(pid) {
      this.isSelected = true;
      this.selPid = pid;
    },
  },
  created() {
    this.getRoadmapshares();
    this.getOfficial();
  }
};
</script>

<style scoped>
.mainContent {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>