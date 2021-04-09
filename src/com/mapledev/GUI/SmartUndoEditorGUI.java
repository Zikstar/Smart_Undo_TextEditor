package com.mapledev.GUI;

import com.mapledev.FileManager;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SmartUndoEditorGUI extends JFrame implements ActionListener{
    private static JTextArea area;
    private static JFrame frame;

    public SmartUndoEditorGUI(){
        run();
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String actionCommand = actionEvent.getActionCommand();

        if(actionCommand.equals("Open")){
            String ingestText = FileManager.openFile();
            area.setText(ingestText);
        }else if (actionCommand.equals("Save")) {
            //Won't work unless you choose an actual text file, so
            //have to create SaveAs method
            FileManager.saveFile(area);
        } else if (actionCommand.equals("New")) {
            area.setText("");
        } else if (actionCommand.equals("Quit")) {
            FileManager.Quit();
        }

    }



    public void run() {
        frame = new JFrame("Smart Undo TextEditor");

        // Set the look-and-feel (LNF) of the application
        // Try to default to whatever the host system prefers
        setTheLookAndFeel();

        // Set attributes of the app window
        setTheAttrOfAppWindow();

        // Build the menu
        JMenuBar menu_main = buildTheMenu();

        frame.setJMenuBar(menu_main);
    }

    private void setTheAttrOfAppWindow() {
        area = new JTextArea();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(area);
        frame.setSize(640, 480);
        frame.setVisible(true);
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
        menuitem_quit.addActionListener(this);

        menu_main.add(menu_file);

        menu_file.add(menuitem_new);
        menu_file.add(menuitem_open);
        menu_file.add(menuitem_save);
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
  
        JMenuItem menuitem_undo = new JMenuItem("Undo");
        
        menuitem_undo.addActionListener(this);

        menu_main.add(menu_edit);

        menu_edit.add(menuitem_undo);
        return menu_main;
        
        
    }
}
