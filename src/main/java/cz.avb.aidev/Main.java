package cz.avb.aidev;


import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {
    public static void main (String[] args) {
        Game1 game = new Game1();

        try {
            AppGameContainer container = new AppGameContainer(game);
            container.start();


        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
