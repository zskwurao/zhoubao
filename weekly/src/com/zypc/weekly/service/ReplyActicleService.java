package com.zypc.weekly.service;

import com.zypc.weekly.pojo.ReplyActicle;
import com.zypc.weekly.result.WeeklyResult;

public interface ReplyActicleService {

	WeeklyResult addReplyArticle(ReplyActicle replyActicle);

	WeeklyResult getreplyArticleList(String uId);

	WeeklyResult getreplyArticleListByaId(Integer aId);

	WeeklyResult gethasReply(String uId);

}
