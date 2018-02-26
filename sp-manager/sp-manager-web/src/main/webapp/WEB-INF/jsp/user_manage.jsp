<%--
  User: james.xu
  Date: 2018/1/18
  Time: 14:22
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
        //是否显示分页工具栏
        pagination: false,
        //自适应父容器
        fit: true,
        //列属性
        columns: [[
            {field: 'username', title: '用户名'},
            {field: 'userGroup', title: '所属组'},
            {field: '  ', title: '操作'}
        ]]
    });
</script>