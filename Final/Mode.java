import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Random;

public class Mode extends JPanel {
    public int BOXSIZE=20;
    protected int width=30;
    protected int height=30;
    protected int BORDERTHICKNESS = 1;
    protected int borderWidth = 0;
    protected int borderHeight = 0;
    protected int settingWidth = 12;
    protected int squares[][];
    protected boolean hasBorder;
    protected Snake snake;
    protected Food food;
    protected int settingSquares[][];

    public Mode(boolean hasBorder, int DIM){
        this.hasBorder = hasBorder;
        width = DIM;
        height = DIM;
        snake = new Snake(width, height);

        if(hasBorder){
            borderWidth = BORDERTHICKNESS * 2;
            borderHeight = BORDERTHICKNESS * 2;
        }
        
        squares = new int[height+borderHeight][width+borderWidth];

        addSquares();

        settingSquares = new int[height+borderHeight][settingWidth];
        for(int r=0; r<height; r++){
            for(int c=0; c<settingWidth; c++){
                if(r==0 || c==0 || r == height-1 || c == settingWidth-1)
                    settingSquares[r][c] = -1;
                else 
                    settingSquares[r][c] = 1;
                
            }
        }

        food = new Food(getRandomPos());

        updateSquares();
    }

    /*
     * resets current mode 
     * resets snake to its start position
     */
    public void reset(){
        snake = new Snake(width, height);
        food.resetCount();
    }
    
    // adds square values based on the condition if border is present or not
    public void addSquares(){
        if(hasBorder)
            for(int r=0; r<height; r++){
                for(int c=0; c<width; c++){
                    if(r==0 || c==0 || r == height-1 || c == width-1)
                        squares[r][c] = -1;
                    else 
                        squares[r][c] = 1;
                    
                }
            }
        else 
            for(int r=0; r<height; r++){
                for(int c=0; c<width; c++){
                    
                        squares[r][c] = 1;
                    
                }
            }
    }

    // returns width of current mode
    public int getWid(){
        return width;
    }

    // returns height of current mode
    public int getHig(){
        return height;
    }

    // checks if the given squres is valid for snake movement
    // return true if valid else returns false
    public boolean validateMove(Position pos){
        if(squares[pos.r][pos.c] > 0)
            return true;

        return false;
    }

    // returns true if snake ate food
    // else returns false
    public boolean ate(){
        Position pos = snake.getPos();

        if(food.pos.c-pos.c == 0 && food.pos.r-pos.r == 0)
            return true;
        
        if(food.getCount() % 5 == 0 && (
            (food.pos.c+1 - pos.c == 0 && food.pos.r - pos.r == 0)|| 
            (food.pos.c - pos.c == 0 && food.pos.r+1 - pos.r == 0)||
            (food.pos.c+1 - pos.c == 0 && food.pos.r-1 - pos.r == 0)
        ))  return true;
        
        return false;
    }

    // used to resets square values after every move
    public void updateSquares(){
        ArrayList<Segment> segments = snake.getSegments();
        for(int r=0; r<height; r++){
            for(int c=0; c<width; c++){
                
                if(squares[r][c] == 0)
                    squares[r][c] = 1;
            }
        }

        for(int i=segments.size()-1; i>=0; i--){
            Position pos = segments.get(i).pos;
            
            if((hasBorder && pos.r > 0 && pos.r <= width + borderWidth && pos.c > 0 && pos.c <= height + borderHeight) || 
                (pos.r >= 0 && pos.r < width && pos.c >= 0 && pos.c < height))
                squares[pos.r][pos.c] = 0;
        }

        if(food.getCount() % 5 == 0){

            squares[food.pos.r+1][food.pos.c] = 2;
            squares[food.pos.r][food.pos.c+1] = 2;
            squares[food.pos.r+1][food.pos.c+1] = 2;
        }
        squares[food.pos.r][food.pos.c] = 2;

    }

    // generates a random postion which used to randomly place food on map
    public Position getRandomPos(){
        Random temp = new Random();
        int MAX = food != null && food.getCount() % 5 != 0 ? width-1 : width-2;
        int randX = temp.nextInt(MAX);
        int randY = temp.nextInt(MAX);

        if(hasBorder){
            randX++;
            randY++;
        }

        Position pos = new Position(randX, randY);
        while(!validateMove(pos)){
            randX = temp.nextInt(MAX);
            randY = temp.nextInt(MAX);

            if(hasBorder){
                randX++;
                randY++;
            }

            pos = new Position(randX, randY);
        }
        return pos;
    }

    @Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        Graphics2D g2 = (Graphics2D) g;
		super.paintComponent (g2);

        // drawing game border
        
            
        for(int r=0; r<height; r++){
            for(int c=0; c<width; c++){
                if(squares[r][c] == -1){
                    g2.setColor(Color.black);
                    Rectangle2D.Double borderSquare =
                    new Rectangle2D.Double (c * BOXSIZE, r * BOXSIZE, BOXSIZE, BOXSIZE);
                    g2.fill(borderSquare);
                }
                
            }
        }

        // drawing setting pane border 
        for(int r=0; r<height; r++){
            for(int c=0; c<settingWidth; c++){
                if(settingSquares[r][c] == -1){
                    g2.setColor(Color.blue);
                    Rectangle2D.Double borderSquare =
                    new Rectangle2D.Double ((c + width + borderWidth) * BOXSIZE, r * BOXSIZE, BOXSIZE, BOXSIZE);
                    g2.fill(borderSquare);
                }
                
            }
        }
        
        // painting snake
        

        ArrayList<Segment> segments = snake.getSegments();
        for(int i=segments.size()-1; i>=0; i--){
            Segment segment = segments.get(i);

            if(i == 0)
                g2.setColor(Color.red);
            else
                g2.setColor(Color.blue);
            
                    // Rectangle2D.Double snakeSegment =
                    // new Rectangle2D.Double (segment.pos.c * BOXSIZE, segment.pos.r * BOXSIZE, BOXSIZE, BOXSIZE);
                Ellipse2D.Double circle = 
                new Ellipse2D.Double(segment.pos.c * BOXSIZE, segment.pos.r * BOXSIZE, BOXSIZE, BOXSIZE);
                g2.fill(circle);


        }

        // painint food
        g2.setColor(Color.green);
        int wid = BOXSIZE, hig = BOXSIZE;
        if(food.getCount() % 5 == 0){
            wid = BOXSIZE * 2;
            hig = BOXSIZE * 2;
            g2.setColor(Color.orange);
        }

        Ellipse2D.Double foodSquare = 
                new Ellipse2D.Double(food.pos.c * BOXSIZE, food.pos.r * BOXSIZE, wid, hig);
                g2.fill(foodSquare);
    }
}


class EasyMode extends Mode {
    public EasyMode(int DIM){
        super(false, DIM);
    }
}

class IntermediateMode extends Mode {
    public IntermediateMode(int DIM){
        super(true, DIM);
    }
}

class HardMode extends Mode {
    public HardMode(int DIM){
        super(false, DIM);

        addSquares();

    }

    public void addWalls(){
        for(int i=height/8-1; i<height/8+3*height/4+2; i++){
            squares[i][width/2] = -1;
        }

        for(int i=width/8-1; i<width/8+3*width/4+2; i++){
            squares[height/2][i] = -1;
        }
    }

    @Override
    public void addSquares(){
        super.addSquares();
        addWalls();
    }
}

class SuperHardMode extends Mode {
    public SuperHardMode(int DIM){
        super(true, DIM);

        addSquares();
    }

    public void addWalls(){
        for(int i=height/8; i<=height/8+3*height/4; i++){
            squares[i][width/2] = -1;
        }

        for(int i=width/8; i<=width/8+3*width/4; i++){
            squares[height/2][i] = -1;
        }
    }

    @Override 
    public void addSquares(){
        super.addSquares();
        addWalls();
    }
}
