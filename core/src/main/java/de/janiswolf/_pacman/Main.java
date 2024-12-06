package de.janiswolf._pacman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    Texture backgroundTexture,walltexture,entityTexture,wallTexture;
    SpriteBatch spriteBatch;
    FitViewport viewport;
    int size = 25;
    private GridWorld gridWorld;
    private Entity entity;


    @Override
    public void create() {
        walltexture = new Texture("wall.png");
        backgroundTexture = new Texture("background.png");
       // entityTexture = new Texture("entity.png");
        entityTexture = new Texture(Gdx.files.internal("entity.png"));
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(size, size);

         gridWorld = new GridWorld(walltexture);
        gridWorld.createGrid(size,size);

        entity = new Entity(entityTexture, 5, 5, 0.1f, 0);  // Position (5, 5) und Geschwindigkeit (0.1, 0)

    }


    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    @Override
    public void dispose() {
        wallTexture.dispose();
        backgroundTexture.dispose();
        entityTexture.dispose();
        spriteBatch.dispose();
    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    private void input() {
        float velocity = 0.2f; //Geschwindigkeit des Chars
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) { //setzt X auf 0, Y auf 0.2
            entity.setVelocityX(0);
            entity.setVelocityY(velocity);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) { //setzt X auf 0, Y auf -0,2
            entity.setVelocityX(0);
            entity.setVelocityY(-velocity);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) { //setzt X auf -0.2, Y auf 0
            entity.setVelocityX(-velocity);
            entity.setVelocityY(0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) { //setzt X auf 0.2, Y auf 0
            entity.setVelocityX(velocity);
            entity.setVelocityY(0);
        } else {    //Char bewegt sich nicht mehr, wenn nichts gedrückt wird
            entity.setVelocityX(0);
            entity.setVelocityY(0);
        }

    }

    private void logic() {
        float nextX = entity.getSprite().getX() + entity.getVelocityX(); //berechnet nächste X Position des Spielers
        float v = entity.getVelocityX();
        float nextY = entity.getSprite().getY() + entity.getVelocityY(); //berechnet nächste Y Position des Spielers
        float v1 =  entity.getVelocityY();
        //setzt Rect. vor dem Spieler
        Rectangle nextPlayerBounds = new Rectangle(nextX, nextY, entity.getSprite().getWidth(), entity.getSprite().getHeight());
        //checkt Kollision mit den Wänden
        for ( Sprite wall : gridWorld.getWallSprites()){
            if(nextPlayerBounds.overlaps(wall.getBoundingRectangle())){ //stoppt den Spieler, wenn er eine Wand berührt
                entity.setVelocityX(0);
                entity.setVelocityY(0);
                return;
            }
        }
        //wenn keine Kollision, bewegt sich der Spieler
        entity.move();
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight); //Background
        for (Sprite wallSprite : gridWorld.getWallSprites()){
            wallSprite.draw(spriteBatch);
        }
        entity.getSprite().draw(spriteBatch);
        spriteBatch.end();

    }
}
