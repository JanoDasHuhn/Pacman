package de.janiswolf._pacman.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.janiswolf._pacman.GridWorld;

public abstract class Entity {
    protected Sprite sprite;
    protected Vector2 position;
    protected Vector2 velocity;
    protected float speed;
    protected int health;
    protected float startX, startY, prevX, prevY;
    protected GridWorld gridWorld;
    protected boolean applyPushback; // New flag for pushback behavior

    public Entity(Texture texture, float startX, float startY, float speed, int health, GridWorld gridWorld) {
        this.sprite = new Sprite(texture);
        this.position = new Vector2(startX, startY);
        this.velocity = new Vector2(0, 0);
        this.speed = speed;
        this.sprite.setPosition(startX, startY);
        this.sprite.setSize(1, 1);
        this.health = health;
        this.startX = startX;
        this.startY = startY;
        this.prevX = startX;
        this.prevY = startY;
        this.gridWorld = gridWorld;
        this.applyPushback = false; // Default to no pushback
    }

    public abstract void update(float deltaTime);

    public Sprite getSprite() {
        return sprite;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getBoundingRectangle() {
        return sprite.getBoundingRectangle();
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    boolean checkCollision() {
        for (Sprite walls : gridWorld.getWallSprites()) {
            Rectangle wallRect = new Rectangle(walls.getX(), walls.getY(), walls.getWidth() - 0.2f, walls.getHeight() - 0.2f);

            if (new Rectangle(position.x, position.y, sprite.getWidth(), sprite.getHeight())
                .overlaps(wallRect)) {

                position.x = prevX;
                position.y = prevY;
                sprite.setPosition(prevX, prevY);

                if (applyPushback) {
                    Vector2 pushBack = position.cpy()
                        .sub(walls.getX() + walls.getWidth() / 2, walls.getY() + walls.getHeight() / 2).nor();
                    position.add(pushBack.scl(0.1f));
                    sprite.setPosition(position.x, position.y);
                }

                return false;
            }
        }
        return true;
    }
}
