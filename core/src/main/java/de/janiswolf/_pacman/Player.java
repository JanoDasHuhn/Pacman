package de.janiswolf._pacman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {



    public Player(Texture texture, float startX, float startY,float speed,int health) {
        super(texture,startX,startY,speed,health);

        this.velocity = new Vector2(0, 0);
        this.speed = 10f;

        this.sprite.setSize(1,1);    }

    @Override
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








    public void lebenVerloren() {
        if(health>0){
            health--;
        }
    }

    public boolean istTot(){
        return health==0;
    }
}
