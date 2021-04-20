package com.mapledev.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListDialog {
    private JList list;
    private JLabel label;
    private JOptionPane optionPane;
    private JButton okButton, cancelButton;
    private ActionListener okEvent, cancelEvent;
    private JDialog dialog;

    public ListDialog(String message, JList listToDisplay){
        list = listToDisplay;
        label = new JLabel(message);
        createAndDisplayOptionPane();
    }

    public ListDialog(String title, String message, JList listToDisplay){
        this(message, listToDisplay);
        dialog.setTitle(title);
    }

    private void createAndDisplayOptionPane(){
        setupButtons();
        JPanel pane = layoutComponents();
        optionPane = new JOptionPane(pane);
        optionPane.setOptions(new Object[]{okButton, cancelButton});
        dialog = optionPane.createDialog("Select option");
    }

    private void setupButtons(){
        okButton = new JButton("Ok");
        okButton.addActionListener(e -> handleOkButtonClick(e));

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> handleCancelButtonClick(e));
    }

    private JPanel layoutComponents(){
        centerListElements();
        JPanel panel = new JPanel(new BorderLayout(5,5));
        panel.add(label, BorderLayout.NORTH);
        panel.add(list, BorderLayout.CENTER);
        return panel;
    }

    private void centerListElements(){
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void setOnOk(ActionListener event){ okEvent = event; }

    public void setOnClose(ActionListener event){
        cancelEvent  = event;
    }

    private void handleOkButtonClick(ActionEvent e){
        if(okEvent != null){ okEvent.actionPerformed(e); }
        hide();
    }

    private void handleCancelButtonClick(ActionEvent e){
        if(cancelEvent != null){ cancelEvent.actionPerformed(e);}
        hide();
    }

    public void show(){ dialog.setVisible(true); }

    private void hide(){ dialog.setVisible(false); }

    public Object getSelectedItem(){ return list.getSelectedValue(); }

    public int[] getSelectedIndices(){return list.getSelectedIndices();}

    public Object[] getSelectedValues(){return list.getSelectedValues();}
}
