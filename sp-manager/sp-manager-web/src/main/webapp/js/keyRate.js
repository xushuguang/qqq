
    //动态折线图
    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });
    function activeLastPointToolip(chart) {
        var points = chart.series[0].points;
        chart.tooltip.refresh(points[points.length - 1]);
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
                        $.ajax({
                            url : 'getKeyRate',
                            type : 'post',
                            data:{"qkdId":id,"time": (new Date().getTime())},
                            success : function(keyRate){
                                console.log(keyRate)
                                var x = (new Date().getTime());
                                var y = keyRate;
                                series.addPoint([x, y], true, true);
                            }
                        });
                        activeLastPointToolip(chart);
                    }, 1000);
                }
            }
        },
        title: {
            text: '密钥速率实时曲线图'
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 150
        },
        yAxis: {
            title:{
                text:'速率'
            },
            startOnTick: true, //为true时，设置min才有效
            min:0,
            max:10,
            labels: {
                formatter: function() {
                    return this.value +'(kb/s)';
                }
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.series.name + '</b><br/>' +
                    Highcharts.dateFormat('%H:%M:%S', this.x) + '<br/>' + '<span style="color:#08c">' +
                    Highcharts.numberFormat(this.y) + ' kb/s' + '</span>';
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
                for (i = -59; i <= 0; i += 1) {
                    data.push({
                        x: time + i * 1000,
                        y: 0
                    });
                }
                return data;
            }())
        }]
    }, function (c) {
        activeLastPointToolip(c)
    });
