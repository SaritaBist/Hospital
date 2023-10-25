/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.dao;

import hms.dbutil.DataBaseConnection;
import hms.pojo.EmployeePojo;
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
public class EmployeeDAO {
     public static String getNextEmpId()throws SQLException
    {
        Connection conn=DataBaseConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select max(empid) from employees");
        rs.next();
        String empid=rs.getString(1);
        int eno=Integer.parseInt(empid.substring(1));
        eno=eno+1;
        return "E"+eno;
    }
     public static boolean isDoctorPresent(String id) throws SQLException
     {
         Connection conn=DataBaseConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("select 1 from employees where empid=? ");
        ps.setString(1,id);
        ResultSet rs=ps.executeQuery();
        return rs.next();
     }
    public static boolean addEmployee(EmployeePojo emp)throws SQLException
    {
        Connection conn=DataBaseConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("insert into employees values(?,?,?,?)");
        ps.setString(1, emp.getEmpid());
        ps.setString(2, emp.getEpname());
        ps.setString(3, emp.getJob());
        ps.setInt(4,emp.getSalary());
        int result=ps.executeUpdate();
        return result==1;
    }
    public static List<EmployeePojo> getAllEmployee()throws SQLException
    {
        Connection conn=DataBaseConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select * from employees");
        List<EmployeePojo> empList=new ArrayList<>();
        while(rs.next())
        {
            EmployeePojo emp=new EmployeePojo();
            emp.setEmpid(rs.getString(1));
            emp.setEpname(rs.getString(2));
            emp.setJob(rs.getString(3));
            emp.setSalary(rs.getInt(4));
            empList.add(emp);
            
        }
        return empList;
    }
    public static List<String> getAllEmpId() throws SQLException
    {
        Connection conn=DataBaseConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select empid from employees order by empid");
       ArrayList<String> allId=new ArrayList<>();
       while(rs.next())
       {
           allId.add(rs.getString(1));
       }
       return allId;
    }
    public static EmployeePojo getEmpById(String empid)throws SQLException
    {
        Connection conn=DataBaseConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("select * from employees where empid=?");
        ps.setString(1,empid);
        ResultSet rs=ps.executeQuery();
        rs.next();
        EmployeePojo e=new EmployeePojo();
          e.setEmpid(rs.getString(1));
          e.setEpname(rs.getString(2));
          e.setJob(rs.getString(3));
          e.setSalary(rs.getInt(4));
      
     return e;
    }
    public static boolean updateEmployee(EmployeePojo emp) throws SQLException
    {
        Connection conn=DataBaseConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("update employees set empname=?,job=?,salary=? where empid=?");
        ps.setString(1,emp.getEpname());
        ps.setString(2, emp.getJob());
        ps.setInt(3, emp.getSalary());
        ps.setString(4, emp.getEmpid());
        int x=ps.executeUpdate();
         if(x==0)
             return false;
         else
         {
             boolean result=UserDAO.isUserPresent(emp.getEmpid());
             if(result==false)
                 return true;
         
         ps=conn.prepareStatement("update users set username=?,usertype=? where empid=?");
          ps.setString(1,emp.getEpname());
        ps.setString(2, emp.getJob());
         ps.setString(3, emp.getEmpid());
         int y=ps.executeUpdate();
         return y==1;
       
    }
    }
     public static boolean deleteEmployee(String empid) throws SQLException
    {
        Connection conn=DataBaseConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("delete  from employees where empid=?");
        ps.setString(1,empid);
       
        int x=ps.executeUpdate();
         return x==1;
    
}
}
