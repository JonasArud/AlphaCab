<%@ page language="java" 
         contentType="text/html; charset=windows-1256"
         pageEncoding="windows-1256"
         import="obj.UserBean"
         %>

<html>
    <head>
        <link rel="stylesheet" href="primaryStyle.css">
        <title> View Turnover </title>
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
            <li><a href="drivers.jsp">View Drivers</a></li>
            <li><a class="active" href="turnover.jsp">View Turnover</a></li>
            <li><a href="completedJobs.jsp">Completed Jobs</a></li>
            <li style="float:right" ><a><%=userName%></a></li>
        </ul>
        <form action="LogoutServlet" method="post">
            <input style="float:right" class="ButtonSubmit" type="submit" value="Logout" >
        </form>

        <form method="POST" name="TurnoverForm" action="Turnover">
            Insert the 1st Date:<br>  <!-- communication  line  -->            
            <input type="date" name="StartDate">   <!-- creates a date field for starting -->  
            <br>                                                   
            Insert the 2nd Date:<br><!--2nd communication line  -->  
            <input type="date" name="EndDate"><!--creates a field(for 2ndDate)  -->  
            <br>
            <button type=submit" name="submit" value="Submit">Calculate</button> <!--Create button for the log in,type submit for the above form-->
        </form> 
    </body>
</html>