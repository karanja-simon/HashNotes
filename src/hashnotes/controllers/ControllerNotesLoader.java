/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashnotes.controllers;

import hashnotes.ui.JFrameMainUI;
import hashnotes.ui.JPanelHome;
import hashnotes.ui.JPanelViewNotes;
import java.awt.CardLayout;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 *
 * @author RESEARCH2
 */
public class ControllerNotesLoader {

    private final JPanelHome view;
    private final JFrameMainUI mainUi;

    public ControllerNotesLoader(JPanelHome view, JFrameMainUI mainUi) {
        this.view = view;
        this.mainUi = mainUi;
        new NotesLoader().execute();
    }

    private class NotesLoader extends SwingWorker<Boolean, Integer> {

        int counter = 0;
        JPanelViewNotes notes;
        ControllerViewNotes viewNotes;

        @Override
        @SuppressWarnings("SleepWhileInLoop")
        protected Boolean doInBackground() throws Exception {
            load();
            return true;
        }

        @Override
        protected void done() {
            
            CardLayout cl = (CardLayout) (mainUi.getjPanelWrapper().getLayout());
            mainUi.getjPanelWrapper().add("view note", notes);
            cl.show(mainUi.getjPanelWrapper(), "view note");
        }

        @Override
        protected void process(List<Integer> chunks) {
            for (int i : chunks) {
                view.getjLabelLoader().setText("Retrieving saved notes..please wait ");
            }
        }

        private void load() {
            try {
                Thread.sleep(2000);
                notes = new JPanelViewNotes();
                viewNotes = new ControllerViewNotes(notes);
            } catch (InterruptedException ex) {
                Logger.getLogger(ControllerNotesLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
