/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PlanetFood.Dao;

import PlanetFood.dbutil.DBConnection;
import PlanetFood.pojo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import static java.util.Calendar.PM;
import java.util.HashMap;

/**
 *
 * @author Anand
 */
public class UserDao {
    
    public static String validateUser(User user)throws SQLException
    {
         
       Connection conn=DBConnection.getConnection();
        String select="select username from users where userid=? and password=? and usertype=?";
        PreparedStatement ps =conn.prepareStatement(select);
        ps.setString(1,user.getUserid());
        ps.setString(2,user.getPassword());
        ps.setString(3,user.getUserType());
        ResultSet rs=ps.executeQuery();
        String userName=null;

        if(rs.next())
        {
            userName=rs.getString(1);
            
        }
        
           return userName;
        
    }
    
    public static boolean registerCashier(User user)throws SQLException
    {
        Connection conn=DBConnection.getConnection();
        String select="insert into users values(?,?,?,?,?)";
        PreparedStatement ps =conn.prepareStatement(select);
         ps.setString(1,user.getUserid());
          select="select empname from employees where empid=?";
          PreparedStatement es =conn.prepareStatement(select);
         es.setString(1,user.getUserType());//empid
          ResultSet rs=es.executeQuery();
           if(rs.next())
        {
           ps.setString(2,rs.getString(1));
        }
       
        ps.setString(3,user.getPassword());
        ps.setString(4,user.getUserType());//empid
        ps.setString(5,"Cashier");
       
          
          
         int count=ps.executeUpdate();
         if(count==0)
            return false;
        else
            return true;   
    }
    
    public static ArrayList<String>getCashierId()throws SQLException
    {
        Connection conn=DBConnection.getConnection();
        String Qry="Select empid from employees where job='Cashier'";
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery(Qry);
        
        ArrayList<String>empList=new ArrayList<String>();
        while(rs.next())
        {  
         
           empList.add(rs.getString(1));
        }
        
        return empList;    
    }
public static boolean removeCashier(String userId)throws SQLException
    {
        Connection conn=DBConnection.getConnection();
        String str="delete from users where userid=?";
        PreparedStatement ps =conn.prepareStatement(str);
        ps.setString(1,userId);
        int count=ps.executeUpdate();
        if(count==0)     
            return false;
        else
            return true; 
    }
    public static HashMap<String,String> getAllData(String userId)throws SQLException
    {
        Connection conn=DBConnection.getConnection();
        String qry="select * from users where userid=?";
        PreparedStatement ps=conn.prepareStatement(qry);
        HashMap<String,String> userList;
        userList = new HashMap<>();
        ps.setString(1,userId);
        ResultSet rs=ps.executeQuery();
        if(rs.next())
        {
            String userName=rs.getString("username");
            String empId=rs.getString("empid");
            userList.put(userName,empId);
        }
        return userList;
    }

    
}
