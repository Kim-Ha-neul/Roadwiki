<template>
  <div class="nanum-bold">
    <b-modal
      class="modal fade"
      v-model="isEmailModal"
      id="modal-email"
      centered
      hide-footer
      >
      <template #modal-header="{ }">
        <h5 class="ml-auto" @click="closeModal">x</h5>
      </template>

      <email-content @confirmSuccess="confirmSuccess" @confirmFail="confirmFail" :userEmail="userEmail" />
      <template v-slot:modal-footer>
        <!-- disabled속성이랑 인증확인되었는지 여부랑 v-bind해주기 -->
      </template>
    </b-modal>
  </div>
</template>
<script>
  import EmailContent from './EmailContent.vue';

  export default {
    name: "emailModal",
    components: {
      EmailContent,
    },
    props: {
      isEmailModal: Boolean,
      userEmail: String
    },
    data: () => {
      return {
        isConfirm: false,
      }
    },
    methods: {
      closeModal() {
        this.$emit("update:show", false);
        this.$emit("close", this.isConfirm);
      },
      confirmSuccess() {
        this.isConfirm = true
        alert('인증에 성공했습니다.')
        this.closeModal()
      },
      confirmFail() {
        this.isConfirm = false
        alert('인증에 실패했습니다.')
      },
    },
  }
</script>
<style>
  .modal-backdrop {
    background-color: rgba(0, 0, 0, 0.6) !important;
  }
</style>
