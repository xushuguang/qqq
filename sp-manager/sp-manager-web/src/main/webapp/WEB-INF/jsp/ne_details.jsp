<%--
  User: james.xu
  Date: 2018/3/12
  Time: 9:23
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
          pageEncoding="UTF-8" %>
<table id="pgDetails" style="width:300px"></table>
<script>
    var id = sessionStorage.getItem("id");
    $('#pgDetails').propertygrid({
        url: 'neDetails',
        showGroup: true,
        scrollbarSize: 0
    });
</script>

