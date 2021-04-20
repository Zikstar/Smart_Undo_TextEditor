package com.mapledev.GUI;

import com.mapledev.EditManager;
import com.mapledev.FileManager;
import com.mapledev.Utils.ListDialog;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;


public class SmartUndoEditorGUI extends JFrame implements ActionListener{
    private static JTextPane area;
    private static JFrame frame;
    private JScrollPane scrollPane;
    protected EditManager um = new EditManager(this);
    public EditManager.UndoAction undoAction;
    public EditManager.RedoAction redoAction;
    HashMap<Object, Action> actions;
    private JList<String> jList;
    private JMenuItem menuitem_redolist;


    public SmartUndoEditorGUI(){
        run();
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String actionCommand = actionEvent.getActionCommand();

        if(actionCommand.equals("Open")){
            String ingestText = FileManager.openFile();
            area.setText(ingestText);
            area.setCaretPosition(0);
        }else if (actionCommand.equals("Save")) {
            FileManager.saveFile(area);
        }else if (actionCommand.equals("Save as")) {
            FileManager.saveFileAs(area);
        } else if (actionCommand.equals("New")) {
            area.setText("");
            EditManager.setOldList(null);
        } else if (actionCommand.equals("Quit")) {
            FileManager.Quit(area, frame);
        } else if (actionCommand.contentEquals("Copy")) {
        	EditManager.copy(area);
        	
        } else if (actionCommand.contentEquals("Cut")) {
        	EditManager.cut(area);
        	
        } else if (actionCommand.contentEquals("Paste")) {
        	EditManager.paste(area);
        } else if (actionCommand.contentEquals("Undo From List")){
            showUndoListDialog("Choose the words you want to undo",
                    EditManager.undoFromList(area));
        }else if (actionCommand.contentEquals("Redo From List(1 Step)")){
            rollBackToOldWordList();
        }else if(actionCommand.contentEquals("Delete From List")){
            showUndoListDialog("Choose the words you want to delete",
                    EditManager.deleteFromList(area));
        }
       
    }



    public void run() {
        frame = new JFrame("Smart Undo TextEditor");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                FileManager.Quit(area, frame);
              }
        });
         
        // Set the look-and-feel (LNF) of the application
        // Try to default to whatever the host system prefers
        setTheLookAndFeel();

        // Build the menu
        JMenuBar menu_main = buildTheMenu();

        frame.setJMenuBar(menu_main);
        
     // Set attributes of the app window
        setTheAttrOfAppWindow();
    }

    private void setTheAttrOfAppWindow() {
        area = new JTextPane();

        area.getDocument().addDocumentListener(new MyDocumentListener());
        frame.setSize(640, 600);
        frame.setLocationRelativeTo(null);
        scrollPane = new JScrollPane(area);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setMinimumSize(new Dimension(500,400));
        frame.add(scrollPane);
        frame.setVisible(true);
        
        addListeners();
    }

    private void showUndoListDialog(String message, String[] words){
        if(words == null)
            return;

        menuitem_redolist.setEnabled(true);
        jList = new JList<>(words);
        ListDialog dialog = new ListDialog(message, jList);
        dialog.setOnOk(e ->{
            int [] selectedIndices = dialog.getSelectedIndices();
            Set<Integer> selectedIndicesSet = new HashSet<>();
            for (int selectedIndex : selectedIndices) {
                selectedIndicesSet.add(selectedIndex);
            }

            ArrayList<String> newWordList = new ArrayList<>();
            for (int i = 0; i < words.length; i++) {
                if(!selectedIndicesSet.contains(i)){
                    newWordList.add(words[i]);
                }
            }

            refreshTextAreaWithNewWordList(newWordList);
        });

        dialog.show();

    }

    private void refreshTextAreaWithNewWordList(ArrayList<String> newWordList) {
        System.out.println(newWordList);
        area.setText(String.join(" ", newWordList));
    }

    private void rollBackToOldWordList(){
        //You can only go one step back
        //Do only if available
        if(EditManager.getOldWordList() == null){
            menuitem_redolist.setEnabled(false);
        }else {
            menuitem_redolist.setEnabled(true);
            area.setText(String.join(" ", EditManager.getOldWordList()));
        }

    }

    private void setTheLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(SmartUndoEditorGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private JMenuBar buildTheMenu() {
        JMenuBar menu_main = new JMenuBar();

        JMenu menu_file = new JMenu("File");
        //JMenu menu_insert = new JMenu("Insert");
        JMenu menu_edit = new JMenu("Edit");

        //Menu Items for File
        JMenuItem menuitem_new = new JMenuItem("New");
        JMenuItem menuitem_open = new JMenuItem("Open");
        JMenuItem menuitem_save = new JMenuItem("Save");
        JMenuItem menuitem_quit = new JMenuItem("Quit");
        JMenuItem menuitem_save_as = new JMenuItem("Save as");
        JMenuItem menuItem_rename = new JMenuItem("Rename");

        menuitem_new.addActionListener(this);
        menuitem_open.addActionListener(this);
        menuitem_save.addActionListener(this);
        menuitem_save_as.addActionListener(this);
        menuitem_quit.addActionListener(this);

        menu_main.add(menu_file);

        menu_file.add(menuitem_new);
        menu_file.add(menuitem_open);
        menu_file.add(menuitem_save);
        menu_file.add(menuitem_save_as);
        menu_file.add(menuitem_quit);
        

        // Menu items for edit
        undoAction = um.new UndoAction();
        menu_edit.add(undoAction);
        
        redoAction = um.new RedoAction();
        menu_edit.add(redoAction);
  
        //JMenuItem menuitem_undo = new JMenuItem("Undo");
        JMenuItem menuitem_copy = new JMenuItem("Copy");
        JMenuItem menuitem_cut = new JMenuItem("Cut");
        JMenuItem menuitem_paste = new JMenuItem("Paste");
        JMenuItem menuitem_undolist = new JMenuItem("Undo From List");
        menuitem_redolist = new JMenuItem("Redo From List(1 Step)");
        JMenuItem menuItem_delete_list = new JMenuItem("Delete From List");
        
        //menuitem_undo.addActionListener(this);
        menuitem_copy.addActionListener(this);
        menuitem_cut.addActionListener(this);
        menuitem_paste.addActionListener(this);
        menuitem_undolist.addActionListener(this);
        menuitem_redolist.addActionListener(this);
        menuItem_delete_list.addActionListener(this);

        menu_main.add(menu_edit);
        //menu_edit.add(menuitem_undo);
        menu_edit.add(menuitem_copy);
        menu_edit.add(menuitem_cut);
        menu_edit.add(menuitem_paste);
        menu_edit.add(menuitem_undolist);
        menu_edit.add(menuitem_redolist);
        menu_edit.add(menuItem_delete_list);
     
        return menu_main;
        
        
    }
    
    class MyDocumentListener implements DocumentListener {
        String newline = "\n";
     
        public void insertUpdate(DocumentEvent e) {
        	if (FileManager.openedTemp) {
        		FileManager.openedTemp = false;
        	} else {
        		FileManager.changed = true;
        	}
        }
        public void removeUpdate(DocumentEvent e) {
        	FileManager.changed = true;
        }
        public void changedUpdate(DocumentEvent e) {
        	FileManager.changed = true;
        }
    }

    
    private HashMap<Object, Action> createActionTable(JTextComponent textComponent) {
        HashMap<Object, Action> actions = new HashMap<Object, Action>();
        Action[] actionsArray = textComponent.getActions();
        for (Action a : actionsArray) {
            actions.put(a.getValue(Action.NAME), a);
        }
        return actions;
    }
    
    public void addListeners(){
        area.getDocument().addUndoableEditListener(new MyUndoableEditListener());
        area.getDocument().addDocumentListener(new MyDocumentListener());
    }

    public class MyUndoableEditListener implements UndoableEditListener {
        public void undoableEditHappened(UndoableEditEvent e) {
            um.addEdit(e.getEdit());
            undoAction.updateUndoState();
            redoAction.updateRedoState();
        }
    }
   
   
}