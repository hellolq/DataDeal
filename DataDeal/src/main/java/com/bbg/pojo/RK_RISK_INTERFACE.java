package com.bbg.pojo;

public class RK_RISK_INTERFACE {
	
	private String rk_msg_id;//消息ID
	private String level_id;//异常级别
	private String class_id;//异常类别
	private String risk_type_id;//异常类型
	private String org_id;//机构
	private String send_time;//发送时间
	private String source_id;//来源ID
	private String source_type_id;//来源类型
	private String receive_time;//接收时间
	private String status;//状态
	private String err_desc;
	private String content;
	
	
	
	
	public String getRk_msg_id() {
		return rk_msg_id;
	}
	public void setRk_msg_id(String rk_msg_id) {
		this.rk_msg_id = rk_msg_id;
	}
	public String getLevel_id() {
		return level_id;
	}
	public void setLevel_id(String level_id) {
		this.level_id = level_id;
	}
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
	public String getRisk_type_id() {
		return risk_type_id;
	}
	public void setRisk_type_id(String risk_type_id) {
		this.risk_type_id = risk_type_id;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public String getSource_id() {
		return source_id;
	}
	public void setSource_id(String source_id) {
		this.source_id = source_id;
	}
	public String getSource_type_id() {
		return source_type_id;
	}
	public void setSource_type_id(String source_type_id) {
		this.source_type_id = source_type_id;
	}
	public String getReceive_time() {
		return receive_time;
	}
	public void setReceive_time(String receive_time) {
		this.receive_time = receive_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErr_desc() {
		return err_desc;
	}
	public void setErr_desc(String err_desc) {
		this.err_desc = err_desc;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	

}
