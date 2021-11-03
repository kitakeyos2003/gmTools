package gm.util;

public class MoneyLogUserInfo{
	private int userid;
	private int accountid;
	private int gameid;
	private int serverid;
	
	
	
	public MoneyLogUserInfo(int userid, int accountid, int gameid, int serverid) {
		super();
		this.userid = userid;
		this.accountid = accountid;
		this.gameid = gameid;
		this.serverid = serverid;
	}

	public MoneyLogUserInfo() {

	}
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getAccountid() {
		return accountid;
	}
	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}
	public int getGameid() {
		return gameid;
	}
	public void setGameid(int gameid) {
		this.gameid = gameid;
	}
	public int getServerid() {
		return serverid;
	}
	public void setServerid(int serverid) {
		this.serverid = serverid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountid;
		result = prime * result + gameid;
		result = prime * result + serverid;
		result = prime * result + userid;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MoneyLogUserInfo other = (MoneyLogUserInfo) obj;
		if (accountid != other.accountid)
			return false;
		if (gameid != other.gameid)
			return false;
		if (serverid != other.serverid)
			return false;
		if (userid != other.userid)
			return false;
		return true;
	}
}
