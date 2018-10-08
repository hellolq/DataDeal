package com.bbg.pojo;

import java.util.ArrayList;
import java.util.List;

public class BbgBhExceptionOne {
	
	private String exceptionType;//异常类别
	private String exceptionTypeChild;//异常类型
	private String exceptionLevel;//异常级别
	private String organizationId;//机构ID
	private String sendTime;//发送时间
	private String source;//数据来源
	private List<BbgBhExceptionTwo> bbgBhExceptionTwos = new ArrayList<>();
	
	public List<BbgBhExceptionTwo> getBbgBhExceptionTwos() {
		return bbgBhExceptionTwos;
	}
	public void setBbgBhExceptionTwos(List<BbgBhExceptionTwo> bbgBhExceptionTwos) {
		this.bbgBhExceptionTwos = bbgBhExceptionTwos;
	}
	public String getExceptionType() {
		return exceptionType;
	}
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}
	public String getExceptionTypeChild() {
		return exceptionTypeChild;
	}
	public void setExceptionTypeChild(String exceptionTypeChild) {
		this.exceptionTypeChild = exceptionTypeChild;
	}
	public String getExceptionLevel() {
		return exceptionLevel;
	}
	public void setExceptionLevel(String exceptionLevel) {
		this.exceptionLevel = exceptionLevel;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	

}
