<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <meta name="viewport" content="width=device-width" />
    <title>用户登录</title>
    <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="js/jquery-easyui-1.5/themes/bootstrap/easyui.css" rel="external nofollow" rel="stylesheet">
    <link rel="stylesheet" href="js/jquery-easyui-1.5/themes/icon.css" rel="external nofollow" rel="stylesheet">
    <script src="js/jquery-easyui-1.5/jquery.min.js"></script>
    <script src="js/jquery-easyui-1.5/jquery.easyui.min.js"></script>
    <script src="js/jquery-easyui-1.5/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">
        $(function () {
            initWin(); //初始化登录窗体
        });
        function initWin() {
            $("#win").window({
                title: "用户登录",
                width: 400,
                height: 270,
                collapsible: false,
                minimizable: false,
                maximizable: false,
                closable: false,
                modal: true,
                resizable: false,
            });
        }
    </script>
</head>
<body onkeydown="keyLogin();">
<div id="win"class="easyui-window">
    <div>
        <div style="height:20px"></div>
        <form class="loginForm" id="userLoginForm" name="userLoginForm" method="post">
        <table>
            <tr>
                <td style="width:20px"></td>
                <td>用户名:</td>
                <td><input type="text"class="easyui-textbox" id="useername" name="username" onkeypress="if(event.keyCode==13) focusNextInput(this);"/></td>
                <td><span id="spanName" style="color:red"></span></td>
            </tr>
            <tr style="height:10px"></tr>

            <tr>
                <td style="width:20px"></td>
                <td>密 码:</td>
                <td><input type="password"class="easyui-textbox"id="password" name="password" onkeypress="if(event.keyCode==13) focusNextInput(this);"></td>
                <td><span id="spanPwd" style="color:red"></span></td>
            </tr>
            <tr style="height:10px"></tr>
            <tr style="height:10px"></tr>
        </table>
       </form>
    </div>
    <div style="height:10px"></div>
    <div data-options="region:'south',border:false" style="text-align:center;padding:5px 0 0;">
        <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="login()" rel="external nofollow" rel="external nofollow" id="btnOk" style="width:80px">登录</a>
    </div>
</div>
<script>
    function keyLogin(){
        if (event.keyCode==13)  //回车键的键值为13
            document.getElementById("btnOk").onclick(); //调用登录按钮的登录事件
    }
    function login(){
        $('#userLoginForm').form('submit', {
            url:"loginUser",
            success:function(data){
                data = JSON.parse(data);
                if (data.success) {
                    $.messager.confirm('消息', data.message, function (r) {
                        if(r){
                            window.location.href="index_snmp";
                        }
                    });
                }else {
                    $.messager.alert('警告', data.message, 'warning');
                    $('#userLoginForm').form('clear');
                }
            }
        });
    }
</script>
</body>
</html>

