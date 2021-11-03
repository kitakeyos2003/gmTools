package gm.entities;

import java.sql.Timestamp;

public class GmLetter {
	private int id;
	/**
	 * 服务器ID
	 */
	private int serverID;
	/**
	 * 发送者ID
	 */
	private int senderRoleId;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 问题类型
	 * 1:Bug问题
	 * 2:充值/消费问题
	 * 3:账号角色问题
	 * 4:数据物品异常
	 * 5:其它问题和建议
	 * 6:游戏操作建议
	 */
	private int type;
	
	/**
	 * 问题类型名称
	 */
	private String typeName;
	
	private String[] typeNames = new String[]{"Bug问题","充值/消费问题","账号角色问题","数据物品异常","其它问题和建议","游戏操作建议"};
	/**
	 * 发送时间
	 */
	private Timestamp sendTime;
	/**
	 * 回复的GM_ID
	 */
	private int replyGmId;
	/**
	 * 回复的GM名称
	 */
	private String replyGmName;
	/**
	 * 回复内容
	 */
	private String replyContent;
	/**
	 * 回复时间
	 */
	private Timestamp replyTime;
	/**
	 * 状态
	 * 1:已回复
	 * 0:未回复
	 */
	private int status;
		
	public GmLetter() {
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
	public int getSenderRoleId() {
		return senderRoleId;
	}
	public void setSenderRoleId(int senderRoleId) {
		this.senderRoleId = senderRoleId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Timestamp getSendTime() {
		return sendTime;
	}
	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}
	public int getReplyGmId() {
		return replyGmId;
	}
	public void setReplyGmId(int replyGmId) {
		this.replyGmId = replyGmId;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public Timestamp getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(Timestamp replyTime) {
		this.replyTime = replyTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public String getReplyGmName() {
		return replyGmName;
	}

	public void setReplyGmName(String replyGmName) {
		this.replyGmName = replyGmName;
	}

	public String[] getTypeNames() {
		return typeNames;
	}

	public void setTypeNames(String[] typeNames) {
		this.typeNames = typeNames;
	}

	public String getTypeName() {
		typeName = getTypeNames()[getType()-1];
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
}
