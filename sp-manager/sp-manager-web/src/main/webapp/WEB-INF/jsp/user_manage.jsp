<%--
  User: james.xu
  Date: 2018/1/18
  Time: 14:22
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div id="alarmListToolbar"></div>
<%--容器放好--%>
<table id=＂dgUser"></table>
<%--通过js代码来渲染容器--%>
<script>
    //初始化数据表格代码
    $('#dgAlarms').datagrid({
        //数据表格的标题
        title: '用户管理',
        // 显示行号
        rownumbers: false,
        //添加工具栏
        toolbar: '#userListToolbar',
        //初始化页面数据条数
        pageSize: 10,
        //在设置分页属性的时候 初始化页面大小选择列表
        pageList: [10, 20, 50],
        //请求服务器端数据
        url: 'listUser',
        //请求方式，默认是POST
        method: 'get',
        //是否显示分页工具栏
        pagination: false,
        //自适应父容器
        fit: true,
        //列属性
        columns: [[
            {field: 'username', title: '用户名'},
            {field: 'userGroup', title: '所属组'}
        ]]
    });
</script>