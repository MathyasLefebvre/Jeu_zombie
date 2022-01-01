package com.company.zombieGame.world;

import com.company.engine.Buffer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Clouds {

    private Image background;
    private final String MAP_PATH = "images/nuage.png";

    public Clouds() {
        load();
    }

    public void draw(Buffer buffer) {
        buffer.drawImage(background, 0, 0);
    }

    public void load() {
        try {
            background = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(MAP_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
