package cn.itcast.store.utils;

import java.util.UUID;
//GUID是一个128位长的数字，一般用16进制表示。算法的核心思想是结合机器的网卡、当地时间、一个随即数来生成GUID。从理论上讲，如果一台机器每秒产生10000000个GUID，则可以保证（概率意义上）3240年不重复。
//
//UUID是1.5中新增的一个类，在java.util下，用它可以产生一个号称全球唯一的ID
//UUID含义是通用唯一识别码 (Universally Unique Identifier)，这 是一个软件建构的标准，也是被开源软件基金会 (Open Software Foundation, OSF) 的组织在分布式计算环境 (Distributed Computing Environment, DCE) 领域的一部份。
public class UUIDUtils {
	/**
	 * 随机生成id
	 * @return
	 */
	public static String getId(){
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	
	
	public static String getUUID64(){
		return getId()+getId();
	}
	
	/**
	 * 生成随机码
	 * @return
	 */
	public static String getCode(){
		return getId();
	}
	
	public static void main(String[] args) {
		System.out.println(getId());
	}
}
