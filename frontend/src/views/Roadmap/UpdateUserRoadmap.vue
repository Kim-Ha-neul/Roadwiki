<template>
  <div class="nanum-bold">
    <base-header class="pb-2 pt-2 pt-md-2 bg-baby-blue">
    <b-container>
      <b-row class="pt-2">
          <i class="fas fa-arrow-left fa-2x text-classic-blue" @click="goToBack"></i>
      </b-row>
      <b-row class="pt-4">
        <!-- 장기 중기 단기 선택 라디오 -->
        <b-form-group v-slot="{ ariaDescribedby }" class="mb-0">
          <b-form-radio-group
            id="btn-radios-1"
            v-model="term"
            :options="options"
            :aria-describedby="ariaDescribedby"
            name="radios-btn-default"
            buttons
            :button-variant="btnColors[term-1]"
            class="mr-2"
          ></b-form-radio-group>
        </b-form-group>
        <i 
          class="far fa-question-circle fa-2x mt-3"
          @click="showInfo"
          id="tooltip-info"
        ></i>
        
        <b-tooltip
          target="tooltip-info"
          triggers="hover"
        >
          로드맵 사용법
        </b-tooltip>
      </b-row>
      <b-row align-h="end">
        <!-- 커리큘럼 히스토리 보여주기 -->
        <!--부트스트랩 드롭다운-->
        <b-dropdown id="dropdown-1" text="이전 수정 기록" class="m-md-2">
          <b-dropdown-item
            @click="previewRoadmap(item.rmid)"
            v-for="(item, index) in logData"
            :key="index"
            >{{ item.createDate }} | {{ item.name }}
          </b-dropdown-item>
        </b-dropdown>
        <!-- 커리큘럼 히스토리 끝 -->
      </b-row>
      <b-row>
        <b-form-input
          v-model="roadmapname"
          class="b-inline-block"
          placeholder="로드맵 제목을 입력해 주세요."
        >
        </b-form-input>
      </b-row>
      <b-row align-h="end" class="mt-3">
        <!-- Card stats -->
        <!-- <b-button
          @click="goToBack"
          variant="peach-quartz"
        >
          돌아가기
        </b-button> -->

        <b-button
          @click="updateRoadmap"
          variant="classic-blue"
        >
          수정완료
        </b-button>
      </b-row>
    </b-container>

      <b-modal ref="infoRoadmap" hide-footer hide-header>
        <h2>
          <i class="fas fa-check"></i> 
          로드맵 보기
        </h2>
        <li>
          '내 로드맵 보기'에서 나만의 로드맵을 볼 수 있습니다.
        </li>
        <li>
          상위의 리스트에서 파일을 클릭하시면 원하시는 로드맵을 볼 수 있습니다.
        </li>
        <li>
          수정버튼을 누르시면 로드맵을 수정 할 수 있는 페이지로 넘어갑니다.
        </li>
        <br>
        <h2>
          <i class="fas fa-check"></i> 
          로드맵 생성
        </h2>
        <li>
          원하시는 커리큘럼을 선택하세요.
        </li>
        <li>
          원하시는 커리큘럼에서 내보내기 버튼을 누르시면 내 로드맵으로
          불러오기가 가능합니다.
        </li>
        <li>
          서비스에서 제공하는 로드맵에서 나만의 로드맵으로 맞춤 설정이
          가능합니다!
        </li>
        <br>
        <h2>
          <i class="fas fa-check"></i> 
          로드맵 수정
        </h2>
        <li>
          수정하고 싶은 요소를 클릭해 delete버튼을 누르시면 요소가 삭제됩니다.
        </li>
        <li>
          오른쪽에는 커리큘럼의 정보가 제공됩니다.
        </li>
        <li>
          왼쪽에는 서비스에서 추천해주는 로드맵 요소들을 끌어다 내 로드맵에 옮길
          수 있습니다.
        </li>
        <li>
          선 이수체계에 맞도록 요소의 상, 하, 좌, 우에서 가지를 요소에 연결해
          보세요.
        </li>
        <br>
        <h2>
          <i class="fas fa-check"></i> 
          로드맵 삭제
        </h2>
        <li>
          로드맵이 마음에 들지 않으시다면 삭제도 가능합니다.
        </li>
        <li>
          삭제버튼을 눌러 로드맵을 삭제하세요.
        </li>
      </b-modal>
      <!-- 사용법 modal / end -->

      <br />
      <b-form-input
        v-model="inputText"
        placeholder="커리큘럼 검색하기"
        id="curSearch"
        style="display: inline-block"
      ></b-form-input>

      <span class="ml-3" style="display: inline-block">{{ recommend }} 어떠세요?</span>
    </base-header>

    <b-container fluid class="mt-1">
      <b-row>
        <b-col>
          <b-card no-body class="border-0">
            <div style="width: 100%;">
              <Roadmap
                :roadmapMode="roadmapMode"
                :roadmapData="roadmapData"
                :inputText="inputText"
                :roadmapname="roadmapname"
                @create-roadmap="createRoadmap"
                @get_recommend ="getRecommend"
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
  },
  data() {
    return {
      roadmapData: {},
      roadmapname: "",
      logData: [],
      roadmapMode: 1,
      inputText: "",
      checkRB: false,
      term: 2,
      recommend :"",
      options: [
        { text: "단기", value: 3 },
        { text: "중기", value: 2 },
        { text: "장기", value: 1 }
      ],
      btnColors: [
        'traffic-green',
        'traffic-yellow', 
        'traffic-red', 
      ],
    };
  },
  created() {},
  mounted() {
    if (this.rmid) {
      this.readRoadmap();
      // 수정로그 가져오기
      this.readRoadmapLog();
      // props 데이터 확인 후 없으면 메인페이지로 보내기
    } else {
      this.$router.push({ name: "나의 로드맵" })
    }
  },
  watch: {},
  computed: {},
  methods: {
    createRoadmap() {
      const childRoadmapData = this.$refs.roadmap.serveRoadmap();
      if (this.roadmapname.length > 0) {
        axios
          .post(`${this.$store.getters.getRoadmapServer}/roadmap/create`, {
            uid: this.$store.getters.getUid,
            name: this.roadmapname,
            term: this.term,
            tmp: childRoadmapData
          })
          .then(res => {
            if (res.data.msg == "success") {
              this.$router.push({ name: "나의 로드맵" });
            } else {
              alert("생성에 실패했습니다.");
            }
          })
          .catch(e => {
            alert("axios 오류");
          });
      } else {
        alert('로드맵 제목을 입력해주세요.')
      }
    },
    showInfo() {
      this.$refs['infoRoadmap'].show()
    },
    // read 요청보내기
    readRoadmap() {
      // 페이지 초기화시 rmid여부 확인해서 바탕화면으로 redirect
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
            `${this.$store.getters.getRoadmapServer}/roadmap/get/${this.rmid}`
          )
          .then(res => {
            //console.log(res);
            if (res.data.msg == "success") {
              this.roadmapData = JSON.parse(res.data["roadmaps"].tmp);
              if (
                res.data["roadmaps"].uid < 0 ||
                res.data["roadmaps"].uid == this.$store.getters.getUid
              ) {
                this.roadmapname = res.data["roadmaps"].name;
                this.term = res.data["roadmaps"].term;
              }
            } else {
              alert("데이터 로드에 실패했습니다.");
            }
          })
          .catch(e => {
            alert("axios 오류");
          });
      }
    },

    // 로드맵 로그 가져오는 함수(mounted에서 rmorder를 불러온뒤 실행)
    readRoadmapLog() {
      axios
        .get(
          `${this.$store.getters.getRoadmapServer}/roadmap/log/${this.$store.getters.getUid}/${this.rmorder}`
        )
        .then(res => {
          if (res.data.msg == "success") {
            this.logData = res.data["roadmaps"];
          } else {
            //console.log(e);
            alert("데이터 로드에 실패했습니다.");
          }
        })
        .catch(e => {
          alert("axios 오류");
        });
    },
    // update 요청보내기
    updateRoadmap() {
      const childRoadmapData = this.$refs.roadmap.serveRoadmap();
      if (this.roadmapname.length >0) {
        axios
          .post(`${this.$store.getters.getRoadmapServer}/roadmap/update`, {
            uid: this.$store.getters.getUid,
            rmorder: this.rmorder,
            name: this.roadmapname,
            term: this.term,
            tmp: childRoadmapData
          })
          .then(res => {
            if (res.data.msg == "success") {
              this.$router.push({ name: "나의 로드맵" });
              this.roadmapname = ''
            } else {
              alert("업데이트 실패했습니다.");
            }
          })
          .catch(e => {
            alert("axios 오류");
          });
      } else {
        alert('로드맵 제목을 입력해주세요.')
      }
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
          //console.log(e);
          alert("axios 오류");
        });
    },
    getRecommend(recommend){
      this.recommend = recommend;
    },
    goToBack() {
      this.$router.push({ name: "나의 로드맵" });
    },
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
