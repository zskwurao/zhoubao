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
import com.zypc.weekly.pojo.User;
import com.zypc.weekly.result.WeeklyResult;

@Service
public class ArticleServiceImpl implements com.zypc.weekly.service.ArticleService {
	@Autowired
	private ArticleMapper articleMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ReplyActicleMapper replyActicleMapper;

	/**
	 * 周报上传或保存操作
	 */
	@Override
	public WeeklyResult addArticle(Article article) {
		// 补全对象的信息
		article.setId(null);
		article.setCreateDate(new Date());
		long total = articleMapper.addArticle(article);
		try {
			if (total > 0) {
				return WeeklyResult.ok(article.getId());
			} else {
				return WeeklyResult.build(0, "上传错误");
			}
		} catch (Exception e) {
			return WeeklyResult.build(0, "上传错误");
		}

	}

	/**
	 * 草稿箱列表
	 */
	@Override
	public WeeklyResult getNoActicleList(String uId) {
		try {
			List<Article> list = articleMapper.getNoActicleList(uId);
			if (list != null) {
				return WeeklyResult.ok(list);
			} else {
				return WeeklyResult.build(0, "当前还没有草稿");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return WeeklyResult.build(0, "列表展示失败");
		}
	}

	/**
	 * 草稿信息回显
	 */
	@Override
	public WeeklyResult getActicleById(Integer id) {
		try {
			Article article = articleMapper.getActicleById(id);
			return WeeklyResult.ok(article);

		} catch (Exception e) {
			e.printStackTrace();
			return WeeklyResult.build(0, "信息查询失败！");
		}
	}

	/**
	 * 更新草稿
	 */
	@Override
	public WeeklyResult updateArticle(Article article) {
		try {
			article.setCreateDate(new Date());
			articleMapper.updateArticle(article);
			return WeeklyResult.ok(article.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return WeeklyResult.build(0, "信息更新失败！");
		}
	}

	/**
	 * 删除草稿
	 */
	@Override
	public WeeklyResult deleteActicleById(Integer id) {
		try {
			articleMapper.deleteActicle(id);
			return WeeklyResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return WeeklyResult.build(0, "删除失败！");
		}
	}

	/**
	 * 收藏列表展示
	 */
	@Override
	public WeeklyResult getActicleListByUserId(String id) {
		try {
			User user = userMapper.selectById(id);
			String collection = user.getCollection();
			if (collection != null) {

				String[] split = collection.split(",");
				List<Article> list = new ArrayList<>();
				for (String string : split) {
					System.out.println(Integer.parseInt(string));
					Article article = articleMapper.getActicle(Integer.parseInt(string));
					if (article != null) {
						list.add(article);
					}

				}

				return WeeklyResult.ok(list);
			} else {
				return WeeklyResult.build(0, "你暂时还没有收藏");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return WeeklyResult.build(0, "查询出错，请稍后再试");
		}
	}

	/**
	 * 查看收藏的全文或以已上传的全文
	 */
	@Override
	public WeeklyResult getArticleById(Integer id) {
		try {
			Article acticle = articleMapper.getActicle(id);
			return WeeklyResult.ok(acticle);
		} catch (Exception e) {
			e.printStackTrace();
			return WeeklyResult.build(0, "查询失败，请稍后重试！");
		}
	}

	/**
	 * 查看已上传列表
	 */
	@Override
	public WeeklyResult getArticleList(String uId) {
		try {
			List<Article> list = articleMapper.getArticleList(uId);
			if (list != null) {
				for (Article article : list) {
					Short power = userMapper.selectPowerById(article.getuId());
					article.setPower(power);
				}
				return WeeklyResult.ok(list);
			}else {
				return WeeklyResult.build(0, "还未上传周报");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return WeeklyResult.build(0, "查询失败，请稍后重试！");
		}
	}

	/**
	 * 删除周报
	 */
	@Override
	public WeeklyResult deleteArticle(Integer id) {
		try {
			replyActicleMapper.deleteReply(id);
			articleMapper.deleteActicle(id);
			return WeeklyResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return WeeklyResult.build(0, "删除出错！");
		}
	}

}
