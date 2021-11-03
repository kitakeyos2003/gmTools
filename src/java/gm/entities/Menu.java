package gm.entities;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jiaodj
 * Date: 11-3-23
 * Time: 下午7:18
 */
public class Menu {
    private int id;
    private String name;
    private int sequence;
    private int show;
    private Timestamp createTime;

    public Menu() {
    }

    private List<Fun> funList;

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

    public List<Fun> getFunList() {
        return funList;
    }

    public void setFunList(List<Fun> funList) {
        this.funList = funList;
    }

    public int getShow() {
        return show;
    }

    public void setShow(int show) {
        this.show = show;
    }
}
