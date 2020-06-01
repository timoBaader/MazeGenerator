public class Cell {

    public boolean hasTopWall;
    public boolean hasBottomWall;
    public boolean hasRightWall;
    public boolean hasLeftWall;
    public boolean visited;

    public int XPosition;
    public int YPosition;


    public Cell(int x, int y){
        hasBottomWall = true;
        hasTopWall = true;
        hasRightWall = true;
        hasLeftWall = true;
        visited = false;

        XPosition = x;
        YPosition = y;
    }

    public int getXPosition() {
        return XPosition;
    }

    public int getYPosition() {
        return YPosition;
    }
}
