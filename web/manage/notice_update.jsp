<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> 
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>GM公告修改</title>
<link href="../css/jquery-ui-1.8.9.custom.css" media="screen" rel="stylesheet" type="text/css" /> 
<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui-1.8.9.custom.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="../js/gm.js"></script>
</head>
<body>
<p>公告修改</p>

<s:form action="updNotice" method="post" id="formID">
<div id="add">
    <input type="hidden" name="id" value="<s:property value="notice.id"/>">
选择分区：
    <s:select list="serverList" name="serverID" id="serverID" listKey="serverID" listValue="serverName" value="%{notice.serverID}"/>
<br/><br/>

时间：&nbsp;&nbsp; 开始:<input type="text" id="startTime" name="startTime" value="<s:date name="notice.startTime" format="yyyy-MM-dd HH:mm:ss"/>"/> &nbsp;至&nbsp;
                        <input type="text" id="endTime" name="endTime" value="<s:date name="notice.endTime" format="yyyy-MM-dd HH:mm:ss"/>"/><br/><br/>

标题：<input type="text" id="title" name="title" value="<s:property value="notice.title"/>"  /><br/><br/>

内容：<textarea name="content" id="content" cols="50" rows="8"><s:property value="notice.content"/></textarea><br/><br/>

时间间隔：
    <s:select list="#{30000:'30秒',60000:'1分钟',180000:'3分钟',300000:'5分钟'}" id="intervaltime" name="intervaltime" listKey="key" listValue="value" value="%{notice.intervaltime}"/>
    &nbsp;&nbsp;&nbsp;&nbsp;

公告次数:
   <s:select list="#{1:'1次',2:'2次',3:'3次',5:'5次'}" id="times" name="times" listKey="key" listValue="value" value="%{notice.times}"/>
<br/><br/>
</div>
<div>
<input name="upd" type="button" value="提交" onclick="addNotice()"/>  &nbsp;&nbsp;
<input name="cancel" type="button" value="取消" onclick="tb_remove()"/>
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