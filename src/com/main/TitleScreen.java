package com.main;

import javax.swing.*;
import java.awt.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class TitleScreen extends JPanel {
    private JFrame mainFrame;
    private GamePanel gamePanel;
    private Image backgroundImage;
    private Clip musicClip;
    private Clip gameplayClip;
    private boolean showingAbout = false;
    private JLabel imageLabel;
    
    public TitleScreen(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        setPreferredSize(new Dimension(800, 600));
        setLayout(null);
        playMusic();

        try {
            backgroundImage = new ImageIcon("res/titlescreenbg/titlescreenbg.png").getImage();
        } catch (Exception e) {
            System.out.println("Error loading background image: " + e.getMessage());
        }

        JLabel titleLabel1 = new JLabel("School Rescue");
        titleLabel1.setFont(new Font("Cambria", Font.BOLD, 45));
        titleLabel1.setForeground(Color.WHITE);
        titleLabel1.setBounds(248, 17, 300, 50);
        add(titleLabel1);
        
        JLabel titleLabel2 = new JLabel("The Hero's Journey");
        titleLabel2.setFont(new Font("Cambria", Font.BOLD, 25));
        titleLabel2.setForeground(Color.WHITE);
        titleLabel2.setBounds(283, 53, 300, 50);
        add(titleLabel2);
        
         JButton playButton = new JButton("Play");
        playButton.setFont(new Font("Cambria", Font.BOLD, 22));
        playButton.setBackground(Color.WHITE);
        playButton.setForeground(Color.BLACK);
        playButton.setBounds(150, 525, 100, 50);  // Moved more to the left
        playButton.addActionListener(e -> startGame());
        add(playButton);
        
        JButton aboutButton = new JButton("About");
        aboutButton.setFont(new Font("Cambria", Font.BOLD, 22));
        aboutButton.setBackground(Color.WHITE);
        aboutButton.setForeground(Color.BLACK);
        aboutButton.setBounds(350, 525, 100, 50);  // Kept in center
        aboutButton.addActionListener(e -> toggleAboutOverlay());
        add(aboutButton);
        
        JButton quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Cambria", Font.BOLD, 22));
        quitButton.setBounds(550, 525, 100, 50);  // Moved more to the right
        quitButton.setBackground(Color.WHITE);
        quitButton.setForeground(Color.BLACK);
        quitButton.setFocusPainted(false);
        quitButton.addActionListener(e -> System.exit(0));
        add(quitButton);
        
        JPanel blackbackground1 = new JPanel();
        blackbackground1.setBackground(Color.BLACK);
        blackbackground1.setBounds(0, 0, 1000, 100);
        add(blackbackground1);
        
        JPanel blackbackground2 = new JPanel();
        blackbackground2.setBackground(Color.BLACK);
        blackbackground2.setBounds(0, 510, 1000, 100);
        add(blackbackground2);
        
        ImageIcon imageIcon = new ImageIcon("res/player/tile001.png");
        imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(350, 200, 100, 250);
        add(imageLabel);
        
    }
    
    private void toggleAboutOverlay() {
        showingAbout = !showingAbout;
        imageLabel.setVisible(!showingAbout); // Hide/show player when toggling About
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        Graphics2D g2 = (Graphics2D)g;
        
        if (showingAbout) {
            // Semi-transparent background
            g2.setColor(new Color(0, 0, 0, 180));
            g2.fillRect(0, 0, getWidth(), getHeight());

            // About content box
            int boxWidth = 600;
            int boxHeight = 350;
            int boxX = getWidth()/2 - boxWidth/2;
            int boxY = getHeight()/2 - boxHeight/2;

            // Draw about box
            g2.setColor(new Color(0, 0, 0, 200));
            g2.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 25, 25);
            g2.setColor(Color.WHITE);
            g2.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 25, 25);

            // Draw title
            g2.setFont(new Font("Cambria", Font.BOLD, 32));
            String title = "Meet the Team!";
            int titleX = boxX + boxWidth/2 - g2.getFontMetrics().stringWidth(title)/2;
            g2.drawString(title, titleX, boxY + 50);

            // Draw team members
            g2.setFont(new Font("Cambria", Font.PLAIN, 20));
            String[] team = {
                "Aaron Lee Apolonio – Technical Lead",
                "Ron Calixto – Game Master",
                "Mark Anthony Cruel – Programmer",
                "Bryan Dizon – Game Designer",
                "Kim Ruds Guston – Game Writer",
                "Jan Louis Toledana – Game Designer"
            };

            int startY = boxY + 100;
            for (String member : team) {
                int memberX = boxX + 50;
                g2.drawString(member, memberX, startY);
                startY += 35;
            }

            // Draw footer
            g2.setFont(new Font("Cambria", Font.ITALIC, 24));
            String footer = "The minds behind this adventure!";
            int footerX = boxX + boxWidth/2 - g2.getFontMetrics().stringWidth(footer)/2;
            g2.drawString(footer, footerX, boxY + boxHeight - 30);

            // Draw close instruction
            g2.setFont(new Font("Cambria", Font.PLAIN, 20));
            String closeText = "Click About to close";
            int closeX = boxX + boxWidth/2 - g2.getFontMetrics().stringWidth(closeText)/2;
            g2.drawString(closeText, closeX, boxY + boxHeight + 23);
        }
    }
    
    private void startGame() {
        if (musicClip != null) {
            musicClip.stop();
        }
        
        playGameMusic();
        
        mainFrame.remove(this);
        gamePanel = new GamePanel();
        mainFrame.add(gamePanel);
        mainFrame.pack();
        gamePanel.requestFocusInWindow();
        gamePanel.startGamethread();
    }
    
    private void playMusic() {
        try {
            File musicFile = new File("res/sound/titlemusic.wav");
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
            musicClip = AudioSystem.getClip();
            musicClip.open(audioInput);
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Error playing music: " + e.getMessage());
        }
    }

    private void playGameMusic() {
        try {
            File musicFile = new File("res/sound/bgm.wav");
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
            gameplayClip = AudioSystem.getClip();
            gameplayClip.open(audioInput);
            gameplayClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Error playing gameplay music: " + e.getMessage());
        }
    }

    public void stopGameMusic() {
        if (gameplayClip != null) {
            gameplayClip.stop();
            gameplayClip.close();
        }
    }
}