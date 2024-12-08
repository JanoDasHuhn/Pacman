package de.janiswolf._pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Player extends Entity {

    public Player(Texture texture, float startX, float startY) {
        super(texture, startX, startY, 0, 0); // Initial keine Geschwindigkeit
}

    public static void playerInput(float velocity) {
    }

    // Spielerinput
    private void playerInput(float velocity) {
        float velocity = 0.2f; //Geschwindigkeit des Chars
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) { //setzt X auf 0, Y auf 0.2
            Player.setVelocityX(0);
            Player.setVelocityY(velocity);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) { //setzt X auf 0, Y auf -0,2
            Player.setVelocityX(0);
            Player.setVelocityY(-velocity);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) { //setzt X auf -0.2, Y auf 0
            Player.setVelocityX(-velocity);
            Player.setVelocityY(0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) { //setzt X auf 0.2, Y auf 0
            Player.setVelocityX(velocity);
            Player.setVelocityY(0);
        } else {    //Char bewegt sich nicht mehr, wenn nichts gedrückt wird
            Player.setVelocityX(0);
            Player.setVelocityY(0);
        }

    }
    private void logic() {
        float nextX = Player.getSprite().getX() + Player.getVelocityX(); //berechnet nächste X Position des Spielers
        float v = Player.getVelocityX();
        float nextY = Player.getSprite().getY() + Player.getVelocityY(); //berechnet nächste Y Position des Spielers
        float v1 =  Player.getVelocityY();
        //setzt Rect. vor dem Spieler
        Rectangle nextPlayerBounds = new Rectangle(nextX, nextY, Player.getSprite().getWidth(), Player.getSprite().getHeight());
        //checkt Kollision mit den Wänden
        for ( Sprite wall : gridWorld.getWallSprites()){
            if(nextPlayerBounds.overlaps(wall.getBoundingRectangle())){ //stoppt den Spieler, wenn er eine Wand berührt
                Player.setVelocityX(0);
                Player.setVelocityY(0);
                return;
            }
        }
        //wenn keine Kollision, bewegt sich der Spieler
        Player.move();
    }
