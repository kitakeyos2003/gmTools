package gm.services;

import gm.db.DataService;

import java.util.Iterator;
import java.util.List;
import java.util.Timer;

import gm.entities.GmUser;
import org.apache.log4j.Logger;

public class GmListManager {
	private static Logger log = Logger.getLogger(GmListManager.class);
	private static GmListManager instance;
	
	private static List<GmUser> gmUserList;

    private static final long INTERVAL_REFLUSH_LINK_TIME = 10*60*1000;

    private Timer timer;

	private GmListManager() {
		gmUserList = DataService.getGmUserList();
		log.debug("gm list manager init size = " + gmUserList.size());
        timer = new Timer();
        timer.schedule(new ReflushGmLinkTimeTask(gmUserList),
                INTERVAL_REFLUSH_LINK_TIME, INTERVAL_REFLUSH_LINK_TIME);
	}
	
	public static GmListManager getInstance(){
		if(instance == null){
			instance = new GmListManager();
		}
		return instance;
	}
	
	public void addGm(GmUser gm){
		boolean nohad = true;
		for(Iterator<GmUser> it = gmUserList.iterator(); it.hasNext();){
			GmUser gmx = it.next();
			if(gmx.getId() == gm.getId()){
				gmx.setOnLine(true);
                gmx.setLastLinkTime(System.currentTimeMillis());
				nohad = false;
                break;
			}
		}
		if(nohad){
			gmUserList.add(gm);
		}
		log.debug("gm list manager add after size = " + gmUserList.size());
	}
	
	public void removeGm(GmUser gm){
		for(Iterator<GmUser> it = gmUserList.iterator(); it.hasNext();){
			GmUser gmx = it.next();
			if(gmx.getId() == gm.getId()){
				gmx.setOnLine(false);
                break;
			}
		}
	}

    /**
     * 查检GM是否已经登录
     * @param gm
     * @return
     */
    public boolean logged(GmUser gm){
        for(Iterator<GmUser> it = gmUserList.iterator(); it.hasNext();){
			GmUser gmx = it.next();
			if(gmx.getId() == gm.getId() && gmx.isOnLine()){
				return true;
			}
		}
        return false;
    }

    /**
     * 刷新连接时间
     * 浏览器每 5 分钟发次请求，这里重新设置时间，
     * ReflushGmLinkTimeTask类 里：如果当前时间与上次发请求的时间间隔大于10分钟，则说明GM已下线
     * @param gmID
     */
    public void reflushGmLinkTime(int gmID){
        for(Iterator<GmUser> it = gmUserList.iterator(); it.hasNext();){
            GmUser gmx = it.next();
            if(gmx.getId() == gmID && gmx.isOnLine()){
                gmx.setLastLinkTime(System.currentTimeMillis());
                gmx.setOnLine(true);
                break;
            }
        }
    }
	
	public List<GmUser> getGmUserList(){
		return gmUserList;
	}
	
}
