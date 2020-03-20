package cn.itcast.store.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import cn.itcast.store.domain.Category;
import cn.itcast.store.domain.PageModel;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.serviceImp.CategoryServiceImp;
import cn.itcast.store.service.serviceImp.ProductServiceImp;
import cn.itcast.store.utils.UploadUtils;
import cn.itcast.store.web.base.BaseServlet;

/**
 * Servlet implementation class AdminProductServlet
 */
@WebServlet("/AdminProductServlet")
public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	//findAllProductsWithPage
	public String findAllProductsWithPage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("查看全部商品");
		//获取当前页
		int curNum = Integer.parseInt(request.getParameter("num")) ;
		//调用业务层查询全部商品信息返回PageModel
		ProductService productService = new ProductServiceImp();
		PageModel pm = productService.findAllProductsWithPage(curNum);
		//将PageModel放入request
		request.setAttribute("page", pm);
		//转发到 /admin/product/list.jsp
		return "/admin/product/list.jsp";
	}
	
	//addProductUI
	public String addProductUI(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		CategoryService categoryService = new CategoryServiceImp();
		//获取全部分类信息
		List<Category> list = categoryService.getAllCats();
		//将全部分类信息放入request
		request.setAttribute("allCats",list);
		//转发到/damin/product/add.jsp
		return "/admin/product/add.jsp";
	}
	
	
	//addProduct
		public String addProduct(HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			
			Map<String, String> map = new HashMap<String, String>();
			
			try {
				//利用req。getInputStream();获取到请求体全部数据，进行拆分和封装
				DiskFileItemFactory fac = new	DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(fac);
				List<FileItem> list = upload.parseRequest(request);
				
				//4遍历集合
				for(FileItem item : list) {
					if(item.isFormField()) {
						//5如果当前的FileItem对象是普通项
						//将普通项上name属性的值作为建，将获取到的内容作为值，放入MAP中
						//{username <===>tom, password<===>123456}
						map.put(item.getFieldName(), item.getString("utf-8"));
					}else {
						//6如果当前fileItem对象是上传项
						
						//获取到原始的文件名称
						String oldFileName = item.getName();
						//获取到要保存文件的名称1112.doc    1232121212.doc
						String newFileName = UploadUtils.getUUIDName(oldFileName);
						
						//通过FileItem获取到输入流对象，通过输入流可以获取到图片二进制数据
						InputStream is = item.getInputStream();
						//获取到当前项目下 products/3 下的真实路径
						//D:\tomcat\tomcat71__sz07\webapps\store_v5\products\3 

						String realPath = getServletContext().getRealPath("products/3/");
						String dir = UploadUtils.getDir(newFileName); //   /f/e/s/d/f/g/s/w
						String path = realPath+dir;
						//内存中声明一个目录
						java.io.File newDir = new java.io.File(path);
//						File newDir = new File(path);
						if(newDir.exists()) {
							newDir.mkdirs();
						}
						
						//在服务端创建一个空文件（后缀必须和上传服务端的文件后缀一致）
						File finalFile = new File(newDir , newFileName);
						if(finalFile.exists()) {
							finalFile.createNewFile();
						}
						//建立和空文件对应的输出流
						OutputStream os = new FileOutputStream(finalFile);
						//将输入流中的数据刷到输出流中
						IOUtils.copy(is, os);
						//释放资源
						IOUtils.closeQuietly(is);
						IOUtils.closeQuietly(os);
						//向map中存入一个键值对的数据，userhead 《===》/image/11.bmp
						//{username<===>tom, password<===>1234,userhead<====>image/11.bmp}
						map.put("pimage", "/products/3/"+dir+"/"+finalFile);
					}
				}
				
				
				//7利用BeanUtils将MAP中的数据填充到user对象上
				//8利用service_dao将user上携带的数据存入数据仓库，重定向到查询全部信息路径
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			return null;
		}
}















