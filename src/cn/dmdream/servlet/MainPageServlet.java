package cn.dmdream.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dmdream.controller.base.BaseServlet;
import cn.dmdream.entity.SnChapter;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.service.SnChapterService;
import cn.dmdream.service.SnNovelService;
import cn.dmdream.service.impl.SnChapterServiceImpl;
import cn.dmdream.service.impl.SnNovelServiceImpl;
import cn.dmdream.vo.JsonMsg;

public class MainPageServlet extends BaseServlet{
	//获取周排行
	public String getWeekRank(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return null;
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
	
}
