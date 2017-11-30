/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newPackage;

public class Customers 
{
    private String id;
    private String password;
    private String name;
    private String address;
    private double checking;
    private double savings;
    private double moneyMarket;
    private boolean submit;
    
    public void setSubmit(boolean sub){submit = true;}
    public boolean getSubmit(){return submit;} 
    
    public void setId(String i){id = i;}
    public String getId(){return id;}
    
    public void setPassword(String p){password = p;}
    public String getPassword(){return password;}
    
    public void setName(String n){name = n;}
    public String getName(){return name;}
    
    public void setAddress(String a){address = a;}
    public String getAddress(){return address;}
    
    public void setChecking(double c){checking = c;}
    public Double getChecking(){return checking;}
    
    public void setSavings(double s){savings = s;}
    public Double getSavings(){return savings;}
    
    public void setMoneyMarket(double m){moneyMarket =  m;}
    public Double getMoneyMarket(){return moneyMarket;}
    
    public void setAccount(){
        
    }
}
