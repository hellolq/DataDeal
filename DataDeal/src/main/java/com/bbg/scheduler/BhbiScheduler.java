package com.bbg.scheduler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.bbg.bhbi.service.Syn_LP_REAL_BFXS_HZ;


@Component 
@EnableScheduling 
public class BhbiScheduler {
	
	private final static Logger logger = (Logger) LoggerFactory.getLogger(BhbiScheduler.class);
	
	@Autowired
	private Syn_LP_REAL_BFXS_HZ syn_LP_REAL_BFXS_HZ;
	
	public void update_LP_REAL_BFXS_HZ(){  
		try{
			long start_time = System.currentTimeMillis();
	 	   syn_LP_REAL_BFXS_HZ.beginDeal();
	 	   long end_time =  (System.currentTimeMillis() - start_time)/1000;
	 	  logger.info("execute time : {} 秒",end_time);
		}catch(Exception e){
			logger.error("Exception Message :{}",e);
		}
    } 

}
