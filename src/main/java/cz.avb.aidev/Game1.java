package cz.avb.aidev;


import cz.avb.aidev.entities.Dummy;
import org.newdawn.slick.*;
import org.newdawn.slick.command.*;
import org.newdawn.slick.tiled.TiledMap;

import java.util.HashSet;
import java.util.Set;

public class Game1 extends BasicGame implements InputProviderListener {
    private TiledMap map;

    private String message = "";
    private int timeDelta;

    private Dummy dummy;
    private Set<Dummy> dummies = new HashSet<>();

    private InputProvider inputProvider;


    private static final Command ACC_U = new BasicCommand("accU");
    private static final Command ACC_D = new BasicCommand("accD");
    private static final Command ACC_L = new BasicCommand("accL");
    private static final Command ACC_R = new BasicCommand("accR");


    public Game1() {
        super("Second Game Try");

    }


    @Override
    public void controlPressed(Command command) {
        //message = "Pressed: "+command;


    }
    @Override
    public void controlReleased(Command command) {
        //message = "Released: "+command;
    }

    @Override
    public void keyPressed(int key, char c) {
        //super.keyPressed(key, c);
        switch (key) {
            case Input.KEY_UP:
                dummy.accelerateByY(-0.01f);
                message = "Pressed UP";
                break;
            case Input.KEY_DOWN:
                dummy.accelerateByY(0.01f);
                message = "Pressed DOWN";
                break;
            case Input.KEY_LEFT:
                dummy.accelerateByX(-0.01f);
                message = "Pressed LEFT";
                break;
            case Input.KEY_RIGHT:
                dummy.accelerateByX(0.01f);
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

        inputProvider = new InputProvider(gameContainer.getInput());
        inputProvider.addListener(this);

        dummy = new Dummy(44f, 21f);
        dummies.add(dummy);

        // bind commands
        inputProvider.bindCommand(new KeyControl(Input.KEY_UP), ACC_U);
        inputProvider.bindCommand(new KeyControl(Input.KEY_DOWN), ACC_D);
        inputProvider.bindCommand(new KeyControl(Input.KEY_LEFT), ACC_L);
        inputProvider.bindCommand(new KeyControl(Input.KEY_RIGHT), ACC_R);


    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        this.timeDelta = delta;
        String pls = "pls";
        for (int i = 0; i < 5000; i++) {
            pls += ".";

        }

        for(Dummy d : dummies) {
            // move them by speed
            d.setX(d.getX() + (d.getSpeedX()));
            d.setY(d.getY() + (d.getSpeedY()));
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        map.render(0, 0);
        for(Dummy d : dummies) {
            d.getAnimation().draw(d.getX(), d.getY());
        }
        graphics.drawString(message, 100f, 100f);
        graphics.drawString(String.valueOf(timeDelta), 100f, 150f);
        graphics.drawString(String.valueOf(dummy.getSpeedX()) + " : " + String.valueOf(dummy.getSpeedY()), 100f, 200f);
    }

}
