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
        width: 50%;
        left:0;
        top:5%;
    }
    #keyRate{
        position: absolute;
        width: 50%;
        height: 50%;
        right: 0;
        bottom: 0;
        margin: 0 auto;
    }
</style>
<div id="tdDetails">
    <table id="pgDetails"></table>
</div>
<div id="keyRate"></div>
<script>
    //设备详情数据表格
    var id = sessionStorage.getItem("id");
    $('#pgDetails').propertygrid({
        url: 'getNEDetails/'+id,
        showGroup: true,
        scrollbarSize: 0
    });
    //动态折线图
    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });
    function activeLastPointToolip(chart) {
        var points = chart.series[0].points;
        chart.tooltip.refresh(points[points.length -1]);
    }
    $('#keyRate').highcharts({
        chart: {
            type: 'spline',
            animation: Highcharts.svg, // don't animate in old IE
            marginRight: 10,
            events: {
                load: function () {
                    // set up the updating of the chart each second
                    var series = this.series[0],
                        chart = this;
                    setInterval(function () {
                        var x = (new Date()).getTime(), // current time
                            y = Math.random();
                        series.addPoint([x, y], true, true);
                        activeLastPointToolip(chart)
                    }, 1000);
                }
            }
        },
        title: {
            text: 'keyRate实时曲线图'
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 150
        },
        yAxis: {
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.series.name +':'+Highcharts.numberFormat(this.y,2)+'</b><br/>' +
                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>';
            }
        },
        legend: {
            enabled: false
        },
        exporting: {
            enabled: false
        },
        credits: {
            enabled: false
        },
        series: [{
            name: 'keyRate',
            data: (function () {
                // generate an array of random data
                var data = [],
                    time = (new Date()).getTime(),
                    i;
                for (i = -19; i <= 0; i += 1) {
                    data.push({
                        x: time + i * 1000,
                        y: Math.random()
                    });
                }
                return data;
            }())
        }]
    }, function(c) {
        activeLastPointToolip(c)
    });
</script>

