package de.janiswolf._pacman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    protected Sprite sprite;
    protected Vector2 position;
    protected Vector2 velocity;
    protected float speed;
    protected int health;
    protected float startX,startY;
    public Entity(Texture texture, float startX, float startY, float speed,int health) {
        this.sprite = new Sprite(texture);
        this.position = new Vector2(startX, startY);
        this.velocity = new Vector2(0, 0);
        this.speed = speed;
        this.sprite.setPosition(startX, startY);
        this.sprite.setSize(1,1);
        this.health = health;
        this.startX = startX;
        this.startY = startY;
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
}
