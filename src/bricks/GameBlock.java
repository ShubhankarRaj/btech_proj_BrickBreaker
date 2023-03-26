package bricks;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class GameBlock extends Rectangle {
    Image pic;
    boolean destroyed = false;

    int xPos, yPos;
    int height, width;
    int movX, movY;

    String file_path;

    GameBlock(int xPos, int yPos, int width, int height, String s){

        this.xPos = xPos;
        this.yPos = yPos;

        this.file_path = s;

        this.movX = 2;
        this.movY = -1;

        this.height = height;
        this.width = width;

        try {
            pic = ImageIO.read(getClass().getResource("/resources/images/"+s));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g, Component c){
        if(!this.destroyed)
            g.drawImage(this.pic, this.xPos, this.yPos, this.width, this.height, c);
    }

}

