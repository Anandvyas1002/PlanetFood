/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PlanetFood.Dao;

import PlanetFood.dbutil.DBConnection;
import PlanetFood.pojo.Order;
import PlanetFood.pojo.OrderDetails;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



/**
 *
 * @author Anand
 */
public class OrderDao {
    public static ArrayList<Order> getOrdersByDate(Date startDate,Date endDate,String user_id) throws SQLException
    {
        Connection conn = DBConnection.getConnection();
       
        PreparedStatement ps=conn.prepareStatement("select * from orders where USERID=? and ord_date between ? and ? ");
         long ms1=startDate.getTime();
         long ms2=endDate.getTime();
         
         java.sql.Date d1=new java.sql.Date(ms1);
          java.sql.Date d2=new java.sql.Date(ms2);
          ps.setDate(2, d1);
          ps.setDate(3, d2);
          ps.setString(1, user_id);
          ResultSet rs= ps.executeQuery();
          ArrayList<Order> orderList =new ArrayList<>();
          while(rs.next())
          {
              Order obj=new Order();
              obj.setOrdId(rs.getString("ord_id"));
              java.sql.Date d=rs.getDate("ord_Date");
              SimpleDateFormat sdf= new SimpleDateFormat("dd-MMM-yyyy");
              String dateStr=sdf.format(d);
              obj.setOrdDate(dateStr);
              obj.setOrdAmount(rs.getDouble("ord_amount"));
              obj.setGst(rs.getDouble("gst"));
              obj.setGstAmount(rs.getDouble("gst_amount"));
              obj.setGrandTotal(rs.getDouble("grand_total"));
              obj.setUserId(rs.getString("userid"));
              orderList.add(obj);
              
          }
          return orderList;
          
          
          
         
    }
    
    
    
    
    public static ArrayList<Order> getOrdersByDate(Date startDate,Date endDate) throws SQLException
    {
        Connection conn = DBConnection.getConnection();
       
        PreparedStatement ps=conn.prepareStatement("select * from orders where ord_date between ? and ?");
         long ms1=startDate.getTime();
         long ms2=endDate.getTime();
         
         java.sql.Date d1=new java.sql.Date(ms1);
          java.sql.Date d2=new java.sql.Date(ms2);
          ps.setDate(1, d1);
          ps.setDate(2, d2);
          
          ResultSet rs= ps.executeQuery();
          ArrayList<Order> orderList =new ArrayList<>();
          while(rs.next())
          {
              Order obj=new Order();
              obj.setOrdId(rs.getString("ord_id"));
              java.sql.Date d=rs.getDate("ord_Date");
              SimpleDateFormat sdf= new SimpleDateFormat("dd-MMM-yyyy");
              String dateStr=sdf.format(d);
              obj.setOrdDate(dateStr);
              obj.setOrdAmount(rs.getDouble("ord_amount"));
              obj.setGst(rs.getDouble("gst"));
              obj.setGstAmount(rs.getDouble("gst_amount"));
              obj.setGrandTotal(rs.getDouble("grand_total"));
              obj.setUserId(rs.getString("userid"));
              orderList.add(obj);
              
          }
          return orderList;
          
          
          
         
    }
     public static ArrayList<Order> getAllOrders() throws SQLException
    {
        Connection conn = DBConnection.getConnection();
       
        PreparedStatement ps=conn.prepareStatement("select * from orders");
       
      
          ResultSet rs= ps.executeQuery();
          ArrayList<Order> orderList =new ArrayList<>();
          while(rs.next())
          {
              Order obj=new Order();
              obj.setOrdId(rs.getString("ord_id"));
              java.sql.Date d=rs.getDate("ord_Date");
             SimpleDateFormat sdf= new SimpleDateFormat("dd-MMM-yyyy");
              String dateStr=sdf.format(d);
              obj.setOrdDate(dateStr);
              obj.setOrdAmount(rs.getDouble("ord_amount"));
              obj.setGst(rs.getDouble("gst"));
              obj.setGstAmount(rs.getDouble("gst_amount"));
              obj.setGrandTotal(rs.getDouble("grand_total"));
              obj.setUserId(rs.getString("userid"));
              orderList.add(obj);
              
          }
          return orderList;
          
     }
     public static String getNewId()throws SQLException
     {
          Connection conn= DBConnection.getConnection();
       PreparedStatement ps=conn.prepareStatement("select count(*) from Orders");
       int id=101;
       ResultSet rs=ps.executeQuery();
       if(rs.next())
       {
           id=id+rs.getInt(1);
       }
       return "OD"+id;
     }
     
     
     
     public static boolean addOrder(Order order,ArrayList<OrderDetails> orderList) throws Exception
     {
         
         Connection conn=DBConnection.getConnection();
         PreparedStatement ps=conn.prepareStatement("insert into orders values(?,?,?,?,?,?,?,?)");
         ps.setString(1,order.getOrdId());
         String dateStr=order.getOrdDate();
         SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
         java.util.Date d1=sdf.parse(dateStr);
         java.sql.Date d2= new java.sql.Date(d1.getTime());
         ps.setDate(2, d2);
         ps.setDouble(3,order.getGst());
          ps.setDouble(4,order.getGstAmount());
          ps.setDouble(5,order.getDiscount());
            ps.setDouble(6,order.getGrandTotal());
         ps.setString(7,order.getUserId());
          ps.setDouble(8,order.getOrdAmount());
          
          int x= ps.executeUpdate();
           PreparedStatement ps2=conn.prepareStatement("insert into order_details values(?,?,?,?)");
          
           int count=0,y;
           for(OrderDetails details:orderList)
               
           {
               ps2.setString(1,details.getOrdId());
               ps2.setString(2,details.getProdId());
               ps2.setDouble(3,details.getQuantity());
               ps2.setDouble(4,details.getCost());
               y=ps2.executeUpdate();
               if(y>0)
                 count=count+y;
           }
           if(count==orderList.size())
               return true;
           else 
                return false;
           
         
     }
            
    
}
