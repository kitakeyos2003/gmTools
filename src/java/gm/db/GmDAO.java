package gm.db;

import gm.entities.*;
import gm.util.MD5;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class GmDAO {

    private static Logger log = Logger.getLogger(GmDAO.class);

    private static final String CHECK_GM_SQL = "select * from gm where name=? limit 1";

    private static final String SELECT_GM_SQL_BY_ID = "select * from gm where id=? limit 1";

    private static final String INSERT_GM_USER = "insert into gm(name,pwd,nickname,group_id,level) values(?,?,?,?,?)";

    private static final String UPDATE_GM_PWD = "update gm set pwd=? where id=? limit 1";

    private static final String GET_GM_LIST_SQL = "select * from gm";

    private static final String SELECT_GM_SQL = "select * from gm where name=? and pwd=? limit 1";

    private static final String SELECT_ACCOUNT_SQL = "select username from account where account_id=? limit 1";

    private static final String INSERT_BLINK = "insert into blink(serverID,type,name,mapID,map_name,memo,gm_id)"
            + " values(?,?,?,?,?,?,?)";

    private static final String INSERT_ACCOUNT_RESET_LOG = "insert into account_reset_log(serverID,gm_id,account_id,role_id,role_name,memo)"
            + " values(?,?,?,?,?,?)";

    private static final String INSERT_CANCEL_BLACK_LOG = "insert into delete_black_log(serverID,operate_type,gm_id,account_id,role_id,role_name,memo,type)"
            + " values(?,?,?,?,?,?,?,?)";

    private static final String INSERT_ROLE_BLACK = "insert into role_black(account_id,serverID,user_id,nickname,keep_time,end_time,memo,gm_id)"
            + " values(?,?,?,?,?,?,?,?)";

    private static final String INSERT_ACCOUNT_BLACK = "insert into account_black(account_id,gm_id,username,keep_time,end_time,memo)"
            + " values(?,?,?,?,?,?)";

    private static final String INSERT_CHAT_BLACK = "insert into chat_black(account_id,serverID,user_id,nickname,keep_time,end_time,memo,gm_id) "
            + " values(?,?,?,?,?,?,?,?)";

    private static final String DELETE_ROLE_BLACK = "delete from role_black where serverID=? and account_id=? and user_id=?";

    private static final String DELETE_ACCOUNT_BLACK = "delete from account_black where account_id=?";

    private static final String DELETE_CHAT_BLACK = "delete from chat_black where serverID=? and account_id=? and user_id=?";

    private static final String SELECT_GM_LETTER_SQL = "select * from gm_letter order by send_time desc limit ?,?";

//    private static final String SELECT_GM_LETTER_NUMBER = "select count(*) from gm_letter";
    private static final String SELECT_SINGLE_GM_LETTER = "select * from gm_letter where id=? limit 1";

    private static final String SELECT_GM_LETTER_BY_USER_ID = "select * from gm_letter where serverID=? and sender_role_id=? order by send_time desc limit ? ";

    private static final String UPDATE_GM_LETTER = "update gm_letter set reply_gm_id=?,reply_content=?,reply_time=?,status=? where id=?";

    private static final String INSERT_NOTICE_SQL = "insert into gm_notice(serverID,gm_id,title,content,update_time,start_time,end_time,interval_time,times) "
            + " values(?,?,?,?,?,?,?,?,?)";

    private static final String UPDATE_NOTICE_SQL = "update gm_notice set serverID=?,gm_id=?, title=?,content=?,update_time=?,start_time=?,end_time=?,interval_time=?,times=? where id=? limit 1";

    private static final String SELECT_NOTICE_SQL = "select * from gm_notice order by update_time desc limit ?,?";

//    private static final String SELECT_NOTICE_NUMBER  = "select count(*) from gm_notice";
    private static final String SELECT_NOTICE_BY_ID = "select * from gm_notice where id=? limit 1";

    private static final String DEL_NOTICE_BY_ID = "delete from gm_notice where id=?";

    private static final String SELECT_ROLE_BLACK_SQL = "select count(id) from role_black where account_id=? and serverID=? and user_id=? limit 1";

    private static final String SELECT_CHAT_BLACK_SQL = "select count(id) from chat_black where account_id=? and serverID=? and user_id=? limit 1";

    private static final String INSERT_GM_LOGIN_LOG = "insert into gm_login_log(gm_id,ip) values(?,?)";

    private static final String ADD_GOODS_FOR_PLAYER = "insert into gm_add_goods_log(gm_id,server_id,account_id,role_id,role_name,goods_id,number,flag,memo,goods_name) values(?,?,?,?,?,?,?,?,?,?)";

    private static final String AUDIT_ADD_GOODS_FOR_PLAYER = "update gm_add_goods_log t set t.flag=?,t.audit_gm_id=?,t.audit_time=? where t.id=?";

    private static final String ADD_POINT_FOR_PLAYER = "insert into gm_add_point_log(gm_id,server_id,account_id,role_id,role_name,point,flag,memo) values(?,?,?,?,?,?,?,?)";

    private static final String AUDIT_ADD_POINT_FOR_PLAYER = "update gm_add_point_log t set t.flag=?,t.audit_gm_id=?,t.audit_time=? where t.id=?";

    private static final String SELECT_ADD_GOODS_INFO = "select * from gm_add_goods_log ";
    private static final String SELECT_ADD_GOODS_INFO_NUMBER = "select count(*) from gm_add_goods_log ";

    private static final String SELECT_ADD_POINT_INFO = "select * from gm_add_point_log ";
    private static final String SELECT_ADD_POINT_INFO_NUMBER = "select count(*) from gm_add_point_log ";

    private static final String SELECT_MODIFY_PLAYER_INFO = "select * from modify_player_pro_log ";
    private static final String SELECT_MODIFY_PLAYER_INFO_NUMBER = "select count(*) from modify_player_pro_log ";

    private static final String SELECT_ADD_GOODS_INFO_BY_ID = "select * from gm_add_goods_log where id=? ";

    private static final String SELECT_ADD_POINT_INFO_BY_ID = "select * from gm_add_point_log where id=? ";

    private static final String SELECT_MODIFY_PLAYER_INFO_BY_ID = "select * from modify_player_pro_log where id=? ";

    private static final String MODIFY_PLAYER_INFO = "insert into modify_player_pro_log(gm_id,server_id,account_id,role_id,role_name,level,money,lover_value,skill_point,memo) values(?,?,?,?,?,?,?,?,?,?)";

    private static final String AUDIT_MODIFY_PLAYER_INFO = "update modify_player_pro_log t set t.flag=?,t.audit_gm_id=?,t.audit_time=? where t.id=?";

    private static final String SELECT_FUN_BY_ID = "select * from gm_tools_fun where id=?";

    private static final String SELECT_MENU_BY_ID = "select * from gm_tools_menu where id=?";

    private static final String SELECT_FUNS_BY_MENU_ID = "select * from gm_tools_fun where menu_id=? order by sequence asc";

    private static final String SELECT_MENUS = "select * from gm_tools_menu ";

    private static final String ADD_MENU = "insert into gm_tools_menu(name,sequence,is_show,gm_id) values(?,?,?,?)";

    private static final String UPD_MENU = "update gm_tools_menu t set t.name=?,t.sequence=?,t.is_show=?,t.gm_id=?,t.update_time=? where t.id=?";

    private static final String ADD_FUN = "insert into gm_tools_fun(name,url,sequence,menu_id,is_show,level,gm_id) values(?,?,?,?,?,?,?)";

    private static final String UPD_FUN = "update gm_tools_fun t set t.name=?,t.url=?,t.sequence=?,t.menu_id=?,t.is_show=?,t.level=?,t.gm_id=?,t.update_time=? where t.id=?";

    private static final String ADD_INDEX_NOTICE = "insert into index_notice(server_id,title,content,is_show,is_top,sequence,type,update_time,color) values(?,?,?,?,?,?,?,?,?)";

    private static final String UPD_IDNEX_NOTICE = "update index_notice t set t.server_id=?,t.title=?,t.content=?,t.is_show=?,t.is_top=?,t.sequence=?,t.type=?,t.update_time=?,t.color=? where t.id=?";

    private static final String SELECT_INDEX_NOTICE = "select * from index_notice t order by t.update_time desc limit ?,?";

    private static final String SELECT_INDEX_NOTICE_BY_ID = "select * from index_notice t where t.id=? limit 1";

    private static final String DELETE_INDEX_NOTICE_BY_ID = "delete from index_notice where id=?";

    private static final String ADD_RECHARGE_PRESENT_POINT = "insert into recharge_present_point(price,present_point,start_time,end_time,update_time,server_id) values(?,?,?,?,?,?)";

    private static final String UPD_RECHARGE_PRESENT_POINT = "update recharge_present_point t set t.price=?,t.present_point=?,t.start_time=?,t.end_time=?,t.update_time=?,t.server_id=? where t.id=?";

    private static final String SELECT_RECHARGE_PRESENT_POINT = "select * from recharge_present_point t order by t.update_time desc limit ?,?";

    private static final String SELECT_SINGLE_RECHARGE_PRESENT_POINT = "select * from recharge_present_point t where t.id=?";

    private static final String ADD_LOADING_TIP = "insert into loading_tip(content,update_time) values(?,?)";

    private static final String UPD_LOADING_TIP = "update loading_tip t set t.content=?,t.update_time=? where t.id=?";

    private static final String SELECT_LOADING_TIP = "select * from loading_tip t where t.id=?";

    private static final String SELECT_ALL_LOADING_TIP = "select * from loading_tip t order by t.update_time desc limit ?,?";

    private static final String DEL_LOADING_TIP = "delete from loading_tip where id=?";

    private static final String SELECT_SERVER_EXP_MOUDLUS = "select * from gm_exp_moudlus t where t.serverId=?";

    private static final String ADD_SERVER_EXP_MOUDLUS = "insert into gm_exp_moudlus(serverId,moudlus,startTime,endTime) values(?,?,?,?)";

    private static final String UPDATE_SERVER_EXP_MOUDLUS = "update gm_exp_moudlus t set t.moudlus=?,t.startTime=?,t.endTime=? where t.serverId=?";

    /**
     * 根据服务器id获取服务器的经验系数
     *
     * @param id
     * @return
     */
    protected static GmExpMoudlus getServerExpMoudlus(int serverId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        GmExpMoudlus gem = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SERVER_EXP_MOUDLUS);
            ps.setInt(1, serverId);
            rs = ps.executeQuery();
            if (rs.next()) {
                gem = new GmExpMoudlus();
                gem.load(rs);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return gem;
    }

    /**
     * 添加服务器经验系数
     *
     * @param
     * @return
     */
    protected static int addExpMoudlus(GmExpMoudlus gem) {
        Connection conn = null;
        PreparedStatement ps = null;
        int res = 0;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(ADD_SERVER_EXP_MOUDLUS);
            ps.setInt(1, gem.getServerId());
            ps.setString(2, gem.getMoudlus());
            ps.setString(3, gem.getStartTime());
            ps.setString(4, gem.getEndTime());
            res = ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 修改游戏服务器经验系数
     *
     * @param rpp
     * @return
     */
    protected static int updateExpMoudlus(GmExpMoudlus gem) {
        Connection conn = null;
        PreparedStatement ps = null;
        int res = 0;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(UPDATE_SERVER_EXP_MOUDLUS);
            ps.setString(1, gem.getMoudlus());
            ps.setString(2, gem.getStartTime());
            ps.setString(3, gem.getEndTime());
            ps.setInt(4, gem.getServerId());
            res = ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != ps) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 获取单个loading tip
     *
     * @param id
     * @return
     */
    protected static LoadingTip getLoadingTip(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        LoadingTip loadingTip = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_LOADING_TIP);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                loadingTip = new LoadingTip();
                loadingTip.setId(rs.getInt("id"));
                loadingTip.setContent(rs.getString("content"));
                loadingTip.setCreateTime(rs.getTimestamp("create_time"));
                loadingTip.setUpdateTime(rs.getTimestamp("update_time"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return loadingTip;
    }

    /**
     * 获取 loading tip 列表
     *
     * @param start
     * @param offset
     * @return
     */
    protected static List<LoadingTip> getLoadingTipList(int start, int offset) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<LoadingTip> loadingTipList = new ArrayList<LoadingTip>();
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_ALL_LOADING_TIP);
            ps.setInt(1, start);
            ps.setInt(2, offset);
            rs = ps.executeQuery();
            LoadingTip loadingTip;
            while (rs.next()) {
                loadingTip = new LoadingTip();
                loadingTip.setId(rs.getInt("id"));
                loadingTip.setContent(rs.getString("content"));
                loadingTip.setCreateTime(rs.getTimestamp("create_time"));
                loadingTip.setUpdateTime(rs.getTimestamp("update_time"));

                loadingTipList.add(loadingTip);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return loadingTipList;
    }

    /**
     * 删除loading tip
     *
     * @param id
     */
    protected static void delLoadingTip(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(DEL_LOADING_TIP);
            ps.setInt(1, id);

            ps.executeUpdate();

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 修改loading tip
     *
     * @param loadingTip
     * @return
     */
    protected static int updLoadingTip(LoadingTip loadingTip) {
        Connection conn = null;
        PreparedStatement ps = null;
        int res = 0;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(UPD_LOADING_TIP);
            ps.setString(1, loadingTip.getContent());
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.setInt(3, loadingTip.getId());

            res = ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 添加loading tip
     *
     * @param loadingTip
     * @return
     */
    protected static int addLoadingTip(LoadingTip loadingTip) {
        Connection conn = null;
        PreparedStatement ps = null;
        int res = 0;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(ADD_LOADING_TIP);
            ps.setString(1, loadingTip.getContent());
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));

            res = ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 获取充值赠点活动列表
     *
     * @param start
     * @param offset
     * @return
     */
    protected static List<RechargePresentPoint> getRechargePresentPointList(int start, int offset) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<RechargePresentPoint> rpplist = new ArrayList<RechargePresentPoint>();
        RechargePresentPoint rpp = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_RECHARGE_PRESENT_POINT);
            ps.setInt(1, start);
            ps.setInt(2, offset);

            rs = ps.executeQuery();
            while (rs.next()) {
                rpp = new RechargePresentPoint();
                rpp.setId(rs.getInt("id"));
                rpp.setPrice(rs.getInt("price"));
                rpp.setPresentPoint(rs.getInt("present_point"));
                rpp.setStartTime(rs.getTimestamp("start_time"));
                rpp.setEndTime(rs.getTimestamp("end_time"));
                rpp.setCreateTime(rs.getTimestamp("create_time"));
                rpp.setUpdateTime(rs.getTimestamp("update_time"));
                rpp.setServerID(rs.getInt("server_id"));
                rpp.setServerName(DataService.getServerName(rpp.getServerID()));

                rpplist.add(rpp);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rpplist;
    }

    /**
     * 获取单个充值赠点活动
     *
     * @param id
     * @return
     */
    protected static RechargePresentPoint getRechargePresentPoint(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        RechargePresentPoint rpp = new RechargePresentPoint();
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SINGLE_RECHARGE_PRESENT_POINT);
            ps.setInt(1, id);

            rs = ps.executeQuery();
            if (rs.next()) {
                rpp.setId(rs.getInt("id"));
                rpp.setPrice(rs.getInt("price"));
                rpp.setPresentPoint(rs.getInt("present_point"));
                rpp.setStartTime(rs.getTimestamp("start_time"));
                rpp.setEndTime(rs.getTimestamp("end_time"));
                rpp.setCreateTime(rs.getTimestamp("create_time"));
                rpp.setUpdateTime(rs.getTimestamp("update_time"));
                rpp.setServerID(rs.getInt("server_id"));
                rpp.setServerName(DataService.getServerName(rpp.getServerID()));
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rpp;
    }

    /**
     * 修改充值赠点活动
     *
     * @param rpp
     * @return
     */
    protected static int updRechargePresentPoint(RechargePresentPoint rpp) {
        Connection conn = null;
        PreparedStatement ps = null;
        int res = 0;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(UPD_RECHARGE_PRESENT_POINT);
            ps.setInt(1, rpp.getPrice());
            ps.setInt(2, rpp.getPresentPoint());
            ps.setTimestamp(3, rpp.getStartTime());
            ps.setTimestamp(4, rpp.getEndTime());
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            ps.setInt(6, rpp.getServerID());
            ps.setInt(7, rpp.getId());

            res = ps.executeUpdate();

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 添加充值赠点活动
     *
     * @param rpp
     * @return
     */
    protected static int addRechargePresentPoint(RechargePresentPoint rpp) {
        Connection conn = null;
        PreparedStatement ps = null;
        int res = 0;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(ADD_RECHARGE_PRESENT_POINT);
            ps.setInt(1, rpp.getPrice());
            ps.setInt(2, rpp.getPresentPoint());
            ps.setTimestamp(3, rpp.getStartTime());
            ps.setTimestamp(4, rpp.getEndTime());
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            ps.setInt(6, rpp.getServerID());

            res = ps.executeUpdate();

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 获取公告/活动的总数
     *
     * @param tableName 表名
     * @return
     */
    protected static int getCountNumber(String tableName) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int num = 0;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement("select count(*) from " + tableName);

            rs = ps.executeQuery();
            if (rs.next()) {
                num = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return num;
    }

    /**
     * 删除一个公告/活动
     *
     * @param id
     * @return
     */
    protected static int delIndexNotice(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        int res = 0;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(DELETE_INDEX_NOTICE_BY_ID);
            ps.setInt(1, id);

            res = ps.executeUpdate();

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 根据ID获取公告/活动
     *
     * @param id
     * @return
     */
    protected static IndexNotice getIndexNotice(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        IndexNotice inotice = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_INDEX_NOTICE_BY_ID);
            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                inotice = new IndexNotice();
                inotice.setId(id);
                inotice.setServerID(rs.getInt("server_id"));
                inotice.setTitle(rs.getString("title"));
                inotice.setContent(rs.getString("content"));
                inotice.setShow(rs.getInt("is_show"));
                inotice.setTop(rs.getInt("is_top"));
                inotice.setType(rs.getInt("type"));
                inotice.setSequence(rs.getInt("sequence"));
                inotice.setCreateTime(rs.getTimestamp("create_time"));
                inotice.setUpdateTime(rs.getTimestamp("update_time"));
                inotice.setColor(rs.getString("color"));
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return inotice;
    }

    /**
     * 获取公告/活动列表
     *
     * @param start
     * @param offset
     * @return
     */
    protected static List<IndexNotice> getIndexNoticeList(int start, int offset) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<IndexNotice> indexNoticeList = new ArrayList<IndexNotice>();

        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_INDEX_NOTICE);
            ps.setInt(1, start);
            ps.setInt(2, offset);

            rs = ps.executeQuery();
            IndexNotice inotice;
            while (rs.next()) {
                inotice = new IndexNotice();
                inotice.setId(rs.getInt("id"));
                inotice.setServerID(rs.getInt("server_id"));
                inotice.setTitle(rs.getString("title"));
                inotice.setContent(rs.getString("content"));
                inotice.setShow(rs.getInt("is_show"));
                inotice.setTop(rs.getInt("is_top"));
                inotice.setType(rs.getInt("type"));
                inotice.setSequence(rs.getInt("sequence"));
                inotice.setCreateTime(rs.getTimestamp("create_time"));
                inotice.setUpdateTime(rs.getTimestamp("update_time"));
                inotice.setColor(rs.getString("color"));

                indexNoticeList.add(inotice);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return indexNoticeList;
    }

    /**
     * 修改公告/活动
     *
     * @param inotice
     * @return
     */
    protected static int updIndexNotice(IndexNotice inotice) {
        Connection conn = null;
        PreparedStatement ps = null;
        int res = 0;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(UPD_IDNEX_NOTICE);
            ps.setInt(1, inotice.getServerID());
            ps.setString(2, inotice.getTitle());
            ps.setString(3, inotice.getContent());
            ps.setInt(4, inotice.getShow());
            ps.setInt(5, inotice.getTop());
            ps.setInt(6, inotice.getSequence());
            ps.setInt(7, inotice.getType());
            ps.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            ps.setString(9, inotice.getColor());
            ps.setInt(10, inotice.getId());

            res = ps.executeUpdate();

            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 添加公告/活动
     *
     * @param inotice
     * @return
     */
    protected static int addIndexNotice(IndexNotice inotice) {
        Connection conn = null;
        PreparedStatement ps = null;
        int res = 0;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(ADD_INDEX_NOTICE);
            ps.setInt(1, inotice.getServerID());
            ps.setString(2, inotice.getTitle());
            ps.setString(3, inotice.getContent());
            ps.setInt(4, inotice.getShow());
            ps.setInt(5, inotice.getTop());
            ps.setInt(6, inotice.getSequence());
            ps.setInt(7, inotice.getType());
            ps.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            ps.setString(9, inotice.getColor());

            res = ps.executeUpdate();

            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 添加功能
     *
     * @param name
     * @param url
     * @param sequence 顺序
     * @param menuID 所属菜单ID
     * @param show 是否显示 1:显示 0不显示
     * @param level 使用等级
     */
    protected static void addFun(String name, String url, int sequence, int menuID, int show, int level, int gmID) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(ADD_FUN);
            ps.setString(1, name);
            ps.setString(2, url);
            ps.setInt(3, sequence);
            ps.setInt(4, menuID);
            ps.setInt(5, show);
            ps.setInt(6, level);
            ps.setInt(7, gmID);

            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 添加菜单
     *
     * @param name
     * @param sequence 顺序
     * @param show 是否显示 1:显示 0不显示
     */
    protected static void addMenu(String name, int sequence, int show, int gmID) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(ADD_MENU);
            ps.setString(1, name);
            ps.setInt(2, sequence);
            ps.setInt(3, show);
            ps.setInt(4, gmID);

            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 审核修改玩家金钱、等级、爱情值、技能点
     *
     * @param id
     * @param flag
     * @param gmID
     * @return
     */
    protected static int auditModifyPlayerInfo(int id, int flag, int gmID) {
        log.debug("audit modify player info id=" + id + ",flag=" + flag + ",gmid=" + gmID);
        Connection conn = null;
        PreparedStatement ps = null;
        int res = 0;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(AUDIT_MODIFY_PLAYER_INFO);
            ps.setInt(1, flag);
            ps.setInt(2, gmID);
            ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            ps.setInt(4, id);

            res = ps.executeUpdate();

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 修改玩家金钱、等级、爱情度
     *
     * @param gmID
     * @param serverID
     * @param accountID
     * @param roleID
     * @param roleName
     * @param level
     * @param money
     * @param loverValue
     * @param memo
     * @return
     */
    protected static int modifyPayerInfo(int gmID, int serverID, int accountID, int roleID, String roleName, int level, int money, int loverValue, int skillPoint, String memo) {
        Connection conn = null;
        PreparedStatement ps = null;
        int res = 0;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(MODIFY_PLAYER_INFO);
            ps.setInt(1, gmID);
            ps.setInt(2, serverID);
            ps.setInt(3, accountID);
            ps.setInt(4, roleID);
            ps.setString(5, roleName);
            ps.setInt(6, level);
            ps.setInt(7, money);
            ps.setInt(8, loverValue);
            ps.setInt(9, skillPoint);
            ps.setString(10, memo);

            res = ps.executeUpdate();

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 根据ID获取添加物品或点数记录
     *
     * @param id
     * @param type 1:获取物品记录 2:获取点数记录 3:修改玩家属性
     * @return
     */
    protected static AddGoodsInfo getInfoByID(int id, int type) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        AddGoodsInfo info = null;

        try {
            conn = DBManager.getInstance().getConnection();
            if (type == 1) {
                ps = conn.prepareStatement(SELECT_ADD_GOODS_INFO_BY_ID);
            }
            if (type == 2) {
                ps = conn.prepareStatement(SELECT_ADD_POINT_INFO_BY_ID);
            }
            if (type == 3) {
                ps = conn.prepareStatement(SELECT_MODIFY_PLAYER_INFO_BY_ID);
            }
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                info = new AddGoodsInfo();
                int userID = rs.getInt("role_id");
                info.setRoleID(userID);
                int flag = rs.getInt("flag");
                info.setFlag(flag);
                if (type == 1) {
                    int goodsID = rs.getInt("goods_id");
                    info.setGoodsID(goodsID);
                    int number = rs.getInt("number");
                    info.setNumber(number);
                }
                if (type == 2) {
                    int point = rs.getInt("point");
                    info.setGoodsID(point);
                }
                if (type == 3) {
                    int money = rs.getInt("money");
                    int level = rs.getInt("level");
                    int loverValue = rs.getInt("lover_value");
                    int skillPoint = rs.getInt("skill_point");

                    info.setMoney(money);
                    info.setLevel(level);
                    info.setLoverValue(loverValue);
                    info.setSkillPoint(skillPoint);
                }
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (rs != null) {
                    rs.close();
                }
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return info;
    }

    protected static int getAddInfoListNumber(int type, int flag) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        int num = 0;
        try {
            conn = DBManager.getInstance().getConnection();

            String sql = SELECT_ADD_GOODS_INFO_NUMBER;

            if (type == 2) {
                sql = SELECT_ADD_POINT_INFO_NUMBER;
            }
            if (type == 3) {
                sql = SELECT_MODIFY_PLAYER_INFO_NUMBER;
            }

            if (flag >= 0) {
                sql += " where flag=" + flag;
            }

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                num = rs.getInt(1);
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (rs != null) {
                    rs.close();
                }
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return num;
    }

    /**
     * 获取添加物品或点数的记录
     *
     * @param type 1:查询物品 2:点数
     * @param flag -1:全部 0:未审核的 1:已审核通过的 2:已审核未通过的
     * @return
     */
    protected static List<AddGoodsInfo> getAddGoodsInfoList(int type, int flag, int start, int offset) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<AddGoodsInfo> infoList = new ArrayList<AddGoodsInfo>();
        AddGoodsInfo info = null;
        try {
            conn = DBManager.getInstance().getConnection();

            String sql = SELECT_ADD_GOODS_INFO;

            if (type == 2) {
                sql = SELECT_ADD_POINT_INFO;
            }
            if (type == 3) {
                sql = SELECT_MODIFY_PLAYER_INFO;
            }

            if (flag >= 0) {
                sql += " where flag=" + flag;
            }

            sql += " order by create_time desc limit " + start + "," + offset;

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                info = new AddGoodsInfo();
                int id = rs.getInt("id");
                int gmID = rs.getInt("gm_id");
                int serverID = rs.getInt("server_id");
                int accountID = rs.getInt("account_id");
                int roleID = rs.getInt("role_id");
                String roleName = rs.getString("role_name");
                int goodsID = 0;
                if (type == 1) {
                    goodsID = rs.getInt("goods_id");
                    int number = rs.getInt("number");
                    String goodsName = rs.getString("goods_name");
                    info.setNumber(number);
                    info.setGoodsName(goodsName);
                }
                if (type == 2) {
                    goodsID = rs.getInt("point");
                }
                if (type == 3) {
                    int money = rs.getInt("money");
                    int level = rs.getInt("level");
                    int loverValue = rs.getInt("lover_value");
                    int skillPoint = rs.getInt("skill_point");
                    info.setMoney(money);
                    info.setLevel(level);
                    info.setLoverValue(loverValue);
                    info.setSkillPoint(skillPoint);
                }
                int flagx = rs.getInt("flag");
                String memo = rs.getString("memo");
                Timestamp creatTime = rs.getTimestamp("create_time");
                if (flagx > 0) {
                    int auditGmId = rs.getInt("audit_gm_id");
                    Timestamp auditTime = rs.getTimestamp("audit_time");
                    info.setAuditGmName(getGmUserById(auditGmId).getUsername());
                    info.setAuditTime(auditTime);
                }

                info.setId(id);
                info.setGmID(gmID);
                info.setGmName(getGmUserById(gmID).getUsername());
                info.setServerID(serverID);
                info.setAccountID(accountID);
                info.setRoleID(roleID);
                info.setRoleName(roleName);
                info.setGoodsID(goodsID);
                info.setFlag(flagx);
                info.setMemo(memo);
                info.setCreateTime(creatTime);

                infoList.add(info);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (rs != null) {
                    rs.close();
                }
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return infoList;
    }

    /**
     * 审核给玩家添加的点数
     *
     * @param id
     * @param flag 1:审核通过 2:审核不通过
     * @param gmID 审核的GM
     */
    protected static int auditAddPointForPlayer(int id, int flag, int gmID) {
        Connection conn = null;
        PreparedStatement ps = null;
        int res = 0;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(AUDIT_ADD_POINT_FOR_PLAYER);
            ps.setInt(1, flag);
            ps.setInt(2, gmID);
            ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            ps.setInt(4, id);

            res = ps.executeUpdate();

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 给玩家加点
     *
     * @param gmID 操作的GM
     * @param accountID
     * @param roleID
     * @param roleName
     * @param point
     * @param flag 0:未审核
     * @param memo
     */
    protected static int addPointForPlayer(int gmID, int serverID, int accountID, int roleID, String roleName, int point, int flag, String memo) {
        Connection conn = null;
        PreparedStatement ps = null;
        int res = 0;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(ADD_POINT_FOR_PLAYER);
            ps.setInt(1, gmID);
            ps.setInt(2, serverID);
            ps.setInt(3, accountID);
            ps.setInt(4, roleID);
            ps.setString(5, roleName);
            ps.setInt(6, point);
            ps.setInt(7, flag);
            ps.setString(8, memo);

            res = ps.executeUpdate();

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 审核给玩家添加的物品
     *
     * @param id
     * @param flag 1:审核通过 2:审核不通过
     * @param gmID 审核的GM
     */
    protected static int auditAddGoodsForPlayer(int id, int flag, int gmID) {
        Connection conn = null;
        PreparedStatement ps = null;
        int res = 0;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(AUDIT_ADD_GOODS_FOR_PLAYER);
            ps.setInt(1, flag);
            ps.setInt(2, gmID);
            ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            ps.setInt(4, id);

            res = ps.executeUpdate();

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 给玩家添加物品
     *
     * @param gmID 操作的GM
     * @param accountID
     * @param roleID
     * @param roleName
     * @param goodsID 物品ID
     * @param flag 0:未审核
     * @param memo
     */
    protected static int addGoodsForPlayer(int gmID, int serverID, int accountID, int roleID, String roleName, int goodsID, String goodsName, int number, int flag, String memo) {
        Connection conn = null;
        PreparedStatement ps = null;
        int res = 0;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(ADD_GOODS_FOR_PLAYER);
            ps.setInt(1, gmID);
            ps.setInt(2, serverID);
            ps.setInt(3, accountID);
            ps.setInt(4, roleID);
            ps.setString(5, roleName);
            ps.setInt(6, goodsID);
            ps.setInt(7, number);
            ps.setInt(8, flag);
            ps.setString(9, memo);
            ps.setString(10, goodsName);

            res = ps.executeUpdate();

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * GM登录日志
     *
     * @param gmID
     * @param ip
     */
    protected static void addGmLoginLog(int gmID, String ip) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(INSERT_GM_LOGIN_LOG);
            ps.setInt(1, gmID);
            ps.setString(2, ip);

            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 查询是否被禁言
     *
     * @param serverID
     * @param accountID
     * @param userID
     * @return 0:未被禁言 1:已被禁言
     */
    protected static int getChatBlack(int serverID, int accountID, int userID) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int num = 0;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_CHAT_BLACK_SQL);
            ps.setInt(1, accountID);
            ps.setInt(2, serverID);
            ps.setInt(3, userID);
            rs = ps.executeQuery();
            while (rs.next()) {
                num = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return num;
    }

    /**
     * 查询是否被冻结
     *
     * @param serverID
     * @param accountID
     * @param userID
     * @return 0:未被冻结 1:已被冻结
     */
    protected static int getRoleBlack(int serverID, int accountID, int userID) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int num = 0;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_ROLE_BLACK_SQL);
            ps.setInt(1, accountID);
            ps.setInt(2, serverID);
            ps.setInt(3, userID);
            rs = ps.executeQuery();
            while (rs.next()) {
                num = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return num;
    }

    protected static void delNotice(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(DEL_NOTICE_BY_ID);
            ps.setInt(1, id);

            ps.executeUpdate();

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected static GmNotice getNoticeById(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        GmNotice notice = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_NOTICE_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                notice = new GmNotice();
                notice.setId(rs.getInt("id"));
                notice.setServerID(rs.getInt("serverID"));
                notice.setTitle(rs.getString("title"));
                notice.setContent(rs.getString("content"));
                notice.setStartTime(rs.getTimestamp("start_time"));
                notice.setEndTime(rs.getTimestamp("end_time"));
                notice.setIntervalTime(rs.getInt("interval_time"));
                notice.setTimes(rs.getInt("times"));
                notice.setCreate_time(rs.getTimestamp("create_time"));
                notice.setUpdate_time(rs.getTimestamp("update_time"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return notice;
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
    protected static int updateNotice(int id, int serverID, int gmID, String title, String content,
            String start_time, String end_time, int intervalTime, int times) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(UPDATE_NOTICE_SQL);
            ps.setInt(1, serverID);
            ps.setInt(2, gmID);
            ps.setString(3, title);
            ps.setString(4, content);
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(6, Timestamp.valueOf(start_time));
            ps.setTimestamp(7, Timestamp.valueOf(end_time));
            ps.setInt(8, intervalTime);
            ps.setInt(9, times);
            ps.setInt(10, id);

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /*protected static int getGmNoticeNumber(){
        Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
        int num = 0;
        try{
            conn = DBManager.getInstance().getConnection();
			ps = conn.prepareStatement(SELECT_NOTICE_NUMBER);
            rs = ps.executeQuery();
            if(rs.next()){
                num = rs.getInt(1);
            }
            rs.close();
            ps.close();

        }catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(null != rs){
					rs.close();
				}
				if(null != ps){
					ps.close();
				}

			}catch(Exception e){
				e.printStackTrace();
			}
		}
        return num;
    }*/
    /**
     * 获取公告列表
     *
     * @param start
     * @param offset
     * @return
     */
    protected static List<GmNotice> getGmNoticeList(int start, int offset) {
        List<GmNotice> gmNoticeList = new ArrayList<GmNotice>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        GmNotice notice = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_NOTICE_SQL);
            ps.setInt(1, start);
            ps.setInt(2, offset);
            rs = ps.executeQuery();
            while (rs.next()) {
                notice = new GmNotice();
                notice.setId(rs.getInt("id"));
                notice.setServerID(rs.getInt("serverID"));
                notice.setTitle(rs.getString("title"));
                notice.setContent(rs.getString("content"));
                notice.setStartTime(rs.getTimestamp("start_time"));
                notice.setEndTime(rs.getTimestamp("end_time"));
                notice.setIntervalTime(rs.getInt("interval_time"));
                notice.setTimes(rs.getInt("times"));
                notice.setCreate_time(rs.getTimestamp("create_time"));
                notice.setUpdate_time(rs.getTimestamp("update_time"));
                gmNoticeList.add(notice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return gmNoticeList;
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
    protected static boolean addGmNotice(int serverID, int gmID, String title, String content,
            String start_time, String end_time, int intervalTime, int times) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(INSERT_NOTICE_SQL);
            ps.setInt(1, serverID);
            ps.setInt(2, gmID);
            ps.setString(3, title);
            ps.setString(4, content);
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(6, Timestamp.valueOf(start_time));
            ps.setTimestamp(7, Timestamp.valueOf(end_time));
            ps.setInt(8, intervalTime);
            ps.setInt(9, times);

            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 获取GM
     *
     * @param id
     * @return
     */
    protected static GmUser getGmUserById(int id) {
        GmUser gm = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_GM_SQL_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                gm = new GmUser();
                String gmName = rs.getString("name");
                gm.setId(id);
                gm.setUsername(gmName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return gm;
    }

    protected static int replyGmLetter(int id, String content, int gmID) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(UPDATE_GM_LETTER);
            ps.setInt(1, gmID);
            ps.setString(2, content);
            ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            ps.setInt(4, 1);
            ps.setInt(5, id);

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    protected static GmLetter getGmLetterById(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        GmLetter letter = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_SINGLE_GM_LETTER);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                letter = new GmLetter();
                letter.setId(rs.getInt("id"));
                letter.setServerID(rs.getInt("serverID"));
                letter.setSenderRoleId(rs.getInt("sender_role_id"));
                letter.setContent(rs.getString("content"));
                letter.setSendTime(rs.getTimestamp("send_time"));
                letter.setStatus(rs.getInt("status"));
                letter.setType(rs.getInt("type"));
                if (letter.getStatus() == 1) {
                    letter.setReplyGmId(rs.getInt("reply_gm_id"));
                    letter.setReplyContent(rs.getString("reply_content"));
                    letter.setReplyTime(rs.getTimestamp("reply_time"));
                    letter.setReplyGmName(getGmUserById(letter.getReplyGmId()).getUsername());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return letter;
    }

    protected static List<GmLetter> getGmLetterByUserId(int serverid, int id, int n) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        GmLetter letter = null;
        List<GmLetter> gmletterList = new ArrayList<GmLetter>();
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_GM_LETTER_BY_USER_ID);
            ps.setInt(1, serverid);
            ps.setInt(2, id);
            ps.setInt(3, n);
            rs = ps.executeQuery();
            while (rs.next()) {
                letter = new GmLetter();
                letter.setId(rs.getInt("id"));
                letter.setServerID(rs.getInt("serverID"));
                letter.setSenderRoleId(rs.getInt("sender_role_id"));
                letter.setContent(rs.getString("content"));
                letter.setSendTime(rs.getTimestamp("send_time"));
                letter.setStatus(rs.getInt("status"));
                letter.setType(rs.getInt("type"));
                if (letter.getStatus() == 1) {
                    letter.setReplyGmId(rs.getInt("reply_gm_id"));
                    letter.setReplyContent(rs.getString("reply_content"));
                    letter.setReplyTime(rs.getTimestamp("reply_time"));
                    letter.setReplyGmName(getGmUserById(letter.getReplyGmId()).getUsername());
                }
                gmletterList.add(letter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return gmletterList;
    }


    /*protected static int getGmLetterNumber(){
        Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
        int num = 0;
        try{
            conn = DBManager.getInstance().getConnection();
			ps = conn.prepareStatement(SELECT_GM_LETTER_NUMBER);
            rs = ps.executeQuery();

            if(rs.next()){
                num = rs.getInt(1);
            }
            rs.close();
            ps.close();

        }catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(null != rs){
					rs.close();
				}
				if(null != ps){
					ps.close();
				}

			}catch(Exception e){
				e.printStackTrace();
			}
		}
        return num;
    }*/
    protected static List<GmLetter> getGmLetterList(int start, int offset) {
        List<GmLetter> letters = new ArrayList<GmLetter>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        GmLetter letter = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_GM_LETTER_SQL);
            ps.setInt(1, start);
            ps.setInt(2, offset);
            rs = ps.executeQuery();
            while (rs.next()) {
                letter = new GmLetter();
                letter.setId(rs.getInt("id"));
                letter.setServerID(rs.getInt("serverID"));
                letter.setSenderRoleId(rs.getInt("sender_role_id"));
                letter.setContent(rs.getString("content"));
                letter.setSendTime(rs.getTimestamp("send_time"));
                letter.setStatus(rs.getInt("status"));
                letter.setType(rs.getInt("type"));
                if (letter.getStatus() == 1) {
                    letter.setReplyGmId(rs.getInt("reply_gm_id"));
                    letter.setReplyContent(rs.getString("reply_content"));
                    letter.setReplyTime(rs.getTimestamp("reply_time"));
                    letter.setReplyGmName(getGmUserById(letter.getReplyGmId()) != null ? getGmUserById(letter.getReplyGmId()).getUsername() : letter.getReplyGmId() + "不存在");
                }
                letters.add(letter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return letters;
    }

    protected static List<GmUser> getGmList() {
        List<GmUser> gmList = new ArrayList<GmUser>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(GET_GM_LIST_SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                GmUser gm = new GmUser(id, name);
                gmList.add(gm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return gmList;
    }

    protected static String getAccountName(int accountID) {
        String username = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_ACCOUNT_SQL);
            ps.setInt(1, accountID);
            rs = ps.executeQuery();
            while (rs.next()) {
                username = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return username;
    }

    /**
     * 解禁角色禁言
     *
     * @param serverID
     * @param accountID
     * @param userID
     */
    protected static boolean deleteChatBlack(int serverID, int accountID, int userID) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(DELETE_CHAT_BLACK);
            ps.setInt(1, serverID);
            ps.setInt(2, accountID);
            ps.setInt(3, userID);

            ps.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 账号解禁
     *
     * @param accountID
     */
    protected static boolean deleteAccountBlack(int accountID) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(DELETE_ACCOUNT_BLACK);
            ps.setInt(1, accountID);

            ps.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 角色解禁
     *
     * @param serverID
     * @param accountID
     * @param userID
     */
    protected static boolean deleteRoleBlack(int serverID, int accountID, int userID) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(DELETE_ROLE_BLACK);
            ps.setInt(1, serverID);
            ps.setInt(2, accountID);
            ps.setInt(3, userID);

            ps.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 角色禁言
     *
     * @param serverID
     * @param accountID
     * @param roleID
     * @param nickname
     * @param keepTime
     * @param end_time 格式"2011-01-01 00:00:00"
     * @param memo
     * @param gmID
     */
    protected static boolean setPlayerChatBlack(int serverID, int accountID, int roleID, String nickname, int keepTime, String end_time, String memo, int gmID) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(INSERT_CHAT_BLACK);
            ps.setInt(1, accountID);
            ps.setInt(2, serverID);
            ps.setInt(3, roleID);
            ps.setString(4, nickname);
            ps.setInt(5, keepTime);
            ps.setTimestamp(6, Timestamp.valueOf(end_time));
            ps.setString(7, memo);
            ps.setInt(8, gmID);

            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 冻结账号
     *
     * @param accountID
     * @param gmID
     * @param username
     * @param keepTime
     * @param endTime 格式"2011-01-01 00:00:00"
     * @param memo
     */
    protected static boolean setAccountBlack(int accountID, int gmID, String username, int keepTime, String endTime, String memo) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(INSERT_ACCOUNT_BLACK);
            ps.setInt(1, accountID);
            ps.setInt(2, gmID);
            ps.setString(3, username);
            ps.setInt(4, keepTime);
            ps.setTimestamp(5, Timestamp.valueOf(endTime));
            ps.setString(6, memo);

            ps.execute();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 冻结角色
     *
     * @param serverID
     * @param accountID
     * @param roleID
     * @param nickname
     * @param keepTime
     * @param end_time 格式"2011-01-01 00:00:00"
     * @param memo
     * @param gmID
     */
    protected static boolean setPlayerBlack(int serverID, int accountID, int roleID, String nickname, int keepTime, String end_time, String memo, int gmID) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(INSERT_ROLE_BLACK);
            ps.setInt(1, accountID);
            ps.setInt(2, serverID);
            ps.setInt(3, roleID);
            ps.setString(4, nickname);
            ps.setInt(5, keepTime);
            ps.setTimestamp(6, Timestamp.valueOf(end_time));
            ps.setString(7, memo);
            ps.setInt(8, gmID);

            ps.execute();

            return true;
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {

                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 获取所有菜单和功能
     *
     * @param loadFunList 是否加载菜单下的功能列表
     * @return
     */
    protected static List<Menu> getMenuList(boolean loadFunList, boolean isOnlyShow) {
        List<Menu> menuList = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBManager.getInstance().getConnection();
            String sql = SELECT_MENUS;

            if (isOnlyShow) {
                sql += " where is_show=1 ";
            }
            sql += " order by sequence asc";
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            Menu menu;
            menuList = new ArrayList<Menu>();
            while (rs.next()) {
                menu = new Menu();
                menu.setId(rs.getInt("id"));
                menu.setName(rs.getString("name"));
                menu.setSequence(rs.getInt("sequence"));
                menu.setShow(rs.getInt("is_show"));
                menu.setCreateTime(rs.getTimestamp("create_time"));

                if (loadFunList) {
                    menu.setFunList(getFunList(menu.getId()));
                }

                menuList.add(menu);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return menuList;
    }

    /**
     * 获取某菜单下的功能列表
     *
     * @param menuID
     * @return
     */
    protected static List<Fun> getFunList(int menuID) {
        List<Fun> funList = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_FUNS_BY_MENU_ID);
            ps.setInt(1, menuID);

            rs = ps.executeQuery();

            Fun fun;
            funList = new ArrayList<Fun>();
            while (rs.next()) {
                fun = new Fun();
                fun.setId(rs.getInt("id"));
                fun.setName(rs.getString("name"));
                fun.setLevel(rs.getInt("level"));
                fun.setUrl(rs.getString("url"));
                fun.setMenuID(rs.getInt("menu_id"));
                fun.setSequence(rs.getInt("sequence"));
                fun.setShow(rs.getInt("is_show"));
                fun.setCreateTime(rs.getTimestamp("create_time"));
                if (fun.getUrl().indexOf("?") > 0) {
                    fun.setHasParams(true);
                }
                funList.add(fun);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return funList;
    }

    /**
     * 根据ID获取菜单
     *
     * @param id
     * @return
     */
    protected static Menu getMenu(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Menu menu = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_MENU_BY_ID);
            ps.setInt(1, id);

            rs = ps.executeQuery();
            if (rs.next()) {
                menu = new Menu();
                menu.setId(rs.getInt("id"));
                menu.setName(rs.getString("name"));
                menu.setSequence(rs.getInt("sequence"));
                menu.setShow(rs.getInt("is_show"));
                menu.setCreateTime(rs.getTimestamp("create_time"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return menu;
    }

    /**
     * 修改菜单
     *
     * @param id
     * @param name
     * @param sequence
     * @param is_show
     * @param gmID
     * @return
     */
    protected static int updMenu(int id, String name, int sequence, int is_show, int gmID) {
        Connection conn = null;
        PreparedStatement ps = null;
        int res = 0;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(UPD_MENU);
            ps.setString(1, name);
            ps.setInt(2, sequence);
            ps.setInt(3, is_show);
            ps.setInt(4, gmID);
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            ps.setInt(6, id);

            res = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 修改功能
     *
     * @param id
     * @param name
     * @param sequence
     * @param is_show
     * @param gmID
     * @return
     */
    protected static int updFun(int id, String name, String url, int sequence, int menuID, int is_show, int level, int gmID) {
        Connection conn = null;
        PreparedStatement ps = null;
        int res = 0;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(UPD_FUN);
            ps.setString(1, name);
            ps.setString(2, url);
            ps.setInt(3, sequence);
            ps.setInt(4, menuID);
            ps.setInt(5, is_show);
            ps.setInt(6, level);
            ps.setInt(7, gmID);
            ps.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            ps.setInt(9, id);

            res = ps.executeUpdate();

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 根据ID获取功能
     *
     * @param id
     * @return
     */
    protected static Fun getFun(int id) {
        Fun fun = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_FUN_BY_ID);
            ps.setInt(1, id);

            rs = ps.executeQuery();
            if (rs.next()) {
                fun = new Fun();
                fun.setId(rs.getInt("id"));
                fun.setName(rs.getString("name"));
                fun.setLevel(rs.getInt("level"));
                fun.setUrl(rs.getString("url"));
                fun.setMenuID(rs.getInt("menu_id"));
                fun.setSequence(rs.getInt("sequence"));
                fun.setShow(rs.getInt("is_show"));
                fun.setCreateTime(rs.getTimestamp("create_time"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fun;
    }

    /**
     * 获取GM
     *
     * @param name
     * @param pwd
     * @return
     */
    protected static GmUser getGm(String name, String pwd) {
        GmUser gm = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(SELECT_GM_SQL);
            ps.setString(1, name);
            ps.setString(2, MD5.enc(pwd));
            rs = ps.executeQuery();
            while (rs.next()) {
                gm = new GmUser();
                int id = rs.getInt("id");
                System.out.println("gm id= " + id);
                String gmName = rs.getString("name");
                int level = rs.getInt("level");
                gm.setId(id);
                gm.setUsername(gmName);
                gm.setLevel(level);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return gm;
    }

    /**
     * 检查GM名称是否存在
     *
     * @param name
     * @return
     */
    protected static boolean isExists(String name) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(CHECK_GM_SQL);
            ps.setString(1, name);
            rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 添加GM
     *
     * @param name
     * @param password
     * @return
     */
    protected static boolean addGm(String name, String password, int groupID, int level) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(INSERT_GM_USER);
            ps.setString(1, name);
            ps.setString(2, MD5.enc(password));
            ps.setString(3, name);
            ps.setInt(4, groupID);
            ps.setInt(5, level);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {

                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * GM修改密码
     *
     * @param id
     * @param newPwd
     * @return
     */
    protected static boolean updatePwd(int id, String newPwd) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(UPDATE_GM_PWD);
            ps.setString(1, MD5.enc(newPwd));
            ps.setInt(2, id);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 瞬移记录
     *
     * @param serverID
     * @param gmID
     * @param type
     * @param name
     * @param mapID
     * @param mapName
     * @param memo
     */
    protected static void addBlink(int serverID, int gmID, int type, String name, int mapID, String mapName, String memo) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(INSERT_BLINK);
            ps.setInt(1, serverID);
            ps.setInt(2, type);
            ps.setString(3, name);
            ps.setInt(4, mapID);
            ps.setString(5, mapName);
            ps.setString(6, memo);
            ps.setInt(7, gmID);

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 踢玩家下线log
     *
     * @param serverID
     * @param gmID
     * @param accountID
     * @param roleID
     * @param roleName
     * @param memo
     * @return
     */
    protected static boolean addAccountResetLog(int serverID, int gmID, int accountID, int roleID, String roleName, String memo) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(INSERT_ACCOUNT_RESET_LOG);
            ps.setInt(1, serverID);
            ps.setInt(2, gmID);
            ps.setInt(3, accountID);
            ps.setInt(4, roleID);
            ps.setString(5, roleName);
            ps.setString(6, memo);

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {

                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 解禁禁言或解冻账号或角色LOG
     *
     * @param serverID
     * @param operateType 1:解禁禁言 2:解冻
     * @param gmID
     * @param accountID
     * @param roleID
     * @param roleName
     * @param memo
     * @param type 1:账号 2:角色
     * @return
     */
    protected static boolean addDeleteBlackLog(int serverID, int operateType, int gmID, int accountID, int roleID, String roleName, String memo, int type) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBManager.getInstance().getConnection();
            ps = conn.prepareStatement(INSERT_CANCEL_BLACK_LOG);
            ps.setInt(1, serverID);
            ps.setInt(2, operateType);
            ps.setInt(3, gmID);
            ps.setInt(4, accountID);
            ps.setInt(5, roleID);
            ps.setString(6, roleName);
            ps.setString(7, memo);
            ps.setInt(8, type);

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {

                if (null != ps) {
                    ps.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
