package de.janiswolf._pacman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends ApplicationAdapter {
    Texture backgroundTexture, wallTexture, entityTexture, entity2Texture, playerTexture;
    SpriteBatch spriteBatch;
    FitViewport viewport;
    int size = 25;
    private GridWorld gridWorld;
    private Player player;
    private Entity entity;
    private Entity2 entity2;

    @Override
    public void create() {
        wallTexture = new Texture("wall.png");
        backgroundTexture = new Texture("background.png");
        entityTexture = new Texture("entity.png");
        entity2Texture = new Texture("entity2.png");
        playerTexture = new Texture("player.png");

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(size, size);

        gridWorld = new GridWorld(wallTexture);
        gridWorld.createGrid(size, size);

        player = new Player(playerTexture, 5, 5);
        entity = new Entity(entityTexture, 10, 10, 0.05f);
        entity2 = new Entity2(entity2Texture, 15, 15, 0.05f);
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
        entity2Texture.dispose();
        playerTexture.dispose();
        spriteBatch.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    private void input() {
        player.update(Gdx.graphics.getDeltaTime());
    }

    private void logic() {
        entity.followPlayer(player);

        entity2.interceptPlayer(player);

        // Bounding-Rectangles für Spieler und Entitäten abrufen
        Rectangle playerBounds = player.getSprite().getBoundingRectangle();
        Rectangle entityBounds = entity.getSprite().getBoundingRectangle();
        Rectangle entity2Bounds = entity2.getSprite().getBoundingRectangle();

        // Überprüfe Kollision zwischen Spieler und Entität 1
        if (playerBounds.overlaps(entityBounds)) {
            if(!player.istTot()) {
                player.lebenVerloren();
                System.out.println("Leben verloren! Ein Leben verbleibend! (Made with ❤love❤ by ChatGPT)");
                player.getSprite().setPosition(5,5);
                if(player.istTot()){
                    System.out.println("Verloren! Kein Leben mehr übrig. LOSER!");
                    Gdx.app.exit();
                }
            }
        }

        // Überprüfe Kollision zwischen Spieler und Entität 2
        if (playerBounds.overlaps(entity2Bounds)) {
            if(!player.istTot()){
                player.lebenVerloren();
                System.out.println("Leben verloren! Ein Leben verbleibend! (Made with ❤love❤ by ChatGPT)");
                player.getSprite().setPosition(5,5);
                if(player.istTot()){
                    System.out.println("Verloren! Kein Leben mehr übrig. LOSER!");
                Gdx.app.exit();
                }
            }
        }
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight); // Hintergrund
        for (Sprite wallSprite : gridWorld.getWallSprites()) {
            wallSprite.draw(spriteBatch);
        }

        player.getSprite().draw(spriteBatch);
        entity.getSprite().draw(spriteBatch);
        entity2.getSprite().draw(spriteBatch);

        spriteBatch.end();
    }
}
