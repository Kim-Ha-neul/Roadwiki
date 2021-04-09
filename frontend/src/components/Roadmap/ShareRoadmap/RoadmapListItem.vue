<template>
  <div @click="goDetail" class="nanum-bold">
    <b-card class="mb-1 btn p-0 mx-0">
      <b-card-title id="overviewCardTitle">{{ roadmap.title }}</b-card-title>  
      <b-card-title v-if="roadmap.name" id="overviewCardTitle">{{ roadmap.name }}</b-card-title>  
      <hr class="mt-1 mb-3" />
      <Overview v-if="roadmapData" :roadmapData="roadmapData" id="overview" />
      <b-card-text class="mb-0 pb-0">
        {{ username }} 
        <small v-if="roadmap.likecnt" class="text-muted">| 좋아요 : {{ roadmap.likecnt }}</small>
      </b-card-text>
      <template #footer>
        <small class="text-muted">{{ roadmap.createDate }}</small>
        <br />
      </template>
    </b-card>
  </div>
</template>
<script>
import Overview from "@/components/Roadmap/Overview.vue";
export default {
  components: {
    Overview
  },
  props: ["roadmap", "username","sidx"],
  data() {
    return {
      roadmapData: ""
    };
  },
  methods: {
    getRoadmapData() {
      // rmid활용하여 로드맵 데이터 불러오기
      axios
        .get(
          `${this.$store.getters.getRoadmapServer}/roadmap/get/${this.roadmap.rmid}`
        )
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
    goDetail() {
      if(this.roadmap.title){
      this.$router.push({
        name: "공유로드맵",
        params: { roadmap: this.roadmap, uname: this.username }
      });
      }else{
        
          this.$router.push({
        name: "나의 로드맵",
        params: { sidx: this.sidx }
      });
      }
    }
  },
  created() {
    
    this.getRoadmapData();
  }
};
</script>

<style>
#overview {
  width: 200px;
  height: 150px;
}
#overviewCardTitle {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
