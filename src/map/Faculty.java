package map;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.main.GamePanel;
import com.main.QuizBattle;


public class Faculty implements MouseListener{
	
	private BufferedImage mapImage;
	private BufferedImage quizBackGround;
    public boolean[][] collisionMap;
    private int tileSize;
    
    private boolean welcomeMessageDisplayedFaculty = false;
    public QuizBattle quizBattle;
    public boolean showQuestion = false;

    public Faculty (String mapPath, int tileSize) {

        this.tileSize = tileSize;

        try {
            mapImage = ImageIO.read(getClass().getResourceAsStream(mapPath));
            initializeCollisionMap();
            
            quizBackGround = ImageIO.read(getClass().getResourceAsStream("/quiz/quizbb.jpg"));
            
            quizBattle = new QuizBattle(
                    "/quiz/1stQ.png", 
                    new String[]{"/quiz/1A.png", "/quiz/1B.png", "/quiz/1C.png"},
                    "/quiz/2ndQ.png", 
                    new String[]{"/quiz/2A.png", "/quiz/2B.png", "/quiz/2C.png"},
                    "/quiz/3rdQ.png", 
                    new String[]{"/quiz/3A.png", "/quiz/3B.png", "/quiz/3C.png"},
                    "/quiz/4thQ.png", 
                    new String[]{"/quiz/4A.png", "/quiz/4B.png", "/quiz/4C.png"},
                    "/quiz/5thQ.png", 
                    new String[]{"/quiz/5A.png", "/quiz/5B.png", "/quiz/5C.png"}
                    
            );

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
            { true, true, true, false, false, false, false, true, true, true, true, false, true, false, false, false, false, true, true, true, true, true },
            { true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true },
            { true, true, true, false, false, false, false, true, true, true, true, true, true, true, true, true, false, true, true, true, true, true },
            { true, true, true, false, false, false, false, true, true, true, true, true, true, true, true, true, false, true, true, true, true, true },
            { true, true, true, false, true, true, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true },
            { true, true, true, false, true, true, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true },
            { true, true, true, false, true, true, false, true, true, true, true, true, true, true, true, true, false, true, true, true, true, true },
            { true, true, true, false, false, false, false, true, true, true, true, true, true, true, true, true, false, true, true, true, true, true },
            { true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true },
            { true, true, true, false, false, false, true, true, true, true, false, true, true, true, false, false, false, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
            { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true },
        };
    }
    
    public boolean isDoor(int x, int y) {
        int col = x / tileSize;
        int row = y / tileSize;
        
        if (5 == col && 3 == row) {
            return true;
        }
        return false;
    }
    
    public boolean isBoss(int x, int y) {
        int col = x / tileSize;
        int row = y / tileSize;
        
        if (17 == col && 8 == row) {
            return true;
        }
        return false;
    }
    
    public boolean isLockerDoor(int x, int y) {
        int col = x / tileSize;
        int row = y / tileSize;
        
        if (17 == col && 8 == row) {
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
        if (!welcomeMessageDisplayedFaculty) {
            gamePanel.addDialogue("Welcome to the Faculty Room");
            welcomeMessageDisplayedFaculty = true;
        }
    }

    public void draw(Graphics2D g2, int screenWidth, int screenHeight) {
    	if(showQuestion == false) {
    		g2.drawImage(mapImage, 0, 0, screenWidth, screenHeight, null);
    	}
        
        if (showQuestion) {
        	g2.drawImage(quizBackGround, 0, 0, screenWidth, screenHeight, null);
        	quizBattle.draw(g2, screenWidth, screenHeight);
        }
    }
    
    public void update(int playerX, int playerY) {
        if (isBoss(playerX, playerY)) {
            showQuestion = true;
        }
        if(quizBattle.bossLife <= 0) {
            quizBattle.winner = true;
            showQuestion = false;
            return; 
        }
    }
    
    public void mouseClicked(MouseEvent e) {
        if (showQuestion) {
        	
        	if (quizBattle.bossLife == 0) {
                quizBattle.winner = true;
                showQuestion = false;
                return;
            }
        	
            if (quizBattle.mcLife == 0) {
            	quizBattle.reset();
            	showQuestion = false;
            	return;
            }
            
            if (!quizBattle.winner) {
                
	            if (quizBattle.questionNumber == 1) {
	                int choice = quizBattle.handleClick(e.getX(), e.getY());
	                if (choice == 0) {
	                    quizBattle.bossLife--; 
	                    quizBattle.checkWinCondition();
	                } else if (choice != -1) {
	                    quizBattle.mcLife--;
	                }
	                quizBattle.questionNumber++;
	            } else if (quizBattle.questionNumber == 2) {
	                int choice2 = quizBattle.handleClick2(e.getX(), e.getY());
	                if (choice2 == 2) {
	                    quizBattle.bossLife--; 
	                    quizBattle.checkWinCondition();
	                } else if (choice2 != -1) {
	                    quizBattle.mcLife--;
	                }
	                quizBattle.questionNumber++;
	            } else if (quizBattle.questionNumber == 3) {
	                int choice3 = quizBattle.handleClick3(e.getX(), e.getY());
	                if (choice3 == 0) {
	                    quizBattle.bossLife--; 
	                    quizBattle.checkWinCondition();
	                } else if (choice3 != -1) {
	                    quizBattle.mcLife--;
	                }
	                quizBattle.questionNumber++;
	            } else if (quizBattle.questionNumber == 4) {
	                int choice4 = quizBattle.handleClick4(e.getX(), e.getY());
	                if (choice4 == 1) {
	                    quizBattle.bossLife--; 
	                    quizBattle.checkWinCondition();
	                } else if (choice4 != -1) {
	                    quizBattle.mcLife--;
	                }
	                quizBattle.questionNumber++;
	            } else if (quizBattle.questionNumber == 5) {
	                int choice5 = quizBattle.handleClick5(e.getX(), e.getY());
	                if (choice5 == 1) {
	                    quizBattle.bossLife--; 
	                    quizBattle.checkWinCondition();
	                } else if (choice5 != -1) {
	                    quizBattle.mcLife--;
	                }
	                quizBattle.questionNumber++;
	            } 
            }
        }
    }

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
    
    
    
    

}
