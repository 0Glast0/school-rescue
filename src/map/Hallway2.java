package map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.main.GamePanel;

public class Hallway2 {
	
	private BufferedImage mapImage;
	public boolean[][] collisionMap; 
    private int tileSize;
    
    public Hallway2(String mapPath, int tileSize) {
    	
    	this.tileSize = tileSize;
    	
        try {
            mapImage = ImageIO.read(getClass().getResourceAsStream(mapPath));
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
            { true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
            { true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
            { true, true, true, true, false, true, true, true, true, true, true, false, true, true, true, true, true, true, true, false, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
        };
    }
     
    public boolean isRoomDoor(int x, int y) {
        int col = x / tileSize;
        int row = y / tileSize;
        
        if (4 == col && 6 == row) {
            return true;
        }
        return false;
    }
    public boolean isComLabDoor(int x, int y) {
        int col = x / tileSize;
        int row = y / tileSize;
        
        if (15 == col && 6 == row) {
            return true;
        }
        return false;
    }
    public boolean isScienceDoor(int x, int y) {
        int col = x / tileSize;
        int row = y / tileSize;
        
        if (4 == col && 10 == row) {
            return true;
        }
        return false;
    } 
    public boolean isMathDoor(int x, int y) {
        int col = x / tileSize;
        int row = y / tileSize;
        
        if (11 == col && 10 == row) {
            return true;
        }
        return false;
    }
    public boolean isFacultyDoor(int x, int y) {
        int col = x / tileSize;
        int row = y / tileSize;
        
        if (19 == col && 10 == row) {
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


    public void draw(Graphics2D g2, int screenWidth, int screenHeight) {
        g2.drawImage(mapImage, 0, 0, screenWidth, screenHeight, null);
    }

}
