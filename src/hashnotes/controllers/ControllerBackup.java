/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashnotes.controllers;

import hashnotes.ui.JPanelBackup;
import hashnotes.utils.FilesOps;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.DefaultListModel;

/**
 *
 * @author RESEARCH2
 */
public class ControllerBackup {

    private final JPanelBackup view;
    private final ControllerViewNotes cvn;

    public ControllerBackup(JPanelBackup view, ControllerViewNotes viewNotes) {
        this.view = view;
        this.cvn = viewNotes;
        HandleEvents he = new HandleEvents();
        view.getjButtonBackup().addActionListener(he);
        loadBackups();
    }

    private void loadBackups() {
        DefaultListModel resultList = new DefaultListModel();
        view.getjListBackups().setModel(resultList);
        for (String backupFile : FilesOps.listFilesForFolder(new File("backups"))) {
           resultList.addElement(backupFile);
        }
    }

    private class HandleEvents implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == view.getjButtonBackup()) {
                String[] notes = new String[cvn.getjTextPaneNote().length];
                for(int i=0; i<notes.length; i++){
                    notes[i] = cvn.getjTextPaneNote()[i].getText();
                }
                FilesOps.writeBackup(notes);

            }
        }
    }

}
