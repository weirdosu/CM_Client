package com.cmclient.output;

import java.io.File;
import java.sql.Connection;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.cmclient.sql.connection2SQL;

public class SubmitXml implements Submit{

	public void submit2SQL(File file) throws Exception {
		// TODO Auto-generated method stub
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(file);
		NodeList nl = doc.getElementsByTagName("record");
		Connection conn = new connection2SQL().getStatement();
		Statement sm = conn.createStatement();
		for (int i = 0; i < nl.getLength(); i++)
		{
			//流水号
			String runningNumString = doc.getElementsByTagName("runningNumber").item(i).getFirstChild().getNodeValue();
			//学号
			String student_ID = doc.getElementsByTagName("student_ID").item(i).getFirstChild().getNodeValue();
			//食堂ID
			String canteenID = doc.getElementsByTagName("canteenID").item(i).getFirstChild().getNodeValue();
			//机器ID
			String machineID = doc.getElementsByTagName("machineID").item(i).getFirstChild().getNodeValue();
			//金额
			float money = Float.parseFloat(doc.getElementsByTagName("money").item(i).getFirstChild().getNodeValue());
			//时间
			String timepart =	doc.getElementsByTagName("timepart").item(i).getFirstChild().getNodeValue();
			String sql_insert = "insert into consumption values('"+runningNumString+"','"+student_ID+"',"+money+",'"+canteenID+"','"+machineID+"','"+timepart+"');";
			sm.execute(sql_insert);
		}
//		 conn.closeAll();

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

		//上传完数据之后立即删除文件
		file.delete();
	}

}
