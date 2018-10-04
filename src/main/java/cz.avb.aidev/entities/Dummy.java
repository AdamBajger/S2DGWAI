package cz.avb.aidev.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Dummy implements MovableEntity {
    private double x;
    private double y;

    private double speedX;
    private double speedY;


    private Animation animation;

    public Dummy(float x, float y) {
        this.x = x;
        this.y = y;

        Image[] images =  new Image[8];
        for (int i = 1; i <= 8; i++) {
            try {
                images[i-1] = new Image("src/main/resources/sprites/cellBasic/stand0" + i + ".png");
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }

        this.animation = new Animation(
                images,
                new int[] {200, 200, 200, 200, 200, 200, 200, 200},
                true);

    }

    public float getX() {
        return (float)x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public float getY() {
        return (float)y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public double getSpeedX() {
        return speedX;
    }

    @Override
    public double getSpeedY() {
        return speedY;
    }

    public Animation getAnimation() {
        return animation;
    }

    @Override
    public void accelerateByX(double x) {
        this.speedX = this.speedX + x;
    }

    @Override
    public void accelerateByY(double y) {
        this.speedY = this.speedY + y;
    }
}
