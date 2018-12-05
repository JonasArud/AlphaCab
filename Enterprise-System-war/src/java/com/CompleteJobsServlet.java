/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ConnectionManager;

/**
 *
 * @author Willi
 */
@WebServlet(name = "CompleteJobsServlet", urlPatterns = {"/CompleteJobsServlet"})
public class CompleteJobsServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, InstantiationException, IllegalAccessException {
        response.setContentType("text/html;charset=UTF-8");

        String id = request.getParameter("d");
        String userType = request.getParameter("br");
        
        

        String driverName = "org.apache.derby.jdbc.ClientDriver";
        String connectionUrl = "jdbc:derby://localhost:1527/userlogin";
        String userId = "pass";
        String password = "pass";

        Connection connection = null;
        Statement statement = null;

        connection = DriverManager.getConnection(connectionUrl, userId, password);
        try {
          
                PreparedStatement ps = connection.prepareStatement("UPDATE PASS.BOOKING_TABLE SET JOBCOMPLETE= ? WHERE DRIVERID=" + id);
                ps.setObject(1, true);
                System.out.println("Driver");
                ps.executeUpdate();
            response.sendRedirect("driverHome.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Yeet");
        }
    }
}

