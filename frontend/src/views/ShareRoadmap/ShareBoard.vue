<template>
  <div class="nanum-bold">
    <base-header class="pb-3 pt-5 pt-md-8 bg-baby-blue" style="height: 350px;">
      <b-container class="pl-5 pr-2">
        <b-row align-h="around">
          <b-col cols="10">
            <b-form-input form-input
              v-model="inputtext"
              placeholder="검색어를 입력해주세요."
              size="lg"
            ></b-form-input>
          </b-col>
          <b-col cols="1">
            <b-button size="lg" class="fas fa-search px-5" @click="searchSharedRoadmap" variant="peach-quartz"></b-button>
          </b-col>
          <b-col cols="1"></b-col>
        </b-row>
      </b-container>
    
      <b-container class="pt-6">
        <b-row align-h="end">
          <!-- <b-button variant="primary" class="mt-2" @click="detailBoard">detail board</b-button> -->
          <b-button variant="classic-blue" class="mt-2 mr-5" @click="createBoard"
            >새 글 작성</b-button
          >
        </b-row>
      </b-container>
    </base-header>
    <b-container>
      <RoadmapList
        class="row m-0 px-0"
        id="roadmaplist"
        v-if="shareList"
        :rmlist="viewShareList"
        :unlist="usernameList"
      />
      <b-pagination
        v-model="currentPage"
        :per-page="10"
        :total-rows="totalPageNum"
        aria-controls="mhtable"
        align="center"
      ></b-pagination>
    </b-container>
  </div>
</template>

<script>
import RoadmapList from "@/components/Roadmap/ShareRoadmap/RoadmapList.vue";
export default {
  mounted() {
    this.getSharedRoadmap();
  },
  components: {
    RoadmapList
  },
  data() {
    return {
      shareList: [],
      usernameList: [],
      searchCategory: ["title", "name"],
      category: "title",
      inputtext: "",
      currentPage: 1,
      totalPageNum: 3
    };
  },
  computed: {
    viewShareList: function() {
      return this.shareList.slice(
        (this.currentPage - 1) * 10,
        (this.currentPage - 1) * 10 + 10
      );
    }
  },
  methods: {
    createBoard() {
      this.$router.push({ name: "로드맵 공유하기" });
    },
    getSharedRoadmap() {
      axios
        .get(`${this.$store.getters.getRoadmapServer}/roadmapshare/get`)
        .then(response => {
          this.shareList = response.data.roadmapshares;
          this.usernameList = response.data.username;
          this.totalPageNum = response.data.roadmapshares.length;
          this.currentPage = 1;
        })
        .catch(e => {
          //console.log(e);
        });
    },
    searchSharedRoadmap() {
      axios
        .get(
          `${this.$store.getters.getRoadmapServer}/roadmapshare/get/${this.category}/${this.inputtext}`
        )
        .then(response => {
          this.shareList = response.data.roadmapshares;
          this.usernameList = response.data.username;
          this.totalPageNum = response.data.roadmapshares.length;
          this.currentPage = 1;
        })
        .catch(e => {
          //console.log(e);
        });
    },
  }
};
</script>

<style>
#roadmaplist {
  display: block;
}
</style>
