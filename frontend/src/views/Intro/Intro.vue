<template>
  <div>
    <full-page ref="fullpage" :options="this.options">
    <div class="section" style="text-align : center;">
      <h1 class="animate__animated animate__backInDown"><strong>당신이 뭘 <span style="color: red;">전공</span>했는지 상관없습니다.</strong></h1>
      <img src="img/intro/come.jpg" 
           alt=""
           width="300px"
           class="animate__animated animate__fadeIn ">
    </div>
    <div class="section">
      <img id="rightPic" 
           alt="Image placeholder" 
           src="img/intro/roadmap.png"
           class="animate__animated animate__backInRight animate__slow	1s"/>
      <h1 class="title-left animate__animated animate__backInLeft animate__slow	1s" >나만의 로드맵을 만들어 보세요.</h1>
       <pre class="animate__animated animate__backInLeft animate__slow	1s" style="text-align: left; padding-left: 300px;"> 
        천릿길도 한 걸음 부터
        세상에 하나뿐인 나만의 로드맵을 만들어보세요.
      </pre>
      
    </div>
    <div class="section">
      <img src="img/intro/worry.png" 
           alt=""
           width="200px"
           style="float: left; margin-left: 500px;"
           class="animate__animated animate__flipInX">
      <h1 class="title-right"> 고민을 털어 놓으세요</h1>
      <pre class="animate__animated animate__fadeIn animate__delay-1s" style="text-align: right; padding-right: 400px;">
        로드맵 생성에 대한 고민을 털어 놓으세요. 
        내가 하는 고민이 모두가 하는 고민이라고 생각하고 
        편하게 고민을 털어 놓으세요.
        좋은 피드백을 받을 수 있습니다.
      </pre>
    </div>
    <div class="section" style="text-align : center;">
      <h1 class="animate__animated animate__tada title-center">로드위키가 조력자가 되겠습니다!</h1>
      <pre style="text-align: center;">
        한 눈에 볼 수 있는 캘린더를 제공하여 쉽게 계획을 세울 수 있습니다.
        나만의 커리큘럼을 통해 꿈에 한 발짝 나아가고 고민을 나눌 수 있는 사람들과 소통하며 
        끝까지 목표를 이뤄나가십시오. 
        로드위키가 여러분들의 꿈을 응원합니다! 
      </pre>
      <img alt="Image placeholder" 
           src="img/intro/final_logo.png" 
           @click="modalShow = !modalShow"/>
      
      <b-modal v-model="modalShow" hide-footer centered>
        <template #modal-title class="text-center">
          <h1 class="ml-2">로그인</h1>
        </template>
        <base-input alternative
                class="mb-3"
                name="Email"
                prepend-icon="ni ni-email-83"
                placeholder="email@email.com"
                v-model="user.email"
                @keydown.enter="signIn"
        >
        </base-input>

        <base-input alternative
                    class="mb-3"
                    name="Password"
                    prepend-icon="ni ni-lock-circle-open"
                    type="password"
                    placeholder="Password"
                    v-model="user.password"
                    @keydown.enter="signIn"
        >
        </base-input>
        <div class="text-center">
          <base-button type="primary" native-type="submit" class="my-4" @click="signIn">확인</base-button>
        </div>
      </b-modal>
    </div>
  </full-page>
</div>
</template>

<script>
export default {
  data() {
    return {
      modalShow: false,
      user:{
        email: '',
        password: '',
      },
      options: {
        licenseKey: 'YOUR_KEY_HEERE',
        menu: '#menu',
        anchors: ['page1', 'page2', 'page3', 'page4'],
        sectionsColor: ['#ffffff', '#ffffff', '#ffffff', '#f3f3f3'],
        animateAnchor: false,
        scrollBar: false,
      },
    }
  },
  methods:{
    signIn() {
      this.$store.dispatch("LOGIN", this.user).then(() => {
        this.$router.push('/dashboard')
      })
      .catch(() => {
        alert('아이디 비밀번호를 확인해주세요.')
        this.user.email = ''
        this.user.password = ''
      })
    },
  },
}
</script>

<style scoped>
h1{
  font-size: 3rem;
}
pre{
  font-size: 1rem;
}
#rightPic{
  position: relative;
  padding-right: 250px;
  float: right;
}

.title-center {
  text-align: center;
  font-size: 36px;
  animation-duration: 1s; 
}
.title-left {
  padding-left: 360px;
  text-align: left;
  font-size: 36px;
}
.title-right{
  padding-right: 400px;
  text-align: right;
  font-size: 36px;
}
</style>