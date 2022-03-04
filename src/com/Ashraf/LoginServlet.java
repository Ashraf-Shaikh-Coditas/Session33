package com.Ashraf;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
//@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("pwd");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		Cookie ck = new Cookie("unameck",username);
		response.addCookie(ck);
		
		ServletConfig config = getServletConfig();
		String s =config.getInitParameter("loginconfig");
		out.println("<h1><b>"+s+"</h1></b><hr>");
		
		ServletContext context = getServletContext();
		String driver = context.getInitParameter("driver");
		String url = context.getInitParameter("url");
		String usernamecon = context.getInitParameter("username");
		
		String passwordcon = context.getInitParameter("password");
		
		if(username.equals(password)) {
			out.println("Welcome !!! Login Successfull");
			
			Connection con = null;
			PreparedStatement pst;
			Statement st ;
			ResultSet rs ;
			
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url,usernamecon,passwordcon);
				pst = con.prepareStatement("INSERT INTO LoginDetails VALUES(?,?)");
				
				pst.setString(1, username);
				pst.setString(2, password);
				pst.execute();
				
				st =con.createStatement();
				
				rs = st.executeQuery("SELECT * FROM LoginDetails");
				
				while(rs.next()) {
					out.println("<table style='border: 1px solid black' ><tr><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td></tr></table>");
				}
				
				
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			out.println("<br><a href='welcome'>Welcome "+username);
		} else {
			out.println("Invalid Login Credentials");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
