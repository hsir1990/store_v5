package cn.itcast.store.dao.daoImp;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.store.dao.UserDao;
import cn.itcast.store.domain.User;
import cn.itcast.store.utils.JDBCUtils;

public class UserDaoImp implements UserDao {
	//注册
	@Override
	public void userRegist(User user) throws SQLException {
		// 注意表名要小写，同时要学会debugger//注意很重要
		String sql = "INSERT INTO user VALUES(?,?,?,?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = { user.getUid(), user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),
				user.getTelephone(), user.getBirthday(), user.getSex(), user.getState(), user.getCode() };
		System.out.println(params.toString());
		qr.update(sql, params);
		System.out.println("1111111111111111111111111");
	}
	//修改状态码
	@Override
	public void updateUser(User user) throws SQLException{
		String sql="update user set username=?, password=?, name=?,email=?, telephone=?, birthday=?, sex=?, state=?, code=? where uid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = {user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode(),user.getUid()};

		qr.update(sql, params);
	}
	//激活
	@Override
	public User userActive(String code) throws SQLException {
		String sql = "select * from user where code=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		User user = qr.query(sql, new BeanHandler<User>(User.class),code);
		return user;
	}
	//登录
	@Override
	public User userLogin(User user) throws SQLException {
		String sql = "select * from user where username=? and password=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		
		return qr.query(sql, new BeanHandler<User>(User.class), user.getUsername(), user.getPassword());
	}

}
