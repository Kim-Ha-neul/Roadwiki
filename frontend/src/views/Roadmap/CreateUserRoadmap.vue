  <template>
  <div class="nanum-bold">
    <base-header class="pb-2 pt-2 pt-md-2 bg-baby-blue">
      <!-- Card stats -->
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
          <!-- 사용법 modal창 아이콘 -->
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

        <b-row>
          <b-form-input
            v-model="roadmapname"
            class="b-inline-block"
            placeholder="로드맵 제목을 입력해 주세요."
          ></b-form-input>
        </b-row>

        <b-row align-h="end" class="mt-3">
          <!-- <b-button
            @click="goToBack"
            variant="peach-quartz"
          >
            돌아가기
          </b-button> -->

          <b-button
            @click="createRoadmap"
            variant="classic-blue"
          >
            생성완료
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
      ></b-form-input>
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
      term: 3,
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
  created() {
    if (this.rmid) {
      this.previewRoadmap(this.rmid)}
  },
  mounted() {
  },
  watch: {},
  computed: {},
  methods: {
    showInfo() {
      this.$refs['infoRoadmap'].show()
    },
    previewRoadmap(rmid) {
      axios
        .get(`${this.$store.getters.getRoadmapServer}/roadmap/get/${rmid}`)
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
    checkCur(e) {
      // 차후에 DB에 요청을 보낸다음 DB정보로 반영
      this.headertext = curriculumName;
    },
    goToBack() {
      this.$router.push({ name: "나의 로드맵" });
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
