<template>
  <div class="nanum-bold">
    <b-button size="sm" @click="modalShow = !modalShow">배경화면 수정</b-button>

    <b-modal v-model="modalShow" hide-footer>
      <template #modal-title>
        <h1>배경화면 업로드</h1>
      </template>
      <div>
        <b-form-file
          v-model="file1"
          :state="Boolean(file1)"
          placeholder="Choose a file or drop it here..."
          drop-placeholder="Drop file here..."
        ></b-form-file>
        <div class="mt-3">Selected file: {{ file1 ? file1.name : '' }}</div>
      </div>
      <div class="text-center">
        <base-button type="primary" native-type="submit" class="my-4" @click="modalShow = false">확인</base-button>
      </div>
    </b-modal>
  </div>
</template>

<script>
export default {
  data() {
    return{
      modalShow: false,
      file1: null,
    }
  },
}
</script>

<style>

</style>