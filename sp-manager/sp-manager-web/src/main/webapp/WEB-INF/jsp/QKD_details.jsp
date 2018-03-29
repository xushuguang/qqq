<%--
  User: james.xu
  Date: 2018/3/12
  Time: 9:23
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
          pageEncoding="UTF-8" %>
<style>
    #tdDetails{
        position: absolute;
        width: 50%;
        left:0;
        top:5%;
    }
    #keyRate{
        position: absolute;
        width: 50%;
        height: 50%;
        right: 0;
        bottom: 0;
        margin: 0 auto;
    }
</style>
<div id="tdDetails">
    <table id="pgDetails"></table>
</div>
<div id="keyRate"></div>
<script src="js/keyRate.js"></script>
<script>
    //设备详情数据表格
    var id = sessionStorage.getItem("id");
    $('#pgDetails').propertygrid({
        url: 'getNEDetails/'+id,
        showGroup: true,
        scrollbarSize: 0
    });
</script>

