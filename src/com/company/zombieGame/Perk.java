package com.company.zombieGame;

import com.company.engine.Buffer;
import com.company.engine.entities.StaticEntity;

public abstract class Perk extends StaticEntity {

    private boolean drawBeforePlayer;

    public boolean isDrawBeforePlayer() {
        return drawBeforePlayer;
    }

    public void setDrawBeforePlayer(boolean drawBeforePlayer) {
        this.drawBeforePlayer = drawBeforePlayer;
    }

    public abstract void blockadeFromTop();
    public abstract void blockadeFromBottom();




}
