<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> 
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<%@ include file="inc.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>Danh sách máy chủ</title>
</head> 
<body>
<%--<s:if test="#session.gm.username=='yunliu'">--%>
<div> <span style="color:red">&nbsp;&nbsp; <s:a href="/gmTools/manage/serverList?ajax=jquery">Làm mới</s:a>  </span></div>
<br/><br/>
<div>
<table width="601" border="1" cellpadding="0" cellspacing="0" align="left">
  <tr>
    <th class="header1" scope="col">Tên máy chủ</th>
    <th class="header1" scope="col">Số người online</th>
    <th class="header1" scope="col">Trạng thái</th>
  </tr>
  <s:set name="num" value="0"/>
  <s:iterator value="serverList" id="server">
  <tr>
    <td> <s:property value="serverName"/> </td>
    <td> <s:property value="onLinePlayerNum"/> </td>
    <td> <s:if test="status">Hoạt động</s:if><s:else>Không hoạt động</s:else> </td>
    <s:set name="num" value="#num+#server.onLinePlayerNum"/>
  </tr>
  </s:iterator>
  <tr>
    <td>Tất cả các máy chủ</td>
    <td> <s:property value="#num"/> </td>
    <td>-------</td>
  </tr>
</table>
</div>
<%--</s:if>--%>
<script type="text/javascript">
    parent.startlink(<s:property value="#session.gm.id"/>);
</script>
</body>
</html>