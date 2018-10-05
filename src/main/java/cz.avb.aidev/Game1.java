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

    private double environmentDensity = 1200d;


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

        exampleCell = new ExampleCell(44f, 21f, 3.5, 5.917, 4.8779, 11.000235, 50.054);
        entities.add(exampleCell);



    }

    @Override
    public void update(GameContainer gc, int delta) {
        this.timeDelta = delta;

        slowDown();




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

            // move them by speed
            me.setX(me.getX() + (me.getSpeedX()));
            me.setY(me.getY() + (me.getSpeedY()));

            // basic reflect off the walls TODO: Implement entity bouncing/elasticity
            while(me.getX() > gc.getWidth()) {
                me.setX((gc.getWidth()*2) - me.getX());
                me.accelerateByX(-me.getSpeedX() *2 );
            }
            while(me.getX() < 0) {
                me.setX(- me.getX());
                me.accelerateByX(-me.getSpeedX() * 2 );
            }
            while(me.getY() > gc.getHeight()) {
                me.setY((gc.getHeight()*2) - me.getY());
                me.accelerateByY(-me.getSpeedY() *2 );
            }
            while(me.getY() < 0) {
                me.setY(- me.getY());
                me.accelerateByY(-me.getSpeedY() * 2 );
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
        graphics.drawString(message, 100f, 100f);
        graphics.drawString(String.valueOf(timeDelta), 100f, 150f);
        graphics.drawString(String.valueOf(exampleCell.getSpeedX()) + " : " + String.valueOf(exampleCell.getSpeedY()), 100f, 200f);



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
