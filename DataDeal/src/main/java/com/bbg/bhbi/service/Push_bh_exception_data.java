package com.bbg.bhbi.service;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.bbg.pojo.BbgBhExceptionOne;
import com.bbg.pojo.BbgBhExceptionThree;
import com.bbg.pojo.BbgBhExceptionTwo;
import com.bbg.pojo.Jf_Exception;
import com.bbg.pojo.RK_RISK_INTERFACE;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
@Service
public class Push_bh_exception_data {
	
	@Autowired
	JdbcTemplate mobiJdbcTemplate;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	String exp_type = "积分异常";
	
	/*
	 * 插入结果表
	 * */
	public void insertRK_RISK_INTERFACE() throws IOException{
		final List<RK_RISK_INTERFACE> res = getRK_RISK_INTERFACE();
		String sql = " insert into RK_RISK_INTERFACE(RK_MSG_ID,LEVEL_ID,CLASS_ID,RISK_TYPE_ID,ORG_ID,SEND_TIME,SOURCE_ID,"
				+ " SOURCE_TYPE_ID,RECEIVE_TIME,STATUS ,ERR_DESC,CONTENT) values(?,?,?,?,?,sysdate,?,?,sysdate,?,?,?) ";
		mobiJdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				//ps.setDate(1, java.sql.Date.valueOf(res.get(i).getJzrq().substring(0, 10)));
				RK_RISK_INTERFACE temp = res.get(i);
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				ps.setString(1, uuid);
				ps.setString(2, temp.getLevel_id());
				ps.setString(3, temp.getClass_id());
				ps.setString(4, temp.getRisk_type_id());
				ps.setString(5, temp.getOrg_id());
				//ps.setDate(6, java.sql.Date.valueOf(temp.getSend_time()));
				ps.setString(6, temp.getSource_id());
				ps.setString(7, temp.getSource_type_id());
				//ps.setDate(9, java.sql.Date.valueOf(temp.getReceive_time()));
				ps.setString(8, temp.getStatus());
				ps.setString(9, temp.getErr_desc());
				ps.setString(10, temp.getContent());
				
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return res.size();
			}
		});
	}
	
	/*
	 * 获取 RK_RISK_INTERFACE
	 * */
	public List<RK_RISK_INTERFACE> getRK_RISK_INTERFACE() throws IOException{
		List<RK_RISK_INTERFACE> res = new ArrayList<>();
		 ObjectMapper mapper = new ObjectMapper();
		String time = "2018-09-27";
		List<String> shops =  getShopList();
		List<Jf_Exception> res_jf =  getJfException_type();
		
		for(int i=0;i<shops.size();i++){
			String shop_temp = shops.get(i);
			List<BbgBhExceptionThree> res_three =  getBbgBhExceptionThreeList(time, shop_temp);
			for(int j=0;j<res_jf.size();j++){
				RK_RISK_INTERFACE res_obj = new RK_RISK_INTERFACE();
				Jf_Exception temp_jf = res_jf.get(j);
				BbgBhExceptionTwo bbgBhExceptionTwo = getBbgBhExceptionTwo(res_three, temp_jf, exp_type, shop_temp, time);
				res_obj.setLevel_id("1");
				res_obj.setClass_id("1");
				res_obj.setRisk_type_id(temp_jf.getId());
				res_obj.setOrg_id(shop_temp);
				res_obj.setSource_id("bh_"+shop_temp);
				res_obj.setContent(mapper.writeValueAsString(bbgBhExceptionTwo));
				res.add(res_obj);
			}
		}
		
		return res;
	} 
	
	/*
	 * 生成  BbgBhExceptionTwo 数组
	 * */
	public List<BbgBhExceptionTwo> getBbgBhExceptionTwos(){
		List<BbgBhExceptionTwo> res = new ArrayList<>();
		String time = "2018-09-27";
		List<String> shops =  getShopList();
		List<Jf_Exception> res_jf =  getJfException_type();
		
		for(int i=0;i<shops.size();i++){
			String shop_temp = shops.get(i);
			List<BbgBhExceptionThree> res_three =  getBbgBhExceptionThreeList(time, shop_temp);
			for(int j=0;j<res_jf.size();j++){
				Jf_Exception temp_jf = res_jf.get(j);
				BbgBhExceptionTwo bbgBhExceptionTwo = getBbgBhExceptionTwo(res_three, temp_jf, exp_type, shop_temp, time);
				res.add(bbgBhExceptionTwo);
			}
		}
		
		return res;
	}
	
	/*
	 * 获取门店列表
	 * */
	public List<String> getShopList(){
		List<String> shops = new ArrayList<>();
		shops.add("012018");
		shops.add("012823");
		shops.add("012824");
		shops.add("012825");
		
		return shops;
	}

	
	/*
	 * 日结时间
	 * */
	public List<Jf_Exception> getJfException_type(){
		final List<Jf_Exception> res = new ArrayList<>();
		String sql = " select id,notes from LP_YCFXSM@BFDB ";
		jdbcTemplate.query(sql, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				Jf_Exception model = new Jf_Exception();
				model.setId(rs.getString("id"));
				model.setNote(rs.getString("notes"));
				res.add(model);
			}
		});
		return res;
	}
	
	public List<BbgBhExceptionThree> getBbgBhExceptionThreeList(String time,String shopId){
		final List<BbgBhExceptionThree> res = new ArrayList<>();
		String sql = " select a.old_mcid as shopId,b.shopname,to_char(rq,'yyyy-mm-dd') as rq,phone,cardno,clid,cljf,xsje,notes from "
				+ " LP_JFYCFX a,lp_bi_shop b where a.old_mcid = b.shopid and rq = to_date('"+time+"','yyyy-mm-dd') "
				+ " and shopId = '"+shopId+"' ";
		jdbcTemplate.query(sql, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				BbgBhExceptionThree model = new BbgBhExceptionThree();
				model.setShopId(rs.getString("shopId"));
				model.setShopName(rs.getString("shopname"));
				model.setTime(rs.getString("rq"));
				model.setPhone(rs.getString("phone"));
				model.setCardNo(rs.getString("cardno"));
				model.setExceptionType(rs.getString("clid"));
				model.setExceptionJf(rs.getDouble("cljf"));
				model.setExceptionXsje(rs.getDouble("xsje"));
				model.setExceptionDesc(rs.getString("notes"));
				if(model.getShopId().equals("012018")){
					model.setEmpId("T8507020019");
				}
				if(model.getShopId().equals("012823")){
					model.setEmpId("1000063573");
				}
				if(model.getShopId().equals("012824")){
					model.setEmpId("1000032711");
				}
				if(model.getShopId().equals("012825")){
					model.setEmpId("T8107284783");
				}
				res.add(model);
			}
		});
		
		return res;
	}
	
	public Map<String,String> getGridSchemaMap(){
		 Map<String,String> gridSchema = new HashMap<>();
		 gridSchema.put("cardNo", "卡号");
		 gridSchema.put("exceptionJf", "积分");
		 gridSchema.put("exceptionXsje", "涉及金额");
		 return gridSchema;
	}
	
	/*
	 * 获取  BbgBhExceptionTwo
	 * */
	public BbgBhExceptionTwo getBbgBhExceptionTwo(List<BbgBhExceptionThree> res_source,Jf_Exception jf_Exception,
			String exp_type,String shopId,String time){
		BbgBhExceptionTwo result = new BbgBhExceptionTwo();
		List<BbgBhExceptionThree> res_two = new ArrayList<>();
		 result.setSubject(shopId+"_"+exp_type+"_"+jf_Exception.getId());
		 result.setRiskDesc(jf_Exception.getNote());
		 result.setSourceObjType("bh_"+shopId);
		 result.setSourceObjId("bh_"+shopId+"_"+"000000");
		 result.setHappenTimeStr(time);
		 result.setContentView("2");
		 result.setGridSchema(getGridSchemaMap());
		 
		 for(int i=0;i<res_source.size();i++){
			 BbgBhExceptionThree temp_three = res_source.get(i);
			 if(temp_three.getExceptionType().equals(jf_Exception.getId())){
				 res_two.add(temp_three);
			 }
		 }
		 
		 result.setData(res_two);
		
		return result;
	}
	
	
	

}
