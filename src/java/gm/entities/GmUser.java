package gm.entities;

public class GmUser {
	private int id;
	private String username;
	private boolean isOnLine;
    private int level;

    /**
     * 上次连接的时间
     * 用来判断是否还在线
     * 时间超过10分钟则判断其下线
     * 实现由浏览器5分钟发一次请求
     */
    private long lastLinkTime;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public GmUser() {
	}

	public GmUser(int id, String username) {
		super();
		this.id = id;
		this.username = username;
	}
	public boolean isOnLine() {
		return isOnLine;
	}
	public void setOnLine(boolean isOnLine) {
		this.isOnLine = isOnLine;
	}


    public long getLastLinkTime() {
        return lastLinkTime;
    }

    public void setLastLinkTime(long lastLinkTime) {
        this.lastLinkTime = lastLinkTime;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GmUser gmUser = (GmUser) o;

        if (id != gmUser.id) return false;
        if (isOnLine != gmUser.isOnLine) return false;
        if (lastLinkTime != gmUser.lastLinkTime) return false;
        if (level != gmUser.level) return false;
        if (username != null ? !username.equals(gmUser.username) : gmUser.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (isOnLine ? 1 : 0);
        result = 31 * result + level;
        result = 31 * result + (int) (lastLinkTime ^ (lastLinkTime >>> 32));
        return result;
    }
}
