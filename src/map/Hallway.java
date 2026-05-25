package map;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;

public class Hallway {
	
	private BufferedImage mapImage;
	public boolean[][] collisionMap; 
    private int tileSize;

    private Queue<String> dialogueQueue = new LinkedList<>();
    private boolean showDialogue = false;
    private String currentMessage = "";
    private int dialogueTimer = 0;
    private final int dialogueDisplayTime = 80;

    public Hallway(String mapPath, int tileSize) {
    	this.tileSize = tileSize;
    	
        try {
            mapImage = ImageIO.read(getClass().getResourceAsStream(mapPath));
            initializeCollisionMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Initialize dialogue (optional)
        initializeDialogue();
    }
    
    private void initializeCollisionMap() {
        collisionMap = new boolean[][] {
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
            { true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
            { true, true, true, true, false, true, true, true, true, true, true, false, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
        };
    }
    
    private void initializeDialogue() {
        addDialogue("Welcome to Ron's Adventure!");
        addDialogue("Explore the hallways and find hidden clues.");
        addDialogue("The hint's are everywhere. Good luck!");
    }
    
    // Check if the player is interacting with specific doors in the hallway
    public boolean isRoomDoor(int x, int y) {
        int col = x / tileSize;
        int row = y / tileSize;
        return (4 == col && 6 == row);
    }
    
 // Method to check if the player is near the Com Lab door
    public boolean isComLabDoor(int x, int y) {
        int col = x / tileSize;
        int row = y / tileSize;

        return (15 == col && 6 == row);  // Com Lab door location
    }

    // Method to check if the player is near the Science door
    public boolean isScienceDoor(int x, int y) {
        int col = x / tileSize;
        int row = y / tileSize;

        return (4 == col && 10 == row);  // Science door location
    }

    // Method to check if the player is near the Math door
    public boolean isMathDoor(int x, int y) {
        int col = x / tileSize;
        int row = y / tileSize;

        return (11 == col && 10 == row);  // Math door location
    }

    public boolean checkCollision(int x, int y) {
        int col = x / tileSize;
        int row = y / tileSize;

        if (col < 0 || row < 0 || col >= collisionMap[0].length || row >= collisionMap.length) {
            return true; 
        }
        return collisionMap[row][col];
    }

    public void addDialogue(String message) {
        dialogueQueue.add(message);
    }

    public void updateDialogue() {
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

    public boolean showDialogue() {
        return showDialogue;
    }

    public String getCurrentDialogueMessage() {
        return currentMessage;
    }

    public void drawDialogue(Graphics2D g2, int screenWidth, int screenHeight) {
        if (!showDialogue) return;

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
        g2.drawString(currentMessage, textX, textY);
    }

    public void draw(Graphics2D g2, int screenWidth, int screenHeight) {
        g2.drawImage(mapImage, 0, 0, screenWidth, screenHeight, null);
    }
}
