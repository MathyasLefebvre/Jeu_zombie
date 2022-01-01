package com.company.zombieGame;

import com.company.engine.AnimationEditor;
import com.company.engine.Buffer;

import java.awt.*;
import java.util.Random;


public class Boss extends Opponent {

    private static final String SPRITESHEET = "images/player.png";
    private AnimationEditor animationEditor;
    private Player player;
    private int playerX;
    private int playerY;
    private int damage = 101;
    private int attackCooldown = 0;
    private char lastAxe;

    public Boss(Player player) {
        super();
        setSpeed(2);
        setDimension(32, 32);
        this.player = player;
        health = 250;
        animationEditor = new AnimationEditor(SPRITESHEET, 192, 0, width, height, this);
    }

    @Override
    public void draw(Buffer buffer) {
        displayHealth(buffer);
        animationEditor.draw(buffer);
    }

    @Override
    public void update() {
        ajustBossPositionAccordingToPlayer();
        playerX = player.getX();
        playerY = player.getY();
        movement();
        cooldown();
        animationEditor.updateFrames();
        if (player.intersectWith(this) || playerX == getX() && playerY == getY()) {
            if (canAttack()) {
                attack();
            }
        }
    }

    private void movement() {
        followPlayer();
        opponentIsStuck();
    }

    private void displayHealth(Buffer buffer) {
        if (health <= 80) {
            buffer.drawText(health + ICON_HEART, x, y, Color.RED);
        } else if (health <= 160) {
            buffer.drawText(health + ICON_HEART, x, y, Color.YELLOW);
        } else {
            buffer.drawText(health + ICON_HEART, x, y, Color.GREEN);
        }
    }

    private boolean canAttack() {
        return attackCooldown == 0;
    }

    private void attack() {
        attackCooldown = 50;
        int damageTake = player.getHealth() - damage;
        player.setHealth(damageTake);
        Sound.ATTACK.start();
    }

    private void cooldown() {
        attackCooldown--;
        if (attackCooldown <= 0) {
            attackCooldown = 0;
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

    private void ajustBossPositionAccordingToPlayer() {
        setSpeed(2);
        if ((player.getX() % 2 != 0 && x % 2 == 0) || (player.getX() % 2 == 0 && x % 2 != 0)) {
            setSpeed(1);
        }
    }

    private void followPlayer() {
        if (!(playerX == getX())) {
            moveIntoXAxe();
        } else {
            moveintoYAxe();
        }
    }

    private void opponentIsStuck() {
        if (!asMoved()) {
            if (lastAxe == 'x') {
                moveintoYAxe();
            }else {
                moveIntoXAxe();
            }
        }
    }
}
