package de.janiswolf._pacman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.Random;

public class GridWorld {

     ArrayList<Texture> wallTextures;
     private int worldSize;
    private final ArrayList<Sprite> wallSprites;
    public GridWorld( int worldSize){

        wallSprites = new ArrayList<>();
        this.worldSize = worldSize;
        wallTextures = new ArrayList<>();
        wallTextures.add(new Texture("wall.png"));
        wallTextures.add(new Texture("wall1.png"));
        wallTextures.add(new Texture("wall2.png"));
        wallTextures.add(new Texture("wall3.png"));
        wallTextures.add(new Texture("wall4.png"));
        wallTextures.add(new Texture("wall5.png"));
    }
    public  void  createGrid(int x, int y) {

        int[][] grid = new int[x][y];

        Random random = new Random();
        for (int i = 0; i < worldSize * 0.1 * worldSize; i++){

            grid[random.nextInt(worldSize)][random.nextInt(6,worldSize)] = 1;
        }
        // Es wird mithilfe einer Forschleife so lange iteriert bis die Anzahl der Itierationen ein Zehntel der Weltgröße erreichen, dann wird jeweils auf einer Zufä
        for (int i = 0; i < worldSize;i++){
            grid[i][0] = 1;
            grid[i][worldSize -1] = 1;
            grid[worldSize -1][i] = 1;
            grid[0][i] = 1;
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    Sprite wallSprite = new Sprite(wallTextures.get(random.nextInt(wallTextures.size())));
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
