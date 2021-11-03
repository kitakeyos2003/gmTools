<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> 
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>过滤查询玩家</title>
<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../js/gm.js"></script>
</head>
<body>
<div>过滤查询</div>
<div>
选择分区：<select id="serverID" name="serverID">
		<s:iterator value="serverList">
			<option value="<s:property value="serverID"/>"><s:property value="serverName"/></option> 
		</s:iterator>
	</select>&nbsp;&nbsp;
账号类型：<select id="type" name="type">
    	<option value="1">角色ID</option>
        <option value="2" selected>角色名称</option>
    </select><input name="content" id="content" type="text" />&nbsp;
<input name="query" id="query" type="button" value="查询" onclick="queryPlayer()"/>
</div>
<div id="playerinfo"></div>
</body>
</html>