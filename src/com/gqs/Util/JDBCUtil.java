package com.gqs.Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JDBCUtil {

	private static Properties prop;
	static {
		prop = new Properties();
		try {
			prop.load(JDBCUtil.class.getClassLoader().getResourceAsStream("db.properties"));
			Class.forName(prop.getProperty("driver"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"),
				prop.getProperty("password"));
	}

	public static void free(ResultSet rs, PreparedStatement ps, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	Connection conn = null;
	PreparedStatement ps =null;
	ResultSet rs =null;
	
	public List<String> sql(String num1){
		List<String> list = new ArrayList<String>();
		try {
			conn = JDBCUtil.getConnection();
			//String sql = "SELECT CODE FROM ORG_MEMBER WHERE  EXT_ATTR_22 = TO_Date(\'" + num1  + "\',\'YYYY-MM\')";

			ps = conn.prepareStatement(prop.getProperty("sql"));
			//System.out.println(prop.getProperty("sql"));
			
			//SimpleDateFormat format = new SimpleDateFormat("YYYY-MM");  
	        //java.util.Date d = null;  
	        try {  
	        	//d = format.parse(num1);  
	        	//System.out.println(d);
	        	//java.sql.Date sd = new java.sql.Date(d.getTime()); 
	        	//System.out.println(sd);
	        	//ps.setDate(1, sd);
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
			//System.out.println(ps.toString());
	        ps.setString(1, num1);
			rs = ps.executeQuery();
			while(rs.next()){
				list.add(rs.getString(prop.getProperty("ID")));
			}
			JDBCUtil.free(rs, ps, conn);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		return list;
	}
	
}
