<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> 
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<%@ include file="inc.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>Danh sách quản trị viên</title>
</head>
<body>
<div>
	<table width="600" border="1">
  <tr>
    <th class="header1" scope="col">ID</th>
    <th class="header1" scope="col">Tên</th>
    <th class="header1" scope="col">Trạng thái</th>
  </tr>
  <% 
  	for(gm.entities.GmUser gmuser : gm.services.GmListManager.getInstance().getGmUserList()) {
  		%>
	   	<tr>
		    <td> <%=gmuser.getId() %> </td>
		    <td> <%=gmuser.getUsername() %> </td>
		    <td> <%=gmuser.isOnLine() ? "Trực tuyến" : "Không trực tuyến"%></td>
	  	</tr> 		
  		<%
  	}
  %>
  
</table>

</div>
</body>
</html>