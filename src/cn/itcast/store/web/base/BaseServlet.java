package cn.itcast.store.web.base;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// localhost:8080/store/productServlet?method=addProduct
		String method = req.getParameter("method");
		System.out.println("--------------------");
		//做出筛选，传进来的method字段不为null，不为空，并且去掉空格也不为空
		if (null == method || "".equals(method) || method.trim().equals("")) {
			method = "execute";
		}

		// 注意:此处的this代表的是子类的对象
		// System.out.println(this);
		// 子类对象字节码对象，通过反射获取
		Class clazz = this.getClass();

		try {
			// 查找子类对象对应的字节码中的名称为method的方法.这个方法的参数类型是:HttpServletRequest.class,HttpServletResponse.class
			Method md = clazz.getMethod(method, HttpServletRequest.class, HttpServletResponse.class);
			if(null!=md){
				//md.invoke直接返回objeact
				String jspPath = (String) md.invoke(this, req, resp);
				if (null != jspPath) {
					//getRequestDispatcher()包含两个方法，分别是请求转发和请求包含
//					请求转发： rd.forward( request , response );
//					请求包含： rd.include( request  , response);
//					请求转发：由下一个Servlet完成响应体！当前Servlet可以设置响应头！（留头不留体）
//					请求包含：由两个Servlet共同完成响应体！（留头又留体）
					req.getRequestDispatcher(jspPath).forward(req, resp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 默认方法
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return null;
	}

}