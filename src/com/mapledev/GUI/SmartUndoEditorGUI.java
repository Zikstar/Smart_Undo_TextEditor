package com.mapledev.GUI;

import com.mapledev.EditManager;
import com.mapledev.FileManager;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;


public class SmartUndoEditorGUI extends JFrame implements ActionListener{
    private static JTextPane area;
    private static JFrame frame;
    AbstractDocument doc;
    JTextArea changeLog;
    String newline = "\n";
    
    protected EditManager um = new EditManager(this);
    public EditManager.UndoAction undoAction;
    public EditManager.RedoAction redoAction;
    HashMap<Object, Action> actions;
    JScrollPane scrollPane, scrollPaneForLog;
    JSplitPane splitPane;

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
        } else if (actionCommand.equals("Quit")) {
            FileManager.Quit(area, frame);
        } else if (actionCommand.contentEquals("Copy")) {
        	EditManager.copy(area);
        	
        } else if (actionCommand.contentEquals("Cut")) {
        	EditManager.cut(area);
        	
        } else if (actionCommand.contentEquals("Paste")) {
        	EditManager.paste(area);
        	
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
        frame.add(area);
        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        addListeners();
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
        JMenu menu_insert = new JMenu("Insert");
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
        
        // Menu items for Insert
        
        JMenuItem menuitem_picture = new JMenuItem("Picture");
        JMenuItem menuitem_table = new JMenuItem("Table");
        JMenuItem menuitem_header = new JMenuItem("Header");
        JMenuItem menuitem_footer = new JMenuItem("Footer");
        

        menuitem_picture.addActionListener(this);
        menuitem_table.addActionListener(this);
        menuitem_header.addActionListener(this);
        menuitem_footer.addActionListener(this);

        menu_main.add(menu_insert);

        menu_insert.add(menuitem_picture);
        menu_insert.add(menuitem_table);
        menu_insert.add(menuitem_header);
        menu_insert.add(menuitem_footer);
        
        // Menu items for edit
        undoAction = um.new UndoAction();
        menu_edit.add(undoAction);
        
        redoAction = um.new RedoAction();
        menu_edit.add(redoAction);
  
        //JMenuItem menuitem_undo = new JMenuItem("Undo");
        JMenuItem menuitem_copy = new JMenuItem("Copy");
        JMenuItem menuitem_cut = new JMenuItem("Cut");
        JMenuItem menuitem_paste = new JMenuItem("Paste");
        
        //menuitem_undo.addActionListener(this);
        menuitem_copy.addActionListener(this);
        menuitem_cut.addActionListener(this);
        menuitem_paste.addActionListener(this);

        menu_main.add(menu_edit);
        //menu_edit.add(menuitem_undo);
        menu_edit.add(menuitem_copy);
        menu_edit.add(menuitem_cut);
        menu_edit.add(menuitem_paste);
     
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
    
    private Action getActionByName(String name) {
        return actions.get(name);
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