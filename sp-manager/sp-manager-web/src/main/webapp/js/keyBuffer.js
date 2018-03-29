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
                    $.each(this.series, function(idx, series) {
                        setInterval(function() {
                            $.ajax({
                                url : 'getKeyBuffer',
                                type : 'post',
                                data:{"neName":neName},
                                success : function(keyBufferMap){
                                    console.log(keyBufferMap)
                                }
                            });
                            var x = (new Date()).getTime(), // current time
                                y = Math.random();
                            series.addPoint([x, y], true, true);
                        }, 10000);
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
            max: 1,
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
                    Highcharts.numberFormat(this.y*100, 2) + ' %' + '</span>';

            }
        },
        legend: {
            enabled: true
        },
        exporting: {
            enabled: false
        },
        series: [{
            name: 'keyBuffer1',
            data:(function() {
                // generate an array of random data
                var data = [],
                    time = (new Date()).getTime(),
                    i;
                for (i = -9; i <= 0; i++) {
                    data.push({
                        x: time + i * 10000,
                        y: 0
                    });
                }
                return data;
            })()
        }, {
            name: 'keyBuffer2',
            data: (function() {
                // generate an array of random data
                var data = [],
                    time = (new Date()).getTime(),
                    i;
                for (i = -9; i <= 0; i++) {
                    data.push({
                        x: time + i * 10000,
                        y: 0
                    });
                }
                return data;
            })()
        }
        ]
    };
    chart = new Highcharts.Chart(options);
});