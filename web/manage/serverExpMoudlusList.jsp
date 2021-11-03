<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> 
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<%@ include file="inc.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>服务器经验系数列表</title>
</head>
<body>
<div>
	<table width="600" border="1">
  <tr>
    <th class="header1" scope="col">服务器</th>
    <th class="header1" scope="col">经验系数</th>
    <th class="header1" scope="col">开始时间</th>
    <th class="header1" scope="col">结束时间</th>
    <th class="header1" scope="col">编辑</th>
  </tr>
  <% 
  	for(gm.entities.GmExpMoudlus gem : gm.services.ServerExpMoudlusService.getExpMoudlusList()) {
  		%>
	   	<tr>
		    <td> <%=gem.getServerId() %> </td>
		    <td> <%=gem.getMoudlus() %> </td>
		    <td> <%=gem.getStartTime() %></td>
		    <td> <%=gem.getEndTime() %></td>
		    <td><a href="/gmTools/manage/updateserverexpmoudlusview?serverid=<%=gem.getServerId() %>&height=600&width=700&modal=true" >修改</a></td>
	  	</tr>
  		<%
  	}
  %>
</table>
</div>
</body>
</html>