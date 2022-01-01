package com.company.engine;

import com.company.engine.controls.Direction;
import com.company.engine.entities.MovableEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class AnimationEditor extends MovableEntity {

    private static final int ANIMATION_SPPED = 8;
    private  String image;
    private int x;
    private int y;
    private int width;
    private int height;
    private BufferedImage spriteSheet;
    private Image[] rightFrame;
    private Image[] leftFrame;
    private Image[] upFrame;
    private Image[] downFrame;
    private MovableEntity movableEntity;
    private int currentAnimationFrame = 1;
    private int nextFrame = ANIMATION_SPPED;

    public AnimationEditor(String path, int x, int y, int width, int height, MovableEntity movableEntity) {
        this.image = path;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.movableEntity = movableEntity;
        loadSpriteSheet();
        loadAnimationFrames();
    }

    @Override
    public void draw(Buffer buffer) {
       drawAccordingToDirection(buffer);
    }

    public void updateFrames() {
        if (movableEntity.asMoved()) {
            --nextFrame;
            if (nextFrame == 0) {
                ++currentAnimationFrame;
                if (currentAnimationFrame >= leftFrame.length) {
                    currentAnimationFrame = 0;
                }
                nextFrame = ANIMATION_SPPED;
            }
        }else {
            currentAnimationFrame = 1;
        }
    }

    private void loadSpriteSheet() {
        try {
            spriteSheet = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAnimationFrames() {
        setDownFrame();
        setLeftFrame();
        setRightFrame();
        setUpFrame();
    }

    private void drawAccordingToDirection(Buffer buffer) {
        if (movableEntity.getDirection() == Direction.RIGHT) {
            buffer.drawImage(rightFrame[currentAnimationFrame], movableEntity.getX(), movableEntity.getY());
        }else if (movableEntity.getDirection() == Direction.LEFT) {
            buffer.drawImage(leftFrame[currentAnimationFrame], movableEntity.getX(), movableEntity.getY());
        }else if (movableEntity.getDirection() == Direction.UP) {
            buffer.drawImage(upFrame[currentAnimationFrame], movableEntity.getX(), movableEntity.getY());
        }else {
            buffer.drawImage(downFrame[currentAnimationFrame], movableEntity.getX(), movableEntity.getY());
        }
    }

    private void setDownFrame() {
        downFrame = new Image[3];
        downFrame[0] = spriteSheet.getSubimage(x , y, width, height);
        downFrame[1] = spriteSheet.getSubimage((x + width), y, width, height);
        downFrame[2] = spriteSheet.getSubimage((x + (width * 2)), y, width, height);
    }

    private void setLeftFrame() {
        leftFrame = new Image[3];
        leftFrame[0] = spriteSheet.getSubimage(x, (y + height), width, height);
        leftFrame[1] = spriteSheet.getSubimage((x + width), (y + height), width, height);
        leftFrame[2] = spriteSheet.getSubimage((x + (width * 2)), (y + height), width, height);
    }

    private void setRightFrame() {
        rightFrame = new Image[3];
        rightFrame[0] = spriteSheet.getSubimage(x, (y + (height*2)), width, height);
        rightFrame[1] = spriteSheet.getSubimage((x + width), (y + (height*2)), width, height);
        rightFrame[2] = spriteSheet.getSubimage((x + (width * 2)), (y + (height*2)), width, height);
    }

    private void setUpFrame() {
        upFrame = new Image[3];
        upFrame[0] = spriteSheet.getSubimage(x, (y + (height*3)), width, height);
        upFrame[1] = spriteSheet.getSubimage((x + width), (y + (height*3)), width, height);
        upFrame[2] = spriteSheet.getSubimage((x + (width * 2)), (y + (height*3)), width, height);
    }
}
