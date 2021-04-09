<template>
  <div class="nanum-bold">
    <div v-show="isview" ref="myDiagramDiv"></div>
    <div ref="myOverviewDiv" id="overviewdiv" style="margin-right: 25px;"></div>
  </div>
</template>

<script>
let go = window.go;
let $ = go.GraphObject.make;
let myDiagram
export default {
  name: "Overview",
  props: {
    roadmapData: Object,
  },
  data() {
    return {
      // diagram 숨기기위한 변수
      isview: 0,
    }
  },
  mounted() {
    // 로드맵 선언
    let go = window.go;
    let $ = go.GraphObject.make;
    myDiagram = 
      $(go.Diagram, this.$refs.myDiagramDiv, {
        initialContentAlignment: go.Spot.Center,
        "undoManager.isEnabled": true,
        isReadOnly: true,
        allowSelect: false,
      });

    // 로드맵 모델 설정
    myDiagram.nodeTemplateMap.add("blue", 
      $(go.Node, "Table", this.nodeStyle(),
        $(go.Panel, "Auto",
          $(go.Shape, "RoundedRectangle",
            {
              fill: "rgb(255, 255 ,255)",
              stroke: "rgb(15, 76, 129)",
              strokeWidth: 3,
              strokeJoin: "round",
              strokeCap: "square",
              
            },
            new go.Binding("figure", "figure")
          ),
          $(
            go.TextBlock,
            this.textStyle(),
            {
              margin: 8,
              maxSize: new go.Size(160, NaN),
              wrap: go.TextBlock.WrapFit,
              editable: false
            },
            new go.Binding("text").makeTwoWay()
          )
        ),
        this.makePort("T", go.Spot.Top, go.Spot.TopSide, false, true),
        this.makePort("L", go.Spot.Left, go.Spot.LeftSide, true, true),
        this.makePort("R", go.Spot.Right, go.Spot.RightSide, true, true),
        this.makePort("B", go.Spot.Bottom, go.Spot.BottomSide, true, false)
      )
    );

    // 중분류 커리큘럼 모델
    myDiagram.nodeTemplateMap.add("black", 
      $(go.Node, "Table", this.nodeStyle(),
        $(go.Panel, "Auto",
          $(go.Shape, "RoundedRectangle",
            {
              fill: "rgb(255, 255, 255)",
              stroke: "rgb(132, 137, 140)",
              strokeWidth: 2.5,
              strokeJoin: "round",
              strokeCap: "square"
            },
            new go.Binding("figure", "figure")
          ),
          $(
            go.TextBlock,
            this.textStyle(),
            {
              margin: 8,
              maxSize: new go.Size(160, NaN),
              wrap: go.TextBlock.WrapFit,
              editable: false
            },
            new go.Binding("text").makeTwoWay()
          )
        ),
        this.makePort("T", go.Spot.Top, go.Spot.TopSide, false, true),
        this.makePort("L", go.Spot.Left, go.Spot.LeftSide, true, true),
        this.makePort("R", go.Spot.Right, go.Spot.RightSide, true, true),
        this.makePort("B", go.Spot.Bottom, go.Spot.BottomSide, true, false)
      )
    );

    // 소분류 커리큘럼 모델
    myDiagram.nodeTemplateMap.add("green", 
      $(go.Node, "Table", this.nodeStyle(),
        $(go.Panel, "Auto",
          $(go.Shape, "RoundedRectangle",
            {
              fill: "rgb(255, 255, 255)",
              stroke: "#307363",
              strokeWidth: 2,
              strokeJoin: "round",
              strokeCap: "square"
            },
            new go.Binding("figure", "figure")
          ),
          $(
            go.TextBlock,
            this.textStyle(),
            {
              margin: 8,
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
    myDiagram.nodeTemplateMap.add("Start",
      $(go.Node, "Table", this.nodeStyle(),
        $(go.Panel, "Spot",
          $(go.Shape, "Circle", {
            desiredSize: new go.Size(70, 70),
            fill: "#ffffff",
            stroke: "#F04A5E",
            strokeWidth: 3.5
          }),
          $(go.TextBlock, "Start", this.textStyle(), new go.Binding("text"))
        ),
        // three named ports, one on each side except the top, all output only:
        this.makePort("L", go.Spot.Left, go.Spot.Left, true, false),
        this.makePort("R", go.Spot.Right, go.Spot.Right, true, false),
        this.makePort("B", go.Spot.Bottom, go.Spot.Bottom, true, false)
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
              strokeWidth: 2.5,
              strokeJoin: "round",
              strokeCap: "square",
            },
            new go.Binding("figure", "figure")
          ),
          $(
            
            go.TextBlock,
            this.textStyle(),
            {
              margin: 8,
              maxSize: new go.Size(160, NaN),
              wrap: go.TextBlock.WrapFit,
            },
            new go.Binding("text").makeTwoWay()
          ),
        ),
        // four named ports, one on each side: node의 가지 옵션
        this.makePort("T", go.Spot.Top, go.Spot.TopSide, false, true),
        this.makePort("L", go.Spot.Left, go.Spot.LeftSide, true, true),
        this.makePort("R", go.Spot.Right, go.Spot.RightSide, true, true),
        this.makePort("B", go.Spot.Bottom, go.Spot.BottomSide, true, false)
      )
    );

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
        { isPanelMain: true, stroke: "#1B443C", strokeWidth: 2.5 },
        new go.Binding("stroke", "isSelected", function(sel) {
          return sel ? "#1B443C" : "#1B443C";
        }).ofObject()
      ),
      $(
        go.Shape, // 화살표 모양
        {
          toArrow: "Triangle",
          strokeWidth: 1.5,
          stroke: "#1B443C",
          fill: "#307362"
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

    // 링크연결시 화살표가 직교하는 모양으로 보일 수 있도록 하는 설정
    myDiagram.toolManager.linkingTool.temporaryLink.routing = go.Link.Orthogonal;
    myDiagram.toolManager.relinkingTool.temporaryLink.routing = go.Link.Orthogonal;
    

    myDiagram.model = go.Model.fromJson(this.roadmapData);
    myDiagram.hasVerticalScrollbar = false;
    myDiagram.hasHorizontalScrollbar = false;
    let myOverview = 
      $(go.Overview, this.$refs.myOverviewDiv,
        { observed: myDiagram,
          contentAlignment: go.Spot.Center,
          drawsTemporaryLayers: false,
          "box.visible" : false,
        }
      );
  },
  methods: {
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
          locationSpot: go.Spot.Center
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
        font: "bold 11pt Lato, Helvetica, Arial, sans-serif",
        stroke: "#000000"
      };
    },
  }
}
</script>

<style>
#overviewdiv {
  width: 200px;
  height: 125px;
}
#overviewdiv canvas {
  width: 200px;
  height: 125px;
  outline: none;
}
</style>