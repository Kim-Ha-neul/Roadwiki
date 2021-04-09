<template>
  <div class="nanum-bold">
    <base-header class="pb-6 pb-8 pt-2 bg-baby-blue" style="height: 350px;">
    <!-- carousel도 컴포넌트화 필요 -->
      <!-- Card stats -->
      <br>
         <carousel :per-page="4" :mouse-drag="true">
          <slide v-for="(item, index) in curriculumData" :key="index"> 
            <b-col>
              
              <b-row v-if="uid < 0" align-h="end" class="mr-1 pb-1">
                <i class="fas fa-times text-traffic-red" @click="deleteRoadmap(item.rmorder)"></i>
              </b-row>
              <b-card 
                id="carouselCard"
                class="text-center mb-2 cursor-event"
                @click="previewRoadmap(item.rmid)"
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

    <b-container fluid class="mt--7">
      <b-row>
        <b-col>
          <div style="text-align : right;">
            <button
              class="btn"
              style="background-color: rgb(256, 256, 256); margin-bottom: 16px;"
              @click="goToCreate"
            >
              내보내기
            </button>
          </div>
          <b-card no-body class="border-0">
            <div style="width: 100%; display: inline-block;">
              <Roadmap :roadmapMode="roadmapMode" :roadmapData="roadmapData" :roadmapname="roadmapname"/>
            </div>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>
<script>
// flatpickr 오류 수정 필요!
import ReadUserRoadMap from "@/views/Roadmap/ReadUserRoadmap";
// import
import router from "@/routes/router";
import Roadmap from "@/components/Roadmap/Roadmap.vue";
export default {
  router,
  name: "",
  components: {
    ReadUserRoadMap,
    Roadmap
  },
  props: {
    mainRmid: Number,
  },
  data() {
    return {
      uid: this.$store.getters.getUid,
      roadmapData: {},
      curriculumData: [],
      rmid: 0,
      memotext: "",
      roadmapMode: 0,
      roadmapname: "",
    };
  },
  created() {
    // carousel로 띄울 officialroadmap 리스트 요청
    axios
      .get(`${this.$store.getters.getRoadmapServer}/roadmap/Official`)
      .then(res => {
        if (res.data.msg == "success") {
          // 유저의 roadmaplist
          if (res.data["roadmaps"].length) {
            this.curriculumData = res.data["roadmaps"];
            // 시작페이지에서 클릭없이 첫 번째 로드맵을 보여줄 수 있도록 저장
            if (this.mainRmid) {
              this.previewRoadmap(this.mainRmid)
            } else {
              this.previewRoadmap(this.curriculumData[0].rmid);
            }
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
  watch: {},
  computed: {},
  methods: {
    previewRoadmap(clickrmid) {
      this.rmid = clickrmid;
      axios
        .get(`${this.$store.getters.getRoadmapServer}/roadmap/get/${clickrmid}`)
        .then(res => {
          if (res.data.msg == "success") {
            this.roadmapData = JSON.parse(res.data["roadmaps"].tmp);
            this.roadmapname = res.data["roadmaps"].name
          } else {
            alert("데이터 로드에 실패했습니다.");
          }
        })
        .catch(e => {
          //console.log(e);
          alert("axios 오류");
        });
    },
    goToCreate() {
      this.$router.push({
        name: "로드맵 생성하기",
        params: { rmid: this.rmid }
      });
    }
  }
};
</script>

<style>
.bntn {
  background-color: aquamarine;
}
#carouselCard:hover {
  box-shadow: 4px 3px 5px rgba(56, 54, 54, 0.5);
}
</style>
