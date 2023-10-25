/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.dao;

import hms.dbutil.DataBaseConnection;
import hms.pojo.DoctorPojo;
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
public class DoctorDAO {
    public static String getNextId()throws SQLException
    {
         Connection conn=DataBaseConnection.getConnection();
         Statement st=conn.createStatement();
         ResultSet rs=st.executeQuery("select max(doctorid) from doctors");
         rs.next();
         String id=rs.getString(1);
         if(id==null)
         {
             return "D101";
         }
         int dno=Integer.parseInt(id.substring(1));
         dno=dno+1;
         return "D"+dno;
        
    }
    public static boolean addDoctors(DoctorPojo doctor)throws SQLException
    {
        Connection conn=DataBaseConnection.getConnection();
        PreparedStatement ps  = conn.prepareStatement("insert into employees values(?,?,?,?)");
        ps.setString(1, doctor.getEmpId());
        ps.setString(2, doctor.getDoctorname());
        ps.setString(3, doctor.getSpecialist());
        ps.setDouble(4, doctor.getSalary());
        int ans = ps.executeUpdate();
        if(ans ==0) return false;
        
        ps=conn.prepareStatement("insert into doctors values(?,?,?,?,?,?,?,?,?)");
        ps.setString(1, doctor.getDoctorid());
        ps.setString(2, doctor.getDoctorname());
        ps.setInt(3,doctor.getAge());
        ps.setString(4, doctor.getSpecialist());
        ps.setDouble(5,doctor.getSalary());
        ps.setString(6, doctor.getPassword());
        ps.setString(7, doctor.getAdress());
        ps.setString(8, doctor.getEmpId());
        ps.setLong(9, doctor.getContactno());
        int a=ps.executeUpdate();
        return a==1;
        
        
    }
    
    public static List<DoctorPojo> viewDoctorsDetails() throws SQLException
    {
        Connection conn=DataBaseConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select doctorid,doctorname,age,specialist,salary,address,empid,contactnum from doctors");
        ArrayList<DoctorPojo> doctors=new ArrayList<>();
        while(rs.next())
        {
            DoctorPojo d=new DoctorPojo();
            d.setDoctorid(rs.getString(1));
            d.setDoctorname(rs.getString(2));
            d.setAge(rs.getInt(3));
            d.setSpecialist(rs.getString(4));
            d.setSalary(rs.getDouble(5));
            d.setAdress(rs.getString(6));
            d.setEmpId(rs.getString(7));
            d.setContactno(rs.getLong(8));
            
            doctors.add(d);
            
        }
        return doctors;
    }
    public static List<String> generateDoctorId() throws SQLException
    {
        Connection conn=DataBaseConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select  doctorid from doctors order by doctorid");
        ArrayList<String> list=new ArrayList<>();
        while(rs.next())
        {
            list.add(rs.getString(1));
        }
        return list;
    }
    public static boolean deleteDoctor(String deptid) throws SQLException
    {
         Connection conn=DataBaseConnection.getConnection();
         PreparedStatement ps=conn.prepareStatement("delete from doctors where doctorid=?");
         ps.setString(1, deptid);
         int x=ps.executeUpdate();
         return x==1;
    }
    public static DoctorPojo getDoctorDetailsById( String id) throws SQLException
    {
         Connection conn=DataBaseConnection.getConnection();
         PreparedStatement ps=conn.prepareStatement("select * from doctors where doctorid=?");
         ps.setString(1, id);
         ResultSet rs=ps.executeQuery();
         rs.next();
         
        DoctorPojo d=new DoctorPojo();
        d.setDoctorid(rs.getString(1));
        d.setDoctorname(rs.getString(2));
        d.setAge(rs.getInt(3));
        d.setSpecialist(rs.getString(4));
        d.setSalary(rs.getDouble(5));
        d.setPassword(rs.getString(6));
        d.setAdress(rs.getString(7));
        d.setEmpId(rs.getString(8));
       
        d.setContactno(rs.getLong(9));
         
        
           return d;
         
         
    }
    
    public static boolean updateDoctors(DoctorPojo d) throws SQLException
    {  
         Connection conn=DataBaseConnection.getConnection();
         PreparedStatement ps=conn.prepareStatement("update doctors set doctorname=?,age=?,specialist=?,salary=?,password=?,address=?,empid=?,contactnum=? where doctorid=? ");
         
        
         ps.setString(1, d.getDoctorname());
         ps.setInt(2,d.getAge());
         ps.setString(3, d.getSpecialist());
         ps.setDouble(4, d.getSalary());
         ps.setString(5,d.getPassword());
         ps.setString(6, d.getAdress());
         ps.setString(7,d.getEmpId());
         ps.setLong(8, d.getContactno());
         ps.setString(9, d.getDoctorid());
         
         int result=ps.executeUpdate();
         if(result==0)
             return false;
         else
         {
             boolean a=EmployeeDAO.isDoctorPresent(d.getEmpId());
             if(a==false)
                 return true;
         
          ps=conn.prepareStatement("update employees set empname=?,job='doctor',salary=?  where empid=?");
          ps.setString(1,d.getDoctorname());
          ps.setDouble(2,d.getSalary());
          ps.setString(3,d.getEmpId());
           int b=ps.executeUpdate();
           return b==1;
         
         }
    }
}
