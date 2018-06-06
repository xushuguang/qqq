<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div class="easyui-panel" title="添加节点" data-options="fit:true">
    <form class="nodeForm" id="nodeAddForm" name="nodeAddForm" method="post">
        <table style="width:600px;">
            <tr>
                <td class="label">node name(节点名)：</td>
                <td>
                    <input class="easyui-validatebox" type="text" id="name" name="name"
                           data-options="required:true" style="width:100%">
                </td>
            </tr>
            <tr>
                <td class="label">node ip(节点ip)：</td>
                <td>
                    <input class="easyui-validatebox" type="text" id="nodeIp" name="nodeIp"
                           data-options="required:true,validType:'IP'" style="width:100%">
                </td>
            </tr>
            <tr>
                <td class="label">add NE(添加设备)：</td>
                <td>
                    <input id="ids" name="ids"  prompt="请选择设备" data-options="required:true" style="width: 100%">
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
    //添加设备下拉列表动态生成
    $('#ids').combogrid({
        panelWidth:450,
        multiple:true,
        mode:'remote',
        idField:'id',
        textField:'neName',
        url:'listNetElement',
        method:'get',
        columns:[[
            {field: 'ck', checkbox: true,width:25},
            {field:'neName',title:'neName',width:100},
            {field:'neIp',title:'neIp',width:200},
            {field:'type',title:'type',width:50},
            {field:'state',title:'state',width:50,formatter:function (params) {
                var res = "";
                if(params==0){
                    res +="<div style='width: 10px;height: 10px;background-color: red ;border-radius: 50%;'></div>";
                }else if(params==1){
                    res +="<div style='width: 10px;height: 10px;background-color: yellow   ;border-radius: 50%;'></div>";
                }else if(params==2){
                    res +="<div style='width: 10px;height: 10px;background-color: green ;border-radius: 50%;'></div>";
                }
                return res;
            }}
        ]]
    });
    //表单提交动作
    function submitForm() {
        console.log($('#ids').val())
        if ($('#name').validatebox('isValid')&&$('#nodeIp').validatebox('isValid')) {
            $('#nodeAddForm').form('submit', {
                //表单提交后交给谁处理
                url: 'addNode',
                //表单提交之前被触发，如果返回false终止提交
                onSubmit: function () {},
                //表单提交成功后触发
                success: function (data) {
                    data = JSON.parse(data);
                    if (data.success) {
                        $.messager.alert('消息', data.message, 'info');
                        snmp.closeTabs('添加节点');
                        snmp.addTabs("节点管理", "node_manage");
                    } else {
                        $.messager.alert('警告', data.message, 'warning');
                    }
                }
            });
        }else {
            $.messager.alert('警告', '信息输入不符合要求！', 'warning');
        }
    }
    //表单重置动作
    function clearForm() {
        $('#nodeAddForm').form('clear');
    }
</script>

