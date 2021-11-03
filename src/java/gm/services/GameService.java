package gm.services;

import gm.db.GmDAO;
import org.apache.log4j.Logger;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import hero.login.rmi.IYOYOLoginRMI;

/**
 * Created by IntelliJ IDEA. User: jiaodongjie Date: 11-2-13 Time: 下午2:06
 */
public class GameService {
    private static Logger log = Logger.getLogger(GameService.class);
    private int serverID;
    private String serverName;

    /**
     * 游戏服务器运行状态
     */
    private boolean statusOfRunning;
    /**
     * 游戏服务器RMI链接
     */
    private String rmiURL;

    /**
     * 游戏服务器RMI对象
     */
    private IYOYOLoginRMI rmi;

    /**
     * 在线玩家数量
     */
    private int onlinePlayerNumber = 0;

    public GameService(int serverID, String serverName, String rmiURL) {
        this.serverID = serverID;
        this.serverName = serverName;
        this.rmiURL = rmiURL;
        lookupGameService();
    }

    /**
     * 更新服务器经验系数
     *
     * @throws GameServerDownException
     */
    public boolean updateServerExpMoudlus(float moudlus, String startTime, String endTime)
            throws GameServerDownException, RemoteException {
        if (rmi == null) {
            throw new GameServerDownException();
        }
        return rmi.updateServerExpMoudlus(moudlus, startTime, endTime);
    }

    /**
     * 加载游戏地图列表 只用加载一个游戏服务器的即可
     */
    public Map<Integer, String> loadGameMapList() {
        Map<Integer, String> gameMapList = new HashMap<Integer, String>();
        try {
            String mapslist = rmi.getGameMapList();
            if (mapslist.length() > 0) {
                String[] mapsInfo = mapslist.split(",");
                String[] m;
                for (String map : mapsInfo) {
                    m = map.split("-");
                    gameMapList.put(Integer.parseInt(m[0]), m[1]);
                }
            }
        } catch (RemoteException e) {
            log.error("load game map list error : " + getServerID(), e);
            e.printStackTrace();
        }
        return gameMapList;
    }

    /**
     * 获取RMI客户端对象
     *
     * @return
     */
    public void lookupGameService() {
        try {
            rmi = (IYOYOLoginRMI) Naming.lookup(rmiURL);

            if (null != rmi) {
                statusOfRunning = true;
                onlinePlayerNumber = rmi.getOnlinePlayerNumber();
            } else {
                statusOfRunning = false;
                onlinePlayerNumber = 0;
            }
        } catch (Exception e) {
            log.error("Loop up RMI error : " + e.getMessage());
            rmi = null;
            statusOfRunning = false;
            onlinePlayerNumber = 0;
        }
    }

    public int getOnlinePlayerNumber() {
        try {
            onlinePlayerNumber = rmi.getOnlinePlayerNumber();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return onlinePlayerNumber;
    }

    public boolean isRunning() {
        return statusOfRunning;
    }

    /**
     * 检测游戏服务器是否正常运行
     *
     * @return 游戏服务器是否正常运行
     */
    public void checkServerStatus() {
        if (rmi == null) {
            statusOfRunning = false;
            onlinePlayerNumber = 0;
        }

        try {
            rmi.checkStatusOfRun();
            statusOfRunning = true;
            onlinePlayerNumber = rmi.getOnlinePlayerNumber();
        } catch (RemoteException re) {
            rmi = null;
            statusOfRunning = false;
            onlinePlayerNumber = 0;
        }
    }

    /**
     * 将帐号下的角色状态置为下线
     *
     * @param _accountID 帐号ID
     * @return 是否完成状态重置
     * @throws GameServerDownException
     */
    public boolean resetPlayerStatus(int _accountID) throws GameServerDownException {
        if (rmi == null) {
            throw new GameServerDownException();
        }

        try {
            return rmi.resetPlayersStatus(_accountID);
        } catch (RemoteException re) {
            throw new GameServerDownException();
        }
    }

    /**
     * 踢所有玩家下线下线
     *
     * @throws GameServerDownException
     */
    public void resetPlayers() throws GameServerDownException, RemoteException {
        if (rmi == null) {
            throw new GameServerDownException();
        }
        rmi.resetPlayers();
    }

    /**
     * 通知游戏服务器创建新的会话，在选择角色进入游戏时调用
     *
     * @param _userID    角色ID
     * @param _accountID 帐号ID
     * @return
     */
    public int createSession(int _userID, int _accountID) throws GameServerDownException {
        if (rmi == null) {
            throw new GameServerDownException();
        }

        try {
            return rmi.createSessionID(_userID, _accountID);
        } catch (RemoteException re) {
            throw new GameServerDownException();
        }
    }

    /**
     * 通过userID获取玩家信息，用于GM查询
     * 
     * @param _userID
     * @return
     * @throws GameServerDownException
     */
    public String getPlayerInfoByUserID(int _userID) throws GameServerDownException {
        if (rmi == null) {
            throw new GameServerDownException();
        }
        try {

            return rmi.getPlayerInfoByUserID(_userID);

        } catch (RemoteException re) {
            throw new GameServerDownException();
        }
    }

    /**
     * 通过昵称获取玩家信息，用于GM查询
     * 
     * @param nickname
     * @return
     * @throws GameServerDownException
     */
    public String getPlayerInfoByNickname(String nickname) throws GameServerDownException {
        if (rmi == null) {
            throw new GameServerDownException();
        }
        try {

            return rmi.getPlayerInfoByNickname(nickname);

        } catch (RemoteException re) {
            throw new GameServerDownException();
        }
    }

    /**
     * 根据 userID 冻结角色
     * 
     * @param _userID
     * @param keepTime
     * @param startTime
     * @param endTime
     * @param memo
     * @return
     * @throws GameServerDownException
     */
    /*
     * public boolean setPlayerUserIDBlack(int _userID,int keepTime,String
     * startTime,String endTime,String memo) throws GameServerDownException{ if(rmi
     * == null){ throw new GameServerDownException(); } try{
     * 
     * return rmi.setPlayerUserIDBlack(_userID,keepTime,startTime,endTime,memo);
     * 
     * }catch (RemoteException re) { throw new GameServerDownException(); } }
     */

    /**
     * 根据 accountID 冻结账号
     * 
     * @param _accountID
     * @param keepTime
     * @param startTime
     * @param endTime
     * @param memo
     * @return
     * @throws GameServerDownException
     */
    /*
     * public boolean setPlayerAccountIDBlack(int _accountID,int keepTime,String
     * startTime,String endTime,String memo) throws GameServerDownException{ if(rmi
     * == null){ throw new GameServerDownException(); } try{
     * 
     * return
     * rmi.setPlayerAccountIDBlack(_accountID,keepTime,startTime,endTime,memo);
     * 
     * }catch (RemoteException re) { throw new GameServerDownException(); } }
     */

    /**
     * 根据 userID 禁言
     * 
     * @param _userID
     * @param keepTime
     * @param startTime
     * @param endTime
     * @param memo
     * @return
     * @throws GameServerDownException
     */
    /*
     * public boolean setPlayerChatBlack(int _userID,int keepTime,String
     * startTime,String endTime,String memo) throws GameServerDownException{ if(rmi
     * == null){ throw new GameServerDownException(); } try{
     * 
     * return rmi.setPlayerChatBlack(_userID,keepTime,startTime,endTime,memo);
     * 
     * }catch (RemoteException re) { throw new GameServerDownException(); } }
     */

    /**
     * 根据 userID 解冻角色
     * 
     * @param _userID
     * @return
     * @throws GameServerDownException
     */
    /*
     * public boolean deletePlayerUserIDBlack(int _userID) throws
     * GameServerDownException{ if(rmi == null){ throw new
     * GameServerDownException(); } try{
     * 
     * return rmi.deletePlayerUserIDBlack(_userID);
     * 
     * }catch (RemoteException re) { throw new GameServerDownException(); } }
     */

    /**
     * 根据 accountID 解冻账号
     * 
     * @param _accountID
     * @return
     * @throws GameServerDownException
     */
    /*
     * public boolean deletePlayerAccountIDBlack(int _accountID) throws
     * GameServerDownException{ if(rmi == null){ throw new
     * GameServerDownException(); } try{
     * 
     * return rmi.deletePlayerAccountIDBlack(_accountID);
     * 
     * }catch (RemoteException re) { throw new GameServerDownException(); } }
     */

    /**
     * 根据 userID 解禁禁言
     * 
     * @param _userID
     * @return
     * @throws GameServerDownException
     */
    /*
     * public boolean deletePlayerChatBlack(int _userID) throws
     * GameServerDownException{ if(rmi == null){ throw new
     * GameServerDownException(); } try{
     * 
     * return rmi.deletePlayerChatBlack(_userID);
     * 
     * }catch (RemoteException re) { throw new GameServerDownException(); } }
     */

    /**
     * 根据 nickname 冻结角色
     * 
     * @param nickname
     * @param keepTime
     * @param startTime
     * @param endTime
     * @param memo
     * @return
     * @throws GameServerDownException
     */
    /*
     * public boolean setPlayerUserIDBlack(String nickname,int keepTime,String
     * startTime,String endTime,String memo) throws GameServerDownException{ if(rmi
     * == null){ throw new GameServerDownException(); } try{
     * 
     * return rmi.setPlayerUserIDBlack(nickname,keepTime,startTime,endTime,memo);
     * 
     * }catch (RemoteException re) { throw new GameServerDownException(); } }
     */

    /**
     * 根据 username 冻结账号
     * 
     * @param username
     * @param keepTime
     * @param startTime
     * @param endTime
     * @param memo
     * @return
     * @throws GameServerDownException
     */
    /*
     * public boolean setPlayerAccountIDBlack(String username,int keepTime,String
     * startTime,String endTime,String memo) throws GameServerDownException{ if(rmi
     * == null){ throw new GameServerDownException(); } try{
     * 
     * return rmi.setPlayerAccountIDBlack(username,keepTime,startTime,endTime,memo);
     * 
     * }catch (RemoteException re) { throw new GameServerDownException(); } }
     */

    /**
     * 根据 nickname 禁言
     * 
     * @param nickname
     * @param keepTime
     * @param startTime
     * @param endTime
     * @param memo
     * @return
     * @throws GameServerDownException
     */
    /*
     * public boolean setPlayerChatBlack(String nickname,int keepTime,String
     * startTime,String endTime,String memo) throws GameServerDownException{ if(rmi
     * == null){ throw new GameServerDownException(); } try{
     * 
     * return rmi.setPlayerChatBlack(nickname,keepTime,startTime,endTime,memo);
     * 
     * }catch (RemoteException re) { throw new GameServerDownException(); } }
     */

    /**
     * 根据 nickname 解冻角色
     * 
     * @param nickname
     * @return
     * @throws GameServerDownException
     */
    /*
     * public boolean deletePlayerUserIDBlack(String nickname) throws
     * GameServerDownException{ if(rmi == null){ throw new
     * GameServerDownException(); } try{
     * 
     * return rmi.deletePlayerUserIDBlack(nickname);
     * 
     * }catch (RemoteException re) { throw new GameServerDownException(); } }
     */

    /**
     * 根据 username 解冻账号
     * 
     * @param username
     * @return
     * @throws GameServerDownException
     */
    /*
     * public boolean deletePlayerAccountIDBlack(String username) throws
     * GameServerDownException{ if(rmi == null){ throw new
     * GameServerDownException(); } try{
     * 
     * return rmi.deletePlayerAccountIDBlack(username);
     * 
     * }catch (RemoteException re) { throw new GameServerDownException(); } }
     */

    /**
     * 根据 nickname 解禁禁言
     * 
     * @param nickname
     * @return
     * @throws GameServerDownException
     */
    /*
     * public boolean deletePlayerChatBlack(String nickname) throws
     * GameServerDownException{ if(rmi == null){ throw new
     * GameServerDownException(); } try{
     * 
     * return rmi.deletePlayerChatBlack(nickname);
     * 
     * }catch (RemoteException re) { throw new GameServerDownException(); } }
     */

    public String getServerName() {
        return serverName;
    }

    public int getServerID() {
        return serverID;
    }

    /**
     * GM 发公告
     * 
     * @param gmName
     * @param content
     * @throws RemoteException
     */
    public void sendNoticeGM(String gmName, String content) throws GameServerDownException {
        if (rmi == null) {
            throw new GameServerDownException();
        }
        try {

            rmi.sendNoticeGM(gmName, content);

        } catch (RemoteException re) {
            throw new GameServerDownException();
        }
    }

    /**
     * GM 回复玩家邮件 用 gmLetterID 到数据库里查询具体信息，然后给玩家发信
     * 
     * @param gmLetterID
     * @throws RemoteException
     */
    public void GMReplyLetter(int gmLetterID) throws GameServerDownException {
        if (rmi == null) {
            throw new GameServerDownException();
        }
        try {

            rmi.GMReplyLetter(gmLetterID);

        } catch (RemoteException re) {
            throw new GameServerDownException();
        }
    }

    /**
     * 瞬移
     * 
     * @param mapID
     * @param userID
     * @return
     * @throws GameServerDownException
     */
    public int blink(int mapID, int userID) throws GameServerDownException {
        if (rmi == null) {
            throw new GameServerDownException();
        }
        try {

            return rmi.blink((short) mapID, userID);

        } catch (RemoteException re) {
            throw new GameServerDownException();
        }
    }

    /**
     * 经审核通过后，给玩家添加物品
     * 
     * @param userID
     * @param goodsID
     * @param number
     * @return
     * @throws GameServerDownException
     */
    public int addGoodsForPlayer(int userID, int goodsID, int number) throws GameServerDownException {
        if (rmi == null) {
            throw new GameServerDownException();
        }
        try {

            return rmi.addGoodsForPlayer(userID, goodsID, number);

        } catch (RemoteException re) {
            throw new GameServerDownException();
        }
    }

    /**
     * 经过审核后，给玩家加点
     * 
     * @param userID
     * @param point
     * @return
     * @throws GameServerDownException
     */
    public int addPointForPlayer(int userID, int point) throws GameServerDownException {
        if (rmi == null) {
            throw new GameServerDownException();
        }
        try {

            return rmi.addPointForPlayer(userID, point);

        } catch (RemoteException re) {
            throw new GameServerDownException();
        }
    }

    /**
     * \ 给玩家添加金钱、等级、爱情度
     * 
     * @param userID
     * @param money
     * @param level
     * @param loverValue
     * @return
     * @throws GameServerDownException
     */
    public int modifyPlayerInfo(int userID, int money, int level, int loverValue, int skillPoint)
            throws GameServerDownException {
        if (rmi == null) {
            throw new GameServerDownException();
        }
        try {

            return rmi.modifyPlayerInfo(userID, money, loverValue, level, skillPoint);

        } catch (RemoteException re) {
            throw new GameServerDownException();
        }
    }

    /**
     * 获取物品名称
     * 
     * @param goodsID
     * @return 如果没有则返回 0
     * @throws GameServerDownException
     */
    public String getGoodsName(int goodsID) throws GameServerDownException {
        if (rmi == null) {
            throw new GameServerDownException();
        }
        try {

            return rmi.getGoodsName(goodsID);

        } catch (RemoteException re) {
            return "0";
        }
    }
}
