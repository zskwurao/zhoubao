package com.zypc.weekly.mapper;

import com.zypc.weekly.pojo.ReplyActicle;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReplyActicleMapper {
  /*  int countByExample(ReplyActicleExample example);

    int deleteByExample(ReplyActicleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ReplyActicle record);

    int insertSelective(ReplyActicle record);

    List<ReplyActicle> selectByExampleWithBLOBs(ReplyActicleExample example);

    List<ReplyActicle> selectByExample(ReplyActicleExample example);

    ReplyActicle selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ReplyActicle record, @Param("example") ReplyActicleExample example);

    int updateByExampleWithBLOBs(@Param("record") ReplyActicle record, @Param("example") ReplyActicleExample example);

    int updateByExample(@Param("record") ReplyActicle record, @Param("example") ReplyActicleExample example);

    int updateByPrimaryKeySelective(ReplyActicle record);

    int updateByPrimaryKeyWithBLOBs(ReplyActicle record);

    int updateByPrimaryKey(ReplyActicle record);*/

	void addReplyArticle(ReplyActicle replyActicle);

	void deleteReply(Integer id);

	List<ReplyActicle> getreplyArticleList(String uId);

	void updateReplyIsRead(Integer id);

	List<ReplyActicle> getreplyArticleListByaId(Integer aId);

	int selectCount(Integer id);
}