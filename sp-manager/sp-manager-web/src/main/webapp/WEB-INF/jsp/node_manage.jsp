<%--
  User: james.xu
  Date: 2018/1/18
  Time: 14:22
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div id="nodeListToolbar">
    <button onclick="addNode()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加节点</button>
    <button onclick="removeNode()" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除节点</button>
    <button onclick="editNode()" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑节点</button>
</div>
<%--容器放好--%>
<table id="dgNodes"></table>
<%--通过js代码来渲染容器--%>
<script>
    //点击添加节点按钮动作
    function addNode() {
        snmp.addTabs('添加节点', 'node_add');
    }
    //点击删除节点按钮动作
    function removeNode() {
        //取到选中的记录集合
        var rows = $('#dgNodes').datagrid('getSelections');
        if (rows.length == 0) {
            $.messager.alert('警告', '请选中至少一个节点！', 'warning');
            return;
        }
        $.messager.confirm('确认', '您确定要删除所选节点吗？', function (r) {
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
                    'node/remove',
                    //data，提交什么到后台，ids
                    {'ids[]': ids},
                    //callback,相当于$.ajax中success
                    function (data) {
                        if (data > 0) {
                            $('#dgNodes').datagrid('reload');
                        }
                    }
                );
            }
        });
    }
    //点击修改节点按钮动作
    function editNode() {
        //取到选中的记录
        var row = $('#dgNodes').datagrid('getSelected');
        console.log(row);
        if (row==null) {
            $.messager.alert('警告', '请选择一个节点！', 'warning');
            return;
        }else {
          snmp.addTabs("编辑节点","node_edit");
        }
    }
    //初始化数据表格代码
    $('#dgNodes').datagrid({
        //数据表格的标题
        title: '节点主列表',
        // 显示行号
        rownumbers: false,
        //添加工具栏
        toolbar: '#nodeListToolbar',
        //请求服务器端数据
        url: 'listNode',
        //请求方式，默认是POST
        method: 'get',
        //是否显示分页工具栏
        pagination: false,
        //自适应父容器
        fit: true,
        //列属性
        columns: [[
            {field: 'ck', checkbox: true},
            {field: 'nodeName', title: '节点名'},
            {field: 'nodeIp', title: '节点ip'},
        ]]
    });
</script>