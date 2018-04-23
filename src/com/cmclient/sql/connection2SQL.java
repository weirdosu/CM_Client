package com.cmclient.sql;

import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.SQLException;

import java.sql.ResultSet;
import java.sql.Statement;

public class connection2SQL {
	//链接数据库
//	public Connection conn;  	//数据库连接对象
//	public java.sql.Statement stmt;   	//语句执行对象
	private Connection ct = null;
	private Statement sm = null;
	private ResultSet rs = null;

	public Connection getStatement(){
		try {
//			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//			//你需要根据自己实际数据库的情况，修改这里的连接字符串
//			conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=CM;user=sa;password=123;");//修改数据库名字
//			stmt = conn.createStatement();
			Class.forName("com.mysql.jdbc.Driver");
//			ct = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteen?user=root&password=&useUnicode=true&characterEncoding=utf-8");
			ct = DriverManager.getConnection("jdbc:mysql://101.132.186.165:3306/canteen?user=sdy&password=123&useUnicode=true&characterEncoding=utf-8");

			//必须在服务器的mysql上加上权限！！ grant all PRIVILEGES on canteen.* to 'sdy'@'112.6.227.9' identified by '123' WITH GRANT OPTION
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ct;
	}

	public void closeAll(){
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (sm != null) {
				sm.close();
				sm = null;
			}
			if (ct != null) {
				ct.close();
				ct = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
