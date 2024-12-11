package de.janiswolf._pacman.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.janiswolf._pacman.GridWorld;

import java.util.Random;

public class Ghost extends Entity {

    private Player player;
    private boolean isInterceptor, isCollider;

    public Ghost(Texture texture, float startX, float startY, float speed, int health, Player player, boolean isInterceptor, GridWorld gridWorld, boolean checkCollision) {
        super(texture, startX, startY, speed, health, gridWorld);
        this.player = player;
        this.isInterceptor = isInterceptor;
        this.isCollider = checkCollision;
        this.applyPushback = true; // Pushback enabled for ghosts
    }

    @Override
    public void update(float deltaTime) {
        prevX = sprite.getX();
        prevY = sprite.getY();
        if (!isInterceptor) {
            followPlayer(player);
        }
        interceptPlayer();
    }

    private void followPlayer(Player player) {
        Vector2 playerPosition = player.getPosition();
        Vector2 direction = playerPosition.cpy().sub(position).nor();
        velocity.set(direction).scl(speed);

        position.add(velocity);
        if (isCollider) {
            if (checkCollision()) {
                sprite.setPosition(position.x, position.y);
            }
        } else {
            sprite.setPosition(position.x, position.y);
        }
    }

    public void interceptPlayer() {
        Vector2 playerPosition = player.getPosition();
        Vector2 directionToPlayer = playerPosition.cpy().sub(position).nor();
        Random random = new Random();
        Vector2 blockPosition = playerPosition.cpy().add(directionToPlayer.scl(random.nextInt(5))); // Block 2 steps ahead

        Vector2 interceptDirection = blockPosition.cpy().sub(position).nor();
        velocity.set(interceptDirection).scl(speed);

        position.add(velocity);
        if (isCollider) {
            if (checkCollision()) {
                sprite.setPosition(position.x, position.y);
            }
        } else {
            sprite.setPosition(position.x, position.y);
        }
    }
}

