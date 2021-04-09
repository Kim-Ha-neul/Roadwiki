<template>
  <div class="header pb-7 nanum-bold">
    <b-container class="container">
      <div class="header-body text-center mb-4">
        <b-row class="justify-content-center">
          <b-col xl="5" lg="6" md="8" class="px-5 py-5">
            <router-link to="/main">
              <img
                src="/img/brand/logo_word.png"
                alt="roadwiki"
                width="250rem;"
              />
            </router-link>
          </b-col>
        </b-row>
      </div>
    </b-container>

    <b-container class="mb-8 pb-5">
      <!-- Table -->
      <b-row align-v="center">
        <b-col cols="3" class="text-center">
          <h2>새 비밀번호</h2>
        </b-col>
        <b-col cols="9">
          <b-form-input 
              v-model="newPassword"
              placeholder="8자 이상 입력해주세요" 
              type="password"
            >
          </b-form-input>
        </b-col>
      </b-row>
      <b-row align-v="center" class="mt-2">
        <b-col cols="3" class="text-center">
          <h2>새 비밀번호 확인</h2>
        </b-col>
        <b-col cols="9">
          <b-form-input 
              v-model="newPasswordConfirm"
              placeholder="8자 이상 입력해주세요"
              type="password" 
            >
          </b-form-input>
        </b-col>
      </b-row>
      <br>
      <b-row align-h="center">
        <b-button 
          variant="classic-blue"
          class="px-4"
          @click="changePassword"
        >
          비밀번호 재설정
        </b-button>
      </b-row>
    </b-container>
  </div>
</template>

<script>
export default {
  name: "resetPassword",
  components: {
  },
  data() {
    return{
      newPassword: '',
      newPasswordConfirm: '',
    }
  },
  methods: {
    changePassword() {
      if (this.newPassword === this.newPasswordConfirm) {
        if (this.newPassword.length >= 8) {
          let user = {
            email: this.$route.params.email,
            password: this.newPassword,
          }
          axios
          .post(`${this.$store.getters.getUserServer}/user/changepassword`, user)
          .then(res => {
            alert('비밀번호 재설정이 완료되었습니다')
            this.$router.push({name:'MAINPAGE'})
          }) 
          .catch(err => {
          })
        } else {
          alert('8자리 이상 입력해주세요')
        }
      } else {
        alert('비밀번호 확인에 실패했습니다. 다시 입력해주세요')
        this.newPassword = ''
        this.newPasswordConfirm = ''
      }
    },
  },
}
</script>

<style>

</style>