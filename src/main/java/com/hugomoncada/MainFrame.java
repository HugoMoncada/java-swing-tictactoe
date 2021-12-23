package com.hugomoncada;

import javax.swing.JFrame;

public class MainFrame extends JFrame{
    
    public MainFrame(){
        add(new Board());
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
