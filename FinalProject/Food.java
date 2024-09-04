import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
public class Food extends JPanel {
     private static final int S_DIAMETER = 15;
     private static final int L_DIAMETER = 35;
     private char type;
     private int x, y, diameter;

     public Food(int x, int y, char type){
        if(type == 's')
            this.diameter = S_DIAMETER;
        else 
            this.diameter = L_DIAMETER;

        this.setBounds(x, y, this.diameter, this.diameter);

          this.x = x;
          this.y = y;
          this.type = type;
     }

     @Override
     protected void paintComponent(Graphics g) {
         super.paintComponent(g);
        //  System.out.println("width = " + getWidth() + " height = " + getHeight());
        //  System.out.println("X = " + getX() + " Y = " + getY());
         int centerX = this.diameter / 2;
         int centerY = this.diameter / 2;
         g.setColor(Color.red);



         g.fillOval(centerX - this.diameter / 2, centerY - this.diameter / 2, this.diameter, this.diameter);
     }
 
     @Override
     public Dimension getPreferredSize() {
         return new Dimension(this.diameter, this.diameter);
     }

     public int getX(){
        return this.x;
     }

     public int getY(){
        return this.y;
     }

     public char getType(){
        return this.type;
     }
}
