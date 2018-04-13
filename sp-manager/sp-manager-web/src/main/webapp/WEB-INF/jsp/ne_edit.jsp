<%--
  User: james.xu
  Date: 2018/4/10
  Time: 15:39
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div class="easyui-panel" title="网元详情" data-options="fit:true">
<form id="neEditForm" name="neEditForm" class="neEditForm" method="post">
    <input name="id"  id="id" type="hidden"></input>
    <table>
        <tr>
            <td>网元名:</td>
            <td><input name="neName"  type="text" id="neName" data-options="required:true"></input></td>
        </tr>
        <tr>
            <td>网元ip:</td>
            <td><input name="neIp"  type="text" id="neIp" data-options="required:true"></input></td>
        </tr>
        <tr>
            <td>网元类型:</td>
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
            </td>
        </tr>
    </table>
</form>
</div>
<script>
    $(document).ready(function() {
        var neId = sessionStorage.getItem("neId");
        $.post(
            //url，提交给后台谁去处理
            'netElement/getNetElementById',
            //data，提交什么到后台，ids
            {'neId': neId},
            //callback,相当于$.ajax中success
            function (data) {
                if (data!=null){
                    $('#id').val(data.id);
                    $('#neName').val(data.neName);
                    $('#neIp').val(data.neIp);
                    $('#type').combobox('select',data.type);
                }
            }
        );
    });
    //表单提交
    //表单提交动作
    function submitForm() {
        $('#neEditForm').form('submit', {
            //表单提交后交给谁处理
            url: 'netElement/edit',
            //表单提交之前被触发，如果返回false终止提交
            onSubmit: function () {},
            success: function (data) {
                if (data>0){
                    $.messager.alert('消息', '修改成功！', 'info');
                    snmp.closeTabs("网元编辑");
                    snmp.addTabs("网元管理","ne_manage");
                }else (
                    $.messager.alert('警告', '修改失败！', 'warning')
                )
            }
        });
    }
</script>
