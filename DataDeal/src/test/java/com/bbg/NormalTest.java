package com.bbg;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.bbg.pojo.MsgParam;
import com.bbg.tools.BbgSendMessage;
import com.bbg.tools.BbgTools;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NormalTest {
	
	
	
	
	@Test
	public void test1() throws Exception {
		String content = "<a href = '#'>测试</a>";
		String result  = BbgSendMessage.sendMessage("1000063573", content);
		System.out.println(result);
	}

}
