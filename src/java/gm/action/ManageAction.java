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
 * Created by IntelliJ IDEA. User: jiaodongjie Date: 11-2-14 Time: 下午2:55
 */
public class ManageAction extends ActionSupport
        implements Preparable, ServletRequestAware, ServletResponseAware, SessionAware {

    private static Logger log = Logger.getLogger(ManageAction.class);

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> session;

    private String errorMsg = "成功";

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

    private int infoListNumber;//通用数量

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
            errorMsg = "您未登录，请登录";
//			response.sendRedirect("/gmTools/index");
            throw new FunException(errorMsg);
        }
        log.debug("p = " + request.getParameter("p"));
        if (request.getParameter("p") != null) {
            if (request.getParameter("funID") == null) {
                errorMsg = "欢迎使用上海幽幽GM工具";
//                response.sendRedirect("/gmTools/login/fun_error");
                throw new FunException(errorMsg);
//                return;
            }

            int funID = Integer.parseInt(request.getParameter("funID"));
            log.debug("operation fun id = " + funID);
            Fun fun = DataService.getFun(funID);
            log.debug("fun == " + fun);
            if (fun == null) {
                errorMsg = "无此功能，非法访问";
//                response.sendRedirect("/gmTools/login/fun_error");
                throw new FunException(errorMsg);
//                return;
            }
            if (gm.getLevel() < fun.getLevel()) {
                errorMsg = "等级不够，不能使用此功能";
//                response.sendRedirect("/gmTools/login/fun_error");
//                return;
                throw new FunException(errorMsg);
            }
            if (fun.getShow() == 0) {
                errorMsg = "此功能暂未开放使用";
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
     * 查询玩家信息
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
            errorMsg = "未查询到该玩家的数据。";
            response.getWriter().write(errorMsg);
            return null;
        }
    }

    /**
     * 修改密码
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
                errorMsg = "修改密码失败";
            } else {
                errorMsg = "修改成功";
            }
        } else {
            errorMsg = "旧密码错误";
        }
        response.getWriter().write(errorMsg);

        return null;
    }

    /**
     * 添加新GM
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
            errorMsg = "用户名已存在！";

            response.getWriter().write(errorMsg);

            return null;
        }
        boolean success = DataService.addGmUser(username, password, groupID, level);
        if (!success) {
            errorMsg = "注册失败，请重新注册";
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
     * 获取审核物品或加点信息
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
            int per_page = Integer.parseInt(request.getParameter("per_page"));//在页面上设置
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
     * 瞬移
     *
     * @return
     */
    public String blink() throws Exception {
        int serverID = Integer.parseInt(request.getParameter("serverID"));
        int type = Integer.parseInt(request.getParameter("type"));//1:userID,2:nickname
        String content = request.getParameter("content");
        int mapID = Integer.parseInt(request.getParameter("mapID"));//瞬移地点暂时只移到地图
        String memo = request.getParameter("memo");
        int res = DataService.addBlink(serverID, gm.getId(), type, content, mapID, memo);
        log.debug("blink [" + content + "], res = " + res);
        if (res == 1) {
            errorMsg = "玩家不在线";
        } else if (res == 2) {
            errorMsg = "地图ID错误";
        } else if (res == -1) {
            errorMsg = "失败,输入有误或服务器关闭";
        }
        response.getWriter().write(errorMsg);

        return null;
    }

    /**
     * 踢玩家下线
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
            errorMsg = "踢玩家下线失败";
        }
        response.getWriter().write(errorMsg);

        return null;
    }

    public String selectResetPlayersServerId() {
        serverList = DataService.getServerInfoBeanList();
        return SUCCESS;
    }

    /**
     * 踢玩家下线
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
        errorMsg = "踢下所有玩家操作完毕";
        if (!succ) {
            errorMsg = "踢下所有玩家失败";
        }
        response.getWriter().write(errorMsg);
        return null;
    }

    /**
     * 禁言
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
            errorMsg = "禁言失败";
        }
        response.getWriter().write(errorMsg);

        return null;
    }

    /**
     * 解禁禁言
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
            errorMsg = "解禁失败";
        }
        response.getWriter().write(errorMsg);

        return null;
    }

    /**
     * 冻结角色
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
            errorMsg = "冻结角色失败";
        }
        response.getWriter().write(errorMsg);

        return null;
    }

    /**
     * 解冻角色
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
            errorMsg = "解冻角色失败";
        }
        response.getWriter().write(errorMsg);

        return null;
    }

    /**
     * 冻结账号
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
            errorMsg = "冻结账号失败";
        }
        response.getWriter().write(errorMsg);

        return null;
    }

    /**
     * 解冻账号
     *
     * @return
     */
    public String delAccountBlack() throws Exception {
        int accountID = Integer.parseInt(request.getParameter("accountID"));
        String memo = request.getParameter("memo");

        boolean succ = DataService.deleteAccountBlack(gm.getId(), accountID, memo);
        if (!succ) {
            errorMsg = "解冻账号失败";
        }
        response.getWriter().write(errorMsg);

        return null;
    }

    /**
     * GM邮件列表
     */
    public String gmLetterList() throws Exception {
        gmLetterNumber = DataService.getGmLetterNumber();
        log.debug("gmLetterNumber = " + gmLetterNumber);
        if (request.getParameter("next_page") != null) {
            int next_page = Integer.parseInt(request.getParameter("next_page"));
            int per_page = Integer.parseInt(request.getParameter("per_page"));//在页面上设置
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
     * GM邮件信息
     *
     * @return
     */
    public String gmLetterInfo() throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        letter = DataService.getGmLetterByID(id);
        return SUCCESS;
    }

    /**
     * GM回复邮件
     *
     * @return
     */
    public String gmReplyLetter() throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        int serverID = Integer.parseInt(request.getParameter("serverID"));
        String content = request.getParameter("content");
        GmLetter letter = DataService.getGmLetterByID(id);
        if (letter.getStatus() == 1) {
            errorMsg = "此邮件已回复";

            return ERROR;
        } else {
            boolean succ = DataService.replyGmLetter(id, serverID, content, gm.getId());
            if (!succ) {
                errorMsg = "回复失败，稍后再试";
                return ERROR;
            }
        }
//    	response.getWriter().write(errorMsg);

        return SUCCESS;
    }

    /**
     * 添加公告
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
            errorMsg = "添加公告失败";
            return ERROR;
        }
//    	response.getWriter().write(errorMsg);
        return SUCCESS;
    }

    /**
     * 公告列表
     *
     * @return
     * @throws Exception
     */
    public String noticeList() throws Exception {
        gmNoticeNumber = DataService.getGmNoticeNumber();
        if (request.getParameter("next_page") != null) {
            int next_page = Integer.parseInt(request.getParameter("next_page"));
            int per_page = Integer.parseInt(request.getParameter("per_page"));//在页面上设置
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
     * 单个公告信息
     *
     * @return
     * @throws Exception
     */
    public String singleNotice() throws Exception {
        serverList = DataService.getServerInfoBeanList();
        int id = Integer.parseInt(request.getParameter("id"));
        notice = DataService.getNoticeById(id);
        if (notice == null) {
            errorMsg = "没有这个公告";
        }
        return SUCCESS;
    }

    /**
     * 删除单个公告
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
     * 修改公告
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
            errorMsg = "修改公告失败";
            return ERROR;
        }
//    	response.getWriter().write(errorMsg);
        return SUCCESS;
    }

    /**
     * 获取聊天内容
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
            content_x = "你已离线，请重新登陆！";
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
                content_x = "此服务器已关闭";
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
     * 给玩家添加物品，需要审核才能真正添加
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
            errorMsg = "Vui lòng đợi xem xét, người chơi có thể thêm vào người chơi sau khi đánh giá được thông qua";
        } else if (res == -1) {
            errorMsg = "Không có vật phẩm này, vui lòng nhập ID vật phẩm chính xác";
        } else if (res == 2) {
            errorMsg = "Không có người chơi này";
        } else {
            errorMsg = "Đã xảy ra lỗi. Vui lòng thử lại";
        }
        response.getWriter().write(errorMsg);
        return null;
    }

    /**
     * 审核给玩家添加的物品
     *
     * @return
     * @throws Exception
     */
    public String auditAddGoods() throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        int flag = Integer.parseInt(request.getParameter("flag"));// 1:通过  2:不通过
        int serverID = Integer.parseInt(request.getParameter("serverID"));
        int res = DataService.auditAddGoodsForPlayer(id, flag, gm.getId(), serverID);
        if (res == 0) {
            errorMsg = "0_给玩家添加物品成功";
        } else if (res == 1) {
            errorMsg = "1_出错了，没有找到这个玩家";
        } else if (res == 3) {
            errorMsg = "3_这条记录已经审核过了";
        } else if (res == 4) {
            errorMsg = "4_审核成功";
        } else if (res == 5) {
            errorMsg = "5_服务端出错了，设置为审核不通过";
        } else {
            errorMsg = "2_出错了";
        }
        response.getWriter().write(errorMsg);
        return null;
    }

    /**
     * 给玩家加点，需要审核才能真正添加
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
            errorMsg = "请等待审核，审核通过后才能给玩家添加";
        } else if (res == 2) {
            errorMsg = "没有这个玩家";
        } else {
            errorMsg = "出错了，请重试";
        }
        response.getWriter().write(errorMsg);
        return null;
    }

    /**
     * 审核给玩家加点
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
            errorMsg = "0_给玩家加点成功";
        } else if (res == 1) {
            errorMsg = "1_出错了，没有找到这个玩家";
        } else if (res == 3) {
            errorMsg = "3_这条记录已经审核过了";
        } else if (res == 4) {
            errorMsg = "4_审核成功";
        } else if (res == 5) {
            errorMsg = "5_服务端出错了，设置为审核不通过";
        } else {
            errorMsg = "2_出错了";
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
            errorMsg = "0_修改玩家属性成功";
        } else if (res == 1) {
            errorMsg = "1_出错了，没有找到这个玩家";
        } else if (res == 3) {
            errorMsg = "3_这条记录已经审核过了";
        } else if (res == 4) {
            errorMsg = "4_审核成功";
        } else if (res == 5) {
            errorMsg = "5_服务端出错了，设置为审核不通过";
        } else {
            errorMsg = "2_出错了";
        }
        response.getWriter().write(errorMsg);
        return null;
    }

    /**
     * 修改玩家属性：等级、金钱、爱情值
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
            errorMsg = "请等待审核，审核通过后才能给玩家添加";
        } else if (res == 0) {
            errorMsg = "出错了，没有找到这个玩家";
        } else {
            errorMsg = "出错了";
        }
        response.getWriter().write(errorMsg);
        return null;
    }

    /**
     * 添加菜单
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
     * 添加功能
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
     * 修改功能
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
     * 开始修改菜单
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
     * 修改菜单
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
     * 开始修改功能
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
     * 显示所有菜单功能
     *
     * @return
     * @throws Exception
     */
    public String menulist() throws Exception {
        menuList = DataService.getMenuList(true, false);
        return SUCCESS;

    }

    /**
     * 添加公告/活动
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
     * 获取公告/活动
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
            int per_page = Integer.parseInt(request.getParameter("per_page"));//在页面上设置
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
     * 获取单个公告/活动
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
     * 修改公告/活动
     *
     * @return
     * @throws Exception
     */
    public String updIndexNotice() throws Exception {
        int res = DataService.updIndexNotice(inotice);
        if (res == 1) {
            return SUCCESS;
        }
        errorMsg = "修改失败，稍后再试....";
        return ERROR;
    }

    /**
     * 删除公告/活动
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
     * 添加充值赠点活动
     *
     * @return
     * @throws Exception
     */
    public String addRechargePresentPoint() throws Exception {
        int res = DataService.addRechargePresentPoint(rpp);
        if (res == 1) {
            return SUCCESS;
        }
        errorMsg = "添加失败，稍后再试。。。";
        return ERROR;
    }

    /**
     * 修改充值 赠点活动
     *
     * @return
     * @throws Exception
     */
    public String updRechargePresentPoint() throws Exception {
        int res = DataService.updRechargePresentPoint(rpp);
        if (res == 1) {
            return SUCCESS;
        }
        errorMsg = "修改失败，稍后再试。。。";
        return ERROR;
    }

    /**
     * 获取单个充值赠点活动
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
     * 获取充值赠点活动列表
     *
     * @return
     * @throws Exception
     */
    public String getRechargePresentPointList() throws Exception {
        infoListNumber = DataService.getRechargePresentPointNumber();
        log.debug("getRechargePresentPointNumber = " + infoListNumber);
        if (request.getParameter("next_page") != null) {
            int next_page = Integer.parseInt(request.getParameter("next_page"));
            int per_page = Integer.parseInt(request.getParameter("per_page"));//在页面上设置
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
        errorMsg = "添加失败，请稍后再试。。。";
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
        errorMsg = "修改失败，请稍后再试。。。";
        return ERROR;
    }

    public String loadingTipList() throws Exception {
        infoListNumber = DataService.getLoadingTipNumber();
        log.debug("getLoadingTipList number  = " + infoListNumber);
        if (request.getParameter("next_page") != null) {
            int next_page = Integer.parseInt(request.getParameter("next_page"));
            int per_page = Integer.parseInt(request.getParameter("per_page"));//在页面上设置
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
     * 获得服务器经验系数列表
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
            errorMsg = "不能配置全部服务器，请单独配置";
            return ERROR;
        }
        boolean succ = false;
        try {
            if (request.getParameter("expMoudlus") == null || "".equals(request.getParameter("expMoudlus"))) {
                errorMsg = "请输入经验系数！";
                return ERROR;
            }
            float expMoudlus = Float.parseFloat(request.getParameter("expMoudlus"));
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            if (expMoudlus <= 0) {
                errorMsg = "请输入正确的系数！";
                return ERROR;
            }
            if (startTime == null || "".equals(startTime)) {
                errorMsg = "请输入开始时间！";
                return ERROR;
            }
            if (endTime == null || "".equals(endTime)) {
                errorMsg = "请输入结束时间！";
                return ERROR;
            }
            GmExpMoudlus gem = new GmExpMoudlus();
            gem.setServerId(serverID);
            gem.setMoudlus(expMoudlus + "");
            gem.setStartTime(startTime);
            gem.setEndTime(endTime);
            if (ServerExpMoudlusService.addExpMoudlus(gem)) {
                succ = DataService.updateServerExpMoudls(serverID, expMoudlus, startTime, endTime);
                errorMsg = "更新服务器端经验系数完毕";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!succ) {
            errorMsg = "更新服务器端经验系数失败";
            return ERROR;
        }
        return SUCCESS;
    }

    public String updateServerExpMoudlus() {
        int serverID = Integer.parseInt(request.getParameter("serverID"));
        if (serverID == 0) {
            errorMsg = "不能配置全部服务器，请单独配置";
            return ERROR;
        }
        boolean succ = false;
        try {
            if (request.getParameter("expMoudlus") == null || "".equals(request.getParameter("expMoudlus"))) {
                errorMsg = "请输入经验系数！";
                return ERROR;
            }
            float expMoudlus = Float.parseFloat(request.getParameter("expMoudlus"));
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            if (expMoudlus <= 0) {
                errorMsg = "请输入正确的系数！";
                return ERROR;
            }
            if (startTime == null || "".equals(startTime)) {
                errorMsg = "请输入开始时间！";
                return ERROR;
            }
            if (endTime == null || "".equals(endTime)) {
                errorMsg = "请输入结束时间！";
                return ERROR;
            }
            GmExpMoudlus gem = new GmExpMoudlus();
            gem.setServerId(serverID);
            gem.setMoudlus(expMoudlus + "");
            gem.setStartTime(startTime);
            gem.setEndTime(endTime);
            if (ServerExpMoudlusService.updateExpMoudlus(gem)) {
                succ = DataService.updateServerExpMoudls(serverID, expMoudlus, startTime, endTime);
                errorMsg = "更新服务器端经验系数完毕";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!succ) {
            errorMsg = "更新服务器端经验系数失败";
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
