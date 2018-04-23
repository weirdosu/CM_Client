package com.cmclient.output;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.Statement;

import com.cmclient.sql.connection2SQL;

public class SubmitObj implements Submit{

	public void submit2SQL(File file) throws Exception {
		// TODO Auto-generated method stub
		FileInputStream fin = new FileInputStream("consumption_object.txt");
		ObjectInputStream ois = new ObjectInputStream(fin);
		String line;
		Connection conn = new connection2SQL().getStatement();
		Statement sm = conn.createStatement();
		try {
			while((line = (String)ois.readObject())!=null)
			{
				System.out.println(line+"");
				String temp[] = line.split(",");
//				example:2018032300004,201500800537,66.5,11,11,2018-03-23 20:01:52
//				String sql_change = "select CONVERT(datetime,'"+temp[5]+"');";
//				ResultSet rs = conn.stmt.executeQuery(sql_change);
//				rs.next();
//				String timeString = rs.getString(1);
				String sql_insert = "insert into consumption values('"+temp[0]+"','"+temp[1]+"',"+temp[2]+",'"+temp[3]+"','"+temp[4]+"','"+temp[5]+"');";
				sm.execute(sql_insert);
			}
		} 
		catch (EOFException ex) 
		{
			System.out.println("End of file reached.");
			
		}
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
		
		ois.close();
		fin.close();
		file.delete();
	}
}
