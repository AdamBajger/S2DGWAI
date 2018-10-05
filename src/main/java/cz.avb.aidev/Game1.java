package cz.avb.aidev;


import cz.avb.aidev.entities.ExampleCell;
import cz.avb.aidev.entities.Entity;
import cz.avb.aidev.entities.MovableEntity;
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

    private double environmentDensity = 12d;


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

        exampleCell = new ExampleCell(44f, 21f, 3.5, 0.917, 0.8779, 11.000235, 5.054);
        entities.add(exampleCell);



    }

    @Override
    public void update(GameContainer gc, int delta) {
        this.timeDelta = delta;

        //slowDown();


        // pressed controlls actions


        // move entities
        for(Entity e : entities.stream().filter(e -> e instanceof MovableEntity).collect(Collectors.toSet())) {
            MovableEntity me = (MovableEntity)e;
            // move them by speed
            me.setX(me.getX() + (me.getSpeedX()));
            me.setY(me.getY() + (me.getSpeedY()));

            // TODO: sounds good, does not work
            //me.accelerateByX(-(me.getSpeedX()/0.332d));
            //me.accelerateByY(-(me.getSpeedY()/0.332d));


            //me.decelerateNaturally(environmentDensity);
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        map.render(0, 0);
        for(Entity e : entities) {
            e.getAnimation().draw(e.getX(), e.getY());
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
        System.out.print(pls);
    }

}
