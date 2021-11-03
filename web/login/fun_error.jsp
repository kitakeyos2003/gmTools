<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>错误提示</title>
<script type="javascript" src="../js/jquery-1.5.min.js"></script>
<script type="javascript" src="../js/gm.js"></script>
</head>
<body>
<div align="center"><p> <span style="color:red"> <s:property value="errorMsg"/>  </span></p>
</div>
</body>
</html>