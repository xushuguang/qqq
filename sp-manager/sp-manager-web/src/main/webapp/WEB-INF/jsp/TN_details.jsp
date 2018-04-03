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
        width: 40%;
        height: 40%;
        left:0;
        top:5%;
    }
    #keyBuffer{
        position: absolute;
        width: 90%;
        height: 50%;
        left:0;
        bottom: 0;
        margin: 0 auto;
    }
</style>
<div id="TNDetails"></div>
<div id="keyBuffer"></div>
<script>
    var neName = sessionStorage.getItem("neName");
</script>
<script src="js/keyBuffer.js"></script>
<script>
    //设备详情数据表格
    $.ajax({
        async : true,//设置异、同步加载
        cache : false,//false就不会从浏览器缓存中加载请求信息了
        type : 'post',
        data:{'neName':neName},
        dataType : "json",
        url : 'getNEChildren',
        success : function(data) { //请求成功后处理函数。
            var res = "<table bgcolor='#a9a9a9'><caption align='top'>QKD设备</caption><tr><td width='100px'>设备名</td><td width='150px'>设备IP</td><td width='100px'>设备状态</td><td width='50px'>操作</td></tr>";
            for (var i =0;i<data.length;i++){
                var id = data[i].id;
                res += "<tr><td>"+data[i].neName+"</td><td>"+data[i].neIp+"</td><td>";
                if(data[i].state==0){
                    res +="<div style='width: 15px;height: 15px;background-color: red ;border-radius: 50%;'></div></td>";
                }else if(data[i].state==1){
                    res +="<div style='width: 15px;height: 15px;background-color: yellow   ;border-radius: 50%;'></div></td>";
                    res += "<td><button onclick='neDetails("+data[i].id+")'>详情</button></td>";
                }else if(data[i].state==2){
                    res +="<div style='width: 15px;height: 15px;background-color: green ;border-radius: 50%;'></div></td>";
                    res += "<td><button onclick='neDetails("+data[i].id+")'>详情</button></td>";
                }
            }
            res += '</table>';
            $('#TNDetails').html(res);
        },
        error : function() {//请求失败处理函数
            $.messager.alert('警告', '请求失败！', 'warning');
        }
    });
    //点击详情事件
    function neDetails(id) {
        sessionStorage.setItem("id",id);
        snmp.closeTabs("QKD详情");
        snmp.addTabs("QKD详情","QKD_details");
    }
</script>

