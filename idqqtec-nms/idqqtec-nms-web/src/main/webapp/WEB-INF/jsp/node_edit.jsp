<%--
  User: james.xu
  Date: 2018/4/10
  Time: 15:39
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div class="easyui-panel" title="节点详情" data-options="fit:true">
<form id="nodeEditForm" name="nodeEditForm" method="post">
    <input name="id" id="eid" type="hidden"></input>
    <table style="width:600px;">
        <tr>
            <td>节点名:</td>
            <td><input name="name" class="easyui-validatebox" id="ename" type="text" data-options="required:true" style="width:100%"></td>
        </tr>
        <tr>
            <td>节点ip:</td>
            <td><input name="nodeIp" class="easyui-validatebox" id="enodeIp" type="text" data-options="required:true" style="width:100%"></td>
        </tr>
        <tr>
            <td>拥有设备:</td>
            <td><input id="eids" name="ids"  prompt="请选择设备" data-options="required:true" style="width:100%"></td>
        </tr>
        <tr>
        <tr>
            <td colspan="2" align="center">
                <button onclick="esubmitForm()" class="easyui-linkbutton" type="button"
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
    $('#eids').combogrid({
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
                    $('#eid').val(data.id);
                    $('#ename').val(data.name);
                    $('#enodeIp').val(data.nodeIp);
                    $('#eids').combogrid('setValue',data.ids);
                }
            }
        );
    });
    //表单提交动作
    function esubmitForm() {
        $('#nodeEditForm').form('submit', {
            //表单提交后交给谁处理
            url: 'node/edit',
            //表单提交之前被触发，如果返回false终止提交
            onSubmit: function () {},
            success: function (data) {
                data = JSON.parse(data);
                if (data.success) {
                    $.messager.alert('消息', data.message, 'info');
                    snmp.closeTabs("节点编辑");
                    snmp.addTabs("节点管理","node_manage");
                } else {
                    $.messager.alert('警告', data.message, 'warning');
                }
            }
        });
    }
    function refresh() {
        
    }
</script>
