package gm.db;

import gm.entities.*;
import gm.services.GameServerDownException;
import gm.services.GameService;
import gm.services.GameServiceListManager;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class DataService {

    private static Logger log = Logger.getLogger(DataService.class);

    /**
     * 解冻操作
     */
    private static final int DELETE_OPERATE_BLACK = 2;
    /**
     * 解禁禁言操作
     */
    private static final int DELETE_OPERATE_CHAT = 1;
    /**
     * 账号
     */
    private static final int OPERATE_ACCOUNT_TYPE = 1;
    /**
     * 角色
     */
    private static final int OPERATE_ROLE_TYPE = 2;

    private Map<Integer, GameService> gameServiceList;

    /**
     * 获取服务器Id
     *
     * @param serverId
     * @return
     */
    public static GmExpMoudlus getServerExpMoudlus(int serverId) {
        return GmDAO.getServerExpMoudlus(serverId);
    }

    /**
     * 添加
     *
     * @param gem
     * @return
     */
    public static boolean addServerExpMoudlus(GmExpMoudlus gem) {
        return GmDAO.addExpMoudlus(gem) > 0;
    }

    /**
     * 修改
     *
     * @param gem
     * @return
     */
    public static boolean updateServerExpMoudlus(GmExpMoudlus gem) {
        return GmDAO.updateExpMoudlus(gem) > 0;
    }

    /**
     * 获取IP
     *
     * @param request
     * @return
     */
    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;

    }

    public static int getLoadingTipNumber() {
        return GmDAO.getCountNumber("loading_tip");
    }

    public static List<LoadingTip> getLoadingTipList(int start, int offset) {
        return GmDAO.getLoadingTipList(start, offset);
    }

    public static void delLoadingTip(int id) {
        GmDAO.delLoadingTip(id);
    }

    public static int updLoadingTip(LoadingTip loadingTip) {
        return GmDAO.updLoadingTip(loadingTip);
    }

    public static LoadingTip getLoadingTip(int id) {
        return GmDAO.getLoadingTip(id);
    }

    public static int addLoadingTip(LoadingTip loadingTip) {
        return GmDAO.addLoadingTip(loadingTip);
    }

    /**
     * 获取充值赠点活动列表
     *
     * @param start
     * @param offset
     * @return
     */
    public static List<RechargePresentPoint> getRechargePresentPointList(int start, int offset) {
        return GmDAO.getRechargePresentPointList(start, offset);
    }

    /**
     * 获取充值赠点活动
     *
     * @param id
     * @return
     */
    public static RechargePresentPoint getRechargePresentPoint(int id) {
        return GmDAO.getRechargePresentPoint(id);
    }

    /**
     * 修改充值赠点活动
     *
     * @param rpp
     * @return
     */
    public static int updRechargePresentPoint(RechargePresentPoint rpp) {
        return GmDAO.updRechargePresentPoint(rpp);
    }

    /**
     * 添加充值赠点活动
     *
     * @param rpp
     * @return
     */
    public static int addRechargePresentPoint(RechargePresentPoint rpp) {
        return GmDAO.addRechargePresentPoint(rpp);
    }

    /**
     * 获取充值活动赠点总记录数
     *
     * @return
     */
    public static int getRechargePresentPointNumber() {
        return GmDAO.getCountNumber("recharge_present_point");
    }

    /**
     * 获取公告/活动的总数
     *
     * @return
     */
    public static int getIndexNoticeNumber() {
        return GmDAO.getCountNumber("index_notice");
    }

    /**
     * 添加公告/活动
     *
     * @param inotice
     * @return
     */
    public static int addIndexNotice(IndexNotice inotice) {
        return GmDAO.addIndexNotice(inotice);
    }

    /**
     * 根据ID获取公告/活动
     *
     * @param id
     * @return
     */
    public static IndexNotice getIndexNotice(int id) {
        return GmDAO.getIndexNotice(id);
    }

    /**
     * 获取公告/活动列表
     *
     * @param start
     * @param offset
     * @return
     */
    public static List<IndexNotice> getIndexNoticeList(int start, int offset) {
        List<IndexNotice> indexNoticeList = GmDAO.getIndexNoticeList(start, offset);
        for (IndexNotice inotice : indexNoticeList) {
            inotice.setServerName(getServerName(inotice.getServerID()));
        }
        return indexNoticeList;
    }

    /**
     * 修改公告/活动
     *
     * @param inotice
     * @return
     */
    public static int updIndexNotice(IndexNotice inotice) {
        return GmDAO.updIndexNotice(inotice);
    }

    /**
     * 删除一个公告/活动
     *
     * @param id
     * @return
     */
    public static int delIndexNotice(int id) {
        return GmDAO.delIndexNotice(id);
    }

    /**
     * 给玩家添加物品，先记录到数据库里，等审核后再给玩家添加
     *
     * @param serverID
     * @param type
     * @param content
     * @param memo
     * @param goodsID
     * @param number
     * @return -1 没有找到物品 1:成功 2:没找到玩家错误 0:错误
     */
    public static int addGoodsForPlayer(int gmID, int serverID, int type, String content, String memo, int goodsID, int number) throws Exception {
        int res = 0;
        GameService gmService = GameServiceListManager.getInstance().getGameServiceList().get(serverID);
        PlayerInfo player = getPlayerInfo(serverID, type, content, gmService);
        if (player != null) {
            String goodsName = getGoodsName(goodsID);
            if (goodsName != null && !goodsName.equals("0")) {
                res = GmDAO.addGoodsForPlayer(gmID, serverID, player.getAccountID(), player.getUserID(), player.getName(), goodsID, goodsName, number, 0, memo);
            } else {
                res = -1;
            }
        } else {
            res = 2;
        }
        return res;
    }

    /**
     * 审核给玩家添加的物品
     *
     * @param id 物品记录id
     * @param flag 1:通过，给玩家添加物品 2:不通过
     * @param gmID 审核的GM
     * @return 0:成功 1找不到玩家错误 2:其它错误 3:已经审核过 4:审核不通过成功
     */
    public static int auditAddGoodsForPlayer(int id, int flag, int gmID, int serverID) throws GameServerDownException {
        int res = 2;
        AddGoodsInfo info = GmDAO.getInfoByID(id, 1);
        int r = GmDAO.auditAddGoodsForPlayer(id, flag, gmID);
        if (r == 1) {
            if (info != null) {
                if (info.getFlag() == 0) {
                    if (flag == 1) {
                        GameService gmService = GameServiceListManager.getInstance().getGameServiceList().get(serverID);
                        res = gmService.addGoodsForPlayer(info.getRoleID(), info.getGoodsID(), info.getNumber());

                        log.debug("审核给玩家添加的物品 flag=" + flag + ",r=" + 1 + ",res=" + res);
                        if (res != 0) { //服务端可能出错了，重新设置为审核不通过
                            GmDAO.auditAddGoodsForPlayer(id, 2, gmID);
                            res = 5;
                        }
                    } else {
                        res = 4;
                    }
                } else {
                    res = 3;
                }
            } else {
                res = 2;
            }
        }
        return res;
    }

    /**
     * 给玩家加点
     *
     * @param gmID
     * @param serverID
     * @param type
     * @param content
     * @param memo
     * @param point
     * @return 1:成功 2:没找到玩家错误 0:错误
     * @throws Exception
     */
    public static int addPointForPlayer(int gmID, int serverID, int type, String content, String memo, int point) throws Exception {
        int res = 0;
        GameService gmService = GameServiceListManager.getInstance().getGameServiceList().get(serverID);
        PlayerInfo player = getPlayerInfo(serverID, type, content, gmService);
        if (player != null) {
            res = GmDAO.addPointForPlayer(gmID, serverID, player.getAccountID(), player.getUserID(), player.getName(), point, 0, memo);
        } else {
            res = 2;
        }
        return res;
    }

    /**
     * 审核给玩家加点
     *
     * @param id
     * @param flag 1:通过，给玩家加点 2:不通过
     * @param gmID
     * @param serverID
     * @return 0:成功 1找不到玩家错误 2:其它错误 3:已经审核过 4:审核不通过成功
     */
    public static int auditAddPointForPlayer(int id, int flag, int gmID, int serverID) throws GameServerDownException {
        int res = 2;
        AddGoodsInfo info = GmDAO.getInfoByID(id, 2);
        int r = GmDAO.auditAddPointForPlayer(id, flag, gmID);
        if (r == 1) {
            if (info != null) {
                if (info.getFlag() == 0) {
                    if (flag == 1) {
                        GameService gmService = GameServiceListManager.getInstance().getGameServiceList().get(serverID);
                        res = gmService.addPointForPlayer(info.getRoleID(), info.getGoodsID());
                        log.debug("审核给玩家加点 flag=" + flag + ",r=" + r + ",res=" + res);
                        if (res != 0) { //服务端出错了，重新设置为审核不通过
                            GmDAO.auditAddPointForPlayer(id, 2, gmID);
                            res = 5;
                        }
                    } else {
                        res = 4;
                    }
                } else {
                    res = 3;
                }
            } else {
                res = 2;
            }
        }
        return res;
    }

    /**
     * 审核修改玩家的金钱、等级、爱情值、技能点
     *
     * @param id
     * @param flag
     * @param gmID
     * @param serverID
     * @return
     * @throws GameServerDownException
     */
    public static int auditModifyPlayerInfo(int id, int flag, int gmID, int serverID) throws GameServerDownException {
        int res = 2;
        AddGoodsInfo info = GmDAO.getInfoByID(id, 3);
        log.debug("audit playerinfo id=" + id + ",flag=" + info.getFlag());
        int r = GmDAO.auditModifyPlayerInfo(id, flag, gmID);
        if (r == 1) {
            if (info != null) {
                if (info.getFlag() == 0) {
                    if (flag == 1) {
                        GameService gmService = GameServiceListManager.getInstance().getGameServiceList().get(serverID);
                        res = gmService.modifyPlayerInfo(info.getRoleID(), info.getMoney(), info.getLevel(), info.getLoverValue(), info.getSkillPoint());
                        log.debug("审核修改玩家属性 flag=" + flag + ",r=" + r + ",res=" + res);
                        if (res != 0) { //服务端出错了，重新设置为审核不通过
                            GmDAO.auditModifyPlayerInfo(id, 2, gmID);
                            res = 5;
                        }
                    } else {
                        res = 4;
                    }
                } else {
                    res = 3;
                }
            } else {
                res = 2;
            }
        }
        return res;
    }

    /**
     * 修改玩家的金钱、等级、爱情值
     *
     * @param gmID
     * @param serverID
     * @param type
     * @param content
     * @param memo
     * @param money
     * @param level
     * @param loverValue
     * @return 1:成功 0找不到玩家错误 2:其它错误
     */
    public static int modifyPlayerInfo(int gmID, int serverID, int type, String content, String memo, int money, int level, int loverValue, int skillPoint) throws Exception {
        GameService gmService = GameServiceListManager.getInstance().getGameServiceList().get(serverID);
        PlayerInfo player = getPlayerInfo(serverID, type, content, gmService);
        int res = 2;
        if (player != null) {
            res = GmDAO.modifyPayerInfo(gmID, serverID, player.getAccountID(), player.getUserID(), player.getName(), level, money, loverValue, skillPoint, memo);
//            res = gmService.modifyPlayerInfo(player.getUserID(),money,level,loverValue,skillPoint);
        } else {
            res = 0;
        }
        return res;
    }

    public static int getAddInfoListNumber(int type, int flag) {
        return GmDAO.getAddInfoListNumber(type, flag);
    }

    public static List<AddGoodsInfo> getAddInfoList(int type, int flag, int start, int offset) {
        return GmDAO.getAddGoodsInfoList(type, flag, start, offset);
    }

    /**
     * 记录GM登录日志
     *
     * @param gmID
     * @param ip
     */
    public static void addGmLoginLog(int gmID, String ip) {
        GmDAO.addGmLoginLog(gmID, ip);
    }

    /**
     * 获取GM列表
     *
     * @return
     */
    public static List<GmUser> getGmUserList() {
        return GmDAO.getGmList();
    }

    /**
     * 获取Gm
     *
     * @param name
     * @param password
     * @return
     */
    public static GmUser getGm(String name, String password) {
        return GmDAO.getGm(name, password);
    }

    /**
     * 添加功能
     *
     * @param name
     * @param url
     * @param sequence
     * @param menuID 所属菜单ID
     * @param level 使用等级
     * @param show 是否显示
     */
    public static void addFun(String name, String url, int sequence, int menuID, int level, int show, int gmID) {
        GmDAO.addFun(name, url, sequence, menuID, show, level, gmID);
    }

    /**
     * 修改功能
     *
     * @param id
     * @param name
     * @param url
     * @param sequence
     * @param menuID
     * @param level
     * @param show
     * @param gmID
     * @return
     */
    public static int updFun(int id, String name, String url, int sequence, int menuID, int level, int show, int gmID) {
        return GmDAO.updFun(id, name, url, sequence, menuID, show, level, gmID);
    }

    /**
     * 添加菜单
     *
     * @param name
     * @param sequence
     * @param show
     */
    public static void addMenu(String name, int sequence, int show, int gmID) {
        GmDAO.addMenu(name, sequence, show, gmID);
    }

    /**
     * 修改菜单
     *
     * @param id
     * @param name
     * @param sequence
     * @param show
     * @param gmID
     * @return
     */
    public static int updMenu(int id, String name, int sequence, int show, int gmID) {
        return GmDAO.updMenu(id, name, sequence, show, gmID);
    }

    /**
     * 获取菜单功能列表
     *
     * @return
     */
    public static List<Menu> getMenuList(boolean loadFunList, boolean isOnlyShow) {
        return GmDAO.getMenuList(loadFunList, isOnlyShow);
    }

    /**
     * 获取菜单
     *
     * @param id
     * @return
     */
    public static Menu getMenu(int id) {
        return GmDAO.getMenu(id);
    }

    /**
     * 获取功能FUN
     *
     * @param id
     * @return
     */
    public static Fun getFun(int id) {
        return GmDAO.getFun(id);
    }

    /**
     * 添加GM
     *
     * @param name
     * @param password
     * @return
     */
    public static boolean addGmUser(String name, String password, int groupID, int level) {
        return GmDAO.addGm(name, password, groupID, level);
    }

    /**
     * 检查GM名称是否已存在
     *
     * @param name
     * @return
     */
    public static boolean checkGmUserExists(String name) {
        return GmDAO.isExists(name);
    }

    /**
     * GM修改密码
     *
     * @param gmID
     * @param newPwd
     * @return
     */
    public static boolean updatePassword(int gmID, String newPwd) {
        return GmDAO.updatePwd(gmID, newPwd);
    }

    /**
     * 获取玩家信息
     *
     * @param serverID
     * @param type
     * @param name
     * @return
     * @throws Exception
     */
    public static PlayerInfo getPlayerInfo(int serverID, int type, String name) throws Exception {
        GameService gmService = GameServiceListManager.getInstance().getGameServiceList().get(serverID);
        PlayerInfo player = getPlayerInfo(serverID, type, name, gmService);
        if (player != null) {
            String accountName = GmDAO.getAccountName(player.getAccountID());
            boolean isChatBlack = GmDAO.getChatBlack(serverID, player.getAccountID(), player.getUserID()) == 1;
            boolean isRoleBlack = GmDAO.getRoleBlack(serverID, player.getAccountID(), player.getUserID()) == 1;
            log.debug("get player info accountName= " + accountName + ",isonline=" + player.isOnline());
            player.setAccountName(accountName);
            player.setChatBlack(isChatBlack);
            player.setRoleBlack(isRoleBlack);
        }
        return player;
    }

    /**
     * 瞬移
     *
     * @param serverID
     * @param gmID
     * @param type
     * @param name
     * @param mapID
     * @param memo
     * @return -1:获取玩家信息失败(可能输入有误或服务器关闭) 0:成功 1:玩家不在线 2:地图ID错误
     */
    public static int addBlink(int serverID, int gmID, int type, String name, int mapID, String memo) throws Exception {
        GameService gmService = GameServiceListManager.getInstance().getGameServiceList().get(serverID);

        PlayerInfo player = getPlayerInfo(serverID, type, name, gmService);
        if (player != null) {
            int result = gmService.blink(mapID, player.getUserID());
            log.debug("blink result = " + result);
            if (result == 0) {
                String mapName = GameServiceListManager.getInstance().getGameMapList().get(mapID);
                GmDAO.addBlink(serverID, gmID, type, name, mapID, mapName, memo);
            }

            return result;
        }
        return -1;
    }

    /**
     * 此方法里的服务器信息只能使用ID和NAME
     */
    public static List<ServerInfoBean> getServerInfoBeanList() {
        List<ServerInfoBean> slist = new ArrayList<ServerInfoBean>();
        ServerInfoBean sib = new ServerInfoBean(0, "所有分区");
        slist.add(sib);
        slist.addAll(getServerInfoList());

        return slist;
    }

    /**
     * 查询所有分区服务器名称和在线人数
     *
     * @return
     */
    public static List<ServerInfoBean> getServerInfoList() {
        List<ServerInfoBean> slist = new ArrayList<ServerInfoBean>();
        Map<Integer, GameService> gameServiceList = GameServiceListManager.getInstance().getGameServiceList();
        for (GameService gameService : gameServiceList.values()) {
            ServerInfoBean bean = new ServerInfoBean();
            bean.setServerID(gameService.getServerID());
            bean.setServerName(gameService.getServerName());
            bean.setStatus(gameService.isRunning());
            if (gameService.isRunning()) {
                bean.setOnLinePlayerNum(gameService.getOnlinePlayerNumber());
            }
            System.out.println("serverid=" + bean.getServerID() + ",servername=" + bean.getServerName());
//			log.debug("serverid="+bean.getServerID()+",servername="+bean.getServerName());

            slist.add(bean);
        }
        return slist;
    }

    public static String getServerName(int serverID) {
        String serverName = "";
        if (serverID == 0) {
            serverName = "所有分区";
        }
        Map<Integer, GameService> gameServiceList = GameServiceListManager.getInstance().getGameServiceList();
        for (GameService gameService : gameServiceList.values()) {
            if (gameService.getServerID() == serverID) {
                serverName = gameService.getServerName();
                break;
            }
        }
        return serverName;
    }

    /**
     * 修改公告
     *
     * @param id
     * @param serverID
     * @param gmID
     * @param title
     * @param content
     * @param start_time
     * @param end_time
     * @param intervalTime
     * @param times
     * @return
     */
    public static boolean updateGmNotice(int id, int serverID, int gmID, String title, String content,
            String start_time, String end_time, int intervalTime, int times) throws GameServerDownException {
        int res = GmDAO.updateNotice(id, serverID, gmID, title, content, start_time, end_time, intervalTime, times);

        if (res == 1) {
            if (serverID == 0) {
                Map<Integer, GameService> gameServiceMap = GameServiceListManager.getInstance().getGameServiceList();
                for (GameService gameService : gameServiceMap.values()) {
                    if (gameService.isRunning()) {
                        for (int i = 0; i < times; i++) {
                            gameService.sendNoticeGM("系统", content); //先发送一次
                        }
                    }
                }
            } else {
                GameService gmService = GameServiceListManager.getInstance().getGameServiceList().get(serverID);
                if (gmService.isRunning()) {
                    for (int i = 0; i < times; i++) {
                        gmService.sendNoticeGM("系统", content); //先发送一次
                    }
                }
            }
        }
        return res == 1;
    }

    /**
     * 删除单个公告
     *
     * @param id
     */
    public static void delNotice(int id) {
        GmDAO.delNotice(id);
    }

    /**
     * 获取单个公告信息
     *
     * @param id
     * @return
     */
    public static GmNotice getNoticeById(int id) {
        return GmDAO.getNoticeById(id);
    }

    /**
     * 添加公告
     *
     * @param serverID
     * @param gmID
     * @param title
     * @param content
     * @param start_time
     * @param end_time
     * @param intervalTime
     * @param times
     * @return
     */
    public static boolean addGmNotice(int serverID, int gmID, String title, String content,
            String start_time, String end_time, int intervalTime, int times) throws GameServerDownException {
        boolean succ = GmDAO.addGmNotice(serverID, gmID, title, content, start_time, end_time, intervalTime, times - 1);
        if (succ) {
            if (serverID == 0) {
                Map<Integer, GameService> gameServiceMap = GameServiceListManager.getInstance().getGameServiceList();
                for (GameService gameService : gameServiceMap.values()) {
                    if (gameService.isRunning()) {
                        for (int i = 0; i < times; i++) {
                            gameService.sendNoticeGM("系统", content); //先发送一次
                        }
                    }
                }
            } else {
                GameService gmService = GameServiceListManager.getInstance().getGameServiceList().get(serverID);
                if (gmService.isRunning()) {
                    for (int i = 0; i < times; i++) {
                        gmService.sendNoticeGM("系统", content); //先发送一次
                    }
                }
            }
        }
        return succ;
    }

    /**
     * 获取公告列表
     *
     * @return
     */
    public static int getGmNoticeNumber() {
        return GmDAO.getCountNumber("gm_notice");
    }

    /**
     * 获取公告列表
     *
     * @param start
     * @param offset
     * @return
     */
    public static List<GmNotice> getGmNoticeList(int start, int offset) {
        return GmDAO.getGmNoticeList(start, offset);
    }

    /**
     * 获取GM邮件数量
     *
     * @return
     */
    public static int getGmLetterNumber() {
        return GmDAO.getCountNumber("gm_letter");
    }

    /**
     * 获取GM邮件
     *
     * @param start
     * @param offset 增量
     * @return
     */
    public static List<GmLetter> getGmLetterList(int start, int offset) {
        return GmDAO.getGmLetterList(start, offset);
    }

    /**
     * 获取单个GM邮件
     *
     * @param id
     * @return
     */
    public static GmLetter getGmLetterByID(int id) {
        return GmDAO.getGmLetterById(id);
    }

    /**
     * 获取这个gm邮件，发送者的最近n条信息
     *
     * @param id
     * @return
     */
    public static List<GmLetter> getGmletterList(int serverid, int id, int n) {
        return GmDAO.getGmLetterByUserId(serverid, id, n);
    }

    /**
     * GM回复邮件
     *
     * @param id
     * @param content
     * @param gmID
     * @return
     */
    public static boolean replyGmLetter(int id, int serverID, String content, int gmID) {
        boolean succ = GmDAO.replyGmLetter(id, content, gmID) == 1;
        if (succ) {
            GameService gmService = GameServiceListManager.getInstance().getGameServiceList().get(serverID);
            if (gmService.isRunning()) {
                try {
                    gmService.GMReplyLetter(id);
                } catch (GameServerDownException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return succ;
    }

    /**
     * 踢玩家下线，并记录log
     *
     * @param serverID
     * @param type
     * @param gmID
     * @param name
     * @param memo
     * @return
     */
    public static boolean resetPlayerStatus(int serverID, int type, int gmID, String name, String memo) {
        try {
            GameService gmService = GameServiceListManager.getInstance().getGameServiceList().get(serverID);
            PlayerInfo player = getPlayerInfo(serverID, type, name, gmService);
            if (player != null) {
                if (gmService.isRunning()) {
                    gmService.resetPlayerStatus(player.getAccountID());
                    addAccountResetLog(serverID, gmID, player.getAccountID(), player.getUserID(), player.getName(), memo);
                    return true;
                }
            } else {
                log.error("踢玩家下线 获取玩家数据出错");
            }
        } catch (GameServerDownException e) {
            log.error("踢玩家下线，并记录log error : ", e);
            return false;
        }
        return false;
    }

    /**
     * 踢玩家下线，并记录log
     *
     * @param serverID
     * @param type
     * @param gmID
     * @param name
     * @param memo
     * @return
     */
    public static boolean resetPlayers(int serverID, int gmID) {
        try {
            GameService gmService = GameServiceListManager.getInstance().getGameServiceList().get(serverID);
            gmService.resetPlayers();
            log.error("踢所有玩家下线操作完毕gmID=" + gmID);
            return true;
        } catch (Exception e) {
            log.error("踢所有玩家下线，并记录log error : gmID=" + gmID, e);
        }
        return false;
    }

    /**
     * 更新服务器经验系数
     *
     * @param serverID
     * @param expMoudlus
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean updateServerExpMoudls(int serverID, float expMoudlus, String startTime, String endTime) {
        try {
            GameService gmService = GameServiceListManager.getInstance().getGameServiceList().get(serverID);
            return gmService.updateServerExpMoudlus(expMoudlus, startTime, endTime);
        } catch (Exception e) {
            log.error("修改服务器经验系数", e);
        }
        return false;
    }

    /**
     * 禁言
     *
     * @param serverID
     * @param type
     * @param name
     * @param keepTime
     * @param endTime 格式"2011-01-01 00:00:00"
     * @param memo
     * @return
     */
    public static boolean setPlayerChatBlack(int serverID, int gmID, int type, String name, int keepTime, String endTime, String memo) {
        try {
            GameService gmService = GameServiceListManager.getInstance().getGameServiceList().get(serverID);
            if (gmService.isRunning()) {
                PlayerInfo player = getPlayerInfo(serverID, type, name, gmService);
                if (player != null) {
                    return GmDAO.setPlayerChatBlack(serverID, player.getAccountID(), player.getUserID(), player.getName(), keepTime, endTime, memo, gmID);
                } else {
                    log.error("禁言操作 获取玩家数据出错");
                }
            }
        } catch (GameServerDownException e) {
            log.error("禁言 error : ", e);
        }

        return false;
    }

    /**
     * 解禁禁言
     *
     * @param serverID
     * @param gmID
     * @param type
     * @param content
     * @param memo
     * @return
     */
    public static boolean deleteChatBlack(int serverID, int gmID, int type, String content, String memo) {
        try {
            GameService gmService = GameServiceListManager.getInstance().getGameServiceList().get(serverID);
            if (gmService.isRunning()) {
                PlayerInfo player = getPlayerInfo(serverID, type, content, gmService);
                if (player != null) {
                    boolean deletesucc = GmDAO.deleteChatBlack(serverID, player.getAccountID(), player.getUserID());
                    if (deletesucc) {
                        addDeleteBlackLog(serverID, DELETE_OPERATE_CHAT, OPERATE_ROLE_TYPE, gmID, player.getAccountID(), player.getUserID(), player.getName(), memo);
                        return true;
                    }
                } else {
                    log.error("解禁言 获取玩家数据出错");
                }
            }
        } catch (GameServerDownException e) {
            log.error("解禁禁言 GameServerDownException error : ", e);
        } catch (Exception e) {
            log.error("解禁禁言  error : ", e);
        }

        return false;
    }

    /**
     * 冻结角色
     *
     * @param serverID
     * @param gmID
     * @param type
     * @param name
     * @param keepTime
     * @param endTime 格式"2011-01-01 00:00:00"
     * @param memo
     * @return
     */
    public static boolean setPlayerBlack(int serverID, int gmID, int type, String name, int keepTime, String endTime, String memo) {
        try {
            GameService gmService = GameServiceListManager.getInstance().getGameServiceList().get(serverID);
            if (gmService.isRunning()) {
                log.debug("set player black server is running..");
                PlayerInfo player = getPlayerInfo(serverID, type, name, gmService);
                if (player != null) {
                    GmDAO.setPlayerBlack(serverID, player.getAccountID(), player.getUserID(), player.getName(), keepTime, endTime, memo, gmID);
                    return setAccountBlack(player.getAccountID(), gmID, keepTime, endTime, memo);
                } else {
                    log.error("冻结角色 获取玩家数据出错");
                }
            }
        } catch (GameServerDownException e) {
            log.error("禁言 error : ", e);
        }

        return false;
    }

    /**
     * 冻结账号
     *
     * @param accountID
     * @param gmID
     * @param keepTime
     * @param endTime 格式"2011-01-01 00:00:00"
     * @param memo
     * @return
     */
    public static boolean setAccountBlack(int accountID, int gmID, int keepTime, String endTime, String memo) {
        String username = GmDAO.getAccountName(accountID);
        if (username != null) {
            return GmDAO.setAccountBlack(accountID, gmID, username, keepTime, endTime, memo);
        }
        return false;
    }

    /**
     * 解冻角色
     *
     * @param serverID
     * @param gmID
     * @param type
     * @param content
     * @param memo
     * @return
     */
    public static boolean deleteRoleBlack(int serverID, int gmID, int type, String content, String memo) {
        try {
            GameService gmService = GameServiceListManager.getInstance().getGameServiceList().get(serverID);
            if (gmService.isRunning()) {
                PlayerInfo player = getPlayerInfo(serverID, type, content, gmService);
                if (player != null) {
                    boolean deletesucc = GmDAO.deleteRoleBlack(serverID, player.getAccountID(), player.getUserID());
                    if (deletesucc) {
                        deleteAccountBlack(gmID, player.getAccountID(), memo);
                        addDeleteBlackLog(serverID, DELETE_OPERATE_BLACK, OPERATE_ROLE_TYPE, gmID, player.getAccountID(), player.getUserID(), player.getName(), memo);
                        return true;
                    }
                } else {
                    log.error("解冻角色 获取玩家数据出错");
                }
            }
        } catch (GameServerDownException e) {
            log.error("解冻角色 GameServerDownException error : ", e);
        } catch (Exception e) {
            log.error("解冻角色  error : ", e);
        }

        return false;
    }

    /**
     * 解冻账号
     *
     * @param gmID
     * @param accountID
     * @param memo
     * @return
     */
    public static boolean deleteAccountBlack(int gmID, int accountID, String memo) {
        boolean delsucc = GmDAO.deleteAccountBlack(accountID);
        if (delsucc) {
            addDeleteBlackLog(-1, DELETE_OPERATE_BLACK, OPERATE_ACCOUNT_TYPE, gmID, accountID, 0, "", memo);
            return true;
        }
        return false;
    }

    /**
     * 获取物品名称
     *
     * @param goodsID
     * @return
     * @throws GameServerDownException
     */
    public static String getGoodsName(int goodsID) throws GameServerDownException {
        String goodsName = null;
        for (GameService gmService : GameServiceListManager.getInstance().getGameServiceList().values()) {
            if (gmService.isRunning()) {
                goodsName = gmService.getGoodsName(goodsID);
                if (!goodsName.equals("0")) {
                    break;
                }
            }
        }
        return goodsName;
    }

    /**
     * 获取玩家信息 格式:
     * [userID,nickname,accountID,sex,vocation,clan,level,exp,money,online,lastLoginTime,lastLogoutTime]
     *
     * @param serverID
     * @param type
     * @param name
     * @return
     * @throws GameServerDownException
     */
    private static PlayerInfo getPlayerInfo(int serverID, int type, String name, GameService gmService) throws GameServerDownException {
        String[] playerInfos = null;
        PlayerInfo player = null;
        if (gmService.isRunning()) {
            String playerInfo = "";
            if (type == 1) {
                playerInfo = gmService.getPlayerInfoByUserID(Integer.parseInt(name));
            }
            if (type == 2) {
                playerInfo = gmService.getPlayerInfoByNickname(name);
            }
            log.debug("get palyer : " + name + "# playerInfo=" + playerInfo);
            if (playerInfo != null && playerInfo.trim().length() > 0 && !playerInfo.equals("-1")) {
                player = new PlayerInfo();
                playerInfos = playerInfo.split(",");
                player.setUserID(Integer.parseInt(playerInfos[0]));
                player.setName(playerInfos[1]);
                player.setAccountID(Integer.parseInt(playerInfos[2]));
                player.setSex(playerInfos[3]);
                player.setVocation(playerInfos[4]);
                player.setClan(playerInfos[5]);
                player.setLevel(Integer.parseInt(playerInfos[6]));
                player.setExp(Integer.parseInt(playerInfos[7]));
                player.setMoney(Integer.parseInt(playerInfos[8]));
                player.setOnline(Integer.parseInt(playerInfos[9]) == 1);
                player.setLastLoginTime(new Timestamp(Long.parseLong(playerInfos[10])));
                player.setLastLogoutTime(new Timestamp(Long.parseLong(playerInfos[11])));
                player.setServerID(serverID);
                player.setWhereName(playerInfos[12]);
                player.setPhone(playerInfos[13]);
                player.setPublisher(playerInfos[14]);

            }
        }
        return player;
    }

    /**
     * 添加踢玩家下线log
     *
     * @param serverID
     * @param accountID
     * @param roleID
     * @param roleName
     * @param memo
     * @return
     * @throws GameServerDownException
     */
    private static boolean addAccountResetLog(int serverID, int gmID, int accountID, int roleID, String roleName, String memo) {
        return GmDAO.addAccountResetLog(serverID, gmID, accountID, roleID, roleName, memo);
    }

    /**
     * 解禁禁言或解冻账号或角色LOG
     *
     * @param serverID
     * @param operateType 1:解禁禁言 2:解冻
     * @param type 1:账号 2:角色
     * @param gmID
     * @param accountID
     * @param roleID
     * @param roleName
     * @param memo
     * @return
     */
    private static boolean addDeleteBlackLog(int serverID, int operateType, int type, int gmID, int accountID, int roleID, String roleName, String memo) {
        return GmDAO.addDeleteBlackLog(serverID, operateType, gmID, accountID, roleID, roleName, memo, type);
    }
}
