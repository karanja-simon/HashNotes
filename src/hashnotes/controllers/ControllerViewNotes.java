/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashnotes.controllers;

import hashnotes.dao.DatabaseHandle;
import hashnotes.models.ModelEntries;
import hashnotes.ui.JDialogPrompt;
import hashnotes.ui.JFrameMainUI;
import hashnotes.ui.JPanelLearn;
import hashnotes.ui.JPanelViewNotes;
import hashnotes.utils.MyLogger;
import java.awt.AlphaComposite;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTextPane;

/**
 *
 * @author RESEARCH2
 */
public class ControllerViewNotes {

    private final JPanelViewNotes view;
    private final JFrameMainUI mainUi;
    private final GridBagConstraints gbc = new GridBagConstraints();
    private JTextPane[] jTextPaneNote;
    private String[] jTextPaneNames;
    private JPanel[] jPanelNoteWrapper;
    private JSeparator[] jSeparators;
    private ModelEntries[] entry;
    JDialogPrompt jdp = new JDialogPrompt(null, true);

    public ControllerViewNotes(JPanelViewNotes view, JFrameMainUI mainUi) {
        this.view = view;
        this.mainUi = mainUi;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        loadNotes();
        // jdp.setLoc(view.getjTextPaneViewNotes());
    }

    private JPopupMenu options() {
        final JPopupMenu option = new JPopupMenu();
        JMenuItem delete = new JMenuItem("Delete Note");
        JMenuItem edit = new JMenuItem("Edit Note");
        final JMenuItem copy = new JMenuItem("Copy Note");
        final JMenuItem paste = new JMenuItem("Paste Note");
        final JMenuItem save = new JMenuItem("Save Note");
        edit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                //System.out.println(option.getInvoker().getName());
                JTextPane notes = (JTextPane) (option.getInvoker());
                notes.setEditable(true);
                String entryDate = notes.getText();
                //System.out.println(entryDate.substring(0, 20));
                Pattern p = Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d+");
                Matcher m = p.matcher(entryDate);
                if (m.find()) {
                    System.out.println("found: " + m.group());
                    DatabaseHandle dh = new DatabaseHandle();
                    dh.deleteNote(m.group());
                    clearComponents();
                    loadNotes();

                }

            }
        });
        save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                //view.getjPanelWrapper().revalidate();
            }
        });
        option.add(delete);
        option.add(edit);
        option.add(copy);
        option.add(paste);
        option.add(save);
        option.setBackground(Color.WHITE);
        edit.setBackground(Color.red);
        return option;
    }

    private void clearComponents() {
        GridBagLayout gbl = (GridBagLayout) view.getjPanelWrapper().getLayout();
        for (int i = 0; i < jPanelNoteWrapper.length; i++) {
            GridBagConstraints gc = gbl.getConstraints(jPanelNoteWrapper[i]);
            view.getjPanelWrapper().remove(jTextPaneNote[i]);
            view.getjPanelWrapper().remove(jPanelNoteWrapper[i]);
            view.getjPanelWrapper().remove(jSeparators[i]);
            view.getjPanelWrapper().revalidate();
            view.getjPanelWrapper().repaint();
        }
        if (jPanelNoteWrapper.length == 1) {
            showTutorial();
        }
        System.out.println("components remaining: " + jPanelNoteWrapper.length);
    }

    public void showTutorial() {
        CardLayout cl = (CardLayout) (mainUi.getjPanelWrapper().getLayout());
        mainUi.getjPanelWrapper().add("learn", new JPanelLearn());
        cl.show(mainUi.getjPanelWrapper(), "learn");
    }

    public class CustomMenuItem extends JMenuItem {

        @Override
        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
            super.paint(g2d);
            g2d.dispose();
        }
    }

    public ModelEntries[] getEntry() {
        return entry;
    }

    public final void loadNotes() {
        entry = new DatabaseHandle().getNotes();
        System.out.println("no of records: " + entry.length);
        String[] notes = new String[entry.length];
        for (int i = 0; i < entry.length; i++) {
            notes[i] = entry[i].getTimestamp().toString() + "\n\n" + entry[i].getNote() + "\n\n";
        }
        initCategoryPanels(entry, notes);
        tags();

//        if (entry.length < 1) {
//            showTutorial();
//        } else {
//            initCategoryPanels(entry, notes);
//            tags();
//        }
    }

    private void initCategoryPanels(ModelEntries[] entries, String[] note) {
        jTextPaneNote = new JTextPane[entries.length];
        jPanelNoteWrapper = new JPanel[entries.length];
        jSeparators = new JSeparator[entries.length];
        jTextPaneNames = new String[entries.length];

        for (int i = 0; i < entries.length; i++) {
            jTextPaneNote[i] = new JTextPane();
            jPanelNoteWrapper[i] = new JPanel();
            jPanelNoteWrapper[i].setBackground(new Color(250, 250, 250));
            view.initBeautifulNotes(jTextPaneNote[i]);
            jTextPaneNote[i].setPreferredSize(new Dimension(699, 80));
            jTextPaneNote[i].setBackground(new Color(250, 250, 250));
            jTextPaneNote[i].setText(note[i]);
            jPanelNoteWrapper[i].add(jTextPaneNote[i]);
            jTextPaneNote[i].setComponentPopupMenu(options());
            jTextPaneNote[i].setEditable(false);
            jTextPaneNote[i].setName("jtpNotes" + i);
            jTextPaneNames[i] = jTextPaneNote[i].getName();
            //

            jSeparators[i] = new JSeparator();
        }
        for (int y = 0; y < entries.length; y++) {
            addGB(view.getjPanelWrapper(), jSeparators[y], 0, y);
            addGB(view.getjPanelWrapper(), jPanelNoteWrapper[y], 0, y);
        }

    }

    private void addGB(Container cont, Component comp, int x, int y) {
        if (!(cont.getLayout() instanceof GridBagLayout)) {
            cont.setLayout(new GridBagLayout());
        }
        gbc.gridx = x;
        gbc.gridy = y;
        cont.add(comp, gbc);
        view.getjPanelWrapper().revalidate();
    }

    public ArrayList<String> tags() {
        ArrayList<String> tags = new ArrayList<>();
        Pattern p = Pattern.compile("#([a-zA-Z0-9]*)|@([a-zA-Z0-9]*)");
        Matcher m;

        for (int i = 0; i < jPanelNoteWrapper.length; i++) {
            //System.out.println(jTextPaneNote[1].getText());
            m = p.matcher(jTextPaneNote[i].getText());
            while (m.find()) {
                tags.add(m.group() + "\n");
                //System.out.println("tag: " + m.group());
            }
        }
        return tags;

    }

    public void exportNotes() {

        if (jPanelNoteWrapper.length > 0) {
            JFileChooser saveFile = new JFileChooser();
            int option = saveFile.showSaveDialog(null);
            saveFile.setDialogTitle("Save the file...");

            if (option == JFileChooser.APPROVE_OPTION) {

                File file = saveFile.getSelectedFile();
                if (!file.exists()) {

                    try {
                        try (BufferedWriter writer = new BufferedWriter(
                                new FileWriter(file.getAbsolutePath() + ".txt"))) {
                            for (int i = 0; i < jPanelNoteWrapper.length; i++) {
                                writer.write(jTextPaneNote[i].getText());
                                writer.newLine();
                            }
                            jdp.setPromptMessage("Notes successfully exported", "DONE!");
                            jdp.setVisible(true);
                            writer.close();
                        }

                    } catch (IOException ex) {
                        MyLogger.log(getClass(), ex.getMessage());
                        System.out.println(ex.getMessage());
                        JOptionPane.showMessageDialog(null, "Failed to save the file");
                    }

                } else if (file.exists()) {

                    int confirm = JOptionPane.showConfirmDialog(null,
                            "File exists do you want to save anyway?");
                    if (confirm == 0) {

                        try {
                            try (BufferedWriter writer = new BufferedWriter(
                                    new FileWriter(file.getAbsolutePath() + ".txt"))) {
                                for (int i = 0; i < jPanelNoteWrapper.length; i++) {
                                    writer.write(jTextPaneNote[i].getText());
                                    writer.newLine();
                                }
                                jdp.setPromptMessage("Notes successfully exported", "DONE!");
                                jdp.setVisible(true);
                                writer.close();
                            }

                        } catch (IOException ex) {

                            ex.printStackTrace();
                            MyLogger.log(getClass(), ex.getMessage());
                            System.out.println(ex.getMessage());
                            JOptionPane.showMessageDialog(null, "Failed to save the file");
                        }

                    } else if (confirm == 1) {

                        JOptionPane.showMessageDialog(null,
                                "The file was not saved.");

                    }

                }

            }

            if (option == JFileChooser.CANCEL_OPTION) {

                saveFile.setVisible(false);

            }

// End of method       
        } else {
            jdp.setPromptMessage("No entry.. Please add entries and try again", "ERROR EXPORTING!");
        }
    }

    public JTextPane[] getjTextPaneNote() {
        return jTextPaneNote;
    }

}
