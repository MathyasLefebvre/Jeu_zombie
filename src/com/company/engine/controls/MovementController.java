package com.company.engine.controls;

import java.awt.event.KeyEvent;

public class MovementController extends Controller{

    private int upKey = KeyEvent.VK_UP;
    private int downKey = KeyEvent.VK_DOWN;
    private int leftKey = KeyEvent.VK_LEFT;
    private int rightKey = KeyEvent.VK_RIGHT;

    public MovementController() {
        int[] keys = {upKey, downKey, leftKey, rightKey};
        bindKeys(keys);
    }

    public boolean isLeftPressed() {
        return isKeyPressed(leftKey);
    }

    public boolean isRigthPressed() {
        return isKeyPressed(rightKey);
    }

    public boolean isDownPressed() {
        return isKeyPressed(downKey);
    }

    public boolean isUpPressed() {
        return isKeyPressed(upKey);
    }

    public boolean isMoving() {
        return isLeftPressed() || isRigthPressed() || isDownPressed() || isUpPressed();
    }

    public void setUpKey(int upKey) {
        removeKey(this.upKey);
        bindKey(upKey);
        this.upKey = upKey;
    }

    public void setDownKey(int downKey) {
        removeKey(this.downKey);
        bindKey(downKey);
        this.downKey = downKey;
    }

    public void setLeftKey(int leftKey) {
        removeKey(this.leftKey);
        bindKey(leftKey);
        this.leftKey = leftKey;
    }

    public void setRightKey(int rightKey) {
        removeKey(this.rightKey);
        bindKey(rightKey);
        this.rightKey = rightKey;
    }
}
