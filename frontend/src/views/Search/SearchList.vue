<template>
  <div class="nanum-bold">
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-baby-blue"> </base-header>

    <b-container
      style="background: white; border-radius: 1rem;"
      class="py-4 mt-4"
    >
      <h1>"{{ $route.query.searchKeyword }}"검색 결과 페이지입니다.</h1>
      <hr class="my-2" />
      <h2>유저</h2>
      <ul v-for="(user, idx) in userlist" :key="idx">
        <router-link
          :to="{ name: '프로필', query: { profileId: `${user.uid}` } }"
        >
          {{ user.name }}
        </router-link>
      </ul>

      <hr class="my-2" />
      <h2>게시글</h2>
      <ul v-for="(posting, index) in postinglist" :key="index">
        {{
          posting.title
        }}
      </ul>
      <hr class="my-2" />
      <h2>공유글</h2>
      <ul v-for="(roadmapdata, index) in roadmapdatalist" :key="index">
        {{
          roadmapdata.title
        }}
      </ul>
    </b-container>
  </div>
</template>

<script>
export default {
  name: "searchlist",
  data() {
    return {
      content: "",
      userlist: [],
      postinglist: [],
      roadmapdatalist: []
    };
  },
  methods: {
    sendWord() {
      axios
        .get(`${this.$store.getters.getUserServer}/search/list/${this.content}`)
        .then(res => {
          //console.log(res.data);
          this.content = "";
          this.userlist = res.data.user;
          this.postinglist = res.data.posting;
        })
        .catch(err => {
          //console.log(err);
        });

      axios
        .get(
          `${this.$store.getters.getRoadmapServer}/roadmapshare/get/title/${this.content}`
        )
        .then(res => {
          this.content = "";
          this.roadmapdatalist = res.data.roadmapshares;
        })
        .catch(err => {
          alert("문제가 생겼습니다. 죄송합니다.")
          //console.log(err);
        });
    }
  },
  created() {
    this.content = this.$route.query.searchKeyword;
    this.sendWord();
  }
};
</script>

<style></style>
