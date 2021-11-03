package gm.entities;

import java.io.UnsupportedEncodingException;

public class ServerInfoBean {
	private int serverID;
	private String serverName;
	private int onLinePlayerNum;
	private boolean status;

    public ServerInfoBean(int serverID, String serverName) {
        this.serverID = serverID;
        this.serverName = serverName;
    }

    public ServerInfoBean() {
	}
	
	public int getServerID() {
		return serverID;
	}
	public void setServerID(int serverID) {
		this.serverID = serverID;
	}
	public String getServerName() {
		/*try {
			serverName = new String(serverName.getBytes("GBK"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public int getOnLinePlayerNum() {
		return onLinePlayerNum;
	}
	public void setOnLinePlayerNum(int onLinePlayerNum) {
		this.onLinePlayerNum = onLinePlayerNum;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}
