//Tevin Stanley
//December 8, 2017
//TA: Lisa Vo and Courtney McKee
//Assignment FINAL PROJECT: Ball.java
//Creates a "ball" object and has functions to give main class information.

public class Ball {
   private int trueX= 0;
   private int trueY = 0;
   private int diameter = 75;//diameter of all balls
   private int increment = 15;//Speed of ball
   private int radius = diameter / 2;//radius of ball
   
   //creates a "ball" with coordinates x, y, and has y direction
   public Ball(int x, int y, int sign) {
      trueX = x;
      trueY = y;  
      increment = sign * increment;
   }
   
   //returns x coordinate of ball
   public int getX(){
      return trueX;
   }
   //returns y coordinate of ball
   public int getY() {
      return trueY;
   }
   
   //returns diameter of ball
   public int getDiameter() {
      return diameter;
   }
   //returns radius of ball
   public int getRadius() {
      return radius;
   }
   
   //returns center x coordinate of ball
   public int centerX() {
      return trueX +  radius;
   }
   
   //returns center y coordinate of ball
   public int centerY() {
      return(trueY + radius);
   }
   
   //changes y coordinate of ball by increment
   public void yIncrement() {
      trueY += increment;
   }
   
   //changes direction of ball movement
   public void signChange(){
      increment = -increment;
   }

   
}