package base.tool.upload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUploadModel {
	/**
	 * 消息记录
	 */
	private List<String> messages = new ArrayList<String>();
	/**
	 * 200成功其余失败
	 */
	private String code;
	/**
	 * 字段->值
	 */
	private Map<String, Object> fieldMap = new HashMap<String, Object>();

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Map<String, Object> getFieldMap() {
		return fieldMap;
	}

	public void setFieldMap(Map<String, Object> fieldMap) {
		this.fieldMap = fieldMap;
	}
}
