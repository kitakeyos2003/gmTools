<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> 
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>GM公告管理</title>
<link href="../css/jquery-ui-1.8.9.custom.css" media="screen" rel="stylesheet" type="text/css" /> 
<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui-1.8.9.custom.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="../js/gm.js"></script>
</head>
<body>
<p>公告管理</p>
<s:form action="addNotice" method="post" id="formID">
<div id="add">
选择分区：<select id="serverID" name="serverID">
		<s:iterator value="serverList">
			<option value="<s:property value="serverID"/>"><s:property value="serverName"/></option> 
		</s:iterator>
	</select><br/><br/>

时间：&nbsp;&nbsp; 开始:<input type="text" id="startTime" name="startTime" /> &nbsp;至&nbsp;
                        <input type="text" id="endTime" name="endTime" /><br/><br/>

标题：<input type="text" id="title" name="title"  /><br/><br/>

内容：<textarea name="content" id="content" cols="80" rows="8"></textarea><br/><br/>

时间间隔：<select id="intervaltime" name="intervaltime">
	<option value="30000">30秒</option>
	<option value="60000">1分钟</option>
	<option value="180000">3分钟</option>
	<option value="300000">5分钟</option>
</select>&nbsp;&nbsp;&nbsp;&nbsp;

公告次数:<select id="times" name="times">
	<option value="1">1次</option>
	<option value="2">2次</option>
	<option value="3">3次</option>
	<option value="5">5次</option>
</select>
<br/><br/>
</div>
<div>
<input name="add" type="button" value="发布" onclick="addNotice()"/>
</s:form>
<script type="text/javascript">
//$(document).ready(function(){
	//$('#endTime ').datepick ({dateFormat : 'yyyy-mm-dd'});	
//});
$("#startTime").datetimepicker({
	showSecond: true,
	dateFormat: 'yy-mm-dd',
	timeFormat: 'hh:mm:ss',
	stepHour: 1,
	stepMinute: 5,
	stepSecond: 10
});
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