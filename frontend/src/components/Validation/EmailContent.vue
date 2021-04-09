<template>
  <div class="nanum-bold">
    <b-container id="validationKey" class="text-center">
      <b-row align-h="center">
        <img
          src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE5LjAuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPHN2ZyB2ZXJzaW9uPSIxLjEiIGlkPSJDYXBhXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4Ig0KCSB2aWV3Qm94PSIwIDAgNTEyIDUxMiIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNTEyIDUxMjsiIHhtbDpzcGFjZT0icHJlc2VydmUiPg0KPGc+DQoJPGc+DQoJCTxwYXRoIGQ9Ik01MDYuOTU1LDEuMzE0Yy0zLjExOS0xLjc4LTYuOTU1LTEuNzUtMTAuMDQ1LDAuMDc4TDMxMy42NTYsMTA5Ljc1NmMtNC43NTQsMi44MTEtNi4zMjksOC45NDMtMy41MTgsMTMuNjk3DQoJCQljMi44MSw0Ljc1Myw4Ljk0Miw2LjMyOCwxMy42OTcsMy41MThsMTMxLjQ4Mi03Ny43NDlMMjEwLjQxMSwzMDMuMzM1TDg4LjYwMywyNjYuMDY5bDE1OC45NjUtOTQNCgkJCWM0Ljc1NC0yLjgxMiw2LjMyOS04Ljk0NCwzLjUxOC0xMy42OThjLTIuODEtNC43NTMtOC45NDMtNi4zMy0xMy42OTctMy41MThMNTguOTEsMjYwLjM5MmMtMy40MSwyLjAxNy01LjMwOSw1Ljg1Ni00Ljg0LDkuNzkxDQoJCQlzMy4yMTYsNy4yMjEsNy4wMDQsOC4zOGwxNDUuNDY5LDQ0LjUwNEwyNzAuNzIsNDM5Ljg4YzAuMDY3LDAuMTIxLDAuMTM2LDAuMjIzLDAuMjA3LDAuMzE0YzEuMDcxLDEuNzg2LDIuNjc2LDMuMjQ1LDQuNjc4LDQuMDg3DQoJCQljMS4yNTMsMC41MjcsMi41NywwLjc4NCwzLjg3OCwwLjc4NGMyLjU2MywwLDUuMDg2LTAuOTg2LDYuOTkxLTIuODQ5bDczLjc5NC03Mi4xMmwxMzguODA2LDQyLjQ2Ng0KCQkJYzAuOTYsMC4yOTMsMS45NDUsMC40MzgsMi45MjUsMC40MzhjMi4xMTYsMCw0LjIwNi0wLjY3Miw1Ljk0OC0xLjk2MUM1MTAuNDk2LDQwOS4xNTMsNTEyLDQwNi4xNyw1MTIsNDAzVjEwDQoJCQlDNTEyLDYuNDA5LDUxMC4wNzQsMy4wOTMsNTA2Ljk1NSwxLjMxNHogTTI3MS4yNjUsMzI5LjIzYy0xLjE1OCwxLjY3My0xLjc3OSwzLjY1OS0xLjc3OSw1LjY5NHY2MS4xNzFsLTQzLjgyMy03OS43NjUNCgkJCWwxOTMuOTIxLTIwMS4yMUwyNzEuMjY1LDMyOS4yM3ogTTI4OS40ODYsNDExLjMwOXYtNjIuODY3bDQ4Ljk5LDE0Ljk4OEwyODkuNDg2LDQxMS4zMDl6IE00OTIsMzg5LjQ4M2wtMTk2LjQ5OS02MC4xMTYNCgkJCUw0OTIsNDUuNzA0VjM4OS40ODN6Ii8+DQoJPC9nPg0KPC9nPg0KPGc+DQoJPGc+DQoJCTxwYXRoIGQ9Ik0xNjQuNDIzLDM0Ny41NzdjLTMuOTA2LTMuOTA1LTEwLjIzNi0zLjkwNS0xNC4xNDMsMGwtOTMuMzUyLDkzLjM1MmMtMy45MDUsMy45MDUtMy45MDUsMTAuMjM3LDAsMTQuMTQzDQoJCQlDNTguODgyLDQ1Ny4wMjQsNjEuNDQxLDQ1OCw2NCw0NThzNS4xMTgtMC45NzYsNy4wNzEtMi45MjlsOTMuMzUyLTkzLjM1MkMxNjguMzI4LDM1Ny44MTUsMTY4LjMyOCwzNTEuNDgzLDE2NC40MjMsMzQ3LjU3N3oiLz4NCgk8L2c+DQo8L2c+DQo8Zz4NCgk8Zz4NCgkJPHBhdGggZD0iTTQwLjA3MSw0NzEuOTI4Yy0zLjkwNi0zLjkwMy0xMC4yMzYtMy45MDMtMTQuMTQyLDAuMDAxbC0yMywyM2MtMy45MDUsMy45MDUtMy45MDUsMTAuMjM3LDAsMTQuMTQzDQoJCQlDNC44ODIsNTExLjAyNCw3LjQ0MSw1MTIsMTAsNTEyczUuMTE4LTAuOTc3LDcuMDcxLTIuOTI5bDIzLTIzQzQzLjk3Niw0ODIuMTY2LDQzLjk3Niw0NzUuODM0LDQwLjA3MSw0NzEuOTI4eiIvPg0KCTwvZz4NCjwvZz4NCjxnPg0KCTxnPg0KCQk8cGF0aCBkPSJNMTQyLjY0OSw0OTQuMzRjLTEuODU5LTEuODYtNC40MzktMi45My03LjA2OS0yLjkzYy0yLjY0MSwwLTUuMjEsMS4wNy03LjA3LDIuOTNjLTEuODYsMS44Ni0yLjkzLDQuNDMtMi45Myw3LjA3DQoJCQljMCwyLjYzLDEuMDY5LDUuMjEsMi45Myw3LjA3YzEuODYsMS44Niw0LjQ0LDIuOTMsNy4wNywyLjkzczUuMjEtMS4wNyw3LjA2OS0yLjkzYzEuODYtMS44NiwyLjkzMS00LjQ0LDIuOTMxLTcuMDcNCgkJCUMxNDUuNTgsNDk4Ljc3LDE0NC41MSw0OTYuMiwxNDIuNjQ5LDQ5NC4zNHoiLz4NCgk8L2c+DQo8L2c+DQo8Zz4NCgk8Zz4NCgkJPHBhdGggZD0iTTIxNy4wNTEsNDE5LjkzNWMtMy45MDMtMy45MDUtMTAuMjMzLTMuOTA1LTE0LjE0MiwwbC00OS40NDYsNDkuNDQ1Yy0zLjkwNSwzLjkwNS0zLjkwNSwxMC4yMzcsMCwxNC4xNDINCgkJCWMxLjk1MywxLjk1Myw0LjUxMiwyLjkyOSw3LjA3MSwyLjkyOXM1LjExOC0wLjk3Nyw3LjA3MS0yLjkyOWw0OS40NDYtNDkuNDQ1QzIyMC45NTYsNDMwLjE3MiwyMjAuOTU2LDQyMy44NCwyMTcuMDUxLDQxOS45MzV6Ii8+DQoJPC9nPg0KPC9nPg0KPGc+DQoJPGc+DQoJCTxwYXRoIGQ9Ik0zODcuNzA0LDQxNi4xMzljLTMuOTA2LTMuOTA0LTEwLjIzNi0zLjkwNC0xNC4xNDIsMGwtNDkuNTgsNDkuNThjLTMuOTA1LDMuOTA1LTMuOTA1LDEwLjIzNywwLDE0LjE0Mw0KCQkJYzEuOTUzLDEuOTUyLDQuNTEyLDIuOTI5LDcuMDcxLDIuOTI5czUuMTE4LTAuOTc3LDcuMDcxLTIuOTI5bDQ5LjU4LTQ5LjU4QzM5MS42MDksNDI2LjM3NywzOTEuNjA5LDQyMC4wNDUsMzg3LjcwNCw0MTYuMTM5eiIvPg0KCTwvZz4NCjwvZz4NCjxnPg0KCTxnPg0KCQk8cGF0aCBkPSJNMjgzLjUsMTM2LjMxYy0xLjg2LTEuODYtNC40NC0yLjkzLTcuMDctMi45M3MtNS4yMSwxLjA3LTcuMDcsMi45M2MtMS44NTksMS44Ni0yLjkzLDQuNDQtMi45Myw3LjA4DQoJCQljMCwyLjYzLDEuMDcsNS4yLDIuOTMsNy4wNmMxLjg2LDEuODcsNC40NCwyLjkzLDcuMDcsMi45M3M1LjIxLTEuMDYsNy4wNy0yLjkzYzEuODU5LTEuODYsMi45My00LjQzLDIuOTMtNy4wNg0KCQkJQzI4Ni40MywxNDAuNzUsMjg1LjM2LDEzOC4xNywyODMuNSwxMzYuMzF6Ii8+DQoJPC9nPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPC9zdmc+DQo="
          height="100rem;"
          width="100rem;"
        />
      </b-row>
      <br />
      <h1>인증번호를 발송했습니다.</h1>
      <h2>{{ timeMin }}분 이내에 발급받은 인증번호를 입력해주세요.</h2>
      <h2 id="timer">남은 시간 {{ resTimeData }}</h2>
      <div>
        <base-input
          type="text"
          placeholder="인증번호를 입력해주세요."
          v-model="inputNum"
        >
        </base-input>
        <!-- 인증하기 누르면 
            사용자가 입력한 인증번호와 발송된 인증번호가 같은지 확인
            1. 같으면 인증되었습니다(입력창 아래에 파란 글씨로) 
              -> 인증완료 버튼 활성화
            2. 다르면 인증번호가 잘못되었다. 
              -> 재발송하기 버튼 활성화 & 리셋-->
        <b-button
          variant="default"
          v-show="!timeOut"
          class="col-4"
          @click="isCorrect"
          >인증하기</b-button
        >
        <b-button variant="danger" @click="reSend">재발송하기</b-button>
      </div>
    </b-container>
  </div>
</template>

<script>
export default {
  data: () => {
    return {
      timeMin: 3, // 단위 분[min]
      timeCounter: 180, // 단위는 초[sec]
      resTimeData: "03 : 00",
      timeOut: false,
      mailNum: "123456",
      inputNum: ""
    };
  },
  props: {
    userEmail: String
  },
  created() {
    // 3분 유효 타이머 시작
    this.start();
  },
  watch: {},
  methods: {
    isCorrect() {
      let map = {
        email: this.$store.getters.getEmail,
        code: this.$store.getters.getCode,
        inputCode: this.inputNum
      };
      axios
        .post(`${this.$store.getters.getUserServer}/email/`, map)
        .then(res => {
          console.log(res.data);
          if (res.data.msg === "success") {
            this.$emit("confirmSuccess");
            clearInterval(this.polling);
            this.timeOut = true;
          } else {
            this.$emit("confirmFail");
          }
        });
    },
    start() {
      this.timeOut = false;
      // 1초에 한 번씩 start 호출
      this.polling = setInterval(() => {
        // 1초씩 감소
        this.timeCounter--;
        this.resTimeData = this.prettyTime();
        if (this.timeCounter <= 0) {
          this.timeStop();
        }
      }, 1000);
    },
    // 시간 형식으로 변환 리턴
    prettyTime() {
      let time = this.timeCounter / 60;
      let minutes = parseInt(time);
      let seconds = Math.round((time - minutes) * 60);
      return `${this.pad(minutes, 2)} : ${this.pad(seconds, 2)}`;
    },
    // 2자리 수로 만들어줌
    pad(n, width) {
      n = n + "";
      return n.length >= width
        ? n
        : new Array(width - n.length + 1).join("0" + n);
    },
    timeStop() {
      clearInterval(this.polling);
      alert("인증번호 유효시간이 초과되었습니다.");
      this.timeOut = true;
    },
    reSend() {
      console.log("재발송");
      axios
        .get(`${this.$store.getters.getUserServer}/email/${this.userEmail}`)
        .then(res => {
          if (res.data.msg === "success") {
            alert("재발송했습니다.");
            clearInterval(this.polling);
            this.$store.dispatch("SETCODE", res.data["code"]);
            this.$store.dispatch("SETEMAIL", res.data["email"]);
            this.timeCounter = 180; // 단위는 초[sec]
            this.resTimeData = "03 : 00";
            this.timeOut = false;
            this.start();
          } else {
            alert("인증번호 메일 발송에 실패했습니다. 다시 시도해주세요.");
          }
        });
    }
  },
  beforeDestroy() {
    clearInterval(this.polling);
  }
};
</script>

<style></style>
