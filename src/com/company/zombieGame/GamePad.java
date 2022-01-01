package com.company.zombieGame;

import com.company.engine.RenderingEngine;
import com.company.engine.controls.MovementController;

import java.awt.event.KeyEvent;

public class GamePad extends MovementController {

    private int quitKey = KeyEvent.VK_Q;
    private int fireKey = KeyEvent.VK_SPACE;
    private int actionKey = KeyEvent.VK_E;
    private int windowKey = KeyEvent.VK_F;

    public GamePad() {
        bindKey(quitKey);
        bindKey(fireKey);
        bindKey(actionKey);
        bindKey(windowKey);
        RenderingEngine.getInstance().addKeyListenner(this);
    }

    public boolean isQuitPressed() {
        return isKeyPressed(quitKey);
    }

    public boolean isFirePressed() {
        return isKeyPressed(fireKey);
    }

    public boolean isActionPressed() {
        return isKeyPressed(actionKey);
    }

    public boolean isWindowPressed() {
        return isKeyPressed(windowKey);
    }


}
