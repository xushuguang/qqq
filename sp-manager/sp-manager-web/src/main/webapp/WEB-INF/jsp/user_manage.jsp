<%--
  User: james.xu
  Date: 2018/1/18
  Time: 14:22
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div id="userListToolbar">
    <button onclick="deleteForm()" type="button" class="easyui-linkbutton">删除用户</button>
</div>
<%--容器放好--%>
<table id="dgUser"></table>
<%--通过js代码来渲染容器--%>
<script>
    //初始化数据表格代码
    $('#dgUser').datagrid({
        //数据表格的标题
        title: '用户管理',
        //请求服务器端数据
        url: 'listUserVo',
        //请求方式，默认是POST
        method: 'get',
        //添加工具栏
        toolbar: '#userListToolbar',
        //是否显示分页工具栏
        pagination: false,
        //自适应父容器
        fit: true,
        //列属性
        columns: [[
            {field: 'ck', checkbox: true},
            {field: 'username', title: '用户名'},
            {field: 'userGroup', title: '所属组'},
        ]]
    });
    //点击删除用户按钮
    function deleteForm(){
        var row = $('#dgUser').datagrid('getSelected');
        if (row==null) {
            $.messager.alert('警告', '请选择一个用户！', 'warning');
            return;
        }else {
            var username = row.username;
            $.post(
                //url，提交给后台谁去处理
                'user/delByUsername',
                //data，提交什么到后台，ids
                {'username': username},
                //callback,相当于$.ajax中success
                function (flag) {
                    if (flag){
                        $.messager.alert('消息', '用户删除成功！', 'info');
                        $('#dgUser').datagrid('reload');
                    }else {
                        $.messager.alert('警告', '用户删除失败！', 'warning');
                    }
                });
        }
    }
</script>