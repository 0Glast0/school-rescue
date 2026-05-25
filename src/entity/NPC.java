
package entity;

import java.awt.Rectangle;
import java.awt.Graphics2D;

public class NPC {
    private int x, y, width, height;
    private boolean dialogueActive = false;  
    private long dialogueStartTime = 0;  
    private final long DIALOGUE_DURATION = 1500; 

    public NPC(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle getHitbox() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(java.awt.Color.RED); 
        g2.fillOval(x, y, width, height); 
    }

    
    public String onPlayerMeet() {
        return "This door is locked!"; 
    }

   
    public void setDialogueActive(boolean active) {
        this.dialogueActive = active;
    }

  
    public boolean isDialogueActive() {
        return dialogueActive;
    }

  
    public void startDialogueTimer() {
        dialogueStartTime = System.currentTimeMillis();
    }

 
    public void updateDialogueState() {
        if (dialogueActive && System.currentTimeMillis() - dialogueStartTime > DIALOGUE_DURATION) {
            dialogueActive = false; 
        }
    }
}
