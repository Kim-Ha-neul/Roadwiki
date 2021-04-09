<template>
  <div class="nanum-bold">
    <b-button @click="logOut">로그아웃</b-button>
  </div>
</template>

<script>
export default {
  data() {
    return{

    }
  },
  methods: {
    logOut() {
      this.$store.dispatch("LOGOUT").then(() => {
        this.$router.push('/')
      })
      .catch(() => {
        alert('로그아웃에 실패했습니다.')
      })
    }
  },
}
</script>

<style>

</style>