package map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.main.GamePanel;

public class MathRoom {
	
	private BufferedImage mapImage;
	private BufferedImage hintImage;
    public boolean[][] collisionMap;
    private int tileSize;
    public boolean hintVisible = false; 
    private Rectangle backButton;
    
    private boolean welcomeMessageDisplayedMathRoom = false;
    public MathRoom (String mapPath, int tileSize) {

        this.tileSize = tileSize;

        try {
            mapImage = ImageIO.read(getClass().getResourceAsStream(mapPath));
            hintImage = ImageIO.read(getClass().getResourceAsStream("/hints/Math Room.png"));
            initializeCollisionMap();
            
            backButton = new Rectangle(10, 10, 100, 50);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeCollisionMap() {
        collisionMap = new boolean[][] {
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, false, false, false, false, false, false, true, true, true, false, true, true, true, true, true, true, true },
            { true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true },
            { true, true, true, true, true, false, false, true, true, false, false, true, true, false, false, true, true, true, true, true, true, true },
            { true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true },
            { true, true, true, true, true, false, false, true, true, false, false, true, true, false, false, true, true, true, true, true, true, true },
            { true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true },
            { true, true, true, true, true, false, false, true, true, false, false, true, true, false, false, true, true, true, true, true, true, true },
            { true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
        };
    }
    
    public boolean isHint(int x, int y) {
        int col = x / tileSize;
        int row = y / tileSize;
        
        if (11 == col && 4 == row) {
            return true;
        }
        return false;
    }
    
    public void checkHint(int x, int y) {
        if (isHint(x, y)) {
            hintVisible = true;
        }
    }
    
    public void handleBackButtonClick(int mouseX, int mouseY) {
        if (hintVisible && backButton.contains(mouseX, mouseY)) {
            hintVisible = false;
        }
    }
    
    public boolean isDoor(int x, int y) {
        int col = x / tileSize;
        int row = y / tileSize;
        
        if (6 == col && 3 == row) {
            return true;
        }
        return false;
    }

    public boolean checkCollision(int x, int y) {
        int col = x / tileSize;
        int row = y / tileSize;

        if (col < 0 || row < 0 || col >= collisionMap[0].length || row >= collisionMap.length) {
            return true;
        }

        return collisionMap[row][col];
    }
    
    public void updateDialogue(GamePanel gamePanel) {
        // Display the welcome message once
        if (!welcomeMessageDisplayedMathRoom) {
            gamePanel.addDialogue("Welcome to the Math Room");
            welcomeMessageDisplayedMathRoom = true;
        }
    }

    public void draw(Graphics2D g2, int screenWidth, int screenHeight) {
        g2.drawImage(mapImage, 0, 0, screenWidth, screenHeight, null);
        
        if (hintVisible) {
            int hintX = (screenWidth - hintImage.getWidth()) / 2;
            int hintY = (screenHeight - hintImage.getHeight()) / 2;
            g2.drawImage(hintImage, hintX, hintY, null);

            g2.setColor(Color.WHITE);
            g2.fill(backButton);
            g2.setColor(Color.BLACK);
            g2.drawString("BACK", backButton.x + 25, backButton.y + 30);
        }
    }

}
