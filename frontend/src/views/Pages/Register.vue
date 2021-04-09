<template>
  <div class="nanum-bold">
    <!-- register -->
    <!-- Header -->
    <div class="header pb-7">
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
    </div>
    <!-- Page content -->
    <b-container class="mt--8 pb-5">
      <!-- Table -->
      <b-row class="justify-content-center">
        <b-col lg="6" md="8">
          <b-card no-body class="bg-secondary border-0">
            <b-card-body class="px-lg-5 pt-0">
              <b-row align-h="end" class="mb-2 h5">
                (*표시가 있는 항목은 필수 입력 항목입니다.)
              </b-row>
              <div class="text-center text-muted mb-4">
                <!-- <br> -->
                <!-- <h1>회원가입</h1> -->
              </div>
              <validation-observer
                v-slot="{ handleSubmit }"
                ref="formValidator"
              >
                <b-form role="form" @submit.prevent="handleSubmit(onSubmit)">
                  <!-- required속성이 true이면 The Name field is required라는 창이 뜸 -->
                  <base-input
                    alternative
                    class="mb-3"
                    label="닉네임*"
                    prepend-icon="ni ni-hat-3"
                    placeholder="닉네임"
                    name="닉네임"
                    :rules="{ required: true }"
                    v-model="name"
                    @blur="canUseNickName"
                  >
                  </base-input>

                  <div class="row">
                    <div class="col-9">
                      <base-input
                        alternative
                        label="이메일*"
                        class="mb-3"
                        prepend-icon="ni ni-email-83"
                        placeholder="example123@ssafy.com"
                        name="이메일"
                        :rules="{ required: true, email: true }"
                        v-model="email"
                        v-if="!confirmEmail"
                      >
                      </base-input>
                      <base-input
                        alternative
                        prepend-icon="ni ni-email-83"
                        v-if="confirmEmail"
                        v-model="email"
                        placeholder="email"
                        disabled
                      >
                      </base-input>
                    </div>
                    <div class="col-3 pl-0 mt-4 pt-2">
                      <!-- 만약 기존에 계정이 존재하는 이메일이라면 this email is already taken 보여주기 -->
                      <b-button
                        variant="peach-quartz"
                        v-if="!confirmEmail"
                        @click="emailNumSend"
                        class="mr-0 ml-0 nanum-bold"
                        size="md"
                        >인증하기</b-button
                      >
                      <ModalEmailValidation
                        :isEmailModal="isEmailModal"
                        :userEmail="email"
                        @close="closeModal"
                      />
                      <!-- <ModalEmailValidation/> -->
                      <b-button v-if="confirmEmail" disabled>인증완료</b-button>
                    </div>
                  </div>

                  <base-input
                    alternative
                    label="비밀번호*"
                    class="mb-3"
                    prepend-icon="ni ni-lock-circle-open"
                    placeholder="비밀번호"
                    type="password"
                    name="비밀번호"
                    vid="password"
                    :rules="{ required: true, min: 8 }"
                    v-model="password"
                  >
                  </base-input>

                  <base-input
                    alternative
                    label="비밀번호 확인*"
                    class="mb-3"
                    prepend-icon="ni ni-lock-circle-open"
                    placeholder="비밀번호 확인"
                    type="password"
                    name="비밀번호 확인"
                    :rules="{ required: true, min: 8, password: password }"
                    v-model="rePassword"
                  >
                  </base-input>
                  <hr class="my-4" />
                  <div>
                    <b-form-group v-slot="{ ariaDescribedby }">
                      <div class="form-control-label">관심 개발 분야*</div>
                      <div class="form-control-label my-1">
                        (1순위부터 3순위까지 순서대로 체크해주세요.)
                      </div>
                      <!-- <p>1순위부터 체크해주세요</p> -->
                      <b-container class="mt-2 bg-white rounded py-2 shadow-sm">
                        <b-form-checkbox
                          v-for="option in options"
                          v-model="selected"
                          :key="option.kwid"
                          :value="option.kwid"
                          :aria-describedby="ariaDescribedby"
                          name="flavour-3a"
                          class="py-1"
                        >
                          <strong>{{ option.word }}</strong>
                        </b-form-checkbox>
                      </b-container>
                    </b-form-group>
                  </div>
                  <hr class="my-4" />
                  <b-row class="my-4">
                    <b-col cols="12">
                      <base-input
                        :rules="{ required: { allowFalse: false } }"
                        name="회원약관 동의"
                        Policy
                      >
                        <b-form-checkbox v-model="agree">
                          <span
                            class="text-muted source-han-serif-k-extralight"
                          >
                            <b-link
                              v-b-modal.modal-scrollable
                              variant="link"
                              class="m-0 p-0 nanum-bold"
                              @click="isPolicyModal = true"
                            >
                              회원 약관
                            </b-link>
                            에 동의합니다.
                            <ModalPolicy
                              v-if="isPolicyModal"
                              @close="isPolicyModal = false"
                              @accept="accept"
                            />
                          </span>
                        </b-form-checkbox>
                      </base-input>
                    </b-col>
                  </b-row>
                  
                  <div class="text-center">
                    <b-button
                      block
                      type="submit"
                      variant="traffic-yellow"
                      class="mt-4 nanum-bold"
                      @click="signUp"
                      >가입하기</b-button
                    >
                  </div>
                  <div class="text-center"></div>
                </b-form>
              </validation-observer>
            </b-card-body>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import ModalEmailValidation from "@/components/Validation/ModalEmailValidation.vue";
import ModalPolicy from "@/components/Validation/ModalPolicy.vue";

import { extend } from "vee-validate";

extend("password", {
  params: ["target"],
  validate(value, { target }) {
    return value === target;
  },
  message: "비밀번호가 일치하지 않습니다."
});

export default {
  name: "register",
  components: {
    ModalEmailValidation,
    ModalPolicy
  },
  data() {
    return {
      name: "",
      email: "",
      password: "",
      rePassword: "",
      isPolicyModal: false,
      confirmEmail: false,
      agree: false,
      selected: [],
      options: [],
      isLoginModal: false,
      isEmailModal: false
    };
  },
  created() {
    axios.get(`${this.$store.getters.getUserServer}/keyword/list`).then(res => {
      this.options = res.data.keywords;
    });
  },
  methods: {
    // 닉네임 입력창에서 포커싱8이 사라지는 순간 -> 닉네임 사용가능한지 확인 (이미 있는 닉네임인지)
    canUseNickName() {
      axios
        .get(`${this.$store.getters.getUserServer}/user/existname/${this.name}`)
        .then(res => {
          if (this.name && res.data.msg === "fail") {
            alert('이미 존재하는 닉네임입니다.')
            this.name = ''
          }
          
          //msg == fail 이면 중복
        })
        .catch(err => {
          alert('오류가 발생했습니다.')
        });
    },
    onSubmit() {
      // this will be called only after form is valid. You can do an api call here to register users
    },
    closeModal(e) {
      this.isEmailModal = false;
      if (e === true) {
        this.confirmEmail = true;
      } else {
        this.confirmEmail = false;
      }
    },
    accept() {
      this.isPolicyModal = false;
      this.agree = true;
    },
    emailNumSend() {
      // 이미 존재하는 이메일인 경우, 존재하는 이메일 경고창 -> 이메일 인증 막기
      axios
        .get(`${this.$store.getters.getUserServer}/user/canjoin/${this.email}`)
        .then(res => {
          if (res.data.msg === "success") {
            axios
              .get(`${this.$store.getters.getUserServer}/email/${this.email}`)
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
              });
          } else {
            alert(
              "이미 존재하는 이메일입니다. 다른 이메일 주소를 입력해주세요."
            );
          }
        });
    },
    signUp() {
      // 인풋이 다 안채워지면 회원가입 버튼이 비활성화되게 로직 추가
      const user = {
        email: this.email,
        name: this.name,
        password: this.password,
        keyword: this.selected
      };
      if (
        this.confirmEmail &&
        this.selected.length >= 3 &&
        this.password.length >= 8 &&
        this.password === this.rePassword &&
        this.agree === true
      ) {
        axios
          .post(`${this.$store.getters.getUserServer}/user/join`, user)
          .then(res => {
            const userinfo = {
              email: this.email,
              password: this.password
            };
            this.$store.dispatch("LOGIN", userinfo).then(() => {
              this.$router.push("/mainpage");
            });
          });
        // this.$router.replace('/dashboard')
      } else {
        if (!this.confirmEmail) {
          alert("이메일 인증이 완료되지 않았습니다.");
        } else {
          if (this.password.length < 8) {
            alert("비밀번호는 8자리 이상으로 입력해주세요.");
          } else {
            if (this.password !== this.rePassword) {
              alert("비밀번호를 다시 확인해주세요");
            } else {
              if (this.selected.length < 3) {
                alert("관심 개발 분야 3개 이상 체크해주세요.");
              } else {
                if (!this.agree) {
                  alert("약관 동의해주세요");
                }
              }
            }
          }
        }
      }
    }
  },
  watch: {
    selected() {
      
    },
    password() {
      // isPasswordEqual()
    },
    rePassword() {
      // isPasswordEqual()
    }
  },
  computed: {}
};
</script>
<style></style>
