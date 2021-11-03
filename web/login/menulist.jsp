<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page session="false" %>
<SCRIPT LANGUAGE="JavaScript">
image1 = new Image();
image1.src = '/gmTools/css/images/plus.gif';

function CloseSitemap()
{
	parent.URL = parent.mainFrame.URL
}

function ToggleDiv(node, id)
{
	sibling = document.getElementById(id);
	if (sibling.style.display == 'none')
	{
		if (node.childNodes.length > 0)
		{
			if (node.childNodes[0].tagName == "IMG")
			{
				node.childNodes[0].src = "/gmTools/css/images/minus.gif";
			}
		}

		sibling.style.display = '';
	}
	else
	{
		if (node.childNodes.length > 0)
		{
			if (node.childNodes[0].tagName == "IMG")
			{
				node.childNodes[0].src = "/gmTools/css/images/plus.gif";
			}
		}
		sibling.style.display = 'none';
	}
}

var currentPage = null;

function highlight(id)
{
	if (currentPage != null)
	{
		currentPage.style.backgroundColor = '#EEEEFF';
		currentPage.style.paddingLeft = '0px';
		currentPage.style.paddingRight = '0px';
		currentPage.style.border = '0px solid #666666';
	}

	currentPage = document.getElementById(id);
    if (currentPage) {
	    currentPage.style.backgroundColor = '#DDDDDD';
	    currentPage.style.paddingLeft = '3px';
	    currentPage.style.paddingRight = '3px';
	    currentPage.style.border = '1px solid #666666';
    }
}

</SCRIPT>
<TABLE BORDER="0" cellspacing="0" cellpadding="0">
	<TR>
		<TD nowrap>
            <A onClick="ToggleDiv(this, 'gm_tools_yoyo')">
                <IMG SRC="/gmTools/css/images/minus.gif"></A>&nbsp;&nbsp;
            <A>
                <IMG class="plusminus" SRC="/gmTools/css/images/page.gif" border=0>
                <SPAN id="gm_tools_yoyo_0">GMtool平台</SPAN></A>
            <DIV id="gm_tools_yoyo">

            <s:iterator value="menuList" id="menu">
                <TABLE BORDER="0" cellspacing="0" cellpadding="0">
                    <TR>
                        <TD WIDTH="13" nowrap>&nbsp;</TD>
                        <TD nowrap>
                            <A onClick="ToggleDiv(this, '<s:property value="id"/>')">
                                <IMG SRC="/gmTools/css/images/minus.gif"></A>&nbsp;&nbsp;
                            <A><IMG class="plusminus" SRC="/gmTools/css/images/page.gif" border=0>
                                <SPAN id="<s:property value="id"/>"><s:property value="name"/> </SPAN></A>
                            <DIV id="<s:property value="id"/>">
                                 <s:iterator value="#menu.funList" id="fun">
                                <TABLE BORDER="0" cellspacing="0" cellpadding="0">
                                    <TR>
                                        <TD WIDTH="30" nowrap>&nbsp;</TD>
                                        <TD nowrap>
                                            <A onClick="parent.mainFrame.location='<s:property value="#fun.url"/><s:if test="#fun.hasParams">&amp;</s:if><s:else>?</s:else>funID=<s:property value="#fun.id"/>&amp;p=menu'">
                                            <IMG class="plusminus" SRC="/gmTools/css/images/page.gif" border=0>
                                                <SPAN id="<s:property value="#fun.id"/>"><s:property value="#fun.name"/> </SPAN>
                                            </A><DIV id="<s:property value="#fun.id"/>_<s:property value="#fun.sequence"/>">
                                            </DIV>
                                        </TD>
                                    </TR>
                                </TABLE>
                                 </s:iterator>
                            </DIV>
                    </TD>
                </TR>
            </TABLE>
            </s:iterator>

        </DIV>
		</TD>
	</TR>
</TABLE></BODY></HTML>