<%--
  User: james.xu
  Date: 2018/3/12
  Time: 9:23
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
          pageEncoding="UTF-8" %>
<style>
    #tdDetails{
        position: absolute;
        width: 20%;
        height: 40%;
        left:0;
        top:8%;
    }
    #keyRate{
        position: absolute;
        width: 100%;
        height: 35%;
        left:0;
        bottom: 0;
        margin: 0 auto;
    }
    #HistorykeyRate{
        position: absolute;
        width: 80%;
        height: 50%;
        right:0;
        top:8%;
        margin: 0 auto;
    }
</style>
<div id="tdDetails">
    <table id="pgDetails" class="easyui-propertygrid"data-options="columns:mycolumns"></table>
    <script>
        var mycolumns = [[
            {field:'name',title:'属性',width:'45%'},
            {field:'value',title:'值',width:'45%'}
        ]];
    </script>
</div>
<div id="HistorykeyRate"></div>
<div id="keyRate"></div>

<script src="js/keyRate.js"></script>
<script>
    //设备详情数据表格
    var id = sessionStorage.getItem("id");
    $('#pgDetails').propertygrid({
        url: 'getNEDetails/'+id,
        showGroup: true,
        resizable: true,
        scrollbarSize: 0
    });
    //获取历史keyrate折线图
    function getHistoryKeyRate() {
        var arr = [];
        $.post(
            //url，提交给后台谁去处理
            'getAllKeyRate',
            //data，提交什么到后台，ids
            {'qkdId': id },
            //callback,相当于$.ajax中success
            function (data) {
                $.each(data, function (i, item) {
                    var dateStr = '2018-5-16 '+item.time;
                    dateStr = dateStr.replace(/-/g,"/");
                    var date = new Date(dateStr );
                    arr.push([
                        date,
                        parseInt(item.keyrate)/1024
                    ]);
                });
                var data1 = arr;
                Highcharts.chart('HistorykeyRate', {
                    chart: {
                        zoomType: 'x'
                    },
                    title: {
                        text: '密钥速率历史折线图'
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
                                Highcharts.numberFormat(this.y) + ' kb/s' + '</span>';
                        }
                    },
                    yAxis: {
                        min:0,
                        title: {
                            text: '速率'
                        },
                        labels: {
                            formatter: function() {
                                return this.value +'(kb/s)';
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
                        name: 'keyRate',
                        data: data1
                    }]
                });
            })
    }
    $(document).ready(function(){
        getHistoryKeyRate();
    });
    setTimeout(getHistoryKeyRate(),1000*60*30);
</script>

