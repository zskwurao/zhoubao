package com.zypc.weekly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
/**
 * 周报回复 
 * @author ASUS
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zypc.weekly.pojo.ReplyActicle;
import com.zypc.weekly.result.WeeklyResult;
import com.zypc.weekly.service.ReplyActicleService;
@Controller
public class ReplyArticleController {
	@Autowired
	private ReplyActicleService replyActicleService;
	/**
	 * 周报回复
	 * @param replyActicle
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/replyArticle/addReplyArticle.action")
	@ResponseBody
	public WeeklyResult addReplyArticle(ReplyActicle replyActicle) {
		WeeklyResult result = replyActicleService.addReplyArticle(replyActicle);
		return result;
	}
	/**
	 * 消息列表查询
	 * @param replyActicle
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/reply/getreplyArticleList.action")
	@ResponseBody
	public WeeklyResult getreplyArticleList(String uId) {
		WeeklyResult result = replyActicleService.getreplyArticleList(uId);
		return result;
	}
	/**
	 * 查看回复信息
	 */
	@RequestMapping(method=RequestMethod.POST,value="/reply/getreplyArticleListByaId.action")
	@ResponseBody
	public WeeklyResult getreplyArticleListByaId(Integer aId) {
		WeeklyResult result = replyActicleService.getreplyArticleListByaId(aId);
		return result;
	}
	
	/**
	 * 查询是否有回复
	 */
	@RequestMapping(method=RequestMethod.POST,value="/reply/hasReply.action")
	@ResponseBody
	public WeeklyResult gethasReply(String uId) {
		WeeklyResult result = replyActicleService.gethasReply(uId);
		return result;
	}
	
}
