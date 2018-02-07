<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div class="easyui-panel" title="添加用户" data-options="fit:true">
    <form class="userForm" id="userAddForm" name="userAddForm" method="post">
        <table border="0" cellspacing="0" cellpadding="0" style="width:600px;">
            <tr>
                <td class="label">用户名称：</td>
                <td>
                    <input class="easyui-textbox" type="text" id="username" name="username"
                           data-options="required:true" style="width:100%">
                </td>
            </tr>
            <tr>
                <td class="label">所属组：</td>
                <td>
                    <input class="easyui-textbox" type="text" id="userGroup" name="userGroup"
                           data-options="required:true" style="width:100%">
                </td>
            </tr>
            <tr>
                <td class="label">用户密码：</td>
                <td>
                    <input id="password" name="password" class="easyui-passwordbox" prompt="请输入不少于8位的数字字母组合" iconWidth="28" data-options="required:true" style="width:100%">
                </td>
            </tr>
            <tr>
                <td class="label">确认密码：</td>
                <td>
                    <input id="password1" name="password1" class="easyui-passwordbox" prompt="请输入不少于8位的数字字母组合" iconWidth="28" data-options="required:true" style="width:100%">
                </td>
            </tr>
            <tr>
                <td class="label">密码有效期：</td>
                <td>
                    <input type="text" id="psdValidity" name="psdValidity"  style="width:100%" editable="false" data-options="required:true"
                           class="easyui-datetimebox"/>
                </td>
            </tr>
            <tr>
                <td class="label">备注：</td>
                <td>
                    <input class="easyui-textbox" type="text" id="remark" name="remark" style="width:100%">
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
    //点击保存按钮动作
    function submitForm(){
        $('#userAddForm').form('submit', {
            url:"insertUser",
            onSubmit: function(){
                var psd1 = $("#password").val();
                var psd2 = $("#password1").val();
                if(psd1 != psd2){
                    $.messager.alert('警告', '两次输入密码不一致！', 'warning');
                    return false;
                }
                return true;
                },
            success:function(data){
                data = JSON.parse(data);
                if (data.success) {
                    $.messager.alert('消息', data.message, 'info');
                    $('#userAddForm').form('clear');
                }else {
                    $.messager.alert('警告', data.message, 'warning');
                    $('#userAddForm').form('clear');
                }
            }
        });
    }
    //点击重置按钮动作
    function clearForm() {
        $('#userAddForm').form('clear');
    }

</script>

