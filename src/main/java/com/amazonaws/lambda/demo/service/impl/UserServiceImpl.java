package com.amazonaws.lambda.demo.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.amazonaws.lambda.demo.dto.User;
import com.amazonaws.lambda.demo.service.IUserService;
import com.amazonaws.lambda.demo.util.Constant;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class UserServiceImpl implements IUserService {

	@Override
	public List<User> getUserByOrderbyLimit(Context context, String orderByClause, int offset, int limit, String search)
			throws Exception {
		LambdaLogger logger = context.getLogger();
		Class.forName(Constant.MYSQL_DRIVER);
		Connection conn = DriverManager.getConnection(Constant.MYSQL_URL, Constant.MYSQL_USER, Constant.MYSQL_PASSWORD);
		if (!conn.isClosed()) {
			logger.log("[" + new Date() + "]connect to database success.");
		}
		Statement statement = conn.createStatement();
		String sql = "SELECT * FROM user ";
		if (search != null) {
			sql += " where INSTR(user_name,'" + search + "')>0 or INSTR(user_info,'" + search + "')>0";
		}
		if (orderByClause != null) {
			sql += " order by " + orderByClause;
		}
		if (limit != 0) {
			sql += " limit " + offset + "," + limit;
		}
		logger.log("[" + new Date() + "]sql:" + sql);
		ResultSet rs = statement.executeQuery(sql);
		List<User> list = new ArrayList<>();
		while (rs.next()) {
			Integer userId = rs.getInt("user_id");
			String userName1 = rs.getString("user_name");
			String userInfo = rs.getString("user_info");
			String password = rs.getString("password");
			Date createTime = rs.getDate("create_time");
			User user = new User();
			user.setUserId(userId);
			user.setUserName(userName1);
			user.setUserInfo(userInfo);
			user.setPassword(password);
			user.setCreateTime(createTime);
			list.add(user);
			logger.log("[" + new Date() + "]user:" + JSONObject.toJSONString(user));
		}
		rs.close();
		conn.close();
		return list;
	}

	@Override
	public int getUserCount(Context context, String search) throws Exception {
		return this.getUserByOrderbyLimit(context, null, 0, 0, search).size();
	}

}
