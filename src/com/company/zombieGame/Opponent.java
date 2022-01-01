package com.company.zombieGame;

import com.company.engine.Buffer;
import com.company.engine.entities.MovableEntity;

import java.util.Random;

public abstract class Opponent extends MovableEntity {

    public static final String ICON_HEART = "\u2764";
    public int health;

    public Opponent() {
        randomSpawn();
    }


    public void randomSpawn() {
        Random random = new Random();
        int rnd = random.nextInt(6) + 1;
        if (rnd == 1) {
            teleport(480, 679);
        }
        if (rnd == 2) {
            teleport(480, 270);
        }
        if (rnd == 3) {
            teleport(806, 263);
        }
        if (rnd == 4) {
            teleport(672, 1289);
        }
        if (rnd == 5) {
            teleport(1346, 939);
        }
        if (rnd == 6) {
            teleport(1052, 1294);
        }
    }
    public abstract void draw(Buffer buffer);
    public abstract void update();
}
