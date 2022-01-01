package com.company.zombieGame;

import com.company.engine.Buffer;
import com.company.engine.CollatableRepository;
import com.company.engine.entities.StaticEntity;

import java.awt.*;

public class Blockade extends StaticEntity {

    public Blockade() {
        CollatableRepository.getInstance().registeredEntity(this);
    }

    @Override
    public void draw(Buffer buffer) {
        if (GameSettings.DEBUG_COLLISION) {
            buffer.drawRectangle(x, y, width, height, new Color(255, 0, 0, 100));
        }
    }
}
