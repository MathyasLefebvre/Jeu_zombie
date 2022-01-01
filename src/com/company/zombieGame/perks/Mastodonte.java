package com.company.zombieGame.perks;

import com.company.engine.Buffer;
import com.company.zombieGame.Blockade;
import com.company.zombieGame.Perk;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Mastodonte extends Perk {

    private static final String SPRITE_SHEET = "images/jugg.png";
    private Image image;
    private Blockade collision;

    public Mastodonte() {
        load();
        collision = new Blockade();
        collision.setDimension(20, 24);
        teleport(1385, 375);
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
        collision.teleport(x, y + 50);
    }

    @Override
    public void blockadeFromBottom() {
        collision.teleport(x , y + 32);
    }

    private void load() {
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(SPRITE_SHEET));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
