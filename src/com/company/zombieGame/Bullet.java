package com.company.zombieGame;

import com.company.engine.Buffer;
import com.company.engine.controls.Direction;
import com.company.engine.entities.MovableEntity;

import java.awt.*;

public class Bullet extends MovableEntity {

    private final Direction playerDirection;
    private boolean isGunPackPunch = false;

    public Bullet(Player player, boolean isPackPunch) {
        sound(isPackPunch);
        playerDirection = player.getDirection();
        setSpeed(10);
        switch (playerDirection) {
            case RIGHT:
                teleport(player.getX() + player.getWidth() + 1, player.getY() + 15 - 2);
                setDimension(8, 4);
                break;
            case LEFT:
                teleport(player.getX() - 9, player.getY() + 15 - 2);
                setDimension(8, 4);
                break;
            case UP:
                teleport(player.getX() + 15 - 2, player.getY() - 9);
                setDimension(4, 8);
                break;
            case DOWN:
                teleport(player.getX() + 15 - 2, player.getY() + player.getHeight() + 1);
                setDimension(4,8);
                break;
        }
    }

    @Override
    public void update() {
        move(playerDirection);
    }

    @Override
    public void draw(Buffer buffer) {
        if (!isGunPackPunch) {
            buffer.drawRectangle(x, y, width, height, Color.YELLOW);
        }else {
            buffer.drawRectangle(x, y, width, height, Color.RED);
            isGunPackPunch = true;
        }
    }

    public void setGunPackPunch(boolean gunPackPunch) {
        isGunPackPunch = gunPackPunch;
    }

    private void sound(boolean isPack) {
        if (isPack) {
            Sound.LASER.start();
            return;
        }
        Sound.GUN.start();
    }
}
