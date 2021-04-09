<template>
  <div class="nanum-bold">
    <base-header class="pb-3 pt-5 pt-md-8 bg-baby-blue" style="height: 350px;"> 
      <b-container class="pl-5 pr-2">
        <b-row align-h="around">
          <b-col cols="10">
            <b-form-input form-input
              v-model="inputtext"
              placeholder="검색어를 입력해주세요."
              size="lg"
            ></b-form-input>
          </b-col>
          <b-col cols="1">
            <b-button size="lg" class="fas fa-search px-5" @keydown.enter="search" @click="search" variant="peach-quartz"></b-button>
          </b-col>
          <b-col cols="1"></b-col>
        </b-row>
      </b-container>
      <b-container class="pt-6">
        <b-row align-h="end">
          <!-- <b-button variant="primary" class="mt-2" @click="detailBoard">detail board</b-button> -->
          <b-button variant="classic-blue" class="mt-2 mr-5 nanum-extra-bold" @click="createBoard">새 글 작성
            </b-button>
        </b-row>
      </b-container>
    </base-header>
    <b-container class="p-1">
      <!-- <b-row align-h="end">
        <b-button 
          variant="classic-blue" 
          class="mt-2 mr-4" 
          @click="createBoard"
          >새 글 작성</b-button
        >
      </b-row> -->
      <light-table @clickRow="clickOn" ref="lightTable"/>
    </b-container>


    <!-- <dark-table></dark-table> -->

    <!-- <board-sidebar @goBack="clickOff" :selPid="selPid" v-if="isSelected" /> -->
  </div>
</template>
<script>
import {
  Dropdown,
  DropdownItem,
  DropdownMenu,
  Table,
  TableColumn
} from "element-ui";
import LightTable from "./Tables/LightTable";
import DarkTable from "./Tables/DarkTable";
import Detail from "./Posting/Detail";
import BoardSidebar from "./BoardSidebar";

export default {
  components: {
    LightTable,
    DarkTable,
    Detail,
    BoardSidebar,
    [Dropdown.name]: Dropdown,
    [DropdownItem.name]: DropdownItem,
    [DropdownMenu.name]: DropdownMenu,
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  data() {
    return {
      isSelected: false,
      tWidth: "550px",
      selPid: "",
      inputtext :"",
    };
  },
  methods: {
    clickOn(pid) {
      this.isSelected = true;
      this.selPid = pid;
    },
    clickOff() {
      this.isSelected = false;
    },
    createBoard() {
      this.$router.push({name: '글쓰기' })
    },
    search(){
      var word = this.inputtext;
      this.$refs.lightTable.getList(word);
    },
    // detailBoard() {
    //   this.$router.push({name: 'detail_board', query: { pid: pid }})
    // },
  },
  created() {
    // console.log(this.$store.getters.getUid);
  }
};
</script>
<style>
.el-table.table-dark {
  background-color: #172b4d;
  color: #f8f9fe;
}

.el-table.table-dark th,
.el-table.table-dark tr {
  background-color: #172b4d;
}

.el-table.table-dark td,
.el-table.table-dark th.is-leaf {
  border-bottom: none;
}

body {
  -ms-overflow-style: none;
}
::-webkit-scrollbar {
  display: none;
}
.box {
  -ms-overflow-style: none;
}
.box::-webkit-scrollbar {
  display: none;
}
</style>
