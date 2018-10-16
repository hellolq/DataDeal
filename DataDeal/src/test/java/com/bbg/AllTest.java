package com.bbg;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bbg.bhbi.service.Push_bh_exception_data;
import com.bbg.bhbi.service.Push_bh_exception_data_new;
import com.bbg.bhbi.service.Syn_LP_REAL_BFXS_HZ;
import com.bbg.pojo.BbgBhExceptionOne;
import com.bbg.pojo.BbgBhExceptionTwo;
import com.bbg.pojo.RK_RISK_INTERFACE;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=MainApplication.class)// 指定spring-boot的启动类
public class AllTest {
	private static ExecutorService es = Executors.newFixedThreadPool(20);
	
	@Autowired
	Push_bh_exception_data_new push_bh_exception_data_new;
	

	
	@Test
	public void test() throws Exception {
		 //ObjectMapper mapper = new ObjectMapper();
		 push_bh_exception_data_new.insertRK_RISK_INTERFACE();
		/*List<RK_RISK_INTERFACE> res = push_bh_exception_data_new.getRK_RISK_INTERFACE();
		System.out.println(mapper.writeValueAsString(res));*/
		System.out.println("over");
	}
	
	
}
