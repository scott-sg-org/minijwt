package com.example.minitest;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.dbcp2.BasicDataSource;

public class LoginServlet extends GenericServlet {
	private static final long serialVersionUID = 1L;

	private String jwtexample = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		String userId = Utility.cleanInputAsString(req.getParameter("userId"));
		String password = Utility.cleanInputAsString(req.getParameter("password"));
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		out.println("<html><head><title>Hello World!</title></head>");
		if (userId != null && !"".equals(userId)) {
			out.println("<body><h1>Hello User" + userId + "</h1>" + getUser(userId) + "</body></html>");
		} else {
			out.println("<body><h1>Hello World!</h1></body></html>");
		}
	}
	
	private static String getUser(String userId) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer result = new StringBuffer();
		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
			conn = bds.getConnection();
			stmt = conn.createStatement();
			String query = " select * from users where id = " + userId;
	        rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	result.append("<li>" + rs.getInt(1)+ " " + rs.getString(2) + " " + rs.getString(3)+ " " + rs.getString(4)+ " " + rs.getString(5) + "</li>");
	        }
	        	result.append("<li>" + jwtexample + "</li>");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (rs != null) 
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null) 
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result.toString();
	}

}
