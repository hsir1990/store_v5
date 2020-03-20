package cn.itcast.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.Order;
import cn.itcast.store.service.OrderService;
import cn.itcast.store.service.serviceImp.OrderServiceImp;
import cn.itcast.store.web.base.BaseServlet;
import net.sf.json.JSONArray;

/**
 * Servlet implementation class AdminOrderServlet
 */
@WebServlet("/AdminOrderServlet")
public class AdminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	//findOrders
		public String findOrders(HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			//获取到全部订单
			OrderService orderService = new OrderServiceImp();
			String st = request.getParameter("state");
			List<Order> list = null;
			if(null == st || "".equals(st)) {
				 list = orderService.findAllOrders();
			}else {
				 list = orderService.findAllOrders(st);
			}
			
			
			//将全部订单放入requset
			request.setAttribute("allOrders", list);
			//转发  /admin/order/list.jsp
			return "/admin/order/list.jsp";
		}
	//findOrderByOidByOidWithAjax
		public String findOrderByOidByOidWithAjax(HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			//服务端获取到订单下的ID
			String id = request.getParameter("id");
			//查询这个订单下所有订单项以及订单项对应的商品信息，返回集合
			OrderService orderService = new OrderServiceImp();
			Order order = orderService.findMyOrderByOid(id);
			//将返回的集合转换为JSON格式字符串，响应到客户端
			String jsonStr = JSONArray.fromObject(order.getList()).toString();
			//设置响应到客户端的格式
			response.setContentType("application/json;charset=utf-8");
			//响应到客户端
			response.getWriter().println(jsonStr);
			return null;
		}
		
		//updateOrderByOid\
		public String updateOrderByOid(HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			//获取订单ID
			String oid = request.getParameter("oid");
			//根据订单ID查询订单
			OrderService orderService = new OrderServiceImp();
			Order order = orderService.findMyOrderByOid(oid);
			//设置订单的状态
			order.setState(3);
			//修改订单状态
			orderService.updateOrder(order);
			//重定向到查询已发货订单
			response.sendRedirect("/store_v5/AdminOrderServlet?method=findOrders&state=3");
			return null;
		}

}
