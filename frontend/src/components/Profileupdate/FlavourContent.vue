<template>
  <div class="nanum-bold">
    <b-button variant="default" size="sm" @click="clickBtn">수정하기</b-button>

    <b-modal v-model="modalShow" hide-footer>
      <template #modal-title>
        <h1>관심 개발 분야</h1>
      </template>
      <div>
        <b-form-group label="" v-slot="{ ariaDescribedby }">
          <p>
            가장 관심있는 것부터 순서대로 체크해주세요. (3개 이상 선택 가능)
          </p>
          <b-container>
            <b-form-checkbox
              v-for="option in options"
              v-model="keywords"
              :key="option.kwid"
              :value="option.kwid"
              :aria-describedby="ariaDescribedby"
              name="flavour-3a"
            >
              {{ option.word }}
            </b-form-checkbox>
            <!-- <hr> -->
            <!-- <b-row class="justify-content-md-center">
              <b-col class="ml-3">1순위</b-col> 
              <b-col v-if="keywords.length > 0">{{ options[keywords[0]-1].word }}</b-col>
            </b-row>
            <b-row class="justify-content-md-center">
              <b-col class="ml-3">2순위</b-col> 
              <b-col v-if="keywords.length > 1">{{ options[keywords[1]-1].word }}</b-col>
            </b-row>
            <b-row class="justify-content-md-center">
              <b-col class="ml-3">3순위</b-col> 
              <b-col v-if="keywords.length > 2">{{ options[keywords[2]-1].word }}</b-col>
            </b-row> -->
          </b-container>
        </b-form-group>
      </div>
      <div class="text-center">
        <base-button
          type="classic-blue"
          native-type="submit"
          class="my-4 px-5"
          @click="sendFlavour"
          >확인</base-button
        >
      </div>
    </b-modal>
  </div>
  <!-- <b-modal id="modal-login">
  </b-modal> -->
</template>

<script>
export default {
  props: ["keywords"],
  data() {
    return {
      modalShow: false,
      options: []
    };
  },
  created() {
    axios.get(`${this.$store.getters.getUserServer}/keyword/list`).then(res => {
      this.options = res.data.keywords;
    });
  },
  methods: {
    clickBtn() {
      this.modalShow = true;
    },
    sendFlavour() {
      this.modalShow = false;
      this.$emit("changeFlavour", this.keywords);
    }
  }
};
</script>

<style></style>
