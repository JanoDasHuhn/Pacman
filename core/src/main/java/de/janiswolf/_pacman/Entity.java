package de.janiswolf._pacman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Entity {
    private Sprite sprite;
    private Vector2 position;
    private Vector2 velocity;
    private float speed;

    public Entity(Texture texture, float startX, float startY, float speed) {
        this.sprite = new Sprite(texture);
        this.position = new Vector2(startX, startY);
        this.velocity = new Vector2(0, 0);
        this.speed = speed;
        this.sprite.setPosition(startX, startY);
        this.sprite.setSize(2,2);    }

    public void followPlayer(Player player) {
        Vector2 playerPosition = player.getPosition();
        Vector2 direction = playerPosition.cpy().sub(position).nor();
        velocity.set(direction).scl(speed);

        position.add(velocity);
        sprite.setPosition(position.x, position.y);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getBoundingRectangle() {
        return sprite.getBoundingRectangle();
    }
}
