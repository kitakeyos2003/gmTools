<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<%@ include file="inc.jsp" %>
<%--<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>--%>
<%--<script type="text/javascript" src="../js/gm.js"></script>--%>

<div> <input type="radio" onclick="document.location='/gmTools/manage/auditAddPointInfoList?type=2&flag=0&ajax=jquery'">尚未审核 &nbsp;&nbsp;
   <input type="radio" onclick="document.location='/gmTools/manage/auditAddPointInfoList?type=2&flag=1&ajax=jquery'"> 已审核通过 &nbsp;&nbsp;
 <input type="radio" onclick="document.location='/gmTools/manage/auditAddPointInfoList?type=2&flag=2&ajax=jquery'">已审核未通过 &nbsp;&nbsp;
 <input type="radio" onclick="document.location='/gmTools/manage/auditAddPointInfoList?type=2&flag=-1&ajax=jquery'">所有
</div>
<div>
<table width="800" border="1">
  <tr>
    <th class="header1" scope="col">编号</th>
    <th class="header1" scope="col">状态</th>
    <th class="header1" scope="col">分区</th>
    <th class="header1" scope="col">账号</th>
    <th class="header1" scope="col">角色</th>    
    <th class="header1" scope="col">点数</th>
    <th class="header1" scope="col">操作人</th>
    <th class="header1" scope="col">说明</th>
    <th class="header1" scope="col">日期</th>
    <th class="header1" scope="col">审核操作</th>
    <th class="header1" scope="col">审核人</th>
    <th class="header1" scope="col">审核时间</th>
  </tr>
  <s:iterator value="addInfoList">
  <tr>
    <td><s:property value="id"/></td>
    <td><span id="status_<s:property value="id"/>"> <s:if test="flag==0">未审核</s:if><s:if test="flag==1">通过</s:if><s:if test="flag==2">未通过</s:if> </span></td>
    <td><s:property value="serverID"/></td>
    <td><s:property value="accountID"/></td>
    <td><s:property value="roleName"/></td>      
    <td><s:property value="goodsID"/></td>
    <td><s:property value="gmName"/> </td>
    <td><s:property value="memo"/></td>
    <td> <s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/> </td>
    <td> <span id="aid_<s:property value="id"/>">
        <s:if test="flag==0">
        <input type="button" value="通过" onclick="audit(2,<s:property value="id"/>,1,<s:property value="serverID"/>)"> &nbsp;&nbsp;
        <input type="button" value="不通过" onclick="audit(2,<s:property value="id"/>,2,<s:property value="serverID"/>)">
        </s:if>
        <s:if test="flag==1">通过</s:if>
        <s:if test="flag==2">未通过</s:if>   </span>
    </td>
    <td> <s:if test="flag>0"> <s:property value="auditGmName"/> </s:if></td>
    <td> <s:if test="flag>0"><s:date name="auditTime" format="yyyy-MM-dd HH:mm:ss"/> </s:if> </td>
  </tr>
  </s:iterator>
</table>
    </div>
<script type="text/javascript">
    function flushCountValue(){
//        alert("flushCountValue");
        flushIndexNoticeNumber(<s:property value="infoListNumber"/>);
    }
</script>
