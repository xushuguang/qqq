<%--
  User: james.xu
  Date: 2018/1/18
  Time: 14:22
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<style type="text/css">
    /*-- 消除grid屏闪问题 --//*/
    .datagrid-mask{
        opacity:0;
        filter:alpha(opacity=0);
    }
    .datagrid-mask-msg{
        opacity:0;
        filter:alpha(opacity=0);
    }
</style>
<div id="alarmListToolbar">
<form id="alarmForm">
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
        <button onclick="searchForm()" type="button" class="easyui-linkbutton">搜索</button>
        <button onclick="clearForm()" type="button" class="easyui-linkbutton">重置</button>
    </div>
</form>
</div>
<%--容器放好--%>
<table id="dgAlarms"></table>
<%--通过js代码来渲染容器--%>
<script>
    //点击搜索按钮动作
    function searchForm() {
        $('#dgAlarms').datagrid('load',{
            qkdIp:$('#qkdIp').val(),
            alarmSeverity:$('#alarmSeverity').combobox('getValue')
        });
    }
    //点击重置按钮动作
    function clearForm() {
        $('#alarmForm').form('clear');
        searchForm();
    }
    //初始化数据表格代码
    $('#dgAlarms').datagrid({
        //数据表格的标题
        title: '告警主列表',
       // 显示行号
        rownumbers: false,
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
                return 'background-color:orangered;';
            }else if(row.alarmSeverity=='Warning'){
                return 'background-color:orange;';
            }else if(row.alarmSeverity=='Info'){
                return 'background-color:darkgrey;';
            }
        },
        //列属性
        columns: [[
            {field: 'id', title: '告警id'},
            {field: 'alarmType', title: '告警类型'},
            {field: 'alarmSeverity', title: '告警级别'},
            {field: 'alarmTime', title: '发生时间',formatter: function (value, rows, index) {
                return moment(value).format('YYYY-MM-DD HH:mm:ss:m');
            }},
            {field: 'qkdIp', title: '告警网元'}
        ]]
    });
    //实时刷新
    timerID = setInterval("refresh()",1000);
    function refresh(){
        $('#dgAlarms').datagrid('reload');
    }
</script>