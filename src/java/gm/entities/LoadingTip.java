package gm.entities;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: jiaodj
 * Date: 11-4-23
 * Time: 下午3:24
 */
public class LoadingTip {
    private int id;
    private String content;
    private Timestamp createTime;
    private Timestamp updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
