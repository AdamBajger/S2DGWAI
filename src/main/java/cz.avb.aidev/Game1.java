package cz.avb.aidev;


import cz.avb.aidev.entities.ExampleCell;
import cz.avb.aidev.entities.Entity;
import cz.avb.aidev.entities.MovableEntity;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Game1 extends BasicGame {
    private TiledMap map;

    private String message = "";
    private int timeDelta;

    private ExampleCell exampleCell;
    private Set<Entity> entities = new HashSet<>();

    private double environmentDensity = 1400d;


    public Game1() {
        super("Second Game Try");

    }


    @Override
    public void keyPressed(int key, char c) {
        //super.keyPressed(key, c);
        switch (key) {
            case Input.KEY_UP:
                exampleCell.pushUp();
                message = "Pressed UP";
                break;
            case Input.KEY_DOWN:
                exampleCell.pushDown();
                message = "Pressed DOWN";
                break;
            case Input.KEY_LEFT:
                exampleCell.pushLeft();
                message = "Pressed LEFT";
                break;
            case Input.KEY_RIGHT:
                exampleCell.pushRight();
                message = "Pressed RIGHT";
                break;
            default:
                message = "Unsupported yet";
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        //super.keyReleased(key, c);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        map = new TiledMap("src/main/resources/Game1map01.tmx");

        exampleCell = new ExampleCell(44f, 21f, 3.5, 500, 54, 11, 50);
        entities.add(exampleCell);



    }

    @Override
    public void update(GameContainer gc, int delta) {
        this.timeDelta = delta;

        //slowDown();




        // pressed controlls actions
        if (gc.getInput().isKeyDown(Keyboard.KEY_UP)) {
            exampleCell.pushUp();
        }
        if (gc.getInput().isKeyDown(Keyboard.KEY_DOWN)) {
            exampleCell.pushDown();
        }
        if (gc.getInput().isKeyDown(Keyboard.KEY_LEFT)) {
            exampleCell.pushLeft();
        }
        if (gc.getInput().isKeyDown(Keyboard.KEY_RIGHT)) {
            exampleCell.pushRight();
        }

        // move entities
        for(Entity e : entities.stream().filter(e -> e instanceof MovableEntity).collect(Collectors.toSet())) {
            MovableEntity me = (MovableEntity)e;

            me.decelerateNaturally(environmentDensity);

            // move them by speed - the speed is in metres, our desk is in nanometres.
            // since one pixel is like 1 nanometer, and we get the speed in metres, we need to modify it
            // --> it is multiplied by 10e+9
            me.setX(me.getX() + (me.getSpeedX() ));
            me.setY(me.getY() + (me.getSpeedY() ));

            // basic reflect off the walls TODO: Implement entity bouncing/elasticity
            while((double)me.getX() > (double)gc.getWidth()) {
                me.setX(((double)(gc.getWidth())*2d) - me.getX());
                me.accelerateByX(-me.getSpeedX() *2d );
            }
            while((double)me.getX() < 0d) {
                me.setX(- me.getX());
                me.accelerateByX(-me.getSpeedX() * 2d );
            }
            while((double)me.getY() > (double)gc.getHeight()) {
                me.setY(((double)(gc.getHeight())*2d) - me.getY());
                me.accelerateByY(-me.getSpeedY() *2d );
            }
            while((double)me.getY() < 0d) {
                me.setY(- me.getY());
                me.accelerateByY(-me.getSpeedY() * 2d );
            }


            //  does work, not satisfying
            //me.accelerateByX(-(me.getSpeedX()/40));
            //me.accelerateByY(-(me.getSpeedY()/40));



        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        map.render(0, 0);
        for(Entity e : entities) {
            e.getAnimation().draw(e.getX() - (e.getAnimation().getWidth()/2f), e.getY() - (e.getAnimation().getHeight()/2f));
        }
        //graphics.drawString(message, 100f, 100f);
        //graphics.drawString(String.valueOf(timeDelta), 100f, 115f);
        graphics.drawString(String.valueOf(exampleCell.getSpeedX()) + " : " + String.valueOf(exampleCell.getSpeedY()), 10f, 30f);
        graphics.drawString(exampleCell.toString(), 10f, 45f);



        //slowDown();
    }

    private void slowDown() {
        // slowing down the proccess
        String pls = "pls";
        for (int i = 0; i < 1000; i++) {
            pls += ".";
        }
        //System.out.print(pls);
    }

}
