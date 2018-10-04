package cz.avb.aidev.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

public interface Entity {
    public static Animation BLANK_ANIMATION = new Animation(new Image[]{}, new int[]{} );


    float getX();
    float getY();

    void setX(double d);
    void setY(double d);

    default Animation getAnimation() {
        return BLANK_ANIMATION;
    }

}
