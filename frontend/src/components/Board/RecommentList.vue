<template>
  <div class="px-4 pb-3 pt-2 nanum-bold">
    <hr class="my-2" />
    <b-row>
      <b-col cols="8" align-self="center">
        <b-img :src="profileUrl" height="50px" width="50px" rounded="circle"></b-img> {{ recomment.userName }}
      </b-col>
      <b-col v-if="!isUpdate">
        <h5>
          {{ recomment.createDate }}
        </h5>
        <h5 v-if="recomment.modifyDate !== null">
          {{ recomment.modifyDate }}
        </h5>
      </b-col>
    </b-row>
    <b-row v-if="!isUpdate">
      <b-col cols="8">
        <p class="px-3">
          {{ recomment.content }}
        </p>
      </b-col>
      <!-- <b-col align-h="end" class="my-2">
        <i
          class="far fa-thumbs-up fa-2x ml-3 text-peach-quartz"
          v-if="!like"
          @click="clickLike"
          ><span class="h3 ml-1">{{ recomment.likeCnt }}</span></i
        >
        <i
          class="fas fa-thumbs-up fa-2x ml-3 text-peach-quartz"
          v-if="like"
          @click="cancelLike"
          ><span class="h3 ml-1">{{ recomment.likeCnt }}</span></i
        >
      </b-col> -->
    </b-row>
    <b-row v-if="isUpdate">
      <base-input class="mx-3">
        <textarea
          class="form-control mt-2"
          rows="3"
          v-model="recomment.content"
          autofocus
          cols="200"
        >
        </textarea>
        <b-row align-h="end">
          <b-button variant="default" class="mt-2 mr-3" @click="sendRecomment"
            >수정 완료</b-button
          >
        </b-row>
      </base-input>
    </b-row>
    <b-row v-if="!isUpdate">
      <b-col cols="8"> </b-col>
      <!-- 아이콘 가운데정렬 -->
      <b-col
        align-h="end"
        class="my-2"
        v-if="recomment.uid === $store.getters.getUid"
      >
        <i
          class="far fa-trash-alt fa-lg mr-3"
          style="color: tomato"
          @click="deleteRecomment"
        ></i>
        <i
          class="far fa-edit fa-lg mr-3"
          style="color: Dodgerblue"
          @click="updateRecomment"
        ></i>
      </b-col>
    </b-row>
  </div>
</template>

<script>
export default {
  name: "commentList",
  components: {},
  data() {
    return {
      // like: false,
      nickname: "",
      isUpdate: false,
      profileUrl:"",
    };
  },
  props: ["recomment"],
  methods: {
    // clickLike() {
    //   this.like = true;
    //   this.recomment.likeCnt++;
    // },
    // cancelLike() {
    //   this.like = false;
    //   this.recomment.likeCnt--;
    // },
    deleteRecomment() {
      if(this.$store.getters.getAccessToken == null){
         alert("로그인 하셔야 해요");
         return;
       }

      axios
        .delete(
          `${this.$store.getters.getBoardServer}/freeboard/recomment/${this.recomment.rcid}`
        )
        .then(() => {
          this.$emit("sendRecomment");
        });
    },
    sendRecomment() {
      if(this.$store.getters.getAccessToken == null){
         alert("로그인 하셔야 해요");
         return;
       }


      let recomment = {
        rcid: this.recomment.rcid,
        content: this.recomment.content
      };
      axios
        .put(
          `${this.$store.getters.getBoardServer}/freeboard/recomment`,
          recomment
        )
        .then(() => {
          alert("수정되었습니다.");
          this.$emit("sendRecomment");
          this.isUpdate = false;
        });
    },
    updateRecomment() {
      this.isUpdate = true;
    }
  },
  created() {
    // 이미지
    // 해당 계정의 프로필 사진 가져오는 걸로 해야함
    axios
      .get(`${this.$store.getters.getUserServer}/user/image/${this.recomment.uid}`)
      .then(res => {
        this.profileUrl = res.data.path;
      });

    axios
      .get(
        `${this.$store.getters.getUserServer}/user/name/${this.recomment.uid}`
      )
      .then(res => {
        this.nickname = res.data.name;
      })
      .catch(err => {
        alert("죄송합니다. 문제가 생겼습니다.")
        // console.log(err);
      });
  }
};
</script>

<style></style>
