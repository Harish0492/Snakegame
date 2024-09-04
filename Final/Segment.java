public class Segment extends GameObject {
    private int dir;

    public Segment(int r, int c, int dir){
        super(new Position(r, c));
        this.dir = dir;
    }

    public int getDir(){
        return dir;
    }

    public void updatePos(Position pos){
        this.pos = pos;
    }

    public void updateDir(int dir){
        this.dir = dir;
    }
}
