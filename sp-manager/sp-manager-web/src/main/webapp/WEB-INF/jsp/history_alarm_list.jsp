<%--
  User: james.xu
  Date: 2018/1/18
  Time: 14:22
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%--容器放好--%>
<div id="historyAlarmListToolbar">
    <form id="historyAlarmForm">
    <div style="padding: 5px; background-color: #fff;">
        <label>告警网元：</label>
        <input class="easyui-textbox" type="text" id="qkdIp">
        &nbsp;&nbsp;
        <label>告警级别：</label>
        <select id="alarmSeverity" class="easyui-combobox">
            <option value="" disabled selected>请选择</option>
            <option value="Fatal">Fatal</option>
            <option value="Error">Error</option>
            <option value="Warning">Warning</option>
        </select>
        &nbsp;&nbsp;
        <label>告警时间：</label>
        从：<input type="text" id="time1" style="width: 195px" editable="false"
               class="easyui-datetimebox"/>
        到：<input type="text" id="time2" style="width: 195px" editable="false"
                 class="easyui-datetimebox"/>
        &nbsp;&nbsp;
        <button onclick="searchForm()" type="button" class="easyui-linkbutton">搜索</button>
        <button onclick="clearForm()" type="button" class="easyui-linkbutton">重置</button>
        <button onclick="up()" type="button" class="easyui-linkbutton">确定处理</button>
    </div>
    </form>
</div>
<table id="dgHistoryAlarms"></table>
<%--通过js代码来渲染容器--%>
<script>
    //点击搜索按钮动作
    function searchForm() {
        $('#dgHistoryAlarms').datagrid('load',{
            qkdIp:$('#qkdIp').val(),
            alarmSeverity:$('#alarmSeverity').combobox('getValue'),
            time1:$("#time1").datetimebox("getValue"),
            time2:$('#time2').datetimebox("getValue")
        });
    }
    //点击重置按钮动作
    function clearForm() {
        $('#historyAlarmForm').form('clear');
        searchForm();
    }
    //点击确定处理按钮动作
    function up() {
        //取到选中的记录集合
        var rows = $('#dgHistoryAlarms').datagrid('getSelections');
        if (rows.length == 0) {
            $.messager.alert('警告', '请选中至少一条告警！', 'warning');
            return;
        }
        $.messager.confirm('确认', '您确定已经处理所选告警吗？', function (r) {
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
                    'historyAlarms/up',
                    //data，提交什么到后台，ids
                    {'ids[]': ids},
                    //callback,相当于$.ajax中success
                    function (data) {
                        if (data > 0) {
                            $('#dgHistoryAlarms').datagrid('reload');
                        }
                    }
                );
            }
        });
    }
    //初始化数据表格代码
    $('#dgHistoryAlarms').datagrid({
        //数据表格的标题
        title: '历史告警结果查询',
        //显示行号
        rownumbers: true,
        //添加工具栏
        toolbar: '#historyAlarmListToolbar',
        //初始化页面数据条数
        pageSize: 20,
        //在设置分页属性的时候 初始化页面大小选择列表
        pageList: [20, 50, 100],
        //请求服务器端数据
        url: 'listHistoryAlarms',
        //请求方式，默认是POST
        method: 'get',
        //是否显示分页工具栏
        pagination: true,
        //自适应父容器
        fit: true,
        //行样式
        rowStyler:function (index, row) {
            if(row.alarmSeverity=='Fatal'){
                return 'background-color:red;';
            }else if(row.alarmSeverity=='Error'){
                return 'background-color:orangered;';
            }else if(row.alarmSeverity=='Warning'){
                return 'background-color:orange;';
            }else if(row.alarmSeverity=='Info'){
                return 'background-color:darkgrey;';
            }
        },
        //列属性
        columns: [[
            {field: 'ck', checkbox: true},
            {field: 'id', title: '编号', sortable: true, hidden:true},
            {field: 'alarmSeverity', title: '告警级别'},
            {field: 'alarmTime', title: '发生时间',formatter: function (value, rows, index) {
                return moment(value).format('YYYY-MM-DD HH:mm:ss:m');;
            }},
            {field: 'alarmAck', title: '确认标志'},
            {field: 'alarmType', title: '告警原因'},
            {field: 'qkdIp', title: '告警网元'}
        ]]
    });
</script>