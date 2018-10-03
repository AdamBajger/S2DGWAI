package cz.avb.aidev;


import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

public class Game1 extends BasicGame {
    private TiledMap map;


    private Animation cell;


    public Game1() {
        super("Second Game Try");
        Image[] images =  new Image[8];
        for (int i = 1; i <= 8; i++) {
            try {
                images[i] = new Image("src/main/resources/sprites/cellBasic/stand0" + i + ".png");
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }

        cell = new Animation(
                images,
                new int[] {300, 300, 300, 300, 300, 300, 300, 300},
                true);



    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        map = new TiledMap("src/main/resources/Game1map01.tmx");



    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        map.render(0, 0);
    }
}
