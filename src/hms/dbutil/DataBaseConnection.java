/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class DataBaseConnection {
     private static Connection conn;
    static
    {
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            conn=DriverManager.getConnection("jdbc:oracle:thin:@//SARITA:1521/XE","hms","hms");
            JOptionPane.showMessageDialog(null, "connection opened successfully","sucess",JOptionPane.INFORMATION_MESSAGE);
            
        }
        catch(ClassNotFoundException cnfe)
        {
            JOptionPane.showMessageDialog(null,"Driver Class is not loaded","Error",JOptionPane.ERROR_MESSAGE);
            cnfe.printStackTrace();
            System.exit(1);
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null,"Connection Not Opened","Error",JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(1);
        }
    }
    public static Connection getConnection()
    {
        return conn;
    }
    public static void closeConnection()
    {
        try
        {
            conn.close();
            JOptionPane.showMessageDialog(null,"Connection Closed successfully","success",JOptionPane.INFORMATION_MESSAGE);
            
        }
        catch(SQLException ex)
        {
           JOptionPane.showMessageDialog(null,"Connection not closed","Error",JOptionPane.ERROR_MESSAGE);
           ex.printStackTrace();
            
        }
    }
}
