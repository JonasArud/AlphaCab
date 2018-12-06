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

<html>
    <head>
        <link rel="stylesheet" href="primaryStyle.css">
        <title> Driver Home Page </title>
        <style>
            table{
                width:100%;
                table-layout: fixed;
            }

            table.tbl-header{
                background-color: rgba(255,255,255,0.3);
            }

            table.tbl-content{
                height:300px;
                overflow-x:auto;
                margin-top: 0px;
                border: 1px solid rgba(255,255,255,0.3);
            }

            th{
                padding: 20px 15px;
                text-align: left;
                font-weight: 500;
                font-size: 12px;
                color: #fff;
                text-transform: uppercase;
            }

            td{
                padding: 15px;
                text-align: left;
                vertical-align:middle;
                font-weight: 300;
                font-size: 15px;
                color: #fff;
                border-bottom: solid 1px rgba(255,255,255,0.1);
            }

            section {
                margin: 50px;
            }

            /* for custom scrollbar for webkit browser*/

            ::-webkit-scrollbar {
                width: 6px;
            }

            ::-webkit-scrollbar-track {
                -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3); 
            }

            ::-webkit-scrollbar-thumb {
                -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3); 
            }

            ul {
                list-style-type: none;
                margin: 0;
                padding: 0;
                overflow: hidden;
                background-color: #333;
            }

            li {
                float: left;
            }

            li a {
                display: block;
                color: white;
                text-align: center;
                padding: 0.75vw 1vw;
                text-decoration: none;
            }

            li a:hover {
                background-color: #111;
                color: #ffffff;
            }

            .active {
                background-color: #ffda00;
                color: #000000;
            }

            input.ButtonSubmit{
                background-color: #f44336;
                color: white;
                padding: 14px 25px;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                border:0px;
            }
        </style>
    </head>

    <body>
        <%
            // Allow access only if session exists
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

            if (userType.equals("HeadOffice")) { // Check that the user has correct access rights
                System.out.println("admin succsessfully logged onto system.");
            } else {
                System.out.println("Login unsuccsessfull, user is not a admin.");
                response.sendRedirect("index.html");
            }
        %>
        <ul>
            <li><a  href="headOfficeHome.jsp">Home</a></li>
            <li><a href="customers.jsp">View Customers</a></li>
            <li><a href="drivers.jsp">View Drivers</a></li>
            <li><a href="turnover.jsp">View Turnover</a></li>
            <li><a href="completedJobs.jsp">Completed Jobs</a></li>
            <li><a class="active" href="assignJobs.jsp">Assign Jobs</a></li>
            <li style="float:right" ><a><%=userName%></a></li>
        </ul>
        <br>
        <div>
            <form action="LogoutServlet" method="post">
                <input style="float:right" class="ButtonSubmit" type="submit" value="Logout">
            </form>
        </div>
        <br><br><br><br>
        <div align="center" class="mainHeader"> Job List </div>
        <%
            String driverName = "org.apache.derby.jdbc.ClientDriver";
            String connectionUrl = "jdbc:derby://localhost:1527/userlogin";
            String userId = "pass";
            String password = "pass";

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
            </div>
            <%
                try {
                    connection = DriverManager.getConnection(connectionUrl, userId, password);

                    PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM BOOKING_TABLE WHERE DRIVERID = 0");

                    ResultSet result2 = statement2.executeQuery();
                    System.out.println("className.methodName()");
                    while (result2.next()) {
            %>
            <div id="tbl-content">
                <table>
                    <tr>
                    <form class="formCenter" role="form" action="AssignDriverServlet" method="post">
                        <td> <input type="text" class="textField" name="id" placeholder="Driver ID"></td>

                        <td><%=result2.getString("StartTime")%></td>

                        <td><%=result2.getString("endTime")%></td>

                        <td><input type="text" class="textField" name="cid" value="<%=result2.getString("CustomerID")%>"></td>

                        <td><%=result2.getString("Bookingreference")%></td>

                        <td><%=result2.getString("Distanceinmiles")%></td>

                        <td><%=result2.getString("Paymentamount")%></td>

                        <td><%=result2.getString("paymenttime")%></td>

                        <td><button type="submit" class="submitButton">Assign Driver</button></td>
                    </form>

                    </tr>
                </table>
            </div>
            <%
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("className.methodName()");
                }
            %>
        </section>
    </body>
</html>



