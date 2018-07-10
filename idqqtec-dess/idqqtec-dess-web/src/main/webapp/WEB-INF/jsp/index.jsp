<%--
  Created by IntelliJ IDEA.
  User: james.xu
  Date: 2018/7/9
  Time: 13:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>DESS Monitor</title>
    <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon"/>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <!-- 可选的Bootstrap主题文件（一般不使用） -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"></script>
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="js/highcharts/highcharts.js"></script>
    <script src="js/highcharts/exporting.js"></script>
    <script src="https://img.hcharts.cn/highcharts/highcharts-3d.js"></script>
    <script src="https://img.hcharts.cn/highcharts-plugins/highcharts-zh_CN.js"></script>
    <style>
        #topDIV{}
    </style>
</head>
<body>
<div class="container">
    <div class="row clearfix" id="topDIV">
        <div class="col-md-12 column">
            <div align="center">
                <img src="images/001.png" align="center">
            </div>
            <ul class="nav nav-pills">
                <li>
                    <a href="#" onclick="showIndex()">首页</a>
                </li>
                <li>
                    <a href="#" onclick="showAlarms()">告警</a>
                </li>
                <li>
                    <a href="#" onclick="showConfig()">配置</a>
                </li>
                <li class="dropdown pull-right">
                    <a href="#" data-toggle="dropdown" class="dropdown-toggle">更多<strong class="caret"></strong></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="#">登录</a>
                        </li>
                        <li>
                            <a href="#">注销</a>
                        </li>
                        <li>
                            <a href="#">更多设置</a>
                        </li>
                        <li class="divider">
                        </li>
                        <li>
                            <a href="#">关于我们</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>

    <br>
    <br>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div id="topologyDIV"  style="height: 500px"></div>
            <div id="tnDetailsDIV"  style="height: 500px">
                <div class="tabbable" id="tabs-517045">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="#showSSD" data-toggle="tab" onclick="showSSD()">SSD</a>
                        </li>
                        <li>
                            <a href="#showMysql" data-toggle="tab" onclick="showMysql()">数据库</a>
                        </li>
                        <li>
                            <a href="#showOthers" data-toggle="tab" onclick="showOthers()">其它</a>
                        </li>
                    </ul>
                    <br>
                    <div class="tab-content">
                        <div class="tab-pane active" id="showSSD"></div>
                        <div class="tab-pane" id="showMysql"></div>
                        <div class="tab-pane" id="showOthers">
                            <div class="col-md-3 four-grid" style="background-color: lawngreen; width: 15%">
                                <img src="images/security.png">
                                <br>
                                <h4>Security Memory</h4>
                                <h4>10</h4>
                            </div>
                            <div class="col-md-3 four-grid" style="width: 10%"></div>
                            <div class="col-md-3 four-grid" style="background-color: aqua; width: 15%">
                                <img src="images/rate.png">
                                <br>
                                <h4>QRNG KeyRate</h4>
                                <h4>1.5(kb/s)</h4>
                            </div>
                            <div class="col-md-3 four-grid" style="width: 10%"></div>
                            <div class="col-md-3 four-grid" style="background-color: darkturquoise; width: 15%">
                                <img src="images/database.png">
                                <br>
                                <h4>Mysql Size</h4>
                                <h4>205MB</h4>
                            </div>
                            <div class="col-md-3 four-grid" style="width: 10%"></div>
                            <div class="col-md-3 four-grid" style="background-color: yellow; width: 15%">
                                <img src="images/ssd.png">
                                <br>
                                <h4>SSD Size</h4>
                                <h4>25.3GB</h4>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="alarmsDIV"  style="height: 500px">
                <table class="table table-hover table-striped table-condensed">
                    <thead>
                    <tr>
                        <th>
                            告警id
                        </th>
                        <th>
                            告警网元
                        </th>
                        <th>
                            告警原因
                        </th>
                        <th>
                            告警级别
                        </th>
                        <th>
                            发生时间
                        </th>
                    </tr>
                    </thead>
                    <tbody id="alarmTB">
                    </tbody>
                </table>
            </div>
            <div id="configurationDIV"  style="height: 500px">
                This is configuration page
            </div>

        </div>
    </div>
</div>
<script src="js/highcharts/ssdPie.js"></script>
<script src="js/highcharts/mysql.js"></script>
<script src="js/echarts.js"></script>
<script src="js/alert.js"></script>
<script>
    $(document).ready(function(){
        showIndex();
    });
    function showIndex() {
        $("#tnDetailsDIV").hide();
        $("#alarmsDIV").hide();
        $("#configurationDIV").hide();
        $("#topologyDIV").show();
        //ajax动态生成设备拓扑图
        var equipmentCharts = echarts.init(document.getElementById('topologyDIV'));
        $.ajax({
            type: "post",
            async : true,
            url: 'tn/getTNTopology',
            dataType: "json",
            success : function (data) {
                console.log(data)
                // 绘制图表。
                equipmentCharts.setOption({
                    title: {
                        text:"设备拓扑图"
                    },
                    tooltip : {
                        show : true,   //默认显示
                        showContent:true, //是否显示提示框浮层
                        trigger:'item',//触发类型，默认数据项触发
                        triggerOn:'click',//提示触发条件，mousemove鼠标移至触发，还有click点击触发
                        alwaysShowContent:false, //默认离开提示框区域隐藏，true为一直显示
                        showDelay:0,//浮层显示的延迟，单位为 ms，默认没有延迟，也不建议设置。在 triggerOn 为 'mousemove' 时有效。
                        hideDelay:200,//浮层隐藏的延迟，单位为 ms，在 alwaysShowContent 为 true 的时候无效。
                        enterable:false,//鼠标是否可进入提示框浮层中，默认为false，如需详情内交互，如添加链接，按钮，可设置为 true。
                        position:'right',//提示框浮层的位置，默认不设置时位置会跟随鼠标的位置。只在 trigger 为'item'的时候有效。
                        confine:true,//是否将 tooltip 框限制在图表的区域内。外层的 dom 被设置为 'overflow: hidden'，或者移动端窄屏，导致 tooltip 超出外界被截断时，此配置比较有用。
                        transitionDuration:0.4,//提示框浮层的移动动画过渡时间，单位是 s，设置为 0 的时候会紧跟着鼠标移动。
                        backgroundColor:'rgba(192,192,192,1.0)',//通过设置rgba调节背景颜色与透明度
                        formatter: function (params,ticket,callback) {
                            if (params.dataType=='node'){//选择的是节点
                                var tnName =params.data.name;//当前选中节点数据
                                $.ajax({
                                    async : true,//设置异、同步加载
                                    cache : false,//false就不会从浏览器缓存中加载请求信息了
                                    type : 'post',
                                    data:{"tnName":tnName},
                                    url : 'tn/toTNDetails',
                                    success: function (data) {
                                        sessionStorage.setItem("tnIP",data);
                                        $("#topologyDIV").hide();
                                        $("#alarmsDIV").hide();
                                        $("#tnDetailsDIV").show();
                                        showSSD();
                                    }
                                });
                                return 'Loading';
                            }else if (params.dataType=='edge'){//选择的是连线
                                console.log(params)
                            }
                        }
                    },
                    series: [{
                        itemStyle: {
                            normal: {
                                lineWidth: 500,
                                textFont: 'bold 15px verdana',
                                textPosition: 'inside',
                                label: {
                                    position: 'top',
                                    show: true,
                                    textStyle: {
                                        color: '#000000'
                                    }
                                },
                            }
                        },
                        force:{
                            initLayout: 'circular',//初始布局
                            repulsion:400,//斥力大小
                        },
                        animation: false,
                        name:"",
                        type: 'graph',//关系图类型
                        layout: 'force',//引力布局
                        roam: false,//可以拖动
                        //legendHoverLink: true,//是否启用图例 hover(悬停) 时的联动高亮。
                        hoverAnimation: true,//是否开启鼠标悬停节点的显示动画
                        //coordinateSystem: null,//坐标系可选
                        //  xAxisIndex: 0, //x轴坐标 有多种坐标系轴坐标选项
                        //  yAxisIndex: 0, //y轴坐标
                        // ribbonType: true,
                        useWorker: false,
                        nodes: data.node,
                        links: data.link
                    }]
                });
            }
        });
    }
    function showAlarms() {
        $("#tnDetailsDIV").hide();
        $("#topologyDIV").hide();
        $("#configurationDIV").hide();
        $("#alarmsDIV").show();
        setInterval(function () {
            $.post(
                "alarm/getAlarms",
                function (dataStr) {
                    var html='';
                    var data = jQuery.parseJSON(dataStr);
                    for(var i in data){//遍历json数组时，这么写p为索引，0,1
                        html+="<tr class='"+data[i].alarmType+"'><td>"
                            +data[i].id +"</td><td>"
                            +data[i].alarmIP +"</td><td>"
                            +data[i].alarmReason +"</td><td>"
                            +data[i].alarmType +"</td><td>"
                            +data[i].alarmTime +"</td></tr>"
                    };
                    $("#alarmTB").html(html);
                });
        },1000);

    }
    function showConfig() {
        $("#tnDetailsDIV").hide();
        $("#topologyDIV").hide();
        $("#alarmsDIV").hide();
        $("#configurationDIV").show();
    }
</script>
</body>
</html>
