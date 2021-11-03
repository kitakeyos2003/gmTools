<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> 
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>显示公告/活动</title>
<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../js/thickbox-compressed.js"></script>
</head>
<body>
<p>显示公告/活动</p>
<div>
  <p>选择分区：
      <s:select list="serverList" name="inotice.serverID" id="serverID" listKey="serverID" listValue="serverName" value="%{inotice.serverID}" disabled="true"/>
    </p>
  <p>类型：
      <s:select list="#{1:'公告',2:'活动'}" listKey="key" listValue="value" name="inotice.type" id="type" value="%{inotice.type}" disabled="true"/>
      <br/>
    <br/>
标题：
<input type="text" id="title" name="inotice.title" size="50" value="<s:property value="inotice.title"/>" disabled/>
      &nbsp;&nbsp;
      颜色：<s:select list="#{'0x8d5a2d':'默认','0xff0000':'红色','0xffffff':'白色','0x000000':'黑色','0x00ff1e':'绿色','0x0000ff':'蓝色','0xf4da00':'黄色'}" name="color" id="color" listKey="key" listValue="value" value="%{inotice.color}" disabled="true"/>
<br/>
<br/>
内容：
<textarea name="inotice.content" id="content" cols="60" rows="8" disabled> <s:property value="inotice.content"/> </textarea>
<br/>
  </p>
  <p>是否显示： &nbsp;<s:if test="inotice.show==1">是</s:if><s:else>否</s:else>
<br/> <br/>
      是否置顶:&nbsp; <s:if test="inotice.top==0"><span style="color:red">是</span></s:if><s:else>否</s:else>

      <br/> <br/>
 显示顺序：&nbsp; <s:property value="inotice.sequence"/>
<br/>
<br/>
      <input type="button" value="关闭" onclick="tb_remove()">
    </p>
</div>
</body>
</html>