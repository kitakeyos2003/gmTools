<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<title>审核给玩家加点</title>
<script type="text/javascript" src="../js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="../js/jquery.pagination.js"></script>
<script type="text/javascript" src="../js/gm.js"></script>
<link href="../css/pagination.css" media="screen" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="Searchresult"></div>
<div id="Pagination"></div>
<br/><br/>
<p>
<div style="display:none">
    <!--下面可以设置显示数量等情况，这样想修改每页显示数量时，只用修改下面的值就可以了，不用修改程序-->
<form name="paginationoptions">
    <input type="hidden" name="acount" id="acount" value="<s:property value="infoListNumber"/> ">
			<p><label for="items_per_page">每页显示数量</label><input type="text" value="20" name="items_per_page" id="items_per_page" class="numeric"/></p>
			<p><label for="num_display_entries">显示的页码数量</label><input type="text" value="10" name="num_display_entries" id="num_display_entries" class="numeric"/></p>
			<p><label for="num">页码末尾显示数量</label><input type="text" value="2" name="num_edge_entries" id="num_edge_entries" class="numeric"/></p>
			<p><label for="prev_text">上一页标签显示文字</label><input type="text" value="上一页" name="prev_text" id="prev_text"/></p>
			<p><label for="next_text">下一页标签显示文字</label><input type="text" value="下一页" name="next_text" id="next_text"/></p>
			<input type="button" id="setoptions" value="Set options" />
</form>
</div>
</p>
<s:set name="type" value="1"/>
<s:set name="flag" value="-1"/>
<s:if test="#request.type != null">
    <s:set name="type" value="#request.type"/>
</s:if>
<s:if test="#request.flag != null">
    <s:set name="flag" value="#request.flag"/>
</s:if>
<script type="text/javascript">
    function pageselectCallback(page_index, jq){

        var items_per_page = $('#items_per_page').val();

        var url = "/gmTools/manage/auditAddPointInfoList";
        var params = "type="+<s:property value="#type"/>+"&flag="+<s:property value="#flag"/>+"&next_page="+(page_index+1)+"&per_page="+items_per_page;

        $.ajax({
            type:"POST",
            url:url,
            data:params,
            datatype:"html",
            beforeSend:function(){
                $("#Searchresult").html("正在加载数据......");
            },
            success:function(data){
//                alert(data);
                $('#Searchresult').html(data);
                flushCountValue();
            },
            complete:function(XMLHttpRequest, textStatus){},
            error:function(XMLHttpRequest, textStatus, errorThrown){
                alert(textStatus+":"+errorThrown)
                alert(XMLHttpRequest.responseText);
            }
        });

        return false;
    }


    function getOptionsFromForm(){
        var opt = {callback: pageselectCallback};
        $("input:text").each(function(){
            opt[this.name] = this.className.match(/numeric/) ? parseInt(this.value) : this.value;
        });
        var htmlspecialchars ={ "&":"&amp;", "<":"&lt;", ">":"&gt;", '"':"&quot;"}
        $.each(htmlspecialchars, function(k,v){
            opt.prev_text = opt.prev_text.replace(k,v);
            opt.next_text = opt.next_text.replace(k,v);
        })
        return opt;
    }

    $(document).ready(function(){
        var optInit = getOptionsFromForm();
        $("#Pagination").pagination($("#acount").val(), optInit);

        $("#setoptions").click(function(){
            var opt = getOptionsFromForm();
            $("#Pagination").pagination($("#acount").val(), opt);
        });

    });
</script>
</body>
</html>