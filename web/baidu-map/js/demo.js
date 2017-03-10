var jQuery = $ = require("jQuery");
console.log("1");
$(function () {
  var map = new BMap.Map("allmap");            // 创建Map实例
  var pointA = new BMap.Point(113.92983, 22.509397); // 创建点坐标   地址 蛇口沃尔玛	
  var pointB = new BMap.Point(113.942129, 22.522642); //地址 海岸城

  map.centerAndZoom(pointA, 15);
  map.enableScrollWheelZoom();                 //启用滚轮放大缩小
  map.addControl(new BMap.NavigationControl());
  map.addControl(new BMap.ScaleControl());
  map.addControl(new BMap.OverviewMapControl());
  //map.setCurrentCity("北京"); // 设置当前城市信息
  /*
     *  给地图添加监控 获取当前点击经纬度
      map.addEventListener("click", function(e){
      alert(e.point.lng + ", " + e.point.lat); 
      });
  */

  addMarker(pointA, "蛇口沃尔玛", "地址：工业大道和东滨路交汇处<br/>电话：(0755)26816711");        // 创建标注1  
  addMarker(pointB, "海岸城购物中心", "地址：文心五路33号海岸城东座212号<br/>电话：(0755)86125888");        // 创建标注2  

  //开始测距		
  var polyline = new BMap.Polyline([pointA, pointB], { strokeColor: "orange", strokeWeight: 6, strokeOpacity: 0.5 });  //定义折线
  map.addOverlay(polyline);     //添加折线到地图上
  polyline.addEventListener("click", function () {    //监听标注事件		
    alert('从蛇口沃尔玛到海岸城购物中心的距离是：' + (map.getDistance(pointA, pointB)).toFixed(2) + ' 米。');  //获取两点距离,保留小数点后两位
  });

  //获取驾车路线
  //var driving = new BMap.DrivingRoute(map, { renderOptions: { map: map, autoViewport: true} });
  //driving.search(pointA, pointB);

  // 编写自定义函数,创建标注
  function addMarker(point, a, b) {
    var marker = new BMap.Marker(point);
    map.addOverlay(marker);
    //map.removeOverlay(marker);                  // 将标注从地图中移除
    //marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
    marker.addEventListener("click", function () {    //监听标注事件  
      var opts = {    //创建信息窗口
        width: 250,     // 信息窗口宽度    
        height: 100,     // 信息窗口高度    
        title: a  // 信息窗口标题   
      }
      var infoWindow = new BMap.InfoWindow(b, opts);  // 创建信息窗口对象    
      map.openInfoWindow(infoWindow, point);      //打开信息窗口			
      //alert("marker的位置是" + p.lng + "," + p.lat);   
    });
  }
});
console.log("2");