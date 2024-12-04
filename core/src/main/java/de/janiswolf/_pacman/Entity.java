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
    }
    //entity bewegen
    public void move() {
        float newX = sprite.getX() + velocityX;
        float newY = sprite.getY() + velocityY;
    }
    public Sprite getSprite() {
        return sprite;
    }
    //geschwindigkeit
    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }
}
