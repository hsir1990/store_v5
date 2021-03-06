package cn.itcast.store.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.serviceImp.CategoryServiceImp;
import cn.itcast.store.service.serviceImp.ProductServiceImp;
import cn.itcast.store.web.base.BaseServlet;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/ProductServlet")
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String findProductByPid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取商品pid
		String pid = request.getParameter("pid");
		//根据商品id查询商品信息
		ProductService ProductService = new ProductServiceImp();
		Product Product =  ProductService.findProductByPid(pid);
		//将获取到的商品放入request
		request.setAttribute("product", Product);

		//转发/jsp/product_info.jsp
		return "/jsp/product_info.jsp";
	}
	public String findProductsByCidWithPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取cid,num
		String cid = request.getParameter("cid");
		int curNum = Integer.parseInt(request.getParameter("num"));
		//调用业务层功能：以分页形式查询当前类别下商品信息
		ProductService ProductService = new ProductServiceImp();
		//返回PageModel对象（1_当前页商品信息2_分页3_url）
		PageModel pm=ProductService.findProductsByCidWithPage(cid, curNum);
		
		//将PageModel对象放入request
		request.setAttribute("page", pm);
		//转发/jsp/product_list.jsp
		
		
		return "/jsp/product_list.jsp";
	}

}
