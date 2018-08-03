package com.zypc.weekly.mapper;

import com.zypc.weekly.pojo.User;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
  /*  int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);*/

	User selectById(String id);
	User selectUserById(String id);

	void updateColl(User user);

	void updateUserStatus(String id);

	void addUser(User user);

	int selectCount(String student_no);

	List<User> selectUserList();

	Short selectPowerById(String getuId);

	List<User> getUserList(String category);

	void updateCollection(User user);

}