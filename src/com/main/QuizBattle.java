package com.main;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class QuizBattle {
	
	GamePanel gp;
	
	private BufferedImage bossSprite3;
	private BufferedImage bossSprite2;
	private BufferedImage bossSprite1;
	private BufferedImage bossSprite0;
    private BufferedImage characterSprite3;
    private BufferedImage characterSprite2;
    private BufferedImage characterSprite1;
    private BufferedImage characterSprite0;
    private BufferedImage questionImage, questionImage2, questionImage3, questionImage4, questionImage5;
    private BufferedImage[] choiceImages, choiceImages2, choiceImages3, choiceImages4, choiceImages5;
    private Rectangle[] choiceAreas, choiceAreas2, choiceAreas3, choiceAreas4,  choiceAreas5;
    private int selectedChoice = -1;
    private int selectedChoice2 = -1;
    private int selectedChoice3 = -1;
    private int selectedChoice4 = -1;
    private int selectedChoice5 = -1;
    public int mcLife = 3;
    public int bossLife = 3;
    public int questionNumber = 1;
    public boolean defeated;
    public boolean winner;


    public QuizBattle(String questionImagePath, String[] choiceImagePaths, 
    		String questionImagePath2, String[] choiceImagePaths2, 
    		String questionImagePath3, String[] choiceImagePaths3,
    		String questionImagePath4, String[] choiceImagePaths4,
    		String questionImagePath5, String[] choiceImagePaths5) {
        try {
            bossSprite3 = ImageIO.read(getClass().getResourceAsStream("/quiz/BOSS3LIFE.png"));
            bossSprite2 = ImageIO.read(getClass().getResourceAsStream("/quiz/BOSS2LIFE.png"));
            bossSprite1 = ImageIO.read(getClass().getResourceAsStream("/quiz/BOSS1LIFE.png"));
            bossSprite0 = ImageIO.read(getClass().getResourceAsStream("/quiz/BOSS0LIFE.png"));
            characterSprite3 = ImageIO.read(getClass().getResourceAsStream("/quiz/MC3LIFE.png"));
            characterSprite2 = ImageIO.read(getClass().getResourceAsStream("/quiz/MC2LIFE.png"));
            characterSprite1 = ImageIO.read(getClass().getResourceAsStream("/quiz/MC1LIFE.png"));
            characterSprite0 = ImageIO.read(getClass().getResourceAsStream("/quiz/MC0LIFE.png"));
            questionImage = ImageIO.read(getClass().getResourceAsStream(questionImagePath));
            choiceImages = new BufferedImage[choiceImagePaths.length];
            choiceAreas = new Rectangle[choiceImagePaths.length];
            
            questionImage2 = ImageIO.read(getClass().getResourceAsStream(questionImagePath2));
            choiceImages2 = new BufferedImage[choiceImagePaths2.length];
            choiceAreas2 = new Rectangle[choiceImagePaths2.length];
            
            questionImage3 = ImageIO.read(getClass().getResourceAsStream(questionImagePath3));
            choiceImages3 = new BufferedImage[choiceImagePaths3.length];
            choiceAreas3 = new Rectangle[choiceImagePaths3.length];
            
            questionImage4 = ImageIO.read(getClass().getResourceAsStream(questionImagePath4));
            choiceImages4 = new BufferedImage[choiceImagePaths4.length];
            choiceAreas4 = new Rectangle[choiceImagePaths4.length];
            
            questionImage5 = ImageIO.read(getClass().getResourceAsStream(questionImagePath5));
            choiceImages5 = new BufferedImage[choiceImagePaths5.length];
            choiceAreas5 = new Rectangle[choiceImagePaths5.length];

            for (int i = 0; i < choiceImagePaths.length; i++) {
                choiceImages[i] = ImageIO.read(getClass().getResourceAsStream(choiceImagePaths[i]));
            }
            for (int i = 0; i < choiceImagePaths2.length; i++) {
                choiceImages2[i] = ImageIO.read(getClass().getResourceAsStream(choiceImagePaths2[i]));
            }
            for (int i = 0; i < choiceImagePaths3.length; i++) {
                choiceImages3[i] = ImageIO.read(getClass().getResourceAsStream(choiceImagePaths3[i]));
            }
            for (int i = 0; i < choiceImagePaths4.length; i++) {
                choiceImages4[i] = ImageIO.read(getClass().getResourceAsStream(choiceImagePaths4[i]));
            }
            for (int i = 0; i < choiceImagePaths5.length; i++) {
                choiceImages5[i] = ImageIO.read(getClass().getResourceAsStream(choiceImagePaths5[i]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void draw(Graphics2D g2, int screenWidth, int screenHeight) {
        int dialogHeight = screenHeight / 4;
        int spriteSize = 300;
        

        if(questionNumber == 1) {
        	int bossX = screenWidth - spriteSize - 20; 
            int bossY = screenHeight - spriteSize - 20; 
            g2.drawImage(bossSprite3, bossX, bossY, spriteSize, spriteSize, null);

           
            int characterX = 20; 
            int characterY = screenHeight - spriteSize - 20;
            g2.drawImage(characterSprite3, characterX, characterY, spriteSize, spriteSize, null);

            int questionX = (screenWidth - questionImage.getWidth()) / 2;
            int questionY = 150; 
            g2.drawImage(questionImage, questionX, questionY, null);

            int choiceX = (screenWidth - choiceImages[0].getWidth()) / 2; 
            int choiceY = questionY + questionImage.getHeight() + 20; 
            for (int i = 0; i < choiceImages.length; i++) {
                g2.drawImage(choiceImages[i], choiceX, choiceY, null);

                choiceAreas[i] = new Rectangle(choiceX, choiceY, choiceImages[i].getWidth(), choiceImages[i].getHeight());

                if (i == selectedChoice) {
                    g2.drawRect(choiceX - 2, choiceY - 2, choiceImages[i].getWidth() + 4, choiceImages[i].getHeight() + 4);
                }

                choiceY += choiceImages[i].getHeight() + 10; 
            }
        } 
        if(questionNumber == 2) {
        	
        	int bossX = screenWidth - spriteSize - 20; 
            int bossY = screenHeight - spriteSize - 20; 
            if(bossLife == 2) {
            	g2.drawImage(bossSprite2, bossX, bossY, spriteSize, spriteSize, null);
        	} else {
        		g2.drawImage(bossSprite3, bossX, bossY, spriteSize, spriteSize, null);
        	}

            int characterX = 20; 
            int characterY = screenHeight - spriteSize - 20;
            if(mcLife == 2) {
            	g2.drawImage(characterSprite2, characterX, characterY, spriteSize, spriteSize, null);
        	} else {
        		g2.drawImage(characterSprite3, characterX, characterY, spriteSize, spriteSize, null);
        	}

            int questionX = (screenWidth - questionImage.getWidth()) / 2;
            int questionY = 150; 
            g2.drawImage(questionImage2, questionX, questionY, null);

            int choiceX = (screenWidth - choiceImages2[0].getWidth()) / 2; 
            int choiceY = questionY + questionImage2.getHeight() + 20; 
            for (int i = 0; i < choiceImages2.length; i++) {
                g2.drawImage(choiceImages2[i], choiceX, choiceY, null);

                choiceAreas2[i] = new Rectangle(choiceX, choiceY, choiceImages2[i].getWidth(), choiceImages2[i].getHeight());

                if (i == selectedChoice2) {
                    g2.drawRect(choiceX - 2, choiceY - 2, choiceImages2[i].getWidth() + 4, choiceImages2[i].getHeight() + 4);
                }

                choiceY += choiceImages2[i].getHeight() + 10; 
            }
        }
        if(questionNumber == 3) {
        	
        	int bossX = screenWidth - spriteSize - 20; 
            int bossY = screenHeight - spriteSize - 20; 
            if(bossLife == 2) {
            	g2.drawImage(bossSprite2, bossX, bossY, spriteSize, spriteSize, null);
        	} else if(bossLife == 1) {
            	g2.drawImage(bossSprite1, bossX, bossY, spriteSize, spriteSize, null);
        	} else {
        		g2.drawImage(bossSprite3, bossX, bossY, spriteSize, spriteSize, null);
        	}

            int characterX = 20; 
            int characterY = screenHeight - spriteSize - 20;
            if(mcLife == 2) {
            	g2.drawImage(characterSprite2, characterX, characterY, spriteSize, spriteSize, null);
        	}else if(mcLife == 1) {
            	g2.drawImage(characterSprite1, characterX, characterY, spriteSize, spriteSize, null);
        	} else {
        		g2.drawImage(characterSprite3, characterX, characterY, spriteSize, spriteSize, null);
        	}

            int questionX = (screenWidth - questionImage.getWidth()) / 2;
            int questionY = 150; 
            g2.drawImage(questionImage3, questionX, questionY, null);

            int choiceX = (screenWidth - choiceImages3[0].getWidth()) / 2; 
            int choiceY = questionY + questionImage3.getHeight() + 20; 
            for (int i = 0; i < choiceImages3.length; i++) {
                g2.drawImage(choiceImages3[i], choiceX, choiceY, null);

                choiceAreas3[i] = new Rectangle(choiceX, choiceY, choiceImages3[i].getWidth(), choiceImages3[i].getHeight());

                if (i == selectedChoice3) {
                    g2.drawRect(choiceX - 2, choiceY - 2, choiceImages3[i].getWidth() + 4, choiceImages3[i].getHeight() + 4);
                }

                choiceY += choiceImages3[i].getHeight() + 10; 
            }
        }
        
        if(questionNumber == 4) {
        	
        	int bossX = screenWidth - spriteSize - 20; 
            int bossY = screenHeight - spriteSize - 20; 
            if(bossLife == 2) {
            	g2.drawImage(bossSprite2, bossX, bossY, spriteSize, spriteSize, null);
        	} else if(bossLife == 1) {
            	g2.drawImage(bossSprite1, bossX, bossY, spriteSize, spriteSize, null);
        	} else if(bossLife == 0) {        		
            	g2.drawImage(bossSprite0, bossX, bossY, spriteSize, spriteSize, null);
            	winner = true;
        	} else {
        		g2.drawImage(bossSprite3, bossX, bossY, spriteSize, spriteSize, null);
        	}

            int characterX = 20; 
            int characterY = screenHeight - spriteSize - 20;
            if(mcLife == 2) {
            	g2.drawImage(characterSprite2, characterX, characterY, spriteSize, spriteSize, null);
        	}else if(mcLife == 1) {
            	g2.drawImage(characterSprite1, characterX, characterY, spriteSize, spriteSize, null);
        	} else if(mcLife == 0) {
        		defeated = true;
            	g2.drawImage(characterSprite0, characterX, characterY, spriteSize, spriteSize, null);
        	} else {
        		g2.drawImage(characterSprite3, characterX, characterY, spriteSize, spriteSize, null);
        	}

            int questionX = (screenWidth - questionImage.getWidth()) / 2;
            int questionY = 150; 
            g2.drawImage(questionImage4, questionX, questionY, null);

            int choiceX = (screenWidth - choiceImages4[0].getWidth()) / 2; 
            int choiceY = questionY + questionImage4.getHeight() + 20; 
            for (int i = 0; i < choiceImages4.length; i++) {
                g2.drawImage(choiceImages4[i], choiceX, choiceY, null);

                choiceAreas4[i] = new Rectangle(choiceX, choiceY, choiceImages4[i].getWidth(), choiceImages4[i].getHeight());

                if (i == selectedChoice4) {
                    g2.drawRect(choiceX - 2, choiceY - 2, choiceImages4[i].getWidth() + 4, choiceImages4[i].getHeight() + 4);
                }

                choiceY += choiceImages4[i].getHeight() + 10; 
            }
        }
        if(questionNumber == 5) {
        	
        	int bossX = screenWidth - spriteSize - 20; 
            int bossY = screenHeight - spriteSize - 20; 
            if(bossLife == 2) {
            	g2.drawImage(bossSprite2, bossX, bossY, spriteSize, spriteSize, null);
        	} else if(bossLife == 1) {
            	g2.drawImage(bossSprite1, bossX, bossY, spriteSize, spriteSize, null);
        	} else if(bossLife == 0) {
        		winner = true;
            	g2.drawImage(bossSprite0, bossX, bossY, spriteSize, spriteSize, null);
        	} else {
        		g2.drawImage(bossSprite3, bossX, bossY, spriteSize, spriteSize, null);
        	}

            int characterX = 20; 
            int characterY = screenHeight - spriteSize - 20;
            if(mcLife == 2) {
            	g2.drawImage(characterSprite2, characterX, characterY, spriteSize, spriteSize, null);
        	}else if(mcLife == 1) {
            	g2.drawImage(characterSprite1, characterX, characterY, spriteSize, spriteSize, null);
        	} else if(mcLife == 0) {
        		defeated = true;
            	g2.drawImage(characterSprite0, characterX, characterY, spriteSize, spriteSize, null);
        	} else {
        		g2.drawImage(characterSprite3, characterX, characterY, spriteSize, spriteSize, null);
        	}

            int questionX = (screenWidth - questionImage.getWidth()) / 2;
            int questionY = 150; 
            g2.drawImage(questionImage5, questionX, questionY, null);

            int choiceX = (screenWidth - choiceImages5[0].getWidth()) / 2; 
            int choiceY = questionY + questionImage5.getHeight() + 20; 
            for (int i = 0; i < choiceImages5.length; i++) {
                g2.drawImage(choiceImages5[i], choiceX, choiceY, null);

                choiceAreas5[i] = new Rectangle(choiceX, choiceY, choiceImages5[i].getWidth(), choiceImages5[i].getHeight());

                if (i == selectedChoice5) {
                    g2.drawRect(choiceX - 2, choiceY - 2, choiceImages5[i].getWidth() + 4, choiceImages5[i].getHeight() + 4);
                }

                choiceY += choiceImages5[i].getHeight() + 10; 
            }
        }
    }
    
    public int handleClick(int mouseX, int mouseY) {
        for (int i = 0; i < choiceAreas.length; i++) {
            if (choiceAreas[i] != null && choiceAreas[i].contains(mouseX, mouseY)) {
                selectedChoice = i; 
                return i; 
            }
        }
        return -1; 
    }

    public int handleClick2(int mouseX, int mouseY) {
        for (int i = 0; i < choiceAreas2.length; i++) {
            if (choiceAreas2[i] != null && choiceAreas2[i].contains(mouseX, mouseY)) {
                selectedChoice2 = i; 
                return i;
            }
        }
        return -1; 
    }
    public int handleClick3(int mouseX, int mouseY) {
        for (int i = 0; i < choiceAreas3.length; i++) {
            if (choiceAreas3[i] != null && choiceAreas3[i].contains(mouseX, mouseY)) {
                selectedChoice3 = i; 
                return i; 
            }
        }
        return -1; 
    }
    public int handleClick4(int mouseX, int mouseY) {
        for (int i = 0; i < choiceAreas4.length; i++) {
            if (choiceAreas4[i] != null && choiceAreas4[i].contains(mouseX, mouseY)) {
                selectedChoice4 = i; 
                return i; 
            }
        }
        return -1; 
    }
    public int handleClick5(int mouseX, int mouseY) {
        for (int i = 0; i < choiceAreas5.length; i++) {
            if (choiceAreas5[i] != null && choiceAreas5[i].contains(mouseX, mouseY)) {
                selectedChoice5 = i; 
                return i; 
            }
        }
        return -1; 
    }

    public int getSelectedChoice() {
        return selectedChoice;
    }

    public void reset() {
        mcLife = 3;
        bossLife = 3;
        questionNumber = 1;
        selectedChoice = -1;
        selectedChoice2 = -1;
        selectedChoice3 = -1;
        selectedChoice4 = -1;
        selectedChoice5 = -1;
        defeated = false;
        winner = false;
    }
    
    public void checkWinCondition() {
        if (bossLife == 0) {
            winner = true;
        }
    }

}
