<%@ page language="java" contentType="text/plain" pageEncoding="UTF-8" 
%><%
    	int serverID = Integer.parseInt(request.getParameter("serverid"));
    	gm.entities.GmExpMoudlus gem = gm.db.DataService.getServerExpMoudlus(serverID); 
    	if(gem != null) {
    		out.write(new StringBuffer().append(gem.getMoudlus()).append(",").append(gem.getStartTime())
    				.append(",").append(gem.getEndTime()).toString());
    	} else {
    		out.write("-1");
    	}
%>