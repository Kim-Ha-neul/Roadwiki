<template>
  <div class="nanum-bold">
    <!-- header 시작 -->
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-default">
    </base-header>
    <!-- 카테고리선택 부분 -->
    <input
      type="radio"
      id="LargeCategory"
      value="LargeCategory"
      v-model="picked"
    />
    <label for="LargeCategory">LargeCategory</label>
    <br />
    <input
      type="radio"
      id="MediumCategory"
      value="MediumCategory"
      v-model="picked"
    />
    <label for="MediumCategory">MediumCategory</label>
    <br />
    <input
      type="radio"
      id="SmallCategory"
      value="SmallCategory"
      v-model="picked"
    />
    <label for="SmallCategory">SmallCategory</label>
    <br />
    <!-- radio 선택에 따른 value값 전달  -->
    <span>선택한 라디오버튼의 value값 : {{ this.picked }}</span>
    <hr />
    <!-- 대분류-->
    <div v-if="picked === 'LargeCategory'">
      <p>대분류 입력란</p>
      <input v-model="largeName" placeholder="대분류" />
      <br />
      <textarea v-model="largeExplanation" placeholder="대분류 설명"></textarea>
      <button
        @click="largeSubmit"
        class="btn"
        style="background-color: rgb(181, 199, 211); display: block; "
      >
        제출하기
      </button>
    </div>
    <!-- 중분류 -->
    <div v-else-if="picked === 'MediumCategory'">
      <!-- 대분류 선택 -->
      <p>대분류를 선택하세요</p>
      <select v-model="Largeselected">
        <option disabled value="">--------</option>
        <!--이 부분 서버 대분류 정보 가져와야 함  -->
        <!-- <div v-for=""></div> 
                <option></option>-->
        <option>A</option>
        <option>B</option>
        <option>C</option>
      </select>
      <span>선택함: {{ this.Largeselected }}</span>
      <br />
      <!-- 중분류 타이핑 -->
      <p>중분류 입력란</p>
      <input v-model="MediumName" placeholder="중분류" />
      <br />
      <textarea
        v-model="MediumExplanation"
        placeholder="중분류 설명"
      ></textarea>
      <button
        class="btn"
        style="background-color: rgb(181, 199, 211); display: block; "
      >
        제출하기
      </button>
    </div>
    <!-- 소분류 -->
    <div v-else-if="picked === 'SmallCategory'">
      소분류
      <p>대분류를 선택하세요</p>
      <select v-model="Largeselected">
        <option disabled value="">--------</option>
        <!--이 부분 서버 대분류 정보 가져와야 함  -->
        <!-- <div v-for=""></div> 
                <option></option> -->
        <option>A</option>
        <option>B</option>
        <option>C</option>
      </select>
      <span>선택함: {{ this.Largeselected }}</span>
      <br />
      <p>중분류를 선택하세요</p>
      <select v-model="Mediumselected">
        <option disabled value="">--------</option>
        <!--이 부분 서버 중분류 정보 가져와야 함  -->
        <!-- <div v-for=""></div> 
                <option></option>-->
        <option>A</option>
        <option>B</option>
        <option>C</option>
      </select>
      <span>선택함: {{ this.Mediumselected }}</span>
      <br />
      <!-- 소분류 타이핑 -->
      <p>소분류 입력란</p>
      <input v-model="SmallName" placeholder="소분류" />
      <br />
      <textarea v-model="SmallExplanation" placeholder="소분류 설명"></textarea>
      <button
        class="btn"
        style="background-color: rgb(181, 199, 211); display: block; "
      >
        제출하기
      </button>
    </div>
  </div>
</template>

<script>
// import
import router from "@/routes/router";

export default {
  name: "AdminRoadmap",
  components: {},
  data() {
    return {
      picked: "",
      // 대분류 관련 정보
      largeName: "",
      largeExplanation: "",
      // 중분류 관련 정보
      Largeselected: "",
      MediumName: "",
      MediumExplanation: "",
      // 소분류 관련 정보
      Mediumselected: "",
      SmallName: "",
      SmallExplanation: "",
      // 커리큘럼 데이터
      curriculumData: []
    };
  },
  created() {
    // 데이터 베이스에 있는 공식로드맵 대,중분류를 가져와야함
    axios
      .get(`${this.$store.getters.getRoadmapServer}/curriculum/suggest`)
      .then(res => {
        // this.curriculumData = res
        
      })
      .catch(e => {
        alert("axois 오류");
      });
  },
  mounted() {},
  watch: {},
  computed: {},
  methods: {
    //대분류 서버 전송
    largeSubmit() {
      alert("서버에 정보를 전송했습니다.");
    },
    // 중분류 서버 전송
    MediumSubmit() {
      // 드롭박스로 선택한 값 : this.Largeselected
      // axios.get(`${this.$store.getters.getRoadmapServer}/curriculum/${this.Largeselected}`)
      //   .then{}
    }
  }
};
</script>

<style></style>
