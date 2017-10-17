package com.backend.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class RongUtils {  
  
    private static final String APPKEY = "RC-App-Key";  
    private static final String NONCE = "RC-Nonce";  
    private static final String TIMESTAMP = "RC-Timestamp";  
    private static final String SIGNATURE = "RC-Signature";
	private static String result;  
  
    public static String getToken(String id) {  
    	
    	String data = "portraitUri=&name=&userId=" + id;
    	String uri = "http://api.cn.ronghub.com/user/getToken.json";
  
        String nonce = String.valueOf(Math.random() * 1000000);  
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);  
        StringBuilder toSign = new StringBuilder("2OpCPyapg4c").append(nonce).append(timestamp);  
        String sign = CodeUtil.hexSHA1(toSign.toString());  
  
        URL url;
		try {
			url = new URL(uri);
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setInstanceFollowRedirects(true);
		conn.setConnectTimeout(30000);
		conn.setReadTimeout(30000);
		
		conn.setRequestProperty(APPKEY, "x18ywvqfxjzcc");
		conn.setRequestProperty(NONCE, nonce);
		conn.setRequestProperty(TIMESTAMP, timestamp);
		conn.setRequestProperty(SIGNATURE, sign);
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
		OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
        
        //write parameters
        writer.write(data);
        writer.flush();
        
        // Get the response
        StringBuffer answer = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            answer.append(line);
        }
        writer.close();
        reader.close();
        result = answer.toString();
        //Output the response
        System.out.println(answer.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return result;
        
       /* Map<String, String> headers = new HashMap<String, String>();  
        headers.put(APPKEY, "x18ywvqfxjzcc");  
        headers.put(NONCE, nonce);  
        headers.put(TIMESTAMP, timestamp);  
        headers.put(SIGNATURE, sign);  
        headers.put("Content-Type", "application/x-www-form-urlencoded");  */
//        JSONObject json = JSON.parseObject(HttpUtil.post("http://api.cn.ronghub.com/user/getToken.json", "portraitUri=&name=&userId=" + id, headers).getContent());  
//        return json.getString("token");  
  
    }  
    
    
}  