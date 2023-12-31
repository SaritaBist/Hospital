/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.dao;

import hms.dbutil.DataBaseConnection;
import hms.pojo.UserPojo;
import hms.pojo.UserProfile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author User
 */
public class UserDAO {
    public static boolean validateUser(UserPojo user) throws SQLException
    {
        Connection conn=DataBaseConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("select * from users where userid=? and password=? and usertype=?");
        ps.setString(1, user.getUserid());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getUsertype());
        ResultSet rs=ps.executeQuery();
        if(rs.next())
        {
            String username=rs.getString(5);
            UserProfile.setUsername(username);
            return true;
        }
        return false;
       
    }
     public static boolean  isUserPresent(String empid)throws SQLException
    {
         Connection conn=DataBaseConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("select 1 from users where empid=? ");
        ps.setString(1,empid);
        ResultSet rs=ps.executeQuery();
       return rs.next();
    }
}
