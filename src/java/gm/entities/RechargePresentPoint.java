package gm.entities;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: jiaodj
 * Date: 11-4-23
 * Time: 上午10:44
 * 充值活动赠点类
 */
public class RechargePresentPoint {
    private int id;
    private int price;
    private int presentPoint;
    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp createTime;
    private Timestamp updateTime;

    private int serverID;
    private String serverName;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getServerID() {
        return serverID;
    }

    public void setServerID(int serverID) {
        this.serverID = serverID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPresentPoint() {
        return presentPoint;
    }

    public void setPresentPoint(int presentPoint) {
        this.presentPoint = presentPoint;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
