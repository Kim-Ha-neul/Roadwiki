<template>
  <!--goJS/start-->
  <div
    style="width: 100%; display: flex; justify-content: space-between; z-index:1;"
    class="nanum-bold"
  >
    <div
      v-show="roadmapMode && !isroadback"
      ref="myPaletteDiv"
      style="width: 170px; margin-right: 2px; background-color: #F9F8F3;"
    ></div>
    <div
      ref="myDiagramDiv"
      style="flex-grow: 1; height: 750px; background-color: #F9F8F3;"
      @click="checkCur"
    ></div>
    <!-- 커리큘럼 데이터 출력 카드/start -->
    <b-card
      v-if="!isroadback"
      style="width: 252px;"
      class="text-center"
    >
      <!-- 헤더텍스트 색 구분 blue, black, green (대, 중, 소) -->
      <div v-if="curriculumData === -1">
        <h1 class="bold">{{ roadmapname }}</h1>
      </div>
      <div v-else-if="curriculumData.category === 'blue'" class="bigCur">
        <h2>{{ headertext }}</h2>
      </div>
      <div v-else-if="curriculumData.category === 'black'" class="middleCur">
        <h2>{{ headertext }}</h2>
      </div>
      <div v-else-if="curriculumData.category === 'green'" class="smallCur">
        <h2>{{ headertext }}</h2>
      </div>
      <div v-else class="otherCur">
        <h2>{{ headertext }}</h2>
      </div>
      <hr>


      <b-card-text v-if="roadmapMode && !isroadback">
        <base-input label="시작날짜-종료날짜">
          <flat-pickr
            slot-scope="{ focus, blur }"
            @on-open="focus"
            @on-close="blur"
            :config="{ allowInput: true, mode: 'range' }"
            class="form-control datepickr"
            v-model="dates"
            :disabled="!roadmapMode"
          >
          </flat-pickr>
        </base-input>
      </b-card-text>

      <b-card-text v-else-if="dates">
        <h3>시작날짜-종료날짜</h3>
        <p>{{ dates }}</p>
      <hr />
      </b-card-text>

      <span>{{ descript }}</span>
      <hr v-show="descript.length > 0"/>

      <b-card-text v-if="roadmapMode">
        <b-form-input
          v-model="memotext"
          placeholder="Enter your memo"
        ></b-form-input>
      </b-card-text>
      <b-card-text v-else>
        <p>{{ memotext }}</p>
      </b-card-text>
    </b-card>
    <!-- 커리큘럼 데이터 출력 카드/end -->
  </div>
  <!--goJs/end-->
</template>

<script>
// flatPickr - Hindi: 날짜 설정 부속기능
import { Hindi } from "flatpickr/dist/l10n/hi.js";
import dropdown from "vue-dropdowns";

// src\views\Roadmap\RoadMap.vue
// Roadmap 폴더 명 변경을 위한 주석
// 코드 변환 시작
let go = window.go;
let $ = go.GraphObject.make;
let myDiagram;
let myPalette;
// node 속성(card에 띄우기위한) 체크위한 전역변수(여기서만 사용)
// let curriculumData = -1;

// 커리큘럼 클릭시 요청을통해 받아온 데이터를 여기에 저장하면 됨
let recommendCurData = [
  // 실제 프로젝트 default data 최초 클릭할 정보가 필요
];
let iseditable = true;
let roadbacktimer = "";
export default {
  name: "Roadmap",
  props: {
    roadmapMode: Number, //0 읽기 1 쓰기 2 로드백 모드
    roadmapData: Object, // 변수 하나를 줘가지고 그러네
    inputText: String,
    isroadback: Boolean,
    rmid: Number,
    roadmapname: String,
    checkName: Boolean,
  },
  data() {
    return {
      headertext: "",
      isworking: false,
      dates: "",
      memotext: "",
      descript: "",
      recommend : "",
      // Get more form https://flatpickr.js.org/options/
      config: {
        wrap: true, // set wrap to true only when using 'input-group'
        altFormat: "M j, Y",
        altInput: true,
        dateFormat: "Y-m-d",
        locale: Hindi // locale for this instance only
      },
      curData: {
        category: "",
        startdate: "",
        enddate: "",
        memo: "",
        bdid: 0,
        mdid: 0,
        sdid: 0,
        text: "",
        content: ""
      },
      curriculumData: -1,
    };
  },
  components: {
    dropdown: dropdown
  },
  created() {
    iseditable = !this.isroadback;
  },
  mounted() {
    if (!iseditable) {
      myDiagram = $(go.Diagram, this.$refs.myDiagramDiv, {
        initialContentAlignment: go.Spot.Center,
        initialAutoScale: go.Diagram.Uniform,
        "undoManager.isEnabled": true,
        "animationManager.isEnabled": false,
        allowLink: false,
        allowRelink: false,
        allowReshape: false,
        allowZoom: false,
        allowVerticalScroll: false,
        allowHorizontalScroll: false,
        "clickCreatingTool.archetypeNodeData": {
          text: "피드백 할 내용을 적으세요.",
          category: "comment"
        },

        positionComputation: function(diagram, pt) {
          return new go.Point(Math.floor(pt.x), Math.floor(pt.y));
        }
      });
    } else {
      myDiagram = $(go.Diagram, this.$refs.myDiagramDiv, {
        initialContentAlignment: go.Spot.Center,
        initialAutoScale: go.Diagram.Uniform,
        
        "undoManager.isEnabled": true
      });
    }

    // 페이지에 변화가 있을 때 title 및 save 버튼 활성화
    // when the document is modified, add a "*" to the title and enable the "Save" button
    // jquery사용되어 있어서 우리 수정완료버튼을 활성하시키고 싶으면 방식을 바꿔야함
    myDiagram.addDiagramListener("Modified", function(e) {
      // var button = document.getElementById("SaveButton");
      // console.log('?',button) => 아무것도 안찍힘
      // if (button) button.disabled = !myDiagram.isModified;
      // var idx = document.title.indexOf("*");
      // console.log('?',document.title) => 페이지 전체 제목이 출력 index.html 제목(roadwiki)
      // if (myDiagram.isModified) {
      //   if (idx < 0) document.title += "*";
      // } else {
      //   if (idx >= 0) document.title = document.title.substr(0, idx);
      // }
    });

    // GUI 시작
    // 대분류 커리큘럼 모델
    myDiagram.nodeTemplateMap.add(
      "blue", // the default category
      $(
        go.Node,
        "Table",
        this.nodeStyle(),
        // the main object is a Panel that surrounds a TextBlock with a rectangular Shape
        $(
          go.Panel,
          "Auto",
          $(
            go.Shape,
            "RoundedRectangle",
            {
              fill: "rgb(255, 255 ,255)",
              stroke: "rgb(15, 76, 129)",
              strokeWidth: 0.5,
              strokeJoin: "round",
              strokeCap: "square"
            },
            new go.Binding("figure", "figure")
          ),
          $(
            go.TextBlock,
            this.textStyle(),
            {
              margin: 10,
              maxSize: new go.Size(160, NaN),
              wrap: go.TextBlock.WrapFit,
              editable: false
            },
            new go.Binding("text").makeTwoWay()
          )
        ),
        // four named ports, one on each side: node의 가지 옵션
        this.makePort("T", go.Spot.Top, go.Spot.TopSide, false, true),
        this.makePort("L", go.Spot.Left, go.Spot.LeftSide, true, true),
        this.makePort("R", go.Spot.Right, go.Spot.RightSide, true, true),
        this.makePort("B", go.Spot.Bottom, go.Spot.BottomSide, true, false)
      )
    );

    // 중분류 커리큘럼 모델
    myDiagram.nodeTemplateMap.add(
      "black", // the default category
      $(
        go.Node,
        "Table",
        this.nodeStyle(),
        // the main object is a Panel that surrounds a TextBlock with a rectangular Shape
        $(
          go.Panel,
          "Auto",
          $(
            go.Shape,
            "RoundedRectangle",
            {
              fill: "rgb(255, 255, 255)",
              stroke: "rgb(137, 119, 173)",
              strokeWidth: 0.5,
              strokeJoin: "round",
              strokeCap: "square"
            },
            new go.Binding("figure", "figure")
          ),
          $(
            go.TextBlock,
            this.textStyle(),
            {
              margin: 10,
              maxSize: new go.Size(160, NaN),
              wrap: go.TextBlock.WrapFit,
              editable: false
            },
            new go.Binding("text").makeTwoWay()
          )
        ),
        // four named ports, one on each side: node의 가지 옵션
        this.makePort("T", go.Spot.Top, go.Spot.TopSide, false, true),
        this.makePort("L", go.Spot.Left, go.Spot.LeftSide, true, true),
        this.makePort("R", go.Spot.Right, go.Spot.RightSide, true, true),
        this.makePort("B", go.Spot.Bottom, go.Spot.BottomSide, true, false)
      )
    );

    // 소분류 커리큘럼 모델
    myDiagram.nodeTemplateMap.add(
      "green", // the default category
      $(
        go.Node,
        "Table",
        this.nodeStyle(),
        // the main object is a Panel that surrounds a TextBlock with a rectangular Shape
        $(
          go.Panel,
          "Auto",
          $(
            go.Shape,
            "RoundedRectangle",
            {
              fill: "rgb(255, 255, 255)",
              stroke: "#307363",
              strokeWidth: 0.5,
              strokeJoin: "round",
              strokeCap: "square"
            },
            new go.Binding("figure", "figure")
          ),
          $(
            go.TextBlock,
            this.textStyle(),
            {
              margin: 10,
              maxSize: new go.Size(160, NaN),
              wrap: go.TextBlock.WrapFit,
              editable: false
            },
            new go.Binding("text").makeTwoWay()
          )
        ),
        // four named ports, one on each side: node의 가지 옵션
        this.makePort("T", go.Spot.Top, go.Spot.TopSide, false, true),
        this.makePort("L", go.Spot.Left, go.Spot.LeftSide, true, true),
        this.makePort("R", go.Spot.Right, go.Spot.RightSide, true, true),
        this.makePort("B", go.Spot.Bottom, go.Spot.BottomSide, true, false)
      )
    );

    // 시작 모델
    myDiagram.nodeTemplateMap.add(
      "Start",
      $(
        go.Node,
        "Table",
        this.nodeStyle(),
        $(
          go.Panel,
          "Spot",
          $(go.Shape, "Circle", {
            desiredSize: new go.Size(70, 70),
            fill: "#ffffff",
            stroke: "#F04A5E",
            strokeWidth: 1
          }),
          $(go.TextBlock, "Start", this.textStyle(), new go.Binding("text"))
        ),
        // three named ports, one on each side except the top, all output only:
        this.makePort("L", go.Spot.Left, go.Spot.Left, true, false),
        this.makePort("R", go.Spot.Right, go.Spot.Right, true, false),
        this.makePort("B", go.Spot.Bottom, go.Spot.Bottom, true, false)
      )
    );
    // 맨위 빈칸 만들기 모델
    myDiagram.nodeTemplateMap.add(
      "Blank",
      $(
        go.Node,
        "Table",
        {
          // the Node.location is at the center of each node
          locationSpot: go.Spot.Center,
          movable: false,
          deletable: false,
          selectable: false
        },
        $(
          go.Panel,
          "Spot",
          $(go.Shape, "Circle", {
            desiredSize: new go.Size(70, 70),
            fill: "#F9F8F3",
            stroke: "#F9F8F3",
            strokeWidth: 1
          }),
          $(go.TextBlock, "Start", this.textStyle(), new go.Binding("text"))
        ),
        // three named ports, one on each side except the top, all output only:
        this.makePort("L", go.Spot.Left, go.Spot.Left, false, false),
        this.makePort("R", go.Spot.Right, go.Spot.Right, false, false),
        this.makePort("B", go.Spot.Bottom, go.Spot.Bottom, false, false)
      )
    );
    // custom 모델
    myDiagram.nodeTemplateMap.add(
      "Custom",
      $(
        go.Node,
        "Table",
        this.nodeStyle(),
        // the main object is a Panel that surrounds a TextBlock with a rectangular Shape
        $(
          go.Panel,
          "Auto",
          $(
            go.Shape,
            "RoundedRectangle",
            {
              fill: "rgb(255, 255, 255)",
              stroke: "rgb(234, 218, 79)",
              strokeWidth: 0.5,
              strokeJoin: "round",
              strokeCap: "square"
            },
            new go.Binding("figure", "figure")
          ),
          $(
            go.TextBlock,
            this.textStyle(),
            {
              margin: 10,
              maxSize: new go.Size(160, NaN),
              wrap: go.TextBlock.WrapFit,
              editable: iseditable
            },
            new go.Binding("text").makeTwoWay()
          )
        ),
        // four named ports, one on each side: node의 가지 옵션
        this.makePort("T", go.Spot.Top, go.Spot.TopSide, true, true),
        this.makePort("L", go.Spot.Left, go.Spot.LeftSide, true, true),
        this.makePort("R", go.Spot.Right, go.Spot.RightSide, true, true),
        this.makePort("B", go.Spot.Bottom, go.Spot.BottomSide, true, true)
      )
    );

    // comment 모델
    myDiagram.nodeTemplateMap.add(
      "comment",
      $(
        go.Node,
        "Table",
        new go.Binding("location", "loc", go.Point.parse).makeTwoWay(
          go.Point.stringify
        ),
        {
          // the Node.location is at the center of each node
          locationSpot: go.Spot.Center,
          movable: true
        },
        // the main object is a Panel that surrounds a TextBlock with a rectangular Shape
        $(
          go.Panel,
          "Auto",
          $(
            go.Shape,
            "RoundedRectangle",
            {
              fill: "#F9F8F3",
              stroke: "#F9F8F3",
              strokeWidth: 0,
              strokeJoin: "round",
              strokeCap: "square"
            },
            new go.Binding("figure", "figure")
          ),
          $(
            go.TextBlock,
            this.textStyle(),
            {
              margin: 1,
              maxSize: new go.Size(160, NaN),
              wrap: go.TextBlock.WrapFit,
              editable: true
            },
            new go.Binding("text").makeTwoWay()
          )
        ),
        // four named ports, one on each side: node의 가지 옵션
        this.makePort("T", go.Spot.Top, go.Spot.TopSide, false, false),
        this.makePort("L", go.Spot.Left, go.Spot.LeftSide, false, false),
        this.makePort("R", go.Spot.Right, go.Spot.RightSide, false, false),
        this.makePort("B", go.Spot.Bottom, go.Spot.BottomSide, false, false)
      )
    );

    // taken from ../extensions/Figures.js:
    go.Shape.defineFigureGenerator("File", function(shape, w, h) {
      var geo = new go.Geometry();
      var fig = new go.PathFigure(0, 0, true); // starting point
      geo.add(fig);
      fig.add(new go.PathSegment(go.PathSegment.Line, 0.75 * w, 0));
      fig.add(new go.PathSegment(go.PathSegment.Line, w, 0.25 * h));
      fig.add(new go.PathSegment(go.PathSegment.Line, w, h));
      fig.add(new go.PathSegment(go.PathSegment.Line, 0, h).close());
      var fig2 = new go.PathFigure(0.75 * w, 0, false);
      geo.add(fig2);
      // The Fold
      fig2.add(new go.PathSegment(go.PathSegment.Line, 0.75 * w, 0.25 * h));
      fig2.add(new go.PathSegment(go.PathSegment.Line, w, 0.25 * h));
      geo.spot1 = new go.Spot(0, 0.25);
      geo.spot2 = go.Spot.BottomRight;
      return geo;
    });

    // replace the default Link template in the linkTemplateMap
    // 링크 결합, 설정관련 코드
    myDiagram.linkTemplate = $(
      go.Link, // the whole link panel
      {
        routing: go.Link.AvoidsNodes,
        curve: go.Link.JumpOver,
        corner: 5,
        toShortLength: 4,
        relinkableFrom: true,
        relinkableTo: true,
        reshapable: true,
        resegmentable: true,
        isEnabled: iseditable,
        // mode에 따라 바뀌어야 하는 부분--------------------------------------------------------|
        // 마우스 오버시 이펙트 효과 부여
        mouseEnter: function(e, link) {
          link.findObject("HIGHLIGHT").stroke = "rgba(255, 255, 255, 1)";
        },
        mouseLeave: function(e, link) {
          link.findObject("HIGHLIGHT").stroke = "transparent";
        },
        //----------------------------------------------------------------------------------------|
        selectionAdorned: false
      },
      new go.Binding("points").makeTwoWay(),
      $(
        go.Shape, // 화살표 클릭시 하이라이트 효과 설정
        {
          isPanelMain: true,
          strokeWidth: 5,
          stroke: "transparent",
          name: "HIGHLIGHT"
        }
      ),
      $(
        go.Shape, // 화살표 바디 모양
        { isPanelMain: true, stroke: "#2a446f", strokeWidth: 1.5 },
        new go.Binding("stroke", "isSelected", function(sel) {
          return sel ? "#2a446f" : "#2a446f";
        }).ofObject()
      ),
      $(
        go.Shape, // 화살표 모양
        {
          toArrow: "Triangle",
          strokeWidth: 1,
          stroke: "#2a446f",
          fill: "#2a446f"
        }
      ),
      $(
        go.Panel,
        "Auto", // 링크 라벨 (안보이게 설정해둠)
        {
          visible: false,
          name: "LABEL",
          segmentIndex: 2,
          segmentFraction: 0.5
        },
        new go.Binding("visible", "visible").makeTwoWay(),
        $(
          go.Shape,
          "RoundedRectangle", // the label shape
          { fill: "#F8F8F8", strokeWidth: 0 }
        ),
        $(
          go.TextBlock,
          "Yes", // the label
          {
            textAlign: "center",
            font: "10pt helvetica, arial, sans-serif",
            stroke: "#333333",
            editable: false
          },
          new go.Binding("text").makeTwoWay()
        )
      )
    );

    // 어떤 커리큘럼을 눌렀는지 체크 => 커리큘럼 추천에 활용할 데이터 추출
    // 추천 로직 1 단계
    myDiagram.addDiagramListener("ObjectSingleClicked", (e) => {
      this.curriculumData = e.subject.part.data;
    });

    if (!iseditable) {
      myDiagram.addDiagramListener("PartCreated", e => {
        this.saveComment(e.subject.part.data);
      });

      myDiagram.addDiagramListener("SelectionMoved", e => {
        e.subject.each(p => {
          this.updateComment(p.part.data);
        });
      });
      myDiagram.addDiagramListener("TextEdited", e => {
        this.updateComment(e.subject.part.data);
      });
      myDiagram.addDiagramListener("SelectionDeleted", e => {
        e.subject.each(p => {
          this.deleteComment(p.part.data);
        });
      });
    }

    myDiagram.addDiagramListener("BackgroundSingleClicked", (e) => {
      this.curriculumData = -1;
    });

    // 팔레트 설정 관련 코드
    // 노드모델에 변경사항이 있다면 반드시 여기서 확인 해주셔야 합니다.
    myPalette = $(
      go.Palette,
      this.$refs.myPaletteDiv, // must name or refer to the DIV HTML element
      {
        // Instead of the default animation, use a custom fade-down
        "animationManager.initialAnimationStyle": go.AnimationManager.None,
        InitialAnimationStarting: this.animateFadeDown, // Instead, animate with this function
        nodeTemplateMap: myDiagram.nodeTemplateMap, // share the templates used by myDiagram
        //######################################################### 추천 커리 백엔드 연동부 핵심코드!
        // 추천 컴포넌트를 띄우려면 여기에 데이터를 가져와서 랜더링
        model: new go.GraphLinksModel(
          // 추천 커리큘럼 전역변수로 저장되어있음
          recommendCurData
        ),
        autoScale: go.Diagram.UniformToFill
      }
    );

    // 링크연결시 화살표가 직교하는 모양으로 보일 수 있도록 하는 설정
    myDiagram.toolManager.linkingTool.temporaryLink.routing =
      go.Link.Orthogonal;
    myDiagram.toolManager.relinkingTool.temporaryLink.routing =
      go.Link.Orthogonal;

    // mode에 따라 분류 -----------------------------------------------------------------|
    // 수정 없이 읽기
    if (this.roadmapMode) {
      myDiagram.isReadOnly = false;
    } else {
      myDiagram.isReadOnly = true;
    }
    //-----------------------------------------------------------------------------------|
    this.readRoadmap();
    // // 수정로그 가져오기
    // update쪽으로 옮기기
    // this.readRoadmapLog();
    if(this.roadmapMode == 1)
      this.getRecommendCur();
    // 팔레트 초기화
    this.curriculumData = -1;

    //로드백 타이머 시작
    if (!iseditable) {
      this.startRoadback();
    }
  },
  watch: {
    // head 데이터 변경때 마다 실행(즉, 커리큘럼 클릭시 실행)
    headertext: function() {
      // 데이터 호출하는 함수
      if (
        this.curriculumData.bdid == 0 &&
        this.curriculumData.mdid == 0 &&
        this.curriculumData.sdid == 0
      ) {
        return;
      }
      this.getRecommendCur();
    },
    memotext: function() {
      if (this.curriculumData != -1) this.curriculumData.memo = this.memotext;
    },
    dates: function() {
      if (this.curriculumData != -1) {
        if (this.dates.length > 10) {
          this.curriculumData.startdate = this.dates.slice(0, 10);
          this.curriculumData.enddate = this.dates.slice(14, 24);
        } else {
          this.curriculumData.startdate = this.dates;
          this.curriculumData.enddate = this.dates;
        }
      }
    },
    roadmapData: function(e) {
      myDiagram.model = go.Model.fromJson(e);
    },
    inputText: function() {
      // 검색메서드 실행
      if (this.inputText != "") this.getSearchCur();
      else this.getRecommendCur();
    },
    roadmapname: function() {
      this.curriculumData = -1
    }
  },
  computed: {},
  methods: {
    // 다이어그램 모델관련 함수
    nodeStyle() {
      return [
        // The Node.location comes from the "loc" property of the node data,
        // converted by the Point.parse static method.
        // If the Node.location is changed, it updates the "loc" property of the node data,
        // converting back using the Point.stringify static method.
        new go.Binding("location", "loc", go.Point.parse).makeTwoWay(
          go.Point.stringify
        ),
        {
          // the Node.location is at the center of each node
          locationSpot: go.Spot.Center,
          movable: iseditable,
          deletable: iseditable,
          selectable: iseditable
        }
      ];
    },
    makePort(name, align, spot, output, input) {
      var horizontal =
        align.equals(go.Spot.Top) || align.equals(go.Spot.Bottom);
      // the port is basically just a transparent rectangle that stretches along the side of the node,
      // and becomes colored when the mouse passes over it
      return $(go.Shape, {
        fill: "transparent", // changed to a color in the mouseEnter event handler
        strokeWidth: 0, // no stroke
        width: horizontal ? NaN : 8, // if not stretching horizontally, just 8 wide
        height: !horizontal ? NaN : 8, // if not stretching vertically, just 8 tall
        alignment: align, // align the port on the main Shape
        stretch: horizontal
          ? go.GraphObject.Horizontal
          : go.GraphObject.Vertical,
        portId: name, // declare this object to be a "port"
        fromSpot: spot, // declare where links may connect at this port
        fromLinkable: output, // declare whether the user may draw links from here
        toSpot: spot, // declare where links may connect at this port
        toLinkable: input, // declare whether the user may draw links to here
        cursor: "pointer"
      });
    },
    // 글씨체, 스타일 수정 필요(프론트 집중기간)
    textStyle() {
      return {
        font: "12pt Lato, Helvetica, Arial, sans-serif",
        stroke: "#000000"
      };
    },
    // 링크라벨 함수(필요없으면 삭제가능)
    showLinkLabel(e) {
      var label = e.subject.findObject("LABEL");
      if (label !== null)
        label.visible = e.subject.fromNode.data.category === "Conditional";
    },
    // 로드될때 위에서 아래로 올라오는 애니메이션
    animateFadeDown(e) {
      var diagram = e.diagram;
      var animation = new go.Animation();
      animation.isViewportUnconstrained = true; // So Diagram positioning rules let the animation start off-screen
      animation.easing = go.Animation.EaseOutExpo;
      animation.duration = 900;
      // Fade "down", in other words, fade in from above
      animation.add(
        diagram,
        "position",
        diagram.position.copy().offset(0, 200),
        diagram.position
      );
      animation.add(diagram, "opacity", 0, 1);
      animation.start();
    },
    checkCur(e) {
      // 차후에 DB에 요청을 보낸다음 DB정보로 반영
      if (this.curriculumData.category == "comment" || this.curriculumData == -1) {
        this.headertext = "";
        this.dates = "";
      this.memotext = "";
      this.descript = "";  
        return;
      }
      this.headertext = this.curriculumData.text;

      if (this.curriculumData.startdate)
        this.dates = this.curriculumData.startdate + " to " + this.curriculumData.enddate;
      else
        this.dates = ""
      this.memotext = this.curriculumData.memo;
      this.descript = this.curriculumData.content;
    },
    getRecommendCur() {
      const _ = require("lodash");
      let color;
      let url;
      if (this.curriculumData == -1 || !this.curriculumData.category) {
        url = `${this.$store.getters.getRoadmapServer}/curriculum/suggest`;
        color = "blue";
      } else if (this.curriculumData.mdid != 0) {
        url = `${this.$store.getters.getRoadmapServer}/curriculum/suggest/${this.curriculumData.bdid}/${this.curriculumData.mdid}`;
        color = "green";
      } else if (this.curriculumData.bdid != 0) {
        url = `${this.$store.getters.getRoadmapServer}/curriculum/suggest/${this.curriculumData.bdid}`;
        color = "black";
      }
      axios
        .get(url)
        .then(res => {
          this.recommend = res.data.recommend.text;
          this.get_recommend();
          for (var i = 0; i < res.data["suggest"].length; i++) {
            res.data["suggest"][i].category = color;
            res.data["suggest"][i].startdate = "";
            res.data["suggest"][i].enddate = "";
            res.data["suggest"][i].memo = "";
          }
          if (this.curriculumData == -1) {
            let custom = _.cloneDeep(this.curData);
            custom.category = "Custom";
            custom.text = "User Custom";
            res.data["suggest"].unshift(custom);
            let start = _.cloneDeep(this.curData);
            start.category = "Start";
            start.text = "시작";
            res.data["suggest"].unshift(start);
          }
          if (this.curriculumData == -1) {
            let custom = _.cloneDeep(this.curData);
            custom.category = "Custom";
            custom.text = "인공지능";
            res.data["suggest"].push(custom);
            let start = _.cloneDeep(this.curData);
            start.category = "Custom";
            start.text = "Big Data";
            res.data["suggest"].push(start);
          }
                    if (this.curriculumData == -1) {
            let custom = _.cloneDeep(this.curData);
            custom.category = "Custom";
            custom.text = "블록체인";
            res.data["suggest"].push(custom);
            let start = _.cloneDeep(this.curData);
            start.category = "Custom";
            start.text = "IoT";
            res.data["suggest"].push(start);
          }
          let blank = _.cloneDeep(this.curData);
          blank.category = "Blank";
           blank.text = "";
          res.data["suggest"].unshift(blank);
          recommendCurData = res.data["suggest"];
          myPalette.model.nodeDataArray = recommendCurData;
        })
        .catch(e => {
          alert("죄송합니다. 문제가 생겼습니다.")
          //console.error(e);
        });
    },
    getSearchCur() {
      let url = `${this.$store.getters.getRoadmapServer}/curriculum/search/${this.inputText}`;
      let color = "";
      axios
        .get(url)
        .then(res => {
          for (var i = 0; i < res.data["suggest"].length; i++) {
            if (res.data["suggest"][i].sdid != 0) color = "green";
            else if (res.data["suggest"][i].mdid != 0) color = "black";
            else color = "blue";
            res.data["suggest"][i].category = color;
            res.data["suggest"][i].startdate = "";
            res.data["suggest"][i].enddate = "";
            res.data["suggest"][i].memo = "";
          }
           let blank = _.cloneDeep(this.curData);
          blank.category = "Blank";
           blank.text = "";
          res.data["suggest"].unshift(blank);
          recommendCurData = res.data["suggest"];
          myPalette.model.nodeDataArray = recommendCurData;
        })
        .catch(err => {
          alert("죄송합니다. 문제가 생겼습니다.")
          //console.error(err);
        });
    },
    readRoadmap() {
      myDiagram.model = go.Model.fromJson(this.roadmapData);
    },
    serveRoadmap() {
      return myDiagram.model.toJson();
    },
    startRoadback() {
      roadbacktimer = setInterval(() => {
        if (
          myDiagram.toolManager.textEditingTool.isActive ||
          myDiagram.toolManager.draggingTool.Wc ||
          this.isworking
        ) {
          return;
        }
        let url = `${this.$store.getters.getRoadmapServer}/roadmap/get/comment/${this.rmid}`;
        axios
          .get(url)
          .then(res => {
            myDiagram.model = go.Model.fromJson(res.data["roadmaps"].tmp);
          })
          .catch(err => {
            alert("죄송합니다. 문제가 생겼습니다.")
            //console.error(err);
          });
      }, 2000);
    },
    saveComment(data) {
      data.rmid = this.rmid;
      this.isworking = true;
      axios
        .put(`${this.$store.getters.getRoadmapServer}/roadcomment/insert`, data)
        .then(res => {
          if (res.data.msg != "success") alert("통신 오류");
        })
        .catch(err => {
          alert("죄송합니다. 문제가 생겼습니다.")
          //console.error(err);
        });
      this.isworking = false;
    },
    updateComment(data) {
      this.isworking = true;
      axios
        .put(`${this.$store.getters.getRoadmapServer}/roadcomment/update`, data)
        .then(res => {
          if (res.data.msg != "success") alert("통신 오류");
        })
        .catch(err => {
          alert("죄송합니다. 문제가 생겼습니다.")
          //console.error(err);
        });
        this.isworking = false;
    },
    deleteComment(data) {
      this.isworking = true;
      axios
        .post(
          `${this.$store.getters.getRoadmapServer}/roadcomment/delete`,
          data
        )
        .then(res => {
          if (res.data.msg != "success") alert("통신 오류");
        })
        .catch(err => {
          alert("죄송합니다. 문제가 생겼습니다.")
          //console.error(err);
        });
        this.isworking = false;
    },
    get_recommend(){
      this.$emit('get_recommend',this.recommend);
    },
  },
  destroyed() {
    clearInterval(roadbacktimer);
  },
};
</script>

<style>
.bigCur {
  display: inline-block;
  border-bottom: 3px solid #b4e7f8;
  box-shadow: inset 0 -4px 0 #b4e7f8;
}
.middleCur {
  display: inline-block;
  border-bottom: 3px solid #ffb3fb;
  box-shadow: inset 0 -4px 0 #ffb3fb;
}
.smallCur {
  display: inline-block;
  border-bottom: 3px solid #a9ffae;
  box-shadow: inset 0 -4px 0 #a9ffae;
}
.otherCur {
  display: inline-block;
  border-bottom: 3px solid #f4f8b4;
  box-shadow: inset 0 -4px 0 #f4f8b4;
}
</style>
