<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>修改玩家属性</title>
<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../js/gm.js"></script>
</head>
<body>
 <h1 class="datepick-ctrl">修改玩家属性</h1>
<span>选择服务器：<select id="serverID" name="serverID">
		<s:iterator value="serverList">
			<option value="<s:property value="serverID"/>"><s:property value="serverName"/></option>
		</s:iterator>
	</select></span><br/><br/>
    <span>账号: <select id="type" name="type">
    	<option value="1">角色ID</option>
        <option value="2" selected>角色名称</option>
    </select>&nbsp;&nbsp;<input type="text" name="content" id="content"/> </span><br /><br/>
     <span>金钱：<input type="text" name="money" id="money" value="0"/><span style="color:red">*增加的金钱</span></span>
    <br/>
 <span>等级：<input type="text" name="level" id="level" value="0"/><span style="color:red">*增加的等级</span></span>
    <br/>
 <span>爱情值：<input type="text" name="loverValue" id="loverValue" value="0"/> </span><span style="color:red">*增加的爱情值，且只有恋人或夫妻才能增加</span>
    <br/>
 <span>技能点：<input type="text" name="skillPoint" id="skillPoint" value="0"/><span style="color:red">*增加的技能点</span></span>
    <br/>
 <span>原因：<textarea rows="5" cols="60" name="memo" id="memo"></textarea></span>
    <br/><br/>
    <input type="button" value="提交" id="but" onclick="modifyPlayer()"/>  &nbsp;&nbsp; <input type="button" value="取消" id="cancelbut" />
    <br/>
    <span id="modid" style="color:red"></span>
</body>
</html>