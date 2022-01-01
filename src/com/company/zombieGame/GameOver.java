package com.company.zombieGame;

import com.company.engine.Buffer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class GameOver {

    private Image background;
    private final String MAP_PATH = "images/Twrgo.png";

    public GameOver() {
        load();
    }

    public void draw(Buffer buffer) {
        buffer.drawImage(background, 125, 125);
    }

    public void load() {
        try {
            background = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(MAP_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
