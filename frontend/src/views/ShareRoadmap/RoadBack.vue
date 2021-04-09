<template>
  <div class="nanum-bold">
    <base-header class="pb-10 pt-5 pt-md-2 bg-baby-blue" style="height: 100px;">
      <button
        @click="goToBack"
        class="btn"
        style="background-color: rgb(242, 214, 174);"
      >
        돌아가기
      </button>
      <br />
      <br />
      <br />
      <br />
    </base-header>

    <b-container fluid class="mt--5">
      <b-row>
        <b-col>
          <b-card no-body class="border-0">
            <div style="width: 100%; height : 100%">
              <Roadmap
                :roadmapMode="roadmapMode"
                :roadmapData="roadmapData"
                :inputText="inputText"
                @create-roadmap="createRoadmap"
                :isroadback="isroadback"
                :rmid="sendrmid"
                ref="roadmap"
              />
            </div>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import dropdown from "vue-dropdowns";
import Roadmap from "@/components/Roadmap/Roadmap.vue";

export default {
  name: "",
  components: {
    dropdown: dropdown,
    Roadmap
  },
  props: {
    rmid: {
      type: Number
    },
    rmorder: {
      type: Number
    },
    CUMode: {
      type: Number
    }
  },
  data() {
    return {
      roadmapData: {},
      roadmapname: "",
      logData: [],
      roadmapMode: 1,
      isroadback: true,
      inputText: "",
      sendrmid: ""
    };
  },
  created() {
    this.sendrmid = this.rmid;
  },
  mounted() {
    this.readRoadmap();
    // 수정로그 가져오기
    this.readRoadmapLog();
    // props 데이터 확인 후 없으면 메인페이지로 보내기
  },
  watch: {},
  computed: {},
  methods: {
    // read 요청보내기
    readRoadmap() {
      // 페이지 초기화시 rmid여부 확인해서 바탕화면으로 redirect
      if (this.rmid === undefined) {
        this.$router.push({ name: "read_user_roadmap" });
        return;
      }
      if (this.rmid == 0) {
        this.roadmapData = {
          class: "go.GraphLinksModel",
          linkFromPortIdProperty: "fromPort",
          linkToPortIdProperty: "toPort",
          nodeDataArray: [],
          linkDataArray: []
        };
      } else {
        axios
          .get(
            `${this.$store.getters.getRoadmapServer}/roadmap/get/comment/${this.rmid}`
          )
          .then(res => {
            if (res.data.msg == "success") {
              this.roadmapData = JSON.parse(res.data["roadmaps"].tmp);
              if (
                res.data["roadmaps"].uid < 0 ||
                res.data["roadmaps"].uid == this.$store.getters.getUid
              ) {
                this.roadmapname = res.data["roadmaps"].name;
              }
            } else {
              alert("데이터 로드에 실패했습니다.");
            }
          })
          .catch(e => {
            alert("axios 오류 1");
          });
      }
    },

    // 로드맵 로그 가져오는 함수(mounted에서 rmorder를 불러온뒤 실행)
    readRoadmapLog() {
      if (this.CUMode == 1) {
        axios
          .get(
            `${this.$store.getters.getRoadmapServer}/roadmap/log/${this.$store.getters.getUid}/${this.rmorder}`
          )
          .then(res => {
            if (res.data.msg == "success") {
              this.logData = res.data["roadmaps"];
            } else {
              alert("데이터 로드에 실패했습니다.");
            }
          })
          .catch(e => {
            alert("axios 오류 2");
          });
      }
    },
    // update 요청보내기
    updateRoadmap() {
      const childRoadmapData = this.$refs.roadmap.serveRoadmap();
      axios
        .post(`${this.$store.getters.getRoadmapServer}/roadmap/update`, {
          uid: this.$store.getters.getUid,
          rmorder: this.rmorder,
          name: this.roadmapname,
          tmp: childRoadmapData
        })
        .then(res => {
          if (res.data.msg == "success") {
            this.$router.push({ name: "read_user_roadmap" });
          } else {
            alert("업데이트 실패했습니다.");
          }
        })
        .catch(e => {
          alert("axios 오류 3");
        });
    },
    createRoadmap() {
      const childRoadmapData = this.$refs.roadmap.serveRoadmap();
      axios
        .post(`${this.$store.getters.getRoadmapServer}/roadmap/create`, {
          uid: this.$store.getters.getUid,
          name: this.roadmapname,
          tmp: childRoadmapData
        })
        .then(res => {
          if (res.data.msg == "success") {
            this.$router.push({ name: "read_user_roadmap" });
          } else {
            alert("생성에 실패했습니다.");
          }
        })
        .catch(e => {
          alert("axios 오류 4");
        });
    },
    checkCur(e) {
      // 차후에 DB에 요청을 보낸다음 DB정보로 반영
      this.headertext = curriculumName;
    },
    previewRoadmap(clickrmid) {
      axios
        .get(`${this.$store.getters.getRoadmapServer}/roadmap/get/${clickrmid}`)
        .then(res => {
          if (res.data.msg == "success") {
            this.roadmapData = JSON.parse(res.data["roadmaps"].tmp);
          } else {
            alert("데이터 로드에 실패했습니다.");
          }
        })
        .catch(e => {
          alert("axios 오류 5");
        });
    },
    goToBack() {
      this.$router.push({ name: "공유로드맵" });
    }
  }
};
</script>

<style>
#header {
  padding-bottom: 4px;
}
#curSearch {
  width: 150px;
}
</style>
