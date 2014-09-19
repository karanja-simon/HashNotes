/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashnotes.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author RESEARCH2
 */
public class FilesOps {

    public static void writeBackup(String[] notes) {
        try {

            SystemTime st = new SystemTime();
            File file = new File("backups/"+st.getTabbedFullDateTime()+".hnbkp");

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for (String note : notes) {
                bw.write(note);
            }
            
            bw.close();

            System.out.println("Done");

        } catch (IOException e) {
            MyLogger.log(FilesOps.class, e.getMessage());
        }
    }

    public static ArrayList<String> listFilesForFolder(final File folder) {
        ArrayList<String> backups = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                backups.add(fileEntry.getName());
            }
        }
        return backups;
    }
}
