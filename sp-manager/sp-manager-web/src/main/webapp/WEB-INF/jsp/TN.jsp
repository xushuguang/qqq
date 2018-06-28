<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<style>
    #sercretKeyManager{
        position: absolute;
        width: 80%;
        height: 80%;
        left:0;
        top:6%;
    }
</style>
<div id="sercretKeyManager"></div>
<script>
    //ajax动态生成设备拓扑图
    var TNCharts = echarts.init(document.getElementById('sercretKeyManager'));
    $.ajax({
        type: "get",
        async : true,
        url: 'listNetElementVo',
        dataType: "json",
        success : function (data) {
            console.log(data)
            // 绘制图表。
            TNCharts.setOption({
               //标题
                title: {
                    text:"密钥管理层拓扑图",
                    x:'left',
                    y:'top'
                },
                tooltip : {
                    show : true,   //默认显示
                    showContent:false, //是否显示提示框浮层
                    trigger:'item',//触发类型，默认数据项触发
                    triggerOn:'mousemove',//提示触发条件，mousemove鼠标移至触发，还有click点击触发
                    alwaysShowContent:false, //默认离开提示框区域隐藏，true为一直显示
                    showDelay:0,//浮层显示的延迟，单位为 ms，默认没有延迟，也不建议设置。在 triggerOn 为 'mousemove' 时有效。
                    hideDelay:200,//浮层隐藏的延迟，单位为 ms，在 alwaysShowContent 为 true 的时候无效。
                    enterable:true,//鼠标是否可进入提示框浮层中，默认为false，如需详情内交互，如添加链接，按钮，可设置为 true。
                    position:'right',//提示框浮层的位置，默认不设置时位置会跟随鼠标的位置。只在 trigger 为'item'的时候有效。
                    confine:false,//是否将 tooltip 框限制在图表的区域内。外层的 dom 被设置为 'overflow: hidden'，或者移动端窄屏，导致 tooltip 超出外界被截断时，此配置比较有用。
                    transitionDuration:0.4,//提示框浮层的移动动画过渡时间，单位是 s，设置为 0 的时候会紧跟着鼠标移动。
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
                                    color: 'red',
                                    curveness: 1,
                                    type: "solid",
                                }
                            }
                        },
                        emphasis: {
                            label: {
                                show: false
                                // textStyle: null      // 默认使用全局文本样式，详见TEXTSTYLE
                            },
                            nodeStyle: {
                                //r: 30
                            },
                            linkStyle: {}
                        }

                    },
                    force:{
                        initLayout: 'circular',//初始布局
                        repulsion:600,//斥力大小
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
                    links: data.links,
                }]
            });
        }
    });
    //点击事件
    TNCharts.on("click", function (param){
        setTimeout(function(){
            if(param.dataType == "node"){//点中节点
                var neName = sessionStorage.getItem("neName");
                var neName1=param.data.name;//当前选中节点数据
                sessionStorage.setItem("neName",neName1);
                //跳转到TN详情页
                snmp.closeTabs(neName+"详情");
                snmp.addTabs(neName1+"详情","TN_details");
            }
        },500);
    });
</script>
