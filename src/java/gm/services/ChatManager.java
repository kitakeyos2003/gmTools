package gm.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 聊天内容管理
 * @author jiaodongjie
 *
 */
public class ChatManager {
	private static ChatManager instance;
	
	/**
	 * 保存聊天内容 
	 * 以 serverID 为 key
	 */
	private static Map<Integer,List<String>> chatMap;
	
	private ChatManager(){
		chatMap = new HashMap<Integer, List<String>>();
	}
	
	public static ChatManager getInstance(){
		if(instance == null){
			instance = new ChatManager();
		}
		return instance;
	}

	/**
	 * 添加聊天内容
	 * @param serverID
	 * @param content
	 */
	public void addChatContent(int serverID,String content){
		content = content + "";
		List<String> chatList = chatMap.get(serverID);
		if(chatList == null){
			chatList = new ArrayList<String>();
			chatList.add(content);
			chatMap.put(serverID, chatList);
		}else{
			chatList.add(content);
		}
	}
	
	/**
	 * 清除某个服务器的聊天内容
	 * 每次请求后清除一次
	 * @param serverID
	 */
	public void clearChatContent(int serverID){
		synchronized (chatMap) {
			List<String> chatList = chatMap.get(serverID);
			if(chatList != null){
				chatList.clear();
			}
		}
		
	}
	/**
	 * 获取某个服务器的聊天内容
	 * @param serverID
	 * @return
	 */
	public List<String> getChatListByServerID(int serverID){
		return chatMap.get(serverID);
	}
}
