<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> 
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>修改密码</title>
<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../js/gm.js"></script>
</head>

<body>
<div align="center">修改密码....</div>
<div>
	旧&nbsp;&nbsp;密&nbsp;&nbsp;码：<input type="password" name="oldpwd" id="oldpwd" /><br/>
	新&nbsp;&nbsp;密&nbsp;&nbsp;码：<input type="password" name="newpwd" id="newpwd"/><br/>
	确认新密码:<input type="password" name="newpwd2" id="newpwd2"/><br/>
	<input type="button" name="submit" value="修改" onclick="updpwd()"/>
</div>
<div id="msg" style="color:red"></div>
</body>
</html>