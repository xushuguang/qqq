<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div class="easyui-panel" title="添加用户组" data-options="fit:true">
    <form class="userGrupForm" id="userGrupAddForm" name="userGrupAddForm" method="post">
        <table style="width:100%;">
            <tr>
                <td class="label">用户组名称：</td>
                <td>
                    <input class="easyui-textbox" type="text" id="groupName" name="groupName"
                           data-options="required:true" style="width:100%">
                </td>
            </tr>
            <tr>
                <td class="label">选择域：</td>
                <td>
                    <input class="easyui-textbox" type="text" id="choseRegion" name="choseRegion"
                           data-options="required:true" style="width:100%">
                </td>
            </tr>
            <tr>
                <td class="label">页面操作权限：</td>
                <td>
                    <ul class="easyui-tree" type="text" name="mid" id="mid" style="width:100%; height:34px; margin-left:15px;">
                    </ul>
                </td>
            </tr>
            <tr>
                <td class="label">描述：</td>
                <td>
                    <input class="easyui-textbox" type="text" id="discription" name="discription"
                           data-options="required:true" style="width:100%">
                </td>
            </tr>
            <tr>
                <td colspan="2">
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
    var ids ;
    //加载页面操作权限功能
    $('#mid').combotree({
        //检索远程数据的URL地址
        url: 'menu?parentid=0',
        required: true,
        multiple: true,
        //检索数据的HTTP方法，默认是POST请求
        method: 'get',

        //在点击页面上节点左边的三角形
        onBeforeExpand: function (node) {
            //获取当前树
            //获取当前树控件的属性
            var options = $('#mid').combotree('tree').tree('options');
            //修改控件属性url变成新的nodeid
            options.url = 'menu?parentid=' + node.id;
        },
        onCheck:function () {
            ids = [];
            var nodes = $('#mid').combotree('tree').tree('getChecked');
            for (var i = 0; i < nodes.length; i++) {
                ids.push(nodes[i].id);
            };
        }
    });
    //表单提交动作
    function submitForm() {
        $('#userGrupAddForm').form('submit', {
            //表单提交后交给谁处理
            url: 'userGroup',
            //表单提交之前被触发，如果返回false终止提交
            onSubmit: function (param) {
                param.ids = ids;
            },
            //表单提交成功后触发，而非item处理成功后触发
            success: function (data) {
                data = JSON.parse(data);
                if (data.success) {
                    $.messager.alert('消息', data.message, 'info');
                    $('#userGrupAddForm').form('clear');
                }else {
                    $.messager.alert('警告', data.message, 'warning');
                    $('#userGrupAddForm').form('clear');
                }
            }
        });
    }
</script>

