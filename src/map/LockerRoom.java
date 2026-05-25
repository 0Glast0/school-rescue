package map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.main.GamePanel;

public class LockerRoom {
	
	private BufferedImage mapImage;
    public boolean[][] collisionMap;
    private int tileSize;
    
    private boolean welcomeMessageDisplayedLocker = false;
    public boolean endVisible = false; 
    private BufferedImage ending;

    public LockerRoom (String mapPath, int tileSize) {

        this.tileSize = tileSize;

        try {
            mapImage = ImageIO.read(getClass().getResourceAsStream(mapPath));
            ending = ImageIO.read(getClass().getResourceAsStream("/ending/still_end_m.png"));
            initializeCollisionMap();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
            { true, true, true, true, true, false, false, false, true, true, false, true, true, false, true, true, false, false, true, true, true, true },
            { true, true, true, true, false, false, false, false, false, false, false, true, true, false, false, false, false, false, true, true, true, true },
            { true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
        };
    }
    
    public boolean isGirl(int x, int y) {
        int col = x / tileSize;
        int row = y / tileSize;
        
        if (11 == col && 8  == row) {
            return true;
        }
        return false;
    }
    
    public void checkGirl(int x, int y) {
        if (isGirl(x, y)) {
        	endVisible = true;
        }
    }
    
    public boolean isDoor(int x, int y) {
        int col = x / tileSize;
        int row = y / tileSize;
        
        if (3 == col && 8 == row) {
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
        if (!welcomeMessageDisplayedLocker) {
            gamePanel.addDialogue("Girl: You saved me, Thank you Ron!");
            welcomeMessageDisplayedLocker = true;
        }
    }


    public void draw(Graphics2D g2, int screenWidth, int screenHeight) {
        g2.drawImage(mapImage, 0, 0, screenWidth, screenHeight, null);
        
        if (endVisible) {
        	 int x = (screenWidth - ending.getWidth()) / 2;
            int y = (screenHeight - ending.getHeight()) / 2;
            g2.drawImage(ending, x, y, null);
        }
    }

}
