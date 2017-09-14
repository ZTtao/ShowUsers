package com.amazonaws.lambda.demo.util;

public class Constant {
	public static final String JWT_ID = "jwt";
	public static final String JWT_SECRET = "myjwtsecret1122334455abcdefghijklmn";
	public static final int JWT_TTL = 60*60*1000;
	public static final int JWT_REFRESH_INTERVAL = 55*60*1000;
	public static final int JWT_REFRESH_TTL = 12*60*60*1000;
	
	public static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String MYSQL_URL = "jdbc:mysql://13.124.193.151:3306/lambdatest";
	public static final String MYSQL_USER = "root";
	public static final String MYSQL_PASSWORD = "root123";
}
