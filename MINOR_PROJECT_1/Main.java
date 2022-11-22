package MINOR_PROJECT_1;

import javax.swing.*;

public class Main{
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        GamePlay gp = new GamePlay(); //GamePlay user defined class
        frame.setBounds(400,100,700,600);
        frame.setResizable(false);
        frame.setTitle("Brick Breaker Game");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(gp);//we can't add simple class in frame hence extended to JPanel
        //so that we can add it as component
    }
}
