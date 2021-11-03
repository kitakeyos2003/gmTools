package gm.services;

import gm.db.DataService;
import gm.db.GmDAO;
import gm.entities.GmExpMoudlus;
import gm.entities.ServerInfoBean;
import org.apache.log4j.Logger;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import hero.login.rmi.IYOYOLoginRMI;


public class ServerExpMoudlusService {
    private static Logger log = Logger.getLogger(ServerExpMoudlusService.class);
    
	/**
     * 获得服务器经验系数列表
     * @return
     * @throws Exception
     */
    public static List<GmExpMoudlus> getExpMoudlusList() throws Exception{
    	List<GmExpMoudlus> gmExpMoudlus = new ArrayList<GmExpMoudlus>();
		List<ServerInfoBean> serverList = DataService.getServerInfoBeanList();
		for(ServerInfoBean  sib : serverList) {
			GmExpMoudlus gem = DataService.getServerExpMoudlus(sib.getServerID()); 
			if(gem != null) {
				gmExpMoudlus.add(gem);
			}
		}
    	return gmExpMoudlus;
    }
    
	/**
     * 给指定服务器添加经验系数列表
     * @return
     * @throws Exception
     */
    public static boolean addExpMoudlus(GmExpMoudlus gem) throws Exception{
    	return DataService.addServerExpMoudlus(gem);
    }
    
	/**
     * 更新指定服务器经验系数列表
     * @return
     * @throws Exception
     */
    public static boolean updateExpMoudlus(GmExpMoudlus gem) throws Exception{
    	return DataService.updateServerExpMoudlus(gem);
    }
    
}
