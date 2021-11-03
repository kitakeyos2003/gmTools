<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>添加功能</title>
<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../js/gm.js"></script>

</head>

<body>
<div align="center"><p>Thêm chức năng....</p></div>
<div>
    <s:form name="addFunForm" action="/manage/addFunMenuInfo" method="post"  onSubmit="addFun()">
        <input type="hidden" name="ajax" value="jquery">
    Tên：<input type="text" name="name" id="name"/><br/>
    URL chức năng：<input type="text" name="url" id="url" size="50"/><br/>
    Hiển thị：<select name="show" id="show">
        <option value="1">Có</option>
        <option value="2">Không</option>
    </select> <br/>
    Thứ tự hiển thị：<input type="text" name="sequence" id="sequence" value="1"><br/>
    Menu：<select name="menuID" id="menuID">
        <s:iterator value="menuList">
            <option value="<s:property value="id"/>"><s:property value="name"/> </option>
        </s:iterator>
    </select>
    <br/>
    Mức độ： <s:select name="level" id="level" list="#{1:'1 级',2:'2 级',3:'3 级',4:'4 级',5:'5 级'}" listKey="key" listValue="value" value="1"></s:select>
  <br/><br/>
        <input type="submit" value="提交">
    </s:form>
</div>
</body>
</html>