/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.dao;

import hms.dbutil.DataBaseConnection;
import hms.pojo.AppointmentPojo;
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
public class AppointmentDAO {
    
    public static List<String> getDoctorId()throws SQLException
    {
         Connection conn=DataBaseConnection.getConnection();
         Statement st=conn.createStatement();
         ResultSet rs=st.executeQuery("select doctorid from doctors");
         ArrayList<String> list=new ArrayList<>();
          while(rs.next())
         {
             String id=rs.getString(1);
             list.add(id);
         }
          return list;
        
         
         
    }
    public static boolean createAppointment(AppointmentPojo p) throws SQLException
    {
        Connection conn=DataBaseConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("insert into appointments values(?,?,?,?,?)");
        ps.setString(1, p.getPatientid());
        ps.setString(2,p.getDoctorid());
       
        ps.setDate(3,p.getDate());
        ps.setString(4,p.getTime());
        ps.setDouble(5,p.getCharge());
        int result=ps.executeUpdate();
         return result==1;
    }
    public static List<AppointmentPojo> ViewAppointment( String doctorid) throws SQLException
    {
         Connection conn=DataBaseConnection.getConnection();
         PreparedStatement ps=conn.prepareStatement("select * from appointments where doctorid=?");
         ps.setString(1, doctorid);
         ResultSet rs=ps.executeQuery();
         ArrayList<AppointmentPojo> list=new ArrayList<>();
         while(rs.next())
         {
             AppointmentPojo p=new AppointmentPojo();
            
             p.setPatientid(rs.getString(1));
             p.setDoctorid(rs.getString(2));
             p.setDate(rs.getDate(3));
             p.setTime(rs.getString(4));
             p.setCharge(rs.getDouble(5));
             
             list.add(p);
             
             
         }
         return list;
    }
}
