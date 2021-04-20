package com.mapledev;

import com.mapledev.GUI.SmartUndoEditorGUI;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextPane;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class EditManager extends UndoManager{
    SmartUndoEditorGUI smartundoeditorgui;
	
	private static String copiedText;

    public EditManager(SmartUndoEditorGUI smartundoeditorgui) {
        super();
        this.smartundoeditorgui = smartundoeditorgui;
    }

    public static void cut(JTextPane content){
    	copiedText = content.getSelectedText();
    	int beg = content.getSelectionStart();
		int end = content.getSelectionEnd();
		String oldText = content.getText();
		content.setText(oldText.substring(0, beg) + oldText.substring(end));
    }

    public static void copy(JTextPane content){
    	copiedText = content.getSelectedText();
    }

    public static boolean paste(JTextPane content){
    	if (copiedText != null) {
    		if (content.getSelectedText() != null) {
    	    	int beg = content.getSelectionStart();
    			int end = content.getSelectionEnd();
    			String oldText = content.getText();
    			content.setText(oldText.substring(0, beg) + copiedText + oldText.substring(end));
    			content.setCaretPosition(beg + copiedText.length());
    		} else {
    			int currentCursorPosition = content.getCaretPosition();
	    		String oldText = content.getText();
	    		content.setText(oldText.substring(0, currentCursorPosition) + copiedText + oldText.substring(currentCursorPosition));
	    		content.setCaretPosition(currentCursorPosition + copiedText.length());
    		}
	    	return true;
    	} else
    		return false;
    }
    
    public class UndoAction extends AbstractAction {
        public UndoAction() {
            super("Undo");
            setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {
            try {
                undo();
            } catch (CannotUndoException ex) {
                System.out.println("Unable to undo: " + ex);
                ex.printStackTrace();
            }
            updateUndoState();
            smartundoeditorgui.redoAction.updateRedoState();
        }

        public void updateUndoState(){
            if (canUndo()) {
                setEnabled(true);
                putValue(Action.NAME, getUndoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Undo");
            }
        }
    }
    
    public class RedoAction extends AbstractAction {
        public RedoAction() {
            super("Redo");
            setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {
            try {
                redo();
            } catch (CannotRedoException ex) {
                System.out.println("Unable to redo: " + ex);
                ex.printStackTrace();
            }
            updateRedoState();
            smartundoeditorgui.undoAction.updateUndoState();
        }

        public void updateRedoState() {
            if (canRedo()) {
                setEnabled(true);
                putValue(Action.NAME, getRedoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Redo");
            }
        }
    }
}
