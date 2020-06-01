import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JFrame Jframe = new JFrame();
        GamePlay gamePlay = new GamePlay();

        Jframe.setSize(600,600);
        Jframe.setResizable(true);
        Jframe.setLocationRelativeTo(null);
        Jframe.setTitle("MazeGenerator");
        Jframe.setDefaultCloseOperation(Jframe.EXIT_ON_CLOSE);
        Jframe.add(gamePlay);
        Jframe.setVisible(true);
    }
}
