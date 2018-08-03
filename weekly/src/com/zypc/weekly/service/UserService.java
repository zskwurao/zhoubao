package com.zypc.weekly.service;

import com.zypc.weekly.pojo.User;
import com.zypc.weekly.result.WeeklyResult;

public interface UserService {

	/**
	 * 获得所有的用户
	 * @return
	 */
	WeeklyResult deleteColl(String id, Integer aId);

	WeeklyResult updateUserStatus(String id);


	WeeklyResult updatePower(User user);

	WeeklyResult addUser(User user);

	int selectCount(String student_no);

	WeeklyResult addColl(String id, Integer aId);

	WeeklyResult selectUserList(String id);

	WeeklyResult getUserList(String id, String category);

	WeeklyResult getUserList();
}
