package com.mapledev;

import javax.swing.JTextPane;

public class EditManager {
	
	private static String copiedText;

    //Todo: Actually implement all of this
    public enum UndoType{

    }

    public enum DeleteType{

    }

    public EditManager(){

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

    public static void handleUndo(UndoType undoType){

    }

    public static void handleDelete(DeleteType deleteType){

    }
}
