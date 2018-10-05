package cz.avb.aidev.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import static java.lang.StrictMath.sqrt;

public class ExampleCell implements MovableEntity {
    private double x;
    private double y;
    private double facing;

    private double speedX = 0;
    private double speedY = 0;

    private double agility; // basic agility, affects movement and maybe more TODO: make it reduce friction
    private double strength; // strength - affects attack, movement acceleration
    private double endurance; // what the cell withstands internally (energy efficiency, regen., poison resistance, etc)
    private double tenacity; // what the cell withstands externally (mechanical damage, impact damage etc)

    private double fat = 0d; // stored energy
    private double energy = 0d;
    private double health = 0d;

    private int age = 0;


    private Animation animation;

    public ExampleCell(float x, float y, double facing, double agility, double strength, double endurance, double tenacity) {
        this.x = x;
        this.y = y;
        this.facing = facing;
        this.agility = agility;
        this.strength = strength;
        this.endurance = endurance;
        this.tenacity = tenacity;

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

    @Override
    public double getFacing() {
        return facing;
    }

    public float getX() {
        return (float)x;
    }
    public float getY() {
        return (float)y;
    }

    public void setX(double x) {
        this.x = x;
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

    @Override
    public double getMass() {
        return fat + strength + tenacity;
    }

    @Override
    public double getVolume() {
        return fat + ((strength + tenacity)/2d);
    }

    @Override
    public double getBaseAccelerationForce() {
        return sqrt(agility) + strength;
    }

    public double getEnergyCap() {
        return endurance + ((strength + agility)/2d);
    }

    public double getHealthCap() {
        return endurance + strength;
    }

    public double getEnergy() {
        return energy;
    }

    public double getHealth() {
        return health;
    }

    public int getAge() {
        return age;
    }
}
