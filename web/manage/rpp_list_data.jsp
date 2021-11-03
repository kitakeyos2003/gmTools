<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> 
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>

<link rel="stylesheet" href="../css/thickbox.css" type="text/css" media="screen" />

<script type="text/javascript" src="../js/thickbox-compressed.js"></script>

 <table width="1050" border="1">
  <tr align="center">
    <td width="5%">编号</td>
    <td width="8%">分区</td>
    <td width="5%">价格</td>
    <td>赠送点数</td>
    <td width="16%">开始时间</td>
    <td width="16%">结束时间</td>
    <td width="16%">创建时间</td>
    <td width="16%">更新时间</td>
    <td width="10%">操作</td>
  </tr>
 <s:iterator value="rppList" id="item">
  <tr align="center">
    <td><s:property value="id"/> </td>
    <td><s:property value="serverName"/></td>
    <td><s:property value="price"/> </td>
    <td><s:property value="presentPoint"/> </td>
    <td><s:date name="startTime" format="yyyy-MM-dd HH:mm:ss"/></td>
    <td><s:date name="endTime" format="yyyy-MM-dd HH:mm:ss"/></td>
    <td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/> </td>
    <td><s:date name="updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
    <td>
        <a href="/gmTools/manage/getRpp?id=<s:property value="id"/>&height=500&width=600&modal=true" class="thickbox">修改</a>
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
