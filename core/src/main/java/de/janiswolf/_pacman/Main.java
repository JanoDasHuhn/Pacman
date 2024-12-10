package de.janiswolf._pacman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
    Texture backgroundTexture, wallTexture, entityTexture, entity2Texture, playerTexture;
    SpriteBatch spriteBatch;
    FitViewport viewport;
    int size = 25;
    private GridWorld gridWorld;
    private Player player;
    private EntitySpawner entitySpawner;

    @Override
    public void create() {
        wallTexture = new Texture("wall.png");
        backgroundTexture = new Texture("background.png");


        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(size, size);

        gridWorld = new GridWorld(wallTexture);
        gridWorld.createGrid(size, size);


         entitySpawner = new EntitySpawner(gridWorld,this,1,0.2f);
         entitySpawner.create(EntityType.PLAYER);
         entitySpawner.create(EntityType.GHOST);
         entitySpawner.create(EntityType.SPEED_GHOST);
         entitySpawner.create(EntityType.NO_COLLISION_GHOST);
         entitySpawner.create(EntityType.GHOST_INTERCEPTOR);



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
        for(Entity entity : entitySpawner.getEntities()){
            if(entity instanceof Player){
                Player player = (Player)entity;
                player.update(Gdx.graphics.getDeltaTime());
            }
        }

    }

    private void logic() {



        // Bounding-Rectangles für Spieler und Entitäten abrufen
        Player player = null;
        Rectangle playerBounds = null;
        ArrayList<Rectangle> entityBounds = new ArrayList<>();
        for(Entity entity : entitySpawner.getEntities() ){
            if(!(entity instanceof Player)){
                entityBounds.add(entity.getSprite().getBoundingRectangle());
                entity.update(0);
            }else {
                playerBounds = entity.getSprite().getBoundingRectangle();
                player = (Player) entity;
            }
        }


        // Überprüfe Kollision zwischen Spieler und Entität
        for(Rectangle rectangle : entityBounds){

            if (playerBounds.overlaps(rectangle.set(rectangle.x, rectangle.y, 0.5f, 0.5f))) {
                if(!player.istTot()) {
                    player.lebenVerloren();
                    System.out.println("Leben verloren! Ein Leben verbleibend! (Made with ❤love❤ by ChatGPT)");

                    for (Entity entity : entitySpawner.getEntities()){
                        entity.setPosition(new Vector2(entity.getStartX(),entity.getStartY()));
                    }
                    if(player.istTot()){
                        System.out.println("Verloren! Kein Leben mehr übrig. LOSER!");
                        Gdx.app.exit();
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

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight); // Hintergrund
        for (Sprite wallSprite : gridWorld.getWallSprites()) {
            wallSprite.draw(spriteBatch);
        }
        for (Entity entity : entitySpawner.getEntities()){
            entity.getSprite().draw(spriteBatch);
        }


        spriteBatch.end();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
