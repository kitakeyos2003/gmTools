package gm.entities;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: jiaodj
 * Date: 11-4-19
 * Time: 下午1:59
 */
public class IndexNotice {
    private int id;
    private int serverID;   //分区ID，0为所有服务器都显示
    private int type;       // 1:公告  2:活动
    private String title;
    private String content;
    private int show;     //是否显示 1:显示 0:不显示
    private int top;      //是否置顶 0:置顶 1:普通
    private int sequence;   //顺序
    private String color;//颜色值 默认 0x8d5a2d
    private Timestamp createTime;
    private Timestamp updateTime;

    private String serverName; //分区名称

    public IndexNotice() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServerID() {
        return serverID;
    }

    public void setServerID(int serverID) {
        this.serverID = serverID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getShow() {
        return show;
    }

    public void setShow(int show) {
        this.show = show;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
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

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

