/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "AssignDriverServlet", urlPatterns = {"/AssignDriverServlet"})
public class AssignDriverServlet extends HttpServlet {

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
            throws ServletException, IOException, InstantiationException, IllegalAccessException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            String id = request.getParameter("id");
            String cid = request.getParameter("cid");
            //currentCon = ConnectionManager.getConnection();

            String driverName = "org.apache.derby.jdbc.ClientDriver";
            String connectionUrl = "jdbc:derby://localhost:1527/userlogin";
            String userId = "pass";
            String password = "pass";
            try {
                Class.forName(driverName).newInstance();
            } catch (ClassNotFoundException e) {
            }

            Connection connection = null;
            Statement statement = null;
            
            Connection currentCon = null;
            Statement stmt = null;
            //id = "2";
            currentCon = DriverManager.getConnection(connectionUrl, userId, password);
            //currentCon = ConnectionManager.getConnection();
            
            
            System.out.println(cid + " " + id);
            String qry = String.format("UPDATE PASS.BOOKING_TABLE SET DRIVERID=%s WHERE ID=%s", id, cid);
            PreparedStatement ps = currentCon.prepareStatement(qry);
          
            //PreparedStatement ps = connection.prepareStatement("UPDATE PASS.BOOKING_TABLE SET JOBCOMPLETED = false WHERE BOOKINGREFERENCE = I40DNT3J98419ZQXMMEZ");
            System.out.println("com.CompleteJobServlet.processRequest()");
            ps.executeUpdate();
            System.out.println("Driver Job assigned");
            response.sendRedirect("assignJobs.jsp");

        } catch (SQLException e) {
            System.out.println("Unable to assign Driver job SQL");
            response.sendRedirect("assignJobs.jsp");
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (InstantiationException ex) {
            Logger.getLogger(CompleteJobServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CompleteJobServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (InstantiationException ex) {
            Logger.getLogger(CompleteJobServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CompleteJobServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
