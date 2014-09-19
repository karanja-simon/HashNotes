/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashnotes.controllers;

import hashnotes.dao.DatabaseHandle;
import hashnotes.ui.JDialogNewNote;
import hashnotes.ui.JDialogPrompt;
import hashnotes.ui.JPanelViewNotes;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class ControllerNewNote {

    private final JDialogNewNote view;
    private StyleContext cont = null;
    private AttributeSet attr = null;
    private AttributeSet attrBlack = null;
    private final ControllerViewNotes viewNotes;
    private final JPanelViewNotes notes;
    public ControllerNewNote(JDialogNewNote view, JPanelViewNotes notes, ControllerViewNotes viewNotes ) {
        this.view = view;
        this.notes = notes;
        this.viewNotes = viewNotes;
        this.cont = StyleContext.getDefaultStyleContext();
        this.attr = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.RED);
        this.attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
        initBeautifulNotes();
        HandleEvents he = new HandleEvents();
       // ItemListener il = (ItemListener) new HandleEvents();
        view.getWebSwitchMoreTags().addActionListener(he);
        view.getAndTag().addActionListener(he);
        view.getAddTag().addActionListener(he);
        view.getExcTag().addActionListener(he);
        view.getWebButtonAddNote().addActionListener(he);
    }

    private void initBeautifulNotes() {
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
        view.getjTextPaneNotes().setDocument(doc);
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

    private class HandleEvents implements ActionListener, ItemListener {
        boolean add = true, and = true, exc = true;
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == view.getWebSwitchMoreTags()) {
                if (view.getWebSwitchMoreTags().isSelected()) {
                    view.getWebSplitButtonTags().setEnabled(true);
                }else{
                  view.getWebSplitButtonTags().setEnabled(false);  
                }
            }
            if (e.getSource() == view.getAndTag()) {
                if(and){
                view.getWebHotkeyLabelTags().setText(view.getWebHotkeyLabelTags().getText()+" / & ");
                }
                and = false;
            }
            if (e.getSource() == view.getAddTag()) {
                if(add){
                view.getWebHotkeyLabelTags().setText(view.getWebHotkeyLabelTags().getText()+" / + ");
                }
                add= false;
            }
            if (e.getSource() == view.getExcTag()) {
                if(exc){
                view.getWebHotkeyLabelTags().setText(view.getWebHotkeyLabelTags().getText()+" / ! ");
                }
                exc = false;
            }
            if(e.getSource() == view.getWebButtonAddNote()){
                DatabaseHandle dh = new DatabaseHandle();
                JDialogPrompt jdp = new JDialogPrompt(null, true);
                jdp.setLoc(view.getjTextPaneNotes());
                if(!view.getjTextPaneNotes().getText().isEmpty()){
                if(dh.insertNewNote(view.getjTextPaneNotes().getText())){
                    jdp.setPromptMessage("Note added successfully!", "DONE!");
                    viewNotes.loadNotes();
                }else{
                   jdp.setPromptMessage("Error adding note!", "TRY AGAIN!"); 
                }
                }else{
                   jdp.setPromptMessage("Cannot add empty note!", "TRY AGAIN!");  
                }
                jdp.setVisible(true);
            }
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                view.getWebSplitButtonTags().setEnabled(true);
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                view.getWebSplitButtonTags().setEnabled(false);
            }
        }

    }
}
