package com.company.zombieGame;

import com.company.engine.Buffer;
import com.company.engine.Camera;
import com.company.engine.entities.MovableEntity;

import java.awt.*;
import java.util.Random;

public class Drop extends MovableEntity {

    int x;
    int y;
    int ySpeed;
    int randomSpeed;
    int currentCameraY;
    Random random = new Random();


    public Drop() {
        x = random.nextInt(1600);
        y = random.nextInt(300);
        y = y * -1;
        ySpeed = 20;
    }

    public void fall() {

        currentCameraY = Camera.getInstance().getY();
        randomSpeed = random.nextInt(4);
        if  (isOutOfBound()) {
            y = random.nextInt(200);
            y = y * -1;
        }
        y = (y + ySpeed) - randomSpeed;
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawRectangle(x, y, 2, 10, Color.BLUE);

    }

    private boolean isOutOfBound() {
        return y > (currentCameraY * -1) + 600;
    }
}
