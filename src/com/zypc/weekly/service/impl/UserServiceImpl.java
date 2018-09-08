package com.zypc.weekly.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.org.apache.regexp.internal.recompile;
import com.zypc.weekly.mapper.ArticleMapper;
import com.zypc.weekly.mapper.UserMapper;
import com.zypc.weekly.pojo.User;
import com.zypc.weekly.result.WeeklyResult;
import com.zypc.weekly.service.UserService;
import com.zypc.weekly.util.DateTimeUtil;

/**
 * 用户服务层
 * 
 * @author ASUS
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;
	@Autowired
	private ArticleMapper ArticleMapper;

	/**
	 * 删除收藏
	 */
	@Override
	public WeeklyResult deleteColl(String id, Integer aId) {
		try {
			User user = userMapper.selectById(id);
			String string = user.getCollection();
			StringBuffer buffer = new StringBuffer();
			String[] split = string.split(",");
			for (String s : split) {
				if (!s.equals(aId + "")) {
					buffer.append(s);
					buffer.append(",");
				}
			}
			System.out.println(buffer.length());
			if(buffer.length()<2) {
				user.setCollection(null);
			}else {
				String string2 = buffer.toString();
				string2 = string2.substring(0, string2.length() - 1);
				System.out.println(string2);
				user.setCollection(string2);
			}
			/*if (string2.charAt(string2.length() - 1) == ',') {
				string2 = string2.substring(0, string2.length() - 2);
				user.setCollection(string2);

			}*/
			userMapper.updateCollection(user);
			return WeeklyResult.ok(user);
		} catch (Exception e) {
			e.printStackTrace();
			return WeeklyResult.build(0, "删除错误");
		}
		// TODO Auto-generated method stub
	}

	/**
	 * 删除用户
	 */
	@Override
	public WeeklyResult updateUserStatus(String id) {
		try {
			userMapper.updateUserStatus(id);
			return WeeklyResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return WeeklyResult.build(0, "删除用户出错");
		}
	}

	/**
	 * 权限管理
	 */
	@Override
	public WeeklyResult updatePower(User user) {
		try {
			if(Math.abs(user.getPower())==1) {
				user.setCategory("开发");
			}else if(Math.abs(user.getPower())==2) {
				user.setCategory("运维");
			}else if(Math.abs(user.getPower())==3) {
				user.setCategory("产品");
			}
			userMapper.updateColl(user);
			return WeeklyResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return WeeklyResult.build(0, "权限设置出错");
		}
	}

	// 用户登录
	@Override
	public WeeklyResult addUser(User user) {
		try {
			userMapper.addUser(user);
			return WeeklyResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return WeeklyResult.build(0, "登录失败！");
		}
	}

	/**
	 * 判断是否是新用户
	 */
	@Override
	public int selectCount(String student_no) {
		int count = 0;
		try {
			count = userMapper.selectCount(student_no);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return count;
	}

	/**
	 * 添加收藏
	 */
	@Override
	public WeeklyResult addColl(String id, Integer aId) {
		try {
			User user = userMapper.selectById(id);
			String string = user.getCollection();

			if (string != null && string != "") {
				String s = string + "," + aId;
				user.setCollection(s);
			} else {
				String s = aId.toString();
				user.setCollection(s);
			}

			userMapper.updateColl(user);
			return WeeklyResult.ok(user);
		} catch (Exception e) {
			e.printStackTrace();
			return WeeklyResult.build(0, "添加收藏出错！");
		}

	}

	// 查询所有用户信息
	@Override
	public WeeklyResult selectUserList(String id) {
		WeeklyResult result = new WeeklyResult();
		List<User> list1 = new ArrayList<>();
		List<User> list2 = new ArrayList<>();
		List<User> list3 = new ArrayList<>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Object> map3 = new HashMap<String, Object>();

		List<Map<String, Object>> userList = new ArrayList<>();
		try {
			List<User> list = userMapper.selectUserList();
			for (User user : list) {
				if (Math.abs(user.getPower()) == 1) {
					list1.add(user);
				} else if (Math.abs(user.getPower()) == 2) {
					list2.add(user);
				} else {
					list3.add(user);
				}

			}
			if (list1 != null && list2 != null && list3 != null) {
				map1.put("category", list1.get(0).getCategory());
				map1.put("list", list1);
				map2.put("category", list2.get(0).getCategory());
				map2.put("list", list2);
				map3.put("category", list3.get(0).getCategory());
				map3.put("list", list3);
				userList.add(map1);
				userList.add(map2);
				userList.add(map3);
				result.setData(userList);
				result.setStatus(200);
				result.setMsg("OK");
			}
			User selectById = userMapper.selectById(id);
			result.setUser(selectById);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return result;
		}
	}

	/**
	 * 首页加载
	 */
	@Override
	public WeeklyResult getUserList(String id, String category) {
		try {
			List<Map<String, List<User>>> userList = new ArrayList<>();
			Map<String, List<User>> map1 = new HashMap<>();
			Map<String, List<User>> map2 = new HashMap<>();
			List<User> list1 = new ArrayList<>();
			List<User> list2 = new ArrayList<>();
			User selectById = userMapper.selectById(id);
			Date date = DateTimeUtil.getBeginThisWeek();
			List<User> list = userMapper.getUserList(category);
			// 各组负责人
			System.out.println("长度"+category);
			if (selectById.getPower() > 0) {
				for (User user : list) {
					if (!(user.getId().equals(id))) {
						user.setTime(date);
						int count = ArticleMapper.getIsSubmit(user);
						// count>0说明已提交 将其放入list1
						if (count > 0) {
							list1.add(user);
						} else {
							list2.add(user);
						}
					}
				}
				map1.put("yes", list1);
				map2.put("no", list2);
				userList.add(map1);
				userList.add(map2);
			}
			// 普通成员
			else {
				for (User user : list) {
					if (user.getPower() > 0) {
						list1.add(user);
					}
				}
				for (User user : list) {
					if (!(user.getId().equals(id)) && user.getPower() < 0) {
						list1.add(user);
					}
				}
				map1.put("yes", list1);
				map2.put("no", null);
				userList.add(map1);
				userList.add(map2);
			}
			
			return WeeklyResult.ok(userList);

		} catch (Exception e) {
			e.printStackTrace();
			return WeeklyResult.build(0, "错误！！！");
		}
	}

	@Override
	public WeeklyResult getUserList() {
		try {
			List<User> list = userMapper.selectUserList();
			return WeeklyResult.ok(list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return WeeklyResult.build(0, "查询失败！");
		}
	}

}
