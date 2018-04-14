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
        width: 30%;
        height: 30%;
        left:0;
        top:5%;
    }
    #keyRate{
        position: absolute;
        width: 90%;
        height: 50%;
        left:0;
        bottom: 0;
        margin: 0 auto;
    }
    #HistorykeyRate{
        position: absolute;
        width: 70%;
        height: 40%;
        right:0;
        top:5%;
        margin: 0 auto;
    }
    #historyKeyRateBar{
        position: absolute;
        width: 50%;
        height: 5%;
        right:5%;
        top:5%;
        margin: 0 auto;
    }
</style>
<div id="tdDetails">
    <table id="pgDetails"></table>
</div>
<div id="HistorykeyRate"></div>
<div id="keyRate"></div>
<div id="historyKeyRateBar">
    从：<input type="text" id="time1"  editable="false"
             class="easyui-datetimebox"/>
    到：<input type="text" id="time2"  editable="false"
             class="easyui-datetimebox"/>
    <input type="button" onclick="historyKeyRateClick()" value="查询">
</div>
<script src="js/keyRate.js"></script>
<script>
    //设备详情数据表格
    var id = sessionStorage.getItem("id");
    $('#pgDetails').propertygrid({
        url: 'getNEDetails/'+id,
        showGroup: true,
        scrollbarSize: 0
    });
    var historyKeyRateCharts =  echarts.init(document.getElementById('HistorykeyRate'));
    function historyKeyRateClick() {
        var time1 = $('#time1').datetimebox("getValue");
        var time2 = $('#time2').datetimebox("getValue");
        console.log(time1+'|'+time2)
        $.post(
            //url，提交给后台谁去处理
            'getKeyRateForTime',
            //data，提交什么到后台，ids
            {'qkdId': id , 'time1': time1 , 'time2': time2 },
            //callback,相当于$.ajax中success
            function (data) {
                console.log(data)
                var dataArry = JSON.parse(data);
                historyKeyRateCharts.setOption(
                    {
                        title : {
                            text: 'KeyRate历史曲线图',
                        },
                        tooltip : {
                            formatter: function() {
                                return '<b>' + this.series.name + '</b><br/>' +
                                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' + '<span style="color:#08c">' +
                                    Highcharts.numberFormat(this.y, 0) + '%' + '</span>';
                            }
                        },
                        toolbox: {
                            show : false,
                            feature : {
                                dataView : {show: true, readOnly: false},
                                magicType : {show: true, type: ['line', 'bar']},
                                restore : {show: true},
                                saveAsImage : {show: true}
                            }
                        },
                        calculable : true,
                        xAxis : [
                            {
                                type : 'category',
                                boundaryGap : false,
                            }
                        ],
                        yAxis : [
                            {
                                type : 'value',
                                axisLabel : {
                                    formatter: '{value} KB/S'
                                }
                            }
                        ],
                        series : [
                            {
                                type: 'line',
                                data: dataArry
                            }
                        ]
                    }
                );
            }
        );
    }
</script>

