<template>
  <div title="Sidebar" id="sidebar-right" right shadow class="nanum-bold">
    <div id="barSpace">
      <b-button
        class="xl-2"
        variant="info"
        size="xl"
        @click="goBack"
        >&lt; go back</b-button
      >
      <div class="px-3 py-2">
        <b-col cols="7">
          <detail :pid="selPid" />
        </b-col>
      </div>
    </div>
  </div>
</template>
<script>
import detail from "./Posting/Detail";
export default {
  components: {
    detail
  },
  props: ["selPid"],
  methods: {
    goBack() {
      this.$emit("goBack");
    }
  }
};
</script>
<style>
#sidebar-right {
  /* position: fixed; */
  right: 0;
  top: 260px;
  height: 100%;
  width: 50%;
  min-width: 500px;
  overflow: hidden;
  /* background-color: rgb(247, 243, 255); */
  z-index: 1000;
  /* border-left: navy solid 1px; */
}
#barSpace {
  margin: 0% 1%;
  background-color: white;
  height: 100%;
}
</style>
