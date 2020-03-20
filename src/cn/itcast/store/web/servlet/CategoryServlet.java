package cn.itcast.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.Category;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.service.serviceImp.CategoryServiceImp;
import cn.itcast.store.utils.JedisUtils;
import cn.itcast.store.web.base.BaseServlet;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/CategoryServlet")
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	public String findAllCats(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("发送ajax，查询分类");
		//在redis中获取全部分类信息,存在redis中，就可以不用每次都调用数据库了，直接从内存中取
		//redis-server 直接启动  暂时未配置开机启动，所以有时未启动报错误        InvocationTargetException   调用目标异常
	 	Jedis jedis=JedisUtils.getJedis();
		String jsonStr = jedis.get("allCats");
		if(null==jsonStr || "".equals(jsonStr)) {
			System.out.println("redis缓存中没有数据");
			//调用业务层获取全部分类
			CategoryService CategoryService = new CategoryServiceImp();
			List<Category> list = CategoryService.getAllCats();
			//将全部分类转换成JSON格式的数据
			jsonStr = JSONArray.fromObject(list).toString(); 
			//将获取到的JSON格式的数据存入redis
			jedis.set("allCats", jsonStr);
		}else {
			System.out.println("redis缓存中有数据");
		}
		
		
		//将全部分类信心响应到客户端
		//告诉浏览器本次相应的数据是JSON格式的字符串
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().print(jsonStr);
		return null;
	}
}
