package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ConnectionManager;

public class RegisterServlet extends HttpServlet {

    static Connection currentCon = null;
    static ResultSet rs = null;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Statement stmt = null;

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String userType = request.getParameter("userT");
        String firstname = request.getParameter("fn");
        String lastname = request.getParameter("ln");
        String email = request.getParameter("un");
        String password = request.getParameter("pw");
        String cpassword = request.getParameter("pw1");
        String dob = request.getParameter("date");

        // validate given input
        if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || password.isEmpty() || cpassword.isEmpty() || dob.isEmpty()) {
            RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
            out.println("<font color=red>Please fill all the fields</font>");
            rd.include(request, response);
        } else if (!password.equals(cpassword)) {
            RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
            out.println("<font color=red>The passwords did not match. Try again</font>");
            rd.include(request, response);
        } else {
            // inserting data into  database 
            try {
                String query = "";
                if(userType != null){    
                    query = "insert into PASS.DRIVER_TABLE (FIRSTNAME,LASTNAME,EMAIL,PASSWORD,DATEOFBIRTH, ISDRIVING) values (?,?,?,?,?,?)";
                }
                else{
                    query = "insert into PASS.CUSTOMER_TABLE (FIRSTNAME,LASTNAME,EMAIL,PASSWORD,DATEOFBIRTH) values (?,?,?,?,?)";
                }
                currentCon = ConnectionManager.getConnection();
                stmt = currentCon.createStatement();

                PreparedStatement ps = currentCon.prepareStatement(query); // generates sql query

                ps.setString(1, firstname);
                ps.setString(2, lastname);
                ps.setString(3, email);
                ps.setString(4, password);
                ps.setString(5, dob);
                if(userType != null){
                    ps.setBoolean(6, false);
                }
                ps.executeUpdate(); // execute it on test database
                System.out.println("successfuly inserted");
                ps.close();
                currentCon.close();
            } catch (SQLException e) {
            }
            if(userType != null){
                RequestDispatcher rd = request.getRequestDispatcher("drivers.jsp");
                out.println("<font color=black>Successfully registered.</font>");
                rd.forward(request, response);
            }else{
                RequestDispatcher rd = request.getRequestDispatcher("index.html");
                out.println("<font color=black>Successfully registered. You can now log in!</font>");
                rd.forward(request, response);  
            }
        }
    }
}
