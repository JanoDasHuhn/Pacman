package de.janiswolf._pacman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private Player player;


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
        player = new Player(playerTexture, 7, 8);
    }


    @Override
    public void render() {
        player.playerInput();
        input();
        logic();
        draw();
    }

    @Override
    public void dispose() {
        wallTexture.dispose();
        backgroundTexture.dispose();
        entityTexture.dispose();
        playerTexture.dispose();
        spriteBatch.dispose();
    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    private void input() {
    }

    private void logic() {
        Entity.move();
        Entity.move();
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
        Entity.getSprite().draw(spriteBatch);
        spriteBatch.end();
        Entity.getSprite().draw(spriteBatch); // Spieler zeichnen
        spriteBatch.end();

    }
}
