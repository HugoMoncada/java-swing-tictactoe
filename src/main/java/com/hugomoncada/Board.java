package com.hugomoncada;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Board extends JPanel implements ActionListener{
    
    byte turn; 
    boolean isOver;
    JButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8 , btn9;
    ArrayList<JButton> buttons, usedButtons, row1, row2, row3, col1,col2,col3, diag1,diag2;
    ImageIcon icon1, icon2, drawIcon;
    ArrayList winConditions[];
    JLabel messageLabel; 
    SoundEffect soundEffect; 

    public Board(){

        initializeVariables();
        addActionListeners(buttons);
        addButtonsToPanel(buttons);
        setLayout(new GridLayout(3,3,2,2));
        
    }

    // Why create a method for this?? just to be more organized.
    private void initializeVariables(){
        turn = 1; 
        
        isOver = false; 
        
        icon1 = new ImageIcon("wave.png");
        icon2 = new ImageIcon("fire.png");
        drawIcon = new ImageIcon("thinking.png");

        messageLabel = new JLabel();
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 23));

        soundEffect = new SoundEffect();
        
        btn1 = new JButton();
        btn2 = new JButton();
        btn3 = new JButton();
        btn4 = new JButton();
        btn5 = new JButton();
        btn6 = new JButton();
        btn7 = new JButton();
        btn8 = new JButton();
        btn9 = new JButton();

        row1 = new ArrayList<>(Arrays.asList(btn1,btn2,btn3));
        row2 = new ArrayList<>(Arrays.asList(btn4,btn5,btn6));
        row3 = new ArrayList<>(Arrays.asList(btn7,btn8,btn9));
        col1 = new ArrayList<>(Arrays.asList(btn1,btn4,btn7));
        col2 = new ArrayList<>(Arrays.asList(btn2,btn5,btn8));
        col3 = new ArrayList<>(Arrays.asList(btn3,btn6,btn9));
        diag1 = new ArrayList<>(Arrays.asList(btn1,btn5,btn9));
        diag2 = new ArrayList<>(Arrays.asList(btn3,btn5,btn7));
        
        // Rows and columns that a player need to complete to win. 
        winConditions = new ArrayList[8];
        winConditions[0] =  row1;
        winConditions[1] =  row2; 
        winConditions[2] =  row3;
        winConditions[3] =  col1;
        winConditions[4] =  col2;
        winConditions[5] =  col3;
        winConditions[6] =  diag1;
        winConditions[7] =  diag2;

        // Buttons that have already been selected
        usedButtons = new ArrayList<>();
        
        // A bit of style for the buttons
        buttons = new ArrayList<>(Arrays.asList(btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9));
        buttons.forEach(btn ->  {
            btn.setFocusable(false);
            btn.setBackground(Color.black);
        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton selectedButton = (JButton) e.getSource();

        if(isOver == false){
            if(!hasBeenUsed(selectedButton)){
                if( turn % 2 > 0 ){
                    selectedButton.setIcon(icon1);
                    usedButtons.add(selectedButton);
                    soundEffect.playFile("effect.wav");
                }
                else{
                    selectedButton.setIcon(icon2);
                    usedButtons.add(selectedButton);
                    soundEffect.playFile("effect.wav");
                }
                turn++; 
                validateBoardStatus();
            }
        }
        
    }

 
    private void addButtonsToPanel(ArrayList<JButton> buttons){
        buttons.forEach(e -> {
            add(e);
        });
    }


    private void  validateBoardStatus(){

        /*
        * Pass every win condition ( all rows and cols posibible to win ) to the check for winner method 
        * If there is a winner change isOver global variable to true,   
        */
        for (ArrayList row : winConditions) {
                if(checkForWinner(row)){
                    isOver = true;
                    break;
            }
        }

        if(isOver){
            
            reStartTheGame();
        }

        if(turn == 10 ){
            messageLabel.setText("It is a Draw!");
            JOptionPane.showMessageDialog(this, messageLabel, "Draw", JOptionPane.PLAIN_MESSAGE, drawIcon);
            reStartTheGame();
        }
        
    }


    /*
    * Checks if the row or column that is being passed has the same icon 3 times. 
    * if any row or column has it then the game is over
    */
    private boolean checkForWinner(ArrayList<JButton> rowOrCol){
        byte player1 = 0; 
        byte player2 = 0; 
        boolean isThereWinner = false; 

        for (JButton button : rowOrCol) {
            if(button.getIcon() == icon1){
                player1++; 
            }
            else if( button.getIcon() == icon2 ){
                player2++; 
            }
        }
        
        if( player1 == 3 ){
            isThereWinner = true;
            messageLabel.setText("Is the winner!!");
            JOptionPane.showMessageDialog(this, messageLabel, "Congratulations", JOptionPane.PLAIN_MESSAGE, icon1);
        }
        else if( player2 == 3 ){
            isThereWinner = true;
            messageLabel.setText("Is the winner!!");
            JOptionPane.showMessageDialog(this,  messageLabel, "Congratulations", JOptionPane.PLAIN_MESSAGE, icon2);
        }
        
        return isThereWinner; 
    }


    private void reStartTheGame(){
        buttons.forEach(e -> {
            e.setIcon(null);
        });
        isOver = false; 
        turn = 1; 
        usedButtons.clear();
        
    }


    private boolean hasBeenUsed(JButton button){
        boolean isUsed = false; 
        if(usedButtons.contains(button)){
            isUsed = true; 
        }
        return isUsed;
    }


    private void addActionListeners(ArrayList<JButton> buttons){
        buttons.forEach(e ->  {
            e.addActionListener(this);
        });
    }

}
