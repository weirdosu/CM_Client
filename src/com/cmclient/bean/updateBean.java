package com.cmclient.bean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.cmclient.sql.connection2SQL;

public class updateBean {

	private String cardID;
	private float consume;
	public String getCardID(){
		return cardID;
	}
	public float getBalance(){
		return consume;
	}
	public void setCardID(String ID)
	{
		cardID =ID;
	}
	public void setBalance(float bal)
	{
		consume =bal;
	}

	//默认构造函数
	public updateBean(){}

	//带参数的构造函数
	public updateBean(String ID, float bal){
		this.cardID = ID;
		this.consume = bal;
	}

	//连接数据库更新余额
	public void operation_update() throws Exception{
		Connection conn = new connection2SQL().getStatement();
		Statement sm = conn.createStatement();
		String sql_qurry = "select balance from card where student_ID = '" + cardID + "';";
		ResultSet res = sm.executeQuery(sql_qurry);
		res.next();
		float money = Float.parseFloat(res.getString("balance"));
		String sql_update = "update card set balance ='" + (money-consume) + "' where student_ID = '" + cardID + "';";
		sm.execute(sql_update);
//		conn.closeAll();
		
		try {
			if (sm != null) {
				sm.close();
				sm = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
