<template>
  <div class="d-flex flex-column nanum-bold">
    <base-header class="pb-6 pb-8 pt-0 bg-baby-blue" style="height: 350px;">
      <b-container>
        <b-row class="pt-5">
          <i class="fas fa-arrow-left fa-2x text-classic-blue cursor-event" @click="goToBack"></i>
        </b-row>
      </b-container>
    </base-header>

    <b-container>
      <div class="p-4">
        <b-input
          placeholder="제목"
          class="form-control-alternative"
          v-model="title"
        />
      </div>

      <!--부트스트랩 드롭다운-->
      <b-dropdown id="dropdown-1" :text="selectedRoadmapName" class="m-md-2" style="padding-left: 15px;">
        <b-dropdown-item
          @click="checkRoadmapToShare(item.rmid, item.name)"
          v-for="(item, index) in userRoadmapList"
          :key="index"
          >{{ item.name }} | {{ item.createDate }}
        </b-dropdown-item>
      </b-dropdown>

      <div v-if="isActiveRoadback" style="display: inline-block;">
        <b-button @click="changeIsActive" id="deAct">로드백 비활성화</b-button>
      </div>
      <div v-else style="display: inline-block;">
        <b-button @click="changeIsActive" id="Act">로드백 활성화</b-button>
      </div>

      <div v-if="isActiveRoadback" style="display: inline-block; margin-left: 5px;">
        <h4>로드백이 <span style="color: rgb(245, 184, 149);">활성화</span> 상태입니다.</h4>
      </div>
      <div v-else style="display: inline-block; margin-left: 5px;">
        <h4>로드백이 <span style="color: grey;">비활성화</span> 상태입니다.</h4>
      </div>

    <!-- 다이어그램 container -->
    <b-container fluid class="mt-3">
      <b-row>
        <b-col class="p-0">
          <b-card no-body class="border-0">
            <div class="inline-block" style="width: 100%;">
              <!-- goJS/start-->
              <Roadmap :roadmapMode="roadmapMode" :roadmapData="roadmapData" :roadmapname="selectedRoadmapName"/>
              <!--goJs/end -->
            </div>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
    <!-- 다이어그램 container -->

      <div>
        <b-button
          @click="createRoadmapToShare()"
          class="mt-3 mx-4"
          variant="default"
        >
          저장
        </b-button>
      </div>
    </b-container>

  </div>
</template>

<script>
import Roadmap from "@/components/Roadmap/Roadmap.vue";

export default {
  name: "Editor",
  components: {
    Roadmap,
  },
  props: {},
  data() {
    return {
      initialText: "내용을 입력해주세요",
      title: "",
      userRoadmapList: [],
      roadmapData: { 
        class: "go.GraphLinksModel",
        linkFromPortIdProperty: "fromPort",
        linkToPortIdProperty: "toPort",
        nodeDataArray: [
        ],
        linkDataArray: [
        ]},
      roadmapMode: 0,
      rmid: "",
      selectedRoadmapName: "로드맵 선택하기",
      isActiveRoadback: true,
    };
  },
  mounted() {
    
    const uid = String(this.$store.getters.getUid);
    axios
      .get(`${this.$store.getters.getRoadmapServer}/roadmap/list/${uid}`)
      .then(res => {
        if (res.data.msg == "success") {
          // 유저의 roadmaplist
          if (res.data["roadmaps"].length) {
            this.userRoadmapList = res.data["roadmaps"];
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
  methods: {
    checkRoadmapToShare(rmid, name) {
      // 1. 로드맵 데이터 호출
      axios
        .get(
          `${this.$store.getters.getRoadmapServer}/roadmapshare/isshared/${rmid}`
        )
        .then(res => {
          if (res.data.isShared) {
            alert("이미 공유된 로드맵입니다.");
          } else {
            this.selectRoadmapToShare(rmid, name);
          }
        })
        .catch(e => {
          // console.log(e);
          alert("axios 오류");
        });
    },
    selectRoadmapToShare(rmid, name) {
      axios
        .get(`${this.$store.getters.getRoadmapServer}/roadmap/get/${rmid}`)
        .then(res => {
          if (res.data.msg == "success") {
            this.roadmapData = JSON.parse(res.data["roadmaps"].tmp);
          } else {
            alert("데이터 로드에 실패했습니다.");
          }
        })
        .catch(e => {
          alert("axios 오류");
        });
      // 2. uid 호출
      const uid = this.$store.getters.getUid;
      // 3. title 호출
      const title = this.title;
      this.rmid = rmid;

      // 4. item.name => this.에 보관
      this.selectedRoadmapName = name;
    },

    createRoadmapToShare() {
      if (this.rmid && this.title) {
        axios
          .post(`${this.$store.getters.getRoadmapServer}/roadmapshare/insert`, {
            rmid: this.rmid,
            uid: this.$store.getters.getUid,
            title: this.title,
            useroadback: this.isActiveRoadback
          })
          .then(res => {
            this.$router.push({ name: "공유로드맵's" });
          })
          .catch(err => {
          });
      } else if (!this.title) {
        alert("글의 제목을 입력해주세요.");
      } else if (!this.rmid) {
        alert("공유할 로드맵을 선택해주세요.");
      }
    },
    changeIsActive() {
      if (this.isActiveRoadback) {
        this.isActiveRoadback = false;
      } else {
        this.isActiveRoadback = true;
      }
    },
    goToBack() {
      this.$router.push({ name: "공유로드맵\'s" })
    }
  }
};
</script>

<style>
#deAct {
  background-color: rosybrown;
}
#Act {
  background-color: aqua;
}
</style>
