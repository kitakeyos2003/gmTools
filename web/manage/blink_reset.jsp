<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> 
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>瞬移/踢玩家下线</title>
<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../js/gm.js"></script>
</head>

<body>
<h1 class="datepick-ctrl">瞬移</h1>
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
    <span>瞬移目地地图：<select id="mapID" name="mapID">
   		<s:iterator value="gameMapList">
   			<option value="<s:property value="key"/>"><s:property value="value"/></option>
   		</s:iterator>
    </select>
    </span>
    <br/><br/>
    <input type="button" value="提交" id="but" onclick="blink()"/>  &nbsp;&nbsp; <input type="button" value="取消" id="cancelbut" />
    <br/>
    <span id="blinkid" style="color:red"></span>
</div>
<br /><br />
<h1 class="datepick-ctrl">踢玩家下线</h1>
<div id="delblackid">
	<span>选择服务器：<select id="resetserverID" name="resetserverID">
	<s:iterator value="serverList">
			<option value="<s:property value="serverID"/>"><s:property value="serverName"/></option> 
		</s:iterator>
	</select></span><br/><br/>
    <span>账号: <select id="resettype" name="resettype">
    	<option value="1">角色ID</option>
        <option value="2" selected>角色名称</option>
    </select>&nbsp;&nbsp;<input type="text" name="resetcontent" id="resetcontent"/> </span><br /><br/>
    <span> 原因： <textarea id="resetmemo" name="resetmemo"></textarea> </span><br/><br/>
    <input type="button" value="提交" id="dbut" onclick="reset()"/>  &nbsp;&nbsp; <input type="button" value="取消" id="canceldbut" />
    <br/>
    <span id="resetid" style="color:red"></span>
</div>
</body>
</html>