<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:directive.page import="gm.db.DataService"/> 
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<%@ page import = "gm.services.*" %>
<%@ page import = "gm.entities.*" %>
<%@ page import = "java.util.*" %>
<%@ include file="inc.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../js/gm.js"></script>
<% 
   	int id = Integer.parseInt(request.getParameter("id"));
   	GmLetter letter = DataService.getGmLetterByID(id);
   	List<GmLetter> gmLetterList = DataService.getGmletterList(letter.getServerID(), letter.getSenderRoleId(), 10);
%>
<title>GM回复邮件</title>
</head>
<body>
<table border="1" width="800">
<tr><td>区：<%=letter.getServerID() %></td><td>玩家：<%=letter.getSenderRoleId() %></td></tr>
<% 
	for(GmLetter gmLetter : gmLetterList) {
		if(gmLetter.getId() != letter.getId()) {
		%>
			<tr><td><%=gmLetter.getSenderRoleId() %>说：</td><td>[<%=gmLetter.getTypeName() %>]<%=gmLetter.getContent() %>[<%=gmLetter.getSendTime() %>]</td></tr>
		<%
			if(gmLetter.getReplyGmId() > 0) {
			%>
			<tr><td><%=gmLetter.getReplyGmName() %>回：</td><td><%=gmLetter.getReplyContent() %>[<%=gmLetter.getReplyTime() %>]</td></tr>
			<%
			}
		}
	}
%>
</table>
<s:form action="gmReplyLetter" method="post" id="formID">
<div>
  <p align="left" style="color:red">日期：<%=letter.getSendTime() %>
  问题类型：<%=letter.getTypeName() %>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <%--<input name="closebut" type="button" value="关闭" onclick="closeLetterInfo()"/>--%>
  </p>
</div>
<br/>
<div>
<input type="hidden" id="letterid" name="id" value="<%=letter.getId() %>"/>
<input type="hidden" id="serverID" name="serverID" value="<%=letter.getServerID() %>"/>
GM邮件内容：<textarea name="letter_content" cols="50" rows="8" readonly="readonly"><%=letter.getContent() %></textarea><p></p>
GM答复内容：<textarea id="reply_content_id" name="content" cols="80" rows="8"><%=letter.getReplyContent() %></textarea><p></p>
	<div align="center">
	  <input name="reply" type="button" value="提交" onclick="replyLetter()"/>
	  &nbsp;&nbsp;
	    <input name="cancel" type="button" value="取消" onclick="tb_remove()"/>
    </div>
</div>
</s:form>
</body>
</html>