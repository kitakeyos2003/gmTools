<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> 
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>聊天监控</title>
<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../js/gm.js"></script>
<script type="text/javascript" src="../js/sleep.js"></script>
</head>
<body>
<div align="center">
<input type="hidden" id="serverID" value="<s:property value="currServerID"/>"/>
<p id="sss"></p>
<p>  <s:property value="currServerName"/> &nbsp;&nbsp; 聊天监控...</p>
<textarea id="content" name="content" cols="80" rows="30" readonly="readonly"> <s:property value="#request.content"/> </textarea>
</div>
<script type="text/javascript">
function getchat(){
	//alert("getchat");
	$.ajax({
		type:"POST",
		url:"/gmTools/manage/chatContentManage",
		data:"serverID="+$("#serverID").val()+"&ajax=jquery",
		datatype:"text",
		beforeSend:function(){},
		success:function(data){
			//alert(data);
			$("#msg").html(data);
			if(data != ""){
				$("#content").append(data);
				scroll("content");
			}
		},
		complete:function(XMLHttpRequest, textStatus){},
		error:function(){}
	});
	
}
//setTimeout(getchat,3000);
setInterval("getchat()",3000);
</script>
<p id="msg"></p>
</body>
</html>