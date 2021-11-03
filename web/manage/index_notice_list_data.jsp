<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> 
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>

<link rel="stylesheet" href="../css/thickbox.css" type="text/css" media="screen" />

<script type="text/javascript" src="../js/thickbox-compressed.js"></script>

 <table width="1050" border="1">
  <tr align="center">
    <td width="5%">编号</td>
    <td width="8%">分区</td>
    <td width="5%">类型</td>
    <td>标题</td>
    <td width="5%">是否显示</td>
    <td width="5%">是否置顶</td>
    <td width="5%">顺序</td>
    <td width="5%">颜色</td>
    <td width="16%">创建时间</td>
    <td width="16%">更新时间</td>
    <td width="18%">操作</td>
  </tr>
 <s:iterator value="indexNoticeList" id="item">
  <tr align="center">
    <td><s:property value="id"/> </td>
    <td><s:property value="serverName"/></td>
    <td><s:if test="#item.type==1">公告</s:if><s:else>活动</s:else></td>
    <td><s:property value="title"/> </td>
    <td><s:if test="#item.show==1">是</s:if><s:else>否</s:else> </td>
    <td><s:if test="#item.top==0"> <span style="color:red">是</span> </s:if><s:else>否</s:else> </td>
    <td><s:property value="sequence"/> </td>
      <td>   <s:select list="#{'0x8d5a2d':'默认','0xff0000':'红色','0xffffff':'白色','0x000000':'黑色','0x00ff1e':'绿色','0x0000ff':'蓝色','0xf4da00':'黄色'}" name="color" id="color" listKey="key" listValue="value" value="%{#item.color}" disabled="true"/></td>
    <td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/> </td>
    <td><s:date name="updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
    <td> <a href="/gmTools/manage/showIndexNotice?id=<s:property value="id"/>&height=500&width=600&modal=true" class="thickbox">查看</a> /
        <a href="/gmTools/manage/startUpdIndexNotice?id=<s:property value="id"/>&height=500&width=600&modal=true" class="thickbox">修改</a>/
        <a href="gmTools/manage/delIndexNotice?id=<s:property value="id"/>">删除</a> </td>
  </tr>
 </s:iterator>
</table>
<%--
<input type="hidden" name="count" id="count" value="<s:property value="inoticeNumer"/>">--%>
<script type="text/javascript">
    function flushCountValue(){
//        alert("flushCountValue");
        flushIndexNoticeNumber(<s:property value="inoticeNumer"/>);
    }
</script>
