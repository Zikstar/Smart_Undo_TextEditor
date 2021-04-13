package com.mapledev;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileSystemView;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManager {
    private static int returnValue = 0;
    static Boolean opened = false;
    public static Boolean openedTemp = false;
    public static Boolean changed = false;
    private static Boolean saveSuccess = false;
    static JFileChooser jfctemp = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    public FileManager(){
    }


    public static String openFile(){
        String ingest = "";
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        jfc.setDialogTitle("Choose destination.");
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File f = new File(jfc.getSelectedFile().getAbsolutePath());
            jfctemp.setSelectedFile(jfc.getSelectedFile());
            opened = true;
            openedTemp = true;
            try {
                FileReader read = new FileReader(f);
                Scanner scan = new Scanner(read);
                while (scan.hasNextLine()) {
                    String line = scan.nextLine();
                    if (scan.hasNextLine()) {
                    	line = line + "\n";
                    }
                    ingest = ingest + line;
                }
                
                return ingest;
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return "";
    }

    public static void saveFile(JTextPane area){
    	if (!opened) 
    		saveFileAs(area);
        try {
            File f = new File(jfctemp.getSelectedFile().getAbsolutePath());
            FileWriter out = new FileWriter(f);
            area.write(out);
            out.close();
            changed = false;
            saveSuccess = true;
        } catch (FileNotFoundException ex) {
        	saveSuccess = false;
            Component f = null;
            JOptionPane.showMessageDialog(f,"File not found.");
        } catch (IOException ex) {
        	saveSuccess = false;
            Component f = null;
            JOptionPane.showMessageDialog(f,"Error.");
        }
        
    }

    public static void saveFileAs(JTextPane area){
    	@SuppressWarnings("serial")
		final JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory())
        {
            public void approveSelection()
            {
                if (getSelectedFile().exists())
                {
                    int n = JOptionPane.showConfirmDialog(
                        this,
                        "Do You Want to Overwrite File?",
                        "Confirm Overwrite",
                        JOptionPane.YES_NO_OPTION);

                    if (n == JOptionPane.YES_OPTION)
                        super.approveSelection();

                }
                else
                    super.approveSelection();
            }
        };
        jfc.setDialogTitle("Choose destination");
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jfc.setMultiSelectionEnabled(false);

        jfc.setSelectedFile( new File("") );
        int returnVal = jfc.showSaveDialog(null);


        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
        	try(FileWriter fw = new FileWriter(jfc.getSelectedFile())) {
        	    area.write(fw);
        	    changed = false;
        	}catch (FileNotFoundException ex) {
                Component f = null;
                JOptionPane.showMessageDialog(f,"File not found.");
            } catch (IOException ex) {
                Component f = null;
                JOptionPane.showMessageDialog(f,"Error.");
            }
        	saveSuccess = true;
        }
        else {
        	saveSuccess = false;
        }
    }

    public static void renameFile(){
        
    }
    

    public static void Quit(JTextPane area, JFrame frame){
    	if (changed) {
    		int n = JOptionPane.showConfirmDialog(
                    frame,
                    "Unsaved changes, do you wish to save?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION);
    			
                if (n == JOptionPane.YES_OPTION) {
                	if (opened) {
                		saveFile(area);
                	} else {
                		saveFileAs(area);
                	}
                	if (!saveSuccess) {
                		return;
                	}
                } else if (n == JOptionPane.CLOSED_OPTION) {
                	return;
                }
    	} 
    	System.exit(0);
    }
    
}
