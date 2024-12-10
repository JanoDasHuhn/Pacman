package de.janiswolf._pacman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.Random;

public class GridWorld {

     Texture wallTexture;
     private int worldSize;
    private final ArrayList<Sprite> wallSprites;
    public GridWorld(Texture wallTexture, int worldSize){
        this.wallTexture = wallTexture;
        wallSprites = new ArrayList<>();
        this.worldSize = worldSize;
    }
    public  void  createGrid(int x, int y) {

        int[][] grid = new int[x][y];

        Random random = new Random();
        for (int i = 0; i < worldSize * 0.1 * worldSize; i++){

            grid[random.nextInt(worldSize)][random.nextInt(6,worldSize)] = 1;
        }
        for (int i = 0; i < worldSize;i++){
            grid[i][0] = 1;
            grid[i][worldSize -1] = 1;
            grid[worldSize -1][i] = 1;
            grid[0][i] = 1;
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    Sprite wallSprite = new Sprite(wallTexture);
                    wallSprite.setPosition(i,j);
                    wallSprite.setSize(1,1);

                    wallSprites.add(wallSprite);
                }
            }
        }
    }

    public ArrayList<Sprite> getWallSprites() {
        return wallSprites;
    }
}
