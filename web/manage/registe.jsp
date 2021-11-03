<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> 
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>添加GM</title>
<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../js/gm.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#addGmButton").click(addgm);
})
</script>
</head>
<s:head theme="xhtml"/>
<body>
<%--<s:if test="#session.gm.username=='yunliu'">--%>
     <input type="hidden" name="admin" value="xjyoyo1706">
<DIV id=u2_rtf><span style=" font-family:'\'cb\'ce\'cc\'e5'; color:#000000; font-size:13px;">1：生成账号</span></DIV>
<div id="formid">

	<li>账号名称：<input type="text" id="gmName"/> </li>
	<li>账号密码：<input type="password" id="gmPassword"/> </li>
	<li>确认密码：<input type="password" id="gmPassword2"/> </li>
	<li>小组：<select id="groupID">
		<option value="1">1 组</option>
		<option value="2">2 组</option>
		<option value="3">3 组</option>
		<option value="4">4 组</option>
		<option value="5">5 组</option>
		<option value="6">6 组</option>
		<option value="7">7 组</option>
		<option value="8">8 组</option>
		<option value="9">9 组</option>
		<option value="10">10 组</option>
	</select> </li>
    <li>等级：        <s:select name="level" id="level" list="#{1:'1 级',2:'2 级',3:'3 级',4:'4 级',5:'5 级'}" listKey="key" listValue="value" value="1"></s:select>
 </li>
	<input type="button" id="addGmButton" value="添加"/>
	<span id="addbut1" style="color:red"></span>
</div>
<%--</s:if>--%>
</body>

</html>