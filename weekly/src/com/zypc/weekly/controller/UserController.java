package com.zypc.weekly.controller;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.alibaba.druid.support.json.JSONUtils;
import com.zypc.weekly.pojo.User;
import com.zypc.weekly.result.WeeklyResult;
import com.zypc.weekly.service.UserService;
import com.zypc.weekly.util.JsonUtils;
import com.zypc.weekly.util.Token;
import com.zypc.weekly.util.UserUtil;


/**
 * 用户接口
 * @author ASUS
 *
 */
@Controller
public class UserController {

	@Autowired
	private UserService userService;
	/*
	@RequestMapping("/user/list")
	@ResponseBody
	public WeeklyResult getAllUser() {
		WeeklyResult result = userService.getUserList();
		return result;
	}*/
	private String url = "https://zypc.xupt.edu.cn/oauth/authorize";
	private String response_type = "code";
	private String client_id = "becef674aa44716a272a760c7a49a024a1ded5a7202a3bb1ad9f8519d1f4274a";
	private String redirect_uri = "http://127.0.0.1:8080/oauth.action";
	@RequestMapping("/")
	public String goTO() {
		String URL = "redirect:"+url+"?response_type="+response_type+"&client_id="+client_id+"&state=1"+"&redirect_uri="+redirect_uri+"&scope=";
		return URL;
	}
	
	/**
	 * 添加收藏
	 * @param id
	 * @param aId
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/addColl.action")
	@ResponseBody
	public WeeklyResult addColl(String id,Integer aId) {
		WeeklyResult result = userService.addColl(id,aId);
		return result; 	
	}
	/**
	 * 删除收藏
	 * @param id
	 * @param aId
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/deleteColl.action")
	@ResponseBody
	public WeeklyResult deleteColl(String id,Integer aId) {
		WeeklyResult result = userService.deleteColl(id,aId);
		return result; 	
	}
	/**
	 * 用户删除
	 * @param id
	 * @param aId
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/updateUserStatus.action")
	@ResponseBody
	public WeeklyResult updateUserStatus(String id) {
		WeeklyResult result = userService.updateUserStatus(id);
		return result; 	
	}
	/**
	 * 权限管理
	 * @param id
	 * @param aId
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/updatePower.action")
	@ResponseBody
	public WeeklyResult updatePower(User user) {
		WeeklyResult result = userService.updatePower(user);
		return result; 	
	}
	
	
	private String user_id ="04161111";
	
	/**
	 * 授权回调
	 * @param id
	 * @param aId
	 * @return
	 */
	@RequestMapping(value="/oauth.action")
	public String oauth(String code) {
		//参数
		String redirect_uri = "http://127.0.0.1:8080/oauth.action";
		String client_id = "becef674aa44716a272a760c7a49a024a1ded5a7202a3bb1ad9f8519d1f4274a";
		String scope = "2f1e06f6f19b5c4dd6adab61a579b6cc0b5d7156a57618653ae649e2b7018fe7";
		//获取token接口
		String url = "https://zypc.xupt.edu.cn/oauth/token";
		String grant_type = "authorization_code";
		//拼接字符串，获得token完整接口
		String toUrl = url + "?"+"grant_type="+grant_type+"&"+"redirect_uri="+redirect_uri+"&"+"client_id="+client_id+"&"+"code="+code+"&"+"client_secret="+scope;
		//获取用户信息接口
		String URL_user = "https://zypc.xupt.edu.cn/oauth/userinfo";
		//生成HttpClient对象
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;  
		//获取token为post请求
		HttpPost httpPost = new HttpPost(toUrl);
		try {
			//获取返回结果
			   response = client.execute(httpPost);
			   //拿到回调的json串
			   String result = EntityUtils.toString(response.getEntity());//可以很好的处理中文，保证中文没有乱码
			   //将字符串转换成java对象并拿到token
			   Token jsonToPojo = JsonUtils.jsonToPojo(result, Token.class);
			   String access_token = jsonToPojo.getAccess_token();
			   //拼接获取用户信息的完整url
			   String URL_use = URL_user+"?"+"access_token="+access_token;
			   //获取用户信息为get请求
			   HttpGet httpGet = new HttpGet(URL_use);
			   //获取返回结果
			   response = client.execute(httpGet);
			   //取出实体
			   String resultGet = EntityUtils.toString(response.getEntity());
			   
			   //转json为对象
			   UserUtil userUtil = JsonUtils.jsonToPojo(resultGet, UserUtil.class);
			   user_id = userUtil.getStudent_no();
			 //判断该用户是否第一次登录
			   int count = userService.selectCount(userUtil.getStudent_no());
			   //count为0代表用户未登录过，需要向用户表插入新数据
			   if(count==0) {
			   //取出需要用到的信息
			   User user = new User();
			   //完善用户信息
			   String imageUrl = "https://zypc.xupt.edu.cn/u/"+userUtil.getUsername()+"/avatar";
			   user.setId(userUtil.getStudent_no());
			   user.setCollection(null);
			   user.setName(userUtil.getRealname());
			   user.setHeadImage(imageUrl);
			   user.setCategory("开发");
			   user.setPower((short) -1);
			   WeeklyResult weeklyResult = userService.addUser(user);
			   }
			   //WeeklyResult weeklyResult = userService.selectUserList(userUtil.getStudent_no());
			   //return weeklyResult;
			} 
		
		catch (Exception e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
			  // return "redirect:http://www.baidu.com";
			   return null;
			}
		return "redirect:http://www.baidu.com";
	}
	/**
	 * 登录接口
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/login.action")
	@ResponseBody
	public WeeklyResult selectUserList() {
		WeeklyResult result = userService.selectUserList(user_id);
		return result;
	}
	/**
	 * 加载首页
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/getUserList.action")
	@ResponseBody
	public WeeklyResult getUserList(String id,String category) {
		WeeklyResult result = userService.getUserList(id,category);
		return result;
	}
	/**
	 * 获取信息列表（权限）
	 */
	@RequestMapping(method=RequestMethod.POST,value="/getUserList.action")
	@ResponseBody
	public WeeklyResult getUserList() {
		WeeklyResult result = userService.getUserList();
		return result;
	}
}
