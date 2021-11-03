<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>上海幽幽GM工具 GM 登录</title>
<script type="javascript" src="/gmTools/js/jquery-1.5.min.js"></script>
<script type="javascript" src="/gmTools/js/gm.js"></script>
</head>
<body>
<div align="center"> <span style="color:red"> <s:property value="errorMsg" default="您未登录，请登录" />  </span> </div>
<br/>
<div align="center">
 <br/>
<s:form action="/login/enter" method="post">
    <s:token/>
	用户名：<input type="text" id="username" name="username"/><br/>
	密&nbsp;&nbsp;码：<input type="password" id="password" name="password"/><br/>
	<s:submit value="登录"></s:submit>
</s:form>
</div>
<s:if test="#request.logout=='logoutclose'">
<script type="text/javascript">
    canceltimer();
//    opener.location.reload();
</script>
</s:if>
</body>
</html>
