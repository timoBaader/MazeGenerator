import javax.swing.*;
import java.awt.*;
import java.util.Stack;

//TO DO -TICK AND RENDER METHOD

public class GamePlay extends JPanel implements Runnable {

    public final int gameSize = 10;
    public boolean running;
    public Thread thread;

    public int visitCounter;

    public int XUnit;
    public int YUnit;

    public MazeGenerator mazeGenerator;

    public GamePlay() {

        mazeGenerator = new MazeGenerator();
        start();
    }

    public void tick() {

        mazeGenerator.getCurrent().visited = true;
        repaint();
        if(visitCounter == gameSize * gameSize) {
            stop();
        }
        // Change current cell
        mazeGenerator.moveCurrentCell();
        int x = mazeGenerator.getCurrent().getXPosition();
        int y = mazeGenerator.getCurrent().getYPosition();

        System.out.println("Current Cell X, Y : " + x + ", " + y);
    }

    public void paint(Graphics g) {
        XUnit = getWidth() / gameSize;
        YUnit = getHeight() / gameSize;
        visitCounter = 0;
        // Draw cells
        g.setColor(Color.GRAY);
        for (int x = 0; x < gameSize; x++) {
            for (int y = 0; y < gameSize; y++) {
                // If the cell has been visited before, paint it purple
                if(mazeGenerator.getCell(x,y).visited) {
                    g.setColor(Color.MAGENTA);
                    g.fillRect(x * XUnit, y * YUnit, x * XUnit + XUnit, y * YUnit + YUnit);
                    g.setColor(Color.GRAY);
                    visitCounter += 1;
                } else
                    g.fillRect(x * XUnit, y * YUnit, x * XUnit + XUnit, y * YUnit + YUnit);
            }
        }
        // Draw current cell green
        g.setColor(Color.GREEN);
        // Width increases as it moves left, height increases as it moves down.
        g.fillRect(mazeGenerator.getCurrent().getXPosition() * XUnit, mazeGenerator.getCurrent().getYPosition() * YUnit, XUnit, YUnit);

        // Draw walls
        g.setColor(Color.WHITE);
        for (int x = 0; x < gameSize; x++) {
            for (int y = 0; y < gameSize; y++) {
                if (mazeGenerator.getCell(x,y).hasTopWall) {
                    g.drawLine(x * XUnit, y * YUnit, x * XUnit + XUnit, y * YUnit);
                }
                if (mazeGenerator.getCell(x,y).hasRightWall) {
                    g.drawLine(x * XUnit + XUnit, y * YUnit, x * XUnit + XUnit, y * YUnit + YUnit);
                }
                if (mazeGenerator.getCell(x,y).hasBottomWall) {
                    g.drawLine(x * XUnit, y * YUnit + YUnit, x * XUnit + XUnit, y * YUnit + YUnit);
                }
                if (mazeGenerator.getCell(x,y).hasLeftWall) {
                    g.drawLine(x * XUnit, y * YUnit, x * XUnit, y * YUnit + YUnit);
                }
            }
        }
    }

    @Override
    public void run() {

        int fps = 3;
        double timePerTick = 1000000000 / fps; // Max time we have to render 1 frame (1 second)
        double delta = 0;
        long now;
        long lastTime = System.nanoTime(); // Nanotime counts computers runningtime in nanoseconds
        long timer = 0;
        int ticks = 0;

        while(running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now -lastTime;
            lastTime = now;
            if (delta >= 1) {
                tick();
                ticks++;
                delta--;
            }
            if(timer >= 1000000000) {
                //System.out.println("Ticks and Frames: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }
        stop();
    }

    public synchronized void start() {
        if(running)
            return;
        running = true;
        thread = new Thread(this);	   //this meaning the Game class
        thread.start();                //calls the run method
    }

    public synchronized void stop() {
        if(!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


