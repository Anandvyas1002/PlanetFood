/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PlanetFood.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Anand
 */
public class DBConnection {
    
      private static Connection conn;//static block only access static data.
    static
    {
        try
        {
           Class.forName("oracle.jdbc.OracleDriver");
           conn=DriverManager.getConnection("jdbc:oracle:thin:@//DESKTOP-9A16B7R:1521/XE","myproject","abc");
           JOptionPane.showMessageDialog(null,"Connection successfully to the database","Success",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null,"Error in Data Base ","Error",JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        catch(ClassNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null,"Error in londing drirver","Error",JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

    /**
     *
     * @return
     */
    }
    public static Connection getConnection ()
        {
              return conn;
        }
    public static void closeConnection()
    {
        try
        {
            conn.close();
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null,"Error in closing connection","Error",JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

   
    
}
