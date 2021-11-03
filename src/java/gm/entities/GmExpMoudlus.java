package gm.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GmExpMoudlus {
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public String getMoudlus() {
		return moudlus;
	}
	public void setMoudlus(String moudlus) {
		this.moudlus = moudlus;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public void load(ResultSet rs) {
		try {
			this.setServerId(rs.getInt("serverId"));
			this.setMoudlus(rs.getString("moudlus"));
			this.setStartTime(rs.getString("startTime"));
			this.setEndTime(rs.getString("endTime"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private int serverId;
	private String moudlus;
	private String startTime;
	private String endTime;
	
}
