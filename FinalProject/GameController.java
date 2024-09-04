public class GameController {
    private Game gui;
    public GameController(){
        
    }

    public void launchGame(){
        this.gui = new Game(this);
    }

    public void arrowClicked(int key){
        if(key == 37 || key == 65)
            this.gui.changeDirection(4);
        else if(key == 38 || key == 87)
            this.gui.changeDirection(1);
        else if(key == 39 || key == 68)
            this.gui.changeDirection(2);
        else if(key == 40 || key == 83)
            this.gui.changeDirection(3);
    }

    public void startButtonPressed(){
        System.out.println("game started");
        this.gui.updataGameState(1);
    }

    public void pauseButtonPressed(){
        System.out.println("Game Paused");
        this.gui.updataGameState(0);
    }
}