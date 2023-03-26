package bricks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    ArrayList<GameBlock> blocks = new ArrayList<>();
    private boolean startPlaying;
    private int score = 0;

    private int num_of_blocks = 0;

    private Timer timer;
    private int delay = 8;


    GameBlock paddle = new GameBlock(175, 600, 125, 25, "bar.png");
    GameBlock ball = new GameBlock(175, 420, 35, 35, "NewBall.png");

    public GamePlay() {
        try {
            // Adding all the bricks to the list of blocks
            for (int i = 0; i < 6; i++)
                blocks.add(new GameBlock((i * 80), 0, 145, 40, "Blue-Brick.png"));
            for (int i = 0; i < 6; i++)
                blocks.add(new GameBlock((i * 80), 41, 145, 40, "Yellow-Brick.png"));
            for (int i = 0; i < 6; i++)
                blocks.add(new GameBlock((i * 80), 80, 145, 40, "Red-Brick.png"));
            for (int i = 0; i < 6; i++)
                blocks.add(new GameBlock((i * 80), 120, 145, 40, "Grey-Brick.png"));
            num_of_blocks = blocks.size();
            addKeyListener(this);
            setFocusable(true);
            setFocusTraversalKeysEnabled(false);
            timer = new Timer(delay, this);
            timer.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //  Draw the Background
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, 500, 700);

        blocks.forEach( block -> {
            block.draw(g2d, this);
        });
        // Drawing the paddle and the ball separately
        paddle.draw(g2d, this);
        ball.draw(g2d, this);

        //  Adding Scores
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("verdana", Font.BOLD, 25));
        g2d.drawString("SCORE: "+score, 200, 650);

        if(!startPlaying){
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("verdana", Font.BOLD, 30));
            g2d.drawString("GAME OVER !!", getWidth()/2 - 200,getHeight()/2);
        }
    }

    public void update() {
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // timer.start();
        if(startPlaying) {
            ball.xPos += ball.movX;

            if(ball.xPos < 10 || (ball.xPos > (getWidth()-ball.width))){
                ball.movX *= -1;
            }

            if(ball.yPos < 0 || (((ball.xPos+ball.width) > paddle.xPos) && (ball.yPos > (paddle.yPos - ball.height)) && (ball.xPos < (paddle.xPos + paddle.width)))){
                ball.movY *= -1;
            }
            ball.yPos += ball.movY;

            //  Stopping the game when the ball crosses the paddle
            if(ball.yPos > (paddle.yPos + paddle.height)){
                startPlaying = false;
                ball.movY = 0;
                ball.movX = 0;
            }

            //  Destroying the blocks when hit
            AtomicInteger sum = new AtomicInteger();
            blocks.forEach(block -> {
                if(block.destroyed == true){
                    sum.getAndIncrement();
                }
            });
            if (sum.get() >= num_of_blocks){
                startPlaying = false;
            }
            blocks.forEach(block -> {
                if((ball.yPos < (block.yPos+block.height)) && ((ball.xPos + ball.width) > block.xPos) && (ball.xPos < (block.xPos+block.width)) && (!block.destroyed)){
                    block.destroyed = true;
                    score += 5;
                    ball.movY *= -1;
                }
                if(((ball.yPos > block.yPos) && (ball.yPos < block.height)) && (((ball.xPos + ball.width) == block.xPos) || ball.xPos == (block.xPos + block.width))){
                    block.destroyed = true;
                    score += 5;
                    ball.movX *= -1;
                }
            });
        }
        update();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            startPlaying = true;
            new Thread(() -> {
                while (true) {
                    update();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException err) {
                        err.printStackTrace();
                    }
                }
            }).start();
        }

        if ((e.getKeyCode() == KeyEvent.VK_RIGHT) && (paddle.xPos < (getWidth() - paddle.width))) {
            moveRight();
        }
        if ((e.getKeyCode() == KeyEvent.VK_LEFT) && (paddle.xPos > 10)) {
            moveLeft();
        }
        update();
    }

    private void moveLeft() {
        startPlaying = true;
        paddle.xPos -= 10;
    }

    private void moveRight() {
        startPlaying = true;
        paddle.xPos += 10;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


}
