<%--
  User: james.xu
  Date: 2018/1/18
  Time: 14:22
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div id="neListToolbar">
    <button onclick="addNE()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加网元</button>
    <button onclick="removeNE()" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除网元</button>
    <button onclick="editNE()" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑网元</button>
</div>
<%--容器放好--%>
<table id="dgNEs"></table>
<%--通过js代码来渲染容器--%>
<script>
    //点击添加网元按钮动作
    function addNE() {
        snmp.addTabs('添加网元', 'ne_add');
    }
    function removeNE() {
        //取到选中的记录集合
        var rows = $('#dgNEs').datagrid('getSelections');
        if (rows.length == 0) {
            $.messager.alert('警告', '请选中至少一个网元！', 'warning');
            return;
        }
        $.messager.confirm('确认', '您确定要删除所选网元吗？', function (r) {
            if (r) {
                //已经点击“确定”按钮
                //定义一个空的数组，用来存放ID的集合
                var ids = [];
                //遍历的是客户选中的记录集合
                for (var i = 0; i < rows.length; i++) {
                    ids.push(rows[i].id);
                }
                //发出ajax请求
                //$.ajax() $.post() $.get()
                $.post(
                    //url，提交给后台谁去处理
                    'netElement/remove',
                    //data，提交什么到后台，ids
                    {'ids[]': ids},
                    //callback,相当于$.ajax中success
                    function (data) {
                        if (data > 0) {
                            $('#dgNEs').datagrid('reload');
                        }
                    }
                );
            }
        });
    }
    //点击修改网元按钮动作
    function editNE() {
        //取到选中的记录
        var netElement = $('#dgNEs').datagrid('getSelected');
        if (netElement==null) {
            $.messager.alert('警告', '请选择一个网元！', 'warning');
            return;
        }else {
            $.post(
                //url，提交给后台谁去处理
                'netElement/edit',
                //data，提交什么到后台，ids
                {'netElement': netElement},
                //callback,相当于$.ajax中success
                function (data) {
                }
            );
        }
    }
    //初始化数据表格代码
    $('#dgNEs').datagrid({
        //数据表格的标题
        title: '网元主列表',
        // 显示行号
        rownumbers: false,
        //添加工具栏
        toolbar: '#neListToolbar',
        //请求服务器端数据
        url: 'listNetElement',
        //请求方式，默认是POST
        method: 'get',
        //是否显示分页工具栏
        pagination: false,
        //自适应父容器
        fit: true,
        //列属性
        columns: [[
            {field: 'ck', checkbox: true},
            {field: 'neName', title: '网元名'},
            {field: 'neIp', title: '网元ip'},
            {field: 'type', title: '网元类型'},
            {field: 'state', title: '网元状态'}
        ]]
    });
</script>