package com.cmclient.output;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

import com.cmclient.sql.connection2SQL;

public class SubmitTxt implements Submit {

	public void submit2SQL(File file) throws Exception {
		// TODO Auto-generated method stub
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;
		Connection conn = new connection2SQL().getStatement();
		Statement sm = conn.createStatement();
		while((line=br.readLine())!=null)
		{
			String temp[] = line.split(",");
			//example:2018032300004,201500800537,66.5,11,11,2018-03-23 20:01:52
			String sql_insert = "insert into consumption values('"+temp[0]+"','"+temp[1]+"',"+temp[2]+",'"+temp[3]+"','"+temp[4]+"','"+temp[5]+"');";
			sm.execute(sql_insert);
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
		
		br.close();
		fr.close();
		file.delete();
	}

}
