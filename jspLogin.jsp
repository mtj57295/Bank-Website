<%-- 
    Document   : jspLogin
    Created on : May 4, 2017, 2:40:23 PM
    Author     : mtj57295
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="newPackage.Bank"%>

<jsp:useBean id="myCustomer" scope="session" class="newPackage.Customers">
    <jsp:setProperty name="myCustomer" property="*"/>
</jsp:useBean>

<jsp:useBean id ="myBean" scope ="session" class ="newPackage.Bank">
            <jsp:setProperty name="myBean" property= "*"/>
</jsp:useBean>  

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Login</title>
    </head>
    <body> 
        <%  String i2 = request.getParameter("id");
            String p2 = request.getParameter("password");
            String action = request.getParameter("submit");

            if(action != null)
            {
                String i = request.getParameter("i");
                String p = request.getParameter("p");
                String n = request.getParameter("n");
                String a = request.getParameter("a");
                String c = request.getParameter("c");
                String s = request.getParameter("s");
                String m = request.getParameter("m");
                myBean.openAccount(i,p,n,a,c,s,m);
                myCustomer.setId(i);
                myCustomer.setPassword(p);
            %>
                <jsp:forward page="MenuHTML.html"/>
            <%
            }       
            else if(request.getParameter("Log Out") != null)
            {
                myCustomer.setId(null);
                myCustomer.setPassword(null);
            %>  
                <jsp:forward page="index.html"/>
            <%
            }
            else if(myBean.checkLogin(i2, 
                    p2) == true)
            {
                myCustomer.setId(i2);
                myCustomer.setPassword(p2);
            %>
                <jsp:forward page="MenuHTML.html"/>     
           <%
            
            }
            else
            {%>
                <center>
                <h1>Account does not exist</h1>
                </center>
            <%}%>      
    </body>    
</html>
