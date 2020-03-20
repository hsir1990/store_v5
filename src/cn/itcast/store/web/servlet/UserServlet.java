package cn.itcast.store.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.commons.beanutils.BeanUtils;
//import org.apache.commons.beanutils.ConvertUtils;
//import org.apache.commons.beanutils.converters.DateConverter;

import cn.itcast.store.domain.User;
import cn.itcast.store.service.UserService;
import cn.itcast.store.service.serviceImp.UserServiceImp;
import cn.itcast.store.utils.MailUtils;
import cn.itcast.store.utils.MyBeanUtils;
import cn.itcast.store.utils.UUIDUtils;
import cn.itcast.store.web.base.BaseServlet;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public UserServlet() {
//        super();
//        // TODO Auto-generated constructor stub
//    }
//
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//	}
	// 跳转注册页
	public String registUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("跳转注册页面成功");
		return "/jsp/register.jsp";
	}
	// 跳转登录页
	public String loginUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("跳转登录页面成功");
		return "/jsp/login.jsp";
	}

	// 注册页注册并跳转
	public String userRegister(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 接收表单参数//post 请求一直有中文乱码，在web.xml增加了配置就好了
		Map<String, String[]> map = request.getParameterMap();

		User user = new User();
//		Class clazz = user.getClass();
//		//需要创建时间转换器来实现1_创建时间转换器
//		DateConverter dt = new DateConverter();
//		//2_设置转换的格式
//		dt.setPattern("yyyy-MM-dd");
//		//3_注册转换器
//		ConvertUtils.register(dt, java.util.Date.class);
//		

//		BeanUtils.populate(user, map);
		MyBeanUtils.populate(user, map);
		System.out.println(user);

//		//遍历map中的数据
//		Set<String> keySet = map.keySet();
//		Iterator<String> iterator = keySet.iterator();
//		while(iterator.hasNext()) {
//			String str = iterator.next();
//			System.out.println(str);
//			String[] strs = map.get(str);
//			for(String string : strs) {
//				System.out.println(string);
//			}
//		}
		// 为用户的其他属性赋值
		user.setUid(UUIDUtils.getId());
		user.setState(0);
		user.setCode(UUIDUtils.getCode());

//		调用业务层注册功能
		UserService UserService = new UserServiceImp();
		try {

			UserService.userRegist(user);
//			注册成功,向用户邮箱发送信息，跳转到提示页面
//			发送邮件
			System.out.println(user.getEmail()+ user.getCode());
			
			
			MailUtils.sendMail(user.getEmail(), user.getCode());
			System.out.println("用户注册成功，清激活！");
			request.setAttribute("msg", "用户注册成功，清激活！");
		} catch (Exception e) {
			System.out.println("用户注册成功，清激活！");
			request.setAttribute("msg", "用户注册失败，请重新注册！");
		}
		return "/jsp/info.jsp";
	}
	
	//激活
	public String active(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("激活");
		//获取激活码
		String code = request.getParameter("code");
		//调用业务激活功能
		UserService UserService = new UserServiceImp();
		boolean flag = UserService.userActive(code);
		System.out.println("flag-------------"+flag);
		//进行激活信息提示
		if(flag == true) {
			request.setAttribute("msg", "用户激活成功请登录！");
			return "/jsp/login.jsp";
		}else {
			request.setAttribute("msg", "用户激活失败请重新激活！");
			return "/jsp/info.jsp";
		}
	}
	
	// 登录
	public String UserLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("登录");
		//获取用户数据(账号,密码)
		User user = new User();
		MyBeanUtils.populate(user, request.getParameterMap());
		//调用业务层登录功能
		UserService userService =new  UserServiceImp();
		User user02 = null;
		try {
			//select * from user where username=? and password=?
			user02 = userService.userLogin(user);
			//用户登录成功,将用户信息放入session中
			request.getSession().setAttribute("loginUser", user02);
			response.sendRedirect("/store_v5/index.jsp");
			return null;
		} catch (Exception e) {
			//用户登录失败
			String msg = e.getMessage();
			System.out.println(msg);
			//向request放入失败的信息
			request.setAttribute("msg", msg);
			return "/jsp/login.jsp";
		}
		
	}
	//	退出
	public String logOut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//清楚session
		request.getSession().invalidate();
//		重新定向到首页
		response.sendRedirect("/store_v5/index.jsp");
		return null;
	}

}
