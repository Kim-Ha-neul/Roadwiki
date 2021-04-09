<template>
  <div class="nanum-bold">
    <base-header class="pb-9 pt-9 bg-baby-blue">
    </base-header>
    <!-- <b-container class="align-items-center"> -->
      <!-- <span class="bg-baby-blue"></span> -->
      <!-- <b-row class="justify-content-end"><BackgroundImg /></b-row> -->
    <!-- </b-container> -->
    
    <b-container fluid class="mt--6">
      <b-card no-body class="card-profile" alt="Image placeholder" img-top>
        <b-row class="justify-content-start">
          <b-col lg="3" class="order-lg-2">
            <b-container class="card-profile-image">
              <b-row>
                <b-img
                  :src="profileUrl"
                  @click="modalShow = !modalShow"
                  rounded="circle"
                  height="200px"
                  width="200px"
                  class="cursor-event"
                />
              </b-row>
              <b-row class="justify-content-end"> </b-row>
            </b-container>
          </b-col>
        </b-row>
        <b-card-header
          class="text-center border-0 pt-8 pt-md-4 pb-0 pb-md-4 mb-4"
        >
          <!-- <h1 class="display-1">
            email.com
          </h1> -->
        </b-card-header>
        <b-card-body class="pt-0">
          <b-row>
            <b-col>
              <div
                class="card-profile-stats d-flex justify-content-center mt-md-5"
              ></div>
            </b-col>
          </b-row>
          <b-container>
            <!-- <hr class="my-4"> -->
            <b-row class="mb-3">
              <b-col cols="3" class="text-center" align-self="center">
                <h2>
                  <!-- <i class="ni ni-hat-3 mr-2"></i> -->
                  닉네임
                </h2>
              </b-col>
              <b-col>
                <b-form-input
                  :value="nickname"
                  v-model="nickname"
                ></b-form-input>
              </b-col>
            </b-row>
            <!-- <b-row class="mb-3">
              <b-col cols="3" class="text-center" align-self="center">
                <h2>
                  전공
                </h2>
              </b-col>
              <b-col>
                <b-form-input :value="major" v-model="major"></b-form-input>
              </b-col>
            </b-row> -->
            <b-row class="mb-3">
              <b-col cols="3" class="text-center" align-self="center">
                <h2>
                  <!-- <i class="ni ni-hat-3 mr-2"></i> -->
                  블로그
                </h2>
              </b-col>
              <b-col>
                <!-- address 수정 -->
                <b-form-input :value="address" v-model="address"></b-form-input>
              </b-col>
            </b-row>
            <b-row class="mb-3">
              <b-col cols="3" class="text-center" align-self="center">
                <h2>
                  <!-- <i class="ni ni-hat-3 mr-2"></i> -->
                  한 줄 소개
                </h2>
              </b-col>
              <b-col>
                <b-form-textarea :value="oneline" v-model="oneline" rows="5">
                </b-form-textarea>
              </b-col>
            </b-row>
            <b-row class="mb-3">
              <b-col cols="3" class="text-center" align-self="center">
                <h2>
                  <!-- <i class="ni ni-hat-3 mr-2"></i> -->
                  관심 개발 분야
                </h2>
              </b-col>
              <b-col cols="7" align-self="center">
                <b-button
                  variant="peach-quartz"
                  size="sm"
                  pill
                  class="mx-1"
                  v-for="(keyword, idx) in keywordtexts"
                  :key="idx"
                >
                  {{ keyword }}
                </b-button>
              </b-col>
              <FlavourContent
                class="col align-self-center pl-5 ml-5"
                @changeFlavour="changeFlavour"
                :keywords="keywords"
              />
            </b-row>
            <!-- <b-row class="mb-3">
              <b-col cols="3" class="text-center" align-self="center">
                <h2>
                  프로필 사진
                </h2>
              </b-col>
              <b-col>
                <h3>여기에 프로필 사진 파일 명</h3>
              </b-col>
              <b-col>
                <div>
                  <b-button size="sm" @click="modalShow = !modalShow"
                    >사진📷</b-button
                  > -->

                  <b-modal v-model="modalShow" hide-footer>
                    <template #modal-title>
                      <h1>프로필 업로드</h1>
                    </template>
                    <div>
                      <b-form-file
                        v-model="file1"
                        placeholder="Choose a file or drop it here..."
                        drop-placeholder="Drop file here..."
                      ></b-form-file>
                      <div class="mt-3">
                        Selected file: {{ file1 ? file1.name : "" }}
                      </div>
                    </div>
                    <div class="text-center">
                      <base-button
                        type="primary"
                        native-type="submit"
                        class="my-4"
                        @click="uploadHandler"
                        >확인</base-button
                      >
                    </div>
                  </b-modal>
                <!-- </div>
              </b-col>
            </b-row> -->

            <hr class="my-4" />
            <b-row class="justify-content-end">
              <b-button
                variant="traffic-red"
                class="mt-4 mr-4"
                @click="withDrawal"
                size="sm"
                >회원탈퇴</b-button
              >
            </b-row>
            <b-row class="justify-content-center">
              <b-button
                pill
                variant="classic-blue"
                class="mt-4 px-5"
                size="lg"
                @click="updateHandler"
                >정보수정</b-button
              >
            </b-row>
          </b-container>
        </b-card-body>
      </b-card>
    </b-container>
  </div>
</template>
<script>
// import EditProfileForm from './UserProfile/EditProfileForm.vue';
import UserCard from "./UserProfile/UserCard.vue";
import LoginContent from "@/components/Login/LoginContent.vue";
import FlavourContent from "@/components/Profileupdate/FlavourContent.vue";
// import ProfileImg from '@/components/Profileupdate/ProfileImg.vue';
// import BackgroundImg from "@/components/Profileupdate/BackgroundImg.vue";

export default {
  components: {
    // EditProfileForm,
    UserCard,
    LoginContent,
    FlavourContent
    // ProfileImg,
    // BackgroundImg
  },
  data() {
    return {
      nickname: "",
      introduction:
        "술잔을 들자하니 천하가 내발아래 있고 6팀 친구들 또한 옆에 있으니 염라대왕 두렵지 않구나",
      profileImg: "",
      backImg: "",
      keywords: [],
      keywordtexts: [],
      options: [],
      follower: "",
      following: "",
      boards: "",
      comments: "",
      email: "",
      modalShow: false,
      file1: null,
      files: [],
      uid: "",
      profileUrl: "",
      address: "https://github.com",
      oneline: "",
      major: ""
    };
  },
  created() {
    this.uid = this.$store.getters.getUid;

    axios.get(`${this.$store.getters.getUserServer}/user/image/${this.uid}`).then(res => {
      this.profileUrl = res.data.path;
    });

    axios
      .get(`${this.$store.getters.getUserServer}/user/info/${this.uid}`)
      .then(res => {
        this.nickname = res.data.name;
        this.email = res.data.email;
        this.keywords = res.data.keywords;
        this.keywordtexts = res.data.keywordtexts;
        this.address = res.data.address;
        this.major = res.data.major;
        this.oneline = res.data.oneline;
      })
      .catch(() => {
        alert("로그인이 필요한 서비스입니다.");
        this.$store.dispatch("LOGOUT").then(() => {
          this.$router.replace("/");
        });
      });
    axios.get(`${this.$store.getters.getUserServer}/keyword/list`).then(res => {
      this.options = res.data.keywords;
    });
  },
  methods: {
    getPic(profileUrl) {
      return require(this.profileURL);
    },
    withDrawal() {
      axios
        .delete(`${this.$store.getters.getUserServer}/user/withdraw`)
        .then(() => {
          alert("회원 탈퇴가 완료되었습니다.");
          this.$store.dispatch("LOGOUT").then(() => {
            this.$router.replace("/main");
          });
        })
        .catch(() => {
          alert("오류가 발생했습니다. 다시 시도해주세요.");
        });
    },
    changeFlavour(keywords) {
      // 다이렉트로 넣기
      let tempkeywordtexts = new Array(keywords.length);
      for (let i = 0; i < keywords.length; i++) {
        tempkeywordtexts[i] = this.options[keywords[i] - 1].word;
      }
      this.keywordtexts = tempkeywordtexts;
      this.keywords = keywords;
    },
    updateHandler() {
      // 보낼때 명명이 중요함
      let user = {
        uid: this.uid,
        name: this.nickname,
        address: this.address,
        major: this.major,
        oneline: this.oneline,
        keyword: this.keywords
      };
      axios
        .put(`${this.$store.getters.getUserServer}/user/modify`, user)
        .then(res => {
          if (res.data.msg == "success") {
            alert("회원 수정이 완료되었습니다.");
            this.$router.push("/profile");
          } else alert("회원 수정 시 문제가 발생했슴다");
        });
    },
    async uploadHandler() {
      this.modalShow = false;
      var formData = new FormData();
      formData.append("file", this.file1);
      // var photoFile = document.getElementById("photo");
      // frm.append("photo", photoFile.files[0]);

      // this.modalShow = false;

      await axios
        .post(`${this.$store.getters.getUserServer}/user/pic`, formData, {
          headers: { "content-type": "multipart/form-data" }
        })
        .then(res => {
          this.profileUrl = res.data.path;
          this.$store.dispatch();
        });
    }
  }
};
</script>
<style></style>
