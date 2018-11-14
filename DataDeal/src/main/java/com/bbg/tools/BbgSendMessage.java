package com.bbg.tools;

import java.util.HashMap;
import java.util.Map;
import com.bbg.pojo.MsgParam;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BbgSendMessage {
	
	/*
	 * 使用步步高企业号-百货移动BI模块推送信息
	 * empId 1000063573|1000063574 多个工号用|连接
	 * 请求成功返回值：{"rsData":null,"status":"0","statusMsg":"ok成功"} 
	 * 换行： \n
	 * */
	public static String sendMessage(String empId,String content) throws Exception{
		String result = "";
		ObjectMapper mapper = new ObjectMapper();
		MsgParam msgParam = new MsgParam();
		//String url = "http://192.168.7.73:7001/OAWSSMS/wx/sendMsg.action";//测试地址
		String url = "http://192.168.245.9:7001/OAWSSMS/wx/sendMsg.action";//正式地址
		Map<String, Object> map = new HashMap<String, Object>();
		
		msgParam.setTouser(empId);
		msgParam.setContent(content);
		msgParam.setAgentid("BhReport");
		msgParam.setOrgin("");
		msgParam.setMsgType("text");
		msgParam.setMsgFomart("json");
		msgParam.setDescription("");
		msgParam.setPicurl("");
		msgParam.setUrl("");
		msgParam.setTitle("");
		msgParam.setTotag("");
		msgParam.setToparty("");
		map.put("reqData", msgParam);
		
		String body = mapper.writeValueAsString(map);
		result  = BbgTools.sendHttpPost(url, body);
		//System.out.println(result);
		//
		return result;
	}
	
	
}


