<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div class="easyui-panel" title="添加用户组" data-options="fit:true">
    <form class="userGrupForm" id="userGrupForm" name="userGrupForm" method="post">
        <table style="width:100%;">
            <tr>
                <td class="label">用户组名称：</td>
                <td>
                    <input class="easyui-textbox" type="text" id="title" name="title"
                           data-options="required:true" style="width:100%">
                </td>
            </tr>
            <tr>
                <td class="label">选择所属组：</td>
                <td>
                    <input id="gid" name="cid" style="width:200px;">
                </td>
            </tr>
            <tr>
                <td class="label">选择域：</td>
                <td>
                    <input id="cid" name="cid" style="width:200px;">
                </td>
            </tr>
            <tr>
                <td class="label">页面操作权限：</td>
                <td>
                    <input class="easyui-textbox" type="text" id="sellPoint" name="sellPoint"
                           data-options="validType:'length[0,150]',multiline:true" style="width:100%;height:60px;">
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <!-- 加载编辑器的容器 -->
                    <script id="container" name="content" type="text/plain">描述</script>
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
        <input name="paramData" id="paramData" style="display:none;">
    </form>
</div>

<script>
    //加载商品类目功能
    $('#cid').combotree({
        //检索远程数据的URL地址
        url: 'itemCats?parentId=0',
        required: true,
        //检索数据的HTTP方法，默认是POST请求
        method: 'get',
        //在点击页面上节点左边的三角形
        onBeforeExpand: function (node) {
            //获取当前树
            //获取当前树控件的属性
            var options = $('#cid').combotree('tree').tree('options');
            //修改控件属性url变成新的nodeid
            options.url = 'itemCats?parentId=' + node.id;
        },
        //在点击页面上节点选中条目时
        onBeforeSelect: function (node) {
            //判断是否是叶子节点
            var isLeaf = $('#cid').tree('isLeaf', node.target);
            if (!isLeaf) {
                //能进入这里说明不是叶子节点，要通知用户重新选择
                $.messager.alert('警告', '请选择最终类目！', 'warning');
                return false;
            } else {
                $.get(
                    'itemParam/' + node.id,
                    function (data) {
                        //动态拼接HTML
                        var $outerTd = $('#itemAddForm .paramsShow td').eq(1);
                        var $ul = $('<ul>');
                        $outerTd.empty().append($ul);
                        if (data) {
                            var paramData = data.paramData;
                            paramData = JSON.parse(paramData);
                            //遍历分组
                            $.each(paramData, function (i, e) {
                                var groupName = e.group;
                                var $li = $('<li>');
                                var $table = $('<table>');
                                var $tr = $('<tr>');
                                var $td = $('<td colspan="2" class="group">' + groupName + '</td>');

                                $ul.append($li);
                                $li.append($table);
                                $table.append($tr);
                                $tr.append($td);

                                //遍历分组项
                                if (e.params) {
                                    $.each(e.params, function (_i, paramName) {
                                        var _$tr = $('<tr><td class="param">' + paramName + '</td><td><input></td></tr>');
                                        $table.append(_$tr);
                                    });
                                }
                            });
//                            $outerTd.append('<div id="hellodiv"><input type="button" id="btnSubmit" value="test"/></div>');
                            $("#itemAddForm .paramsShow").show();
                        } else {

                            $("#itemAddForm .paramsShow").hide();
                            $("#itemAddForm .paramsShow td").eq(1).empty();//第二个td
                        }
//                        $('#hellodiv').delegate('#btnSubmit', 'click', function () {
//                            alert("gggggg");
//                        });
//                        $('#hellodiv').on('click', '#btnSubmit',function () {
//                            alert("jjj");
//                        });
                    },
                    'json'
                );
            }


        }
    });


    //初始化富文本编辑器
    var ue = UE.getEditor('container', {
        initialFrameWidth: 1000,
        initialFrameHeight: 300,
        serverUrl: 'file/upload'
    });

    //表单提交动作
    function submitForm() {
        $('#itemAddForm').form('submit', {
            //表单提交后交给谁处理
            url: 'item',
            //表单提交之前被触发，如果返回false终止提交
            onSubmit: function () {
                $('#price').val($('#priceView').val() * 100);

                //获取参数规格部分
                var paramsJson = [];
                var $liList = $('#itemAddForm .paramsShow li');
                $liList.each(function (i, e) {
                    $group = $(e).find('.group');
                    var groupName = $group.text();

                    var params = [];
                    var $trParams = $(e).find('tr').has('td.param');
                    $trParams.each(function (_i, _e) {
                        var $oneDataTr = $(_e);
                        var $keyTd = $oneDataTr.find('.param');
                        var $valueInput = $keyTd.next('td').find('input');
                        var key = $keyTd.text();
                        var value = $valueInput.val();
                        var _o = {
                            k: key,
                            v: value
                        };
                        params.push(_o);
                    });
                    var o = {};
                    o.group = groupName;
                    o.params = params;
                    paramsJson.push(o);
                });
                paramsJson = JSON.stringify(paramsJson);
                $('#paramData').val(paramsJson);
                console.log(paramsJson);
                return $(this).form('validate');
            },
            //表单提交成功后触发，而非item处理成功后触发
            success: function (data) {
                data = JSON.parse(data);
                if (data.success) {
                    $.messager.alert('消息', data.message, 'info');
                    ttshop.addTabs('查询商品', 'item-list');
                }
            }
        });
    }
</script>

