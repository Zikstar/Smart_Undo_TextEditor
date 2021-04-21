package com.mapledev.GUI;

import javax.swing.*;

import com.mapledev.FileManager;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HomePageGUI extends JFrame implements ActionListener {
    private static JFrame frame;
    private JLabel smartUndoLabel;
    private JLabel newLabelIcon;
    private JLabel newLabelText;
    private JLabel openLabel;
    private JLabel recentLabelText;
    private JLabel openLabelText;
    private JLabel recentLabel;

    public HomePageGUI() {
        run();

    }

    public void run() {
        setTheAttrOfAppWindow();
        setTheLookAndFeel();
        addMouseClickListeners();
    }


    private void setTheAttrOfAppWindow() {
        frame = this;
        this.setSize(640, 500);
        displayActionIconsAndText();
        this.setVisible(true);
    }

    private void setTheLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(SmartUndoEditorGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void displayActionIconsAndText() {
        Icon home = new ImageIcon(getClass().getResource("images/home_icon.png"));
        ImageIcon new_file = new ImageIcon(getClass().getResource("images/new_file_icon.png"));
        getContentPane().setLayout(null);

        smartUndoLabel = new JLabel("Welcome To The Smart Undo Editor", home, SwingConstants.CENTER);
        smartUndoLabel.setBounds(0, 0, 600, 200);
        smartUndoLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        getContentPane().add(smartUndoLabel);

        newLabelIcon = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(new_file.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        newLabelIcon.setLocation(50, 300);
        //newLabel.setIcon(new_file);
        newLabelIcon.setSize(100, 100);
        newLabelIcon.setHorizontalTextPosition(SwingConstants.CENTER);
        newLabelIcon.setVerticalTextPosition(SwingConstants.BOTTOM);
        getContentPane().add(newLabelIcon);

        newLabelText = new JLabel("New");
        newLabelText.setHorizontalAlignment(SwingConstants.CENTER);
        newLabelText.setVerticalAlignment(SwingConstants.TOP);
        newLabelText.setBounds(50, 420, 100, 50);
        getContentPane().add(newLabelText);


        ImageIcon open = new ImageIcon(getClass().getResource("images/open_file_icon.png"));

        openLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(open.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        openLabel.setLocation(275, 300);
        //newLabel.setIcon(new_file);
        openLabel.setSize(100, 100);
        openLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        openLabel.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(openLabel);


        openLabelText = new JLabel("Open");
        openLabelText.setHorizontalAlignment(SwingConstants.CENTER);
        openLabelText.setVerticalAlignment(SwingConstants.TOP);
        openLabelText.setBounds(275, 420, 100, 50);
        getContentPane().add(openLabelText);


        ImageIcon recent = new ImageIcon(getClass().getResource("images/recent_icon.png"));

        recentLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(recent.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        recentLabel.setLocation(500, 300);
        recentLabel.setSize(100, 100);
        recentLabel.setHorizontalAlignment(SwingConstants.CENTER);
        recentLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        getContentPane().add(recentLabel);


        recentLabelText = new JLabel("Recent");
        recentLabelText.setHorizontalAlignment(SwingConstants.CENTER);
        recentLabelText.setVerticalAlignment(SwingConstants.TOP);
        recentLabelText.setBounds(500, 420, 100, 50);
        getContentPane().add(recentLabelText);
    }

    private void addMouseClickListeners(){
        newLabelIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.setVisible(false);
                SmartUndoEditorGUI gui = new SmartUndoEditorGUI();

            }
        });

        newLabelText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.setVisible(false);
                SmartUndoEditorGUI gui = new SmartUndoEditorGUI();
            }
        });


        openLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openNewAndRecentFile();
            }
        });

        openLabelText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openNewAndRecentFile();
            }
        });


        recentLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openNewAndRecentFile();
            }
        });

        recentLabelText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openNewAndRecentFile();
            }
        });
    }

    private void openNewAndRecentFile(){
        //They do the same thing for now
        frame.setVisible(false);
        ActionEvent openEvent = new ActionEvent(this, 1, "Open");
        SmartUndoEditorGUI gui = new SmartUndoEditorGUI(openEvent);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
