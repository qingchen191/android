package cn.smarthome.sap.model;

import cn.smarthome.sap.db.AbstractBaseModel;
import cn.smarthome.sap.util.DateUtils;

public class LogInfo extends AbstractBaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String logType;
	private String logTime;
	private String content;

	public LogInfo(){
		super();
	}
	public LogInfo(String type, String content){
		this.logType = type;
		this.logTime = DateUtils.getCurrentDateTime();
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getLogTime() {
		return logTime;
	}

	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	

}
