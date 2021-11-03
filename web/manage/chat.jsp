<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> 
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>聊天监控</title>
</head>

<body>
<div align="center">聊天监控...</div><p></p>
<div align="center">
	点击服务器名称监控各服务器聊天记录:<br/>
	<s:iterator value="serverList">
	 <p>
	 <a href="/gmTools/manage/showChatContent?serverID=<s:property value="serverID"/>&s=<s:property value="serverID"/>&ajax=jquery" target="_blank"> <s:property value="serverName"/> </a>
	 </p>	
	</s:iterator>
</div>
</body>
</html>