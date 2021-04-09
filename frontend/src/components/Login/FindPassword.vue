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
      <b-row class="justify-content-center">
        <h2>비밀번호를 찾기 위한 이메일을 입력해주세요</h2>
      </b-row>
      <b-row>
        <b-col>
          <b-form-input 
            v-model="inputEmail" 
            placeholder="example@example.com" 
            @keydown.enter="checkEmail"
          >
          </b-form-input>
        </b-col>
      </b-row>
    </b-container>
    <ModalEmailValidation
      :isEmailModal="isEmailModal"
      :userEmail="inputEmail"
      @close="closeModal"
    />
  </div>
</template>

<script>
import ModalEmailValidation from "@/components/Validation/ModalEmailValidation.vue";

export default {
  name: "findPassword",
  components: {
    ModalEmailValidation,
  },
  data() {
    return{
      inputEmail: '',
      isEmailModal: false,
      confirmEmail: false,
    }
  },
  methods: {
    checkEmail() {
      if (this.inputEmail) {
        axios.get(`${this.$store.getters.getUserServer}/user/checkemail/${this.inputEmail}`)
        .then((res) => {
          if (res.data.msg === 'success') {
            // 1. 이메일 발송
            this.emailNumSend()
            // 2. 모달창 보여주기
            this.isEmailModal = true
            // 이메일 인증 진행
            // 비밀번호 찾기 할 수 있는거
            
          } else {
            alert('존재하지 않는 이메일 입니다.')
          }
        })
      }
      // 1. checkEmail이 빈 값이 아니어야 함
      //  axios 요청 보내서 존재하는 이메일인지 확인
      //    1) 존재하는 이메일인 경우 -> 이메일 인증 진행
      //      (1) 이메일 인증 모달 이용해서 (회원가입처럼)
      //      (2) 새 비밀번호 설정 (새 비밀번호, 새 비밀번호 확인)

      //    2) 존재하지 않는 이메일인 경우 -> alert 존재하지 않는 이메일입니다.
        

      // 2. checkEmail이 빈 값이면
      //  무시


    },
    closeModal(e) {
      this.isEmailModal = false;
      if (e === true) {
        this.confirmEmail = true;
        alert('인증되었습니다.')
        this.$router.push({name:'비밀번호재설정', params: {email: this.inputEmail}})
        // 비밀번호 재설정 페이지로 이동
      } else {
        this.confirmEmail = false;
        alert('인증에 실패했습니다. 다시 시도해주세요')
      }
    },
    emailNumSend() {
      axios
      .get(`${this.$store.getters.getUserServer}/email/${this.inputEmail}`)
      .then(res => {
        if (res.data.msg === "success") {
          this.isEmailModal = true;
          this.$store.dispatch("SETCODE", res.data["code"]);
          this.$store.dispatch("SETEMAIL", res.data["email"]);
        } else {
          alert(
            "인증번호 메일 발송에 실패했습니다. 다시 시도해주세요."
          );
          this.isEmailModal = false;
        }
      })
    },
  },
}
</script>

<style>

</style>