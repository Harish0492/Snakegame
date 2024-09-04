import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math.*;
import java.util.Random;

class Game extends JFrame implements KeyListener{
    private Snake snake;
    private Food food;
    private int score;
    private JLabel scoreLable;
    private GameController gameController;
    private int gameState; 
    /*
     * gameState = 1 - game started
     * gamestate = 0 - game paused
     */

    public Game(GameController gameController){
        // initalizing member variables
        this.snake = new Snake(200, 200);
        this.food = new Food((new Random()).nextInt(440)+30, (new Random()).nextInt(440)+30, 's');
        this.score = 0;
        this.scoreLable = new JLabel("Score : " + this.score);
        this.gameController = gameController;
        this.gameState = 0;

        // adding score label to the jframe
        this.scoreLable.setBounds(500-100, 0, 100, 50);;
        this.add(scoreLable);

        // adding snake
        this.snake.addSnake(this);
        
        // adding food
        this.add(this.food);
        
        // adding start/pause button
        JButton button = new JButton("start");
        button.setBounds(200, 520, 80, 30);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(gameState == 0){
                    gameController.startButtonPressed();
                    startGame();
                    button.setText("Pause");
                } else {
                    gameController.pauseButtonPressed();
                    button.setText("Start");
                }
                
            }
        });

        this.add(button);
        
        this.addKeyListener(this);
        this.setTitle("Snake Game");
        this.setSize(500, 600);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        requestFocus();
        
    }   

    @Override 
    public void keyPressed(KeyEvent e){
        this.gameController.arrowClicked(e.getKeyCode());
        
    }

    @Override
    public void keyTyped(KeyEvent e){

    }

    @Override 
    public void keyReleased(KeyEvent e){

    }

    public void startGame(){

        requestFocus();
        Timer timer = new Timer(200, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (gameState == 1) {
                    if(Math.abs(snake.getX() - food.getX()) <= 30 && Math.abs(snake.getY() - food.getY()) <= 30){
                        // score = +1 for food type = 's'
                        // score = +3 for food type = 'l'
                        if(food.getType() == 's')
                            score++;
                        else    
                            score += 3;

                        snake.ate();
                        food.setVisible(false);

                        // adding food at random on the game space
                        if(snake.foodCount() % 5 == 0)
                            food = new Food((new Random()).nextInt(440)+30, (new Random()).nextInt(440)+30, 'l');
                        else
                            food = new Food((new Random()).nextInt(440)+30, (new Random()).nextInt(440)+30, 's');
                        addFood();

                        food.setVisible(true);

                        // updating score label
                        updateScoreLabel();
                    }
                    snake.move();
                    snake.addSnake(Game.this); 
                    repaint();
                } else {
                    ((Timer) e.getSource()).stop(); 
                }
            }
        });

        timer.start();
    }
    
    public void growSnake(){
        this.snake.addSegmentToSnake();
        this.snake.addSnake(this);
    }

    public void changeDirection(int direction){
        this.snake.changeDirection(direction);
    }
   
    public void addFood(){
        this.add(this.food);
    }

    public void updateScoreLabel(){
        scoreLable.setText("Score : " + score);
    }

    public void updataGameState(int newState){
        this.gameState = newState;
    } 
}