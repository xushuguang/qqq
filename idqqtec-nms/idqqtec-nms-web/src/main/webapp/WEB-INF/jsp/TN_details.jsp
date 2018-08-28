<%--
  User: james.xu
  Date: 2018/3/12
  Time: 9:23
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
          pageEncoding="UTF-8" %>
<style>
    #TNDetails{
        position: absolute;
        width: 100%;
        height: 45%;
        display: flex;
        justify-content: space-around;
        flex-wrap: wrap;
        left:0;
        top:10%;
    }
    #TNRelation{
        position: absolute;
        width: 100%;
        height: 45%;
        display: flex;
        justify-content: space-around;
        flex-wrap: wrap;
        left:0%;
        top:55%;
    }
</style>
<div id="TNDetails"></div>
<div id="TNRelation"></div>
<script>
    var neName = sessionStorage.getItem("neName");
</script>
<script>
    $(document).ready(function(){
        getTNDetails();
        getTNRelation();
    });
    function getTNDetails() {
        //设备详情数据表格
        $.ajax({
            async : true,//设置异、同步加载
            cache : false,//false就不会从浏览器缓存中加载请求信息了
            type : 'post',
            data:{'neName':neName},
            dataType : "json",
            url : 'getNEChildren',
            success : function(data) { //请求成功后处理函数。
                //var res = "<table bgcolor='#a9a9a9'><caption align='top'>对端TN设备</caption><tr><td width='100px'>对端TN名</td><td width='150px'>对端TNIP</td><td width='100px'>对端TN状态</td><td width='50px'>操作</td></tr>";
                var res = "";
                for (var i =0;i<data.length;i++){
                    res+="<div style='border-radius: 10px;width:15%;height:45%;background-color: cadetblue;color: white'><table style='width: 100%;height: 100%'><tr><td style='width: 40%;height: 25%;'>设备名：</td><td style='width: 60%;height: 25%'>"+data[i].neName+"</td><tr><tr><td>设备IP：</td><td>"+data[i].neIp+"</td></tr><tr><td>设备状态：</td>";
                    //res+="<tr><td style='width: 40%;height: 25%;'>设备名：</td><td style='width: 60%;height: 25%'>"+data[i].neName+"</td></tr><tr><td>设备IP：</td><td>"+data[i].neIp+"</td></tr>"
                    if(data[i].state==0){
                        res +="<td><div style='width: 15px;height: 15px;background-color: red ;border-radius: 50%;'></div></td></tr>";
                    }else if(data[i].state==1){
                        res +="<td><div style='width: 15px;height: 15px;background-color: yellow   ;border-radius: 50%;'></div></td></tr>";
                    }else if(data[i].state==2){
                        res +="<td><div style='width: 15px;height: 15px;background-color: green ;border-radius: 50%;'></div></td></tr>";
                    }
                    res += "<tr><td>操作：</td><td><button style='border-radius: 5px' onclick='neDetails("+data[i].id+")'>keyRate</button></td></tr></table></div>";
                }
                //res += '</table></div>';
                $('#TNDetails').html(res);
            },
            error : function() {//请求失败处理函数
                $.messager.alert('警告', '请求失败！', 'warning');
            }
        });
    }
    function getTNRelation() {
        //TN关系数据表格
        $.ajax({
            async : true,//设置异、同步加载
            cache : false,//false就不会从浏览器缓存中加载请求信息了
            type : 'post',
            data:{'neName':neName},
            dataType : "json",
            url : 'getTNRelation',
            success : function(data) { //请求成功后处理函数。
                var res="";
//                var res = "<table bgcolor='#a9a9a9'><caption align='top'>对端设备</caption><tr><td width='100px'>设备名</td><td width='150px'>设备IP</td><td width='100px'>设备状态</td><td width='150px'>操作</td></tr>";
                for (var i =0;i<data.length;i++){
                    res+="<div style='border-radius: 10px;width:15%;height:45%;background-color: cadetblue;color: white'><table style='width: 100%;height: 100%'><tr><td>"+data[i].neName+"</td><td>"+data[i].neIp+"</td><td>";
                    //res += "<tr><td>"+data[i].neName+"</td><td>"+data[i].neIp+"</td><td>";
                    if(data[i].state==0){
                        res +="<tr><td><div style='width: 15px;height: 15px;background-color: red ;border-radius: 50%;'></div></td></tr>";
                    }else if(data[i].state==1){
                        res +="<tr><td><div style='width: 15px;height: 15px;background-color: yellow   ;border-radius: 50%;'></div></td></tr>";
                    }else if(data[i].state==2){
                        res +="<tr><td><div style='width: 15px;height: 15px;background-color: green ;border-radius: 50%;'></div></td></tr>";
                    }
                    if(data[i].neName.indexOf("TN")==0){
                        res += "<tr><td><button onclick='tnDetails("+data[i].id+")'>keybuffer详情</button></td></tr></table></div>";
                    }else if(data[i].neName.indexOf("QTN")==0){
                        res += "<tr><td><button onclick='tnDetails("+data[i].id+")'>keybuffer</button><button onclick='qncRate("+data[i].id+")'>qncRate</button></td></tr></table></div>";
                    }
                }
//                res += '</table>';
                $('#TNRelation').html(res);
            },
            error : function() {//请求失败处理函数
                $.messager.alert('警告', '请求失败！', 'warning');
            }
        });
    }
    function getQTNRelation() {
        var res = "<button onclick='qncRate()'>详情</button>"
        $('#TNRelation').html(res);
    }
    //点击详情事件
    function neDetails(id) {
        sessionStorage.setItem("id",id);
        snmp.closeTabs("QKD详情");
        snmp.addTabs("QKD详情","QKD_details");
    }
    //点击详情事件
    function tnDetails(pairId) {
        sessionStorage.setItem("pairId",pairId);
        sessionStorage.setItem("neName",neName);
        snmp.closeTabs("KeyBuffer详情");
        snmp.closeTabs("QncRate详情");
        snmp.addTabs("KeyBuffer详情","keybuffer");
    }
    function qncRate(pairId) {
        sessionStorage.setItem("pairId",pairId);
        sessionStorage.setItem("neName",neName);
        snmp.closeTabs("KeyBuffer详情");
        snmp.closeTabs("QncRate详情");
        snmp.addTabs("QncRate详情","qncRate");
    }
</script>

