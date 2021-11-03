package gm.entities;

import java.sql.Timestamp;

public class PlayerInfo {
	private int accountID;
	private String accountName;
	private int userID;
	private String name;
	private boolean online;
	private Timestamp lastLoginTime;
	private Timestamp lastLogoutTime;
	private boolean chatBlack;
	private boolean roleBlack;
	
	private String sex;
	private String vocation;
	private String clan;
	private int level;
	private int exp;
	private int money;
	
	private int serverID;
	
	private String whereName;
	private String phone;
	private String publisher;
	
	public PlayerInfo(){}
	
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Timestamp getLastLogoutTime() {
		return lastLogoutTime;
	}
	public void setLastLogoutTime(Timestamp lastLogoutTime) {
		this.lastLogoutTime = lastLogoutTime;
	}
	public boolean isChatBlack() {
		return chatBlack;
	}
	public void setChatBlack(boolean chatBlack) {
		this.chatBlack = chatBlack;
	}
	public boolean isRoleBlack() {
		return roleBlack;
	}
	public void setRoleBlack(boolean roleBlack) {
		this.roleBlack = roleBlack;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getVocation() {
		return vocation;
	}

	public void setVocation(String vocation) {
		this.vocation = vocation;
	}

	public String getClan() {
		return clan;
	}

	public void setClan(String clan) {
		this.clan = clan;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getServerID() {
		return serverID;
	}

	public void setServerID(int serverID) {
		this.serverID = serverID;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getWhereName() {
		return whereName;
	}

	public void setWhereName(String whereName) {
		this.whereName = whereName;
	}
	
	
}
