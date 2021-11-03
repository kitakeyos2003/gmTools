<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>欢迎使用上海幽幽GM工具</title>
<script type="text/javascript" src="/gmTools/js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="/gmTools/js/gm.js"></script>
<script type="text/javascript">
    startlink(<s:property value="#session.gm.id"/>);
</script>
</head>

	<frameset cols="200,*" frameborder="1" framespacing="1">
		<frame name="topFrame" src="/gmTools/sitetree">
	    <frame name="mainFrame" src="/gmTools/login/logined">
	</frameset>


</html>