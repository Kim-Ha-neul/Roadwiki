<template>
  <div class="nanum-bold">
    <b-container>
      <b-col>
        <b-row class="justify-content-center">
          <i 
            class="btn ni ni-single-02 nav-btn"
            @click="modalShow=!modalShow"
          >
          </i>
        </b-row>
        <b-row class="justify-content-center">
          <h6 class="mb-0">로그인</h6>
        </b-row>
      </b-col>
    </b-container>

    <!-- <b-button 
      @click="modalShow = !modalShow" 
      variant="white"
      style="color: black; border: 0; outline: 0;"
      class="p-2 ml-3 mt-1"
    >Login</b-button> -->

    <b-modal v-model="modalShow" hide-footer hide-header centered>
      <!-- <template #modal-title class="text-center mx-auto">
        <div class="h1">로그인</div>
      </template> -->
      <div class="h1 text-center display-3 mb-4">
        <!-- <img src="/img/brand/logo_blue.png" alt="roadwiki" width="250rem;"> -->
        <img src="/img/brand/logo_word.png" alt="roadwiki" width="300rem;" />
      </div>
      <base-input
        alternative
        class="mb-3"
        name="Email"
        prepend-icon="ni ni-email-83"
        placeholder="email@email.com"
        v-model="user.email"
        @keydown.enter="signIn"
      >
      </base-input>

      <base-input
        alternative
        class="mb-3"
        name="Password"
        prepend-icon="ni ni-lock-circle-open"
        type="password"
        placeholder="Password"
        v-model="user.password"
        @keydown.enter="signIn"
      >
      </base-input>
        <b-row align-h="between" class="mx-1">
        <!-- 근데 이거 왜 필요한거지 -->
        <!-- <a href="">비밀번호를 잊으셨나요</a> -->
        <router-link to="/find-password">비밀번호를 잊으셨나요?</router-link>
        <br>
        <router-link to="/register">회원가입하기</router-link>
      </b-row>
      <div class="text-center">
        <base-button
          block
          type="peach-quartz"
          native-type="submit"
          class="my-4"
          @click="signIn"
          >로그인하기</base-button
        >
      </div>
    </b-modal>
  </div>
  <!-- <b-modal id="modal-login">
  </b-modal> -->
</template>

<script>
export default {
  data() {
    return {
      modalShow: false,
      user: {
        email: "",
        password: ""
      }
    };
  },
  methods: {
    signIn() {
      this.$store
        .dispatch("LOGIN", this.user)
        .then(() => {
          // this.$router.push({ name: "MAINPAGE" });
        })
        .catch(() => {
          alert("아이디 비밀번호를 확인해주세요.");
          this.user.email = "";
          this.user.password = "";
        });
    }
  }
};
</script>

<style></style>
