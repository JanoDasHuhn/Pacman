package de.janiswolf._pacman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.janiswolf._pacman.entities.*;

import java.util.ArrayList;

public class Main extends ApplicationAdapter {
    Texture backgroundTexture, playerTexture, deathScreenTexture;
    SpriteBatch spriteBatch;
    FitViewport viewport;
    int worldSize = 100;
    private GridWorld gridWorld;
    private Player player;
    private EntitySpawner entitySpawner;
    OrthographicCamera camera;
    private boolean gameOver;
    private Sound deathSound;

    @Override
    public void create() {
        backgroundTexture = new Texture("background.png");
        deathScreenTexture = new Texture("deathscreen.png"); // Assuming a death screen image
        deathSound = Gdx.audio.newSound(Gdx.files.internal("deathsound.mp3")); // Load the death sound

        spriteBatch = new SpriteBatch();
        gridWorld = new GridWorld(worldSize);
        gridWorld.createGrid(worldSize, worldSize);
        entitySpawner = new EntitySpawner(gridWorld, this, 1, 0.2f);
        entitySpawner.create(EntityType.PLAYER);
        entitySpawner.create(EntityType.DOG);
        entitySpawner.create(EntityType.SPEED_DOG);
        entitySpawner.create(EntityType.NO_COLLISION_DOG);
        entitySpawner.create(EntityType.DOG_INTERCEPTOR);

        camera = new OrthographicCamera(player.getPosition().x, player.getPosition().y);
        viewport = new FitViewport(40, 25, camera);

        gameOver = false;  // Initially, the game is not over

    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        deathScreenTexture.dispose();
        deathSound.dispose();
        playerTexture.dispose();
        spriteBatch.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    private void input() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            if (Gdx.graphics.isFullscreen()) {
                Gdx.graphics.setWindowedMode(800, 600); // Replace with desired windowed dimensions
            } else {
                Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
                Gdx.graphics.setFullscreenMode(displayMode);
            }
        }
        if (!gameOver) {
            for (Entity entity : entitySpawner.getEntities()) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    player.update(Gdx.graphics.getDeltaTime());
                }
            }
        }
    }

    private void logic() {
        if (!gameOver) {
            // Bounding-Rectangles für Spieler und Entitäten abrufen
            Player player = null;
            Rectangle playerBounds = null;
            ArrayList<Rectangle> entityBounds = new ArrayList<>();
            for (Entity entity : entitySpawner.getEntities()) {
                if (!(entity instanceof Player)) {
                    entityBounds.add(entity.getSprite().getBoundingRectangle());
                    Dog dog = (Dog) entity;
                    dog.update();
                } else {
                    playerBounds = entity.getSprite().getBoundingRectangle();
                    player = (Player) entity;
                }
            }

            // Überprüfe Kollision zwischen Spieler und Entität
            for (Rectangle rectangle : entityBounds) {
                if (playerBounds.overlaps(rectangle.set(rectangle.x, rectangle.y, 0.5f, 0.5f))) {
                    if (!player.istTot()) {
                        player.lebenVerloren();
                        System.out.println("Leben verloren! Ein Leben verbleibend!");

                        for (Entity entity : entitySpawner.getEntities()) {
                            entity.setPosition(new Vector2(entity.getStartX(), entity.getStartY()));
                        }
                        if (player.istTot()) {
                            System.out.println("Verloren! Kein Leben mehr übrig. LOSER!");
                            Gdx.app.exit();
                            //deathSound.play(); // Play death sound
                            //gameOver = true;  // Set game over state to true
                        }
                    }
                }
            }
        }
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        if (gameOver) {
            spriteBatch.draw(deathScreenTexture, 0, 0, 20, 20);  // Draw the death screen
        } else {
            camera.position.set(player.getPosition().x, player.getPosition().y, 0);
            camera.update();
            spriteBatch.draw(backgroundTexture, 0, 0, worldSize, worldSize);
            for (Sprite wallSprite : gridWorld.getWallSprites()) {
                wallSprite.draw(spriteBatch);
            }
            for (Entity entity : entitySpawner.getEntities()) {
                entity.getSprite().draw(spriteBatch);
            }
        }

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.end();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
