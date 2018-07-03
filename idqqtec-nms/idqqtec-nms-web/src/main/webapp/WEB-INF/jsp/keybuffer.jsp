<%--
  User: james.xu
  Date: 2018/3/12
  Time: 9:23
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
          pageEncoding="UTF-8" %>
<style>
    #tnDetails{
        position: absolute;
        width: 20%;
        height: 40%;
        left:0;
        top:8%;
    }
    #keyBuffer{
        position: absolute;
        width: 100%;
        height: 35%;
        left:0;
        bottom: 0;
        margin: 0 auto;
    }
    #HistorykeyBuffer{
        position: absolute;
        width: 80%;
        height: 50%;
        right:0;
        top:8%;
        margin: 0 auto;
    }
</style>
<div id="tnDetails">
    <table id="tbDetails" class="easyui-propertygrid"data-options="columns:mycolumns"></table>
    <script>
        var mycolumns = [[
            {field:'name',title:'属性',width:'35%'},
            {field:'value',title:'值',width:'55%'}
        ]];
    </script>
</div>
<div id="HistorykeyBuffer"></div>
<div id="keyBuffer"></div>
<script src="js/keyBuffer.js"></script>
<script src="js/currentTime.js"></script>
<script>
    //设备详情数据表格
    var pairId = sessionStorage.getItem("pairId");
    var neName = sessionStorage.getItem("neName");
    $('#tbDetails').propertygrid({
        url: 'getTNDetails?neName='+neName+'&&pairId='+pairId,
        showGroup: true,
        resizable: true,
        scrollbarSize: 0
    });
    //获取历史keyrate折线图
    function getHistoryKeyBuffer() {
        var arr = [];
        $.post(
            //url，提交给后台谁去处理
            'getAllKeyBuffer',
            //data，提交什么到后台，ids
            {'neName': neName,"pairId":pairId },
            //callback,相当于$.ajax中success
            function (dataStr) {
                var data = $.parseJSON(dataStr);
                for(var i in data){
                    var dateStr = CurentTime()+data[i].time;
                    dateStr = dateStr.replace(/-/g,"/");
                    var date = new Date(dateStr );
                    arr.push([
                        date,
                        parseInt(data[i].keybuffer)
                    ]);
                }
                var data1 = arr;
                Highcharts.chart('HistorykeyBuffer', {
                    chart: {
                        zoomType: 'x'
                    },
                    title: {
                        text: '可用密钥量历史折线图'
                    },
                    xAxis: {
                        type: 'datetime',
                        dateTimeLabelFormats: {
                            millisecond: '%H:%M:%S.%L',
                            second: '%H:%M:%S',
                            minute: '%H:%M',
                            hour: '%H:%M',
                            day: '%m-%d',
                            week: '%m-%d',
                            month: '%Y-%m',
                            year: '%Y'
                        },
                    },
                    tooltip: {
                        formatter: function () {
                            return '<b>' + this.series.name + '</b><br/>' +
                                Highcharts.dateFormat('%H:%M:%S', this.x) + '<br/>' + '<span style="color:#08c">' +
                                Highcharts.numberFormat(this.y) + ' %' + '</span>';
                        }
                    },
                    yAxis: {
                        min:0,
                        max:100,
                        title: {
                            text: '可用密钥量'
                        },
                        labels: {
                            formatter: function() {
                                return this.value +'(%)';
                            }
                        },
                    },
                    legend: {
                        enabled: false
                    },
                    plotOptions: {
                        area: {
                            fillColor: {
                                linearGradient: {
                                    x1: 0,
                                    y1: 0,
                                    x2: 0,
                                    y2: 1
                                },
                                stops: [
                                    [0, Highcharts.getOptions().colors[0]],
                                    [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                                ]
                            },
                            marker: {
                                radius: 2
                            },
                            lineWidth: 1,
                            states: {
                                hover: {
                                    lineWidth: 1
                                }
                            },
                            threshold: null
                        }
                    },
                    series: [{
                        type: 'area',
                        name: 'keyBuffer',
                        data: data1
                    }]
                });
            })
    }
    $(document).ready(function(){
        getHistoryKeyBuffer();
    });
     (getHistoryKeyBuffer(),1000*60*5);
</script>

