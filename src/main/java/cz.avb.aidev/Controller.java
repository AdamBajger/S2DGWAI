package cz.avb.aidev;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.command.*;

public class Controller implements InputProviderListener {
    private static final Command ACC_U = new BasicCommand("accU");
    private static final Command ACC_D = new BasicCommand("accD");
    private static final Command ACC_L = new BasicCommand("accL");
    private static final Command ACC_R = new BasicCommand("accR");

    private final GameContainer gameContainer;

    public Controller(GameContainer gameContainer) {
        this.gameContainer = gameContainer;

        // for event based stu, idk
        InputProvider inputProvider = new InputProvider(gameContainer.getInput());
        inputProvider.addListener(this);

        // bind commands
        inputProvider.bindCommand(new KeyControl(Input.KEY_UP), ACC_U);
        inputProvider.bindCommand(new KeyControl(Input.KEY_DOWN), ACC_D);
        inputProvider.bindCommand(new KeyControl(Input.KEY_LEFT), ACC_L);
        inputProvider.bindCommand(new KeyControl(Input.KEY_RIGHT), ACC_R);
    }

    @Override
    public void controlPressed(Command command) {
        //message = "Pressed: "+command;
        if(command == ACC_D) {
            gameContainer.getGraphics().drawString("Fuck", 100f, 120f);
        }


    }
    @Override
    public void controlReleased(Command command) {
        //message = "Released: "+command;
    }




}
