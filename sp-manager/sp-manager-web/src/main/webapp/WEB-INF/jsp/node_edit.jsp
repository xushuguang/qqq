<%--
  User: james.xu
  Date: 2018/4/10
  Time: 15:39
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div style="padding:3px 2px;border-bottom:1px solid #ccc">节点详情</div>
<form id="ff" action="form1_proc.php" method="post">
    <input name="id" type="hidden"></input>
    <table>
        <tr>
            <td>节点名:</td>
            <td><input name="name" type="text" data-options="required:true"></input></td>
        </tr>
        <tr>
            <td>节点ip:</td>
            <td><input name="email" type="text" data-options="required:true"></input></td>
        </tr>
        <tr>
            <td>拥有设备:</td>
            <td><input id="ids" name="ids"  prompt="请选择设备" data-options="required:true"></input></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="保存"></input></td>
        </tr>
    </table>
</form>
<script>
    //添加设备下拉列表动态生成
    $('#ids').combogrid({
        panelWidth:450,
        multiple:true,
        mode:'remote',
        idField:'id',
        textField:'neName',
        url:'listNetElement',
        method:'get',
        columns:[[
            {field: 'ck', checkbox: true,width:25},
            {field:'neName',title:'neName',width:100},
            {field:'neIp',title:'neIp',width:200},
            {field:'type',title:'type',width:50},
            {field:'state',title:'state',width:50,formatter:function (params) {
                var res = "";
                if(params==0){
                    res +="<div style='width: 10px;height: 10px;background-color: red ;border-radius: 50%;'></div>";
                }else if(params==1){
                    res +="<div style='width: 10px;height: 10px;background-color: yellow   ;border-radius: 50%;'></div>";
                }else if(params==2){
                    res +="<div style='width: 10px;height: 10px;background-color: green ;border-radius: 50%;'></div>";
                }
                return res;
            }}
        ]]
    });
</script>
