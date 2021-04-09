<template>
  <div class="nanum-bold">
    <base-header
      class="pb-6 pb-8 pt-5 pt-md-8 bg-baby-blue" style="height: 350px;"
    ></base-header>
    <!-- <b-container fluid class="mt--7"> -->
      <!-- 캘린더 CURD -->
      <!-- <b-card no-body class="border-0"> -->
        <div id="calendar" style="width: 100% !important; margin-top: -61px !important;">
          <div 
            class="calendar-parent mx-9 justify-content-center"
            style="border-radius: 20px 20px 20px 20px !important;"
            >
            <calendar-view
              :items="items"
              :show-date="showDate"
              :time-format-options="{ hour: 'numeric', minute: '2-digit' }"
              :enable-drag-drop="false"
              :disable-past="disablePast"
              :disable-future="disableFuture"
              :class="themeClasses"
              :period-changed-callback="periodChanged"
              :current-period-label="useTodayIcons ? 'icons' : ''"
              :displayWeekNumbers="displayWeekNumbers"
              :enable-date-selection="false"
              :selection-start="selectionStart"
              :selection-end="selectionEnd"
              @date-selection-start="setSelection"
              @date-selection="setSelection"
              @date-selection-finish="finishSelection"
              @drop-on-date="onDrop"
              @click-date="onClickDay"
              @click-item="onClickItem"
            >
              <calendar-view-header
                slot="header"
                slot-scope="{ headerProps }"
                :header-props="headerProps"
                @input="setShowDate"
                style="
                  width: 100% !important;
                  z-index: 100 !important; 
                  background-color: rgba(255, 255, 255, 0) !important;"
                />  
            </calendar-view>
          </div>
        </div>
      <!-- </b-card> -->
    <!-- </b-container> -->

    <b-modal 
      ref = "calendar-modal" 
      id="modal-scrollable" 
      centered
      hide-header
    >
      <b-container>
        <b-row align-h="center">
          <h1>{{ showTitle }}</h1>
        </b-row>
        <hr class="mt-1 mb-4">
        <b-container class="ml-4">

          <b-row>
            <h3>시작 날짜</h3>
          </b-row>

          <b-row class="form-group">
            <!-- <label for="start-date-input" class="col-md-2 col-form-label form-control-label">시작날짜</label> -->
            <b-col md="10">
              <base-input type="date" v-model="newItemStartDate" id="start-date-input"/>
            </b-col>
          </b-row>

          <b-row>
            <h3>종료 날짜</h3>
          </b-row>

          <b-row class="form-group">
            <!-- <label for="start-date-input" class="col-md-2 col-form-label form-control-label">시작날짜</label> -->
            <b-col md="10">
              <base-input type="date" v-model="newItemEndDate" id="end-date-input"/>
            </b-col>
          </b-row>

          <b-row>
            <h3>메모</h3>
          </b-row>
          <b-row 
            style="background-color: rgba(255, 215, 0, 0.3);" 
            class="mr-5 rounded"
            v-show="items.memo"
          >
            <p>{{ items.memo }}</p>
          </b-row>
        </b-container>
      </b-container>
      <template #modal-footer="{}">
        <!-- Emulate built in modal footer ok and cancel button actions -->
        <b-button
          size="lm"
          variant="traffic-blue"
          @click="clickUpdateItem"
        >
          일정업데이트
        </b-button>
        <b-button
          size="lm"
          variant="traffic-red"
          @click="clickDeleteItem"
        >
          일정삭제하기
        </b-button>
      </template>
    </b-modal>
  </div>
</template>

<script>
require("vue-simple-calendar/static/css/default.css");
require("vue-simple-calendar/static/css/holidays-us.css");

import {
  CalendarView,
  CalendarViewHeader,
  CalendarMathMixin
} from "vue-simple-calendar";

export default {
  name: "App",
  components: {
    CalendarView,
    CalendarViewHeader
  },
  mixins: [CalendarMathMixin],
  data() {
    return {
      username: "",
      // 버튼 보여주기
      disabledBtn: true,
      // calendar
      itemId: "",
      showTitle: "",
      showDate: this.today(),
      message: "",
      startingDayOfWeek: 0,
      disablePast: false,
      disableFuture: false,
      displayPeriodUom: "month",
      displayPeriodCount: 1,
      displayWeekNumbers: false,
      selectionStart: null,
      selectionEnd: null,
      // update관련 data
      newItemSdid: "",
      newItemMdid: "",
      newItemBdid: "",
      newItemKey: "",
      newItemRmid: "",
      newItemTitle: "",
      newItemStartDate: "",
      newItemEndDate: "",
      newItemMemo: "",
      useDefaultTheme: true,
      useHolidayTheme: true,
      useTodayIcons: false,
      items: []
    };
  },
  created() {
    // 사용자 커리큘럼 및 일정 로드해 오기
    const uid = String(this.$store.getters.getUid);

    // 해당 유저에 대한 정보 백앤드에 전달
    axios
      .get(`${this.$store.getters.getRoadmapServer}/calendar/get/${uid}`)
      .then(res => {
        res.data["calendars"].map(curr => {
          let item = {};
          item.key = curr.key;
          item.rmid = curr.rmid;
          item.startDate = curr.startdate;
          item.endDate = curr.enddate;
          item.title = curr.text;
          item.sdid = curr.sdid;
          item.mdid = curr.mdid;
          item.bdid = curr.bdid;
          item.memo = curr.memo;
          //날짜,  대중소 에 따른 classes 색 변경
          let classes = ""
          var today = new Date();
          var dd = today.getDate();
          var mm = today.getMonth()+1;
          var yyyy = today.getFullYear();

          if(dd<10) {
              dd='0'+dd
          } 

          if(mm<10) {
              mm='0'+mm
          } 

          today =yyyy+'-'+ mm+'-'+dd;
          let start = (new Date(curr.startdate)).getTime();
          let end = (new Date(curr.enddate)).getTime();
          today = (new Date(today)).getTime();

          if(end < today)
            classes = " past"
          else if(start<=today)
            classes = " now"
          else
            classes = " future"

          if (curr.category === "blue") {
            item.classes = "big" + classes;
          } else if (curr.category === "black") {
            item.classes = "middle"+ classes;
          } else if (curr.category === "green") {
            item.classes = "small"+ classes;
          } else {
            item.classes = "yellow" + classes;
          }
          this.items.push(item);
        });
      })
      .catch(e => {
        alert("axios 통신오류");
      });
  },
  computed: {
    userLocale() {
      return this.getDefaultBrowserLocale;
    },
    dayNames() {
      return this.getFormattedWeekdayNames(this.userLocale, "long", 0);
    },
    // 아이콘 및 테마 설정
    themeClasses() {
      return {
        "theme-default": this.useDefaultTheme,
        "holiday-us-traditional": this.useHolidayTheme,
        "holiday-us-official": this.useHolidayTheme
      };
    }
  },
  mounted() {
    this.username = this.$store.getters.getName;

    this.newItemStartDate = this.isoYearMonthDay(this.today());
    this.newItemEndDate = this.isoYearMonthDay(this.today());
  },
  methods: {
    periodChanged() {
      // range, eventSource) {
      // Demo does nothing with this information, just including the method to demonstrate how
      // you can listen for changes to the displayed range and react to them (by loading items, etc.)
      //console.log(eventSource)
      //console.log(range)
    },
    onClickDay(d) {
      this.disabledBtn = true;
      this.selectionStart = null;
      this.selectionEnd = null;
      this.newItemStartDate = this.isoYearMonthDay(d);
      this.newItemEndDate = this.isoYearMonthDay(d);
      this.newItemMemo = "";
      this.message = `날짜 : ${d.toLocaleDateString()}`;
    },
    // 해당 item선택
    onClickItem(e) {
      this.disabledBtn = false;
      this.newItemKey = e.originalItem.key;
      this.newItemRmid = e.originalItem.rmid;
      this.newItemSdid = e.originalItem.sdid;
      this.newItemBdid = e.originalItem.bdid;
      this.newItemMdid = e.originalItem.mdid;
      this.newItemTitle = e.title;
      this.newItemStartDate = this.isoYearMonthDay(e.startDate);
      this.newItemEndDate = this.isoYearMonthDay(e.endDate);
      this.showTitle = e.title;
      this.message = `일정: ${e.title}`;
      this.newItemMemo = e.originalItem.memo;
      this.$root.$emit("bv::show::modal", "modal-scrollable");
    },
    setShowDate(d) {
      this.showDate = d;
    },
    setSelection(dateRange) {
      this.selectionEnd = dateRange[1];
      this.selectionStart = dateRange[0];
    },
    finishSelection(dateRange) {
      this.setSelection(dateRange);
      this.newItemStartDate = this.isoYearMonthDay(this.selectionStart);
      this.newItemEndDate = this.isoYearMonthDay(this.selectionEnd);
    },
    onDrop(item, date) {
      // Determine the delta between the old start date and the date chosen,
      // and apply that delta to both the start and end date to move the item.
      const eLength = this.dayDiff(item.startDate, date);
      item.originalItem.startDate = this.addDays(item.startDate, eLength);
      item.originalItem.endDate = this.addDays(item.endDate, eLength);
    },
    // 스케쥴 생성버튼
    clickAddItem() {
      this.items.push({
        startDate: this.newItemStartDate,
        endDate: this.newItemEndDate,
        title: this.newItemTitle,
        id:
          "e" +
          Math.random()
            .toString(36)
            .substr(2, 10)
      });
    },

    //  스케쥴 수정버튼
    clickUpdateItem() {
      let updateUserData = {};
      updateUserData.rmid = this.newItemRmid;
      updateUserData.key = this.newItemKey;
      updateUserData.startdate = this.newItemStartDate;
      updateUserData.enddate = this.newItemEndDate;
      updateUserData.sdid = this.newItemSdid;
      updateUserData.mdid = this.newItemMdid;
      updateUserData.bdid = this.newItemBdid;
      // 백엔드 서버 통신 (변경사항 전달)
      axios
        .put(
          `${this.$store.getters.getRoadmapServer}/calendar/modify`,
          updateUserData
        )
        .then(res => {
          this.items.map(item => {
            if (item.rmid == this.newItemRmid && item.key == this.newItemKey) {
              item.startDate = this.newItemStartDate;
              item.endDate = this.newItemEndDate;
              return;
            }
          });
          this.$refs['calendar-modal'].hide()
        })
        .catch(e => {
        });
      alert("일정을 수정했습니다.");
    },
    // 계획일정을 지우는 함수
    clickDeleteItem() {
      // 해당 일정 삭제''
      this.newItemStartDate = "";
      this.newItemEndDate = "";
      this.clickUpdateItem();
    }
  }
};
</script>

<style>
#calendar {
	display: flex;
	flex-direction: row;
	/* font-family: Calibri, sans-serif; */
	width: 100rem;
	height: 50rem;
	min-width: 30rem;
	max-width: 100rem;
	min-height: 50rem;
	margin-top: 5rem;
	margin-left: auto;
	margin-right: auto;
}
.calendar-controls {
  /* margin-left : 0.1rem; */
  /* margin-right: 3%; */
  min-width: 12rem;
  max-width: 12rem;
}
.calendar-parent {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  overflow-x: hidden;
  overflow-y: hidden;
  max-height: 80vh;
  background-color: white;
}
/* For long calendars, ensure each week gets sufficient height. The body of the calendar will scroll if needed */
.cv-wrapper.period-month.periodCount-2 .cv-week,
.cv-wrapper.period-month.periodCount-3 .cv-week,
.cv-wrapper.period-year .cv-week {
  min-height: 6rem;
}

/* The following classes style the classes computed in myDateClasses and passed to the component's dateClasses prop. */
.theme-default .cv-day.ides {
  background-color: #dbdbdb;
  /* background-color: rgb(245, 185, 149); */
}
.ides .cv-day-number::before {
  content: "\271D";
}
.theme-default .cv-day.do-you-remember.the-21st .cv-day-number::after {
  content: "\1F30D\1F32C\1F525";
}
.notification.is-success {
  background-color: rgb(245, 185, 149);
  border-radius: 6px;
  box-shadow: 0 2px 3px rgb(10 10 10 / 10%), 0 0 0 1px rgb(10 10 10 / 10%);
  color: #443600;
  display: block;
  padding: 1.25rem;
  margin-bottom: 20%;
  min-height: 10rem;
}
.box {
  background-color: #fff;
  border-radius: 6px;
  box-shadow: 0 2px 3px rgb(10 10 10 / 10%), 0 0 0 1px rgb(10 10 10 / 10%);
  color: #4a4a4a;
  display: block;
  padding: 1.25rem;
}
.control {
	margin: 0.5rem;
  clear: both;
  font-size: 1.2rem;
  position: relative;
  text-align: left;
}
.input,
.textarea {
  background-color: #fff;
  border-color: #dbdbdb;
  color: #363636;
  box-shadow: inset 0 1px 2px rgb(10 10 10 / 10%);
  max-width: 100%;
  width: 100%;
}
.disabled {
  background-color: #ccc9c9c2;
}

.cv-item {
  cursor: pointer;
}

.form-control {
  cursor: pointer;
}

/* 스케쥴 색상 */
.theme-default .cv-item.big {
  /* background-color: lemonchiffon; */
  background-color: rgb(245, 185, 149);
}
.theme-default .cv-item.middle {
  background-color: paleturquoise;
}
.theme-default .cv-item.small {
  background-color: rgb(26, 231, 135)
}
.theme-default .cv-item.past { /*과거*/
  border-color: rgb(229, 157, 229);
}
.theme-default .cv-item.now { /*현재*/
  /* border-color: rgb(226, 238, 175); */
  border-color: rgb(245, 185, 149);
}
.theme-default .cv-item.future { /* 미래*/
  border-color: rgb(38, 0, 253);
}
/* 지난 날 */
.theme-default .cv-day.past {
  /* background-color: rgb(253, 251, 251); */
  background-color: white;
}
.theme-default .cv-header-day {
  background-color: white;
}
/* 다가올 날 */
.theme-default .cv-day.outsideOfMonth {
  /* background-color: rgb(253, 251, 251); */
  background-color: rgba(132, 137, 140, 0.3);
  /* background-color: rgba(242, 214, 174, 0.3); */
}

.theme-default .cv-header,
.theme-default .cv-header-day {
  /* background-color: white; */
  /* background-color: rgba(15, 76, 129, 0.5); */
  background-color: rgba(245, 185, 149, 0.5);
	border-width: 0px 0px 1px 0px;
}

.cv-header .previousYear,
.cv-header .nextYear {
  display: none;
}

.cv-header .currentPeriod {
  /* display: none; */
  background-color: rgba(181, 199, 211) !important;
}

/* 헤더 속성 */
.cv-header button {
  box-sizing: border-box;
  line-height: 1em;
  font-size: 2em;
  border-width: 0px;
  /* border-width: 1px; */
  border-radius: 50% 50% 50% 50%;
  /* background-color: white; */
  background-color: rgba(255, 255 ,255, 0.8);
  color: rgba(15, 76, 129) !important;
}

.cv-header .periodLabel {
  margin: 0 0 0 550px;
  padding: 0 0 15px 0 !important;
  font-weight: bolder;
  font-size: 40px !important;
}

/* 오늘 날짜에표시 */
.theme-default .cv-day.today {
  /* background-color: white; */
  background-color: rgba(242, 214, 174, 0.3);
}
</style>