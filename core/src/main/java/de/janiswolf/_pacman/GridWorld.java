package de.janiswolf._pacman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.Random;

public class GridWorld {

     Texture wallTexture;
    private final ArrayList<Sprite> wallSprites;
    public GridWorld(Texture wallTexture){
        this.wallTexture = wallTexture;
        wallSprites = new ArrayList<>();
    }
    public  void  createGrid(int x, int y) {

        int[][] grid = new int[x][y];

        Random random = new Random();
        grid[random.nextInt(25)][10] = 1;
        grid[random.nextInt(25)][10] = 1;
        grid[random.nextInt(25)][10] = 1;
        grid[random.nextInt(25)][12] = 1;
        grid[random.nextInt(25)][11] = 1;
        grid[random.nextInt(25)][13] = 1;
        grid[random.nextInt(25)][13] = 1;
        grid[random.nextInt(25)][13] = 1;
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
