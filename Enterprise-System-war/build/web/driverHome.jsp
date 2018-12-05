<%@ page language="java" 
         contentType="text/html; charset=windows-1256"
         pageEncoding="windows-1256"
         import="obj.UserBean"
         import="java.sql.DriverManager"
         import="java.sql.ResultSet"
         import="java.sql.Statement"
         import="java.sql.PreparedStatement"
         import="java.sql.Connection"
         import="java.sql.ResultSet"
         %>
<!DOCTYPE html>

<!-- Add above content ^ -->
<html>
    <head>
        <link rel="stylesheet" href="primaryStyle.css">
        <title> Driver Home Page </title>
        
    </head>

    <body>

        <%
            //allow access only if session exists
            String user = null;
            if (session.getAttribute("user") == null) {
                response.sendRedirect("index.html");
            } else {
                user = (String) session.getAttribute("user");
            }

            String userName = null;
            String sessionID = null;
            String userType = null;

            Cookie[] cookies = request.getCookies(); // Assign user variables
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("userName")) {
                        userName = cookie.getValue();
                    }
                    if (cookie.getName().equals("JSESSIONID")) {
                        sessionID = cookie.getValue();
                    }
                    if (cookie.getName().equals("userType")) {
                        userType = cookie.getValue();

                    }

                }
            } else {
                sessionID = session.getId();
            }
            if (userType.equals("Driver")) {  // Check that the user has correct privelege
                System.out.println("Welcome Driver");

            } else {
                System.out.println("Records show you are not a driver");
                response.sendRedirect("index.html");
            }

        %>

        <ul>
            <li><a href="headOfficeHome.jsp">Home</a></li>
            <li><a href="customers.jsp">PlaceHolder</a></li>
            <li><a class="active" href="drivers.jsp">PlaceHolder</a></li>
            <li style="float:right" ><a><%=userName%></a></li>
        </ul>
        <div class="subHeader">
            <form action="LogoutServlet" method="post">
                <input style="float:right" class="ButtonSubmit" type="submit" value="Logout" >
            </form>
        </div><br>
        <a class="button" href='drivers.jsp' role="button">Go back</a><br><br>

        <h2 align="center">BOOKED JOURNEYS</h2><br>


        <%

            String driverName = "org.apache.derby.jdbc.ClientDriver";
            String connectionUrl = "jdbc:derby://localhost:1527/userlogin";
            String userId = "pass";
            String password = "pass";

            String id = request.getParameter("d");
            String isD = request.getParameter("j");
            boolean isDriving = Boolean.getBoolean(isD);
            //int number = Integer.parseInt(id);

            try {
                int number = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                // log the error or ignore it
            }

            System.out.println(id + " " + isDriving);

            try {
                Class.forName(driverName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;
        %>
        <section>
            <div id="tbl-header">
                <table>
                    <tr>
                        <td><b>ID</b></td>
                        <td><b>Start Time</b></td>
                        <td><b>End Time</b></td>
                        <td><b>Customer ID</b></td>
                        <td><b>Ref</b></td>
                        <td><b>Distance</b></td>
                        <td><b>Amount</b></td>
                        <td><b>When?</b></td>
                        <td><b>Complete?</b></td>
                    </tr>
                </table>
                </<div>
                    <%
                        try {
                            connection = DriverManager.getConnection(connectionUrl, userId, password);
                            PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM DRIVER_TABLE WHERE EMAIL=?");
                            statement1.setString(1, userName);

                            ResultSet result = statement1.executeQuery();

                            System.out.println(userName);

                            while (result.next()) {
                                String driverUID = result.getString("DRIVERID");
                                System.out.println(driverUID);

                                PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM BOOKING_TABLE WHERE JOBCOMPLETED =" + false + " AND DRIVERID=?");
                                statement2.setString(1, driverUID);

                                ResultSet result2 = statement2.executeQuery();
                                while (result2.next()) {

                    %>
                    <div id="tbl-content">
                        <table>
                            <tr>
                                <td><%=result2.getString("DriverID")%></td>
                                <td><%=result2.getString("StartTime")%></td>
                                <td><%=result2.getString("endTime")%></td>
                                <td><%=result2.getString("CustomerId")%></td>
                                <td><%=result2.getString("Bookingreference")%></td>
                                <td><%=result2.getString("Distanceinmiles")%></td>
                                <td><%=result2.getString("Paymentamount")%></td>
                                <td><%=result2.getString("PaymentTime")%></td>
                                <td><%=result2.getString("jobcompleted")%></td>
                            </tr>
                        </table>
                    </div>


                    <%
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    %>
                    </section>

                    </body>
                    </html>



