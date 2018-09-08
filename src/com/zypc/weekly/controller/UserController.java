package com.zypc.weekly.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zypc.weekly.pojo.User;
import com.zypc.weekly.result.WeeklyResult;
import com.zypc.weekly.service.UserService;
import com.zypc.weekly.util.JsonUtils;
import com.zypc.weekly.util.Token;
import com.zypc.weekly.util.UserUtil;

/**
 * 用户接口
 * 
 * @author ASUS
 *
 */
@Controller
public class UserController {

	private String REDIRECT_IP = "118.126.117.138:8080";

	@Autowired
	private UserService userService;
	private String url = "https://zypc.xupt.edu.cn/oauth/authorize";
	private String response_type = "code";
	private String client_id = "becef674aa44716a272a760c7a49a024a1ded5a7202a3bb1ad9f8519d1f4274a";
	private String redirect_uri = "http://" + REDIRECT_IP + "/weekly/oauth.action";

	@RequestMapping("/")
	public String goTO() {
		String URL = "redirect:" + url + "?response_type=" + response_type + "&client_id=" + client_id + "&state=1"
				+ "&redirect_uri=" + redirect_uri + "&scope=";
		System.err.println(URL);
		return URL;
	}

	/**
	 * 添加收藏
	 * 
	 * @param id
	 * @param aId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/user/addColl.action")
	@ResponseBody
	public WeeklyResult addColl(String id, Integer aId) {
		WeeklyResult result = userService.addColl(id, aId);
		return result;
	}

	/**
	 * 删除收藏
	 * 
	 * @param id
	 * @param aId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/user/deleteColl.action")
	@ResponseBody
	public WeeklyResult deleteColl(String id, Integer aId) {
		WeeklyResult result = userService.deleteColl(id, aId);
		return result;
	}

	/**
	 * 用户删除
	 * 
	 * @param id
	 * @param aId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/user/updateUserStatus.action")
	@ResponseBody
	public WeeklyResult updateUserStatus(String id) {
		WeeklyResult result = userService.updateUserStatus(id);
		return result;
	}

	/**
	 * 权限管理
	 * 
	 * @param id
	 * @param aId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/user/updatePower.action")
	@ResponseBody
	public WeeklyResult updatePower(User user) {
		WeeklyResult result = userService.updatePower(user);
		return result;
	}


	/**
	 * 授权回调
	 * 
	 * @param id
	 * @param aId
	 * @return
	 */
	@RequestMapping(value = "/oauth.action")
	public String oauth(String code,HttpServletResponse responses) {
		// 参数
		String redirect_uri = "http://" + REDIRECT_IP + "/weekly/oauth.action";
		String client_id = "becef674aa44716a272a760c7a49a024a1ded5a7202a3bb1ad9f8519d1f4274a";
		String scope = "2f1e06f6f19b5c4dd6adab61a579b6cc0b5d7156a57618653ae649e2b7018fe7";
		// 获取token接口
		String url = "https://zypc.xupt.edu.cn/oauth/token";
		String grant_type = "authorization_code";
		// 拼接字符串，获得token完整接口
		String toUrl = url + "?" + "grant_type=" + grant_type + "&" + "redirect_uri=" + redirect_uri + "&"
				+ "client_id=" + client_id + "&" + "code=" + code + "&" + "client_secret=" + scope;
		// 获取用户信息接口
		String URL_user = "https://zypc.xupt.edu.cn/oauth/userinfo";
		// 生成HttpClient对象
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		// 获取token为post请求
		HttpPost httpPost = new HttpPost(toUrl);
		String user_id;
		try {
			// 获取返回结果
			response = client.execute(httpPost);
			// 拿到回调的json串
			String result = EntityUtils.toString(response.getEntity());// 可以很好的处理中文，保证中文没有乱码
			// 将字符串转换成java对象并拿到token
			Token jsonToPojo = JsonUtils.jsonToPojo(result, Token.class);
			String access_token = jsonToPojo.getAccess_token();
			// 拼接获取用户信息的完整url
			String URL_use = URL_user + "?" + "access_token=" + access_token;
			// 获取用户信息为get请求
			HttpGet httpGet = new HttpGet(URL_use);
			// 获取返回结果
			response = client.execute(httpGet);
			// 取出实体
			String resultGet = EntityUtils.toString(response.getEntity());

			// 转json为对象
			UserUtil userUtil = JsonUtils.jsonToPojo(resultGet, UserUtil.class);
			user_id = userUtil.getStudent_no();
			// 判断该用户是否第一次登录
			int count = userService.selectCount(userUtil.getStudent_no());
			// count为0代表用户未登录过，需要向用户表插入新数据
			if (count == 0) {
				// 取出需要用到的信息
				User user = new User();
				// 完善用户信息
				String imageUrl = "https://zypc.xupt.edu.cn/u/" + userUtil.getUsername() + "/avatar";
				user.setId(userUtil.getStudent_no());
				user.setCollection(null);
				user.setName(userUtil.getRealname());
				user.setHeadImage(imageUrl);
				user.setCategory("开发");
				user.setPower((short) -1);
				userService.addUser(user);
			}
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// return "redirect:http://www.baidu.com";
			return null;
		}
		Cookie cookie = new Cookie("user_id", user_id);
	    responses.addCookie(cookie);
		return "redirect:index.html";
	}

	/**
	 * 登录接口
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/user/login.action")
	@ResponseBody
	public WeeklyResult selectUserList(HttpServletRequest request) {
		String user_id = "";
		 Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	      for (Cookie cookie : cookies) {
	        if (cookie.getName().equals("user_id")) {
	          user_id = cookie.getValue();
	        }
	      }
	    }
	    WeeklyResult result = this.userService.selectUserList(user_id);
	    return result;
	}

	/**
	 * 加载首页
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/user/getUserList.action")
	@ResponseBody
	public WeeklyResult getUserList(String id, String category) {
		WeeklyResult result = userService.getUserList(id, category);
		return result;
	}

	/**
	 * 获取信息列表（权限）
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/getUserList.action")
	@ResponseBody
	public WeeklyResult getUserList() {
		WeeklyResult result = userService.getUserList();
		return result;
	}
}
