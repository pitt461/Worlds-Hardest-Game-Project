import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;


public class InteractivePanel 
{
   
   public static void main(String[] args) 
   {
         
      DrawingPanel panel = new DrawingPanel(1500, 1500);
      panel.setBackground(Color.white);
      Graphics g = panel.getGraphics();
            
      // listen for key presses
      InteractiveKeyListener listener = new InteractiveKeyListener(panel);
      panel.addKeyListener(listener);
      
      // listen for mouse clicks
      InteractiveMouseListener listener2 = new InteractiveMouseListener(panel);
      panel.addMouseListener(listener2);
      
      Random r = new Random(); 
      while (true)
      {
         int size = 1; 
         if (listener2.pressed())
         {
            size = 5; 
         }
         Point p = listener2.getPoint();
         g.fillRect(p.x, p.y, size, size); 

         String output = "";
         int code = listener.getKeyCode();
         // Look up KeyEvent in the Javadoc to see other things you can respond to
         if (code == KeyEvent.VK_A) 
            output = "a";
         else if (code == KeyEvent.VK_B)
            output = "b";
         else
            output = "";
         // This is just for demonstration purposes. 
         if ( !output.equals(""))
            g.drawString(output, r.nextInt(500), r.nextInt(500)); 
            
         panel.sleep(10);
      }
   }
   
   /**
    * A class for responding to mouse clicks on the drawing panel.
    */
   public static class InteractiveMouseListener extends MouseInputAdapter 
   {
      /**
       * A private field to store the drawing panel for access later.
       */
      private DrawingPanel panel;
      
      /*
       * The point that was hit
       */
      private Point hitPoint = new Point(0,0); 
      
      /*
       * Whether or not the mouse is pressed
       */
      private boolean pressed = false;  
       
      /**
       * Constructs the InteractivePanel's Mouse listener
       * and stores a reference to the panel itself for use later
       *
       * @param panel A reference to the DrawingPanel we're using
       */   
      public InteractiveMouseListener(DrawingPanel panel) 
      {
         this.panel = panel;
      }
      
      /**
       * Returns the point that the last mouseMove or mouseDown happened. 
       *
       * @return the last place a mouse move/down happened. 
       */      
      public Point getPoint()
      {
         return hitPoint;
      }
      
       
      /**
       * Returns whether or not the mouse was pressed. 
       *
       * @return has the mouse been pressed and not released? . 
       */      
      public boolean pressed()
      {
         return pressed;
      }
      

      
      /**
       * This will respond to a mouse move event.  
       *
       * @param event A MouseEvent that is sent by the system
       * that contains information we'll need to process. 
       */ 
      public void mouseMoved(MouseEvent event) 
      {
         // Getting the x and y coordinate of the mouse. 
         hitPoint.x = event.getX() / panel.getZoom();
         hitPoint.y = event.getY() / panel.getZoom();
      }
   
      /**
       * This will respond to a mouse  pressed event.  
       *
       * @param event A MouseEvent that is sent by the system
       * that contains information we'll need to process. 
       */ 
      public void mousePressed(MouseEvent event) 
      {
         hitPoint.x = event.getX() / panel.getZoom();
         hitPoint.y = event.getY() / panel.getZoom();
         pressed = true;
      }
      
     /**
       * This will respond to a mouse  pressed event.  
       *
       * @param event A MouseEvent that is sent by the system
       * that contains information we'll need to process. 
       */ 
      public void mouseReleased(MouseEvent event) 
      {
         hitPoint.x = event.getX() / panel.getZoom();
         hitPoint.y = event.getY() / panel.getZoom(); 
         pressed = false;    
      }
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
