//动态折线图
$(document).ready(function() {
    Highcharts.setOptions({
        global: {
            useUTC: false
        },
    colors:"#08c,#ff5a00".split(","),
        symbols: ["circle","triangle"]
    });
    var options = {
        chart: {
        renderTo: 'keyBuffer',
            type: 'spline',
            marginRight: 10,
            events: {
                load: function() {
                    // set up the updating of the chart each second
                    // var series = this.series[0];
                    $.each(this.series, function(i, series) {
                        setInterval(function() {
                            $.ajax({
                                url : 'getKeyBuffer',
                                type : 'post',
                                data:{"neName":neName},
                                success : function(data){
                                    var x = (new Date()).getTime(); // current time
                                    var y;
                                    if(data.length>0){
                                        for(var j = 0; j < data.length; j++){
                                            if (chart.series[j].name==data[j].name) {
                                                y = parseInt(data[i].data);
                                            }
                                            series.addPoint([x, y], true, true);
                                        }
                                    }else {
                                        y = 0;
                                        series.addPoint([x, y], true, true);
                                    }
                                }
                            });
                        }, 1000*60);
                    });
                }
            }
        },
        title: {
            text: 'keyBuffer实时曲线图'
        },
        credits: {
            enabled: false
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 80
        },
        yAxis: {
            title: {
                text: ''
            },
            startOnTick: true, //为true时，设置min才有效
            min: 0,
            max: 100,
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            formatter: function() {
                return '<b>' + this.series.name + '</b><br/>' +
                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' + '<span style="color:#08c">' +
                    Highcharts.numberFormat(this.y, 0)+'%'+ '</span>';

            }
        },
        legend: {
            enabled: true
        },
        exporting: {
            enabled: false
        },
        series: create()
    };
    function create() {
        var seriesArr = new Array();
        $.ajax({
            url: 'getKeyBuffer',
            type: 'post',
            data: {"neName": neName},
            async : false, //同步处理后面才能处理新添加的series
            success: function (data) {
                var value =  (function () {
                    var data = [],
                        time = (new Date()).getTime(),
                        i;
                    for (i = -119; i <= 0; i += 1) {
                        data.push({
                            x: time + i * 1000 * 60,
                            y: 0
                        });
                    }
                    return data;
                }());
                if (data.length>0){
                    for (var i = 0; i < data.length; i++) {
                        var name = data[i].name;
                        seriesArr.push({"name": name, "data": value});
                    }
                }else {
                    var name = '无数据';
                    seriesArr.push({"name": name, "data": value});
                }
            }
        },false);
        if (seriesArr.length>0){
            return seriesArr;
        }
    }
    chart = new Highcharts.Chart(options);
});