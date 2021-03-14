package ua.darkphantom1337.darksnakegame;

import javax.swing.*;

public class MainWindow extends JFrame {

    /**
     * @Author DarkPhantom1337
     * @Description SnakeGame by DarkPhantom1337
     */

    public MainWindow(){
        setTitle("SnakeGame by DarkPhantom1337");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(320,345);
        setLocation(400,400);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        System.out.println("Starting SnakeGame... by DarkPhantom1337");
        MainWindow mw = new MainWindow();
    }
}