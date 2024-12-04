package de.janiswolf._pacman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class GridWorld {

     Texture wallTexture;
    private ArrayList<Sprite> wallSprites;
    public GridWorld(Texture wallTexture){
        this.wallTexture = wallTexture;
        wallSprites = new ArrayList<>();
    }
    public  void  createGrid(int x, int y) {

        int[][] grid = new int[x][y];

        grid[10][10] = 1;
        grid[9][10] = 1;
        grid[11][10] = 1;
        grid[10][12] = 1;
        grid[10][11] = 1;


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
