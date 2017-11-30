/*
 * The Bank class is where are the sql code is to access the database. There are
    several method with sql code to exract or add things to the database. The 
    constructor also has a built getConnection method so the user does not
    have to connect himself.
 */
package newPackage;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Bank 
{
    //Attributes
    private Scanner input;
    private Connection c;
    private Statement sm;
    private String sql;
    private PreparedStatement preparedS;
    private ResultSet rs;
    private DatabaseMetaData dbmd;
    private double balance;
    private Customers customers;

    public Bank() throws SQLException
    {
        new com.mysql.jdbc.Driver();
        c = DriverManager.getConnection("jdbc:mysql://148.137.9.28/mtj57295", "mtj57295", "Husky2017");
        sm = c.createStatement();   
    }
   /**CheckLogin method interacts with the jspLogin to make sure that there is
    * a id and password in the database where they much up
    * @param id the id you plug in to login from the index.html
    * @param password th password you plug in to login from the index.html
    * @return true if there is an id and password in the database that matches
    * the users login information
    * @throws SQLException 
    */
    public boolean checkLogin(String id, String password) throws SQLException
    {
        sql = "SELECT id, password FROM MTJBank";
        rs = sm.executeQuery(sql);
        while(rs.next())
        {          
            if(id.equals(rs.getString("id")) &&
               password.equals(rs.getString("password")))
            {
               return true;       
            }     
        }
        return false;
    }
    /**theGetResultSet gets a ResultSet with the metaData from the MTJBank data
     * base
     * @return ResultSet with metaData in it
     * @throws SQLException 
     */
    public ResultSet getResultSet() throws SQLException
    {
        dbmd = c.getMetaData();
        rs = dbmd.getColumns(null, null, "MTJBank", "%");
        return rs;
    }
    /**OpenAccount adds the information that the user entered into the 
     * database
     * @param i is the id 
     * @param p is the password
     * @param n is the name
     * @param a is the address
     * @param c2 is the checking account
     * @param s is the savings account
     * @param m is the money market account
     * @throws SQLException 
     */
    public void openAccount(String i, String p, String n, String a, 
    String c2, String s, String m) throws SQLException
    {
        Double c1= Double.parseDouble(c2);
        Double s1= Double.parseDouble(s);
        Double m1= Double.parseDouble(m);
        sql = "INSERT INTO MTJBank VALUES(?, ?, ?, ?, ?, ?, ?)";
        preparedS = c.prepareStatement(sql);
        preparedS.setString(1, i);
        preparedS.setString(2, p);
        preparedS.setString(3, n);
        preparedS.setString(4, a);
        preparedS.setDouble(5, c1);
        preparedS.setDouble(6, s1);
        preparedS.setDouble(7, m1);  
        preparedS.execute();
    }
    /**accessAccount will use sql to retrieve the table that has the id number
     * in it and put it in a ResultSet
     * @param id is the id
     * @return a ResultSet with data quried out
     * @throws SQLException 
     */
    public ResultSet accessAccount(String id) throws SQLException
    {
        sql = "SELECT * FROM MTJBank WHERE id ="+ id;
        preparedS = c.prepareStatement(sql);
        rs = preparedS.executeQuery();
        return rs;  
    }
    /**makeTransaction uses sql to make a transaction between two accounts
     * @param id is the id number
     * @param transferFrom the account you want to transfer out of
     * @param transferTo the account you want to transfer to
     * @param amountString is the amount you want transfered
     * @throws SQLException 
     */
    public void makeTransaction(String id, String transferFrom, 
            String transferTo, String amountString) throws SQLException
    {
        double amount = Double.parseDouble(amountString);
        sql = "SELECT * FROM MTJBank WHERE id ="+ id;
        preparedS = c.prepareStatement(sql);
        rs = preparedS.executeQuery();
        while(rs.next())
        {
                sql = "SELECT " +transferFrom+ " FROM MTJBank WHERE id = " + id;
                preparedS = c.prepareStatement(sql);
                rs = preparedS.executeQuery();
                if(rs.next())    
                    balance = rs.getDouble(transferFrom); 
                balance -= amount;
                sql = "UPDATE MTJBank SET " +transferFrom + " = " + balance+ 
                        " WHERE id = " + id;
                sm.executeUpdate(sql);
                sql = "SELECT " +transferTo+ " FROM MTJBank WHERE id = " + id;
                preparedS = c.prepareStatement(sql);
                rs = preparedS.executeQuery();
                if(rs.next())    
                    balance = rs.getDouble(transferTo); 
                balance += amount;               
                sql = "UPDATE MTJBank SET " +transferTo + " = " + balance+ 
                        " WHERE id = " + id; 
                sm.executeUpdate(sql);     
                sql = "INSERT INTO MTJTransactions VALUES('"+id+"', '"+amount
                        +"', '"+transferFrom+"', '"+transferTo+"', NOW())";
                sm.executeUpdate(sql);
        }
    }
    /**seeTransactions allows you to view all the transactions you made between 
     * the three accounts, between certain dates
     * @param id is the id number
     * @param startDate is the date where you want to start from
     * @param endDate is the date where you want to end at
     * @return a ResultSet with the data quried out to display the transactions
     * @throws SQLException 
     */
    public ResultSet seeTransactions(String id, String startDate, String endDate) throws SQLException
    {
        sql = "SELECT * FROM MTJTransactions WHERE user=" + id;
        preparedS = c.prepareStatement(sql);
        rs = preparedS.executeQuery();
        if(rs.next())
        {
                sql = "SELECT * FROM MTJTransactions WHERE "
                        + "date >= " + startDate + " AND date <= " + endDate; 
                rs = preparedS.executeQuery();        
        }  
        return rs;
    }
    /**deposit will allow the user to deposit into any three accounts
     * @param id the id number
     * @param amount you want to deposit into account
     * @param account you want to deposit into
     * @throws SQLException 
     */
    public void deposit(String id, String amount, String account) throws SQLException
    {
        double amount2 = Double.parseDouble(amount);
        sql = "SELECT * FROM MTJBank WHERE id = "+ id;
        preparedS = c.prepareStatement(sql);
        rs = preparedS.executeQuery();
        while(rs.next())
        {
            sql = "SELECT " + account + " FROM MTJBank WHERE id = " + id;
            preparedS = c.prepareStatement(sql);
            rs = preparedS.executeQuery();
            if(rs.next())
                balance = rs.getDouble(account);
            balance += amount2;
            sql = "UPDATE MTJBank SET " + account + " = " + balance + 
                        " WHERE id = " + id; 
            sm.executeUpdate(sql);
        }

    }
    /**withdrawal will allow user to withdrawal out of any three accounts
     * @param id is the id number
     * @param amount is the amount you want to withdrawal
     * @param account is the account you want to withdrawal money from
     * @throws SQLException 
     */
    public void withdraw(String id, String amount , String account) throws SQLException
    {
        double amount2 = Double.parseDouble(amount);
        sql = "SELECT * FROM MTJBank WHERE id = "+ id;
        preparedS = c.prepareStatement(sql);
        rs = preparedS.executeQuery();
        while(rs.next())
        {
            sql = "SELECT " + account + " FROM MTJBank WHERE id = " + id;
            preparedS = c.prepareStatement(sql);
            rs = preparedS.executeQuery();
            if(rs.next())
                balance = rs.getDouble(account);
            balance -= amount2;
            sql = "UPDATE MTJBank SET " + account + " = " + balance + 
                        " WHERE id = " + id; 
            sm.executeUpdate(sql);
        }
    }
}  
