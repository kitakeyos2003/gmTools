<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<%@ include file="inc.jsp" %>
<%--<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>--%>
<%--<script type="text/javascript" src="../js/gm.js"></script>--%>

<div> <input type="radio" onclick="document.location='/gmTools/manage/auditAddGoodsInfoList?type=1&flag=0&ajax=jquery'">Chưa xử lý &nbsp;&nbsp;
   <input type="radio" onclick="document.location='/gmTools/manage/auditAddGoodsInfoList?type=1&flag=1&ajax=jquery'"> Thông qua &nbsp;&nbsp;
 <input type="radio" onclick="document.location='/gmTools/manage/auditAddGoodsInfoList?type=1&flag=2&ajax=jquery'">Không thông qua &nbsp;&nbsp;
 <input type="radio" onclick="document.location='/gmTools/manage/auditAddGoodsInfoList?type=1&flag=-1&ajax=jquery'">Tất cả
</div>
<div>
<table width="1000" border="1">
  <tr>
    <th class="header1" scope="col">ID</th>
    <th class="header1" scope="col">Trạng thái</th>
    <th class="header1" scope="col">Máy chủ</th>
    <th class="header1" scope="col">Mã tài khoản</th>
    <th class="header1" scope="col">Tên nhân vật</th>
    <th class="header1" scope="col">Mã vật phẩm</th>
    <th class="header1" scope="col">Tên vật phẩm</th>
    <th class="header1" scope="col">Số lượng</th>
    <th class="header1" scope="col">Tên GM</th>
    <th class="header1" scope="col">Lý do</th>
    <th class="header1" scope="col">Thời gian yêu cầu</th>
    <th class="header1" scope="col">Hành động</th>
    <th class="header1" scope="col">Người xử lý</th>
    <th class="header1" scope="col">Thời gian xử lý</th>
  </tr>
  <s:iterator value="addInfoList">
  <tr>
    <td><s:property value="id"/></td>
    <td><span id="status_<s:property value="id"/>"> <s:if test="flag==0">Chưa xử lý</s:if><s:if test="flag==1">Thông qua</s:if><s:if test="flag==2">Không thông qua</s:if> </span></td>
    <td><s:property value="serverID"/></td>
    <td><s:property value="accountID"/></td>
    <td><s:property value="roleName"/></td>
    <td><s:property value="goodsID"/></td>
    <td><s:property value="goodsName"/></td>
    <td><s:property value="number"/></td>
    <td><s:property value="gmName"/> </td>
    <td><s:property value="memo"/></td>
    <td> <s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/> </td>
    <td> <span id="aid_<s:property value="id"/>">
        <s:if test="flag==0">
        <input type="button" value="Đồng ý" onclick="audit(1,<s:property value="id"/>,1,<s:property value="serverID"/>)"> &nbsp;&nbsp;
        <input type="button" value="Không đồng ý" onclick="audit(1,<s:property value="id"/>,2,<s:property value="serverID"/>)">
        </s:if>
        <s:if test="flag==1">Đồng ý</s:if>
        <s:if test="flag==2">Không đồng ý</s:if>   </span>
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