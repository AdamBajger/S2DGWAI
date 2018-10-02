package cz.avb.aidev;


import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Game1 extends BasicGame {
    private TiledMap map;


    public Game1() {
        super("Second Game Try");
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
