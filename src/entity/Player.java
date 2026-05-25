package entity;

import com.main.GamePanel;
import com.main.GamePanel.MapState;
import com.main.KeyHandler;
import com.main.QuizBattle;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity{
    
    GamePanel gp;
    KeyHandler keyH;
    private QuizBattle quizBattle;
    
    public Player(GamePanel gp, KeyHandler keyH){
        
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
    
    }
    
    public void setDefaultValues(){
        
    	x = (gp.screenWidth / 2) - (gp.tileSize / 2);
        y = (gp.screenHeight / 2) - (gp.tileSize / 2);
        speed = 2;
        direction = "down";
    }
    public void getPlayerImage() {
    	
    	try {
    		
    		up = ImageIO.read(getClass().getResourceAsStream("/player/tile010.png"));
    		up1 = ImageIO.read(getClass().getResourceAsStream("/player/tile009.png"));
    		up2 = ImageIO.read(getClass().getResourceAsStream("/player/tile011.png"));
    		down = ImageIO.read(getClass().getResourceAsStream("/player/tile001.png"));
    		down1 = ImageIO.read(getClass().getResourceAsStream("/player/tile000.png"));
    		down2 = ImageIO.read(getClass().getResourceAsStream("/player/tile002.png"));
    		left = ImageIO.read(getClass().getResourceAsStream("/player/tile004.png"));
    		left1 = ImageIO.read(getClass().getResourceAsStream("/player/tile003.png"));
    		left2 = ImageIO.read(getClass().getResourceAsStream("/player/tile005.png"));
    		right = ImageIO.read(getClass().getResourceAsStream("/player/tile007.png"));
    		right1 = ImageIO.read(getClass().getResourceAsStream("/player/tile006.png"));
    		right2 = ImageIO.read(getClass().getResourceAsStream("/player/tile008.png"));
    		
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    
    boolean isMoving = false;
    
    public void update(){
    	
    	if (!gp.isPaused()) {  // Only update if game is not paused
            if(keyH.upPressed == true || keyH.downPressed == true ||
               keyH.leftPressed == true || keyH.rightPressed == true) {
               
		    	
		    	if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
		    		
		    		isMoving = true;
		    		
		    		int nextX = x;
		    	    int nextY = y;
		    		
		    		if(keyH.upPressed == true){
		    			nextY -= speed;
		    	        direction = "up";
		            }
		            else if(keyH.downPressed == true){
		            	nextY += speed;
		                direction = "down";
		            }
		            else if(keyH.leftPressed == true){
		            	nextX -= speed;
		                direction = "left";
		            }
		            else if(keyH.rightPressed == true){
		            	nextX += speed;
		                direction = "right";
		            }
		    		
		    		boolean canMove = false;
		    		if(gp.room1WelcomeMessageDisplayed == true && gp.welcomeMessageDisplayedComLab == true && gp.welcomeMessageDisplayedScience == true && gp.welcomeMessageDisplayedMathRoom == true ) {
		            	gp.finalLevel = true;
		            	if (gp.currentMap == MapState.ROOM) {
		                    canMove = !gp.room1.checkCollision(nextX, nextY);
		                    
		                    if (gp.room1.isDoor(nextX, nextY)) {
		                        gp.currentMap = MapState.HALLWAY2; 
		                        x = 215; 
		                        y = 350;
		                        return; 
		                    }
		                } else if (gp.currentMap == MapState.COMPUTERLAB) {
		                    canMove = !gp.comlab.checkCollision(nextX, nextY);
		
		                    if (gp.comlab.isDoor(nextX, nextY)) {
		                        gp.currentMap = MapState.HALLWAY2; 
		                        x = 745; 
		                        y = 350;
		                        return; 
		                    }
		                } else if (gp.currentMap == MapState.SCIENCEROOM) {
		                    canMove = !gp.science.checkCollision(nextX, nextY);
		
		                    if (gp.science.isDoor(nextX, nextY)) {
		                        gp.currentMap = MapState.HALLWAY2; 
		                        x = 215; 
		                        y = 460;
		                        return; 
		                    }
		                } else if (gp.currentMap == MapState.MATHROOM) {
		                    canMove = !gp.math.checkCollision(nextX, nextY);
		
		                    if (gp.math.isDoor(nextX, nextY)) {
		                        gp.currentMap = MapState.HALLWAY2; 
		                        x = 555; 
		                        y = 460;
		                        return; 
		                    }
		                }
		            	gp.finalLevel = true;
		            	
		    		}
		    		if (gp.finalLevel == true) {
		    			
		    			if (gp.currentMap == MapState.HALLWAY2) {
		                    canMove = !gp.hallway2.checkCollision(nextX, nextY);
		
		                    if (gp.hallway2.isRoomDoor(nextX, nextY)) {
		                        gp.currentMap = MapState.ROOM; 
		                        x = 215;
		                        y = 700;
		                        return; 
		                    } else if (gp.hallway2.isComLabDoor(nextX, nextY)) {
		                        gp.currentMap = MapState.COMPUTERLAB; 
		                        x = 215;
		                        y = 700;
		                        return; 
		                    } else if (gp.hallway2.isScienceDoor(nextX, nextY)) {
		                        gp.currentMap = MapState.SCIENCEROOM; 
		                        x = 265;
		                        y = 200;
		                        return; 
		                    } else if (gp.hallway2.isMathDoor(nextX, nextY)) {
		                        gp.currentMap = MapState.MATHROOM; 
		                        x = 310;
		                        y = 200;
		                        return; 
		                    } else if (gp.hallway2.isFacultyDoor(nextX, nextY)) {
		                        gp.currentMap = MapState.FACULTY; 
		                        x = 265;
		                        y = 200;                        
		                        return;                        
		                    }
		                } else if (gp.currentMap == MapState.ROOM) {
		                    canMove = !gp.room1.checkCollision(nextX, nextY);
		                    
		                    gp.room1.checkHint(nextX, nextY);
		
		                    if (gp.room1.isDoor(nextX, nextY)) {
		                        gp.currentMap = MapState.HALLWAY2; 
		                        x = 215; 
		                        y = 350;
		                        return; 
		                    }
		                } else if (gp.currentMap == MapState.COMPUTERLAB) {
		                    canMove = !gp.comlab.checkCollision(nextX, nextY);
		                    
		                    gp.comlab.checkHint(nextX, nextY);
		
		                    if (gp.comlab.isDoor(nextX, nextY)) {
		                        gp.currentMap = MapState.HALLWAY2; 
		                        x = 745; 
		                        y = 350;
		                        return; 
		                    }
		                } else if (gp.currentMap == MapState.SCIENCEROOM) {
		                    canMove = !gp.science.checkCollision(nextX, nextY);
		                    
		                    gp.science.checkHint(nextX, nextY);
		
		                    if (gp.science.isDoor(nextX, nextY)) {
		                        gp.currentMap = MapState.HALLWAY2; 
		                        x = 215; 
		                        y = 460;
		                        return; 
		                    }
		                } else if (gp.currentMap == MapState.MATHROOM) {
		                    canMove = !gp.math.checkCollision(nextX, nextY);
		                    
		                    gp.math.checkHint(nextX, nextY);
		
		                    if (gp.math.isDoor(nextX, nextY)) {
		                        gp.currentMap = MapState.HALLWAY2; 
		                        x = 555; 
		                        y = 460;
		                        return; 
		                    }
		                } else if (gp.currentMap == MapState.FACULTY) {
		                    
							if (gp.faculty.quizBattle.winner == true && gp.faculty.showQuestion == false) {
								gp.currentMap = MapState.LOCKERROOM; 
								x = 200;
								y = 415;
								return; 
							}
							
							canMove = !gp.faculty.checkCollision(nextX, nextY);

		                    gp.faculty.update(nextX, nextY);
		                    
		
		                    if (gp.faculty.isDoor(nextX, nextY)) {
		                        gp.currentMap = MapState.HALLWAY2; 
		                    
		                        x = 940; 
		                        y = 460;
		                        return; 
		                    }
		                } else if (gp.currentMap == MapState.LOCKERROOM) {
		                    canMove = !gp.locker.checkCollision(nextX, nextY);
		                    
		                    gp.locker.checkGirl(nextX, nextY);
		
		                    if (gp.locker.isDoor(nextX, nextY)) {
		                        gp.currentMap = MapState.FACULTY; 
		                        x = 810; 
		                        y = 415;
		                        return; 
		                    }
		                }
		    			
		    		}
		
		    		if (gp.finalLevel == false) {
		    			
		    			if (gp.currentMap == MapState.HALLWAY) {
		                    canMove = !gp.hallway.checkCollision(nextX, nextY);
		
		                    if (gp.hallway.isRoomDoor(nextX, nextY)) {
		                        gp.currentMap = MapState.ROOM; 
		                        x = 215;
		                        y = 700;
		                        return; 
		                    } else if (gp.hallway.isComLabDoor(nextX, nextY)) {
		                        gp.currentMap = MapState.COMPUTERLAB; 
		                        x = 215;
		                        y = 700;
		                        return; 
		                    } else if (gp.hallway.isScienceDoor(nextX, nextY)) {
		                        gp.currentMap = MapState.SCIENCEROOM; 
		                        x = 265;
		                        y = 200;
		                        return; 
		                    } else if (gp.hallway.isMathDoor(nextX, nextY)) {
		                        gp.currentMap = MapState.MATHROOM; 
		                        x = 310;
		                        y = 200;
		                        return; 
		                    }
		                } else if (gp.currentMap == MapState.ROOM) {
		                    canMove = !gp.room1.checkCollision(nextX, nextY);
		                    
		                    gp.room1.checkHint(nextX, nextY);
		                    if (gp.room1.isDoor(nextX, nextY)) {
		                        gp.currentMap = MapState.HALLWAY; 
		                        x = 215; 
		                        y = 350;
		                        return; 
		                    }
		                } else if (gp.currentMap == MapState.COMPUTERLAB) {
		                    canMove = !gp.comlab.checkCollision(nextX, nextY);
		                    
		                    gp.comlab.checkHint(nextX, nextY);
		
		                    if (gp.comlab.isDoor(nextX, nextY)) {
		                        gp.currentMap = MapState.HALLWAY; 
		                        x = 745; 
		                        y = 350;
		                        return; 
		                    }
		                } else if (gp.currentMap == MapState.SCIENCEROOM) {
		                    canMove = !gp.science.checkCollision(nextX, nextY);
		                    
		                    gp.science.checkHint(nextX, nextY);
		                    
		                    if (gp.science.isDoor(nextX, nextY)) {
		                        gp.currentMap = MapState.HALLWAY; 
		                        x = 215; 
		                        y = 460;
		                        return; 
		                    }
		                } else if (gp.currentMap == MapState.MATHROOM) {
		                    canMove = !gp.math.checkCollision(nextX, nextY);
		                    
		                    gp.math.checkHint(nextX, nextY);
		
		                    if (gp.math.isDoor(nextX, nextY)) {
		                        gp.currentMap = MapState.HALLWAY; 
		                        x = 555; 
		                        y = 460;
		                        return; 
		                    }
		                }
		    			
		    		}
		
		    		if (canMove) {
		    		    x = nextX;
		    		    y = nextY;
		    		}
		    		
		            spriteCounter++;
		            if(spriteCounter > 10) {
		            	if(spriteNum == 1) {
		            		spriteNum = 2;
		            	}
		            	else if(spriteNum == 2) {
		            		spriteNum = 1;
		            	}
		            	spriteCounter = 0;
		            }
		    	} else {
		    		isMoving = false;
		    	}
    	
            }
            
    	}
    }
    public void draw(Graphics2D g2){
        
    	BufferedImage image = null;
    	
    	switch(direction) {
    	case "up":
    		
    		if(isMoving == true) {
    			if(spriteNum == 1) {
        			image = up1;
        		}
        		if(spriteNum == 2) {
        			image = up2;
        		}
    		} else {
    			image = up;
    		}
    		break;
    	case "down":
    		if(isMoving == true) {
    			if(spriteNum == 1) {
        			image = down1;
        		}
        		if(spriteNum == 2) {
        			image = down2;
        		}
    		} else {
    			image = down;
    		}
    		break;
    	case "left":
    		if(isMoving == true) {
    			if(spriteNum == 1) {
        			image = left1;
        		}
        		if(spriteNum == 2) {
        			image = left2;
        		}
    		} else {
    			image = left;
    		}
    		break;
    	case "right":
    		if(isMoving == true) {
    			if(spriteNum == 1) {
        			image = right1;
        		}
        		if(spriteNum == 2) {
        			image = right2;
        		}
    		} else {
    			image = right;
    		}
    		break;
    	}
    	g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);	
    	
    }
    public Rectangle getHitbox() {
        return new Rectangle(x, y, gp.tileSize, gp.tileSize);
    }
    
}
