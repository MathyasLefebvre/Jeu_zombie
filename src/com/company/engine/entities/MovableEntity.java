package com.company.engine.entities;

import com.company.engine.Buffer;
import com.company.engine.controls.Direction;

import java.awt.*;

public abstract class MovableEntity extends UpdatableEntity {

    private Collision collision;
    private Direction direction = Direction.UP;
    private int speed;
    private boolean moved;
    private int lastX;
    private int lastY;

    public MovableEntity() {
        collision = new Collision(this);
        speed = 1;
    }

    @Override
    public void update() {
        moved = false;
    }

    public void moveDown() {
        move(Direction.DOWN);
    }

    public void moveUp() {
        move(Direction.UP);
    }

    public void moveLeft() {
        move(Direction.LEFT);
    }

    public void moveRight() {
        move(Direction.RIGHT);
    }

    public void move(Direction direction) {
        this.direction = direction;
        int allowedSpeed = collision.getAllowedSpeed(direction);
        x += direction.getVelocityX(allowedSpeed);
        y += direction.getVelocityY(allowedSpeed);
        moved = (x != lastX || y != lastY);
        lastX = x;
        lastY = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean asMoved() {
        return moved;
    }

    public Rectangle getHitBox() {
        switch (direction) {
            case UP: return getUpperHitBox();
            case DOWN: return getLowerHitBox();
            case LEFT: return getLeftHitBox();
            case RIGHT: return  getRightHitBox();
            default: return getBounds();
        }
    }

    public void drawHitBox(Buffer buffer) {
        Rectangle rectangle = getHitBox();
        Color color = new Color(255, 0, 255, 200);
        buffer.drawRectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height, color);
    }

    public boolean hitBoxIntersection(StaticEntity other) {
        return getHitBox().intersects(other.getBounds());
    }

    private Rectangle getLeftHitBox() {
        return new Rectangle(x - speed, y, speed, height);
    }

    private Rectangle getRightHitBox() {
        return new Rectangle(x + width, y, speed, height);
    }

    private Rectangle getUpperHitBox() {
        return new Rectangle(x , y - speed, width, speed);
    }

    private Rectangle getLowerHitBox() {
        return new Rectangle(x, y + height, width, speed);
    }

}
