package cn.itcast.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.Category;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.serviceImp.CategoryServiceImp;
import cn.itcast.store.service.serviceImp.ProductServiceImp;
import cn.itcast.store.web.base.BaseServlet;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/IndexServlet")
public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
//	当你打的debugger的断点过多时，启动服务器debugger的时候，可能会报超时，导致debugger启不动，而java正常服务模式没有问题，可以正常启动
    @Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//分类。。。
		//    	//问题，只有当前页面会用，调用的就用不到，其实可以写成java的方式
		//		//调用业务功能：获取全部分类信息，返回集合
		//		 CategoryService CategoryService = new CategoryServiceImp();
		//		 List<Category> list = null;
		//		try {
		//			list = CategoryService.getAllCats();
		//		} catch (Exception e) {
		//			
		//			e.printStackTrace();
		//		}
		//		//将返回的集合放入request
		//		 request.setAttribute("allCats", list);
		    	
		//列表
    	ProductService productService = new ProductServiceImp();
    	//调用业务层查询最新商品，查询最热商品，返回2个集合
    	List<Product> list01 = null;
    	List<Product> list02 = null;
		try {
			list01 = productService.findHots();
			list02 = productService.findNews();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    	
    	//将两个集合放入到request
    	request.setAttribute("hots", list01);
    	request.setAttribute("news", list02);
    	
    	
    	
    	
    	
		//转发到真实的首页
		return "/jsp/index.jsp";
	}



}
