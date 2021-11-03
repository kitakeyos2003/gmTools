<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> 
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>修改公告/活动</title>
<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../js/gm.js"></script>
</head>
<body>
<p>修改公告/活动</p>
<div>
    <s:form action="updIndexNotice" method="post" id="formID">
    <input type="hidden" name="inotice.id" value="<s:property value="inotice.id"/>"/>
  <p>选择分区：
      <s:select list="serverList" name="inotice.serverID" id="serverID" listKey="serverID" listValue="serverName" value="%{inotice.serverID}"/>
    </p>
  <p>类型：
      <s:select list="#{1:'公告',2:'活动'}" listKey="key" listValue="value" name="inotice.type" id="type" value="%{inotice.type}"/>
      <br/>
    <br/>
标题：
<input type="text" id="title" name="inotice.title" value="<s:property value="inotice.title"/> " size="50" />
      &nbsp;&nbsp;
   颜色：<s:select list="#{'0x8d5a2d':'默认','0xff0000':'红色','0xffffff':'白色','0x000000':'黑色','0x00ff1e':'绿色','0x0000ff':'蓝色','0xf4da00':'黄色'}" name="inotice.color" id="color" listKey="key" listValue="value" value="%{inotice.color}"/>
<br/>
<br/>
内容：
<textarea name="inotice.content" id="content" cols="60" rows="8"><s:property value="inotice.content"/></textarea>
<br/>
  </p>
  <p>是否显示：
    <s:select list="#{1:'是',0:'否'}" name="inotice.show" id="show" listKey="key" listValue="value" value="%{inotice.show}"/>
&nbsp;&nbsp;&nbsp;&nbsp;是否置顶:
      <s:select list="#{0:'是',1:'否'}" name="inotice.top" id="top" listKey="key" listValue="value" value="%{inotice.top}"/>
<br/> <br/>
 显示顺序：
<input type="text" id="sequence" name="inotice.sequence" value="<s:property value="inotice.sequence"/> " size="20" />
<br/>
<br/>
    </p>
</div>
<div>
<input name="upd" type="button" value="修改" onclick="checkIndexNotice()"/> &nbsp;&nbsp;&nbsp;&nbsp; <input name="cancel" type="button" value="取消" onclick="tb_remove()"/>
    </s:form>
</div>

</body>
</html>