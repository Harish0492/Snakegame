import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class Snake {
    private ArrayList<Segment> segments;
    private int size;
    /*
     * previous snake position used when multiple arrow keys pressed
     */
    private Position prev; 
    /*
     * xMax , yMax max x and y coodinates of the mode
     */
    private int xMax, yMax;

    public Snake(int xMax, int yMax){
        size = 1;
        segments = new ArrayList<Segment>();
        segments.add(new Segment(1, 1, 2));
        // for(int i=0; i<5; i++)
        // addSegment();

        prev = getPos();

        this.xMax = xMax;
        this.yMax = yMax;
    }

    // adds a segment at the end of the snake
    public void addSegment(){
        Segment lastSegment = segments.get(size-1);
        segments.add(getNextSegment(lastSegment));
        size++;
    }

    // returns all the segments of the snake
    public ArrayList<Segment> getSegments(){
        return segments;
    }

    // changes the direction of the first segment in snake
    public void turn(int dir){
        Position curr = getPos();

        if(prev.r-curr.r + prev.c-curr.c == 0){
            Timer timer = new Timer(10, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    turn(dir);
                }
            });
            timer.setRepeats(false);
            timer.start();
            // turn(dir);
            return;
        }

        prev = curr;

        int snakeDir = segments.get(0).getDir();

        // if(snakeDir == dir){
        //     move();
        //     return;
        // }

        if((dir == 1 && (snakeDir == 2 || snakeDir == 4)) ||
            (dir == 2 && (snakeDir == 1 || snakeDir == 3)) ||
             (dir == 3 && (snakeDir == 4 || snakeDir == 2)) ||
              (dir == 4 && (snakeDir == 1 || snakeDir == 3)))
                segments.get(0).updateDir(dir);

    }

    // moves snake updating location of segments with the segment before it
    public void move(boolean hasBorder){
        for(int i=segments.size()-1; i>0; i--){
            segments.get(i).updatePos(segments.get(i-1).pos);
        }

        segments.get(0).updatePos(getNextPos(hasBorder));
    }

    // returns position of snake, i.e starting segment location
    public Position getPos(){
        return segments.get(0).pos;
    }
    
    // gets next position if snake moves in current direction
    public Position getNextPos(boolean hasBorder){
        int snakeDir = segments.get(0).getDir();
        if(snakeDir == 1){
            if(hasBorder)
                return new Position(segments.get(0).pos.r-1, segments.get(0).pos.c);
            else 
                return new Position(segments.get(0).pos.r-1 < 0 ? yMax-1 : segments.get(0).pos.r-1, segments.get(0).pos.c);
        }else if(snakeDir == 2){
            if(hasBorder)
                return new Position(segments.get(0).pos.r, segments.get(0).pos.c+1);
            else
                return new Position(segments.get(0).pos.r, segments.get(0).pos.c+1 > xMax-1 ? 0 : segments.get(0).pos.c+1);
        }else if(snakeDir == 3){
            if(hasBorder)
                return new Position(segments.get(0).pos.r+1, segments.get(0).pos.c);
            else 
                return new Position(segments.get(0).pos.r+1 > yMax-1 ? 0 : segments.get(0).pos.r+1, segments.get(0).pos.c);
        }else {
            if(hasBorder)
                return new Position(segments.get(0).pos.r, segments.get(0).pos.c-1);
            else 
                return new Position(segments.get(0).pos.r, segments.get(0).pos.c-1 < 0 ? xMax-1 : segments.get(0).pos.c-1);
        }
    }

    // returns a new segment to be added at the end of snake 
    // with its direction same as current last segment
    private Segment getNextSegment(Segment segment){
        int dir = segment.getDir();
        int r = segment.pos.r, c = segment.pos.c;
        if(dir == 1){
            r += 1;
        } else if(dir == 2){
            c -= 1;
        } else if(dir == 3){
            r -= 1;
        } else if(dir == 4){
            c += 1;
        }

        return new Segment(r, c, dir);
    }
}
