package cn.itcast.store.utils;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.itcast.store.dao.UserDao;
import cn.itcast.store.domain.User;

public class BeanFactory {
	//注意object  中  o要大写 O  ，同时直接调用的话要加上static
	
	//解析XML   dom4j用来解析xml
	public static Object createObject(String name) {
		//通过传递过来的name获取application.xml 中的name对应的class值
		
		
		try {
			//获取Document对象
			SAXReader reader = new SAXReader();
			//如何获取application.xml文件的输入流 (application.xml必须位于src下，其他的地方搞不定)
			InputStream is = BeanFactory.class.getClassLoader().getResourceAsStream("application.xml");
			Document doc = reader.read(is);
			
			//通过Document对象获取根节点  beans
			Element rootElement = doc.getRootElement();
			//通过根节点获取到根节点下所有子节点bean，返回集合
			List<Element> list = rootElement.elements();
			//如果一致，获取到当前元素上class属性值
			for (Element ele : list) {
				//ele相当于bean节点下的每个bean
				//获取到当前节点的id属性值
				String id = ele.attributeValue("id");
				//如果一致，获取到当前元素上class属性值
				if(id.equals(name)) {
					String str = ele.attributeValue("class");
					//通过反射创建对象并且返回
					Class clazz = Class.forName(str);
					//利用class值通过反射创建对象返回
					return clazz.newInstance();
				}
					
			}
		} catch (DocumentException e) {
					
			e.printStackTrace();
		}
		
		return null;
	}
	public static void main(String[] args) throws SQLException {
		UserDao ud = (UserDao)BeanFactory.createObject("UserDao");
		User user = new User();
		user.setUsername("11");
		user.setPassword("11");
		User uu = ud.userLogin(user);
		System.out.println(uu);
	}
}
