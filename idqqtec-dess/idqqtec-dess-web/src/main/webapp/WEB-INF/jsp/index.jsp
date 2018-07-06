<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>aaaaa</title>
    <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon"/>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <!-- 可选的Bootstrap主题文件（一般不使用） -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"></script>
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="js/highcharts/highcharts.js"></script>
    <script src="js/highcharts/exporting.js"></script>
    <script src="https://img.hcharts.cn/highcharts/highcharts-3d.js"></script>
    <script src="https://img.hcharts.cn/highcharts-plugins/highcharts-zh_CN.js"></script>
    <style>
        .container{

        }
        .dropdown{
            position:relative;
            z-index:66;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div align="center">
                <img src="images/001.png" align="center">
            </div>
            <ul class="nav nav-pills">
                <li>
                    <a href="#" onclick="showIndex()">首页</a>
                </li>
                <li>
                    <a href="#" onclick="showSSD()">SSD</a>
                </li>
                <li>
                    <a href="#" onclick="showMysql()">数据库</a>
                </li>
                <li class="dropdown pull-right">
                    <a href="#" data-toggle="dropdown" class="dropdown-toggle" aria-expanded="true">更多<strong class="caret"></strong></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="#">登录</a>
                        </li>
                        <li>
                            <a href="#">个人中心</a>
                        </li>
                        <li>
                            <a href="#">退出</a>
                        </li>
                        <li>
                            <a href="#">关于我们</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
    <div id="myCarousel" class="carousel slide" style="height: 50%">
        <!-- 轮播（Carousel）指标 -->
        <ol class="carousel-indicators">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
            <li data-target="#myCarousel" data-slide-to="2"></li>
        </ol>
        <!-- 轮播（Carousel）项目 -->
        <div class="carousel-inner">
            <div class="item active">
                <img src="images/111.jpg" alt="First slide">
            </div>
            <div class="item">
                <img src="images/222.jpg" alt="Second slide">
            </div>
            <div class="item">
                <img src="images/333.jpg" alt="Third slide">
            </div>
        </div>
        <!-- 轮播（Carousel）导航 -->
        <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>
    <div class="row clearfix">
        <div class="col-md-2 column">
        </div>
        <div class="col-md-6 column">
            <div id="ssdPie" style="height: 400px"></div>
            <div id="mysqlMonitor" style="height: 400px"></div>
        </div>
        <div class="col-md-4 column">
            <div id="alertDismissable">
            </div>
        </div>
    </div>
</div>
<script src="js/highcharts/ssdPie.js"></script>
<script src="js/highcharts/mysql.js"></script>
<script>
    $(document).ready(function(){
        showIndex();
    });
    function showIndex() {
        $(this).addClass("active");
        $("#alertDismissable").hide();
        $("#ssdPie").hide();
        $("#mysqlMonitor").hide();
        $("#myCarousel").show();
    }
</script>
</body>
</html>
