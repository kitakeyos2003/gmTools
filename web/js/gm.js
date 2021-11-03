function checkAddLoadingTip() {
    if ($("#content").val() == "") {
        alert("Nội dung không được để trống");
        return;
    }
    $("#formID").submit();
}
function checkRechargePresentPoint() {
    if ($("#point").val <= 0) {
        alert("Điểm quà tặng không hợp lệ");
        return;
    } else if ($("#startTime").val() == "") {
        alert("Thời gian bắt đầu không hợp lệ");
        return;
    } else if ($("#endTime").val() == "") {
        alert("Thời gian kết thúc không hợp lệ");
        return;
    }
    $("#formID").submit();
}
function flushIndexNoticeNumber(num) {
    $("#acount").val(num);
}
function checkIndexNotice() {
    if ($("#title").val() == "") {
        alert("Tiêu đề không hợp lệ");
        return;
    } else if ($("#content").val() == "") {
        alert("Nội dung không hợp lệ.");
        return;
    }
    $("#formID").submit();
}
function addMenu() {
    if ($("#name").val() == "") {
        alert("Tên không được bỏ trống.");
        return false;
    }
    if ($("#sequence").val() == "") {
        alert("Thứ tự hiển thị không được để trống.");
        return false;
    }
    return true;
}
function addFun() {
    if ($("#name").val() == "") {
        alert("Tên không được bỏ trống.");
        return false;
    }
    if ($("#url").val() == "") {
        alert("URL tính năng không được để trống");
        return false;
    }
    if ($("#sequence").val() == "") {
        alert("Thứ tự hiển thị không được để trống");
        return false;
    }
    return true;
}
function reloadmenu() {
    var url = "/gmTools/login/menulist";
    sendAjax(url, "", "menulistid");
}
function modifyPlayer() {
    if ($("#content").val() == "") {
        alert("Vui lòng điền vào tên  hoặc ID nhân vật.");
        return;
    }
    if ($("#memo").val() == "") {
        alert("Không được để trống lý do");
        return;
    }
    var url = "/gmTools/manage/modifyPlayerManage";
    var params = "serverID=" + $("#serverID").val() + "&type=" + $("#type").val() + "&content=" + $("#content").val()
            + "&memo=" + $("#memo").val() + "&money=" + $("#money").val() + "&level=" + $("#level").val()
            + "&loverValue=" + $("#loverValue").val() + "&skillPoint=" + $("#skillPoint").val();
    sendAjax(url, params, "modid");
}
function addgoods() {
    if ($("#content").val() == "") {
        alert("Vui lòng điền vào tên  hoặc ID nhân vật.");
        return;
    }
    if ($("#goodsID").val() == "") {
        alert("Vui lòng nhập mã vật phẩm.");
        return;
    }
    if ($("#memo").val() == "") {
        alert("Vui long điền lý do.");
        return;
    }
    var url = "/gmTools/manage/addGoodsForPlayerManage";
    var params = "serverID=" + $("#serverID").val() + "&type=" + $("#type").val() + "&content=" + $("#content").val()
            + "&memo=" + $("#memo").val() + "&goodsID=" + $("#goodsID").val() + "&number=" + $("#number").val();
    sendAjax(url, params, "addgoodsid");
}
function addpoint() {
    if ($("#content").val() == "") {
        alert("Vui lòng điền vào tên  hoặc ID nhân vật.");
        return;
    }
    if ($("#memo").val() == "") {
        alert("Vui lòng nhập lý do.");
        return;
    }
    var url = "/gmTools/manage/addPointManage";
    var params = "serverID=" + $("#serverID").val() + "&type=" + $("#type").val() + "&content=" + $("#content").val()
            + "&memo=" + $("#memo").val() + "&point=" + $("#point").val();
    sendAjax(url, params, "pointid");
}
function audit(type, id, flag, serverID) {
    var url = "/gmTools/manage/auditAddGoodsManage";
    if (type == 2) {
        url = "/gmTools/manage/auditAddPointManage";
    }
    if (type == 3) {
        url = "/gmTools/manage/auditModifyPlayerInfoManage"
    }
    var content = flag == 1 ? "Bạn muốn thông qua？" : "Bạn muốn không thông qua？";
    if (confirm(content)) {
        var params = "id=" + id + "&flag=" + flag + "&serverID=" + serverID + "&ajax=jquery";

        $.ajax({
            type: "POST",
            url: url,
            data: params,
            datatype: "text",
            dataFilter: function (data, type) {
                alert(data);
                $("#ares").html(data.substring(2));
                if (data.substring(0, 1) == "0" || data.substring(0, 1) == "4") {
                    var restr = "Xử lý hoàn thành";
                    if (flag == 1) {
                        restr = "thông qua";
                    }
                    if (flag == 0) {
                        restr = "không thông qua";
                    }
                    $("#aid_" + id).html(restr);
                    $("#status_" + id).html(restr);
                }
                if (data.substring(0, 1) == "5") {
                    var restr = "Đã xảy ra lỗi trên máy chủ";
                    $("#aid_" + id).html(restr);
                    $("#status_" + id).html(restr);
                }
            },
            beforeSend: function () {
                $("div").disabled = true;
//			$("#ares").html("处理中，请稍后......");
            },
            success: function (data) {
//			$("#ares").html(data);
            },
            complete: function (XMLHttpRequest, textStatus) {},
            error: function () {}
        });
    }
}
function updpwd() {
    if ($("#oldpwd").val() == "") {
        alert("Mật khẩu cũ không được bỏ trống.");
        return;
    }
    if ($("#newpwd").val() == "") {
        alert("Mật khẩu mới không được bỏ trống.");
        return;
    }
    if ($("#newpwd").val() != $("#newpwd2").val()) {
        alert("Mật khẩu nhập lại không khớp.");
        return;
    }
    var url = "/gmTools/manage/updpwdManage";
    var params = "oldpwd=" + $("#oldpwd").val() + "&newpwd=" + $("#newpwd").val();
    sendAjax(url, params, "msg");
}
function scroll(id) {
    var scrollTop = document.getElementById(id).scrollHeight - document.getElementById(id).clientHeight >= 0 ? document.getElementById(id).scrollHeight - document.getElementById(id).clientHeight : 0;
    document.getElementById(id).scrollTop = scrollTop;
}
function queryPlayer() {
    if ($("#content").val() == "") {
        alert("Vui lòng điền vào tên  hoặc ID nhân vật.");
        return;
    }
    var url = "/gmTools/manage/playerInfo";
    var params = "serverID=" + $("#serverID").val() + "&type=" + $("#type").val() + "&content=" + $("#content").val();
    sendAjax(url, params, "playerinfo");
}
function addNotice() {
    if ($("#startTime").val() == "") {
        alert("Thời gian bắt đầu không hợp lệ.");
        return;
    }
    if ($("#endTime").val() == "") {
        alert("Thời gian kết thúc không hợp lệ.");
        return;
    }
    if ($("#title").val() == "") {
        alert("Tiêu đề không hợp lệ.");
        return;
    }
    if ($("#content").val() == "") {
        alert("Nội dung không hợp lệ.");
        return;
    }
    $("#formID").submit();
//	var url = "/gmTools/manage/addNoticeManage";
//	var params = "serverID="+$("#serverID").val()+"&startTime="+$("#startTime").val()
//				+"&endTime="+$("#endTime").val()+"&title="+$("#title").val()+"&content="+$("#content").val()
//				+"&intervaltime="+$("#intervaltime").val()+"&times="+$("#times").val();
//	sendAjax(url, params, "resid");
//	gmNotileList();
}
function gmNotileList() {
    var url = "/gmTools/manage/gmNoticeList";
    sendAjax(url, "", "noticelist");
}
function showLetterInfo(id) {
    var url = "/gmTools/manage/letterInfo";
    var params = "id=" + id;
    sendAjax(url, params, "infoID");
//    $("#"+id).show();
//
//    $.ajax({
//		type:"POST",
//		url:url,
//		data:params,
//        datatype:"text",
//        dataFilter:function(data,type){
////            alert(data);
//            $("#"+id).html(data);
//        },
//		beforeSend:function(){
//			$("div").disabled = true;
////			$("#ares").html("处理中，请稍后......");
//		},
//		success:function(data){
////			$("#ares").html(data);
//		},
//		complete:function(XMLHttpRequest, textStatus){},
//		error:function(){}
//	});
}
function replyLetter() {
    if ($("#reply_content_id").val() == "") {
        alert("Vui lòng điền nội dung trả lời");
        return;
    }
    $("#formID").submit();
//	var url = "/gmTools/manage/gmReplyLetterManage";
//	var params = "id="+$("#letterid").val()+"&serverID="+$("#serverID").val()+"&content="+$("#reply_content_id").val();
//	sendAjax(url, params, "did");
//	closeLetterInfo();
//	if(succ){
//		document.location="/gmTools/manage/gmNoticeList";
//	}
}
function closeLetterInfo() {
    $("#infoID").html("");
}
function addgm() {
//	alert("agg gm");
    if ($("#gmName").val().length > 12) {
        alert("Tên không được vượt quá 12 ký tự");
        return;
    }
    if ($("#gmPassword").val() != $("#gmPassword2").val()) {
        alert("Mật khẩu nhập lại không khớp.");
        return;
    }
    var url = "/gmTools/manage/registe";
    var params = "username=" + $("#gmName").val() + "&password=" + $("#gmPassword").val() + "&groupID=" + $("#groupID").val() + "&level=" + $("#level").val();
    sendAjax(url, params, "addbut1");
}

function addserverexpmoudlus() {
//	alert("agg gm");
    if ($("#serverID").val() == 0) {
        alert("Yếu tố trải nghiệm không thể được định cấu hình cho tất cả các máy chủ, vui lòng định cấu hình nó một cách riêng biệt");
        return false;
    }
    if ($("#expMoudlus").val().length <= 0) {
        alert("Vui lòng nhập hệ số kinh nghiệm");
        return false;
    }
    if ($("#startTime").val().length <= 0) {
        alert("Vui lòng nhập thời gian bắt đầu");
        return false;
    }
    if ($("#endTime").val().length <= 0) {
        alert("Vui lòng nhập thời gian kết thúc");
        return false;
    }
    return true;
}

function black() {
    if ($("#content").val() == "") {
        alert("Vui lòng điền vào tên  hoặc ID nhân vật.");
        return;
    }
    if ($("#endTime").val() == "") {
        alert("Vui lòng nhập thời gian kết thúc");
        return;
    }
    var url = "/gmTools/manage/roleBlackManage";
    var params = "serverID=" + $("#serverID").val() + "&type=" + $("#type").val()
            + "&content=" + $("#content").val() + "&keepTime=" + $("#keepTime").val()
            + "&endTime=" + $("#endTime").val() + "&memo=" + $("#memo").val();
    sendAjax(url, params, "balid");
}
function delblack() {
    if ($("#delcontent").val() == "") {
        alert("Vui lòng điền vào tên  hoặc ID nhân vật.");
        return;
    }

    var url = "/gmTools/manage/delRoleBlackManage";
    var params = "serverID=" + $("#delserverID").val() + "&type=" + $("#deltype").val()
            + "&content=" + $("#delcontent").val() + "&memo=" + $("#delmemo").val();
    sendAjax(url, params, "delid");
}
function blink() {
    if ($("#content").val() == "") {
        alert("Vui lòng điền vào tên  hoặc ID nhân vật.");
        return;
    }
    var url = "/gmTools/manage/blinkManage";
    var params = "serverID=" + $("#serverID").val() + "&content=" + $("#content").val() + "&type=" + $("#type").val()
            + "&mapID=" + $("#mapID").val() + "&memo=" + $("#memo").val();
    sendAjax(url, params, "blinkid");
}
function reset() {
    if ($("#resetcontent").val() == "") {
        alert("Vui lòng điền vào tên  hoặc ID nhân vật.");
        return;
    }
    var url = "/gmTools/manage/resetManage";
    var params = "serverID=" + $("#resetserverID").val() + "&content=" + $("#resetcontent").val() + "&type=" + $("#resettype").val()
            + "&memo=" + $("#resetmemo").val();
    sendAjax(url, params, "resetid");
}
function chat_black() {
    if ($("#content").val() == "") {
        alert("Vui lòng điền vào tên  hoặc ID nhân vật.");
        return;
    }
    if ($("#endTime").val() == "") {
        alert("Vui lòng nhập thời gian kết thúc");
        return;
    }
    var url = "/gmTools/manage/chatBlackManage";
    var params = "serverID=" + $("#serverID").val() + "&type=" + $("#type").val()
            + "&content=" + $("#content").val() + "&keepTime=" + $("#keepTime").val()
            + "&endTime=" + $("#endTime").val() + "&memo=" + $("#memo").val();
    sendAjax(url, params, "balid");
}
function del_chat_black() {
    if ($("#delcontent").val() == "") {
        alert("Vui lòng điền vào tên  hoặc ID nhân vật.");
        return;
    }

    var url = "/gmTools/manage/delChatBlackManage";
    var params = "serverID=" + $("#delserverID").val() + "&type=" + $("#deltype").val()
            + "&content=" + $("#delcontent").val() + "&memo=" + $("#delmemo").val();
    sendAjax(url, params, "delid");
}

function sendAjax(url, params, msgHtmlId) {
    if (params.length > 0) {
        params += "&ajax=jquery";
    } else {
        params = "ajax=jquery";
    }

    $.ajax({
        type: "POST",
        url: url,
        data: params,
        datatype: "text",
        beforeSend: function () {
            $("div").disabled = true;
            $("#" + msgHtmlId).html("Đang xử lý, vui long đợi.............");
        },
        success: function (data) {
            $("#" + msgHtmlId).html(data);
            return data;
        },
        complete: function (XMLHttpRequest, textStatus) {},
        error: function () {}
    });
}

var timerID;
function relink(id) {
    $.get("/gmTools/login/relink?gmID=" + id);
//    alert(id);
}
function startlink(id) {//定时发送
    timerID = setInterval("relink(" + id + ")", 5 * 60 * 1000);
}
function canceltimer() { //取消定时
    alert("cancel timer..");
    clearInterval(timerID);
    timerID = 0;
}