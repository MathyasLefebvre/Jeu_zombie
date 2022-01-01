package com.company.zombieGame.world;

import com.company.engine.Buffer;
import com.company.zombieGame.Blockade;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Map {

    private static final String MAP_PATH = "images/world/map2.png";
    private Image background;
    private ReadJsonMap jsonMap;
    private ArrayList<Blockade> worldBorders;

    public Map() {
        worldBorders = new ArrayList<>();
        jsonMap = new ReadJsonMap();
        worldBorders = jsonMap.getCollisions();
    }
    public void draw(Buffer buffer) {
        buffer.drawImage(background, 0, 0);
        for (Blockade collision : worldBorders) {
            collision.draw(buffer);
        }
    }

    public void load() {
        try {
            background = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(MAP_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Blockade> getWorldBorders() {
        return worldBorders;
    }
}
