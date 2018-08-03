package com.zypc.weekly.service;

import com.zypc.weekly.pojo.Article;
import com.zypc.weekly.result.WeeklyResult;

/**
 * 文章接口规范
 * @author Administrator
 *
 */
public interface ArticleService {

	WeeklyResult addArticle(Article article);

	WeeklyResult getNoActicleList(String uId);

	WeeklyResult getActicleById(Integer id);

	WeeklyResult updateArticle(Article article);

	WeeklyResult deleteActicleById(Integer id);

	WeeklyResult getActicleListByUserId(String id);

	WeeklyResult getArticleById(Integer id);

	WeeklyResult getArticleList(String uId);

	WeeklyResult deleteArticle(Integer id);
	
	
}
