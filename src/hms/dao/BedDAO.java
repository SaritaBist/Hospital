/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.dao;

import hms.dbutil.DataBaseConnection;
import hms.pojo.BedPojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class BedDAO {
    
    public static boolean addBed(BedPojo b) throws SQLException
    {
        Connection conn=DataBaseConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("insert into bed values(?,?,?)");
        ps.setString(1,b.getBedid());
        ps.setInt(2,b.getRoomno());
        ps.setString(3,b.getPatientId());
        int ans=ps.executeUpdate();
        return 1==ans;
    }
    
    public static List<BedPojo> viewBed()throws SQLException
    {
         Connection conn=DataBaseConnection.getConnection();
         Statement st=conn.createStatement();
         ResultSet rs=st.executeQuery("select * from bed");
         ArrayList<BedPojo> list=new ArrayList<>();
          while(rs.next())
          {
              BedPojo b =new BedPojo();
                 b.setBedid(rs.getString(1));
                 b.setRoomno(rs.getInt(2));
                 b.setPatientId(rs.getString(3));
                 list.add(b);
          }
          return list;
    }
}
