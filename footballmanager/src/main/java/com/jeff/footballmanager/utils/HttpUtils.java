package com.jeff.footballmanager.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.google.gson.Gson;
import com.jeff.footballmanager.domain.DataDetails;
import com.jeff.footballmanager.domain.Result;

public class HttpUtils {

	public Result doGet(String path){
		Result result=null;
		URL url=null;
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type",  
			           "application/x-www-form-urlencoded");
			conn.connect();
			if(conn.getResponseCode()==200){
				ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
				in = conn.getInputStream();
				int len=0;
				byte[] b = new byte[1024];
				while((len=in.read(b))!=-1){
					arrayOutputStream.write(b, 0, len);
				}
				arrayOutputStream.flush();
				result = GsonUtils.getGson().fromJson(arrayOutputStream.toString(),Result.class);
				arrayOutputStream.close();
			}else{
				
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (ProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally{
			if(conn!=null){
				conn.disconnect();
			}
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public DataDetails doGetNew(String path){
		DataDetails result=null;
		//第一步：创建HttpClient对象
		HttpClient httpClient = new DefaultHttpClient();
        //第二步：创建代表请求的对象,参数是访问的服务器地址
        HttpGet httpGet = new HttpGet(path);
        try {
        	//第三步：执行请求，获取服务器发还的相应对象
            HttpResponse response = httpClient.execute(httpGet);
            //第四步：检查相应的状态是否正常：检查状态码的值是200表示正常
            	if (response.getStatusLine().getStatusCode() == 200) {
	            	//第五步：从相应对象当中取出数据，放到entity当中
	                HttpEntity entity = response.getEntity();
	                String json = EntityUtils.toString(entity, "utf-8");
	                Gson gson = new Gson();
					result = gson.fromJson(json,DataDetails.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        
		return result;
	}
	
	public Bitmap getBitMap(String http){
		URL url = null;
		HttpURLConnection conn = null;
		InputStream in = null;
		Bitmap map = null;
//		String picName = getName(http); 
		try {
			url = new URL(http);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			conn.connect();
			if(conn.getResponseCode()==200){
				BitmapFactory.Options opt = new BitmapFactory.Options();
		        opt.inPreferredConfig = Bitmap.Config.RGB_565;
		        opt.inPurgeable = true;
		        opt.inInputShareable = true;
				in = conn.getInputStream();
				map = BitmapFactory.decodeStream(in,null,opt);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null){
				conn.disconnect();
			}
		}
		return map;
	}
	
	private void saveBitMap(InputStream in,String picName) {
		File file = new File(Environment.getDownloadCacheDirectory(),picName);
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			int len = 0;
			byte[] b = new byte[1024];
			while((len=in.read(b))!=-1){
				os.write(b, 0, len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Drawable getDrawable(String http){
		URL url = null;
		HttpURLConnection conn = null;
		InputStream in = null;
		Drawable map = null;
//		File file = new File(Environment.getDownloadCacheDirectory(),"");
//		String picName = getName(http); 
		try {
			url = new URL(http);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			conn.connect();
			if(conn.getResponseCode()==200){
				in = conn.getInputStream();
				map = Drawable.createFromStream(in, null);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null){
				conn.disconnect();
			}
		}
		return map;
	}

	private Bitmap getBitMapFromCache(String picName) {
		File file = new File(Environment.getDownloadCacheDirectory(),picName);
		InputStream in = null;
		Bitmap map = null;
		try {
			in = new FileInputStream(file);
			map = BitmapFactory.decodeStream(in);
		} catch (FileNotFoundException e) {
			return null;
		}
		return map;
	}

	private String getName(String http) {
		int index = http.lastIndexOf("/");
		long time = Calendar.getInstance().getTimeInMillis();
		String name = time+http.substring(index+1, http.length()); 
		return name;
	}
	
	public String getFootBallInfo(String http,String params){
		URL url = null;
		HttpURLConnection conn = null;
		InputStream in = null;
		OutputStream os = null;
		PrintWriter printWriter = null;
		String result = "";
		try {
			url = new URL(http);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(5000);
//			conn.setRequestMethod("GET");
			conn.setRequestMethod("POST");
			// 发送POST请求必须设置如下两行  
			conn.setDoOutput(true);  
			conn.setDoInput(true);
			conn.setRequestProperty("Content-Type",  
		               "application/x-www-form-urlencoded");
//			conn.connect();
			// 获取URLConnection对象对应的输出流  
            printWriter = new PrintWriter(conn.getOutputStream());  
            // 发送请求参数  
            printWriter.write(params);  
            // flush输出流的缓冲  
            printWriter.flush();
            int i = conn.getResponseCode();
			if(conn.getResponseCode()==200){
				ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
				in = conn.getInputStream();
				int len=0;
				byte[] b = new byte[1024];
				while((len=in.read(b))!=-1){
					arrayOutputStream.write(b, 0, len);
				}
				arrayOutputStream.flush();
				result = arrayOutputStream.toString();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null){
				conn.disconnect();
			}
		}
		return result;
	}
	
//	public Result doPost(String path){
//	Result result=null;
//	HttpClient client = new DefaultHttpClient();
//	HttpPost post = new HttpPost(path);
//	
//	NameValuePair nameValuePair = new BasicNameValuePair("ok",null);
//	List<NameValuePair> pairs = new ArrayList<>();
//	pairs.add(nameValuePair);
//	
//	try {
//		
//		HttpEntity requestEntity = new UrlEncodedFormEntity(pairs);
//		post.setEntity(requestEntity);
//		
//		try {
//			
//			HttpResponse response = client.execute(post);
//			
//			if(response.getStatusLine().getStatusCode()==200){
//				HttpEntity entity = response.getEntity();
//				String json = EntityUtils.toString(entity, "utf-8");
//				Gson gson = new Gson();
//				result = gson.fromJson(json,Result.class);
//			}
//			
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	} catch (UnsupportedEncodingException e) {
//		e.printStackTrace();
//	}
//	return result;
//}
}