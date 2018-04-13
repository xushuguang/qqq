<%--
  User: james.xu
  Date: 2018/4/10
  Time: 15:39
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div class="easyui-panel" title="节点详情" data-options="fit:true">
<form id="nodeEditForm" name="nodeEditForm" method="post">
    <input name="id" id="id" type="hidden"></input>
    <table>
        <tr>
            <td>节点名:</td>
            <td><input name="name" id="name" type="text" data-options="required:true"></input></td>
        </tr>
        <tr>
            <td>节点ip:</td>
            <td><input name="nodeIp" id="nodeIp" type="text" data-options="required:true"></input></td>
        </tr>
        <tr>
            <td>拥有设备:</td>
            <td><input id="ids" name="ids"  prompt="请选择设备" data-options="required:true"></input></td>
        </tr>
        <tr>
        <tr>
            <td colspan="2" align="center">
                <button onclick="submitForm()" class="easyui-linkbutton" type="button"
                        data-options="iconCls:'icon-ok'">保存
                </button>
            </td>
        </tr>
        </tr>
    </table>
</form>
</div>
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
    $(document).ready(function() {
        var nodeId = sessionStorage.getItem("nodeId");
        $.post(
            //url，提交给后台谁去处理
            'node/getNodeById',
            //data，提交什么到后台，ids
            {'nodeId': nodeId},
            //callback,相当于$.ajax中success
            function (data) {
                if (data!=null){
                    $('#id').val(data.id);
                    $('#name').val(data.name);
                    $('#nodeIp').val(data.nodeIp);
                    $('#ids').combogrid('setValue',data.ids);
                }
            }
        );
    });
    //表单提交动作
    function submitForm() {
        $('#nodeEditForm').form('submit', {
            //表单提交后交给谁处理
            url: 'node/edit',
            //表单提交之前被触发，如果返回false终止提交
            onSubmit: function () {},
            success: function (data) {
                console.log(data)
                if (data=='true'){
                    $.messager.alert('消息', '修改成功！', 'info');
                    snmp.closeTabs("节点编辑");
                    snmp.addTabs("节点管理","node_manage");
                }else if(data=='false'){
                    $.messager.alert('警告', '修改失败！', 'warning')
                }
            }
        });
    }
    function refresh() {
        
    }
</script>
