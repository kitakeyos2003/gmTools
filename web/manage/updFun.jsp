<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>修改功能</title>
<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../js/gm.js"></script>
</head>

<body>
<div align="center"><p>修改功能....</p></div>
<div>
    <s:form name="updFun" action="/manage/updupdFun" method="post" onSubmit="addFun()">
        <input type="hidden" name="ajax" value="jquery">
    <input type="hidden" name="id" id="id" value="<s:property value="fun.id"/>">
    名&nbsp;&nbsp;&nbsp;&nbsp;称：<input type="text" name="name" id="name"  value="<s:property value="fun.name"/>"/><br/>
    功能&nbsp;URL：<input type="text" name="url" id="url" size="50" value="<s:property value="fun.url"/>" /><br/>
    是否显示：<%--<select name="show" id="show">
        <option value="1">是</option>
        <option value="0">否</option>
    </select>--%>
        <s:select name="show" id="show" list="#{1:'是',0:'否'}" listKey="key" listValue="value"></s:select>
        <br/>
    显示顺序：<input type="text" name="sequence" id="sequence" value="<s:property value="fun.sequence"/>"><br/>
    所属菜单：<%--<select name="menuID" id="menuID">
        <s:iterator value="menuList">
            <option value="id"><s:property value="name"/> </option>
        </s:iterator>
    </select>--%>
        <s:select list="menuList" name="menuID" id="menuID" listKey="id" listValue="name" value="%{fun.menuID}"></s:select>
    <br/>
    使用等级：
        <s:select name="level" id="level" list="#{1:'1 级',2:'2 级',3:'3 级',4:'4 级',5:'5 级'}" listKey="key" listValue="value" value="%{fun.level}"></s:select>
        <br/><br/>
        <input type="submit" value="提交">
    </s:form>
</div>
<script type="text/javascript">
    $("#show").val(<s:property value="fun.show"/>);
</script>
</body>
</html>