<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>Thêm menu</title>
<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../js/gm.js"></script>

</head>

<body>
<div align="center"><p>Thêm menu ....</p></div>
<div>
    <s:form name="addMenuForm" action="/manage/addMenuMenuInfo" method="post" onSubmit="addMenu()">
        <input type="hidden" name="ajax" value="jquery">
    Tên：<input type="text" name="name" id="name"/><br/>
    Có hiển thị không：<s:select name="show" id="show" list="#{1:'Hiển thị',0:'Không hiển thị'}" listKey="key" listValue="value" value="1"></s:select> <br/>
    Thứ tự hiển thị：<input type="text" name="sequence" id="sequence" value="1"><br/>
    <br/>
        <input type="submit" value="Gửi">
    </s:form>
</div>
</body>
</html>