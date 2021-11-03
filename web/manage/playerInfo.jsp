<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> 
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<%@ include file="inc.jsp" %>
角色查询结果：
<table width="600" border="1" >
  <tr>
    <th scope="col">通行账号</th><td><s:property value="player.accountID"/></td></tr><tr>
    <th scope="col">角色名称</th><td><s:property value="player.name"/></td></tr><tr>
    <th scope="col">角色ID</th><td><s:property value="player.userID"/></td></tr><tr>
    <th scope="col">登录时间</th><td><s:date name="player.lastLoginTime" format="yyyy-MM-dd HH:mm:ss"/></td></tr><tr>
    <th scope="col">离线时间</th><td><s:date name="player.lastLogoutTime" format="yyyy-MM-dd HH:mm:ss"/></td></tr><tr>

    <th scope="col">性别</th><td><s:property value="player.sex"/></td></tr><tr>    
    <th scope="col">等级</th><td><s:property value="player.level"/></td></tr><tr>
    <th scope="col">经验</th><td><s:property value="player.exp"/></td></tr><tr>
    <th scope="col">金钱</th><td><s:property value="player.money"/></td></tr><tr>
    <th scope="col">职业</th><td><s:property value="player.vocation"/></td></tr><tr>
   	<th scope="col">种族</th><td><s:property value="player.clan"/></td></tr><tr>
    <th scope="col">所在区</th><td><s:property value="player.serverID"/></td></tr><tr>
    
    <th scope="col">地图</th><td><s:property value="player.whereName"/></td></tr><tr>
   	<th scope="col">电话</th><td><s:property value="player.phone"/></td></tr><tr>
    <th scope="col">渠道</th><td><s:property value="player.publisher"/></td></tr><tr>
    
    <th scope="col">状态1</th><td><s:if test="player.online">在线</s:if><s:else>离线</s:else></td></tr><tr>
    <th scope="col">状态2</th><td><s:if test="player.chatBlack"> <span style="color:red">已禁言</span></s:if><s:else>未禁言</s:else></td></tr><tr>
    <th scope="col">状态3</th><td><s:if test="player.roleBlack"><span style="color:red">已冻结</span></s:if><s:else>未冻结</s:else></td>
  </tr>
</table>