package gm.entities;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: jiaodj
 * Date: 11-3-23
 * Time: 下午7:18
 */
public class Fun {
    private int id;
    private String name;
    private String url;
    private int sequence;
    private int menuID;
    private int show;
    private int level;
    private Timestamp createTime;

    private boolean hasParams;//url里是否有参数

    public Fun() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getMenuID() {
        return menuID;
    }

    public void setMenuID(int menuID) {
        this.menuID = menuID;
    }

    public int getShow() {
        return show;
    }

    public void setShow(int show) {
        this.show = show;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public boolean isHasParams() {
        return hasParams;
    }

    public void setHasParams(boolean hasParams) {
        this.hasParams = hasParams;
    }
}
