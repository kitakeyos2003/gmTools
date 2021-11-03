<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>配置服务器经验系数</title>
<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../js/gm.js"></script>
<script language="javascript" type="text/javascript" src="../js/My97DatePicker/WdatePicker.js"></script>
</head>

<body>
 <h1 class="datepick-ctrl">配置服务器经验系数</h1>
<div id="blackid">
    <s:form name="addFunForm" action="manage/updateserverexpmoudlus" method="post"  onSubmit="addserverexpmoudlus()">
	<span>服务器：<s:property value="expmoudlus.serverId"/></span><br/><br/>
	<input type="hidden" name="serverID" id="serverID" value="<s:property value="expmoudlus.serverId"/>"/>
    <span>经验系数:<input type="text" name="expMoudlus" id="expMoudlus" value="<s:property value="expmoudlus.moudlus"/>"/> </span><br /><br/>
     <span>开始时间：<input type="text" name="startTime" id="startTime" value="<s:property value="expmoudlus.startTime"/>" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" style="width:300px"/> </span><br /><br/>
    <span>结束时间：<input type="text" name="endTime" id="endTime" value="<s:property value="expmoudlus.endTime"/>" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" style="width:300px"/> </span><br /><br/>
    <input type="submit" value="修改">
    </s:form><br/>
</div>
</body>
</html>