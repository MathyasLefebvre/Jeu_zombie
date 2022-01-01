package com.company.zombieGame.perks;

import com.company.engine.Buffer;
import com.company.zombieGame.Blockade;
import com.company.zombieGame.Perk;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class PackPunch extends Perk {

    public static String SPRITE_SHEET = "images/pack-a-puch.png";
    private Image image;
    private Blockade collision;

    public PackPunch() {
        load();
        collision = new Blockade();
        collision.setDimension(73, 16);
        teleport(800 , 600);
        setDimension(24, 81);
        blockadeFromTop();
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawImage(image, x, y);
        collision.draw(buffer);
    }

    @Override
    public void blockadeFromTop() {
        collision.teleport(x , y + 64);
    }

    @Override
    public void blockadeFromBottom() {
        collision.teleport(x, y + 48);
    }

    private void load() {
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(SPRITE_SHEET));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
