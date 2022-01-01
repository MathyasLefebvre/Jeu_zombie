package com.company.zombieGame;

import com.company.engine.AnimationEditor;
import com.company.engine.Buffer;
import com.company.engine.controls.Direction;
import com.company.engine.controls.MovementController;
import com.company.engine.entities.ControllableEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends ControllableEntity {

    private static final String SPRITESHEET = "images/comando.png";
    private AnimationEditor animationEditor;
    private int health = 100;
    private int fireCooldown = 0;
    private int shootTime = 40;
    private int damage = 35;
    private int money = 0;
    private boolean changeGun = false;

    public Player(MovementController controller) {
        super(controller);
        setSpeed(4);
        setDimension(32,35);
        animationEditor = new AnimationEditor(SPRITESHEET, 192, 140, width, height, this);
    }

    @Override
    public void update() {
        super.update();
        moveAccordingToController();
        checkCooldown();
        animationEditor.updateFrames();
    }

    @Override
    public void draw(Buffer buffer) {
        buffer.drawText((money + " $"), x + 5, y - 5, Color.WHITE);
        animationEditor.draw(buffer);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }


    public Bullet fire() {
        fireCooldown = shootTime;
        return new Bullet(this, changeGun);
    }

    public boolean canFire() {
        return fireCooldown == 0;
    }

    public void setShootTime(int shootTime) {
        this.shootTime = shootTime;
    }

    private void checkCooldown() {
        fireCooldown--;
        if (fireCooldown <= 0) {
            fireCooldown = 0;
        }
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setChangeGun(boolean changeGun) {
        this.changeGun = changeGun;
    }
}
