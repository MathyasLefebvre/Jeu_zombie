package com.company.zombieGame;

import com.company.engine.AnimationEditor;
import com.company.engine.Buffer;
import com.company.engine.CollatableRepository;

import java.awt.*;
import java.util.Random;

public class Zombie extends Opponent {

    private static final String SPRITESHEET = "images/player.png";
    private int playerX;
    private int playerY;
    private Player player;
    private AnimationEditor animation;
    private int damage = 60;
    private int attackCooldown = 0;
    private char lastAxe;

    public Zombie(Player player) {
        super();
        this.player = player;
        setSpeed(1);
        setDimension(32, 32);
        animation = new AnimationEditor(SPRITESHEET, 96, 128, width, height, this);
        health = 100;
        CollatableRepository.getInstance().registeredEntity(this);
    }

    @Override
    public void draw(Buffer buffer) {
        displayHealth(buffer);
        animation.draw(buffer);
    }

    @Override
    public void update() {
        playerX = player.getX();
        playerY = player.getY();
        if (player.intersectWith(this) || playerX == getX() && playerY == getY()) {
            if (canAttack()) {
                attack();
            }
        }
        followPlayer();
        cooldown();
        animation.updateFrames();
    }

    private void followPlayer() {
        if (!(playerX == getX())) {
            moveIntoXAxe();
        } else {
            moveintoYAxe();
        }

        if (!asMoved()) {
            if (lastAxe == 'x') {
                moveintoYAxe();
            }else {
                moveIntoXAxe();
            }
        }
    }

    private boolean canAttack() {
        return attackCooldown == 0;
    }

    private void cooldown() {
        attackCooldown--;
        if (attackCooldown <= 0) {
            attackCooldown = 0;
        }
    }

    private void attack() {
        attackCooldown = 50;
        int damageTake = player.getHealth() - damage;
        player.setHealth(damageTake);
        Sound.ATTACK.start();
    }

    private void displayHealth(Buffer buffer) {
        if (health <= 36) {
            buffer.drawText(health + ICON_HEART, x, y, Color.RED);
        } else if (health <= 66) {
            buffer.drawText(health + ICON_HEART, x, y, Color.YELLOW);
        } else {
            buffer.drawText(health + ICON_HEART, x, y, Color.GREEN);
        }
    }

    private void moveIntoXAxe() {
        lastAxe  = 'x';
        if (playerX < getX()) {
            moveLeft();
        }else {
            moveRight();
        }
    }

    private void moveintoYAxe() {
        lastAxe  = 'y';
        if (playerY < getY()) {
            moveUp();
        }else {
            moveDown();
        }
    }

}
