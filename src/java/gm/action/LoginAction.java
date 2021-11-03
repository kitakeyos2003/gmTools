package gm.action;

import gm.db.DataService;
import gm.entities.GmExpMoudlus;
import gm.entities.GmUser;
import gm.entities.Menu;
import gm.services.ChatManager;
import gm.services.GmListManager;
import gm.util.TimeUtil;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport 
	implements ServletRequestAware,ServletResponseAware,SessionAware{
	private static Logger log = Logger.getLogger(LoginAction.class);
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Map<String, Object> session;
	
	private String errorMsg;
	
	private static final String FAIL = "fail";
	
	private List<GmUser> gmUserList;

    private List<Menu> menuList;

    public String loginStart() throws Exception{
        if(session.get("gm") != null){
            return "logged";
        }
        return SUCCESS;
    }

	public String login(){
        log.debug("request = " + request);
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		GmUser gm = DataService.getGm(username, password);
		if(gm == null){
			errorMsg = "错误的用户名或密码";
			return FAIL;
		}

        if(GmListManager.getInstance().logged(gm)){
            errorMsg = "该账号已经在其它地方登陆了";
            return FAIL;
        }

		GmListManager.getInstance().addGm(gm);
		gmUserList = GmListManager.getInstance().getGmUserList();
		session.put("gm", gm);
//		String ip = DataService.getIP(request);
		String ip = request.getRemoteAddr();
		DataService.addGmLoginLog(gm.getId(), ip);

        errorMsg = "欢迎使用上海幽幽GM工具";

		return SUCCESS;
	}
	
	public String logout(){
		GmUser gm = (GmUser)session.get("gm");
		if(gm != null){
			session.remove("gm");
			GmListManager.getInstance().removeGm(gm);
			gmUserList = GmListManager.getInstance().getGmUserList();
		}
        request.setAttribute("logout","logoutclose");
		return SUCCESS;
	}

    /**
     * 菜单列表
     * @return
     */
    public String menulist(){
        menuList = DataService.getMenuList(true,true);
        return SUCCESS;
    }
	
	/**
     * 添加聊天内容
     * @return
     * @throws Exception
     */
    public String addchat() throws Exception{
    	int serverID = Integer.parseInt(request.getParameter("serverID"));
    	String content = request.getParameter("content");
    	log.debug(serverID+")聊天内容："+content);
    	ChatManager.getInstance().addChatContent(serverID, content + TimeUtil.formatCurrentDate());
    	return null;
    }
    
	/**
     * 获得服务器经验系数
     * @return
     * @throws Exception
     */
    public String getexpmoudlus() throws Exception{
    	int serverID = Integer.parseInt(request.getParameter("serverid"));
    	log.debug("获取经验系数serverID=" + serverID);
    	GmExpMoudlus gem = DataService.getServerExpMoudlus(serverID); 
    	if(gem != null) {
    		request.setAttribute("GETEXPMOUDLUS", new StringBuffer().append(gem.getMoudlus()).append(",").append(gem.getStartTime())
    				.append(",").append(gem.getEndTime()).toString());
    		return SUCCESS;
    	}
    	request.setAttribute("GETEXPMOUDLUS", "");
    	return SUCCESS;
    }

    /**
     * 刷新连接时间
     * @return
     * @throws Exception
     */
    public String link() throws Exception{
        int gmID = Integer.parseInt(request.getParameter("gmID"));
        log.debug("link gmID = " + gmID);
        GmListManager.getInstance().reflushGmLinkTime(gmID);
        return null;
    }

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}



	public List<GmUser> getGmUserList() {
		return gmUserList;
	}



	public void setGmUserList(List<GmUser> gmUserList) {
		this.gmUserList = gmUserList;
	}

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }
}
