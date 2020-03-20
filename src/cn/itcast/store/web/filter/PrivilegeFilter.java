package cn.itcast.store.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import cn.itcast.store.domain.User;

/**
 * Servlet Filter implementation class PrivilegeFilter
 */
@WebFilter(
		asyncSupported = true, 
		urlPatterns = { 
				"/jsp/cart.jsp", 
				"/jsp/order_info.jsp", 
				"/jsp/order_list.jsp"
		})
public class PrivilegeFilter implements Filter {

    /**
     * Default constructor. 
     */
    public PrivilegeFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		//判断当前的session中是否存在已经登录的成功的用户
		HttpServletRequest myReq = (HttpServletRequest)request;
		User user = (User)myReq.getSession().getAttribute("loginUser");
		if(null != user) {
			System.out.println("放行");
			//如果存在放行
			chain.doFilter(request, response);
		}else {
			System.out.println("不放行");
			//如果不存在，转入其他的页面
			myReq.setAttribute("msg", "请用户登录之后在去访问");
			//转入到提示页面
			myReq.getRequestDispatcher("/jsp/info.jsp").forward(request, response);
		}
		
		
		
//		// pass the request along the filter chain
//		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
