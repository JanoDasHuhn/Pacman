package de.janiswolf._pacman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Ghost extends Entity{

    private Player player;
    private boolean isInterceptor;
    public Ghost(Texture texture, float startX, float startY, float speed,int health,Player player,boolean isInterceptor) {
        super(texture,startX,startY,speed,health);

        this.position = new Vector2(startX, startY);
        this.velocity = new Vector2(0, 0);
        this.speed = speed;
        this.sprite.setPosition(startX, startY);
        this.sprite.setSize(2,2);
        this.player = player;
        this.isInterceptor = isInterceptor;
    }

    @Override
    public void update(float deltaTime) {
        if(!isInterceptor){
            followPlayer(player);
        }
        interceptPlayer();

    }

    private void followPlayer(Player player) {
        Vector2 playerPosition = player.getPosition();
        Vector2 direction = playerPosition.cpy().sub(position).nor();
        velocity.set(direction).scl(speed);

        position.add(velocity);
        sprite.setPosition(position.x, position.y);
    }

    public Sprite getSprite() {
        return sprite;
    }


    public void interceptPlayer() {
        Vector2 playerPosition = player.getPosition();

        Vector2 directionToPlayer = playerPosition.cpy().sub(position).nor();
        Random random = new Random();
        Vector2 blockPosition = playerPosition.cpy().add(directionToPlayer.scl(random.nextInt(5)));  // Blockiere 2 Schritte vor dem Spieler

        Vector2 interceptDirection = blockPosition.cpy().sub(position).nor();

        velocity.set(interceptDirection).scl(speed);

        position.add(velocity);
        sprite.setPosition(position.x, position.y);
    }
    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getBoundingRectangle() {
        return sprite.getBoundingRectangle();
    }
}
