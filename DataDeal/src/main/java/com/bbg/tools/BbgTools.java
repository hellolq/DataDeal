package com.bbg.tools;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class BbgTools {
	
	public static String sendHttpPost(String url, String body) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
	
		HttpPost httpPost = new HttpPost(url);
		
		//装配请求参数
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		NameValuePair paramMap = new BasicNameValuePair("data", body);
		list.add(paramMap);
//		httpPost.addHeader("Content-Type", "application/json");
		httpPost.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));

		CloseableHttpResponse  response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		String responseContent = EntityUtils.toString(entity, "UTF-8"); 

		response.close();
		httpClient.close();
		return responseContent;
		}

}
