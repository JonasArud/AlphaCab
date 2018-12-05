package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceRef;
import model.ConnectionManager;
import server.IOException_Exception;
import server.ProtocolException_Exception;
import server.WebService;

public class GoogleMapsServlet extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/WebServiceServer/WebService.wsdl")
    private WebService service;

    private static String api = "AIzaSyC30fCnCqt0kI4tdOqRMm4mg0kW5oe1tGo";
    static Connection currentCon = null;
    static ResultSet rs = null;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
    private static final int mileageRate = 2;
    private static final int flatRate = 10;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ProtocolException_Exception, IOException_Exception {
        response.setContentType("text/html;charset=UTF-8");
        String ori = request.getParameter("origins");
        String desti = request.getParameter("destinations");
        String a = ori.replace(' ', '-');
        String b = desti.replace(' ', '-');
        System.out.println(ori + " : " + a);
        System.out.println(desti + " : " + b);
        String distance = googleMapsDistance(a,b);
        int dist = parseInt(distance);
        double km = dist * 0.001;
        System.out.println(km);
        float miles = (int) Math.round(km * 0.62137);
        System.out.println(miles);
        Statement stmt = null;
        String pickupTime = request.getParameter("pickupTime");
        pickupTime = pickupTime.replace("T", " ");
        pickupTime = pickupTime + ":00";
        System.out.println(pickupTime);

        ServletContext application = getServletConfig().getServletContext();

        int userID = -1;
        HttpSession session = request.getSession(false);
        String userIDtemp = (String) session.getAttribute("userid");
        userID = Integer.parseInt(userIDtemp);
        System.out.println(userID);

        int rateIncrement = 0;

        try {
            String query = "insert into PASS.BOOKING_TABLE (DRIVERID,STARTTIME,ENDTIME,CUSTOMERID,BOOKINGREFERENCE,DISTANCEINMILES,PAYMENTAMOUNT,PAYMENTTIME,JOBCOMPLETED,ORIGINNAME,DESTINATIONNAME) values (?,?,?,?,?,?,?,?,?,?,?)";

            try {
                String data = (String) application.getAttribute("increment");
                System.out.println(data);
                rateIncrement = parseInt(data);
            } catch (NumberFormatException e) {
                System.out.println("rateIncrement not set");
            }

            System.out.println(rateIncrement);
            currentCon = ConnectionManager.getConnection();
            stmt = currentCon.createStatement();
            double totalCost = 0;
            PreparedStatement ps = currentCon.prepareStatement(query); // generates sql query
            int driverId = 0;
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String ref = Long.toHexString(Double.doubleToLongBits(Math.random()));
            ps.setInt(1, driverId);
            ps.setString(2, pickupTime);
            ps.setString(3, pickupTime);
            ps.setInt(4, userID);
            ps.setString(5, ref);
            ps.setDouble(6, miles);

            // Calculate total journey cost
            if (miles < 5) {
                totalCost = flatRate + rateIncrement;
            } else {
                totalCost = flatRate + (miles * mileageRate);
            }

            ps.setDouble(7, totalCost);
            ps.setTimestamp(8, timestamp);
            ps.setBoolean(9, false);
            ps.setString(10, ori);
            ps.setString(11, desti);

            ps.executeUpdate(); // execute it on test database
            System.out.println("successfuly inserted job to database");
            ps.close();
            currentCon.close();
            response.sendRedirect("customerHome.jsp");
        } catch (SQLException e) {
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
        } catch (ProtocolException_Exception ex) {
            Logger.getLogger(GoogleMapsServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException_Exception ex) {
            Logger.getLogger(GoogleMapsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ProtocolException_Exception ex) {
            Logger.getLogger(GoogleMapsServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException_Exception ex) {
            Logger.getLogger(GoogleMapsServlet.class.getName()).log(Level.SEVERE, null, ex);
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

    private String googleMapsDistance(java.lang.String origin, java.lang.String destination) throws ProtocolException_Exception, IOException_Exception {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        server.WebServices port = service.getWebServicesPort();
        return port.googleMapsDistance(origin, destination);
    }

}
