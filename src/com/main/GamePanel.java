package com.main;

import entity.Player;
import entity.NPC;

import map.ComputerLab;
import map.Faculty;
import map.Hallway;
import map.Hallway2;
import map.LockerRoom;
import map.MathRoom;
import map.Room1;
import map.ScienceRoom;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

import java.awt.AlphaComposite;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.BasicStroke;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class GamePanel extends JPanel implements Runnable{
    
    final int originalTileSize = 16;
    final int scale = 3;
    
    public int tileSize = originalTileSize * scale;
    final int maxScreenCol = 22;
    final int maxScreenRow = 16;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    public enum MapState { HALLWAY, ROOM, COMPUTERLAB, SCIENCEROOM, MATHROOM, FACULTY, HALLWAY2, LOCKERROOM}
    public MapState currentMap = MapState.HALLWAY;
    
    int FPS = 60;
    public boolean finalLevel = false;
    
	
	private Rectangle pauseButton;
	private boolean mouseHoverPause;
	private final int PAUSE_BUTTON_SIZE = 32;
	private final int PAUSE_BUTTON_PADDING = 10;
    private boolean isPaused = false;
    private BufferedImage backgroundBuffer;
    private final String[] pauseOptions = {"Continue", "Restart", "Quit"};
    private int selectedOption = 0;
    
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this,keyH);
    public Hallway hallway; 
    public Hallway2 hallway2;
    public Room1 room1;
    public ComputerLab comlab;
    public ScienceRoom science;
    public MathRoom math;
    public Faculty faculty;
    public LockerRoom locker;
    public QuizBattle quizBattle;
    
    NPC npc = new NPC(980, 450, 32, 32);
   
    private Queue<String> dialogueQueue = new LinkedList<>();
    private boolean showDialogue = false;
    private int dialogueTimer = 0;
    private final int dialogueDisplayTime = 80;
    private String currentMessage = "";

    public boolean room1WelcomeMessageDisplayed = false; 
    public boolean welcomeMessageDisplayedComLab = false;// NEW FLAG
    public boolean welcomeMessageDisplayedScience = false;
    public boolean welcomeMessageDisplayedMathRoom = false;
    public boolean welcomeMessageDisplayedFaculty = false;
    public boolean welcomeMessageDisplayedLocker = false;

    public GamePanel(){
    	
    	//if(room1WelcomeMessageDisplayed == true && welcomeMessageDisplayedComLab == true && welcomeMessageDisplayedScience == true && welcomeMessageDisplayedMathRoom == true ) {
        	//finalLevel = true;
    		//currentMap = MapState.HALLWAY2;
        //}
        
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        
        hallway = new Hallway("/map/Hallway (22x16) Ver 1.png", tileSize);
        hallway2 = new Hallway2("/map/Hallway (22x16) Ver 2.png", tileSize);
        room1 = new Room1("/map/Classroom B.png", tileSize);
        comlab = new ComputerLab("/map/Computer Room.png", tileSize);
        science = new ScienceRoom("/map/Science Room.png", tileSize);
        math = new MathRoom("/map/Art Room.png", tileSize);
        faculty = new Faculty("/map/Faculty Room.png", tileSize);
        locker = new LockerRoom("/map/Locker Room.png", tileSize);
        
        pauseButton = new Rectangle(
                screenWidth - PAUSE_BUTTON_SIZE - PAUSE_BUTTON_PADDING,
                PAUSE_BUTTON_PADDING,
                PAUSE_BUTTON_SIZE,
                PAUSE_BUTTON_SIZE
            );
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isPaused) {
                    // Check for menu option clicks
                    int menuWidth = 200;
                    int menuHeight = 250;
                    int menuX = screenWidth/2 - menuWidth/2;
                    int menuY = screenHeight/2 - menuHeight/2;
                    
                    for (int i = 0; i < pauseOptions.length; i++) {
                        int optionY = menuY + 100 + (i * 40);
                        Rectangle optionBounds = new Rectangle(menuX, optionY - 20, menuWidth, 30);
                        if (optionBounds.contains(e.getPoint())) {
                            selectedOption = i;
                            switch(i) {
                                case 0: // Continue
                                    isPaused = false;
                                    break;
                                case 1: // Restart
                                    resetGame();
                                    isPaused = false;
                                    break;
                                case 2: // Quit
                                    System.exit(0);
                                    break;
                            }
                        }
                    }
                } else if (pauseButton.contains(e.getPoint())) {
                    togglePause();
                }
            }
        });
        
        // Single MouseMotionAdapter for hover effect
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseHoverPause = pauseButton.contains(e.getPoint());
                repaint();
            }
        });
        
        this.addMouseListener(new java.awt.event.MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                
                room1.handleBackButtonClick(mouseX, mouseY);
                science.handleBackButtonClick(mouseX, mouseY);
                math.handleBackButtonClick(mouseX, mouseY);
                comlab.handleBackButtonClick(mouseX, mouseY);
            }
        });
        
        addMouseListener(faculty);
    }
    
    public boolean isPaused() {
        return isPaused;
    }
    
    public void startGamethread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    private void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            // Create a snapshot of the current screen
            backgroundBuffer = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = backgroundBuffer.createGraphics();
            super.paintComponent(g2);
            paintComponent(g2);
            g2.dispose();
        }
    }

    private void handlePauseMenuInput() {
        if (keyH.upPressed) {
            selectedOption = (selectedOption - 1 + pauseOptions.length) % pauseOptions.length;
            keyH.upPressed = false;
        }
        if (keyH.downPressed) {
            selectedOption = (selectedOption + 1) % pauseOptions.length;
            keyH.downPressed = false;
        }
        if (keyH.enterPressed) {
            switch(selectedOption) {
                case 0: // Continue
                    isPaused = false;
                    break;
                case 1: // Restart
                    resetGame();
                    isPaused = false;
                    break;
                case 2: // Quit
                    System.exit(0);
                    break;
            }
            keyH.enterPressed = false;
        }
    }

    void resetGame() {
        // Reset game state
        currentMap = MapState.HALLWAY;
        player.setDefaultValues();
        room1WelcomeMessageDisplayed = false;
        welcomeMessageDisplayedComLab = false;
        welcomeMessageDisplayedScience = false;
        welcomeMessageDisplayedMathRoom = false;
        welcomeMessageDisplayedFaculty = false;
        welcomeMessageDisplayedLocker = false;
        finalLevel = false;
        faculty.showQuestion = false;
    }

    @Override
    public void run() {
        
        while(gameThread != null){
            
            double drawInterval = 1000000000/FPS;
            double nextDrawTime = System.nanoTime() + drawInterval;
            
            gameUpdate();            
            repaint();
                        
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
                
                if(remainingTime < 0){
                    remainingTime = 0;
                }
                
                Thread.sleep((long) remainingTime);
                
                nextDrawTime += drawInterval;
                
            } catch (InterruptedException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    public void gameUpdate() {
    	
    	/*if(quizBattle.mcLife == 0) {
    		resetGame();
    	}*/
    	
        
        if (keyH.escPressed) {
            togglePause();
            keyH.escPressed = false;
        }
        
        if (isPaused) {
            handlePauseMenuInput();
            return;
        }
        
        player.update();
        checkCollisions();
        
        if (currentMap == MapState.HALLWAY) {
            hallway.updateDialogue();
        } else if (currentMap == MapState.ROOM) {
            room1.updateDialogue(this);
            room1WelcomeMessageDisplayed = true; 
        } else if (currentMap == MapState.COMPUTERLAB) {
        	comlab.updateDialogue(this); 
        	welcomeMessageDisplayedComLab = true;
        }
        else if (currentMap == MapState.SCIENCEROOM) {
        	science.updateDialogue(this);
        	welcomeMessageDisplayedScience = true;// Delegates dialogue handling to Room1
        }
        else if (currentMap == MapState.MATHROOM) {
        	math.updateDialogue(this); 
        	welcomeMessageDisplayedMathRoom = true;// Delegates dialogue handling to Room1
        }
        else if (currentMap == MapState.FACULTY) {
        	faculty.updateDialogue(this); 
        	welcomeMessageDisplayedFaculty = true;// Delegates dialogue handling to Room1
        }
        else if (currentMap == MapState.LOCKERROOM) {
        	locker.updateDialogue(this); 
        	welcomeMessageDisplayedLocker = true;// Delegates dialogue handling to Room1
        }
        
        else if(room1WelcomeMessageDisplayed == true && welcomeMessageDisplayedComLab == true && welcomeMessageDisplayedScience == true && welcomeMessageDisplayedMathRoom == true ) {
        	finalLevel = true;
    		currentMap = MapState.HALLWAY2;
        }
        

        if (showDialogue) {
            dialogueTimer++;
            if (dialogueTimer > dialogueDisplayTime) {
                dialogueTimer = 0;
                if (!dialogueQueue.isEmpty()) {
                    currentMessage = dialogueQueue.poll();
                } else {
                    showDialogue = false;
                }
            }
        } else if (!dialogueQueue.isEmpty()) {
            showDialogue = true;
            currentMessage = dialogueQueue.poll();
        }
    }

    private void checkCollisions() {
        // Only check collisions if we're in HALLWAY (Hallway1)
        if (currentMap == MapState.HALLWAY) {
            if (npc != null && player.getHitbox().intersects(npc.getHitbox())) {
                if (!npc.isDialogueActive()) {
                    addDialogue("This door is locked!");
                    addDialogue("You need to visit all rooms first!");
                    npc.setDialogueActive(true);
                }
            } else {
                npc.setDialogueActive(false);
            }
        }
    }

    private boolean isPathOpen() {
        return room1WelcomeMessageDisplayed && 
               welcomeMessageDisplayedComLab && 
               welcomeMessageDisplayedScience && 
               welcomeMessageDisplayedMathRoom &&
               welcomeMessageDisplayedFaculty &&
               welcomeMessageDisplayedLocker;
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        if (currentMap == MapState.HALLWAY) {
            hallway.draw(g2, screenWidth, screenHeight);
        if (hallway.showDialogue()) {
            drawDialogue(g2, hallway.getCurrentDialogueMessage());
        }
        
        } else if (currentMap == MapState.ROOM) {
            room1.draw(g2, screenWidth, screenHeight);
        } else if (currentMap == MapState.COMPUTERLAB) {
        	comlab.draw(g2, screenWidth, screenHeight);
        } else if (currentMap == MapState.SCIENCEROOM) {
        	science.draw(g2, screenWidth, screenHeight);
        } else if (currentMap == MapState.MATHROOM) {
        	math.draw(g2, screenWidth, screenHeight);
        } else if (currentMap == MapState.FACULTY) {
        	faculty.draw(g2, screenWidth, screenHeight);
        } else if (currentMap == MapState.HALLWAY2) {
        	hallway2.draw(g2, screenWidth, screenHeight);
        } else if (currentMap == MapState.LOCKERROOM) {
        	locker.draw(g2, screenWidth, screenHeight);
        }
        
        if(math.hintVisible == false && comlab.hintVisible == false && room1.hintVisible == false && science.hintVisible == false && faculty.showQuestion == false && locker.endVisible == false) {
        	player.draw(g2);
        } 
        
        if (showDialogue) {
            drawDialogue(g2, currentMessage);
        }
        
        if (!isPaused) {
        	drawPauseButton(g2);
            
        }
        
        // Draw pause menu if game is paused
        if (isPaused) {
            // Draw blurred background
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g2.drawImage(backgroundBuffer, 0, 0, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            // Draw pause menu
            int menuWidth = 200;
            int menuHeight = 250;
            int menuX = screenWidth/2 - menuWidth/2;
            int menuY = screenHeight/2 - menuHeight/2;

            // Draw menu background
            g2.setColor(new Color(0, 0, 0, 200));
            g2.fillRoundRect(menuX, menuY, menuWidth, menuHeight, 25, 25);
            g2.setColor(Color.WHITE);
            g2.drawRoundRect(menuX, menuY, menuWidth, menuHeight, 25, 25);

            // Draw menu title
            g2.setFont(new Font("Arial", Font.BOLD, 30));
            String title = "PAUSED";
            int titleX = menuX + menuWidth/2 - g2.getFontMetrics().stringWidth(title)/2;
            g2.drawString(title, titleX, menuY + 50);

            // Draw menu options
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            for (int i = 0; i < pauseOptions.length; i++) {
                if (i == selectedOption) {
                    g2.setColor(Color.YELLOW);
                } else {
                    g2.setColor(Color.WHITE);
                }
                int optionX = menuX + menuWidth/2 - g2.getFontMetrics().stringWidth(pauseOptions[i])/2;
                g2.drawString(pauseOptions[i], optionX, menuY + 100 + (i * 40));
            }
        }
        
        g2.dispose();
    }
    
    private void drawPauseButton(Graphics2D g2) {
        // Save the original stroke and color
        Stroke originalStroke = g2.getStroke();
        Color originalColor = g2.getColor();
        
        // Draw button background
        g2.setColor(new Color(0, 0, 0, mouseHoverPause ? 180 : 120));
        g2.fillRoundRect(pauseButton.x, pauseButton.y, pauseButton.width, pauseButton.height, 10, 10);
        
        // Draw button border
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(pauseButton.x, pauseButton.y, pauseButton.width, pauseButton.height, 10, 10);
        
        // Draw pause icon
        int padding = 8;
        int barWidth = 4;
        g2.setColor(Color.WHITE);
        g2.fillRect(pauseButton.x + padding, 
                    pauseButton.y + padding, 
                    barWidth, 
                    pauseButton.height - (padding * 2));
        g2.fillRect(pauseButton.x + pauseButton.width - padding - barWidth, 
                    pauseButton.y + padding, 
                    barWidth, 
                    pauseButton.height - (padding * 2));
        
        // Restore original stroke and color
        g2.setStroke(originalStroke);
        g2.setColor(originalColor);
    }
    
    private void drawDialogue(Graphics2D g2, String message) {
        int boxWidth = screenWidth - 100;
        int boxHeight = 100;
        int boxX = 50;
        int boxY = screenHeight - 150;

        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

        g2.setColor(Color.white);
        g2.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

        g2.setFont(new Font("Arial", Font.BOLD, 24));
        g2.setColor(Color.white);
        int textX = boxX + 20;
        int textY = boxY + 50;
        g2.drawString(message, textX, textY);
    }

    public void addDialogue(String message) {
        dialogueQueue.add(message);
    }
    
    /*public void update() {
        faculty.update(player.getX(), player.getY());
    }*/
}
