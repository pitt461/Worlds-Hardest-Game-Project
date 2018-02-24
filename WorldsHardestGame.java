//Tevin Stanley
//December 8, 2017
//TA: Lisa Vo and Courtney McKee
//Assignment FINAL PROJECT: WorldsHardestGame.java
//Game where player must make the red square reach the opposite side of the field
//but must avoid the blue objects, or they must restart.
//The controls for the movement are the 'W','A','S','D' keys.
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

public class WorldsHardestGame {
   public static final int safePositionX = 250;//the starting spawn of player's x coordinate
   public static final int safePositionY = 500;//the starting spawn of player's y coordinate
   public static final int playerMv = 20;
   public static final int FRAMERATE = 40;
   public static final int widthNheight = 100;
   public static InteractiveKeyListener listener;
   
   public static void main(String[] args) { 
      int finalX = safePositionX;
      int finalY = safePositionY;
      int deathCounter = 0;
      Scanner input = new Scanner(System.in);
      boolean win = false;
      DrawingPanel panel = new DrawingPanel(2000,1000);
      panel.setBackground(Color.lightGray);
      drawBackground(panel);
      Graphics g = panel.getGraphics();
      g.setColor(Color.RED);
      
   // listen for key presses
      listener = new InteractiveKeyListener(panel);
      panel.addKeyListener(listener);
      
      //creating my 5 ball objects
      Ball b1 = new Ball(600, 500, 1);
      Ball b2 = new Ball(800, 500, -1);
      Ball b3 = new Ball(1000, 500, 1);
      Ball b4 = new Ball(1200, 500, -1);
      Ball b5 = new Ball(1400, 500, 1);
      Ball[] myBalls = new Ball[]{b1,b2,b3,b4,b5};//array containing my ball objects
      Color gold = new Color(255, 215, 0);
   
      
      //while the player has not won the game yet   
      while(win == false) {
         panel.sleep( 700 / FRAMERATE);//helps drawing panel not look choppy.
         drawBackground(panel);//redraws background to game
         finalX = playerMovementX(panel, finalX);//uses 'a' & 'd' keys to change x value
         finalY = playerMovementY(panel, finalY);//uses 'w' & 's' keys to change y value
         if(finalX <= 300) {
            if(finalX < 200) {
               finalX = 200;
            }
            if(finalY < 300) {
               finalY = 300;
            }
            if(finalY > 700) {
               finalY = 700;
            }   
         }
           
         if((finalX >= 400) && (finalX <= 1600)) {//checking to see if it reaches top border.
            if(finalY  < 100) {
               finalY += playerMv;
            }
            if(finalY + widthNheight > 900) {
               finalY -= playerMv;
            }
         }
       
         if((finalY >= 100) && (finalY  <= 300)) {//checking side 2 and 11 for X leaving the border
            finalX = xInBox(finalX); 
         }
       
         if((finalY > 600) && (finalY  <= 900)) {//checking side
            finalX = xInBox(finalX);
         } 
         win = winCondition(finalX, finalY);         
         g.setColor(Color.RED);
         drawPlayer(panel, finalX, finalY);
         g.setColor(Color.BLUE);
         for(int i = 0; i < myBalls.length; i++) { // refrencing my array of balls.
            g.fillOval(myBalls[i].getX(), myBalls[i].getY(), myBalls[i].getDiameter(), myBalls[i].getDiameter());
            if(myBalls[i].getY() + myBalls[i].getDiameter() > 900) {
               myBalls[i].signChange();
            }
            if(myBalls[i].getY() < 110) {
               myBalls[i].signChange();
            }
            myBalls[i].yIncrement();
            if(checkAllX(finalX, finalY, myBalls[i]) || checkAllX(finalX, finalY + widthNheight, myBalls[i]) ||
            checkAllY(finalX, finalY, myBalls[i]) || checkAllY(finalX + widthNheight, finalY, myBalls[i]) == true) {
               finalX = safePositionX;
               finalY = safePositionY;
               deathCounter++;
            }
         }         
      
         g.setColor(Color.BLACK);
         g.setFont(new Font("Arial", Font.BOLD, 30));
         g.drawString("DEATH COUNT: " + deathCounter, 1330, 150);//prints death counter in top right corner
      }
      g.setColor(gold);
      g.setFont(new Font("Serif", Font.BOLD, 50));
      g.drawString("YOU WON!", 870, 460); 
      
             
   }
   
//moves the X value of the red square depending on what key the user inputs, AD      
   public static int playerMovementX(DrawingPanel myPanel,int moving) {
      int code = listener.getKeyCode();
      if (code == KeyEvent.VK_A) {
         moving -= playerMv;
      }
      if (code == KeyEvent.VK_D) {
         moving += playerMv;
      }
      return moving;
      
   }
   
//moves the Y value of the red square depending on what key the user inputs, WS
   public static int playerMovementY(DrawingPanel myPanel,int moving) {
      int code = listener.getKeyCode();
      if (code == KeyEvent.VK_W) {
         moving -= playerMv;
      }
      if (code == KeyEvent.VK_S) {
         moving += playerMv;
      }
      return moving;
   }
   
   //makes all X's that are inside the main box stay within the main box.
   public static int xInBox(int x) {
      if(x < 400 ) {
         x = 400;
      }  
      if(x + widthNheight > 1600) {
         x -= playerMv;
      } 
      return x;
   }

//draws the background of the game. Ex. Safe zones, white field with black borders.   
   public static void drawBackground(DrawingPanel myPanel) {
      Graphics g = myPanel.getGraphics();
      g.setColor(Color.BLACK);
      g.fillRect(395, 95, 1210, 5);//top border of game
      g.fillRect(395, 100, 5, 200);//left border above safe zone
      g.fillRect(195, 295, 200, 5);// border above left safe zone
      g.fillRect(195, 300, 5, 405);//border on left of the left green zone
      g.fillRect(200, 700, 200, 5);//border on bottom of left safe zone
      g.fillRect(395, 705, 5, 200);//border on bottom left, bellow the green safe zone
      g.fillRect(400, 900, 1205, 5);//very bottom border of the game
      g.fillRect(1600, 700, 5, 205);//bottom right border, under the right safe zone.
      g.fillRect(1605, 700, 200, 5);//border on right under the right safe zone.  
      g.fillRect(1800, 295, 5, 405);//furthest right border.
      g.fillRect(1600, 295, 205, 5);//border on top touching right safe zone.
      g.fillRect(1600, 100, 5, 195); //top right border above the right safe zone.
      Color safeZone = new Color(149, 255, 167);//light green color for safe zone
      g.setColor(Color.WHITE);
      g.fillRect(400, 100, 1200, 800);//border of game
      g.setColor(safeZone);
      g.fillRect(200, 300, 200, 400);// left safe zone
      g.fillRect(1600, 300, 200, 400);// Right safe zone
   }

//Draws red square at given location x and y 
   public static void drawPlayer(DrawingPanel myPanel, int myX, int myY ) {
      Graphics g = myPanel.getGraphics();
      g.fillRect(myX, myY, widthNheight, widthNheight);//drawing
     
   }
//checks if the x and y are within the unsafe zone of the game. returns true or false  
   public static boolean checkMainBox(int x, int y) {
      if((x > 400) && (x < 1600) && (y >= 100) && (y <= 900)) {
         return true;   
      }
      else 
         return false;
   }
   
   //checks if player made it to the end goal using x, y location, if they won and will return true if they did
   public static boolean winCondition(int x, int y) {
      if((x + widthNheight > 1600) && (y >= 300) &&(y <= 700)) {
         return true;
      }
      return false;
   }
   
//Checks if the given x and y value is within the radius of a given ball. returns true false.  
   public static boolean check(int x, int y, Ball b) {
      if((((x - b.centerX())*(x - b.centerX())) + ((y - b.centerY())) * (y - b.centerY())) <= (b.getRadius()*b.getRadius())) {
         return true;
      }
      return false;   
   } 

//Checks all values of the top or bottom side of a square to see if it touched the ball   
   public static boolean checkAllX(int x, int y, Ball b) {
      boolean hit = false;
      for(int i = 0; i < widthNheight; i++) {
         if(check(x+i, y, b) == true) {
            hit = true;
         }
      }
      return hit;
   }

//Checks all values of the left or right side of a square to see if it touched the ball  
   public static boolean checkAllY(int x, int y, Ball b) {
      boolean hit = false;
      for(int i = 0; i < widthNheight; i++) {
         if(check(x, y + i, b) == true) {
            hit = true;
         }
      }
      return hit;
   }   
   
   

   /** A class for responding to key presses on the drawing panel.
    */
   public static class InteractiveKeyListener extends KeyAdapter 
   {
      /**
       * A private field to store the drawing panel for access later.
       */
      private DrawingPanel panel;
      
      /*
       * The point that was hit
       */
      private int keyCode = 0;  
       
            
      /**
       * Constructs the InteractivePanel's Mouse listener
       * and stores a reference to the panel itself for use later
       *
       * @param panel A reference to the DrawingPanel we're using
       */   
      public InteractiveKeyListener(DrawingPanel panel) 
      {
         this.panel = panel;
      }
      
      
      /**
       * Returns the KeyCode of the last key that was hit.
       *
       * @return the KeyCode of the last key that was hit. 
       */      
      public int getKeyCode()
      {
         return keyCode;
      }
   
      /**
       * This will respond to a key pressed event.  
       *
       * @param event A KeyEvent that is sent by the system
       * that contains information we'll need to process. 
       */ 
      public void keyPressed(KeyEvent event) 
      {
         keyCode = event.getKeyCode();
      }
      
      /**
       * This will respond to a key released event which will set the keycode back to 0.  
       *
       * @param event A KeyEvent that is sent by the system
       * that contains information we'll need to process. 
       */ 
      public void keyReleased(KeyEvent event) 
      {
         keyCode = 0;
      }
   }
}

