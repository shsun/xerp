package base.tool;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import base.utils.BaseObjectUtil;

public class ProgressManage{


	private static ConcurrentMap<String,Vector<Object>> messageMap =  new ConcurrentHashMap<String,Vector<Object>>();
	
	private static ConcurrentMap<String,Boolean> finishMap = new ConcurrentHashMap<String,Boolean>();
	
	private static ConcurrentMap<String,Object> progressMap = new ConcurrentHashMap<String,Object>();

	
	/**
	 * 获取最新消息会把获取到的内容删除
	 * @return
	 */
	public static Vector<Object> getRecentMessages(String id) {
		
		Vector<Object> messages = getMessage(id);
		Vector<Object> messagesCopy = BaseObjectUtil.getDeepCopy(messages);
		messages.clear();
		return messagesCopy;
	}
	
	/**
	 * 获取消息集合，并不会清除以获取到的内容
	 * @param id
	 * @return
	 */
	public static Vector<Object> getMessage(String id) {
		Vector<Object> messages = (Vector<Object>)messageMap.get(id);
		if ( messages != null ) {
			return messages;
		}
		messages = new Vector<Object>();
		messageMap.put(id, messages);
		return messages;
	}
	
	/**
	 * 添加消息
	 * @param id
	 * @param value
	 */
	public static void addMessage(String id,Object message) {
		
		Vector<Object> messages = getMessage(id);
		messages.add(message);
		
	}
	
	/**
	 * 完成状态
	 * @param id
	 * @return
	 */
	public static boolean isFinish(String id) {
		Object value = finishMap.get(id);
		if ( value == null ) {
			finishMap.put(id, false);
		}
		return finishMap.get(id);
	}
	
	/**
	 * 设置完成状态
	 * @param id
	 * @return
	 */
	public static void setFinishStatus(String id,boolean status) {
		finishMap.put(id, status);
	}
	
	/**
	 * 获取进度
	 * @param id
	 * @return
	 */
	public static Object getProgress(String id) {
		Object v = progressMap.get(id);
		if ( v == null ) {
			v  = new Object();
		}
		return v;
	}
	
	/**
	 * 添加进度
	 * @param id
	 * @return
	 */
	public static void setProgress(String id,Object progress) {
		progressMap.put(id, progress);
	}
}
