import java.util.Stack;

public class MazeGenerator  {

    // TO DO - Only try directions of unvisited neighbours
    // TO DO - if stack gets empty move along cells with no walls in between them

    public Cell current;
    public Cell[][] cellArray;
    public final int gameSize = 10;
    public Stack<Cell> travelledPath;

    int failCounter;

    int oneTried;
    int twoTried;
    int threeTried;
    int fourTried;


    public MazeGenerator() {
        failCounter = 0;
        travelledPath = new Stack<>();

        int oneTried = 0;
        int twoTried = 0;
        int threeTried = 0;
        int fourTried = 0;

        cellArray = new Cell[gameSize][gameSize];
        for (int x = 0; x < gameSize; x++) {
            for (int y = 0; y < gameSize; y++) {
                cellArray[x][y] = new Cell(x, y);
            }
        }
        current = cellArray[0][0];
        travelledPath.push(current);
    }

    public Cell getCell(int x, int y){
        return cellArray[x][y];
    }
    public Cell getCurrent(){
        return current;
    }

    // Give possible numbers as parameters frommovecurrentcell method to avoid testing to go to visited cells

    public int generateNumber(){
        int number = (int)(Math.random() * 4) + 1;
        return number;
    }


    // Generate a random number from 1 - 4
    // 1 moves the current cell to the cell above
    // 2 moves the current cell to the right
    // 3 moves the current cell to the bottom
    // 4 moves the current cell to the right
    public void moveCurrentCell() {
        // Detecting if im stuck. If all neighbours have been checked for "visited" and returned false
        int sum = oneTried + twoTried + threeTried + fourTried;
        System.out.println("Sum: " + sum);
        if(sum >= 3) {
            //backTrack();
            System.out.println("Stuck XD");
            if(!travelledPath.isEmpty()) {
                this.current = travelledPath.pop();
                oneTried = 0;
                twoTried = 0;
                threeTried = 0;
                fourTried = 0;
                moveCurrentCell();
            } else {
                System.out.println("Stack is empty!");
            }

        }
        Cell lastCell = this.current;
        Cell newCell = this.current;
        int direction = generateNumber();
        // Account for out of bound tries
        if (direction == 3 && current.getYPosition() >= gameSize - 1)
            threeTried += 1;
        if (direction == 1 && current.getYPosition() <= 0)
            oneTried += 1;
        if (direction == 2 && current.getXPosition() >= gameSize - 1)
            twoTried += 1;
        if (direction == 4 && current.getXPosition() <=  0)
            fourTried += 1;


        //
        if (direction == 3 && current.getYPosition() < gameSize - 1) {
            if(!cellArray[newCell.getXPosition()][newCell.getYPosition() + 1].visited) {
                newCell = cellArray[newCell.getXPosition()][newCell.getYPosition() + 1];
                oneTried = 0;
                twoTried = 0;
                threeTried = 0;
                fourTried = 0;
                this.current = newCell;
                travelledPath.push(newCell);
                wallRemover(lastCell, newCell);
            } else{
                threeTried = 1;
            }
        } else if (direction == 1 && current.getYPosition() > 0) {
            if (!cellArray[newCell.getXPosition()][newCell.getYPosition() - 1].visited){
                newCell = cellArray[newCell.getXPosition()][newCell.getYPosition() - 1];
                oneTried = 0;
                twoTried = 0;
                threeTried = 0;
                fourTried = 0;
                this.current = newCell;
                travelledPath.push(newCell);
                wallRemover(lastCell, newCell);
            } else {
                oneTried = 1;
            }
        } else if (direction == 2 && current.getXPosition() < gameSize - 1) {
            if (!cellArray[newCell.getXPosition() + 1][newCell.getYPosition()].visited) {
                newCell = cellArray[newCell.getXPosition() + 1][newCell.getYPosition()];
                oneTried = 0;
                twoTried = 0;
                threeTried = 0;
                fourTried = 0;
                this.current = newCell;
                travelledPath.push(newCell);
                wallRemover(lastCell, newCell);
            } else {
                twoTried = 1;
            }
        } else if (direction == 4 && current.getXPosition() > 0) {
            if (!cellArray[newCell.getXPosition() - 1][newCell.getYPosition()].visited) {
                newCell = cellArray[newCell.getXPosition() - 1][newCell.getYPosition()];
                oneTried = 0;
                twoTried = 0;
                threeTried = 0;
                fourTried = 0;
                this.current = newCell;
                travelledPath.push(newCell);
                wallRemover(lastCell, newCell);
            } else {
                fourTried = 1;
            }
        }
    }
    public void wallRemover(Cell last, Cell current) {
        // Take last and current cell and remove the wall(s) in between

        // Compare X and Y to get relative location between cells
        int lastX = last.getXPosition();
        int lastY = last.getYPosition();
        int currentX = current.getXPosition();
        int currentY = current.getYPosition();
        int xSum = lastX - currentX;
        int ySum = lastY - currentY;

        // If xSum = -1 then current cell is right of last cell
        // If xSum = 1 then current cell is left of last cell
        // If ySum = -1 then current cell is below last cell
        // If ySum = 1 then current cell is above last cell

        if(xSum == -1){
            current.hasLeftWall = false;
            last.hasRightWall = false;
        } else if (xSum == 1) {
            current.hasRightWall = false;
            last.hasLeftWall = false;
        }
        if(ySum == -1){
            current.hasTopWall = false;
            last.hasBottomWall = false;
        } else if (ySum == 1) {
            current.hasBottomWall = false;
            last.hasTopWall = false;
        }


    }




}
