package com.bbg.pojo;

import java.sql.Date;

public class PCursor {
	
	private String jzrq;
	private Date jzrqDate;
	private int jysj;
	private String deptid;
	private String sp_id;
	private double xsje;
	private double xsml;
	private int xsbs;
	private double yhje;
	private int xssl;
	private double xsje_hy;
	private double yhje_hy;
	private int hybs;
	private int hth;
	private int v_XSSD1;
	private int v_XSSD2;
	
	
	public double getXsml() {
		return xsml;
	}
	public void setXsml(double xsml) {
		this.xsml = xsml;
	}
	public Date getJzrqDate() {
		return jzrqDate;
	}
	public void setJzrqDate(Date jzrqDate) {
		this.jzrqDate = jzrqDate;
	}
	public String getJzrq() {
		return jzrq;
	}
	public void setJzrq(String jzrq) {
		this.jzrq = jzrq;
	}
	public int getJysj() {
		return jysj;
	}
	public void setJysj(int jysj) {
		this.jysj = jysj;
	}
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getSp_id() {
		return sp_id;
	}
	public void setSp_id(String sp_id) {
		this.sp_id = sp_id;
	}
	public double getXsje() {
		return xsje;
	}
	public void setXsje(double xsje) {
		this.xsje = xsje;
	}
	public int getXsbs() {
		return xsbs;
	}
	public void setXsbs(int xsbs) {
		this.xsbs = xsbs;
	}
	public double getYhje() {
		return yhje;
	}
	public void setYhje(double yhje) {
		this.yhje = yhje;
	}
	public int getXssl() {
		return xssl;
	}
	public void setXssl(int xssl) {
		this.xssl = xssl;
	}
	public double getXsje_hy() {
		return xsje_hy;
	}
	public void setXsje_hy(double xsje_hy) {
		this.xsje_hy = xsje_hy;
	}
	public double getYhje_hy() {
		return yhje_hy;
	}
	public void setYhje_hy(double yhje_hy) {
		this.yhje_hy = yhje_hy;
	}
	public int getHybs() {
		return hybs;
	}
	public void setHybs(int hybs) {
		this.hybs = hybs;
	}
	public int getHth() {
		return hth;
	}
	public void setHth(int hth) {
		this.hth = hth;
	}
	public int getV_XSSD1() {
		return v_XSSD1;
	}
	public void setV_XSSD1(int v_XSSD1) {
		this.v_XSSD1 = v_XSSD1;
	}
	public int getV_XSSD2() {
		return v_XSSD2;
	}
	public void setV_XSSD2(int v_XSSD2) {
		this.v_XSSD2 = v_XSSD2;
	}
	@Override
	public String toString() {
		return "PCursor [jzrq=" + jzrq + ", jzrqDate=" + jzrqDate + ", jysj="
				+ jysj + ", deptid=" + deptid + ", sp_id=" + sp_id + ", xsje="
				+ xsje + ", xsml=" + xsml + ", xsbs=" + xsbs + ", yhje=" + yhje
				+ ", xssl=" + xssl + ", xsje_hy=" + xsje_hy + ", yhje_hy="
				+ yhje_hy + ", hybs=" + hybs + ", hth=" + hth + ", v_XSSD1="
				+ v_XSSD1 + ", v_XSSD2=" + v_XSSD2 + "]";
	}
	
	
	
}
