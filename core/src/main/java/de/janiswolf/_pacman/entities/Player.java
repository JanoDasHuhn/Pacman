package de.janiswolf._pacman.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import de.janiswolf._pacman.GridWorld;

public class Player extends Entity {

    public Player(Texture texture, float startX, float startY, float speed, int health, GridWorld gridWorld) {
        super(texture, startX, startY, speed, health, gridWorld);
        this.velocity = new Vector2(0, 0);
        this.speed = 10f;
        this.sprite.setSize(1, 1);
        this.applyPushback = true;
    }

    @Override
    public void update(float deltaTime) {
        velocity.set(0, 0);
        prevY = sprite.getY();
        prevX = sprite.getX();

        if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
            velocity.y = speed;
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
            velocity.y = -speed;
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
            velocity.x = -speed;
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
            velocity.x = speed;
        }

        position.add(velocity.x * deltaTime, velocity.y * deltaTime);

        if (checkCollision()) {
            sprite.setPosition(position.x, position.y);
        }
    }

    public void lebenVerloren() {
        if (health > 0) {
            health--;
        }
    }

    public boolean istTot() {
        return health == 0;
    }
}
