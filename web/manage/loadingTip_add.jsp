<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> 
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>添加LoadingTip</title>
<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../js/gm.js"></script>
</head>
<body>
<p>添加LoadingTip</p>
<div>
  <s:form action="addLoadingTip" method="post" id="formID">

  <p>提示内容：
     <textarea rows="6" cols="30" name="loadingtip.content" id="content"></textarea>
  </p>


</div>
<div>
<input name="add" type="button" value="添加" onclick="checkAddLoadingTip()()"/>
</s:form>
</div>
</body>
</html>