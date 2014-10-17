/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashnotes.ui;

import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.managers.hotkey.Hotkey;
import hashnotes.controllers.ControllerBackup;
import hashnotes.controllers.ControllerNewNote;
import hashnotes.controllers.ControllerNotesLoader;
import hashnotes.controllers.ControllerViewNotes;
import hashnotes.utils.DatabaseConnection;
import hashnotes.utils.MyLogger;
import java.awt.CardLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author RESEARCH2
 */
public class JFrameMainUI extends javax.swing.JFrame {

    private Point initialClick;
    private final CardLayout cl;
    private final WebMenuItem wmiAbout;
    private final WebMenuItem wmiViewNote;
    private final WebMenuItem wmiGreenPolicy;
    private final WebMenuItem wmiExport;
    private final WebMenuItem wmiBackup;
    private final WebMenuItem wmiNewNote;
    private final WebMenuItem wmiTags;
    private ControllerViewNotes viewNotes;
    private JPanelViewNotes notes;

    /**
     * Creates new form JFrameMainUI
     */
    public JFrameMainUI() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(JFrameMainUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
        cl = (CardLayout) (jPanelWrapper.getLayout());

        this.setLocationRelativeTo(null);
        homePanel();
        notes = new JPanelViewNotes();
        viewNotes = new ControllerViewNotes(notes, this);
        this.wmiTags = new WebMenuItem("Tags", Hotkey.T);
        this.wmiNewNote = new WebMenuItem("Add New Note", Hotkey.V);
        this.wmiBackup = new WebMenuItem("Backup the Notes", Hotkey.ALT_B);
        this.wmiExport = new WebMenuItem("Export to a File", Hotkey.ALT_A);
        this.wmiAbout = new WebMenuItem("About HashNotes", Hotkey.ESCAPE);
        this.wmiViewNote = new WebMenuItem("View Notes", Hotkey.ALT_X);
        this.wmiGreenPolicy = new WebMenuItem("Our Green Policy", Hotkey.ALT_Y);
        wmiAbout.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                jPanelWrapper.add("about", new JPanelAbout());
                cl.show(jPanelWrapper, "about");
            }
        });
        wmiExport.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                jPanelWrapper.add("view note", notes);
                cl.show(jPanelWrapper, "view note");
                viewNotes.exportNotes();
            }
        });
        wmiViewNote.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                showNotes();
            }
        });
        wmiGreenPolicy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                jPanelWrapper.add("green policy", new JPanelGreenPolicy());
                cl.show(jPanelWrapper, "green policy");
            }
        });
        wmiBackup.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                showBackup();
            }
        });
        wmiNewNote.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                newNote();
            }
        });
        wmiTags.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JDialogTags dt = new JDialogTags(me(), true, viewNotes.tags());
                int locX = jPanelMargin.getLocationOnScreen().x;
                int locY = jPanelMargin.getLocationOnScreen().y;
                System.out.println(locX);
                dt.setLocation(locX, locY);
                dt.setSize(dt.getWidth(), jPanelHome.getHeight() + jPanelBottom.getHeight() - 1);
                dt.setVisible(true);
            }
        });
    }

    public void showNotes() {
        //JPanelViewNotes notes = new JPanelViewNotes();
        //viewNotes = new ControllerViewNotes(notes);
        jPanelWrapper.add("view note", notes);
        cl.show(jPanelWrapper, "view note");
    }

    public void showBackup() {
        JPanelBackup jpb = new JPanelBackup();
        jPanelWrapper.add("backup", jpb);
        ControllerBackup cbk = new ControllerBackup(jpb, viewNotes, this);
        cl.show(jPanelWrapper, "backup");
    }

    private JFrame me() {
        return this;
    }

    private void closeDatabaseCon() {
        try {
            DatabaseConnection.getConnection().close();
            DatabaseConnection.getServer().shutdown();
        } catch (Exception ex) {
            MyLogger.log(getClass(), ex.getMessage());
        }
    }

    private void homePanel() {
        JPanelHome jph = new JPanelHome();
        ControllerNotesLoader cnl = new ControllerNotesLoader(jph, this);
        jPanelWrapper.add("home panel", jph);
        cl.show(jPanelWrapper, "home panel");
    }

    public JPanel getjPanelWrapper() {
        return jPanelWrapper;
    }

    public void setjPanelWrapper(JPanel jPanelWrapper) {
        this.jPanelWrapper = jPanelWrapper;
    }

    private void newNote() {
        JDialogNewNote note = new JDialogNewNote(this, true, this.getX(), this.getY(), this.getWidth());
        ControllerNewNote cNote = new ControllerNewNote(note, notes, viewNotes, this);
        note.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        webButtonUI1 = new com.alee.laf.button.WebButtonUI();
        webMenu1 = new com.alee.laf.menu.WebMenu();
        webMenuItem1 = new com.alee.laf.menu.WebMenuItem();
        webMenuItemUI1 = new com.alee.laf.menu.WebMenuItemUI();
        jPanel1 = new javax.swing.JPanel();
        jPanelWrapper = new javax.swing.JPanel();
        jPanelHome = new javax.swing.JPanel();
        jPanelBottom = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        webSplitButtonMenu = new com.alee.extended.button.WebSplitButton();
        webButton1 = new com.alee.laf.button.WebButton();
        webLabel1 = new com.alee.laf.label.WebLabel();
        webLabel2 = new com.alee.laf.label.WebLabel();
        jPanelMargin = new javax.swing.JPanel();

        webMenu1.setText("webMenu1");

        webMenuItem1.setText("webMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
        });
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });

        jPanelWrapper.setBackground(new java.awt.Color(255, 255, 255));
        jPanelWrapper.setLayout(new java.awt.CardLayout());

        jPanelHome.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanelHomeLayout = new javax.swing.GroupLayout(jPanelHome);
        jPanelHome.setLayout(jPanelHomeLayout);
        jPanelHomeLayout.setHorizontalGroup(
            jPanelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
        );
        jPanelHomeLayout.setVerticalGroup(
            jPanelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 357, Short.MAX_VALUE)
        );

        jPanelWrapper.add(jPanelHome, "card2");

        jPanelBottom.setBackground(new java.awt.Color(255, 102, 0));

        javax.swing.GroupLayout jPanelBottomLayout = new javax.swing.GroupLayout(jPanelBottom);
        jPanelBottom.setLayout(jPanelBottomLayout);
        jPanelBottomLayout.setHorizontalGroup(
            jPanelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelBottomLayout.setVerticalGroup(
            jPanelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 34, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(51, 51, 51));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hashnotes/resources/close (2).png"))); // NOI18N
        jButton3.setContentAreaFilled(false);
        jButton3.setFocusPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hashnotes/resources/minimize.png"))); // NOI18N
        jButton4.setContentAreaFilled(false);
        jButton4.setFocusPainted(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        webSplitButtonMenu.setText("Menu");
        webSplitButtonMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                webSplitButtonMenuActionPerformed(evt);
            }
        });

        webButton1.setText("+");
        webButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                webButton1ActionPerformed(evt);
            }
        });

        webLabel1.setForeground(new java.awt.Color(255, 102, 0));
        webLabel1.setText("NOTES");
        webLabel1.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N

        webLabel2.setText("#HASH");
        webLabel2.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(webLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(webButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(webSplitButtonMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(webLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(631, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(webLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(webSplitButtonMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(webButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(webLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE))
        );

        jPanelMargin.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanelMarginLayout = new javax.swing.GroupLayout(jPanelMargin);
        jPanelMargin.setLayout(jPanelMarginLayout);
        jPanelMarginLayout.setHorizontalGroup(
            jPanelMarginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelMarginLayout.setVerticalGroup(
            jPanelMarginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelWrapper, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelMargin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanelMargin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanelWrapper, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanelBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
        // get location of Window
        int thisX = getLocation().x;
        int thisY = getLocation().y;

        // Determine how much the mouse moved since the initial click
        int xMoved = (thisX + evt.getX()) - (thisX + initialClick.x);
        int yMoved = (thisY + evt.getY()) - (thisY + initialClick.y);

        // Move window to this position
        int X = thisX + xMoved;
        int Y = thisY + yMoved;
        setLocation(X, Y);
    }//GEN-LAST:event_jPanel1MouseDragged

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        initialClick = evt.getPoint();
        getComponentAt(initialClick);
    }//GEN-LAST:event_jPanel1MousePressed

    private void webSplitButtonMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_webSplitButtonMenuActionPerformed
        // Menu for split button
        final WebPopupMenu popupMenu = new WebPopupMenu();
        popupMenu.add(wmiViewNote);
        popupMenu.add(wmiNewNote);
        popupMenu.add(wmiTags);
        popupMenu.addSeparator();
        popupMenu.add(wmiExport);
        popupMenu.add(wmiBackup);
        popupMenu.addSeparator();
        popupMenu.add(wmiAbout);
        popupMenu.add(wmiGreenPolicy);
        webSplitButtonMenu.setPopupMenu(popupMenu);
    }//GEN-LAST:event_webSplitButtonMenuActionPerformed

    private void webButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_webButton1ActionPerformed
        // TODO add your handling code here:  
        newNote();

    }//GEN-LAST:event_webButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        closeDatabaseCon();
        System.exit(0);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrameMainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameMainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameMainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameMainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameMainUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelBottom;
    private javax.swing.JPanel jPanelHome;
    private javax.swing.JPanel jPanelMargin;
    private javax.swing.JPanel jPanelWrapper;
    private com.alee.laf.button.WebButton webButton1;
    private com.alee.laf.button.WebButtonUI webButtonUI1;
    private com.alee.laf.label.WebLabel webLabel1;
    private com.alee.laf.label.WebLabel webLabel2;
    private com.alee.laf.menu.WebMenu webMenu1;
    private com.alee.laf.menu.WebMenuItem webMenuItem1;
    private com.alee.laf.menu.WebMenuItemUI webMenuItemUI1;
    private com.alee.extended.button.WebSplitButton webSplitButtonMenu;
    // End of variables declaration//GEN-END:variables
}
