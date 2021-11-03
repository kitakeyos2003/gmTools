<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>修改菜单</title>
<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../js/gm.js"></script>
</head>

<body>
<div align="center"><p>修改菜单....</p></div>
 <div>
    <s:form name="updMenu" action="/manage/updupdMenu" method="post" onSubmit="addMenu()">
        <input type="hidden" name="ajax" value="jquery">
    <input type="hidden" name="id" id="id" value="<s:property value="menu.id"/>">
    名&nbsp;&nbsp;&nbsp;&nbsp;称：<input type="text" name="name" id="name"  value="<s:property value="menu.name"/>"/><br/>
    是否显示：<s:select name="show" id="show" list="#{1:'是',0:'否'}" listKey="key" listValue="value"></s:select>
        <br/>
    显示顺序：<input type="text" name="sequence" id="sequence" value="<s:property value="menu.sequence"/>" ><br/> <br/>
     <input type="submit" value="提交">
    </s:form>
</div>
<script type="text/javascript">
    $("#show").val(<s:property value="menu.show"/>);
</script>
</body>
</html>