<%--
  User: james.xu
  Date: 2018/1/18
  Time: 14:22
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div>
    <button onclick="addNE()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加网元</button>
    <button onclick="addNode()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加节点</button>
</div>
<%--容器放好--%>
<ul class="easyui-tree" type="text" name="tt" id="tt" style="width:100%; height:34px; margin-left:15px;"></ul>
<%--通过js代码来渲染容器--%>
<script>
    //点击添加网元按钮动作
    function addNE() {
        snmp.addTabs('添加网元', 'ne_add');
    }
    //点击添加节点按钮动作
    function addNode() {
        snmp.addTabs('添加节点', 'node_add');
    }
    /*
    $('#tt').tree({
        //检索远程数据的URL地址
        url: 'treeNode',
        required: true,
        multiple: true,
        //检索数据的HTTP方法，默认是POST请求
        method: 'get',
        //在点击页面上节点左边的三角形
        onBeforeExpand: function (node) {
            //获取当前树
            //获取当前树控件的属性
            var options = $('#tt').tree('options');
            //修改控件属性url变成新的nodeid
            options.url = 'treeNetElement?belongGroup=' + node.text;
        }
    });
    */
</script>