<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div class="easyui-panel" title="统计数据查询" data-options="fit:true">
    <form class="dataForm" id="dataForm" name="dataForm" method="post">
        <table style="width:100%;">
            <tr>
                <td class="label">起始日期：</td>
                <td>
                    <input class="easyui-datetimebox" name="start_date"
                           data-options="required:true,showSeconds:false" value="3/4/2010 2:3" style="width:150px">
                </td>
                <td class="label">结束日期：</td>
                <td>
                    <input class="easyui-datetimebox" name="start_date"
                           data-options="required:true,showSeconds:false" value="3/4/2010 2:3" style="width:150px">
                </td>
            </tr>
            <tr>
                <td class="label">设备类型：</td>
                <td>
                    <input class="easyui-textbox" type="text" id="title" name="title"
                           data-options="required:true" style="width:100%">
                </td>
            </tr>
            <tr>
                <td class="label">网元名：</td>
                <td>
                    <input id="gid" name="cid" style="width:200px;">
                </td>
            </tr>
            <tr>
                <td class="label">统计粒度：</td>
                <td>
                    <input class="easyui-textbox" type="text" id="user_psd" name="title"
                           data-options="required:true" style="width:100%">
                </td>
            </tr>
            <tr align="center">
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

</script>

