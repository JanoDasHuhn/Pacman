package de.janiswolf._pacman.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import de.janiswolf._pacman.GridWorld;
import de.janiswolf._pacman.Main;

import java.util.ArrayList;
import java.util.Random;

public class EntitySpawner {
    private ArrayList<Entity> entities;
    Texture entityTexture, entity2Texture, entity3Texture, entity4Texture, playerTexture;
    private int size;
    private float speed;
    private Main main;
    private GridWorld gridWorld;

    public EntitySpawner(GridWorld gridWorld, Main main, int size, float speed) {
        entityTexture = new Texture("geist1.png");
        entity2Texture = new Texture("hund2.png");
        entity3Texture = new Texture("hund3.png");
        entity4Texture = new Texture("hund4.png");
        playerTexture = new Texture("Katze.png");

        this.gridWorld = gridWorld;

        entities = new ArrayList<>();
        this.size = size;
        this.speed = speed;
        this.main = main;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public Entity create(EntityType entityType) {
        if (entityType.equals(EntityType.PLAYER)) {
            return create(EntityType.PLAYER, 5, 5);
        }
        Position position = generateSpawningPosition();
        return create(entityType,position.x, position.y);



    }

    private Position generateSpawningPosition(){
        Random random = new Random();
        Position position = new Position(random.nextInt(25),random.nextInt(10,20));
        for (Sprite sprite :gridWorld.getWallSprites()){
            if(position.x == sprite.getX()){
                if(position.y == sprite.getY()){
                    return  generateSpawningPosition();
                }
            }
        }
        for (Entity entity : getEntities()){
            if(position.x == entity.sprite.getX()){
                if (position.y == entity.sprite.getY()){
                    return generateSpawningPosition();
                }
            }
        }
        return position;
    }


    public Entity create(EntityType entityType, float startX, float startY) {
        boolean isInterceptor = false, isCollider = true;
        Texture texture = entityTexture;

        switch (entityType) {
            case PLAYER:
                Player player1 = new Player(playerTexture, startX, startY, 0.06f, 3, gridWorld);
                main.setPlayer(player1);
                entities.add(player1);
                return player1;

            case SPEED_GHOST:
                speed = 1.7f;
                texture = entity4Texture;
                break;

            case GHOST_INTERCEPTOR:
                isInterceptor = true;
                texture = entity3Texture;
                break;

            case NO_COLLISION_GHOST:
                isCollider = false;
                texture = entity2Texture;
                break;

            default:
                break;
        }

        Ghost ghost = new Ghost(texture, startX, startY, 0.05f, 1, main.getPlayer(), isInterceptor, gridWorld, isCollider);
        entities.add(ghost);
        return ghost;
    }


}

