/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.ConnectionManager;
/**
 *
 * @author JonasArud
 */
@WebServlet(name = "CompleteJobs", urlPatterns = {"/CompleteJobs"})
public class CompleteJobs extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    static Connection currentCon = null;
    static ResultSet rs = null;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);        
        Statement stmt = null;
        String date1 = request.getParameter("StartDate");
        String date2 = request.getParameter("StartDate");
        date1 = date1 + " 00:00:00";
        date2 = date2 + " 23:59:59";
        System.out.println(date1);
        System.out.println(date2);
        try {
            boolean more = false;
            int counter = 0;
            double value = 0.0;
            String query = String.format("SELECT * FROM PASS.BOOKING_TABLE WHERE STARTTIME BETWEEN TIMESTAMP('%s') AND TIMESTAMP('%s') AND JOBCOMPLETED = true", date1, date2);
            //connect to database
            currentCon = ConnectionManager.getConnection();
            stmt = currentCon.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()){
                String customerID = rs.getString(4);
                String paymentAmount = rs.getString(7);
                String originName = rs.getString(10);
                String destinationName = rs.getString(11);
                System.out.println(customerID);
                System.out.println(paymentAmount);
                System.out.println(originName);
                System.out.println(destinationName);
                session.setAttribute("customerID", customerID);
                session.setAttribute("paymentAmount", paymentAmount);
                session.setAttribute("originName", originName);
                session.setAttribute("destinationName", destinationName);
                
            }
            response.sendRedirect("completedJobsDisplay.jsp");
            
        } catch (SQLException ex) {
            System.out.println("Failed: An Exception has occurred! " + ex);
        } //some exception handling
        finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                }
                stmt = null;
            }

            if (currentCon != null) {
                try {
                    currentCon.close();
                } catch (SQLException e) {
                }
                currentCon = null;
            }
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
        processRequest(request, response);
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
        processRequest(request, response);
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
