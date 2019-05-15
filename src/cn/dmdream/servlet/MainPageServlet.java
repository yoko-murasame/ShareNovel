package cn.dmdream.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dmdream.controller.base.BaseServlet;
import cn.dmdream.entity.SnChapter;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.service.SnChapterService;
import cn.dmdream.service.SnNovelService;
import cn.dmdream.service.impl.SnChapterServiceImpl;
import cn.dmdream.service.impl.SnNovelServiceImpl;
import cn.dmdream.utils.JedisUtils;
import cn.dmdream.vo.JsonMsg;
import redis.clients.jedis.Jedis;

public class MainPageServlet extends BaseServlet{
	
	private SnNovelService novelService = new SnNovelServiceImpl();
	private Jedis jedis = JedisUtils.getJedis();
	private ObjectMapper objectMapper = new ObjectMapper();
	
	//获取周排行
	public String getWeekRank(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return null;
	}
	
	
	//跳转主页
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			//尝试从Redis获取最强推荐
			String highlyRecomm = jedis.hget("indexNovelCache", "highlyRecomm");
			if (highlyRecomm != null) {
				System.out.println("数据-最强推荐-是从Redis中查询的");
				List<SnNovel> list = objectMapper.readValue(highlyRecomm, new TypeReference<List<SnNovel>>() {
				});
				System.out.println(list);
				//放入req域
				req.setAttribute("highlyRecomm", list);
			} else {
				// 若没有,从数据库中查询并赋值Redis再返回
				System.out.println("数据-最强推荐-是从数据库查询的");
				//获取最新4条
				List<SnNovel> novelist = novelService.findByCheckByPage(1, 4, 1);
				String jsonStr = objectMapper.writeValueAsString(novelist);
				// 存入redis
				jedis.hset("indexNovelCache", "highlyRecomm",jsonStr);
				System.out.println(novelist);
				//放入req域
				req.setAttribute("highlyRecomm", novelist);
			}
			//尝试从redis获取本周热榜
			String weeklyHot = jedis.hget("indexNovelCache", "weeklyHot");
			if (weeklyHot != null) {
				System.out.println("数据-本周热榜-是从Redis中查询的");
				List<SnNovel> list = objectMapper.readValue(weeklyHot, new TypeReference<List<SnNovel>>() {
				});
				System.out.println(list);
				//放入req域
				req.setAttribute("weeklyHot", list);
			} else {
				// 若没有,从数据库中查询并赋值Redis再返回
				System.out.println("数据-本周热榜-是从数据库查询的");
				//获取最新4条
				List<SnNovel> novelist = novelService.findByCheckByPage(1, 9, 1);
				String jsonStr = objectMapper.writeValueAsString(novelist);
				// 存入redis
				jedis.hset("indexNovelCache", "weeklyHot",jsonStr);
				System.out.println(novelist);
				//放入req域
				req.setAttribute("weeklyHot", novelist);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		
		}
		//转发到主页
		return "/mainpage.jsp";
	}
	//获取热门分类
	public String getHotCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return null;
	}	
	//获取新章节
	public String getNewChapter(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SnChapterService chapterService=new SnChapterServiceImpl();
		List<SnChapter> chapters = chapterService.findRecentUpdate(7, 10);
		System.out.println(chapters);
		JsonMsg msg=JsonMsg.makeSuccess("1",chapters);
		ObjectMapper mapper=new ObjectMapper();
		String jsonstr=mapper.writeValueAsString(msg);
		resp.getWriter().print(jsonstr);
		return null;
	}	
	//获取新小说
	public String getNewNovel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SnNovelService novelsvc=new SnNovelServiceImpl();
		List<SnNovel> novels = novelsvc.findNewestNovel(10);
		JsonMsg msg=JsonMsg.makeSuccess("1", novels);
		ObjectMapper mapper=new ObjectMapper();
		String jsonstr=mapper.writeValueAsString(msg);
		resp.getWriter().print(jsonstr);
		return null;
	}	
	//获取推荐
	public String getRecommendNoevl(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return null;
	}
	//获取公告
	public String getNotice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return null;
	}
	public String getWheelInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return null;
	}
	public String search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return null;
	}
	
}
