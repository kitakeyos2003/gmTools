package gm.util;

public class MoneyLogInfo{
	private MoneyLogUserInfo pk;
	private String nickname;
	private String cause;
	private int changeMoney;
	private String time;

	
	
	public MoneyLogInfo(MoneyLogUserInfo pk, String nickname, String cause,
			int changeMoney, String time) {
		super();
		this.pk = pk;
		this.nickname = nickname;
		this.cause = cause;
		this.changeMoney = changeMoney;
		this.time = time;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public MoneyLogInfo() {
		super();
	}
	
	public MoneyLogUserInfo getPk() {
		return pk;
	}
	public void setPk(MoneyLogUserInfo pk) {
		this.pk = pk;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public int getChangeMoney() {
		return changeMoney;
	}
	public void setChangeMoney(int changeMoney) {
		this.changeMoney = changeMoney;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cause == null) ? 0 : cause.hashCode());
		result = prime * result + changeMoney;
		result = prime * result
				+ ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
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
		MoneyLogInfo other = (MoneyLogInfo) obj;
		if (cause == null) {
			if (other.cause != null)
				return false;
		} else if (!cause.equals(other.cause))
			return false;
		if (changeMoney != other.changeMoney)
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}
	
	
}
