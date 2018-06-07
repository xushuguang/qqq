<%--
  User: james.xu
  Date: 2018/1/16
  Time: 10:45
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>量子密钥网元管理系统</title>
    <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon"/>
    <!-- 导入easyui的样式表 -->
    <%--如果在页面上使用easyui框架：1、CSS  2、JS(注意载入顺序)--%>
    <link rel="stylesheet" href="js/jquery-easyui-1.5/themes/bootstrap/easyui.css">
    <link rel="stylesheet" href="js/jquery-easyui-1.5/themes/icon.css">
    <link rel="stylesheet" href="css/common.css">
    <style>
        #sign{
            width: 172px;
            height: 62px;
            position: absolute;
            left: 0;
            background: url("images/sign.png");
        }
        #title{
            position: absolute;
            top: 20%;
            left: 40%;
            width: 20%;
        }
        #HistoryAlarm{
            position: absolute;
            width: 40%;
            height:50%;
            right:0;
            top:6%;
        }
        #element{
            position: absolute;
            width: 40%;
            height:30%;
            right: 0;
            bottom:0;
        }
        #Equipment{
            position: absolute;
            width: 50%;
            height: 70%;
            left:0;
            top:6%;
        }
        #banquan{
            position: absolute;
            bottom:30%;
        }
        a{
        text-decoration:none;
        }
    </style>
</head>
<body class="easyui-layout" >
<div data-options="region:'north'" style="height:70px;padding-left:10px;background: url('images/111.jpg')" >
    <div id="sign"></div>
    <div id="title">
        <a style="font-size: large;color: cyan">量子保密通信网元管理系统</a>
    </div>
    <div align="right" style="position: absolute; right: 2%">
        <a style="color: aliceblue">当前用户: ${sessionScope.user.username}</a><br><br>
        <button onclick="exitUser()" class="easyui-linkbutton" type="button"
                data-options="iconCls:'icon-undo'">退出/切换用户</button>
    </div>
</div>
<div data-options="region:'south'" style="padding:5px;background:#eee;">
    <div id='banquan'align="left">
        C版权所有@<a href="http://www.idq-qtec.com/index.aspx" style="color: teal" target="_blank">浙江科易理想量子信息技术有限公司</a>
    </div>
    <div align="right">
        <a href="html/about_us.html" target="_blank">关于我们</a>
    </div>
</div>
<div data-options="region:'west'" style="width:200px;">
    <p hidden="hidden">${sessionScope.user.id}</p>
    <div id="menu" class="easyui-accordion">
        <!--
        <div title="网元管理" style="padding:10px 0;">
            <ul class="easyui-tree">
                <li data-options="attributes:{'href':'ne_manage'}">网元管理</li>
                <li data-options="attributes:{'href':'node_manage'}">节点管理</li>
                <li data-options="attributes:{'href':'TN'}">量子秘钥管理层</li>
            </ul>
        </div>
        <div title="实时告警管理"  style="padding:10px 0;">
            <ul class="easyui-tree">
                <li data-options="attributes:{'href':'alarm_list'}">实时告警监控</li>
            </ul>
        </div>
        <div title="历史告警管理"  style="padding:10px 0;">
            <ul class="easyui-tree">
                <li data-options="attributes:{'href':'history_alarm_list'}">告警结果查询</li>
            </ul>
        </div>
        <div title="统计数据管理"  style="padding:10px 0;">
            <ul class="easyui-tree">
                <li data-options="attributes:{'href':'statistic_data_query'}">统计数据查询</li>
            </ul>
        </div>
        <div title='用户管理'  style='padding:10px 0;'>
            <ul class="easyui-tree">
                <li data-options="attributes:{'href':'user_group_manage'}">用户组管理</li>
                <li data-options="attributes:{'href':'user_group_add'}">添加用户组</li>
                <li data-options="attributes:{'href':'user_manage'}">用户管理</li>
                <li data-options="attributes:{'href':'user_add'}">添加用户</li>
            </ul>
        </div>
        <div title="工具箱"  style="padding:10px 0;">
            <ul class="easyui-tree">
                <li data-options="attributes:{'href':'online_help'}">在线帮助</li>
            </ul>
        </div>
        -->
    </div>
</div>
<div data-options="region:'center'" style="background:#eee;">
    <div id="tab" class="easyui-tabs" data-options="fit:true">
        <div  title="主页" style="padding:20px;">
            <div id="element" ></div>
            <div id="HistoryAlarm" ></div>
            <div id="Equipment"></div>
        </div>
    </div>
</div>
<!-- jquery -->
<script src="js/jquery-easyui-1.5/jquery.min.js"></script>
<!-- jquery easyui -->
<script src="js/jquery-easyui-1.5/jquery.easyui.min.js"></script>
<script src="js/jquery-easyui-1.5/locale/easyui-lang-zh_CN.js"></script>
<script src="js/echarts.js"></script>
<script src="js/moment/moment-with-locales.js"></script>
<script src="js/common.js"></script>
<!-- 引入验证输入框js-->
<script src="js/validatebox.js"></script>
<!--highcharts-->
<script src="js/highcharts/highcharts.js"></script>
<script src="js/highcharts/exporting.js"></script>
<script>
    snmp.onTreeClick();
</script>
<script>
    moment.locale();
</script>
<script>
    var uid = $("p").html();
    $(document).ready(function(){
        $.post(
            //url，提交给后台谁去处理
            'user/findMenu',
            //data，提交什么到后台，ids
            {'uid': uid},
            //callback,相当于$.ajax中success
            function (data) {
                for (var key in data) {
                    $('#menu').accordion('add', {
                        title: key,
                        iconCls: 'icon-tip',
                        selected: false,
                        content: '<div style="padding:10px"><ul name="' + key + '"></ul></div>',
                    });
                    $("ul[name='" + key + "']").tree({
                        data:data[key],
                        onClick : function(node) {
                            console.log(node);
                                snmp.addTabs(node.text,node.url);
                        }
                    });
            };
       });
    });
</script>
<script>
    //退出当前用户按钮
    function exitUser() {
        $.messager.confirm('确认', '您确定要退出当前用户吗？', function (r) {
            if (r) {
                //发出ajax请求
                //$.ajax() $.post() $.get()
                $.get(
                    //url，提交给后台谁去处理
                    'exitUser',
                    function () {
                        window.location.href='login';
                    }
                );
            }
        });
    }
    var elementCharts = echarts.init(document.getElementById('element'));
    $.ajax({
        type: "get",
        async : true,
        url: 'statisticsNetElemet',
        dataType: "json",
        success : function (data) {
            // 绘制图表。
            elementCharts.setOption({
                title : {
                    text: '设备统计',
                    x:'center',
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                color:['lightseagreen','green','navy'],
                calculable : true,
                series : [
                    {
                        type:'pie',
                        radius : '30%',
                        center: ['50%', '60%'],
                        data:data
                    }
                ]
            });
        }
    });
    var historyAlarmCharts = echarts.init(document.getElementById('HistoryAlarm'));
    function refresh(){
        //ajax动态生成历史告警监控图表
        $.ajax({
            type: "get",
            async : true,
            url: 'listHistoryAlarmVo',
            dataType: "json",
            success : function (data) {
                // 绘制图表。
                historyAlarmCharts.setOption({
                    title : {
                        text: '告警监控',
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        x : 'center',
                        y : '90%',
                        data:['紧急告警','严重告警','重要告警']
                    },
                    calculable : true,
                    color:['dimgrey','orange','red'],
                    series : [
                        {
                            type:'pie',
                            radius : [20, 100],
                            center : ['50%', 150],
                            roseType : 'area',
                            x: '20%',
                            max: 20,
                            sort : 'ascending',
                            data: data
                        }
                    ]
                });
            }
        });
    }
    //第一次加载时刷新
    window.onload=refresh;
    //定时刷新
    timerID = setInterval("refresh()",1000*60);
    //ajax动态生成设备拓扑图
    var equipmentCharts = echarts.init(document.getElementById('Equipment'));
    $.ajax({
        type: "get",
        async : true,
        url: 'listNodeVo',
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
                            console.log(params)
                            var nodeName=params.data.name;//当前选中节点数据
                            $.ajax({
                                async : true,//设置异、同步加载
                                cache : false,//false就不会从浏览器缓存中加载请求信息了
                                type : 'post',
                                data:{"nodeName":nodeName},
                                dataType : "json",
                                url : 'getNodeDetails',
                                success : function(data) { //请求成功后处理函数。
                                    var res = "<table><caption align='top'style='color: black'>网元设备</caption><tr><td width='100px' style='color: black'>设备名</td><td width='150px' style='color: black'>设备IP</td><td width='100px' style='color: black'>设备状态</td></tr>";
                                    for (var i =0;i<data.length;i++){
                                        res += '<tr><td style=\'color: black\'>'+data[i].neName+'</td><td style=\'color: black\'>'+data[i].neIp+'</td><td>';
                                        if(data[i].state==0){
                                            res +="<div style='width: 15px;height: 15px;background-color: red ;border-radius: 50%;'></div></td></tr>";
                                        }else if(data[i].state==1){
                                            res +="<div style='width: 15px;height: 15px;background-color: yellow   ;border-radius: 50%;'></div></td></tr>";
                                        }else if(data[i].state==2){
                                            res +="<div style='width: 15px;height: 15px;background-color: green ;border-radius: 50%;'></div></td></tr>";
                                        }

                                    }
                                    res += '</table>';
                                    callback(ticket,res);
                                },
                                error : function() {//请求失败处理函数
                                    $.messager.alert('警告', '请求失败！', 'warning');
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
                        repulsion:300,//斥力大小
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
                    nodes: data.nodes,
                    links: data.links
                }]
            });
        }
    });
</script>
</body>
</html>
