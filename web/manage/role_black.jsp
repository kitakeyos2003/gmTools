<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> 
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<%--<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
--%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>冻结/解冻玩家</title>

<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui-1.8.9.custom.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="../js/gm.js"></script>
<link href="../css/jquery-ui-1.8.9.custom.css" media="screen" rel="stylesheet" type="text/css" /> 
</head>
<%--<sx:head/>
--%><body>
<h1 class="datepick-ctrl">冻结角色</h1>
<div id="blackid">
	<span>选择服务器：<select id="serverID" name="serverID">
		<s:iterator value="serverList">
			<option value="<s:property value="serverID"/>"><s:property value="serverName"/></option> 
		</s:iterator>
	</select></span><br/><br/>
    <span>账号: <select id="type" name="type">
    	<option value="1">角色ID</option>
        <option value="2" selected>角色名称</option>
    </select>&nbsp;&nbsp;<input type="text" name="content" id="content"/> </span><br /><br/>
    <span>冻结日期：<select id="keepTime" name="keepTime">
   		<option value="1">1天</option>
   		<option value="2">2天</option>
   		<option value="3">3天</option>
   		<option value="4">4天</option>
   		<option value="5">5天</option>
   		<option value="6">6天</option>
   		<option value="7">7天</option>
   		<option value="8">8天</option>
   		<option value="9">9天</option>
   		<option value="10">10天</option>
    </select>
    </span>
    <span>至 <input type="text" id="endTime" name="endTime" /> </span> <br/><br/>
    <span> 原因： <textarea id="memo" name="memo"></textarea> </span><br/><br/>
    <input type="button" value="提交" id="but" onclick="black()"/>  &nbsp;&nbsp; <input type="button" value="取消" id="cancelbut" />
    <br/>
    <span id="balid" style="color:red"></span>
</div>
<br /><br />
<h1 class="datepick-ctrl">解冻角色</h1>
<div id="delblackid">
	<span>选择服务器：<select id="delserverID" name="delserverID">
	<s:iterator value="serverList">
			<option value="<s:property value="serverID"/>"><s:property value="serverName"/></option> 
		</s:iterator>
	</select></span><br/>
	<br/>
    <span>账号: <select id="deltype" name="deltype">
    	<option value="1">角色ID</option>
        <option value="2" selected>角色名称</option>
    </select>&nbsp;&nbsp;<input type="text" name="delcontent" id="delcontent"/> </span><br /><br/>
    <span> 原因： <textarea id="delmemo" name="delmemo"></textarea> </span><br/><br/>
    <input type="button" value="提交" id="dbut" onclick="delblack()"/>  &nbsp;&nbsp; <input type="button" value="取消" id="canceldbut" />
    <br/>
    <span id="delid" style="color:red"></span>
</div>
<script type="text/javascript">
$("#endTime").datetimepicker({
	showSecond: true,
	dateFormat: 'yy-mm-dd',
	timeFormat: 'hh:mm:ss',
	stepHour: 1,
	stepMinute: 5,
	stepSecond: 10
});
</script>
</body>
</html>