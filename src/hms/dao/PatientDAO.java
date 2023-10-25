/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.dao;

import hms.dbutil.DataBaseConnection;
import hms.pojo.AppointmentPojo;
import hms.pojo.PatientHistoryPojo;
import hms.pojo.PatientPojo;
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
public class PatientDAO {
    public static List<PatientPojo> getAllPatients() throws SQLException
    {
        Connection conn=DataBaseConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select * from patients");
        ArrayList<PatientPojo> list=new ArrayList<>();
        while(rs.next())
        {
            PatientPojo patient=new PatientPojo();
            patient.setPatientId(rs.getString(1));
            patient.setPatientName(rs.getString(2));
            patient.setAge(rs.getInt(3));
            patient.setGender(rs.getString(4));
            patient.setBloodgroup(rs.getString(5));
            patient.setBedNo(rs.getString(6));
            patient.setContactNo(rs.getLong(7));
            patient.setAddress(rs.getString(8));
            list.add(patient);
        }
        return list;
  }
    
    public static String generateNextId() throws SQLException
    {
         Connection conn=DataBaseConnection.getConnection();
         Statement st=conn.createStatement();
         ResultSet rs=st.executeQuery("select max(patientid) from patients");
         rs.next();
         String patientid=rs.getString(1);
         if(patientid==null)
         {
             return "P101";
         }
         int pid=Integer.parseInt(patientid.substring(1));
         pid=pid+1;
         return "P"+pid;
    }
    public static boolean addPatients(PatientPojo p) throws SQLException
    {
        Connection conn=DataBaseConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("insert into patients values(?,?,?,?,?,?,?,?)");
        ps.setString(1,p.getPatientId());
        ps.setString(2, p.getPatientName());
        ps.setInt(3,p.getAge());
        ps.setString(4, p.getGender());
        ps.setString(5,p.getBloodgroup());
        ps.setString(6,p.getBedNo());
        ps.setLong(7, p.getContactNo());
        ps.setString(8,p.getAddress());
        int a=ps.executeUpdate();
        return a==1;
        
       }
    public static List<String> getAllPatienstid() throws SQLException
    {
        Connection conn=DataBaseConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select patientid from patients");
        ArrayList<String> patientid= new ArrayList<>();
        while(rs.next())
        {
            patientid.add(rs.getString(1));
        }
        return patientid;
    }
    public static PatientPojo getAllPatientsById(String pid) throws SQLException
    {
        Connection conn=DataBaseConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("select * from patients where patientid=?");
        ps.setString(1, pid);
         ResultSet rs=ps.executeQuery();
        
         rs.next();
         PatientPojo p=new PatientPojo();
         p.setPatientId(rs.getString(1));
         p.setPatientName(rs.getString(2));
         p.setAge(rs.getInt(3));
         p.setGender(rs.getString(4));
         p.setBloodgroup(rs.getString(5));
         p.setBedNo(rs.getString(6));
         p.setContactNo(rs.getLong(7));
         p.setAddress(rs.getString(8));
         
        
        return p;
        
        
    }
    public static boolean deletePatients(String id) throws SQLException
    {
        Connection conn=DataBaseConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("delete from patients where patientid=?");
        ps.setString(1, id);
        int a=ps.executeUpdate();
        return a==1;
    } 
    public static boolean updatePatients(PatientPojo p) throws SQLException
    {
        Connection conn=DataBaseConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("update patients set patientname=?,age=?,gender=?,bloodgrp=?,bed=?,contactnum=?,address=? where patientid=?");
        ps.setString(1,p.getPatientName());
        ps.setInt(2,p.getAge());
        ps.setString(3,p.getGender());
        ps.setString(4,p.getBloodgroup());
        ps.setString(5,p.getBedNo());
        ps.setLong(6,p.getContactNo());
        ps.setString(7,p.getAddress());
        ps.setString(8,p.getPatientId());
        
        int a=ps.executeUpdate();
        return a==1;
        
    }
    
    public static List<PatientHistoryPojo> searchPatientHistory(String id) throws SQLException{
        Connection conn = DataBaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select p.patientid, p.patientname, p.age, p.gender, p.bloodgrp, p.bed, p.contactnum, p.address, a.doctorid from patients p, appointments a where p.patientid = a.patientid and p.patientid = ?");
        ps.setString(1, id);
        ResultSet rs=ps.executeQuery();
        ArrayList<PatientHistoryPojo> list=new ArrayList<>();
        while(rs.next())
        {
           PatientHistoryPojo p=new PatientHistoryPojo();
           p.setPatientId(rs.getString(1));
           p.setPatientName(rs.getString(2));
           p.setAge(rs.getInt(3));
           p.setGender(rs.getString(4));
           p.setBloodgroup(rs.getString(5));
           p.setBedNo(rs.getString(6));
           p.setContactNo(rs.getLong(7));
           p.setAddress(rs.getString(8));
           p.setDoctorId(rs.getString(9));
           
           list.add(p);
           
            }
        return list;
    }
    
}
 