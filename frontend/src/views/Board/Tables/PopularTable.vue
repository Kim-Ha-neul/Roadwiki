<template>
  <div class="nanum-bold">
    <b-card class="m-2" no-body style="border: none;">
      <el-table
        class="table-responsive table bg-stucco"
        header-row-class-name="bg-stucco"
        :data="postings"
        @row-click="openDetail"
        id="mhtable"
      >
        <el-table-column label="ID" min-width="120px" prop="ID">
          <template v-slot="{ row }">
            <b-media no-body class="align-items-center">
              <b-media-body>
                <span class="font-weight-600 name mb-0 text-sm">
                  {{ row.pid }}
                </span>
              </b-media-body>
            </b-media>
          </template>
        </el-table-column>

        <el-table-column label="TITLE" min-width="300px" prop="title">
          <template v-slot="{ row }">
            <b-media no-body class="align-items-center">
              <b-media-body>
                <span class="font-weight-600 name mb-0 text-sm">
                  {{ row.title }}({{ row.commentCnt }})
                </span>
              </b-media-body>
            </b-media>
          </template>
        </el-table-column>

        <el-table-column label="NAME" min-width="180px" prop="name">
          <template v-slot="{ row }">
            <b-media no-body class="align-items-center">
              <b-media-body>
                <span
                  class="font-weight-600 name mb-0 text-sm"
                  @click="clickName(row.uid)"
                >
                  {{ row.name }}
                </span>
              </b-media-body>
            </b-media>
          </template>
        </el-table-column>

        <el-table-column label="LIKE" prop="like" min-width="120px">
          <template v-slot="{ row }">
            <div class="d-flex align-items-center">
              <div>
                <span class="font-weight-600 name mb-0 text-sm">{{
                  row.likeCnt
                }}</span>
              </div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="CREATEDATE" prop="createdate" min-width="160px">
          <template v-slot="{ row }">
            <div class="d-flex align-items-center">
              <div>
                <span class="font-weight-600 name mb-0 text-sm">{{
                  row.createDate
                }}</span>
              </div>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </b-card>
  </div>
</template>
<script>
import { Table, TableColumn } from "element-ui";

export default {
  name: "light-table",
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  data() {
    return {
      currentPage: 1,
      currentPosts: "",
      word: "",
      selector: "none",
      tag: "",
      postings: [],
      names: [],
      totalPageNum: "",
      commentCnts: [],
      likeCnts: [],
      fields: ['pid', 'classifier', 'title'],
    };
  },
  methods: {
    getList() {
      let adr = `${this.$store.getters.getBoardServer}/freeboard/list/board/${this.selector}`;
      if (this.word === "") adr += "/ ";
      else adr + `/${this.word}`;
      adr += `/${this.currentPage}`;
      if (this.tag != "") adr += `${this.tag}`;

      axios
        .get(adr)
        .then(response => {
          this.postings = response.data.postings;
          this.names = response.data.names;
          this.commentCnts = response.data.commentCnts;
          for (let i = 0; i < this.postings.length; i++) {
            this.postings[i].createDate = this.$moment(
              this.postings[i].createDate
            ).format("MM/DD HH:mm");
            this.postings[i].name = this.names[i];
            this.postings[i].commentCnt = this.commentCnts[i];
          }
        })
        .catch(err => {
          alert("죄송합니다. 문제가 생겼습니다");
        });
    },

    openDetail(row) {
      const pid = row.pid;
      this.$store.dispatch("SETPID", pid);
      this.$router.push({ name: "게시글", query: { pid } });
    },

    getTotalNum() {
      axios
        .get(`${this.$store.getters.getBoardServer}/freeboard/totalCount`)
        .then(res => {
          this.totalPageNum = res.data.total;
        });
    },

    clickName(uid) {
      this.$router.push({ name: "프로필", query: { profileId: uid } });
    },

    getCommentNum() {
      axios.get(
        `${this.$store.getters.getBoardServer}/freeboard/posting/${this.row.pid}`
      );
    }
  },
  
  created() {
    this.getList();
    this.getTotalNum();
  },
  
  watch: {
    currentPage() {
      this.getList();
    }
  }
};
</script>

<style scoped>


</style>
