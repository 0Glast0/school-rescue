package com.main;

import javax.swing.*;

public class FinalsGameMain {
    private static JFrame mainFrame;
    
    public static void main(String[] args) {
        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setTitle("FINALS GAME");
        
        mainFrame.add(new TitleScreen(mainFrame));
        
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}
