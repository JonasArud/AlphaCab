<%@page import="java.text.DecimalFormat"%>
<%@page import="java.sql.DriverManager"%>
<%@ page language="java" 
         contentType="text/html; charset=windows-1256"
         pageEncoding="windows-1256"
         import="obj.UserBean"
         import="java.sql.DriverManager"
         import="java.sql.ResultSet"
         import="java.sql.Statement"
         import="java.sql.Connection"
         %>

<html>
    <head>
        <link rel="stylesheet" href="primaryStyle.css">
        <title> Customer Home Page </title>
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
            int Id = -1;
            //HttpSession session = request.getSession(false);
            String userIDtemp = (String) session.getAttribute("userid");
            Id = Integer.parseInt(userIDtemp);
            System.out.println("User ID is " + Id);

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
                        userType = cookie.getName();
                    }
                    if (cookie.getName().equals("userId")) {
                        Id = Integer.parseInt(userIDtemp);
                    }
                }
            } else {
                sessionID = session.getId();
            }
        %>
        <ul>
            <li><a class="active" href="customerHome.jsp"> View bookings </a></li>
            <li><a href="bookACab.jsp"> New Booking </a></li>
            <li style="float:right" ><a> <%=userName%> </a></li>
        </ul>

        <div>
            <form action="LogoutServlet" method="post">
                <input style="float:right" class="ButtonSubmit" type="submit" value="Logout" >
            </form>
        </div>

        <div class="container">
            <div class="subHeader">
                <%
                    String driverName = "org.apache.derby.jdbc.ClientDriver";
                    String connectionUrl = "jdbc:derby://localhost:1527/userlogin";
                    String userId = "pass";
                    String password = "pass";

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
                                <td><b> Booking Reference </b></td>
                                <td><b> Pick-up Date and Time </b></td>
                                <td><b> Distance (Miles) </b></td>
                                <td><b> Journey Cost (VAT) </b></td>
                                <td><b> Pick Up Location </b></td>
                                <td><b> Destination </b></td>
                            </tr>
                        </table>
                    </div>
                    <%
                        try {
                            connection = DriverManager.getConnection(connectionUrl, userId, password);
                            statement = connection.createStatement();
                            String sql = "SELECT * FROM PASS.BOOKING_TABLE WHERE CUSTOMERID=" + Id;
                            System.out.println(Id);
                            resultSet = statement.executeQuery(sql);
                            DecimalFormat df = new DecimalFormat("##.00");
                            while (resultSet.next()) {
                            double VATCalc = (Double.parseDouble(resultSet.getString("paymentamount"))) / 5;
                    %>
                    <div id="tbl-content">
                        <table>
                            <tr>
                                <td><%=resultSet.getString("bookingreference")%></td>
                                <td><%=resultSet.getString("starttime")%></td>
                                <td><%=resultSet.getString("distanceinmiles")%></td>
                                <td>£<%=resultSet.getString("paymentamount")%> (£<%=df.format(VATCalc)%>)</td>
                                <td><%=resultSet.getString("originName")%></td>
                                <td><%=resultSet.getString("destinationName")%></td>
                            </tr>
                        </table>
                    </div>

                    <%
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    %>
                </section>
                <br>
            </div>
        </div>
    </body>
</html>
