package gm.entities;

import java.sql.Timestamp;

/**
 * 给玩家添加的物品或点数类
 * @author jiaodj
 *
 */
public class AddGoodsInfo {
	private int id;
	private int gmID; //操作的GM
	private String gmName;
	private int serverID;
	private int accountID;
	private int roleID;
	private String roleName;
	private int goodsID; //添加的物品ID或点数
    private String goodsName;
    private int number;//物品数量，只在添加物品时用
	private Timestamp createTime;
	private int flag;
	private String memo;

    private int money;//金钱
    private int level; //等级
    private int loverValue; //爱情值
    private int skillPoint; //技能点
	
	private int auditGmID; //审核的GM
    private String auditGmName;
	private Timestamp auditTime;
	
	public AddGoodsInfo() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(int goodsID) {
		this.goodsID = goodsID;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getAuditGmID() {
		return auditGmID;
	}

	public void setAuditGmID(int auditGmID) {
		this.auditGmID = auditGmID;
	}

	public Timestamp getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Timestamp auditTime) {
		this.auditTime = auditTime;
	}

	public int getGmID() {
		return gmID;
	}

	public void setGmID(int gmID) {
		this.gmID = gmID;
	}

	public String getGmName() {
		return gmName;
	}

	public void setGmName(String gmName) {
		this.gmName = gmName;
	}

	public int getServerID() {
		return serverID;
	}

	public void setServerID(int serverID) {
		this.serverID = serverID;
	}

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getAuditGmName() {
        return auditGmName;
    }

    public void setAuditGmName(String auditGmName) {
        this.auditGmName = auditGmName;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLoverValue() {
        return loverValue;
    }

    public void setLoverValue(int loverValue) {
        this.loverValue = loverValue;
    }

    public int getSkillPoint() {
        return skillPoint;
    }

    public void setSkillPoint(int skillPoint) {
        this.skillPoint = skillPoint;
    }
}
