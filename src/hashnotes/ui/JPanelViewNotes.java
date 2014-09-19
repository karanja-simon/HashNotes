/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashnotes.ui;

import hashnotes.utils.CustomScrollbarUI;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author RESEARCH2
 */
public class JPanelViewNotes extends javax.swing.JPanel {

    private StyleContext cont = null;
    private AttributeSet attr = null;
    private AttributeSet attrBlack = null;

    public JPanelViewNotes() {
        this.cont = StyleContext.getDefaultStyleContext();
        this.attr = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.RED);
        this.attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
        initComponents();
        jScrollPane2.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        jScrollPane2.getVerticalScrollBar().setUI(new CustomScrollbarUI(Color.WHITE));
        //initBeautifulNotes();
    }



    public JPanel getjPanelWrapper() {
        return jPanelWrapper;
    }

    public void setjPanelWrapper(JPanel jPanelWrapper) {
        this.jPanelWrapper = jPanelWrapper;
    }

//    public JTextPane getjTextPaneViewNotes() {
//        return jTextPaneViewNotes;
//    }
//
//    public void setjTextPaneViewNotes(JTextPane jTextPaneViewNotes) {
//        this.jTextPaneViewNotes = jTextPaneViewNotes;
//    }

    public void initBeautifulNotes(JTextPane jtp) {
        DefaultStyledDocument doc = new DefaultStyledDocument() {
            @Override
            public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
                super.insertString(offset, str, a);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offset);
                if (before < 0) {
                    before = 0;
                }
                int after = findFirstNonWordChar(text, offset + str.length());
                int wordL = before;
                int wordR = before;

                while (wordR <= after) {
                    if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) {
                        if (text.substring(wordL, wordR).matches("#([a-zA-Z0-9])*|@([a-zA-Z0-9])*")) {
                            int randR = (int) (Math.random() * 255);
                            int randG = (int) (Math.random() * 255);
                            int randB = (int) (Math.random() * 255);
                            attr = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(randR, randG, randB));
                            setCharacterAttributes(wordL, wordR - wordL, attr, false);
                        } else {
                            setCharacterAttributes(wordL, wordR - wordL, attrBlack, false);
                        }
                        wordL = wordR;
                    }
                    wordR++;
                }
            }

            @Override
            public void remove(int offs, int len) throws BadLocationException {
                super.remove(offs, len);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offs);
                if (before < 0) {
                    before = 0;
                }
                int after = findFirstNonWordChar(text, offs);

                if (text.substring(before, after).matches("#([a-zA-Z0-9])*|@([a-zA-Z0-9])*")) {
                    setCharacterAttributes(before, after - before, attr, false);
                } else {
                    setCharacterAttributes(before, after - before, attrBlack, false);
                }
            }
        };
        SimpleAttributeSet bSet = new SimpleAttributeSet();  
        StyleConstants.setAlignment(bSet, StyleConstants.ALIGN_LEFT);   
        StyleConstants.setFontFamily(bSet, "Segoe UI");  
        StyleConstants.setFontSize(bSet, 12); 
        doc.setParagraphAttributes(0, 104, bSet, false); 
        jtp.setDocument(doc);
    }

    private int findLastNonWordChar(String text, int index) {
        while (--index >= 0) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
        }
        return index;
    }

    private int findFirstNonWordChar(String text, int index) {
        while (index < text.length()) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
            index++;
        }
        return index;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jPanelWrapper = new javax.swing.JPanel();

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanelWrapper.setLayout(new java.awt.GridBagLayout());
        jScrollPane2.setViewportView(jPanelWrapper);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanelWrapper;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
