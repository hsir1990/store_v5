package cn.itcast.store.test;


import org.junit.Test;

import redis.clients.jedis.Jedis;

public class TestJedis {
//	public static void main(String[]  arg) {
//		Jedis jedis = new Jedis();
//		
//	}
	@Test
	//获取单一的数据
	public void test1() {
		//链接redis注意里面的配置，service redisd start  配置的实际上是6379.conf文件。是从reids.conf中复制过来的,同时注意要打开端口号在防火墙上
		Jedis  jedis = new Jedis("47.92.48.144",6379);
		jedis.auth("123456");
//		String ss = jedis.set("username","213");
		String ss = jedis.get("username");
		System.out.println(jedis.ping());
		System.out.println("ss================================="+ss);
	}
}
