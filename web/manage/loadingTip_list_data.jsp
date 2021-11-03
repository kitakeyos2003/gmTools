<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> 
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>

<link rel="stylesheet" href="../css/thickbox.css" type="text/css" media="screen" />

<script type="text/javascript" src="../js/thickbox-compressed.js"></script>

 <table width="1050" border="1">
  <tr align="center">
    <td width="5%">编号</td>
    <td width="75%">提示内容</td>
    <td width="20%">操作</td>
  </tr>
 <s:iterator value="loadingTipList" id="item">
  <tr align="center">
    <td><s:property value="id"/> </td>
    <td><s:property value="content"/></td>
    <td>
        <a href="/gmTools/manage/getLoadingTip?id=<s:property value="id"/>&height=500&width=600&modal=true" class="thickbox">修改</a>/
        <a href="/gmTools/manage/delLoadingTip?id=<s:property value="id"/>">删除</a>
    </td>
  </tr>
 </s:iterator>
</table>
<%--
<input type="hidden" name="count" id="count" value="<s:property value="inoticeNumer"/>">--%>
<script type="text/javascript">
    function flushCountValue(){
//        alert("flushCountValue");
        flushIndexNoticeNumber(<s:property value="infoListNumber"/>);
    }
</script>
