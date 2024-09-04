import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Snake extends JComponent{
    public ArrayList<Segment> segments;
    private int x, y;
    private int foodCount;

    public Snake(int width, int height){

        segments = new ArrayList<Segment>();
        Segment segment = new Segment(100, 0, 30, 30);
        this.x = 100;
        this.y = 0;
        this.foodCount = 0;
        // Segment segment2 = new Segment(100+25, 0, 30, 30);
        // Segment segment3 = new Segment(100+25+25, 0, 30, 30);

        segments.add(0, segment);
        // segments.add(1, segment2);
        // segments.add(2, segment3);
    }

    public void addSnake(JFrame jframe){
        int count = 1;
        for(Segment segment: this.segments){
            // System.out.println(count++);

            segment.setVisible(true);
            jframe.add(segment);
        }
    }

    public void addSegmentToSnake(){
        int prev = this.segments.size()-1;
        int prevX = this.segments.get(prev).getX();
        int prevY = this.segments.get(prev).getY(); 
        int dir = this.segments.get(0).dir;
        Segment segment;

        if(dir == 1)
            segment = new Segment(prevX, prevY+30, 40, 40);
        else if(dir == 2)
            segment = new Segment(prevX-30, prevY, 40, 40);
        else if(dir == 3)
            segment = new Segment(prevX, prevY-30, 40, 40);
        else 
            segment = new Segment(prevX+30, prevY, 40, 40);
        
        this.segments.add(this.segments.size(), segment);
        // System.out.println("segments = " + this.segments.size());
    }

    public void move(){
        

        Segment firstSegment = this.segments.get(0);

        if(firstSegment.dir == 1){
            this.y = (firstSegment.getY()-15+510)%510;
            firstSegment.setLocation(this.x, this.y);
        }else if(firstSegment.dir == 2){
            this.x = (firstSegment.getX()+15)%510;
            firstSegment.setLocation(this.x, this.y);
        }else if(firstSegment.dir == 3){
            this.y = (firstSegment.getY()+15)%510;
            firstSegment.setLocation(this.x, this.y);
        }else if(firstSegment.dir == 4){
            this.x = (firstSegment.getX()-15+510)%510;
            firstSegment.setLocation(this.x, this.y);
        }

        this.segments.add(0, firstSegment);
    
    }

    public void changeDirection(int dir){
        Segment firstSegment = this.segments.get(0);
        if(dir == 1 && (firstSegment.dir == 2 || firstSegment.dir == 4)){
            firstSegment.dir = 1;
        } else if(dir == 2 && (firstSegment.dir == 1 || firstSegment.dir == 3)){
            firstSegment.dir = 2;
        } else if(dir == 3 && (firstSegment.dir == 2 || firstSegment.dir == 4)){
            firstSegment.dir = 3;
        } else if(dir == 4 && (firstSegment.dir == 3 || firstSegment.dir == 1)){
            firstSegment.dir = 4;
        }

        this.segments.add(0, firstSegment);
    }
    
    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void ate(){
        this.foodCount++;
    }

    public int foodCount(){
        return this.foodCount;
    }

}