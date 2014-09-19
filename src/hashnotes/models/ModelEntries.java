/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hashnotes.models;

import hashnotes.dao.DatabaseHandle;
import java.sql.Timestamp;

/**
 *
 * @author RESEARCH2
 */
public class ModelEntries {
    private Timestamp   timestamp;
    private String note;
    
    public ModelEntries() {
        DatabaseHandle databaseHandle = new DatabaseHandle();
    }
    
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    

    
    
    
}
