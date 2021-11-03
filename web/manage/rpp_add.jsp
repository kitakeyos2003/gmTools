<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> 
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>添加充值赠点活动</title>
<link href="../css/jquery-ui-1.8.9.custom.css" media="screen" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui-1.8.9.custom.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="../js/gm.js"></script>
</head>
<body>
<p>添加充值赠点活动</p>
<div>
  <s:form action="addRpp" method="post" id="formID">
  <p>选择分区：
    <select id="serverID" name="rpp.serverID">
      <s:iterator value="serverList">
        <option value="<s:property value="serverID"/>">
        <s:property value="serverName"/>
        </option> 
      </s:iterator>
    </select>
  </p>
  <p>选择价格：
      <s:select list="#{30:'30元',50:'50元',100:'100元',300:'300元'}" name="rpp.price" id="price" listKey="key" listValue="value"/>
  </p>
  <p>赠送点数：
     <input type="text" name="rpp.presentPoint" id="point" value="0">
  </p>
  <p>活动时间：&nbsp;&nbsp; 开始:<input type="text" id="startTime" name="rpp.startTime" /> &nbsp;至&nbsp;
                        <input type="text" id="endTime" name="rpp.endTime" />
  </p>

</div>
<div>
<input name="add" type="button" value="添加" onclick="checkRechargePresentPoint()()"/>
</s:form>
</div>
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