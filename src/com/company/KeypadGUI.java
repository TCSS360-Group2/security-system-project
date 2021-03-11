package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class KeypadGUI extends JFrame{
    //The components of the GUI
    private JButton PANICButton;
    private JProgressBar progressBar1;
    private JPasswordField passwordField1;
    private JButton submitButton;

    //Keypad buttons
    private JButton a1Button;
    private JButton a3Button;
    private JButton a2Button;
    private JButton a7Button;
    private JButton a8Button;
    private JButton a9Button;
    private JButton a4Button;
    private JButton a5Button;
    private JButton a6Button;
    private JButton a0Button;

    //The main Panel
    private JPanel main;



    //The default password is 0000 for testing
    String  password = "0000";

    /**
     * Creates a new keypad and handles some of the basic setup
     *
     * */
    public KeypadGUI(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(main);
        this.pack();
        setSize(300,400);
        setupActions();

    }

    /**
     * Sets up all the action listeners for the buttons on the Keypad
     * */
    public void setupActions(){

        //At this point if you hit this button it will always close the keypad and open the main as if it has been unlocked
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(new String(passwordField1.getPassword()));
                if(new String(passwordField1.getPassword()).equals(password) ) {
                    setVisible(false);
                    JFrame GUI = new MainGUI("Ninja Protection Service");
                    GUI.setVisible(true);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Incorrect Password");
                }
            }
        });

        //As we cannot actually contact emergency services this just prints some text saying it has
        //Will eventually alert the base station
        PANICButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Panic button pressed... Contacting Authorities");
                System.out.println("Alerting monitoring center...");
                System.out.println("Arming System...");
            }
        });

        //This is the setup for all of the keys, they are exactly the same
        a0Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordField1.setText(new String(passwordField1.getPassword()) + "0");
            }
        });
        a1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordField1.setText(new String(passwordField1.getPassword()) + "1");
            }
        });
        a2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordField1.setText(new String(passwordField1.getPassword()) + "2");
            }
        });
        a3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordField1.setText(new String(passwordField1.getPassword()) + "3");
            }
        });
        a4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordField1.setText(new String(passwordField1.getPassword()) + "4");
            }
        });
        a5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordField1.setText(new String(passwordField1.getPassword()) + "5");
            }
        });
        a6Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordField1.setText(new String(passwordField1.getPassword()) + "6");
            }
        });
        a7Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordField1.setText(new String(passwordField1.getPassword()) + "7");
            }
        });
        a8Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordField1.setText(new String(passwordField1.getPassword()) + "8");
            }
        });
        a9Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordField1.setText(new String(passwordField1.getPassword()) + "9");
            }
        });

    }

    /**
     * Launches the application
     * */
    public static void main(String[] args){
        JFrame frame = new KeypadGUI("Ninja Protection Services");
        frame.setVisible(true);
    }

}
