<%--
  User: james.xu
  Date: 2018/1/18
  Time: 14:22
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div>
    <button onclick="start()" class="easyui-linkbutton" data-options="iconCls:'icon-up',plain:true">开始</button>
    <button onclick="stop()" class="easyui-linkbutton" data-options="iconCls:'icon-down',plain:true">停止</button>
    <button onclick="remove()" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</button>
    <button onclick="refresh()" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">刷新</button>
</div>
<%--容器放好--%>
<table id="dgAlarms"></table>

<%--通过js代码来渲染容器--%>
<script>
    //定时刷新
    setTimeout(function () {
        $('#dgAlarms').datagrid('reload');
    },1000);
    //点击搜索按钮动作
    function searchForm() {
        $('#dgAlarms').datagrid('load',{
            qkdIp:$('#qkdIp').val(),
            alarmSeverity:$('#alarmSeverity').combobox('getValue'),
            time1:$("#time1").datetimebox("getValue"),
            time2:$('#time2').datetimebox("getValue")
        });
    }
    //点击重置按钮动作
    function clearForm() {
        $('#alarmForm').form('clear');
        searchForm();
    }
    //开始监控按钮
    function start() {
        $.messager.confirm('确认', '您确定要开始监控吗？', function (r) {
            if (r) {
                //发出ajax请求
                //$.ajax() $.post() $.get()
                $.get(
                    //url，提交给后台谁去处理
                    'startTrap',
                    function () {
                        $('#dgAlarms').datagrid('reload');
                    }
                );
            }
        } );
    }
    //停止监控按钮
    function stop() {
        $.messager.confirm('确认', '您确定要停止监控吗？', function (r) {
            if (r) {
                //发出ajax请求
                //$.ajax() $.post() $.get()
                $.get(
                    //url，提交给后台谁去处理
                    'stopTrap',
                    function () {
                        $('#dgAlarms').datagrid('reload');
                    }
                );
            }
        } );
    }//停止监控按钮
    function remove() {
        $.messager.confirm('确认', '您确定要删除实时监控信息吗？', function (r) {
            if (r) {
                //发出ajax请求
                //$.ajax() $.post() $.get()
                $.get(
                    //url，提交给后台谁去处理
                    'removeRTAlarms',
                    function () {
                        //刷新
                        $('#dgAlarms').datagrid('reload');
                    }
                );
            }
        } );
    }
    //初始化数据表格代码
    $('#dgAlarms').datagrid({
        //数据表格的标题
        title: '告警主列表',
       // 显示行号
        rownumbers: true,
        //添加工具栏
        toolbar: '#alarmListToolbar',
        //请求服务器端数据
        url: 'listRTAlarms',
        //请求方式，默认是POST
        method: 'get',
        //是否显示分页工具栏
        pagination: false,
        //自适应父容器
        fit: true,
        //行样式
        rowStyler:function (index, row) {
            if(row.alarmSeverity=='Fatal'){
                return 'background-color:red;';
            }else if(row.alarmSeverity=='Error'){
                return 'background-color:orange;';
            }else if(row.alarmSeverity=='Warning'){
                return 'background-color:burlywood;';
            }else if(row.alarmSeverity=='Info'){
                return 'background-color:darkgray;';
            }
        },
        //列属性
        columns: [[
            {field: 'alarmType', title: '告警类型'},
            {field: 'alarmSeverity', title: '告警级别'},
            {field: 'alarmTime', title: '发生时间',formatter: function (value, rows, index) {
                return moment(value).format('YYYY-MM-DD HH:mm:ss:m');
            }},
            {field: 'qkdIp', title: '告警网元'}
        ]]
    });
    //实时刷新
    //timerID = setInterval("refresh()",2000);
    function refresh(){
        $('#dgAlarms').datagrid('reload');
    }
</script>