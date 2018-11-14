package com.bbg.pojo;

public class MsgParam {
	private String msgFomart;//消息格式	消息格式: json  或者xml 
	private String touser;//成员工号列表	（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送
	private String toparty;//部门ID列表	多个接收者用‘|’分隔，最多支持100个。当touser为@all时忽略本参数
	private String totag;//标签ID列表		多个接收者用‘|’分隔。当touser为@all时忽略本参数
	private String msgType;//消息类型		分别有 文本（text）、图片(image)、图文(news)
	private String orgin;//信息来源		来自各业务系统 签名 如 OA系统
	private String agentid;//企业应用的编码		会议：meeting  news;//新闻： process;//制度：challeng一站到底
							//外勤：outwork //待办：todo //邮箱：email//IT服务：itserver //其他：other 
	private String content;//消息内容		msgType为文本（text） 时必填
	private String title;//标题		msgType为图文(news) 时 为图文标题 如不填这客户端不显示标题
	private String description;//描述		msgType为图文(news) 时 为图文描述如不填这客户端不显示描述信息
	private String url;//链接		点击后跳转的链接。msgType为图文(news) 为点击图文跳转连接
	private String picurl;//图片链接
	public String getMsgFomart() {
		return msgFomart;
	}
	public void setMsgFomart(String msgFomart) {
		this.msgFomart = msgFomart;
	}
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getToparty() {
		return toparty;
	}
	public void setToparty(String toparty) {
		this.toparty = toparty;
	}
	public String getTotag() {
		return totag;
	}
	public void setTotag(String totag) {
		this.totag = totag;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getOrgin() {
		return orgin;
	}
	public void setOrgin(String orgin) {
		this.orgin = orgin;
	}
	public String getAgentid() {
		return agentid;
	}
	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	
	
	@Override
	public String toString() {
		return "MsgParam [msgFomart=" + msgFomart + ", touser=" + touser
				+ ", toparty=" + toparty + ", totag=" + totag + ", msgType="
				+ msgType + ", orgin=" + orgin + ", agentid=" + agentid
				+ ", content=" + content + ", title=" + title
				+ ", description=" + description + ", url=" + url + ", picurl="
				+ picurl + "]";
	}
	
	
	
	
}
