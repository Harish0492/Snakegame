import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
public class Segment extends JPanel {
     private static final int SIDE = 30;
     public int dir;
     private int x, y, width, height;

     public Segment(int x, int y, int width, int height){
          this.dir = 3;
          this.setBounds(x, y, width, height);
          // this.set
          this.x = x;
          this.y = y;
          this.width = width;
          this.height = height;
     }

     @Override
     protected void paintComponent(Graphics g) {
         super.paintComponent(g);
        //  System.out.println("width = " + getWidth() + " height = " + getHeight());
        //  System.out.println("X = " + getX() + " Y = " + getY());
         int centerX = this.width / 2;
         int centerY = this.height / 2;
         g.setColor(Color.BLUE);
     //     g.fillOval(centerX, centerY, 2*SIDE, 2*SIDE);



         g.fillOval(centerX - SIDE / 2, centerY - SIDE / 2, SIDE, SIDE);
     }
 
     @Override
     public Dimension getPreferredSize() {
         return new Dimension(SIDE, SIDE);
     }

     public void setDim(int x, int y){
        this.x = x;
        this.y = y;
     }

     public void setDir(int dir){
        this.dir = dir;
     }

     public int getDir(){
        return this.dir;
     }

     public int getXCood(){
        return this.x;
     }
 
     public int getYCood(){
        return this.y;
     }

// public Segment() {
//     setSize(100, 100);
    
//     // super();
//     // JPanel panel = new JPanel();
//     // JLabel query = new JLabel ("");
//     // query.setMinimumSize(new Dimension(1000, 1000));
//     // query.setBackground(new Color(225, 0, 0));
//     // query.setOpaque(true);
//     // JCompo
//     // JTextField input = new JTextField(10);
//     // JLabel output = new JLabel ("");
//     // JButton button = new JButton ("Say Hello");
//     // this.add (panel);
//     // panel.add (query);
//     // panel.add (input);
//     // panel.add (button);
//     // panel.add (output);

//     // this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
//     // this.setSize(200, 200);
//     // this.setVisible(true);
// }

}
