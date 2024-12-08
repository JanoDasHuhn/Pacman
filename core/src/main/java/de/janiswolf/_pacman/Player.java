package de.janiswolf._pacman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private Sprite sprite;
    private Vector2 position;
    private Vector2 velocity;
    private float speed;
    private int lives;

    public Player(Texture texture, float x, float y) {
        this.sprite = new Sprite(texture);
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
        this.speed = 10f;
        this.lives = 2;
        this.sprite.setSize(2,2);    }

    public void update(float deltaTime) {
        velocity.set(0, 0);

        if (Gdx.input.isKeyPressed(Keys.UP)) {
            velocity.y = speed;
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            velocity.y = -speed;
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            velocity.x = -speed;
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            velocity.x = speed;
        }

        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
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

    public int getLives() {
        return lives;
    }
    public void lebenVerloren() {
        if(lives>0){
            lives--;
        }
    }

    public boolean istTot(){
        return lives==0;
    }
}
