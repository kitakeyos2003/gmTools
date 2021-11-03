<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> 
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>踢所选服务器所有玩家下线</title>
<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../js/gm.js"></script>
</head>

<body>
<h1 class="datepick-ctrl">踢所选服务器所有玩家下线</h1>
<div>
  <s:form action="resetPlayers" method="post" id="formID">
	<span>选择服务器：<select id="serverID" name="serverID">
		<s:iterator value="serverList">
			<option value="<s:property value="serverID"/>"><s:property value="serverName"/></option> 
		</s:iterator>
	</select></span><br/><br/>
    <input type="submit" value="提交" /> 
    <br/>
    </s:form>
</div>
</body>
</html>