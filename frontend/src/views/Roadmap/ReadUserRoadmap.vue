<template>
  <div class="nanum-bold">
    <!-- header 시작 -->
    <base-header class="pb-6 pb-8 pt-2 bg-baby-blue" style="height: 350px;">
    <!-- carousel도 컴포넌트화 필요 -->
      <!-- Card stats -->
      <br>
         <carousel :per-page="4" :mouse-drag="true">
          <slide v-for="(item, index) in userRoadmapList" :key="index"> 
            <b-col>
              
              <b-row align-h="end" class="mr-1 pb-1">
                <i class="fas fa-times text-traffic-red" @click="deleteRoadmap(item.rmorder)"></i>
              </b-row>
              <b-card 
                id="carouselCard"
                class="text-center mb-2"
                @click="previewRoadmap(item.rmorder, item.rmid)"
              >
                <!-- 장기 -->
                <div v-if="item.term == 1">
                  <b-card-body class="border border-traffic-green rounded" align="center">
                    <b-card-title class="h3">
                      {{ item.name }}
                    </b-card-title> 
                    <b-card-text class="small text-muted text-center">
                      {{ item.createDate }}
                    </b-card-text>
                  </b-card-body>
                </div>

                <!-- 중기 -->
                <div v-else-if="item.term == 2">
                  <b-card-body class="border border-traffic-yellow rounded" align="center">
                    <b-card-title class="h3">
                      {{ item.name }}
                    </b-card-title> 
                    <b-card-text class="small text-muted text-center">
                      {{ item.createDate }}
                    </b-card-text>
                  </b-card-body>
                </div>

                <!-- 단기 -->
                <div v-else>
                  <b-card-body class="border border-traffic-red rounded" align="center">
                    <b-card-title class="h3">
                      {{ item.name }}
                    </b-card-title> 
                    <b-card-text class="small text-muted text-center">
                      {{ item.createDate }}
                    </b-card-text>
                  </b-card-body>
                </div>
              </b-card>

            </b-col>
          </slide>
        </carousel>
    </base-header>
    <!-- header 종료 -->
    <!-- 다이어그램 container -->
    <b-container fluid class="mt--7">
      <b-row>
        <b-col>
          <div style="text-align: right;">
            <b-button class="mb-3 mr-2" @click="goToCreate" variant="cornhusk">생성하기</b-button>
            <b-button class="mb-3 ml-2" @click="goToUpdate" variant="classic-blue">수정하기</b-button>
          </div>
          <b-card no-body class="border-0">
            <div class="inline-block" style="width: 100%;">
              <!-- goJS/start-->
              <Roadmap :roadmapMode="roadmapMode" :roadmapData="roadmapData" :roadmapname="roadmapname"/>
              <!--goJs/end -->
            </div>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
    <!-- 다이어그램 container -->
  </div>
</template>
<script>
import Roadmap from "@/components/Roadmap/Roadmap.vue";
import UpdateUserRoadmap from "@/views/Roadmap/UpdateUserRoadmap";
import router from "@/routes/router";
import { Carousel, Slide } from "vue-carousel";

export default {
  router,
  name: "ReadUserRoadmap",
  components: {
    Roadmap,
    Carousel,
    Slide,
    UpdateUserRoadmap
  },
  data() {
    return {
      roadmapMode: 0,
      // 서버 연결시 반드시 비어있는 리스트로 반환한다.
      userRoadmapList: [],
      isSelectCard: false,
      rmid: 0,
      rmorder: 0,
      roadmapData: {},
      roadmapname: "",
    };
  },
  created() {
    // carousel로 띄울 roadmap 리스트 요청
    this.getUserRoadmapList();
  },
  mounted() {},
  computed: {},
  methods: {
    // 리스트
    previewRoadmap(clickrmorder, clickrmid) {
      this.isSelectCard = true;
      this.rmid = clickrmid;
      this.rmorder = clickrmorder;
      axios
        .get(`${this.$store.getters.getRoadmapServer}/roadmap/get/${clickrmid}`)
        .then(res => {
          if (res.data.msg == "success") {
            this.roadmapData = JSON.parse(res.data["roadmaps"].tmp);
            this.roadmapname = res.data["roadmaps"].name
            this.load();
          } else {
            alert("데이터 로드에 실패했습니다.");
          }
        })
        .catch(e => {
          //console.log(e);
          alert("axios 오류");
        });
    },
    deleteRoadmap(clickrmorder){
      axios
        .delete(`${this.$store.getters.getRoadmapServer}/roadmap/delete/${this.$store.getters.getUid}/${clickrmorder}`)
        .then(res => {
          //console.log(res);
          if (res.data.msg === "success") {
            this.getUserRoadmapList();
            alert("삭제 완료");
          } else {
            alert("데이터 로드에 실패했습니다.");
          }
        })
        .catch(e => {
          //console.log(e);
          alert("axios 오류");
        });
    },

    // 외부 json파일 초기화면에 출력
    load() {
      // ismounted? 어디서 쓰이는지 확인
      this.ismounted = true;
    },
    // updateuserroadmap을 create, update모두 가능하도록 변경(mode로 나눈다.)
    goToCreate() {
      this.$router.push({
        name: "로드맵 생성하기",
        params: { rmid: 0 }
      });
    },
    goToUpdate() {
      this.$router.push({
        name: "로드맵 수정하기",
        params: { rmid: this.rmid, rmorder: this.rmorder }
      });
    },
    getUserRoadmapList() {
      axios
      .get(
        `${this.$store.getters.getRoadmapServer}/roadmap/list/${this.$store.getters.getUid}`
      )
      .then(res => {
        //console.log(res);
        if (res.data.msg == "success") {
          // 유저의 roadmaplist
          if (res.data["roadmaps"].length) {
            this.userRoadmapList = res.data["roadmaps"];
            // 시작페이지에서 클릭없이 첫 번째 로드맵을 보여줄 수 있도록 저장
            let idx = this.$route.params.sidx

            if(!idx)
              idx = 0;
            this.previewRoadmap(
              this.userRoadmapList[idx].rmorder,
              this.userRoadmapList[idx].rmid
            );
          }else{
            this.userRoadmapList = [];
            this.roadmapData = {};
            this.load();
          }
        } else {
          alert("데이터 로드에 실패했습니다.");
        }
      })
      .catch(e => {
        //console.log(e);
        alert("axios 오류");
      });
    }
  }
};
</script>

<style>
#carouselCard:hover {
  box-shadow: 4px 3px 7px rgba(56, 54, 54, 0.5);
  transition: 0.5s;
}
/* .roadmap {
  width: 175px;
  height: 140px;
  border-width: 10px;
  border-style: solid;
  overflow: hidden;
} */
</style>