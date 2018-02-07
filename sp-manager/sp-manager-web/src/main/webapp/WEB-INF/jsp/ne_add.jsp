<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div class="easyui-panel" title="添加网元" data-options="fit:true">
    <form class="neForm" id="neAddForm" name="neAddForm" method="post">
        <table style="width:100%;">
            <tr>
                <td class="label">网元名：</td>
                <td>
                    <input class="easyui-textbox" type="text" id="neName" name="neName"
                           data-options="required:true" style="width:100%">
                </td>
            </tr>
            <tr>
                <td class="label">网元IP：</td>
                <td>
                    <input class="easyui-textbox" type="text" id="neIp" name="neIp"
                           data-options="required:true" style="width:100%">
                </td>
            </tr>
            <tr>
                <td class="label">所属QNCIP：</td>
                <td>
                    <input class="easyui-textbox" type="text" id="correspondingQncIp" name="correspondingQncIp" style="width:100%">
                </td>
            </tr>
            <tr>
                <td class="label">对应QKDIP：</td>
                <td>
                    <input class="easyui-textbox" type="text" id="paringQkdIp" name="paringQkdIp" style="width:100%">
                </td>
            </tr>
            <tr>
                <td class="label">所属组：</td>
                <td>
                    <input class="easyui-textbox" type="text" id="belongGroup" name="belongGroup"
                           data-options="required:true" style="width:100%">
                </td>
            </tr>
            <tr>
                <td colspan="2">
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
        $('#neAddForm').form('submit', {
            //表单提交后交给谁处理
            url: 'netElementAdd',
            //表单提交之前被触发，如果返回false终止提交
            onSubmit: function () {},
            success: function (data) {
                data = JSON.parse(data);
                if (data.success) {
                    $.messager.alert('消息', data.message, 'info');
                    snmp.closeTabs('添加网元');
                }
            }
        });
    }
</script>

