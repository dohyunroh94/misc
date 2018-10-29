/*
Servelet class used to view and control 
employee sql database. 
*/

import javax.servlet.*;
import java.sql.*;
import javax.sql.*;
import javax.servlet.http.*;
import java.io.*;


public class My extends HttpServlet {

 // Used for primarily login
 public void doGet(HttpServletRequest req, HttpServletResponse resp) 
	throws ServletException, IOException {
  
	String user = req.getParameter("username");
	String pass = req.getParameter("password");
	
	if ("user1".equals(user) && "pass1".equals(pass)){
		correctResponse(resp);
	} else {
		incorrectResponse(resp, "Invalid password/username");
	}
 }

 // Function for form actions that request deleting or adding employees 
 public void doPost(HttpServletRequest req, HttpServletResponse resp) 
	throws ServletException, IOException {
		
	PrintWriter out = resp.getWriter();
		
	try { 
	
		Class.forName("com.mysql.jdbc.Driver");
		String jdbcUrl = "jdbc:mysql://localhost:3306";
		String username = "root";
		String password = "pass";
		Connection connection = null;
	 
		connection = DriverManager.getConnection(jdbcUrl, username, password);
		Statement statement = connection.createStatement();
		statement.executeQuery("use employees;");
	
		if (req.getParameter("idNum") != null){
			
			String sql1 = "DELETE FROM EMPLOYEES WHERE e_id = " + req.getParameter("idNum");
			statement.executeUpdate(sql1);
			
		} else {
			
			String id = "'" + req.getParameter("id") + "',";
			String lastName = "'" + req.getParameter("lastName") + "',";
			String firstName = "'" + req.getParameter("firstName") + "',";
			String dateOfBirth = "'" + req.getParameter("dateOfBirth") + "',";
			String address = "'" + req.getParameter("address") + "',";
			String city = "'" + req.getParameter("city") + "',";
			String state = "'" + req.getParameter("state") + "'";
			String sql2 = "INSERT INTO EMPLOYEES VALUES (" +id+lastName+firstName+dateOfBirth+address+city+state+");"; 
			statement.executeUpdate(sql2);
		
		}
	
		correctResponse(resp);
	
	} catch (Exception ex) {

		out.println("<html>");
		out.println("<body>");
		out.println("<t1>" + ex + "</t1>");
		out.println("</body>");
		out.println("</html>");	
	
	}
 }
 
 // If user successfully logs in
 // Logs in the database and displays it on a table
 // Included features such as adding and deleting employees
 public void correctResponse(HttpServletResponse resp) throws IOException{
	
	PrintWriter out = resp.getWriter();
	
	try {

		
		Class.forName("com.mysql.jdbc.Driver");
		String jdbcUrl = "jdbc:mysql://localhost:3306";
		String username = "root";
		String password = "pass";
		Connection connection = null;
	 
		connection = DriverManager.getConnection(jdbcUrl, username, password);
		Statement statement = connection.createStatement();
		statement.executeQuery("use employees;");
	 
		String sql = "SELECT * FROM EMPLOYEES;";
		ResultSet rs = statement.executeQuery(sql);
	 
		out.println("<html>");
		out.println("<body>");
		out.println("<t1> EMPLOYEE DATABASE </t1>");
		out.println("<table border='2'>");
	 
		out.println("<tr>");
		out.println("<th>ID</th>");
		out.println("<th>LAST NAME</th>");
		out.println("<th>FIRST NAME</th>");
		out.println("<th>DATE OF BIRTH</th>");
		out.println("<th>ADDRESS</th>");
		out.println("<th>CITY</th>");
		out.println("<th>STATE</th>");
		out.println("</tr>");
	 
		while (rs.next()){
			String id = rs.getString("e_id");
			String lastName = rs.getString("e_lastName");
			String firstName = rs.getString("e_firstName");
			String dateOfBirth = rs.getString("e_dateOfBirth");
			String address = rs.getString("e_address");
			String city = rs.getString("e_city");
			String state = rs.getString("e_state");
		
			out.println("<tr>");
			out.println("<td>"+id+"</td>");
			out.println("<td>"+lastName+"</td>");
			out.println("<td>"+firstName+"</td>");
			out.println("<td>"+dateOfBirth+"</td>");
			out.println("<td>"+address+"</td>");
			out.println("<td>"+city+"</td>");
			out.println("<td>"+state+"</td>");
			out.println("</tr>");
		}
		
		out.println("</table>");
			
		out.println("<form action='/databaseProj/servlet' method='post'>");
		out.println("Delete a user by ID: <input type='text' name='idNum'>");
		out.println("<input type='submit' value='Delete'>");
		out.println("</form>");
		out.println("<br>");
		out.println("<br>");
		
		out.println("<form action='/databaseProj/servlet' method='post'>");
		out.println("Add an employee: "); 
		out.println("<br>ID            <input type='text' name='id'>");
		out.println("<br>Last Name     <input type='text' name='lastName'>");
		out.println("<br>First Name    <input type='text' name='firstName'>");
		out.println("<br>Date Of Birth <input type='text' name='dateOfBirth'>");
		out.println("<br>Address       <input type='text' name='address'>");
		out.println("<br>City          <input type='text' name='city'>");
		out.println("<br>State         <input type='text' name='state'>");
		out.println("<input type='submit' value='Add'>");
		out.println("</form>");
		out.println("<br>");
		out.println("<br>");
		
		
		out.println("<form action='/databaseProj/index.html' method='get'>");
		out.println("<input type='submit' value='Back'>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
 
 
	} catch (Exception ex) { 		
		
		
		out.println("<html>");
		out.println("<body>");
		out.println("<t1>" + ex + "</t1>");
		out.println("</body>");
		out.println("</html>");		
		
	}
 }
 
  // If the user does not log in successfully
  // will display an erro message and give the option
  // to go back 
  public void incorrectResponse(HttpServletResponse resp, String msg) throws IOException{
	 PrintWriter out = resp.getWriter();
	 out.println("<html>");
	 out.println("<script>");
	 out.println("function goBack(){");
	 out.println("window.history.back()}");
	 out.println("</script>");
	 out.println("<body>");
	 out.println("<t1>" + msg + "</t1>");
	 out.println("<button onclick='goBack()'>Go Back</button>");
	 out.println("</body>");
	 out.println("</html>");
 }
 
 
}