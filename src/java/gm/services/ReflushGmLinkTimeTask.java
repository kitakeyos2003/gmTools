package gm.services;

import gm.entities.GmUser;

import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by IntelliJ IDEA.
 * User: jiaodj
 * Date: 11-3-22
 * Time: 下午8:49
 * 刷新GM连接时间
 * 如果现在距上次发连接请求超过10分钟，则判断其下线
 */
public class ReflushGmLinkTimeTask extends TimerTask{

    private List<GmUser> gmUserList;

    public ReflushGmLinkTimeTask(List<GmUser> gmUserList) {
        this.gmUserList = gmUserList;
    }

    @Override
    public void run() {
        for (Iterator<GmUser> it = gmUserList.iterator(); it.hasNext();){
            GmUser gm = it.next();
            if(gm.isOnLine() && System.currentTimeMillis()-gm.getLastLinkTime()>10*60*1000){
                GmListManager.getInstance().removeGm(gm);
            }
        }
    }
}
