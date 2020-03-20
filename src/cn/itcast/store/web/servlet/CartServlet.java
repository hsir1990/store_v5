package cn.itcast.store.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.Cart;
import cn.itcast.store.domain.CartItem;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.serviceImp.ProductServiceImp;
import cn.itcast.store.web.base.BaseServlet;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/CartServlet")
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	public String addCartItemToCart(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("添加购物项到购物车=================================-----");
		//从session中获取购物车
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		//如果获取不到，创建购物车对象，放到session中
		if(null == cart) {
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		//如果获取到，使用即可
		//获取到商品id，shuliang
		String pid = request.getParameter("pid");
		int num = Integer.parseInt(request.getParameter("quantity"));
		//通过商品id查询到商品对象
		ProductService ProductService = new ProductServiceImp();
		Product product = ProductService.findProductByPid(pid);
		//获取到待购买的购物项		
		CartItem cartItem = new CartItem();
		cartItem.setNum(num);
		cartItem.setProduct(product);
		
		//调用购物车上的方法
		cart.addCartItemToCar(cartItem);
		//重定向到/jsp/cart.jsp
		response.sendRedirect("/store_v5/jsp/cart.jsp");
		return null;
	}
	
	//清空购物车
	public String clearCart(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("清空购物车----------");
		//获取购物车
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		//调用购物车的清除方法
		cart.clearCart();
		//重新定向到/jsp/cart.jsp?为什么用重定向，为啥不直接请求
		response.sendRedirect("/store_v5/jsp/cart.jsp");
		return null;
	}
	//清除购物车
	public String removeCartItem(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("清除购物车某件商品----------");
		//获取待删除的商品pid
		String pid = request.getParameter("id");
		System.out.println("清除购物车某件商品----------"+pid);
		//获取到购物车
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		//调用购物车的删除方法
		cart.removeCartItem(pid);
		//重定向到jsp/cart.jsp
		response.sendRedirect("/store_v5/jsp/cart.jsp");
		return null;
	}
	
}
