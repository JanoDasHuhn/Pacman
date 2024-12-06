package de.janiswolf._pacman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class Entity {
    private Sprite sprite;
    private float velocityX;
    private float velocityY;

    public Entity(Texture texture, float startX, float startY, float velocityX, float velocityY) {
        this.sprite = new Sprite(texture);
        this.sprite.setPosition(startX, startY);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.sprite.setSize(1,1);
    }
    //entity bewegen
    public void move() {
        float newX = sprite.getX() + velocityX;
        float newY = sprite.getY() + velocityY;
        sprite.setPosition(newX, newY);
    }
    //Getter und Setter für Sprite, X und Y
    public Sprite getSprite() {
        return sprite;
    }
    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }
    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }
    public float getVelocityX() {
        return velocityX;
    }
    public float getVelocityY() {
        return velocityY;
    }
}
