package com.company.zombieGame.perks;

import com.company.engine.Buffer;
import com.company.zombieGame.Blockade;
import com.company.zombieGame.Perk;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class DoubleTap extends Perk {

    private static final String SPRITE_SHEET = "images/doubleTap.png";
    private Image image;
    private Blockade collision;

    public DoubleTap() {
        load();
        collision = new Blockade();
        collision.setDimension(45, 16);
        teleport(372 ,1266);
        setDimension(24, 61);
        blockadeFromTop();
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawImage(image, x, y);
        collision.draw(buffer);
    }

    @Override
    public void blockadeFromTop() {
        collision.teleport(x, y + 62);
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
