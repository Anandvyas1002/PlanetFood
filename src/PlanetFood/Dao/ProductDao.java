/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PlanetFood.Dao;

import PlanetFood.dbutil.DBConnection;
import PlanetFood.pojo.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Anand
 */
public class ProductDao  {
    
    public static String getNewId()throws SQLException
    {
        Connection conn= DBConnection.getConnection();
       Statement st=conn.createStatement();
       int id=101;
       ResultSet rs=st.executeQuery("Select count(*)from product");
       if(rs.next())
           id=id+rs.getInt(1);
       
       return "P"+id;
    }
    
     
    
    public static boolean addProduct(Product p)throws SQLException
    {
        Connection conn= DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("insert into product values(?,?,?,?,?)");
         ps.setString(1,p.getProdId());
        ps.setString(2,p.getCatId());
         ps.setString(3, p.getProdName());
         ps.setDouble(4,p.getProdPrice());
         ps.setString(5,p.getIsActive());
         
          int count=ps.executeUpdate();
        if(count==0)
            return false;
        else
            return true;
        
    }
    
    public static boolean removeProduct(String prod_id)throws SQLException
    {
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("update product set Active='N' where prod_id=?");
        ps.setString(1,prod_id);
        
        int count=ps.executeUpdate();
        if(count==0)
            return false;
        else
            return true;
    }
    
    public static String getcatID(String str)throws SQLException
    {
        Connection conn=DBConnection.getConnection();
        String Query="Select cat_id from categories where cat_name=?";
        PreparedStatement ps=conn.prepareStatement(Query);
        ps.setString(1,str);
        ResultSet rs=ps.executeQuery();
        String cat_id=null;
        if(rs.next()){
            cat_id=rs.getString(1);
            
        }
        return cat_id;
        
    }
    public static HashMap<String,Product>getProductsByCategories(String cat_Id) throws SQLException
    {
       Connection conn=DBConnection.getConnection();
       String Qry="Select * from Product where cat_id=?";
        PreparedStatement ps=conn.prepareStatement(Qry);
        HashMap<String,Product>productList=new HashMap<>();
        ps.setString(1,cat_Id);
         ResultSet rs=ps.executeQuery();
         while(rs.next())
         {
             Product p=new Product();
             p.setCatId(cat_Id);
             p.setProdId(rs.getString("prod_id"));
             p.setProdName(rs.getString("prod_name"));
             p.setProdPrice(rs.getDouble("prod_price"));
             p.setIsActive(rs.getString("active"));
             productList.put(p.getProdId(),p);
             
         }
    
   return productList;
    }
    
    
      public static boolean editProduct(Product p)throws SQLException
    {
        
      Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("update product set prod_name= ?,prod_price= ?,Active=? where prod_id=?");
        
        ps.setString(1,p.getProdName());
         ps.setDouble(2,p.getProdPrice());
         ps.setString(3,p.getIsActive());
         ps.setString(4, p.getProdId());
        int count=ps.executeUpdate();
        if(count==0)
            return false;
        else
            return true;
    }
      
      
      public static ArrayList<Product>getAllData()throws SQLException
      {
          Connection conn=DBConnection.getConnection();
          
          Statement st=conn.createStatement();
   
       ResultSet rs=st.executeQuery("Select * from product");
      
       ArrayList<Product> pList=new ArrayList<>();
       while(rs.next()) 
       {   
            Product p=new Product();
            p.setProdId(rs.getString(1));
           p.setCatId(rs.getString(2));
           p.setProdName(rs.getString(3));
           p.setProdPrice(rs.getDouble(4));
           p.setIsActive(rs.getString(5));
           pList.add(p);
       }
       
       return pList;
       
          
      }
      
      
       public static ArrayList<Product>getAllData(String cat)throws SQLException
      {
          Connection conn=DBConnection.getConnection();
          
        
       PreparedStatement ps=conn.prepareStatement("Select * from product where cat_id=?");
       ps.setString(1, cat);
       ResultSet rs=ps.executeQuery();
      
       ArrayList<Product> pList=new ArrayList<>();
       while(rs.next()) 
       {   
            Product p=new Product();
            p.setProdId(rs.getString(1));
           p.setCatId(rs.getString(2));
           p.setProdName(rs.getString(3));
           p.setProdPrice(rs.getDouble(4));
           p.setIsActive(rs.getString(5));
           pList.add(p);
       }
       
       return pList;
       
          
      }
    
}
