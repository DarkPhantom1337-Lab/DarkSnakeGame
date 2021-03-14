package ua.darkphantom1337.darksnakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

public class GameField extends JPanel implements ActionListener {

    /**
     * @Author DarkPhantom1337
     * @Description SnakeGame by DarkPhantom1337
     */

    private final int SIZE = 320;
    private final int DOT_SIZE = 20;
    private final int ALL_DOTS = 400;
    private Image snake_tail_up, snake_tail_down, snake_tail_left, snake_tail_right;
    private Image snake_body_vertically, snake_body_horizontally;
    private Image snake_body_right_up, snake_body_right_down;
    private Image snake_body_left_up, snake_body_left_down;
    private Image snake_head_up, snake_head_down, snake_head_left, snake_head_right;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int[] leftupx = new int[ALL_DOTS], leftupy = new int[ALL_DOTS];
    private int[] leftdownx = new int[ALL_DOTS];
    private int[] leftdowny = new int[ALL_DOTS];
    private int[] rightupx = new int[ALL_DOTS], rightupy = new int[ALL_DOTS];
    private int[] rightdownx = new int[ALL_DOTS], rightdowny = new int[ALL_DOTS];
    private int[] upleftx = new int[ALL_DOTS], uplefty = new int[ALL_DOTS];
    private int[] uprightx = new int[ALL_DOTS], uprighty = new int[ALL_DOTS];
    private int[] downleftx = new int[ALL_DOTS], downlefty = new int[ALL_DOTS];
    private int[] downrightx = new int[ALL_DOTS], downrighty = new int[ALL_DOTS];
    private int[] verticallybodyx = new int[ALL_DOTS], verticallybodyy = new int[ALL_DOTS];
    private int[] horizontallybodyx = new int[ALL_DOTS], horizontallybodyy = new int[ALL_DOTS];
    private java.util.List<Integer> levels = new ArrayList<Integer>();
    private int dots,speed = 1;
    private Timer timer;
    private boolean left = false, last_left = false;
    private boolean right = true, last_right = false;
    private boolean up = false, last_up = false;
    private boolean down = false, last_down = false;
    private boolean inGame = false;


    public GameField() {
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);

    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = dots * DOT_SIZE - i * DOT_SIZE;
            y[i] = dots * DOT_SIZE;
        }
        timer = new Timer(250, this);
        timer.start();
        new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        },50);
        createApple();
    }

    public void createApple() {
        appleX = new Random().nextInt(15) * DOT_SIZE;
        appleY = new Random().nextInt(15) * DOT_SIZE;
    }

    public void loadImages() {
        apple = new ImageIcon("apple.png").getImage();
        snake_body_vertically = new ImageIcon("snake_body_vertically.png").getImage();
        snake_body_horizontally = new ImageIcon("snake_body_horizontally.png").getImage();
        snake_head_up = new ImageIcon("snake_head_up.png").getImage();
        snake_head_down = new ImageIcon("snake_head_down.png").getImage();
        snake_head_left = new ImageIcon("snake_head_left.png").getImage();
        snake_head_right = new ImageIcon("snake_head_right.png").getImage();
        snake_tail_up = new ImageIcon("snake_tail_up.png").getImage();
        snake_tail_down = new ImageIcon("snake_tail_down.png").getImage();
        snake_tail_left = new ImageIcon("snake_tail_left.png").getImage();
        snake_tail_right = new ImageIcon("snake_tail_right.png").getImage();
        snake_body_right_up = new ImageIcon("snake_body_right_up.png").getImage();
        snake_body_right_down = new ImageIcon("snake_body_right_down.png").getImage();
        snake_body_left_up = new ImageIcon("snake_body_left_up.png").getImage();
        snake_body_left_down = new ImageIcon("snake_body_left_down.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.setColor(Color.GREEN);
            g.drawString("Счёт: " + (dots-2) + " Скорость: " + speed, 1, 10);
            g.drawImage(apple, appleX, appleY, this);
            if (up) {
                g.drawImage(snake_head_up, x[0], y[0], this);
                g.drawImage(snake_tail_up, x[dots - 1], y[dots - 1], this);
            }
            if (down) {
                g.drawImage(snake_head_down, x[0], y[0], this);
                g.drawImage(snake_tail_down, x[dots - 1], y[dots - 1], this);
            }
            if (left) {
                g.drawImage(snake_head_left, x[0], y[0], this);
                g.drawImage(snake_tail_left, x[dots - 1], y[dots - 1], this);
            }
            if (right) {
                g.drawImage(snake_head_right, x[0], y[0], this);
                g.drawImage(snake_tail_right, x[dots - 1], y[dots - 1], this);
            }
            for (int i = 1; i < dots - 1; i++) {
                Boolean leftdown = (leftdownx[i] == x[i] && leftdowny[i] == y[i]),
                        upright = (uprightx[i] == x[i] && uprighty[i] == y[i]);
                if (leftdown || upright) {
                    g.drawImage(snake_body_right_down, x[i], y[i], this);
                    if (i == dots - 2)
                        if (leftdown) g.drawImage(snake_tail_left, x[dots - 1], y[dots - 1], this);
                        else g.drawImage(snake_tail_up, x[dots - 1], y[dots - 1], this);
                    continue;
                }
                Boolean leftup = (leftupx[i] == x[i] && leftupy[i] == y[i]),
                        downright = (downrightx[i] == x[i] && downrighty[i] == y[i]);
                if (leftup || downright) {
                    g.drawImage(snake_body_right_up, x[i], y[i], this);
                    if (i == dots - 2)
                        if (leftup) g.drawImage(snake_tail_left, x[dots - 1], y[dots - 1], this);
                        else g.drawImage(snake_tail_down, x[dots - 1], y[dots - 1], this);
                    continue;
                }
                Boolean rightdown = (rightdownx[i] == x[i] && rightdowny[i] == y[i]),
                        upleft = (upleftx[i] == x[i] && uplefty[i] == y[i]);
                if (rightdown || upleft) {
                    g.drawImage(snake_body_left_down, x[i], y[i], this);
                    if (i == dots - 2)
                        if (rightdown) g.drawImage(snake_tail_right, x[dots - 1], y[dots - 1], this);
                        else g.drawImage(snake_tail_up, x[dots - 1], y[dots - 1], this);
                    continue;
                }
                Boolean rightup = (rightupx[i] == x[i] && rightupy[i] == y[i]),
                        downleft =  (downleftx[i] == x[i] && downlefty[i] == y[i]);
                if (rightup || downleft) {
                    g.drawImage(snake_body_left_up, x[i], y[i], this);
                    if (i == dots - 2)
                        if (rightup) g.drawImage(snake_tail_right, x[dots - 1], y[dots - 1], this);
                        else g.drawImage(snake_tail_down, x[dots - 1], y[dots - 1], this);
                    continue;
                }
                if (horizontallybodyx[i] == x[i] && horizontallybodyy[i] == y[i]) {
                    g.drawImage(snake_body_horizontally, x[i], y[i], this);
                    continue;
                }
                if (verticallybodyx[i] == x[i] && verticallybodyy[i] == y[i]) {
                    g.drawImage(snake_body_vertically, x[i], y[i], this);
                    continue;
                }
            }
        } else {
            String str = "Game Over";
            //Font f = new Font("Arial",14,Font.BOLD);
            g.setColor(Color.white);
            // g.setFont(f);
            g.drawString(str, 125, SIZE / 2);
        }
    }

    public void move() {
        //Проходим каждый сегмент(20пх) змейки не считая голову
        for (int i = dots; i > 0; i--) {
            if (i != dots) { // если это не хвост
                if (leftdownx[i] == x[i] && leftdowny[i] == y[i]) { // и был поворот с лево вниз
                    leftdownx[i + 1] = leftdownx[i]; // перемещаем "повёрнутый" сегмент дальше
                    leftdowny[i + 1] = leftdowny[i]; // чтобы он не был всегда в одном сегменте змеи
                    // а был в одном сегменте карты.
                    leftdownx[i] = -1; // удаляем старый "повёрнутый" сегмент и он стаёт обычным
                    leftdowny[i] = -1; // удаляем старый "повёрнутый" сегмент и он стаёт обычным
                } // все остальные повороты работают так же как и этот просто перемещаються по сегменту
                if (leftupx[i] == x[i] && leftupy[i] == y[i]) { // и был поворот с лево вверх
                    leftupx[i + 1] = leftupx[i];
                    leftupy[i + 1] = leftupy[i];
                    leftupx[i] = -1;
                    leftupy[i] = -1;
                }
                if (rightdownx[i] == x[i] && rightdowny[i] == y[i]) { // и был поворот с право вниз
                    rightdownx[i + 1] = rightdownx[i];
                    rightdowny[i + 1] = rightdowny[i];
                    rightdownx[i] = -1;
                    rightdowny[i] = -1;
                }
                if (rightupx[i] == x[i] && rightupy[i] == y[i]) { // и был поворот с право вверх
                    rightupx[i + 1] = rightupx[i];
                    rightupy[i + 1] = rightupy[i];
                    rightupx[i] = -1;
                    rightupy[i] = -1;
                }
                if (upleftx[i] == x[i] && uplefty[i] == y[i]) { // и был поворот сверху налево
                    upleftx[i + 1] = upleftx[i];
                    uplefty[i + 1] = uplefty[i];
                    upleftx[i] = -1;
                    uplefty[i] = -1;
                }
                if (uprightx[i] == x[i] && uprighty[i] == y[i]) { // и был поворот сверху направо
                    uprightx[i + 1] = uprightx[i];
                    uprighty[i + 1] = uprighty[i];
                    uprightx[i] = -1;
                    uprighty[i] = -1;
                }
                if (downleftx[i] == x[i] && downlefty[i] == y[i]) { // и был поворот снизу налево
                    downleftx[i + 1] = downleftx[i];
                    downlefty[i + 1] = downlefty[i];
                    downleftx[i] = -1;
                    downlefty[i] = -1;
                }
                if (downrightx[i] == x[i] && downrighty[i] == y[i]) { // и был поворот снизу направо
                    downrightx[i + 1] = downrightx[i];
                    downrighty[i + 1] = downrighty[i];
                    downrightx[i] = -1;
                    downrighty[i] = -1;
                }
            }
            //делаем здесь тоже самое что и для поворотов
            if (horizontallybodyx[i] == x[i] && horizontallybodyy[i] == y[i]) {
                horizontallybodyx[i + 1] = horizontallybodyx[i];
                horizontallybodyy[i + 1] = horizontallybodyy[i];
                horizontallybodyx[i] = -1;
                horizontallybodyy[i] = -1;
            }
            if (verticallybodyx[i] == x[i] && verticallybodyy[i] == y[i]) {
                verticallybodyx[i + 1] = verticallybodyx[i];
                verticallybodyy[i + 1] = verticallybodyy[i];
                verticallybodyx[i] = -1;
                verticallybodyy[i] = -1;
            }
            x[i] = x[i - 1]; // перемещаем сегмент змейки дальше по карте
            y[i] = y[i - 1]; // перемещаем сегмент змейки дальше по карте
        }
        if (left) { // если сейчас змейка идёт в левую сторону
            if (last_down) { // а раньше шла вниз
                downleftx[1] = x[1]; // запоминаем что тут поворот снизу налево
                downlefty[1] = y[1]; // перемещаем сегмент змейки дальше по карте
            }
            if (last_up) { // а раньше шла вверх
                upleftx[1] = x[1]; // запоминаем что тут поворот сверху налево
                uplefty[1] = y[1]; // запоминаем что тут поворот сверху налево
            }
            horizontallybodyx[1] = x[1]; // запоминаем что этот сегмент стоит горизонтально
            horizontallybodyy[1] = y[1]; // запоминаем что этот сегмент стоит горизонтально
            last_down = false; // убираем повороты чтобы каждый сегмент не был им
            last_up = false; // убираем повороты чтобы каждый сегмент не был им
            x[0] -= DOT_SIZE; // перемещаем голову влево на 1 сегмент ( -x)
        }
        if (right) { // если сейчас змейка идёт в правую сторону
            if (last_down) { // а раньше шла вниз
                downrightx[1] = x[1]; // запоминаем что тут поворот снизу направо
                downrighty[1] = y[1]; // запоминаем что тут поворот снизу направо
            }
            if (last_up) { // а раньше шла вверх
                uprightx[1] = x[1]; // запоминаем что тут поворот сверху направо
                uprighty[1] = y[1]; // запоминаем что тут поворот сверху направо
            }
            horizontallybodyx[1] = x[1]; // запоминаем что этот сегмент стоит горизонтально
            horizontallybodyy[1] = y[1]; // запоминаем что этот сегмент стоит горизонтально
            last_down = false; // убираем повороты чтобы каждый сегмент не был им
            last_up = false; // убираем повороты чтобы каждый сегмент не был им
            x[0] += DOT_SIZE; // перемещаем голову вправо на 1 сегмент ( +x)
        }
        if (up) { // если сейчас змейка идёт вверх
            if (last_left) { // а раньше шла в левую сторону
                leftupx[1] = x[1]; // запоминаем что тут поворот слево вверх
                leftupy[1] = y[1]; // запоминаем что тут поворот слево вверх
            }
            if (last_right) { // а раньше шла в правую сторону
                rightupx[1] = x[1]; // запоминаем что тут поворот справо вверх
                rightupy[1] = y[1]; // запоминаем что тут поворот справо вверх
            }
            verticallybodyx[1] = x[1]; // запоминаем что этот сегмент стоит вертикально
            verticallybodyy[1] = y[1]; // запоминаем что этот сегмент стоит вертикально
            last_left = false; // убираем повороты чтобы каждый сегмент не был им
            last_right = false; // убираем повороты чтобы каждый сегмент не был им
            y[0] -= DOT_SIZE; // перемещаем голову вверх на 1 сегмент (-y)
        }
        if (down) { // если сейчас змейка идёт вниз
            if (last_left) { // а раньше шла в левую сторону
                leftdownx[1] = x[1]; // запоминаем что тут поворот слеов вниз
                leftdowny[1] = y[1]; // запоминаем что тут поворот слеов вниз
            }
            if (last_right) { // а раньше шла в правую сторону
                rightdownx[1] = x[1]; // запоминаем что тут поворот справо вниз
                rightdowny[1] = y[1]; // запоминаем что тут поворот справо вниз
            }
            verticallybodyx[1] = x[1]; // запоминаем что этот сегмент стоит вертикально
            verticallybodyy[1] = y[1]; // запоминаем что этот сегмент стоит вертикально
            last_left = false; // убираем повороты чтобы каждый сегмент не был им
            last_right = false; // убираем повороты чтобы каждый сегмент не был им
            y[0] += DOT_SIZE; // перемещаем голову вверх на 1 сегмент (+y)
        }
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            createApple();
        }
    }

    public void checkCollisions() {
        for (int i = dots; i > 0; i--)
            if (i > 4 && x[0] == x[i] && y[0] == y[i])
                inGame = false;
        if (x[0] > SIZE-40)
            inGame = false;
        if (x[0] < 0)
            inGame = false;
        if (y[0] > SIZE-40)
            inGame = false;
        if (y[0] < 0)
            inGame = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            if ((dots+2)%5 == 0){
                if ((dots-2)/5 != 0 && !levels.contains((dots-2)/5)) {
                    checkApple();
                    checkCollisions();
                    move();
                    repaint();
                    speed = (dots / 5)+1;
                    System.out.println("Change Snake speed, current speed: " + (dots / 5));
                    levels.add((dots-2)/5);
                    timer.setDelay(250 - ((dots + 2) / 5 * 20));
                    timer.restart();
                }
            }
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_SPACE) {
                inGame = !inGame;
            }
            if (key == KeyEvent.VK_LEFT && !right) {
                changeDirection();
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                changeDirection();
                right = true;
                up = false;
                down = false;
            }

            if (key == KeyEvent.VK_UP && !down) {
                changeDirection();
                right = false;
                up = true;
                left = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                changeDirection();
                right = false;
                down = true;
                left = false;
            }
        }
    }

    private void changeDirection() {
        last_left = left;
        last_right = right;
        last_down = down;
        last_up = up;
        left = false;
        right = false;
        down = false;
        up = false;
    }


}