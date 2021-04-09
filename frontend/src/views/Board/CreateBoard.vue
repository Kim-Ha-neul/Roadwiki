<template>
  <div class="d-flex flex-column nanum-bold">
    <base-header class="pb-6 pb-8 pt-0 bg-baby-blue" style="height: 350px;">
      <b-container>
        <b-row class="pt-5">
          <i class="fas fa-arrow-left fa-2x text-classic-blue cursor-event" @click="goToBack"></i>
        </b-row>
      </b-container>
    </base-header>

    <b-container>
      <div class="p-4 bg-apple-bg">
        <b-input
          placeholder="제목"
          class="form-control-alternative"
          v-model="title"
        />
      </div>

      <div>
        <editor
          ref="toastuiEditor"
          :options="editorOptions"
          height="500px"
          initialEditType="wysiwyg"
          previewStyle="vertical"
          class="mx-4"
          v-model="editorText"
          placeholder="내용을 입력해주세요"
        />
      </div>

    <b-container class="mt-3">
      <b-row class="mx-2">
        <div
          class="ml-1 mt-2 mr-1 py-1 px-2"
          v-for="(tag, idx) in tags"
          :key="idx"
        >
          <span class="h5"># {{ tag }}</span>
          <span class="ml-2 h5" style="color:black" @click="delTag(idx)">
            <i class="fas fa-times"></i>
          </span>
        </div>
      </b-row>
    </b-container>

    <div class="p-4 bg-apple-bg">
      <b-input
        placeholder="태그를 추가해주세요"
        class="form-control-alternative"
        @keydown.enter="tagEnter"
        @blur="tagEnterBlur"
        v-model="tagInput"
      />
    </div>

      <b-row class="justify-content-center">
        <b-button @click="datachange" class="mt-3 mx-4" block variant="default">
          저장
        </b-button>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import "@toast-ui/editor/dist/toastui-editor.css";
import "codemirror/lib/codemirror.css";
import { Editor } from "@toast-ui/vue-editor";

export default {
  name: "Editor",
  components: {
    editor: Editor
  },
  props: {},
  data() {
    return {
      editorOptions: {
        hideModeSwitch: false
      },
      editorText: "",
      tags: [],
      tagInput: "",
      title: ""
    };
  },
  mounted() {},
  methods: {
    datachange() {
      const content = this.$refs.toastuiEditor.invoke("getMarkdown");
      axios
        .post(`${this.$store.getters.getBoardServer}/freeboard/posting`, {
          uid: this.$store.getters.getUid,
          classifier: "자유게시판",
          title: this.title,
          content: content,
          tags: this.tags
        })
        .then(res => {
          this.$router.push("/tmp_board");
        });
      // this.editorText = content
      // this.$refs.toastuiViewer.editor.markdownValue = content
    },
    tagEnter() {
      if (this.tags.length < 5) {
        if (this.tagInput) {
          this.tags.push(this.tagInput);
          this.tagInput = "";
        }
      } else {
        alert("태그는 최대 5개까지 가능합니다.");
        this.tagInput = "";
      }
    },
    tagEnterBlur() {
      if (this.tagInput.length > 0) {
        if (this.tags.length < 5) {
          if (this.tagInput) {
            this.tags.push(this.tagInput);
            this.tagInput = "";
          }
        } else {
          alert("태그는 최대 5개까지 가능합니다.");
          this.tagInput = "";
        }
      }
    },
    delTag(idx) {
      this.tags.splice(idx, 1);
    },
    goToBack() {
      this.$router.push({ name: "게시판" });
    }
  }
  // watch: {
  //   editorText() {
  //     console.log(this.editorText)
  //   }
  // },
};
</script>

<style scoped>
.img-container {
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
}

</style>
