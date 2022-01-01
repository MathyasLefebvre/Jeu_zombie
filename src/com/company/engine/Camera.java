package com.company.engine;

import com.company.zombieGame.Player;

public class Camera {

    private static Camera instance;
    private int x;
    private int y;

    public static Camera getInstance() {
        if (instance == null) {
            instance = new Camera();
        }
        return instance;
    }

    private Camera() {
        x = 0;
        y = 0;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void position(Player player) {
        y = -player.getY() + 300;
        limitYCamera();
        x = -player.getX() + 400;
        limitXCamera();
    }

    private void limitXCamera() {
        if (x > 0) {
            x = 0;
        }
        if (x < -800) {
            x = -800;
        }
    }

    private void limitYCamera() {
        if (y > 0) {
            y = 0;
        }
        if (y < -1000) {
            y = -1000;
        }
    }
}
