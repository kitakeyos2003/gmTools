<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> 
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>添加公告/活动</title>
<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../js/gm.js"></script>
</head>
<body>
<p>添加公告/活动</p>
<div>
  <s:form action="addIndexNotice" method="post" id="formID">
  <p>选择分区：
    <select id="serverID" name="inotice.serverID">
      <s:iterator value="serverList">
        <option value="<s:property value="serverID"/>">
        <s:property value="serverName"/>
        </option> 
      </s:iterator>
    </select>
    </p>
  <p>类型：
  <select id="type" name="inotice.type">
      <option value="1">公告</option>
      <option value="2">活动</option>
            </select><br/>
    <br/>
标题：
<input type="text" id="title" name="inotice.title" size="50" /> &nbsp;&nbsp;
      颜色：<s:select list="#{'0x8d5a2d':'默认','0xff0000':'红色','0xffffff':'白色','0x000000':'黑色','0x00ff1e':'绿色','0x0000ff':'蓝色','0xf4da00':'黄色'}" name="inotice.color" id="color" listKey="key" listValue="value"/>
<br/>
<br/>
内容：
<textarea name="inotice.content" id="content" cols="80" rows="8"></textarea>
<br/>
  </p>
  <p>是否显示：
    <select id="is_show" name="inotice.show">
      <option value="1">是</option>
      <option value="0">否</option>
            </select>
&nbsp;&nbsp;&nbsp;&nbsp;是否置顶:
<select id="is_top" name="inotice.top">
  <option value="1">否</option>
  <option value="0">是</option>
</select>
<br/>  <br/>
 显示顺序：
<input type="text" id="sequence" name="inotice.sequence" size="20" />
<br/>
<br/>
    </p>
</div>
<div>
<input name="add" type="button" value="添加" onclick="checkIndexNotice()"/>
</s:form>
</div>
</body>
</html>