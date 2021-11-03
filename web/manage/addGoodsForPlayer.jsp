<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Thêm vật phẩm cho người chơi</title>
        <script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
        <script type="text/javascript" src="../js/gm.js"></script>
    </head>

    <body>
        <h1 class="datepick-ctrl">Thêm vật phẩm cho người chơi</h1>
        <div id="blackid">
            <span>Chọn máy chủ：<select id="serverID" name="serverID">
                    <s:iterator value="serverList">
                        <option value="<s:property value="serverID"/>"><s:property value="serverName"/></option>
                    </s:iterator>
                </select></span><br/><br/>
            <span>Loại: <select id="type" name="type">
                    <option value="1">ID nhân vật</option>
                    <option value="2" selected>Tên nhân vật</option>
                </select>&nbsp;&nbsp;<input type="text" name="content" id="content"/> </span><br /><br/>
            <span>Mã vật phẩm：<input type="text" name="goodsID" id="goodsID"/>  &nbsp;&nbsp;&nbsp;&nbsp;
                Số lượng：<input type="text" name="number" id="number" value="1"/></span>
            <br/>  <br/>
            <span>Lý do：<textarea rows="5" cols="60" name="memo" id="memo"></textarea>  </span>
            <br/><br/>
            <input type="button" value="Thêm" id="but" onclick="addgoods()"/>  &nbsp;&nbsp; <input type="button" value="Hủy" id="cancelbut" />
            <br/>
            <span id="addgoodsid" style="color:red"></span>
        </div>
    </body>
</html>