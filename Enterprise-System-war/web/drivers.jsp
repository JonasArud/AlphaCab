<%@ page language="java" 
         contentType="text/html; charset=windows-1256"
         pageEncoding="windows-1256"
         import="obj.UserBean"
         import="java.sql.DriverManager"
         import="java.sql.ResultSet"
         import="java.sql.Statement"
         import="java.sql.Connection"
         %>
<!DOCTYPE html>

<html>
    <head>
        <link rel="stylesheet" href="primaryStyle.css">

        <title> View Drivers </title>
        
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
            if (userType.equals("HeadOffice")) {
                System.out.println("Welcome admin");

            } else {
                System.out.println("You are not an admin.. GET OUT!");
                response.sendRedirect("index.html");
            }

        %>


        <ul>
            <li><a href="headOfficeHome.jsp">Home</a></li>
            <li><a href="customers.jsp">View Customers</a></li>
            <li><a class="active" href="drivers.jsp">View Drivers</a></li>
            <li><a href="turnover.jsp">View Turnover</a></li>
            <li><a href="completedJobs.jsp">Completed Jobs</a></li>
            <li style="float:right" ><a><%=userName%></a></li>
        </ul>
        <div class="subHeader">
            <form action="LogoutServlet" method="post">
                <input style="float:right" class="ButtonSubmit" type="submit" value="Logout" >
            </form>
        </div><br>
        <a class="button" href='addDriver.jsp' role="button">Add Driver</a><br><br>
        <h2 align="center">DRIVER INFORMATION</h2><br>


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
                        <td><b>ID</b></td>
                        <td><b>First Name</b></td>
                        <td><b>Last Name</b></td>
                        <td><b>Email</b></td>
                        <td><b>Password</b></td>
                        <td><b>DOB</b></td>
                        <td><b>Driving?</b></td>
                        <td><b>Action</b></td>
                    </tr>
                </table>
            </div>    
            <%
                try {
                    connection = DriverManager.getConnection(connectionUrl, userId, password);
                    statement = connection.createStatement();
                    String sql = "SELECT * FROM PASS.DRIVER_TABLE";

                    resultSet = statement.executeQuery(sql);
                    while (resultSet.next()) {
            %>
            <div id="tbl-content">
                <table>
                    <tr>
                        <td><%=resultSet.getString("driverID")%></td>
                        <td><%=resultSet.getString("firstName")%></td>
                        <td><%=resultSet.getString("lastName")%></td>
                        <td><%=resultSet.getString("email")%></td>
                        <td><%=resultSet.getString("password")%></td>
                        <td><%=resultSet.getString("dateOfBirth")%></td>
                        <td><%=resultSet.getString("isDriving")%></td>
                        <td><a class="button" href='delete.jsp?d=<%=resultSet.getString("driverID")%>&j=<%="Driver"%>' role="button">Delete Driver</a> <br><br> <a class="button" href='checkJobs.jsp?d=<%=resultSet.getString("driverID")%>&j=<%=resultSet.getString("isDriving")%>' role="button">Check Jobs</a> <br><br> <a class="button" href='editDriver.jsp?d=<%=resultSet.getString("driverID")%>' role="button">Edit Driver</a></td>
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
    </body>
</html>

