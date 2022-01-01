package com.company.zombieGame;

import com.company.engine.Buffer;

import java.awt.*;

public class GameInformation {

    private static GameInformation instance;
    private int x;
    private int y;

    public static GameInformation getInstance() {
        if (instance == null) {
            instance = new GameInformation();
        }
        return instance;
    }

    private GameInformation() {
        x = 0;
        y = 0;
    }

    public void updateInformation(int xPosition, int yPosition) {
        x = xPosition * -1;
        y = yPosition * -1;
    }

    public void draw( Buffer buffer, int round, Player player) {
        buffer.drawRectangle(x, y, 150, 20, Color.BLACK);
        buffer.drawText("Round " + round, x + 10 , y + 15, Color.WHITE);
        buffer.drawText("HP: " + player.getHealth() + " \u2764", x + 80 , y + 15, Color.WHITE);
    }
}
