package com.zypc.weekly.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zypc.weekly.pojo.Article;
import com.zypc.weekly.result.WeeklyResult;
import com.zypc.weekly.service.ArticleService;
/**
 * 周报接口类
 * @author ASUS
 *
 */
@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;
	/**
	 * 周报上传或保存
	 */
	@RequestMapping(value="/article/addArticle.action",method=RequestMethod.POST)
	@ResponseBody
	public WeeklyResult addArticle(Article article) {
		WeeklyResult result = new WeeklyResult();
		//第一次提交
		if(article.getId()==0) {
			result = articleService.addArticle(article);
			
		}
		//再次编辑草稿
		else {
			result = articleService.updateArticle(article);
		}
		return result;
	}
	/** 
	 * 草稿箱列表
	 * @param uId
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/article/getNoActicleList.action")
	@ResponseBody
	public WeeklyResult getNoActicleList(String uId) {
		WeeklyResult result = articleService.getNoActicleList(uId);
		return result;
	}
	/**
	 * 草稿编辑
	 */
	@RequestMapping(method=RequestMethod.POST,value="/article/getActicleById.action")
	@ResponseBody
	public WeeklyResult getActicleById(Integer id) {
		WeeklyResult result = articleService.getActicleById(id);
		return result;
	}
	/**
	 * 草稿删除
	 */
	@RequestMapping(method=RequestMethod.POST,value="/article/deleteActicle.action")
	@ResponseBody
	public WeeklyResult deleteActicle(Integer id) {
		WeeklyResult result = articleService.deleteActicleById(id);
		return result;
	}
	/**
	 * 收藏列表展示
	 */
	@RequestMapping(method=RequestMethod.POST,value="/article/getActicleListByUserId.action")
	@ResponseBody
	public WeeklyResult getActicleListByUserId(String uId) {
		WeeklyResult result = articleService.getActicleListByUserId(uId);
		return result;
	}
	/**
	 * 查看收藏全文或查看已上传的文章
	 */
	@RequestMapping(method=RequestMethod.POST,value="/article/getArticleById.action")
	@ResponseBody
	public WeeklyResult getArticleById(Integer id) {
		WeeklyResult result = articleService.getArticleById(id);
		return result;
	}
	/**
	 * 查看已上传列表
	 */
	@RequestMapping(method=RequestMethod.POST,value="/article/getArticleList.action")
	@ResponseBody
	public WeeklyResult getArticleList(String uId) {
		WeeklyResult result = articleService.getArticleList(uId);
		return result;
	}
	/**
	 * 删除周报
	 */
	@RequestMapping(method=RequestMethod.POST,value="/article/deleteArticle.action")
	@ResponseBody
	public WeeklyResult deleteArticle(Integer id) {
		WeeklyResult result = articleService.deleteArticle(id);
		return result;
	}
	
	
}
