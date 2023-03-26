import bricks.GamePlay;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame obj = new JFrame();
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePlay play = new GamePlay();

        obj.pack();
        obj.setBounds(10, 10, 500, 700);
        obj.setTitle("Break Your Bricks");
        obj.setResizable(true);
        obj.add(play);
        obj.setVisible(true);
        obj.getContentPane().add(play);
    }
}