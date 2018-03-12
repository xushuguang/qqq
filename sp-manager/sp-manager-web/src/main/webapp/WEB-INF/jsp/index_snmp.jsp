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
    <title>首页</title>
    <!-- 导入easyui的样式表 -->
    <%--如果在页面上使用easyui框架：1、CSS  2、JS(注意载入顺序)--%>
    <link rel="stylesheet" href="js/jquery-easyui-1.5/themes/bootstrap/easyui.css">
    <link rel="stylesheet" href="js/jquery-easyui-1.5/themes/icon.css">
    <link rel="stylesheet" href="css/common.css">
    <style>
        #RTalarm{
            position: absolute;
            width: 40%;
            height:60%;
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
            width: 60%;
            height: 60%;
            left:0;
            top:6%;
        }
        #secretKey{
            position: absolute;
            width: 60%;
            height: 30%;
            left:0;
            bottom:0;
        }
    </style>
</head>
<body class="easyui-layout" >
<div data-options="region:'north'" style="height:70px;padding-left:10px;background-color: #00bbee" >
       <a style="font-size: large">量子秘钥网元管理系统</a>
    <div align="right">
        <a>欢迎${sessionScope.user.username}！</a>
        <button onclick="exitUser()" class="easyui-linkbutton" type="button"
                data-options="iconCls:'icon-undo'">退出/切换用户</button>
    </div>
</div>
<div data-options="region:'south'" style="padding:5px;background:#eee;">
    <div align="left">
        C版权所有@浙江科易理想量子信息技术股份有限公司
    </div>
    <div align="right">
        版本号1.0
    </div>
</div>
<div data-options="region:'west'" style="width:200px;">
    <div id="menu" class="easyui-accordion">
        <div title="网元管理" data-options="selected:true,iconCls:'icon-tip'" style="padding:10px 0;">
            <ul class="easyui-tree">
                <li data-options="attributes:{'href':'ne_list'}">设备管理</li>
                <li data-options="attributes:{'href':'D_view_manage'}">三维视图层管理</li>
                <li data-options="attributes:{'href':'secretKey_manager'}">量子秘钥管理层</li>
                <li data-options="attributes:{'href':' '}">量子秘钥应用层</li>
            </ul>
        </div>
        <div title="实时告警管理" data-options="iconCls:'icon-tip'" style="padding:10px 0;">
            <ul class="easyui-tree">
                <li data-options="attributes:{'href':'alarm_list'}">实时告警监控</li>
                <li data-options="attributes:{'href':' '}">实时告警配置</li>
                <li data-options="attributes:{'href':' '}">实时告警查询</li>
            </ul>
        </div>
        <div title="历史告警管理" data-options="iconCls:'icon-tip'" style="padding:10px 0;">
            <ul class="easyui-tree">
                <li data-options="attributes:{'href':'history_alarm_list'}">告警结果查询</li>
            </ul>
        </div>
        <div title="配置数据管理" data-options="iconCls:'icon-tip'" style="padding:10px 0;">
            <ul class="easyui-tree">
                <li data-options="attributes:{'href':' '}">#</li>
            </ul>
        </div>
        <div title="统计数据管理" data-options="iconCls:'icon-tip'" style="padding:10px 0;">
            <ul class="easyui-tree">
                <li data-options="attributes:{'href':'statistic_data_query'}">统计数据查询</li>
            </ul>
        </div>
        <div title="日志管理" data-options="iconCls:'icon-tip'" style="padding:10px 0;">
            <ul class="easyui-tree">
                <li data-options="attributes:{'href':' '}">系统操作日志</li>
            </ul>
        </div>
        <div title="用户管理" data-options="iconCls:'icon-tip'" style="padding:10px 0;">
            <ul class="easyui-tree">
                <li data-options="attributes:{'href':'user_group_manage'}">用户组管理</li>
                <li data-options="attributes:{'href':'user_group_add'}">添加用户组</li>
                <li data-options="attributes:{'href':'user_manage'}">用户管理</li>
                <li data-options="attributes:{'href':'user_add'}">添加用户</li>
            </ul>
        </div>
        <div title="工具箱" data-options="iconCls:'icon-tip'" style="padding:10px 0;">
            <ul class="easyui-tree">
                <li data-options="attributes:{'href':'online_help'}">在线帮助</li>
                <li data-options="attributes:{'href':' '}">连线样式管理</li>
                <li data-options="attributes:{'href':' '}">首页新闻管理</li>
            </ul>
        </div>
    </div>
</div>
<div data-options="region:'center'" style="background:#eee;">
    <div id="tab" class="easyui-tabs" data-options="fit:true">
        <div  title="主页" style="padding:20px;">
            <div id="element" ></div>
            <div id="RTalarm" ></div>
            <div id="Equipment" ></div>
            <div id="secretKey" ></div>
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
<script>
    snmp.onTreeClick();
</script>
<script>
    moment.locale();
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
    //ajax动态生成设备统计图表
    $.ajax({
        type: "get",
        async : true,
        url: 'statisticsNetElemet',
        dataType: "json",
        success : function (data) {
            // 绘制图表。
            echarts.init(document.getElementById('element')).setOption({
                title : {
                    text: '设备统计',
                    x:'center',
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
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
    //ajax动态生成实时告警监控图表
    $.ajax({
        type: "get",
        async : true,
        url: 'listRTalarmVo',
        dataType: "json",
        success : function (data) {
            // 绘制图表。
            echarts.init(document.getElementById('RTalarm')).setOption({
                title : {
                    text: '实时告警监控',
                    x:'center'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    x : 'center',
                    y : '90%',
                    data:['紧急告警','严重告警','重要告警','中等告警']
                },
                calculable : true,
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
    //ajax动态生成设备拓扑图
    $.ajax({
        type: "get",
        async : true,
        url: 'listNodeVo',
        dataType: "json",
        success : function (data) {
            // 绘制图表。
            echarts.init(document.getElementById('Equipment')).setOption({
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
                    formatter: function (params,ticket,callback) {
                        var nodeName=params.data.name;//当前选中节点数据
                        $.ajax({
                            async : true,//设置异、同步加载
                            cache : false,//false就不会从浏览器缓存中加载请求信息了
                            type : 'post',
                            data:{"nodeName":nodeName},
                            dataType : "json",
                            url : 'getNodeDetails',
                            success : function(data) { //请求成功后处理函数。
                                var res = "<table><caption align='top'>网元设备</caption><tr><td width='100px'>设备名</td><td width='150px'>设备IP</td><td width='100px'>设备状态</td></tr>";
                                for (var i =0;i<data.length;i++){
                                    res += '<tr><td>'+data[i].neName+'</td><td>'+data[i].neIp+'</td><td>';
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
                    }
                },
                series: [{
                    layoutAnimation : false,
                    itemStyle: {
                        normal: {
                            label: {
                                position: 'top',
                                show: true,
                                textStyle: {
                                    color: '#333'
                                }
                            },
                            nodeStyle: {
                                brushType: 'both',
                                borderColor: 'rgb(240,255,255)',
                                borderWidth: 1
                            },
                            linkStyle: {
                                normal: {
                                    color: 'source',
                                    curveness: 0,
                                    type: "solid"
                                }
                            }
                        },

                    },
                    force:{
                        initLayout: 'circular',//初始布局
                        repulsion:130,//斥力大小
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
                    minRadius: 15,
                    maxRadius: 25,
                    gravity: 1.1,
                    scaling: 1.1,
                    nodes: data.nodes,
                    links: data.links
                }]
            });
        }
    });
    //ajax动态生成密钥生成总量柱状图
    $.ajax({
        type: "get",
        async : true,
        url: 'listSecretKey',
        dataType: "json",
        success : function (data) {
            // 绘制图表。
            echarts.init(document.getElementById('secretKey')).setOption({
                title:{
                    text:"密钥生成总量"
                },
                tooltip:{},
                //x轴的文本
                xAxis:{
                    data:["01-05 09:34:44","01-05 09:39:44", "01-05 09:34:44","01-05 09:34:44", "01-05 09:34:44",
                        "01-05 09:34:44","01-05 09:34:44","01-05 09:34:44","01-05 09:34:44","01-05 09:34:44",
                        "01-05 09:34:44","01-05 09:34:44","01-05 09:34:44","01-05 09:34:44","01-05 09:34:44",
                        "01-05 09:34:44","01-05 09:34:44","01-05 09:34:44","01-05 09:34:44","01-05 09:34:44"]
                },
                //y轴的文本
                yAxis:{},
                series:[{
                    name:"",
                    type:"bar",
                    data:[9099,9000,9454,9887,8500,9099,9000,9454,9887,8500,9099,9000,9454,9887,8500,9099,9000,9454,9887,8500,]
                }]
            });
        }
    });
</script>
</body>
</html>
