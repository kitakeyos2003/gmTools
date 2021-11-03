<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>菜单功能列表</title>
<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../js/gm.js"></script>
</head>
<body>
 <div style="width:300px">
     <s:iterator value="menuList" id="menu">
         <div>
             <span>-------------------------------------------------------------------------------------------</span>
            <li> <s:property value="sequence"/>. &nbsp;名称： <s:property value="name"/> &nbsp;&nbsp;&nbsp;&nbsp; <a href="/gmTools/manage/manageupdMenu?id=<s:property value="#menu.id"/>&ajax=jquery">修改</a>  </li>

             <div id="funlist_<s:property value="id"/>" align="right">
                <s:iterator value="#menu.funList" id="fun">
                    &nbsp;&nbsp;&nbsp;&nbsp; <li> <s:property value="#fun.sequence"/>:<s:property value="#fun.name"/>&nbsp;&nbsp;[等级：<s:property value="#fun.level"/>]&nbsp;&nbsp; <a href="/gmTools/manage/manageupdFun?id=<s:property value="#fun.id"/>&ajax=jquery">修改</a> </li>

                </s:iterator>
             </div>
         </div>
     </s:iterator>
 </div>
</body>
</html>