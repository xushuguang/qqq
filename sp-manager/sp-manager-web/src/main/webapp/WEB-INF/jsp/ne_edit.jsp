<%--
  User: james.xu
  Date: 2018/4/10
  Time: 15:39
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div style="padding:3px 2px;border-bottom:1px solid #ccc">网元详情</div>
<form id="ff"  method="post">
    <input name="id" type="hidden"></input>
    <table>
        <tr>
            <td>网元名:</td>
            <td><input name="neName"  data-options="required:true"></input></td>
        </tr>
        <tr>
            <td>网元ip:</td>
            <td><input name="neIp"  data-options="required:true"></input></td>
        </tr>
        <tr>
            <td>网元类型:</td>
            <td><input id="ids" name="type"  data-options="required:true"></input></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="保存"></input></td>
        </tr>
    </table>
</form>
<script>
    var netElement = sessionStorage.getItem("netElement");
</script>
