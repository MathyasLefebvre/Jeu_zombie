package com.company.zombieGame;

import com.company.engine.Buffer;
import com.company.engine.entities.StaticEntity;

public class BuyingPoint extends StaticEntity {

    public BuyingPoint(int x, int y) {
        teleport(x,y);
        setDimension(48,48);
    }

    @Override
    public void draw(Buffer buffer) {}

    public boolean canBuy(int x,int y,Player player) {
        return player.getX() >= x && player.getX() <= x + 48 && player.getY() >= y && player.getY() <= y + 48;
    }
}
