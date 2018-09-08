package com.zypc.weekly.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zypc.weekly.mapper.ArticleMapper;
import com.zypc.weekly.mapper.ReplyActicleMapper;
import com.zypc.weekly.mapper.UserMapper;
import com.zypc.weekly.pojo.Article;
import com.zypc.weekly.pojo.ReplyActicle;
import com.zypc.weekly.pojo.User;
import com.zypc.weekly.result.WeeklyResult;
import com.zypc.weekly.service.ReplyActicleService;

@Service
public class ReplyActicleServiceImpl implements ReplyActicleService {
	@Autowired
	private ReplyActicleMapper replyActiclemapper;
	@Autowired
	private UserMapper UserMapper;
	@Autowired
	private ArticleMapper articleMapper;

	/**
	 * 周报回复
	 */
	@Override
	public WeeklyResult addReplyArticle(ReplyActicle replyActicle) {
		try {
			replyActicle.setCreateTime(new Date());
			replyActiclemapper.addReplyArticle(replyActicle);
			return WeeklyResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return WeeklyResult.build(0, "添加失败，请重试！");
		}
	}

	/**
	 * 消息列表展示
	 */
	@Override
	public WeeklyResult getreplyArticleList(String uId) {
		try {
			List<Article> articleList = articleMapper.getArticleList(uId);
			List<ReplyActicle> list = new ArrayList<>();
			List<ReplyActicle> list2 = replyActiclemapper.getreplyArticleList(uId);
			
				
				
				for (ReplyActicle replyActicle : list2) {
					User user = UserMapper.selectUserById(replyActicle.getuId());
					Article article = articleMapper.getActicle(replyActicle.getaId());
					replyActicle.setArticle(article);
					replyActicle.setName(user.getName());
					replyActicle.setPower(user.getPower());
					
				}
			
			// 修改当前所有消息为已读状态
			for (ReplyActicle replyActicle : list2) {
				replyActiclemapper.updateReplyIsRead(replyActicle.getId());
			}
			return WeeklyResult.ok(list2);
		} catch (Exception e) {
			e.printStackTrace();
			return WeeklyResult.build(0, "查询出错，请稍后重试！");
		}
	}

	/**
	 * 查看回复信息
	 */
	@Override
	public WeeklyResult getreplyArticleListByaId(Integer aId) {
		try {
			List<ReplyActicle> list = replyActiclemapper.getreplyArticleListByaId(aId);
			if (list != null) {
				for (ReplyActicle replyActicle : list) {
					User user = UserMapper.selectById(replyActicle.getuId());
					replyActicle.setName(user.getName());
				}
				return WeeklyResult.ok(list);
			} else {
				return WeeklyResult.build(0, "当前还没有回复");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return WeeklyResult.build(0, "查看失败");
		}
	}

	
	/**
	 * 查看是否有消息
	 */

	@Override
	public WeeklyResult gethasReply(String uId) {
		try {
			List<Article> list = articleMapper.getArticleList(uId);
			if (list != null) {
				int sum = 0;
				for (Article article : list) {
					int count = replyActiclemapper.selectCount(article.getId());
					sum+=count;
				}
				if(sum>0) {
					return WeeklyResult.build(200, "有消息");
				}else {
					return WeeklyResult.build(0, "没有消息");
				}
			}else {
				return WeeklyResult.build(0, "没有消息");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return WeeklyResult.build(0, "查看失败！");
		}
	}

}
