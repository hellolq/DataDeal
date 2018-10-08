package com.bbg.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BbgBhExceptionTwo {
	
	private String subject;//异常标题
	private String riskDesc;//异常描述
	private String sourceObjType;//原对象类型ID
	private String sourceObjId;//原对象ID
	private String happenTimeStr;//异常发生时间
	private String amount;//异常涉及金额或者积分
	private String contentView;//展示形式
	private Map<String,String> gridSchema = new HashMap<>();//要展示的字段
	private List<BbgBhExceptionThree> data = new ArrayList<>();//数据源
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getRiskDesc() {
		return riskDesc;
	}
	public void setRiskDesc(String riskDesc) {
		this.riskDesc = riskDesc;
	}
	public String getSourceObjType() {
		return sourceObjType;
	}
	public void setSourceObjType(String sourceObjType) {
		this.sourceObjType = sourceObjType;
	}
	public String getSourceObjId() {
		return sourceObjId;
	}
	public void setSourceObjId(String sourceObjId) {
		this.sourceObjId = sourceObjId;
	}
	public String getHappenTimeStr() {
		return happenTimeStr;
	}
	public void setHappenTimeStr(String happenTimeStr) {
		this.happenTimeStr = happenTimeStr;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getContentView() {
		return contentView;
	}
	public void setContentView(String contentView) {
		this.contentView = contentView;
	}
	
	public Map<String, String> getGridSchema() {
		return gridSchema;
	}
	public void setGridSchema(Map<String, String> gridSchema) {
		this.gridSchema = gridSchema;
	}
	public List<BbgBhExceptionThree> getData() {
		return data;
	}
	public void setData(List<BbgBhExceptionThree> data) {
		this.data = data;
	}
	
	
	
	
	
	

}
