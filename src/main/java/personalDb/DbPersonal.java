/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package personalDb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hp
 */
public class DbPersonal {
    
     public static Connection getConnection() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    private String dbUrl;
    private String user;
    private String pwd;
    
    public DbPersonal(){
        dbUrl="jdbc:mysql://localhost:3306/user";
        user="root";
        pwd="selena007gomax";
    }
    private Connection getDbConnection(){
         Connection con=null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection(dbUrl,user,pwd);
        }catch(ClassNotFoundException ex){
            Logger.getLogger(DbPersonal.class.getName()).log(Level.SEVERE,null,ex);
        }catch(SQLException ex){
            Logger.getLogger(DbPersonal.class.getName()).log(Level.SEVERE,null,ex);
        }
        return con;
    }
    public void save(Personal personal) {
        Connection dbConnection=null;
        try {
            dbConnection = getDbConnection();
            String insertSQL = "INSERT INTO personal(name, age, nid, sex, contact_no, email, address, blood_type, symptoms, date_of_birth) " +
                               "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = dbConnection.prepareStatement(insertSQL);

            pstmt.setString(1, personal.getName());
            pstmt.setInt(2, personal.getAge());
            pstmt.setString(3, personal.getNid());
            pstmt.setString(4, personal.getSex());
            pstmt.setString(5, personal.getContactNo());
            pstmt.setString(6, personal.getEmail());
            pstmt.setString(7, personal.getAddress());
            pstmt.setString(8, personal.getBloodType());
            pstmt.setString(9, personal.getSymptoms());
            pstmt.setString(10, personal.getDateOfBirth());
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DbPersonal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dbConnection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DbPersonal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Update an existing personal record
    public void update(Personal personal) {
        Connection dbConnection = null;
        try {
            dbConnection = getDbConnection();
            String updateSQL = "UPDATE personal SET name = ?, age = ?, nid = ?, sex = ?, contact_no = ?, email = ?, address = ?, blood_type = ?, symptoms = ?, date_of_birth = ? " +
                               "WHERE id = ?";
            PreparedStatement pstmt = dbConnection.prepareStatement(updateSQL);

            pstmt.setString(1, personal.getName());
            pstmt.setInt(2, personal.getAge());
            pstmt.setString(3, personal.getNid());
            pstmt.setString(4, personal.getSex());
            pstmt.setString(5, personal.getContactNo());
            pstmt.setString(6, personal.getEmail());
            pstmt.setString(7, personal.getAddress());
            pstmt.setString(8, personal.getBloodType());
            pstmt.setString(9, personal.getSymptoms());
            pstmt.setString(10, personal.getDateOfBirth());
            pstmt.setInt(11, personal.getId());
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DbPersonal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dbConnection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DbPersonal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Delete a personal record
    public void delete(int id) {
        Connection dbConnection = null;
        try {
            dbConnection = getDbConnection();
            String deleteSQL = "DELETE FROM personal WHERE id = ?";
            PreparedStatement pstmt = dbConnection.prepareStatement(deleteSQL);

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DbPersonal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dbConnection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DbPersonal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Find a personal record by ID
    public Personal findById(int id) {
        Connection dbConnection = null;
        Personal personal = null;

        try {
            dbConnection = getDbConnection();
            String query = "SELECT * FROM personal WHERE id = ?";
            PreparedStatement pstmt = dbConnection.prepareStatement(query);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                personal = new Personal();
                personal.setId(rs.getInt("id"));
                personal.setName(rs.getString("name"));
                personal.setAge(rs.getInt("age"));
                personal.setNid(rs.getString("nid"));
                personal.setSex(rs.getString("sex"));
                personal.setContactNo(rs.getString("contact_no"));
                personal.setEmail(rs.getString("email"));
                personal.setAddress(rs.getString("address"));
                personal.setBloodType(rs.getString("blood_type"));
                personal.setSymptoms(rs.getString("symptoms"));
                personal.setDateOfBirth(rs.getString("date_of_birth"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbPersonal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                 dbConnection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DbPersonal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return personal;
    }

    public ArrayList<Personal> search(String search) {
    ArrayList<Personal> searchResults = new ArrayList<>();
    Connection dbConnection = null;

    try {
        dbConnection = getDbConnection(); 
        String searchQuery = "SELECT * FROM personal WHERE id LIKE ? OR name LIKE ? OR age LIKE ? OR nid LIKE ? OR sex LIKE ? "
                           + "OR contact_no LIKE ? OR email LIKE ? OR address LIKE ? OR blood_type LIKE ? "
                           + "OR symptoms LIKE ? OR date_of_birth LIKE ?";
        
        PreparedStatement pstmt = dbConnection.prepareStatement(searchQuery);
        String searchWithWildcards = "%" + search + "%";

        
        for (int i = 1; i <= 11; i++) {
            pstmt.setString(i, searchWithWildcards);
        }

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Personal personal = new Personal();
            personal.setId(rs.getInt("id"));
            personal.setName(rs.getString("name"));
            personal.setAge(rs.getInt("age"));
            personal.setNid(rs.getString("nid"));
            personal.setSex(rs.getString("sex"));
            personal.setContactNo(rs.getString("contact_no"));
            personal.setEmail(rs.getString("email"));
            personal.setAddress(rs.getString("address"));
            personal.setBloodType(rs.getString("blood_type"));
            personal.setSymptoms(rs.getString("symptoms"));
            personal.setDateOfBirth(rs.getString("date_of_birth"));

            searchResults.add(personal);
        }

        rs.close();
        pstmt.close();
    } catch (SQLException ex) {
        Logger.getLogger(DbPersonal.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        try {
            if (dbConnection != null) {
                dbConnection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbPersonal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    return searchResults;
}

    
    
    public ArrayList<Personal> getAllPersonalRecords() {
        ArrayList<Personal> allPersonalRecords = new ArrayList<>();
        Connection dbConnection = null;

        try {
            dbConnection = getDbConnection();
            String query = "SELECT * FROM personal";
            PreparedStatement pstmt = dbConnection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Personal personal = new Personal();
                personal.setId(rs.getInt("id"));
                personal.setName(rs.getString("name"));
                personal.setAge(rs.getInt("age"));
                personal.setNid(rs.getString("nid"));
                personal.setSex(rs.getString("sex"));
                personal.setContactNo(rs.getString("contact_no"));
                personal.setEmail(rs.getString("email"));
                personal.setAddress(rs.getString("address"));
                personal.setBloodType(rs.getString("blood_type"));
                personal.setSymptoms(rs.getString("symptoms"));
                personal.setDateOfBirth(rs.getString("date_of_birth"));
                allPersonalRecords.add(personal);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbPersonal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                 dbConnection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DbPersonal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return allPersonalRecords;
    }

    

    
    
    
}
