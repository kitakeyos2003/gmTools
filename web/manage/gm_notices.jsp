<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> 
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<%@ include file="inc.jsp" %>
<link rel="stylesheet" href="../css/thickbox.css" type="text/css" media="screen" />
<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../js/thickbox-compressed.js"></script>

<table width="800" border="1">
  <tr>
    <th class="header1" scope="col">编号</th>
    <th class="header1" scope="col">标题</th>
    <th class="header1" scope="col">发布时间</th>
    <th class="header1" scope="col">操作</th>
  </tr>
  <s:iterator value="gmNoticeList">
  <tr align="center">
    <td><s:property value="id"/></td>
    <td><s:property value="title"/></td>
    <td><s:date name="startTime" format="yyyy-MM-dd HH:mm:ss"/>--<s:date name="endTime" format="yyyy-MM-dd HH:mm:ss"/></td>
    <td><a href="/gmTools/manage/singleNotice?id=<s:property value="id"/>&height=600&width=700&modal=true" target="_blank">修改</a> /
        <a href="/gmTools/manage/delNotice?id=<s:property value="id"/>">删除</a>  </td>
  </tr>
  </s:iterator>
</table>
<script type="text/javascript">
    function flushCountValue(){
//        alert("flushCountValue");
        flushIndexNoticeNumber(<s:property value="gmNoticeNumber"/>);
    }
</script>