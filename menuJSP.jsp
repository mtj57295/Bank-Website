<%-- 
    Document   : menuJSP
    Created on : May 8, 2017, 2:12:13 PM
    Author     : mtj57295
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.sql.ResultSetMetaData"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="newPackage.Bank"%>

<jsp:useBean id ="myBean" scope ="session" class ="newPackage.Bank">
            <jsp:setProperty name="myBean" property= "*"/>
</jsp:useBean> 
<jsp:useBean id="myCustomer" scope="session" class="newPackage.Customers">
    <jsp:setProperty name="myCustomer" property="*"/>
</jsp:useBean>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    <center>
        <form action="http://localhost:8080/Bank/MenuHTML.html" method="POST">
        <%ResultSet rs = myBean.getResultSet();
          DecimalFormat df = new DecimalFormat("#.##");
        %>
     <% 
        if(request.getParameter("View") !=  null)
        {
     %>   
         <h1>Account Information:</h1>
        
        <table border ="1">           
            <tr>
                <%while(rs.next())
                {%>
                    <td><%=rs.getString("COLUMN_NAME")%></td>
                <%}%>
            </tr>
            <%rs = myBean.accessAccount(myCustomer.getId());%>
                
            <tr>         
                <% while(rs.next())
                {%>
                    <td><%=rs.getString("id")%></td>
                    <td><%=rs.getString("password")%></td>
                    <td><%=rs.getString("name")%></td>
                    <td><%=rs.getString("address")%></td>
                    <td><%=df.format(rs.getDouble("checking"))%></td>
                    <td><%=df.format(rs.getDouble("savings"))%></td>
                    <td><%=df.format(rs.getDouble("moneyMarket"))%></td>                
                <%}%>
            </tr>
        </table>
            <br>
      <%}%>
       
        <%if(request.getParameter("Transfer") != null){
     
            myBean.makeTransaction(myCustomer.getId(),
                request.getParameter("transferFrom"), 
                request.getParameter("transferTo"),
                request.getParameter("amount"));
        %>
        <strong>
            <%out.print(request.getParameter("amount"));%>$ has been 
           transfered from <%out.print(request.getParameter("transferFrom"));%>
          account to <%out.print(request.getParameter("transferTo"));%> account
        </strong>
        <br>
        <table border ="1">           
            <tr>
                <%while(rs.next())
                {%>
                    <td><%=rs.getString("COLUMN_NAME")%></td>
                <%}%>
            </tr>
            <%rs = myBean.accessAccount(myCustomer.getId());%>
                
            <tr>         
                <% while(rs.next())
                {%>
                    <td><%=rs.getString("id")%></td>
                    <td><%=rs.getString("password")%></td>
                    <td><%=rs.getString("name")%></td>
                    <td><%=rs.getString("address")%></td>
                    <td><%=df.format(rs.getDouble("checking"))%></td>
                    <td><%=df.format(rs.getDouble("savings"))%></td>
                    <td><%=df.format(rs.getDouble("moneyMarket"))%></td>                
                <%}%>
            </tr>
        </table>
            <br>
    <%  
        }
        
        
        if(request.getParameter("View Transactions") != null){
    %>  
            <h1>Account Information:</h1>
            <% rs = myBean.seeTransactions(myCustomer.getId()
                    ,request.getParameter("startDate"),
                    request.getParameter("endDate"));
            %>
        <table border ="1">           
            <tr>
                <td>ID</td>
                <td>Amount</td>
                <td>Transfered From</td>
                <td>Transfered To</td>
                <td>Date<td>
            </tr>      
                    
            <% while(rs.next())
            {%>
                <tr>    
                    <td><%=rs.getString("user")%></td> 
                    <td><%=rs.getDouble("amount")%></td>   
                    <td><%=rs.getString("fromAccount")%></td>   
                    <td><%=rs.getString("toAccount")%></td>   
                    <td><%=rs.getDate("date")%></td>   
                </tr>
            <%}%>
            </tr
        </table>
            <br>
    <%  
        }
    
        if(request.getParameter("Deposit") !=  null)
        {
            myBean.deposit(myCustomer.getId(), 
            request.getParameter("amountDeposit"), 
            request.getParameter("accountDeposit"));
    %>
            <strong>You have successfully deposited $
            <%out.print(request.getParameter("amountDeposit"));%> into 
            <%out.print(request.getParameter("accountDeposit"));%> account 
            </strong>
    <%
        }
     
    
        if(request.getParameter("Withdraw") !=  null)
        {
            myBean.withdraw(myCustomer.getId(), 
            request.getParameter("amountWithdraw"), 
            request.getParameter("accountWithdraw"));
    %>
            <strong>You have successfully withdrawn $
            <%out.print(request.getParameter("amountWithdraw"));%> out of 
            <%out.print(request.getParameter("accountWithdraw"));%> account 
            </strong>
    <%
        }
    %> 

        <Strong> Go back to the menu: </strong>
        <input type="submit" name="Back" value="Back"
    </form>
    </center>
    </body>
</html>
