<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<%@ include file="inc.jsp" %>
<link rel="stylesheet" href="../css/thickbox.css" type="text/css" media="screen" />
<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../js/thickbox-compressed.js"></script>

<div>
<table width="1000" border="1">
  <tr>
    <th class="header1" scope="col" width="30">编号</th>
    <th class="header1" scope="col" width="50">日期</th>
    <th class="header1" scope="col" width="30">分区</th>
    <th class="header1" scope="col" width="50">账号</th>    
    <th class="header1" scope="col" width="400">内容</th>
    <th class="header1" scope="col" width="50">问题分类</th>
    <th class="header1" scope="col" width="50">状态</th>
    <th class="header1" scope="col" >应答GM</th>
    <th class="header1" scope="col" width="50">详细</th>
  <!--  <th scope="col">标记答复</th> --> 
  </tr>
  <s:iterator value="gmLetterList" id="indx">
  <tr>
    <td> <s:property value="id"/> </td>
    <td> <s:date name="sendTime" format="yyyy-MM-dd HH:mm:ss"/> </td>
    <td><s:property value="serverID"/></td>
    <td><s:property value="senderRoleId"/> </td>
    <td><s:property value="content"/></td>
    <td><s:property value="typeName"/> </td>
    <td> <s:if test="status==1">已回复</s:if><s:else>未回复</s:else></td>
    <td><s:property value="replyGmName"/></td>
    <td> <a href="/gmTools/manage/reply_letter.jsp?id=<s:property value="id"/>&height=600&width=700&modal=true" target="_blank" >详细</a>  </td>
  <!-- <td>&nbsp;</td> --> 
  </tr>
      <tr ><div id="<s:property value="id"/>" style="display:none"></div></tr>
  </s:iterator>
</table>
<script type="text/javascript">
    function flushCountValue(){
//        alert("flushCountValue");
        flushIndexNoticeNumber(<s:property value="gmLetterNumber"/>);
    }
</script>
</div>
