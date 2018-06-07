<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div class="easyui-panel" title="添加网元" data-options="fit:true">
    <form class="neAddForm" id="neAddForm" name="neAddForm" method="post">
        <table style="width:600px;">
            <tr>
                <td class="label">网元名：</td>
                <td>
                    <input class="easyui-validatebox" type="text" id="neName" name="neName"
                           data-options="required:true" style="width:100%">
                </td>
            </tr>
            <tr>
                <td class="label">网元ip：</td>
                <td>
                    <input class="easyui-validatebox" type="text" id="neIp" name="neIp"
                           data-options="required:true,validType:'IP'" style="width:100%">
                </td>
            </tr>
            <tr>
                <td class="label">网元类型：</td>
                <td>
                    <select id="type" class="easyui-combobox" name="type" data-options="required:true" style="width:200px;">
                        <option value="TN">TN</option>
                        <option value="QNC">QNC</option>
                        <option value="QKD">QKD</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <button onclick="submitForm()" class="easyui-linkbutton" type="button"
                            data-options="iconCls:'icon-ok'">保存
                    </button>
                    <button onclick="clearForm()" class="easyui-linkbutton" type="button"
                            data-options="iconCls:'icon-undo'">重置
                    </button>
                </td>
            </tr>
        </table>
    </form>
</div>
<script>
    //表单提交动作
    function submitForm() {
        if ($('#neName').validatebox('isValid')&&$('#neIp').validatebox('isValid')){
            $('#neAddForm').form('submit', {
                //表单提交后交给谁处理
                url: 'addNetElement',
                //表单提交之前被触发，如果返回false终止提交
                onSubmit: function () {},
                success: function (data) {
                    data = JSON.parse(data);
                    if (data.success) {
                        $.messager.alert('消息', data.message, 'info');
                        snmp.closeTabs('添加网元');
                        snmp.addTabs("网元管理","ne_manage");
                    }else {
                        $.messager.alert('警告', data.message, 'warning');
                    }
                }
            });
        }else {
            $.messager.alert('警告', '信息输入不符合要求！', 'warning');
        }
    }
    //表单重置动作
    function clearForm() {
        $('#neAddForm').form('clear');
    }
</script>

