public class Food extends GameObject {
    private int count;
    public Food(Position pos){
        super(pos);
        count = 1;
    }

    // updates food positon and increases the food count
    public void updateFood(Position pos){
        this.pos = pos;
        count++;
    }

    // returns current food generated count
    public int getCount(){
        return count;
    }

    // resets food generated count to 0
    public void resetCount(){
        count = 0;
    }
}