package gm.action;

import gm.db.DataService;
import gm.entities.*;
import gm.exceptions.FunException;
import gm.services.ChatManager;
import gm.services.GameService;
import gm.services.GameServiceListManager;
import gm.services.GmListManager;
import gm.services.ServerExpMoudlusService;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Created by IntelliJ IDEA. User: jiaodongjie Date: 11-2-14 Time: δΈε2:55
 */
public class ManageAction extends ActionSupport
        implements Preparable, ServletRequestAware, ServletResponseAware, SessionAware {

    private static Logger log = Logger.getLogger(ManageAction.class);

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> session;

    private String errorMsg = "ζε";

    private static final String START = "start";
    private static final String FAIL = "fail";

    private GmUser gm = null;

    private List<GmUser> gmUserList;
    private List<ServerInfoBean> serverList;
    private Map<Integer, String> gameMapList;

    private List<GmLetter> gmLetterList;
    private GmLetter letter;
    private int gmLetterNumber;

    private List<GmNotice> gmNoticeList;
    private GmNotice notice;
    private int gmNoticeNumber;

    private int currServerID;
    private String currServerName;

    private PlayerInfo player;

    private List<AddGoodsInfo> addInfoList;

    private List<Menu> menuList;

    private Menu menu;
    private Fun fun;

    private IndexNotice inotice;
    private List<IndexNotice> indexNoticeList;
    private int inoticeNumer;

    private int infoListNumber;//ιη¨ζ°ι

    private RechargePresentPoint rpp;
    private List<RechargePresentPoint> rppList;

    private LoadingTip loadingtip;
    private List<LoadingTip> loadingTipList;

    private List<GmExpMoudlus> gmExpMoudlus;
    private GmExpMoudlus expmoudlus;

    public GmExpMoudlus getExpmoudlus() {
        return expmoudlus;
    }

    public void setExpmoudlus(GmExpMoudlus expmoudlus) {
        this.expmoudlus = expmoudlus;
    }

    public List<GmExpMoudlus> getGmExpMoudlus() {
        return gmExpMoudlus;
    }

    public void setGmExpMoudlus(List<GmExpMoudlus> gmExpMoudlus) {
        this.gmExpMoudlus = gmExpMoudlus;
    }

    @Override
    public void prepare() throws Exception {
        gm = (GmUser) session.get("gm");
        if (null == gm) {
            errorMsg = "ζ¨ζͺη»ε½οΌθ―·η»ε½";
//			response.sendRedirect("/gmTools/index");
            throw new FunException(errorMsg);
        }
        log.debug("p = " + request.getParameter("p"));
        if (request.getParameter("p") != null) {
            if (request.getParameter("funID") == null) {
                errorMsg = "ζ¬’θΏδ½Ώη¨δΈζ΅·εΉ½εΉ½GMε·₯ε·";
//                response.sendRedirect("/gmTools/login/fun_error");
                throw new FunException(errorMsg);
//                return;
            }

            int funID = Integer.parseInt(request.getParameter("funID"));
            log.debug("operation fun id = " + funID);
            Fun fun = DataService.getFun(funID);
            log.debug("fun == " + fun);
            if (fun == null) {
                errorMsg = "ζ ζ­€εθ½οΌιζ³θ?Ώι?";
//                response.sendRedirect("/gmTools/login/fun_error");
                throw new FunException(errorMsg);
//                return;
            }
            if (gm.getLevel() < fun.getLevel()) {
                errorMsg = "η­ηΊ§δΈε€οΌδΈθ½δ½Ώη¨ζ­€εθ½";
//                response.sendRedirect("/gmTools/login/fun_error");
//                return;
                throw new FunException(errorMsg);
            }
            if (fun.getShow() == 0) {
                errorMsg = "ζ­€εθ½ζζͺεΌζΎδ½Ώη¨";
//                response.sendRedirect("/gmTools/login/fun_error");
//                return;
                throw new FunException(errorMsg);
            }
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
    }

    public String serverlist() {
        serverList = DataService.getServerInfoList();
        return SUCCESS;
    }

    public String gmlist() {
        gmUserList = GmListManager.getInstance().getGmUserList();
        log.debug("gm list size  = " + gmUserList.size());
        return SUCCESS;
    }

    /**
     * ζ₯θ―’η©ε?ΆδΏ‘ζ―
     *
     * @return
     * @throws Exception
     */
    public String playerInfo() throws Exception {
        int serverID = Integer.parseInt(request.getParameter("serverID"));
        int type = Integer.parseInt(request.getParameter("type"));
        String content = request.getParameter("content");
        player = DataService.getPlayerInfo(serverID, type, content);
        if (player != null) {
            return SUCCESS;
        } else {
            errorMsg = "ζͺζ₯θ―’ε°θ―₯η©ε?Άηζ°ζ?γ";
            response.getWriter().write(errorMsg);
            return null;
        }
    }

    /**
     * δΏ?ζΉε―η 
     *
     * @return
     * @throws Exception
     */
    public String updpwd() throws Exception {
        String oldpwd = request.getParameter("oldpwd");
        String newpwd = request.getParameter("newpwd");
        GmUser currGm = (GmUser) session.get("gm");

        GmUser gm = DataService.getGm(currGm.getUsername(), oldpwd);
        if (gm != null) {
            boolean succ = DataService.updatePassword(gm.getId(), newpwd);
            if (!succ) {
                errorMsg = "δΏ?ζΉε―η ε€±θ΄₯";
            } else {
                errorMsg = "δΏ?ζΉζε";
            }
        } else {
            errorMsg = "ζ§ε―η ιθ――";
        }
        response.getWriter().write(errorMsg);

        return null;
    }

    /**
     * ζ·»ε ζ°GM
     *
     * @return
     * @throws Exception
     */
    public String registe() throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int groupID = Integer.parseInt(request.getParameter("groupID"));
        int level = Integer.parseInt(request.getParameter("level"));
        System.out.println("register username=" + username + ",password=" + password + ",groupID=" + groupID + ",level=" + level);
        boolean isExists = DataService.checkGmUserExists(username);
        if (isExists) {
            errorMsg = "η¨ζ·εε·²ε­ε¨οΌ";

            response.getWriter().write(errorMsg);

            return null;
        }
        boolean success = DataService.addGmUser(username, password, groupID, level);
        if (!success) {
            errorMsg = "ζ³¨εε€±θ΄₯οΌθ―·ιζ°ζ³¨ε";
            response.getWriter().write(errorMsg);

            return null;
        }
        gm = DataService.getGm(username, password);
        log.debug("registe success gm = " + gm.getId());
        GmListManager.getInstance().addGm(gm);
        return SUCCESS;
    }

    public String start() {
        serverList = DataService.getServerInfoBeanList();
        gameMapList = GameServiceListManager.getInstance().getGameMapList();
        String m = request.getParameter("m");
        if (m != null) {
            /*if(m.equals("notice"))
    	        gmNoticeList = DataService.getGmNoticeList(0, 100);
            if(m.equals("auditgoods"))
                addInfoList = DataService.getAddInfoList(1,-1);
            if(m.equals("auditpoint"))
                addInfoList = DataService.getAddInfoList(2,-1);
            if(m.equals("auditmodiyplayer"))
                addInfoList = DataService.getAddInfoList(3,-1);*/
            if (m.equals("addfun")) {
                menuList = DataService.getMenuList(false, false);
            }
        }

        return SUCCESS;
    }

    public String queryPlayerInfo() {
        serverList = DataService.getServerInfoBeanList();
        return SUCCESS;
    }

    /**
     * θ·εε?‘ζ Έη©εζε ηΉδΏ‘ζ―
     *
     * @return
     */
    public String infoList() {
        int type = Integer.parseInt(request.getParameter("type"));
        int flag = Integer.parseInt(request.getParameter("flag"));

        request.setAttribute("type", type);
        request.setAttribute("flag", flag);

        infoListNumber = DataService.getAddInfoListNumber(type, flag);
        log.debug(type + "--infoListNumber = " + infoListNumber);
        if (request.getParameter("next_page") != null) {
            int next_page = Integer.parseInt(request.getParameter("next_page"));
            int per_page = Integer.parseInt(request.getParameter("per_page"));//ε¨ι‘΅ι’δΈθ?Ύη½?
            log.debug("next_page=" + next_page + ",per_page=" + per_page);
            if (next_page <= 0) {
                next_page = 1;
            }

            int start = (next_page - 1) * per_page;
            int end = per_page;
            log.debug("addInfoList list start == " + start + ",end==" + end);
            addInfoList = DataService.getAddInfoList(type, flag, start, end);
            log.debug("addInfoList size=" + addInfoList.size());
            return SUCCESS;
        }

        return START;
    }

    /**
     * η¬η§»
     *
     * @return
     */
    public String blink() throws Exception {
        int serverID = Integer.parseInt(request.getParameter("serverID"));
        int type = Integer.parseInt(request.getParameter("type"));//1:userID,2:nickname
        String content = request.getParameter("content");
        int mapID = Integer.parseInt(request.getParameter("mapID"));//η¬η§»ε°ηΉζζΆεͺη§»ε°ε°εΎ
        String memo = request.getParameter("memo");
        int res = DataService.addBlink(serverID, gm.getId(), type, content, mapID, memo);
        log.debug("blink [" + content + "], res = " + res);
        if (res == 1) {
            errorMsg = "η©ε?ΆδΈε¨ηΊΏ";
        } else if (res == 2) {
            errorMsg = "ε°εΎIDιθ――";
        } else if (res == -1) {
            errorMsg = "ε€±θ΄₯,θΎε₯ζθ――ζζε‘ε¨ε³ι­";
        }
        response.getWriter().write(errorMsg);

        return null;
    }

    /**
     * θΈ’η©ε?ΆδΈηΊΏ
     *
     * @return
     */
    public String reset() throws Exception {
        int serverID = Integer.parseInt(request.getParameter("serverID"));
        int type = Integer.parseInt(request.getParameter("type"));//1:userID,2:nickname
        String content = request.getParameter("content");
        String memo = request.getParameter("memo");

        boolean succ = DataService.resetPlayerStatus(serverID, type, gm.getId(), content, memo);

        if (!succ) {
            errorMsg = "θΈ’η©ε?ΆδΈηΊΏε€±θ΄₯";
        }
        response.getWriter().write(errorMsg);

        return null;
    }

    public String selectResetPlayersServerId() {
        serverList = DataService.getServerInfoBeanList();
        return SUCCESS;
    }

    /**
     * θΈ’η©ε?ΆδΈηΊΏ
     *
     * @return
     */
    public String resetPlayers() throws Exception {
        int serverID = Integer.parseInt(request.getParameter("serverID"));
        boolean succ = false;
        if (serverID == 0) {
            serverList = DataService.getServerInfoBeanList();
            for (ServerInfoBean sib : serverList) {
                succ = DataService.resetPlayers(sib.getServerID(), gm.getId());
            }
        } else {
            succ = DataService.resetPlayers(serverID, gm.getId());
        }
        errorMsg = "θΈ’δΈζζη©ε?Άζδ½ε?ζ―";
        if (!succ) {
            errorMsg = "θΈ’δΈζζη©ε?Άε€±θ΄₯";
        }
        response.getWriter().write(errorMsg);
        return null;
    }

    /**
     * η¦θ¨
     *
     * @return
     */
    public String chatBlack() throws Exception {
        int serverID = Integer.parseInt(request.getParameter("serverID"));
        int type = Integer.parseInt(request.getParameter("type"));
        String content = request.getParameter("content");
        String memo = request.getParameter("memo");
        int keepTime = Integer.parseInt(request.getParameter("keepTime"));
        String endTime = request.getParameter("endTime");

        boolean succ = DataService.setPlayerChatBlack(serverID, gm.getId(), type, content, keepTime, endTime, memo);

        if (!succ) {
            errorMsg = "η¦θ¨ε€±θ΄₯";
        }
        response.getWriter().write(errorMsg);

        return null;
    }

    /**
     * θ§£η¦η¦θ¨
     *
     * @return
     */
    public String delChatBlack() throws Exception {
        int serverID = Integer.parseInt(request.getParameter("serverID"));
        int type = Integer.parseInt(request.getParameter("type"));
        String content = request.getParameter("content");
        String memo = request.getParameter("memo");

        boolean succ = DataService.deleteChatBlack(serverID, gm.getId(), type, content, memo);
        if (!succ) {
            errorMsg = "θ§£η¦ε€±θ΄₯";
        }
        response.getWriter().write(errorMsg);

        return null;
    }

    /**
     * ε»η»θ§θ²
     *
     * @return
     */
    public String roleBlack() throws Exception {
        int serverID = Integer.parseInt(request.getParameter("serverID"));
        int type = Integer.parseInt(request.getParameter("type"));
        String content = request.getParameter("content");
        String memo = request.getParameter("memo");
        int keepTime = Integer.parseInt(request.getParameter("keepTime"));
        String endTime = request.getParameter("endTime");
        log.debug("black role : " + serverID + ",type=" + type + ",content=" + content + ",keepTime=" + keepTime
                + ",endtime=" + endTime + ",memo=" + memo);
        boolean succ = DataService.setPlayerBlack(serverID, gm.getId(), type, content, keepTime, endTime, memo);
        if (!succ) {
            errorMsg = "ε»η»θ§θ²ε€±θ΄₯";
        }
        response.getWriter().write(errorMsg);

        return null;
    }

    /**
     * θ§£ε»θ§θ²
     *
     * @return
     */
    public String delRoleBlack() throws Exception {
        int serverID = Integer.parseInt(request.getParameter("serverID"));
        int type = Integer.parseInt(request.getParameter("type"));
        String content = request.getParameter("content");
        String memo = request.getParameter("memo");
        log.debug("del black role : " + serverID + ",type=" + type + ",content=" + content + ",memo=" + memo);
        boolean succ = DataService.deleteRoleBlack(serverID, gm.getId(), type, content, memo);
        if (!succ) {
            errorMsg = "θ§£ε»θ§θ²ε€±θ΄₯";
        }
        response.getWriter().write(errorMsg);

        return null;
    }

    /**
     * ε»η»θ΄¦ε·
     *
     * @return
     */
    public String accountBlack() throws Exception {
        int accountID = Integer.parseInt(request.getParameter("accountID"));
        String memo = request.getParameter("memo");
        int keepTime = Integer.parseInt(request.getParameter("keepTime"));
        String endTime = request.getParameter("endTime");

        boolean succ = DataService.setAccountBlack(accountID, gm.getId(), keepTime, endTime, memo);
        if (!succ) {
            errorMsg = "ε»η»θ΄¦ε·ε€±θ΄₯";
        }
        response.getWriter().write(errorMsg);

        return null;
    }

    /**
     * θ§£ε»θ΄¦ε·
     *
     * @return
     */
    public String delAccountBlack() throws Exception {
        int accountID = Integer.parseInt(request.getParameter("accountID"));
        String memo = request.getParameter("memo");

        boolean succ = DataService.deleteAccountBlack(gm.getId(), accountID, memo);
        if (!succ) {
            errorMsg = "θ§£ε»θ΄¦ε·ε€±θ΄₯";
        }
        response.getWriter().write(errorMsg);

        return null;
    }

    /**
     * GMι?δ»Άεθ‘¨
     */
    public String gmLetterList() throws Exception {
        gmLetterNumber = DataService.getGmLetterNumber();
        log.debug("gmLetterNumber = " + gmLetterNumber);
        if (request.getParameter("next_page") != null) {
            int next_page = Integer.parseInt(request.getParameter("next_page"));
            int per_page = Integer.parseInt(request.getParameter("per_page"));//ε¨ι‘΅ι’δΈθ?Ύη½?
            log.debug("next_page=" + next_page + ",per_page=" + per_page);
            if (next_page <= 0) {
                next_page = 1;
            }

            int start = (next_page - 1) * per_page;
            int end = per_page;
            log.debug("gmLetterList list start == " + start + ",end==" + end);
            gmLetterList = DataService.getGmLetterList(start, end);
            log.debug("gmLetterList size=" + gmLetterList.size());
            return SUCCESS;
        }

        return START;
    }

    /**
     * GMι?δ»ΆδΏ‘ζ―
     *
     * @return
     */
    public String gmLetterInfo() throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        letter = DataService.getGmLetterByID(id);
        return SUCCESS;
    }

    /**
     * GMεε€ι?δ»Ά
     *
     * @return
     */
    public String gmReplyLetter() throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        int serverID = Integer.parseInt(request.getParameter("serverID"));
        String content = request.getParameter("content");
        GmLetter letter = DataService.getGmLetterByID(id);
        if (letter.getStatus() == 1) {
            errorMsg = "ζ­€ι?δ»Άε·²εε€";

            return ERROR;
        } else {
            boolean succ = DataService.replyGmLetter(id, serverID, content, gm.getId());
            if (!succ) {
                errorMsg = "εε€ε€±θ΄₯οΌη¨εεθ―";
                return ERROR;
            }
        }
//    	response.getWriter().write(errorMsg);

        return SUCCESS;
    }

    /**
     * ζ·»ε ε¬ε
     *
     * @return
     * @throws Exception
     */
    public String addNotice() throws Exception {
        int serverID = Integer.parseInt(request.getParameter("serverID"));
        String srtarTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        int intervalTime = Integer.parseInt(request.getParameter("intervaltime"));
        int times = Integer.parseInt(request.getParameter("times"));

        boolean succ = DataService.addGmNotice(serverID, gm.getId(), title, content, srtarTime, endTime, intervalTime, times);
        if (!succ) {
            errorMsg = "ζ·»ε ε¬εε€±θ΄₯";
            return ERROR;
        }
//    	response.getWriter().write(errorMsg);
        return SUCCESS;
    }

    /**
     * ε¬εεθ‘¨
     *
     * @return
     * @throws Exception
     */
    public String noticeList() throws Exception {
        gmNoticeNumber = DataService.getGmNoticeNumber();
        if (request.getParameter("next_page") != null) {
            int next_page = Integer.parseInt(request.getParameter("next_page"));
            int per_page = Integer.parseInt(request.getParameter("per_page"));//ε¨ι‘΅ι’δΈθ?Ύη½?
            log.debug("next_page=" + next_page + ",per_page=" + per_page);
            if (next_page <= 0) {
                next_page = 1;
            }

            int start = (next_page - 1) * per_page;
            int end = per_page;
            log.debug("noticeList start == " + start + ",end==" + end);
            gmNoticeList = DataService.getGmNoticeList(start, end);
            log.debug("noticeList size=" + gmNoticeList.size());
            return SUCCESS;
        }
        return START;
    }

    /**
     * εδΈͺε¬εδΏ‘ζ―
     *
     * @return
     * @throws Exception
     */
    public String singleNotice() throws Exception {
        serverList = DataService.getServerInfoBeanList();
        int id = Integer.parseInt(request.getParameter("id"));
        notice = DataService.getNoticeById(id);
        if (notice == null) {
            errorMsg = "ζ²‘ζθΏδΈͺε¬ε";
        }
        return SUCCESS;
    }

    /**
     * ε ι€εδΈͺε¬ε
     *
     * @return
     * @throws Exception
     */
    public String delNotice() throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        DataService.delNotice(id);

        return SUCCESS;
    }

    /**
     * δΏ?ζΉε¬ε
     *
     * @return
     * @throws Exception
     */
    public String updateNotice() throws Exception {
        serverList = DataService.getServerInfoBeanList();
        int id = Integer.parseInt(request.getParameter("id"));
        int serverID = Integer.parseInt(request.getParameter("serverID"));
        String srtarTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        int intervalTime = Integer.parseInt(request.getParameter("intervaltime"));
        int times = Integer.parseInt(request.getParameter("times"));

        boolean succ = DataService.updateGmNotice(id, serverID, gm.getId(), title, content, srtarTime, endTime, intervalTime, times);
        if (!succ) {
            errorMsg = "δΏ?ζΉε¬εε€±θ΄₯";
            return ERROR;
        }
//    	response.getWriter().write(errorMsg);
        return SUCCESS;
    }

    /**
     * θ·εθε€©εε?Ή
     *
     * @return
     * @throws Exception
     */
    public String chatContent() throws Exception {
        int serverID = Integer.parseInt(request.getParameter("serverID"));
        String startStr = request.getParameter("s");

        int start = 0;

        if (startStr != null) {
            start = Integer.parseInt(startStr);
        }

        currServerID = serverID;
        GameService gameService = GameServiceListManager.getInstance().getGameServiceList().get(serverID);
        currServerName = gameService.getServerName();

        String content_x = "";
        if (!GmListManager.getInstance().logged(gm)) {
            content_x = "δ½ ε·²η¦»ηΊΏοΌθ―·ιζ°η»ιοΌ";
        } else {
            if (gameService.isRunning()) {
                List<String> contentList = ChatManager.getInstance().getChatListByServerID(serverID);
                StringBuffer sb = new StringBuffer("");
                if (contentList != null) {
                    for (int i = contentList.size() - 1; i >= 0; i--) {
//                    for(String content : contentList){
                        log.debug("get chat content : " + contentList.get(i));
                        sb.append(contentList.get(i)).append("\r\n");
                    }
                    ChatManager.getInstance().clearChatContent(serverID);
                }
                content_x = sb.toString();
            } else {
                content_x = "ζ­€ζε‘ε¨ε·²ε³ι­";
            }
        }
        if (start == serverID) {
            request.setAttribute("content", content_x);
            return SUCCESS;
        } else {
            response.getWriter().write(content_x);
            return null;
        }
    }

    /**
     * η»η©ε?Άζ·»ε η©εοΌιθ¦ε?‘ζ Έζθ½ηζ­£ζ·»ε 
     *
     * @return
     * @throws Exception
     */
    public String addGoodsForPlayer() throws Exception {
        int serverID = Integer.parseInt(request.getParameter("serverID"));
        int type = Integer.parseInt(request.getParameter("type"));
        String content = request.getParameter("content");
        String memo = request.getParameter("memo");
        int goodsID = Integer.parseInt(request.getParameter("goodsID"));
        int number = Integer.parseInt(request.getParameter("number"));
        int res = DataService.addGoodsForPlayer(gm.getId(), serverID, type, content, memo, goodsID, number);
        if (res == 1) {
            errorMsg = "Vui lΓ²ng Δα»£i xem xΓ©t, ngΖ°α»i chΖ‘i cΓ³ thα» thΓͺm vΓ o ngΖ°α»i chΖ‘i sau khi ΔΓ‘nh giΓ‘ ΔΖ°α»£c thΓ΄ng qua";
        } else if (res == -1) {
            errorMsg = "KhΓ΄ng cΓ³ vαΊ­t phαΊ©m nΓ y, vui lΓ²ng nhαΊ­p ID vαΊ­t phαΊ©m chΓ­nh xΓ‘c";
        } else if (res == 2) {
            errorMsg = "KhΓ΄ng cΓ³ ngΖ°α»i chΖ‘i nΓ y";
        } else {
            errorMsg = "ΔΓ£ xαΊ£y ra lα»i. Vui lΓ²ng thα»­ lαΊ‘i";
        }
        response.getWriter().write(errorMsg);
        return null;
    }

    /**
     * ε?‘ζ Έη»η©ε?Άζ·»ε ηη©ε
     *
     * @return
     * @throws Exception
     */
    public String auditAddGoods() throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        int flag = Integer.parseInt(request.getParameter("flag"));// 1:ιθΏ  2:δΈιθΏ
        int serverID = Integer.parseInt(request.getParameter("serverID"));
        int res = DataService.auditAddGoodsForPlayer(id, flag, gm.getId(), serverID);
        if (res == 0) {
            errorMsg = "0_η»η©ε?Άζ·»ε η©εζε";
        } else if (res == 1) {
            errorMsg = "1_εΊιδΊοΌζ²‘ζζΎε°θΏδΈͺη©ε?Ά";
        } else if (res == 3) {
            errorMsg = "3_θΏζ‘θ?°ε½ε·²η»ε?‘ζ ΈθΏδΊ";
        } else if (res == 4) {
            errorMsg = "4_ε?‘ζ Έζε";
        } else if (res == 5) {
            errorMsg = "5_ζε‘η«―εΊιδΊοΌθ?Ύη½?δΈΊε?‘ζ ΈδΈιθΏ";
        } else {
            errorMsg = "2_εΊιδΊ";
        }
        response.getWriter().write(errorMsg);
        return null;
    }

    /**
     * η»η©ε?Άε ηΉοΌιθ¦ε?‘ζ Έζθ½ηζ­£ζ·»ε 
     *
     * @return
     * @throws Exception
     */
    public String addPoint() throws Exception {
        int serverID = Integer.parseInt(request.getParameter("serverID"));
        int type = Integer.parseInt(request.getParameter("type"));
        String content = request.getParameter("content");
        String memo = request.getParameter("memo");
        int point = Integer.parseInt(request.getParameter("point"));
        int res = DataService.addPointForPlayer(gm.getId(), serverID, type, content, memo, point);
        if (res == 1) {
            errorMsg = "θ―·η­εΎε?‘ζ ΈοΌε?‘ζ ΈιθΏεζθ½η»η©ε?Άζ·»ε ";
        } else if (res == 2) {
            errorMsg = "ζ²‘ζθΏδΈͺη©ε?Ά";
        } else {
            errorMsg = "εΊιδΊοΌθ―·ιθ―";
        }
        response.getWriter().write(errorMsg);
        return null;
    }

    /**
     * ε?‘ζ Έη»η©ε?Άε ηΉ
     *
     * @return
     * @throws Exception
     */
    public String auditAddPoint() throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        int flag = Integer.parseInt(request.getParameter("flag"));
        int serverID = Integer.parseInt(request.getParameter("serverID"));

        int res = DataService.auditAddPointForPlayer(id, flag, gm.getId(), serverID);
        if (res == 0) {
            errorMsg = "0_η»η©ε?Άε ηΉζε";
        } else if (res == 1) {
            errorMsg = "1_εΊιδΊοΌζ²‘ζζΎε°θΏδΈͺη©ε?Ά";
        } else if (res == 3) {
            errorMsg = "3_θΏζ‘θ?°ε½ε·²η»ε?‘ζ ΈθΏδΊ";
        } else if (res == 4) {
            errorMsg = "4_ε?‘ζ Έζε";
        } else if (res == 5) {
            errorMsg = "5_ζε‘η«―εΊιδΊοΌθ?Ύη½?δΈΊε?‘ζ ΈδΈιθΏ";
        } else {
            errorMsg = "2_εΊιδΊ";
        }
        response.getWriter().write(errorMsg);
        return null;
    }

    public String auditModifyPlayerInfo() throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        int flag = Integer.parseInt(request.getParameter("flag"));
        int serverID = Integer.parseInt(request.getParameter("serverID"));

        int res = DataService.auditModifyPlayerInfo(id, flag, gm.getId(), serverID);
        if (res == 0) {
            errorMsg = "0_δΏ?ζΉη©ε?Άε±ζ§ζε";
        } else if (res == 1) {
            errorMsg = "1_εΊιδΊοΌζ²‘ζζΎε°θΏδΈͺη©ε?Ά";
        } else if (res == 3) {
            errorMsg = "3_θΏζ‘θ?°ε½ε·²η»ε?‘ζ ΈθΏδΊ";
        } else if (res == 4) {
            errorMsg = "4_ε?‘ζ Έζε";
        } else if (res == 5) {
            errorMsg = "5_ζε‘η«―εΊιδΊοΌθ?Ύη½?δΈΊε?‘ζ ΈδΈιθΏ";
        } else {
            errorMsg = "2_εΊιδΊ";
        }
        response.getWriter().write(errorMsg);
        return null;
    }

    /**
     * δΏ?ζΉη©ε?Άε±ζ§οΌη­ηΊ§γιι±γη±ζεΌ
     *
     * @return
     * @throws Exception
     */
    public String modifyPlayer() throws Exception {
        int serverID = Integer.parseInt(request.getParameter("serverID"));
        int type = Integer.parseInt(request.getParameter("type"));
        String content = request.getParameter("content");
        String memo = request.getParameter("memo");
        String money_s = request.getParameter("money");
        String level_s = request.getParameter("level");
        String loverValue_s = request.getParameter("loverValue");
        String skillPoint_s = request.getParameter("skillPoint");

        int money = money_s == null ? 0 : Integer.parseInt(money_s);
        int level = level_s == null ? 0 : Integer.parseInt(level_s);
        int loverValue = loverValue_s == null ? 0 : Integer.parseInt(loverValue_s);
        int skillPoint = skillPoint_s == null ? 0 : Integer.parseInt(skillPoint_s);

        int res = DataService.modifyPlayerInfo(gm.getId(), serverID, type, content, memo, money, level, loverValue, skillPoint);
        if (res == 1) {
            errorMsg = "θ―·η­εΎε?‘ζ ΈοΌε?‘ζ ΈιθΏεζθ½η»η©ε?Άζ·»ε ";
        } else if (res == 0) {
            errorMsg = "εΊιδΊοΌζ²‘ζζΎε°θΏδΈͺη©ε?Ά";
        } else {
            errorMsg = "εΊιδΊ";
        }
        response.getWriter().write(errorMsg);
        return null;
    }

    /**
     * ζ·»ε θε
     *
     * @return
     * @throws Exception
     */
    public String addMenu() throws Exception {
        String name = request.getParameter("name");
        int sequence = Integer.parseInt(request.getParameter("sequence"));
        int show = Integer.parseInt(request.getParameter("show"));

        DataService.addMenu(name, sequence, show, gm.getId());

        menuList = DataService.getMenuList(true, false);
        return SUCCESS;
    }

    /**
     * ζ·»ε εθ½
     *
     * @return
     * @throws Exception
     */
    public String addFun() throws Exception {
        String name = request.getParameter("name");
        String url = request.getParameter("url");
        int sequence = Integer.parseInt(request.getParameter("sequence"));
        int menuID = Integer.parseInt(request.getParameter("menuID"));
        int level = Integer.parseInt(request.getParameter("level"));
        int show = Integer.parseInt(request.getParameter("show"));

        DataService.addFun(name, url, sequence, menuID, level, show, gm.getId());
        menuList = DataService.getMenuList(true, false);
        return SUCCESS;
    }

    /**
     * δΏ?ζΉεθ½
     *
     * @return
     * @throws Exception
     */
    public String updFun() throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String url = request.getParameter("url");
        int sequence = Integer.parseInt(request.getParameter("sequence"));
        int menuID = Integer.parseInt(request.getParameter("menuID"));
        int level = Integer.parseInt(request.getParameter("level"));
        int show = Integer.parseInt(request.getParameter("show"));

        DataService.updFun(id, name, url, sequence, menuID, level, show, gm.getId());
        menuList = DataService.getMenuList(true, false);
        return SUCCESS;
    }

    /**
     * εΌε§δΏ?ζΉθε
     *
     * @return
     * @throws Exception
     */
    public String updMenuStart() throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        menu = DataService.getMenu(id);

        return SUCCESS;
    }

    /**
     * δΏ?ζΉθε
     *
     * @return
     * @throws Exception
     */
    public String updMenu() throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int sequence = Integer.parseInt(request.getParameter("sequence"));
        int show = Integer.parseInt(request.getParameter("show"));

        DataService.updMenu(id, name, sequence, show, gm.getId());

        menuList = DataService.getMenuList(true, false);
        return SUCCESS;
    }

    /**
     * εΌε§δΏ?ζΉεθ½
     *
     * @return
     * @throws Exception
     */
    public String updFunStart() throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        fun = DataService.getFun(id);
        menuList = DataService.getMenuList(false, false);
        return SUCCESS;
    }

    /**
     * ζΎη€Ίζζθεεθ½
     *
     * @return
     * @throws Exception
     */
    public String menulist() throws Exception {
        menuList = DataService.getMenuList(true, false);
        return SUCCESS;

    }

    /**
     * ζ·»ε ε¬ε/ζ΄»ε¨
     *
     * @return
     * @throws Exception
     */
    public String addIndexNotice() throws Exception {
        int res = DataService.addIndexNotice(inotice);
        if (res == 1) {
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * θ·εε¬ε/ζ΄»ε¨
     *
     * @return
     * @throws Exception
     */
    public String indexNoticeList() throws Exception {
//        int page = Integer.parseInt(request.getParameter("page"));
        inoticeNumer = DataService.getIndexNoticeNumber();
        log.debug("index notice number = " + inoticeNumer);
        if (request.getParameter("next_page") != null) {
            int next_page = Integer.parseInt(request.getParameter("next_page"));
            int per_page = Integer.parseInt(request.getParameter("per_page"));//ε¨ι‘΅ι’δΈθ?Ύη½?
            log.debug("next_page=" + next_page + ",per_page=" + per_page);
            if (next_page <= 0) {
                next_page = 1;
            }

            int start = (next_page - 1) * per_page;
            int end = per_page;
            log.debug("idnex notice list start == " + start + ",end==" + end);
            indexNoticeList = DataService.getIndexNoticeList(start, end);
            log.debug("indexNoticeList size=" + indexNoticeList.size());
            return SUCCESS;
        }
        return START;
    }

    /**
     * θ·εεδΈͺε¬ε/ζ΄»ε¨
     *
     * @return
     * @throws Exception
     */
    public String getIndexNotice() throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        serverList = DataService.getServerInfoBeanList();
        inotice = DataService.getIndexNotice(id);
        log.debug("get inotice id=" + id + ",notice=" + inotice.getTitle());
        return SUCCESS;
    }

    /**
     * δΏ?ζΉε¬ε/ζ΄»ε¨
     *
     * @return
     * @throws Exception
     */
    public String updIndexNotice() throws Exception {
        int res = DataService.updIndexNotice(inotice);
        if (res == 1) {
            return SUCCESS;
        }
        errorMsg = "δΏ?ζΉε€±θ΄₯οΌη¨εεθ―....";
        return ERROR;
    }

    /**
     * ε ι€ε¬ε/ζ΄»ε¨
     *
     * @return
     * @throws Exception
     */
    public String delIndexNotice() throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        DataService.delIndexNotice(id);
        return SUCCESS;
    }

    /**
     * ζ·»ε εεΌθ΅ ηΉζ΄»ε¨
     *
     * @return
     * @throws Exception
     */
    public String addRechargePresentPoint() throws Exception {
        int res = DataService.addRechargePresentPoint(rpp);
        if (res == 1) {
            return SUCCESS;
        }
        errorMsg = "ζ·»ε ε€±θ΄₯οΌη¨εεθ―γγγ";
        return ERROR;
    }

    /**
     * δΏ?ζΉεεΌ θ΅ ηΉζ΄»ε¨
     *
     * @return
     * @throws Exception
     */
    public String updRechargePresentPoint() throws Exception {
        int res = DataService.updRechargePresentPoint(rpp);
        if (res == 1) {
            return SUCCESS;
        }
        errorMsg = "δΏ?ζΉε€±θ΄₯οΌη¨εεθ―γγγ";
        return ERROR;
    }

    /**
     * θ·εεδΈͺεεΌθ΅ ηΉζ΄»ε¨
     *
     * @return
     * @throws Exception
     */
    public String getRechargePresentPoint() throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        rpp = DataService.getRechargePresentPoint(id);
        serverList = DataService.getServerInfoBeanList();
        return SUCCESS;
    }

    /**
     * θ·εεεΌθ΅ ηΉζ΄»ε¨εθ‘¨
     *
     * @return
     * @throws Exception
     */
    public String getRechargePresentPointList() throws Exception {
        infoListNumber = DataService.getRechargePresentPointNumber();
        log.debug("getRechargePresentPointNumber = " + infoListNumber);
        if (request.getParameter("next_page") != null) {
            int next_page = Integer.parseInt(request.getParameter("next_page"));
            int per_page = Integer.parseInt(request.getParameter("per_page"));//ε¨ι‘΅ι’δΈθ?Ύη½?
            log.debug("next_page=" + next_page + ",per_page=" + per_page);
            if (next_page <= 0) {
                next_page = 1;
            }

            int start = (next_page - 1) * per_page;
            int end = per_page;
            log.debug("rppList start == " + start + ",end==" + end);
            rppList = DataService.getRechargePresentPointList(start, end);
            log.debug("rppList size=" + rppList.size());
            return SUCCESS;
        }
        return START;

    }

    public String addLoadingTip() throws Exception {
        int res = DataService.addLoadingTip(loadingtip);
        if (res == 1) {
            return SUCCESS;
        }
        errorMsg = "ζ·»ε ε€±θ΄₯οΌθ―·η¨εεθ―γγγ";
        return ERROR;
    }

    public String getLoadingTip() throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        loadingtip = DataService.getLoadingTip(id);
        return SUCCESS;
    }

    public String delLoadingTip() throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));

        DataService.delLoadingTip(id);

        return SUCCESS;
    }

    public String updLoadingTip() throws Exception {
        int res = DataService.updLoadingTip(loadingtip);
        if (res == 1) {
            return SUCCESS;
        }
        errorMsg = "δΏ?ζΉε€±θ΄₯οΌθ―·η¨εεθ―γγγ";
        return ERROR;
    }

    public String loadingTipList() throws Exception {
        infoListNumber = DataService.getLoadingTipNumber();
        log.debug("getLoadingTipList number  = " + infoListNumber);
        if (request.getParameter("next_page") != null) {
            int next_page = Integer.parseInt(request.getParameter("next_page"));
            int per_page = Integer.parseInt(request.getParameter("per_page"));//ε¨ι‘΅ι’δΈθ?Ύη½?
            log.debug("next_page=" + next_page + ",per_page=" + per_page);
            if (next_page <= 0) {
                next_page = 1;
            }

            int start = (next_page - 1) * per_page;
            int end = per_page;
            log.debug("loadingTipList start == " + start + ",end==" + end);
            loadingTipList = DataService.getLoadingTipList(start, end);
            log.debug("loadingTipList size=" + loadingTipList.size());
            return SUCCESS;
        }
        return START;

    }

    public String addServerexpMoudlusView() throws Exception {
        serverList = DataService.getServerInfoBeanList();
        return SUCCESS;
    }

    public String updateServerExpMoudlusView() throws Exception {
        int serverid = Integer.parseInt(request.getParameter("serverid"));
        expmoudlus = DataService.getServerExpMoudlus(serverid);
        return SUCCESS;
    }

    /**
     * θ·εΎζε‘ε¨η»ιͺη³»ζ°εθ‘¨
     *
     * @return
     * @throws Exception
     */
    public String getExpMoudlusList() throws Exception {
        gmExpMoudlus = ServerExpMoudlusService.getExpMoudlusList();
        return SUCCESS;
    }

    public String addServerExpMoudlus() {
        int serverID = Integer.parseInt(request.getParameter("serverID"));
        if (serverID == 0) {
            errorMsg = "δΈθ½ιη½?ε¨ι¨ζε‘ε¨οΌθ―·εη¬ιη½?";
            return ERROR;
        }
        boolean succ = false;
        try {
            if (request.getParameter("expMoudlus") == null || "".equals(request.getParameter("expMoudlus"))) {
                errorMsg = "θ―·θΎε₯η»ιͺη³»ζ°οΌ";
                return ERROR;
            }
            float expMoudlus = Float.parseFloat(request.getParameter("expMoudlus"));
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            if (expMoudlus <= 0) {
                errorMsg = "θ―·θΎε₯ζ­£η‘?ηη³»ζ°οΌ";
                return ERROR;
            }
            if (startTime == null || "".equals(startTime)) {
                errorMsg = "θ―·θΎε₯εΌε§ζΆι΄οΌ";
                return ERROR;
            }
            if (endTime == null || "".equals(endTime)) {
                errorMsg = "θ―·θΎε₯η»ζζΆι΄οΌ";
                return ERROR;
            }
            GmExpMoudlus gem = new GmExpMoudlus();
            gem.setServerId(serverID);
            gem.setMoudlus(expMoudlus + "");
            gem.setStartTime(startTime);
            gem.setEndTime(endTime);
            if (ServerExpMoudlusService.addExpMoudlus(gem)) {
                succ = DataService.updateServerExpMoudls(serverID, expMoudlus, startTime, endTime);
                errorMsg = "ζ΄ζ°ζε‘ε¨η«―η»ιͺη³»ζ°ε?ζ―";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!succ) {
            errorMsg = "ζ΄ζ°ζε‘ε¨η«―η»ιͺη³»ζ°ε€±θ΄₯";
            return ERROR;
        }
        return SUCCESS;
    }

    public String updateServerExpMoudlus() {
        int serverID = Integer.parseInt(request.getParameter("serverID"));
        if (serverID == 0) {
            errorMsg = "δΈθ½ιη½?ε¨ι¨ζε‘ε¨οΌθ―·εη¬ιη½?";
            return ERROR;
        }
        boolean succ = false;
        try {
            if (request.getParameter("expMoudlus") == null || "".equals(request.getParameter("expMoudlus"))) {
                errorMsg = "θ―·θΎε₯η»ιͺη³»ζ°οΌ";
                return ERROR;
            }
            float expMoudlus = Float.parseFloat(request.getParameter("expMoudlus"));
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            if (expMoudlus <= 0) {
                errorMsg = "θ―·θΎε₯ζ­£η‘?ηη³»ζ°οΌ";
                return ERROR;
            }
            if (startTime == null || "".equals(startTime)) {
                errorMsg = "θ―·θΎε₯εΌε§ζΆι΄οΌ";
                return ERROR;
            }
            if (endTime == null || "".equals(endTime)) {
                errorMsg = "θ―·θΎε₯η»ζζΆι΄οΌ";
                return ERROR;
            }
            GmExpMoudlus gem = new GmExpMoudlus();
            gem.setServerId(serverID);
            gem.setMoudlus(expMoudlus + "");
            gem.setStartTime(startTime);
            gem.setEndTime(endTime);
            if (ServerExpMoudlusService.updateExpMoudlus(gem)) {
                succ = DataService.updateServerExpMoudls(serverID, expMoudlus, startTime, endTime);
                errorMsg = "ζ΄ζ°ζε‘ε¨η«―η»ιͺη³»ζ°ε?ζ―";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!succ) {
            errorMsg = "ζ΄ζ°ζε‘ε¨η«―η»ιͺη³»ζ°ε€±θ΄₯";
            return ERROR;
        }
        return SUCCESS;
    }

    public List<IndexNotice> getIndexNoticeList() {
        return indexNoticeList;
    }

    public void setIndexNoticeList(List<IndexNotice> indexNoticeList) {
        this.indexNoticeList = indexNoticeList;
    }

    public IndexNotice getInotice() {
        return inotice;
    }

    public void setInotice(IndexNotice inotice) {
        this.inotice = inotice;
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

    public GmUser getGm() {
        return gm;
    }

    public void setGm(GmUser gm) {
        this.gm = gm;
    }

    public List<ServerInfoBean> getServerList() {
        return serverList;
    }

    public void setServerList(List<ServerInfoBean> serverList) {
        this.serverList = serverList;
    }

    public Map<Integer, String> getGameMapList() {
        return gameMapList;
    }

    public void setGameMapList(Map<Integer, String> gameMapList) {
        this.gameMapList = gameMapList;
    }

    public List<GmLetter> getGmLetterList() {
        return gmLetterList;
    }

    public void setGmLetterList(List<GmLetter> gmLetterList) {
        this.gmLetterList = gmLetterList;
    }

    public GmLetter getLetter() {
        return letter;
    }

    public void setLetter(GmLetter letter) {
        this.letter = letter;
    }

    public List<GmNotice> getGmNoticeList() {
        return gmNoticeList;
    }

    public void setGmNoticeList(List<GmNotice> gmNoticeList) {
        this.gmNoticeList = gmNoticeList;
    }

    public PlayerInfo getPlayer() {
        return player;
    }

    public void setPlayer(PlayerInfo player) {
        this.player = player;
    }

    public int getCurrServerID() {
        return currServerID;
    }

    public void setCurrServerID(int currServerID) {
        this.currServerID = currServerID;
    }

    public String getCurrServerName() {
        return currServerName;
    }

    public void setCurrServerName(String currServerName) {
        this.currServerName = currServerName;
    }

    public GmNotice getNotice() {
        return notice;
    }

    public void setNotice(GmNotice notice) {
        this.notice = notice;
    }

    public List<AddGoodsInfo> getAddInfoList() {
        return addInfoList;
    }

    public void setAddInfoList(List<AddGoodsInfo> addInfoList) {
        this.addInfoList = addInfoList;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Fun getFun() {
        return fun;
    }

    public void setFun(Fun fun) {
        this.fun = fun;
    }

    public int getInoticeNumer() {
        return inoticeNumer;
    }

    public void setInoticeNumer(int inoticeNumer) {
        this.inoticeNumer = inoticeNumer;
    }

    public int getGmLetterNumber() {
        return gmLetterNumber;
    }

    public void setGmLetterNumber(int gmLetterNumber) {
        this.gmLetterNumber = gmLetterNumber;
    }

    public int getGmNoticeNumber() {
        return gmNoticeNumber;
    }

    public void setGmNoticeNumber(int gmNoticeNumber) {
        this.gmNoticeNumber = gmNoticeNumber;
    }

    public int getInfoListNumber() {
        return infoListNumber;
    }

    public void setInfoListNumber(int infoListNumber) {
        this.infoListNumber = infoListNumber;
    }

    public RechargePresentPoint getRpp() {
        return rpp;
    }

    public void setRpp(RechargePresentPoint rpp) {
        this.rpp = rpp;
    }

    public List<RechargePresentPoint> getRppList() {
        return rppList;
    }

    public void setRppList(List<RechargePresentPoint> rppList) {
        this.rppList = rppList;
    }

    public LoadingTip getLoadingtip() {
        return loadingtip;
    }

    public void setLoadingtip(LoadingTip loadingtip) {
        this.loadingtip = loadingtip;
    }

    public List<LoadingTip> getLoadingTipList() {
        return loadingTipList;
    }

    public void setLoadingTipList(List<LoadingTip> loadingTipList) {
        this.loadingTipList = loadingTipList;
    }
}
