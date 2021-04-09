<template>
  <div class="nanum-bold">
    <b-card class="m-2" no-body footer-bg-variant="baby-blue" style="border: none;">
      
      <!-- <b-card-header class="border-0">
        <h3 class="mb-0">TMP_BOARD</h3>
      </b-card-header> -->
      <el-table
        class="table-responsive table"
        header-row-class-name="thead-light"
        :data="postings"
        @row-click="openDetail"
        id="mhtable"
      >
        <el-table-column label="ID" min-width="120px" prop="ID">
          <template v-slot="{ row }">
            <b-media no-body class="align-items-center">
              <b-media-body>
                <span class=" name mb-0 text-sm nanum-bold">
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
                <span class=" name mb-0 text-sm nanum-bold cursor-event">
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
                  class="name mb-0 text-sm nanum-bold cursor-event"
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
                <span class="name mb-0 text-sm nanum-bold">{{
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
                <span class="font-weight-600 name mb-0 text-sm nanum-bold">
                  {{ row.createDate }}
                </span>
              </div>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <b-card-footer class="py-4 d-flex justify-content-center bg-apple-bg">
        <!-- 
          selector 종류는 none, name, title, content 중 하나여야 하므로 selectbox 등을 활용해야합니다.
          검색어는 word입니다.
          검색 시 selector가 none이면 전체 리스트 출력이고 word는 무시됩니다.
          getList() 메소드를 실행시키면 현재 조건에 맞춰 테이블이 갱신됩니다.
        -->

        <!-- 페이지 눌린 숫자를 currentPage로 지정한 후 getList() 메소드 실행시키면 테이블 페이지를 넘길 수 있습니다. -->
        <b-pagination
          v-model="currentPage"
          :per-page="10"
          :total-rows="totalPageNum"
          aria-controls="mhtable"
          first-text="<<"
          prev-text="<"
          next-text=">"
          last-text=">>"
        ></b-pagination>

        <!-- 글 작성은 uid가 반드시 필요하므로, vuex에 user 정보가 존재할 경우만 작성 가능하고 서버에는 uid도 함께 보내줘야 합니다. -->
      </b-card-footer>
    </b-card>
  </div>
</template>
<script>
import { Table, TableColumn } from "element-ui";
// TableColumn.rowKey = ["pid", "uid", "tag", "title", "createDate"];

export default {
  name: "light-table",
  components: {
    [Table.name]: Table,
    //   [TableColumn.name]: TableColumn
    [TableColumn.name]: TableColumn,
  },
  data() {
    return {
      currentPage: 1,
      currentPosts: "",
      // word: "",
      selector: "title",
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
    getList(word) {
      let adr = `${this.$store.getters.getBoardServer}/freeboard/list/board/${this.selector}`;
      if (word.length < 1) adr += "/ ";
      else adr += `/${word}`;
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
    getStart() {
      let adr = `${this.$store.getters.getBoardServer}/freeboard/list/board/none/ /`;
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
    this.getStart();
    this.getTotalNum();
  },
  watch: {
    currentPage() {
      this.getList("");
    }
  }
};
</script>
