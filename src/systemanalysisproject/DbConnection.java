/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemanalysisproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;
import static systemanalysisproject.Application.users;

/**
 *
 * @author DCL
 */
public class DbConnection {
     String url="jdbc:mysql://localhost:3306/preleave?useSSL=False";
    String user="root";
    String pass="raihan";
     String aid;
        String name;
        String sdate;
        String edate;
        String reason;
        
    public void connection() throws ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
    }
    
    
    public void insert(){
        
//        Connection con= DriverManager.getConnection(url, user,pass);
//          Statement st=con.createStatement();
//          PreparedStatement pst=con.prepareStatement(query);
//          pst.setString(1, users);
//          pst.setString(2, name);
//          pst.setDate(3, sDate);
//          pst.setDate(4, eDate);
//          pst.setString(5,reason.getText());
//          pst.setString(6, "wait");
//          
//          int count =pst.executeUpdate();
    }
    public void fetchData(JTable table) throws ClassNotFoundException, SQLException{
        connection();
        Connection con=DriverManager.getConnection(url, user, pass);
        String query="select id,name from leaverequest where status='pending'";
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(query);
        table.setModel(DbUtils.resultSetToTableModel(rs));
    }
     public void fetchAll(String _query,String id) throws SQLException, ClassNotFoundException{
        Administrator admin=new Administrator();
        connection();
        Connection con=DriverManager.getConnection(url,user,pass);
        PreparedStatement pst=con.prepareStatement(_query);
        pst.setString(1, id);
        ResultSet rs=pst.executeQuery();
        while(rs.next()){
            aid=(rs.getString("id"));
            name=(rs.getString("name"));
            sdate=(rs.getDate("startdate").toString());
            edate=(rs.getDate("enddate").toString());
            reason=(rs.getString("reason"));
        }
        
    }
     
     public void updating(String status,String id,JTable t) throws ClassNotFoundException, SQLException{
          connection();
            String query="update leaverequest set status=? where id=? and requestid =(select max(requestid) from(select requestid from leaverequest where id=?) as t)";
            Connection con=DriverManager.getConnection(url,user,pass);
            PreparedStatement pst=con.prepareStatement(query);
            pst.setString(1,status);
            pst.setString(2, id);
            pst.setString(3,id);
            //pst.setInt(3,rid);
            int count=pst.executeUpdate();
            JOptionPane.showMessageDialog(null,status);
            fetchData(t);
       
     }
     
  
  
}
    

