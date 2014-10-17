/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashnotes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import hashnotes.models.ModelEntries;
import hashnotes.utils.DatabaseConnection;
import hashnotes.utils.MyLogger;

public class DatabaseHandle {

    private final Connection con;
    private ModelEntries entries;

    public DatabaseHandle() {
        //DatabaseConnection dc = new DatabaseConnection();
        this.con = DatabaseConnection.getConnection();

    }

    public ModelEntries[] getNotes() {
        ArrayList<ModelEntries> entry = new ArrayList<>();
        try {
            PreparedStatement read;
            read = con.prepareStatement("SELECT * FROM HASHNOTES.TBL_NOTES ORDER BY entry_date ASC");
            ResultSet rs = read.executeQuery();
            while (rs.next()) {
                entries = new ModelEntries();
                entries.setTimestamp(rs.getTimestamp("entry_date"));
                entries.setNote(rs.getString("entry_note"));
                entry.add(entries);

            }
        } catch (SQLException e) {
            System.out.println("Error executing query" + e.getMessage());
            MyLogger.log(getClass(), e.getMessage());
        }
        ModelEntries entriesArray[] = new ModelEntries[entry.size()];
        entry.toArray(entriesArray);
        return entriesArray;
    }

    public boolean insertNewNote(String note) {
        try {
            PreparedStatement insert;
            insert = con.prepareStatement("INSERT INTO HASHNOTES.TBL_NOTES VALUES(CURRENT_TIMESTAMP, ?)");
            insert.setString(1, note);

            if (insert.executeUpdate() != 0) {
                System.out.print("insert ok");
            }
        } catch (SQLException e) {
            System.out.println("Error executing query" + e.getMessage());
            MyLogger.log(getClass(), e.getMessage());
            return false;
        }
        return true;
    }
    public boolean deleteNote(String match){
        try {
            PreparedStatement delete;
            String sql = "DELETE FROM HASHNOTES.TBL_NOTES WHERE entry_date ='"+match+"'";
            delete = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //delete.setString(0, match);
            //System.out.println("entry timestamp: "+match);
            //stmt.execute(sql)
            
            if(delete.executeUpdate() == 1){
                System.out.println("deleted"); 
            }else{
               System.out.println("not deleted");  
            }
        } catch (SQLException ex) {
            MyLogger.log(getClass(), ex.getMessage());
            System.out.println(ex);
        }
        return true;
        
    }
}