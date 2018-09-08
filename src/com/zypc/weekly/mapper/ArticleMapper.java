package com.zypc.weekly.mapper;

import com.zypc.weekly.pojo.Article;
import com.zypc.weekly.pojo.User;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArticleMapper {
   /* int countByExample(ArticleExample example);

    int deleteByExample(ArticleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ArticleWithBLOBs record);

    int insertSelective(ArticleWithBLOBs record);

    List<ArticleWithBLOBs> selectByExampleWithBLOBs(ArticleExample example);

    List<Article> selectByExample(ArticleExample example);

    ArticleWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ArticleWithBLOBs record, @Param("example") ArticleExample example);

    int updateByExampleWithBLOBs(@Param("record") ArticleWithBLOBs record, @Param("example") ArticleExample example);

    int updateByExample(@Param("record") Article record, @Param("example") ArticleExample example);

    int updateByPrimaryKeySelective(ArticleWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ArticleWithBLOBs record);

    int updateByPrimaryKey(Article record);*/

	long addArticle(Article article);

	List<Article> getNoActicleList(String uId);

	Article getActicleById(Integer id);

	void updateArticle(Article article);

	void deleteActicle(Integer id);

	Article getActicle(Integer parseInt);

	List<Article> getArticleList(String uId);
	int getIsSubmit(User user);
}