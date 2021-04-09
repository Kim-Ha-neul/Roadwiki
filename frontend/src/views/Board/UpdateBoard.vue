<template>
  <div class="d-flex flex-column">
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-baby-blue" style="height: 350px;"></base-header>

    <div class="p-4 bg-apple-bg">
      <b-input
        v-if="title"
        placeholder="제목"
        class="form-control-alternative"
        v-model="title"
      />
    </div>

    <div>
      <editor
        v-if="content"
        ref="toastuiEditor"
        :options="editorOptions"
        height="500px"
        initialEditType="wysiwyg"
        previewStyle="vertical"
        class="mx-4"
        :initialValue="content"
        id="editor"
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

    <b-button @click="datachange" class="mt-3 mx-4" variant="default">
      저장
    </b-button>
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
  data() {
    return {
      content: "",
      editorOptions: {
        hideModeSwitch: false
      },
      tags: [],
      tagInput: "",
      title: "",
      name: "",
      oneline: "",
      classifier: "",
      uid: "",
      pid: ""
    };
  },
  methods: {
    getOriginPosting() {
      axios
        .get(
          `${this.$store.getters.getBoardServer}/freeboard/posting/${this.$route.query.pid}`
        )
        .then(res => {
          this.name = res.data.user.name;
          this.oneline = res.data.user.oneline;
          this.classifier = res.data.posting.classifier;
          this.title = res.data.posting.title;
          this.content = res.data.posting.content;
          this.createDate = res.data.posting.createDate;
          this.modifyDate = res.data.posting.modifyDate;
          this.uid = res.data.posting.uid;
          this.pid = res.data.posting.pid;
          this.comments = res.data.comments;
          this.recomments = res.data.recomments;
          this.tags = res.data.posting.tags;
        });
    },
    datachange() {
      const content = this.$refs.toastuiEditor.invoke("getMarkdown");
      const pid = this.$route.query.pid;

      let posting = {
        title: this.title,
        content: content,
        pid: pid,
        tags: this.tags
      };
      axios
        .put(`${this.$store.getters.getBoardServer}/freeboard/posting`, posting)
        .then(() => {
          alert("글 작성이 완료되었습니다.");
          this.$router.push({ name: "게시글", query: { pid: pid } });
        });
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
    editorLoading() {
      axios
        .get(
          `${this.$store.getters.getBoardServer}/freeboard/posting/${this.$route.query.pid}`
        )
        .then(res => {
          this.content = res.data.posting.content;
        })
        .finally(() => {});
    }
  },
  created() {
    this.getOriginPosting();
    this.editorLoading();
  }
};
</script>

<style></style>
