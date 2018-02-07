<%--
  User: james.xu
  Date: 2018/1/18
  Time: 14:22
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div id="alarmListToolbar">
    <button onclick="add()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加网元</button>
</div>
<%--容器放好--%>
<table id="dgNetElements"></table>
<%--通过js代码来渲染容器--%>
<script>
    function add() {
        snmp.addTabs('添加网元', 'ne_add');
    }
    /*
    //点击搜索按钮动作
    function searchForm() {
        $('#dgItems').datagrid('load',{
            title:$('#title').val(),
            status:$('#status').combobox('getValue')
        });
    }
    //各个按钮的JS动作
    function add() {
        ttshop.addTabs('新增商品', 'item-add');
    }

    function edit() {
        console.log('edit');
    }

    function down() {
        console.log('down');
    }

    function up() {
        console.log('up');
    }
    function remove() {
        //debugger; //尤其可以使用这种嵌套的页面
        //取到客户选中的记录集合
        var rows = $('#dgItems').datagrid('getSelections');
//            console.log(rows);
        if (rows.length == 0) {
            $.messager.alert('警告', '请选中至少一条记录！', 'warning');
            return;
        }
        $.messager.confirm('确认', '您确定要删除记录吗？', function (r) {
            if (r) {
                //客户已经点击“确定”按钮
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
                    'items/batch',
                    //data，提交什么到后台，ids
                    {'ids[]': ids},
                    //callback,相当于$.ajax中success
                    function (data) {
                        if (data > 0) {
                            $('#dgItems').datagrid('reload');
                        }
                    }
                );
            }

        });
    }
    //初始化数据表格代码
    $('#dgNetElements').datagrid({
        //数据表格的标题
        title: '告警主列表',
       // 显示行号
        rownumbers: true,
        //添加工具栏
        toolbar: '#alarmListToolbar',
        //初始化页面数据条数
        pageSize: 20,
        //在设置分页属性的时候 初始化页面大小选择列表
        pageList: [20, 50, 100],
        //请求服务器端数据
        url: 'listRTAlarms',
        //请求方式，默认是POST
        method: 'get',
        //是否显示分页工具栏
        pagination: true,
        //自适应父容器
        fit: true,
        //列属性
        columns: [[
            {field: 'ck', checkbox: true},
            {field: 'alarmType', title: '告警类型'},
            {field: 'alarmSeverity', title: '告警级别'},
            {field: 'alarmTime', title: '发生时间',formatter: function (value, rows, index) {
                return moment(value).format('YYYY-MM-DD HH:mm:ss:m');
            }},
            {field: 'qkdIp', title: '告警网元'}
        ]]
    });
    */
</script>