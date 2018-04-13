
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
                            data:{"qkdId":id},
                            success : function(keyRate){
                                var x = (new Date()).getTime();;
                                var y;
                                if (keyRate==""){
                                    y = 0;
                                }else {
                                    y = parseInt(keyRate.keyRate)/1024;
                                }
                                series.addPoint([x, y], true, true);
                            }
                        });
                        activeLastPointToolip(chart);
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
            startOnTick: true, //为true时，设置min才有效
            min:0,
            max:30,
            labels: {
                formatter: function() {
                    return this.value +'(k/s)';
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
                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' + '<span style="color:#08c">' +
                    Highcharts.numberFormat(this.y) + ' k/s' + '</span>';
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
                for (i = -119; i <= 0; i += 1) {
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
