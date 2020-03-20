package cn.itcast.store.test;

import java.util.HashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.junit.Test;

import cn.itcast.store.domain.User;

public class TestBeanUtils {
	@Test
	public void test01() throws Exception{
		//模拟request.getParameterMap()
		HashMap<String, String[]> map = new HashMap<String, String[]>();
		//new String[] {"tom"}----->简写 {"tom"}
		map.put("username", new String[] {"tom"});
		map.put("password", new String[] {"123456"});
		
		User user = new User();
		BeanUtils.populate(user, map);
		System.out.println(user);
		
	
	}
	//加上时间以后
	@Test
	public void test02() throws Exception{
		//模拟request.getParameterMap()
		HashMap<String, String[]> map = new HashMap<String, String[]>();
		//new String[] {"tom"}----->简写 {"tom"}
		map.put("username", new String[] {"tom"});
		map.put("password", new String[] {"123456"});
		map.put("birthday", new String[] {"1990-01-12"});
		
		User user = new User();
		//需要创建时间转换器来实现1_创建时间转换器
		DateConverter dt = new DateConverter();
		//2_设置转换的格式
		dt.setPattern("yyyy-MM-dd");
		//3_注册转换器
		ConvertUtils.register(dt, java.util.Date.class);
		BeanUtils.populate(user, map);
		System.out.println(user);
		
	
	}
}
