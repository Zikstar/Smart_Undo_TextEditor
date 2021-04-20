package com.mapledev.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomePageGUI extends JFrame implements ActionListener {
    public HomePageGUI(){

    }

    public void run(){

    }

    private void setTheAttrOfAppWindow() {

    }

    private void setTheLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(SmartUndoEditorGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void displayActionIconsAndText(){

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
