package com.bbg.bhbi.service;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbg.pojo.PCursor;


/*
 * 因新版实时报表增加战略品牌
 * 需要增加4个字段 
 * 次版本在测试阶段  没有投入使用
 * */

@Controller
@Service
public class Syn_LP_REAL_BFXS_HZ_bt  {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/*
	 * 实时报表  数据刷新
	 * */
	@Transactional(value="bhbiTransactionManager")
	public void beginDeal() throws IOException{
		//获取日结时间(当日时间为日结时间+1天)
		String time  = getRclTime();
		//删除历史数据 (1.清空 LP_REAL_BFXS_REALTIME 临时表数据;2.删除 LP_REAL_BFXS_HZ 表当天数据)
		deleteData(time);
		//插入数据进临时表 LP_REAL_BFXS_REALTIME
		exec_real_xsbf(time);
		//向目标表插入数据
		insertInto_LP_REAL_BFXS_HZ(time);
		
	}
	
	/*
	 * 日结时间
	 * */
	public String getRclTime(){
		final String[] result = new String[1];
		String sql = "select to_char((max(RQ)+1),'yyyymmdd') AS RQ   from RCL@bfdb where libid =101010 and (STATUS=0 or STATUS=41)";
		jdbcTemplate.query(sql, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				result[0] = rs.getString("RQ");
			}
		});
		return result[0];
	}
	
	/*
	 * 清空中间表：LP_REAL_BFXS_REALTIME
	 * 实时数据表：LP_REAL_BFXS_HZ 当天数据
	 * */
	public void deleteData(String time){
		String sql = " Truncate  table  LP_REAL_BFXS_REALTIME ";
		//String sql2 = " DELETE FROM  LP_REAL_BFXS_HZ WHERE JZRQ >= TO_DATE('"+time+"','yyyymmdd') ";//正式
		String sql2 = " DELETE FROM  LP_REAL_BFXS_HZ_TEST WHERE JZRQ >= TO_DATE('"+time+"','yyyymmdd') ";//测试
		
		jdbcTemplate.execute(sql);
		jdbcTemplate.execute(sql2);
	}
	
	/*
	 * 获取 PCursor 
	 * */
	@SuppressWarnings("unchecked")
	public Map<String, PCursor> getPCursor(String rq) {
		final Map<String, PCursor> msp = new HashMap<>();
		
		/*String sql = " select A.JZRQ, k.HTH, to_number(to_char(A.JYSJ, 'HH24')) * 100 +to_number(to_char(A.JYSJ, 'MI')) JYSJ,"
				+ " C.DEPTID, C.SP_ID, 0 AS xsje, COUNT(distinct A.SKTNO || A.JLBH) XSBS, 0 AS YHJE, 0 AS XSSL, "
				+ "0 AS XSJE_HY, 0 AS YHJE_HY, count(distinct A.SKTNO || A.JLBH) * decode(viptype, -1, "
				+ "decode(phone, null, 0, 1), 1) HYBS from XSJL@bfdb A, XSJLC@bfdb C, SKT@bfdb T ,SPXX@BFDB K where A.SKTNO = "
				+ "C.SKTNO and A.JLBH = C.JLBH and A.JYSJ > TO_DATE('"
				+ rq
				+ "', 'yyyy/mm/dd') and A.SKTNO = T.SKTNO and k.SP_ID =  C.SP_ID"
				
				+ " group by A.JZRQ, k.HTH, to_number(to_char(A.JYSJ, 'HH24')) * 100 + "
				+ "to_number(to_char(A.JYSJ, 'MI')), C.DEPTID, C.SP_ID, phone, VIPTYPE UNION ALL select A.JZRQ,  k.HTH,"
				+ "to_number(to_char(A.JYSJ, 'HH24')) * 100 + to_number(to_char(A.JYSJ, 'MI')) JYSJ, C.DEPTID, "
				+ "C.SP_ID, sum(C.XSJE - C.YHJE) XSJE, 0 XSBS, sum(C.YHJE) YHJE, sum(C.XSSL) XSSL, "
				+ "sum((C.XSJE - C.YHJE) * decode(viptype, -1, decode(phone, null, 0, 1), 1)) XSJE_HY, "
				+ "sum(C.yhje * decode(viptype, -1, decode(phone, null, 0, 1), 1)) YHJE_HY, 0 HYBS from XSJL@bfdb A, "
				+ "XSJLC@bfdb C, SKT@bfdb T ,SPXX@BFDB K where A.SKTNO = C.SKTNO and A.JLBH = C.JLBH "
				+ "and A.JYSJ > TO_DATE('"
				+ rq
				+ "', 'yyyy/mm/dd') and A.SKTNO = T.SKTNO and k.SP_ID =  C.SP_ID group by A.JZRQ, k.HTH, "
				+ "to_number(to_char(A.JYSJ, 'HH24')) * 100 + to_number(to_char(A.JYSJ, 'MI')), C.DEPTID, C.SP_ID ";*/
		String sql = " select A.JZRQ, k.HTH, to_number(to_char(A.JYSJ, 'HH24')) * 100 + to_number(to_char(A.JYSJ, 'MI')) JYSJ, "
				+ " C.DEPTID, C.SP_ID, 0 AS xsje, COUNT(distinct A.SKTNO || A.JLBH) XSBS, 0 AS YHJE, 0 AS XSSL, 0 AS XSJE_HY, "
				+ " 0 AS YHJE_HY, count(distinct A.SKTNO || A.JLBH) * decode(viptype, -1, decode(phone, null, 0, 1), 1) HYBS, "
				+ " 0 xsml from XSJL@bfdb A, XSJLC@bfdb C, SKT@bfdb T, SPXX@BFDB K where A.SKTNO = C.SKTNO and A.JLBH = C.JLBH "
				+ " and A.JYSJ >= TO_DATE('"+rq+"', 'yyyy/mm/dd') and A.SKTNO = T.SKTNO and k.SP_ID = C.SP_ID group by A.JZRQ, "
				+ " k.HTH, to_number(to_char(A.JYSJ, 'HH24')) * 100 + to_number(to_char(A.JYSJ, 'MI')), C.DEPTID, C.SP_ID, phone, "
				+ " VIPTYPE UNION ALL select A.JZRQ, k.HTH, "
				+ " to_number(to_char(A.JYSJ, 'HH24')) * 100 + to_number(to_char(A.JYSJ, 'MI')) JYSJ, C.DEPTID, C.SP_ID, "
				+ " sum(C.XSJE - C.YHJE) XSJE, 0 XSBS, sum(C.YHJE) YHJE, sum(C.XSSL) XSSL, "
				+ " sum((C.XSJE - C.YHJE) * decode(viptype, -1, decode(phone, null, 0, 1), 1)) XSJE_HY, "
				+ " sum(C.yhje * decode(viptype, -1, decode(phone, null, 0, 1), 1)) YHJE_HY, 0 HYBS, "
				+ " SUM (C.XSJE - C.YHJE) - SUM (CASE WHEN k.HSFS IN (0, 1) THEN XSSL * P.COST ELSE (C.XSJE) * (1 - P.COST) END) "
				+ " AS XSML from XSJL@bfdb A, XSJLC@bfdb C, SKT@bfdb T, SPXX@BFDB K,SP_COST@bfdb P where A.SKTNO = C.SKTNO "
				+ " and A.JLBH = C.JLBH and A.JYSJ >= TO_DATE('"+rq+"', 'yyyy/mm/dd') and A.SKTNO = T.SKTNO and k.SP_ID = "
				+ " C.SP_ID and DECODE(k.HSFS, 0, C.DEPTID, 1, C.deptid, 0) = P.deptid AND P.SP_ID = k.SP_ID group by A.JZRQ, "
				+ " k.HTH, to_number(to_char(A.JYSJ, 'HH24')) * 100 + to_number(to_char(A.JYSJ, 'MI')), C.DEPTID, C.SP_ID ";
        jdbcTemplate.setFetchSize(5000);
		jdbcTemplate.query(sql, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				PCursor crMC = new PCursor();
				crMC.setJzrq(rs.getString("jzrq"));
				crMC.setJysj(rs.getInt("jysj"));
				crMC.setDeptid(rs.getString("deptid"));
				crMC.setSp_id(rs.getString("sp_id"));
				crMC.setXsje(rs.getDouble("xsje"));
				crMC.setXsml(rs.getDouble("xsml"));
				crMC.setXsbs(rs.getInt("xsbs"));
				crMC.setYhje(rs.getDouble("yhje"));
				crMC.setXssl(rs.getInt("xssl"));
				crMC.setXsje_hy(rs.getDouble("xsje_hy"));
				crMC.setYhje_hy(rs.getDouble("yhje_hy"));
				crMC.setHybs(rs.getInt("hybs"));
				crMC.setV_XSSD1(getV_XSSD(crMC.getJysj())[0]);
				crMC.setV_XSSD2(getV_XSSD(crMC.getJysj())[1]);
				crMC.setHth(rs.getInt("HTH"));
				String key = crMC.getJzrq() + "," + crMC.getDeptid() + ","
						+ crMC.getHth() + "," + crMC.getSp_id() + ","
						+ crMC.getV_XSSD1();

				if (msp.containsKey(key)) {
					PCursor temPc = msp.get(key);
					temPc.setXsbs(temPc.getXsbs() + crMC.getXsbs());
					temPc.setXsje(temPc.getXsje() + crMC.getXsje());
					temPc.setXsml(temPc.getXsml() + crMC.getXsml());
					temPc.setYhje(temPc.getYhje() + crMC.getYhje());
					temPc.setXssl(temPc.getXssl() + crMC.getXssl());
					temPc.setHybs(temPc.getHybs() + crMC.getHybs());
					temPc.setXsje_hy(temPc.getXsje_hy() + crMC.getXsje_hy());
					temPc.setYhje_hy(temPc.getYhje_hy() + crMC.getYhje_hy());
				} else {
					msp.put(key, crMC);
				}

			}
		});
		return msp;
	}
	
	public int[] getV_XSSD(int v_JYSJ) {
		int[] v_XSSD = new int[2];
		int v_XSSD1 = (int) (100 * Math.floor(v_JYSJ / 100) + Math
				.floor((v_JYSJ - 100 * Math.floor(v_JYSJ / 100)) / 30) * 30);

		if (v_XSSD1 == 0) {
			v_XSSD1 = 2400;
		} else if (v_XSSD1 == 30) {
			v_XSSD1 = 2430;
		} else if (v_XSSD1 == 100) {
			v_XSSD1 = 2500;
		} else if (v_XSSD1 == 130) {
			v_XSSD1 = 2530;
		} else if (v_XSSD1 == 200) {
			v_XSSD1 = 2600;
		} else if (v_XSSD1 == 230) {
			v_XSSD1 = 2630;
		} else if (v_XSSD1 == 300) {
			v_XSSD1 = 2700;
		} else if (v_XSSD1 == 330) {
			v_XSSD1 = 2730;
		} else if (v_XSSD1 == 400) {
			v_XSSD1 = 2800;
		} else if (v_XSSD1 == 430) {
			v_XSSD1 = 2830;
		} else if (v_XSSD1 == 500) {
			v_XSSD1 = 2900;
		} else if (v_XSSD1 == 530) {
			v_XSSD1 = 2930;
		} else if (v_XSSD1 == 600) {
			v_XSSD1 = 3000;
		} else if (v_XSSD1 == 630) {
			v_XSSD1 = 3300;
		}

		int v_XSSD2 = v_XSSD1 + 30;

		if ((v_XSSD2 - 100 * Math.floor(v_XSSD2 / 100)) == 60) {
			v_XSSD2 = (int) ((1 + Math.floor(v_XSSD2 / 100)) * 100);
		}
		v_XSSD[0] = v_XSSD1;
		v_XSSD[1] = v_XSSD2;
		return v_XSSD;
	}
	
	/*
	 * 插入实时临时表      LP_REAL_BFXS_REALTIME
	 * */
	public void exec_real_xsbf(String time) throws IOException {
		Map<String, PCursor> mpc = getPCursor(time);
		final List<PCursor> res = new ArrayList<>();
		Iterator<Entry<String, PCursor>> it = mpc.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, PCursor> entry = it.next();
			PCursor value = (PCursor) entry.getValue();
			res.add(value);
		}
		
		String sql = " insert into LP_REAL_BFXS_REALTIME(JZRQ,DEPTID,HTH,SP_ID,XSSD1,XSSD2,XSJE,XSBS,YHJE,XSSL,"
				+ "HYXS,YHJE_HY,HYBS,XSML) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		jdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setDate(1, java.sql.Date.valueOf(res.get(i).getJzrq().substring(0, 10)));
				ps.setInt(2, Integer.parseInt(res.get(i).getDeptid()));
				ps.setInt(3, res.get(i).getHth());
				ps.setInt(4, Integer.parseInt(res.get(i).getSp_id()));
				ps.setInt(5, res.get(i).getV_XSSD1());
				ps.setInt(6, res.get(i).getV_XSSD2());
				ps.setDouble(7, res.get(i).getXsje());
				ps.setInt(8, res.get(i).getXsbs());
				ps.setDouble(9, res.get(i).getYhje());
				ps.setInt(10, res.get(i).getXssl());
				ps.setDouble(11,res.get(i).getXsje_hy() );
				ps.setDouble(12, res.get(i).getYhje_hy());
				ps.setInt(13,  res.get(i).getHybs());
				ps.setDouble(14, res.get(i).getXsml());
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return res.size();
			}
		});
		
	}
	
	/*
	 * LP_REAL_BFXS_HZ 插入数据进目标表
	 * */
	
public void insertInto_LP_REAL_BFXS_HZ(String strDate){
		//正式
		/*String sql = 
				"INSERT INTO LP_REAL_BFXS_HZ(JZRQ,SPCODE,NAME,XSSD1,XSSD2,XSBS,XSJE,YHJE,XSSL,HYXS,YHJE_HY,HYBS,"
				+ "DL,DLNAME,ZL,ZLNAME,XL, XLNAME,BMDM,DEPT_NAME,XSDD,XSDDMC,SHOPID,SHOPNAME,XSML)  "
				+"SELECT JZRQ,SPCODE,NAME,XSSD1,XSSD2,SUM(XSBS) XSBS,SUM(XSJE) XSJE,SUM(YHJE) YHJE,SUM(XSSL) XSSL,"
				+ "SUM(HYXS) HYXS,SUM(YHJE_HY) YHJE_HY, SUM(HYBS) HYBS, DL,DLNAME,ZL,ZLNAME,XL,XLNAME,BMDM,DEPT_NAME,"
				+ "XSDD,XSDDMC,OLD_MCID,MCNAME,SUM(XSML) XSML FROM ( SELECT jzrq,A.deptid,A.hth,xssd1,A.sp_id,xssd2,xsbs,xsje,yhje,"
				+ "xssl,hyxs,yhje_hy,hybs, D.BMDM AS BMDM,  DEPT_NAME, B.SPCODE AS SPCODE,B.NAME AS NAME,"
				+ "SUBSTR(B.SPFL,1,2) AS DL,K.DLNAME AS DLNAME, SUBSTR(B.SPFL,1,4) AS ZL,K.ZLNAME AS ZLNAME,"
				+ "SUBSTR(B.SPFL,1,6) AS XL,K.XLNAME AS XLNAME, SUBSTR(S.XSDDDM,1,6) AS XSDD, XSDDMC, "
				+ "p.OLD_MCID AS OLD_MCID,P.NAME AS MCNAME,XSML FROM LP_REAL_BFXS_REALTIME A , BM@BFDB D,SPXX@BFDB B,"
				+ "XSDD@BFDB S,MCDEF_LP@BFDB P ,BH_PL_PZ K WHERE A.DEPTID = D.DEPTID AND A.SP_ID = B.SP_ID AND "
				+ "D.XSDDID = S.XSDDID AND SUBSTR(D.BMDM,1,4)=P.BMDM AND SUBSTR(B.SPFL,1,6) = K.XL AND "
				+ "A.JZRQ = TO_DATE('"+strDate+"','yyyy/mm/dd') ) GROUP BY JZRQ,SPCODE,NAME,XSSD1,XSSD2,DL,DLNAME,ZL,"
				+ "ZLNAME,XL,XLNAME,BMDM,DEPT_NAME,XSDD,XSDDMC,OLD_MCID,MCNAME";*/
	    //测试
		/*String sql = 
				"INSERT INTO LP_REAL_BFXS_HZ_TEST(JZRQ,SPCODE,NAME,XSSD1,XSSD2,XSBS,XSJE,YHJE,XSSL,HYXS,YHJE_HY,HYBS,"
				+ "DL,DLNAME,ZL,ZLNAME,XL, XLNAME,BMDM,DEPT_NAME,XSDD,XSDDMC,SHOPID,SHOPNAME,XSML)  "
				+"SELECT JZRQ,SPCODE,NAME,XSSD1,XSSD2,SUM(XSBS) XSBS,SUM(XSJE) XSJE,SUM(YHJE) YHJE,SUM(XSSL) XSSL,"
				+ "SUM(HYXS) HYXS,SUM(YHJE_HY) YHJE_HY, SUM(HYBS) HYBS, DL,DLNAME,ZL,ZLNAME,XL,XLNAME,BMDM,DEPT_NAME,"
				+ "XSDD,XSDDMC,OLD_MCID,MCNAME,SUM(XSML) XSML FROM ( SELECT jzrq,A.deptid,A.hth,xssd1,A.sp_id,xssd2,xsbs,xsje,yhje,"
				+ "xssl,hyxs,yhje_hy,hybs, D.BMDM AS BMDM,  DEPT_NAME, B.SPCODE AS SPCODE,B.NAME AS NAME,"
				+ "SUBSTR(B.SPFL,1,2) AS DL,K.DLNAME AS DLNAME, SUBSTR(B.SPFL,1,4) AS ZL,K.ZLNAME AS ZLNAME,"
				+ "SUBSTR(B.SPFL,1,6) AS XL,K.XLNAME AS XLNAME, SUBSTR(S.XSDDDM,1,6) AS XSDD, XSDDMC, "
				+ "p.OLD_MCID AS OLD_MCID,P.NAME AS MCNAME,XSML FROM LP_REAL_BFXS_REALTIME A , BM@BFDB D,SPXX@BFDB B,"
				+ "XSDD@BFDB S,MCDEF_LP@BFDB P ,BH_PL_PZ K WHERE A.DEPTID = D.DEPTID AND A.SP_ID = B.SP_ID AND "
				+ "D.XSDDID = S.XSDDID AND SUBSTR(D.BMDM,1,4)=P.BMDM AND SUBSTR(B.SPFL,1,6) = K.XL AND "
				+ "A.JZRQ = TO_DATE('"+strDate+"','yyyy/mm/dd') ) GROUP BY JZRQ,SPCODE,NAME,XSSD1,XSSD2,DL,DLNAME,ZL,"
				+ "ZLNAME,XL,XLNAME,BMDM,DEPT_NAME,XSDD,XSDDMC,OLD_MCID,MCNAME";*/
	    String sql = " INSERT INTO LP_REAL_BFXS_HZ_TEST(JZRQ,SPCODE,NAME,XSSD1,XSSD2,XSBS,XSJE,YHJE,XSSL,HYXS,YHJE_HY,HYBS, DL,"
	    		+ " DLNAME,ZL,ZLNAME,XL, XLNAME,BMDM,DEPT_NAME,XSDD,XSDDMC,SHOPID,SHOPNAME,XSML,GHDWDM,GYSZZ,SBID,HTH) SELECT "
	    		+ " JZRQ,SPCODE,NAME,XSSD1,XSSD2,SUM(XSBS) XSBS,SUM(XSJE) XSJE,SUM(YHJE) YHJE,SUM(XSSL) XSSL, SUM(HYXS) HYXS,"
	    		+ " SUM(YHJE_HY) YHJE_HY, SUM(HYBS) HYBS, DL,DLNAME,ZL,ZLNAME,XL,XLNAME,BMDM,DEPT_NAME, XSDD,XSDDMC,OLD_MCID,"
	    		+ " MCNAME,SUM(XSML) XSML, CODE AS GHDWDM,XSNR AS GYSZZ,SB AS SBID,HTH FROM ( SELECT jzrq,A.deptid,A.hth,xssd1,"
	    		+ " A.sp_id,xssd2,xsbs,xsje,yhje, xssl,hyxs,yhje_hy,hybs, D.BMDM AS BMDM, DEPT_NAME, B.SPCODE AS SPCODE,"
	    		+ " B.NAME AS NAME, SUBSTR(B.SPFL,1,2) AS DL,K.DLNAME AS DLNAME, SUBSTR(B.SPFL,1,4) AS ZL,K.ZLNAME AS ZLNAME, "
	    		+ " SUBSTR(B.SPFL,1,6) AS XL,K.XLNAME AS XLNAME, SUBSTR(S.XSDDDM,1,6) AS XSDD, XSDDMC, p.OLD_MCID AS OLD_MCID,"
	    		+ " P.NAME AS MCNAME,XSML , W.CODE, (SELECT XSNR FROM WLDW_SXDY@BFDB Y WHERE W.QYLX = Y.SXID(+)) XSNR, B.SB "
	    		+ " FROM LP_REAL_BFXS_REALTIME A , BM@BFDB D,SPXX@BFDB B,XSDD@BFDB S,MCDEF_LP@BFDB P ,BH_PL_PZ K,HT@BFDB T,"
	    		+ " WLDW@BFDB W WHERE A.DEPTID = D.DEPTID AND A.SP_ID = B.SP_ID AND D.XSDDID = S.XSDDID AND "
	    		+ " SUBSTR(D.BMDM,1,4)=P.BMDM AND SUBSTR(B.SPFL,1,6) = K.XL AND A.JZRQ = TO_DATE('"+strDate+"','yyyy/mm/dd') "
	    		+ " AND B.HTH=T.HTH AND T.GHDWDM=W.CODE ) GROUP BY JZRQ,SPCODE,NAME,XSSD1,XSSD2,DL,DLNAME,ZL, ZLNAME,XL,XLNAME,"
	    		+ " BMDM,DEPT_NAME,XSDD,XSDDMC,OLD_MCID,MCNAME,CODE,XSNR,SB,HTH ";
		jdbcTemplate.execute(sql);
	}
	
}
