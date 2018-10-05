package cz.avb.aidev.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import static java.lang.StrictMath.sqrt;

/**
 * TODO: Extract interface for living cell (metabolic processes, food ingestion, energy regen, fat circulation, etc)
 * Represents an example of a cell. Not perfect, not bad either. Just an example...
 */
public class ExampleCell implements MovableEntity {
    private double x;
    private double y;
    private double facing;

    private double speedX = 0;
    private double speedY = 0;

    /**
     * basic agility, affects movement and maybe more TODO: make it reduce friction
     * unit: nanograms
     */
    private int agility; // nanograms of agility focused mass

    /**
     * strength - affects attack, movement acceleration
     * unit: nanograms
     */
    private int strength; // nanograms of strength focused mass

    /**
     * what the cell withstands internally (energy efficiency, regen., poison resistance, etc)
     * each endurance point weights 1/2 of a nanogram
     */
    private int endurance; // halves of nanograms of endurance focused mass

    /**
     * what the cell withstands externally (mechanical damage, impact damage etc)
     */
    private int tenacity; // nanograms of tenacity focused mass

    private double fat = 0d; // stored energy
    private double energy = 0d;
    private double health = 0d;

    private int age = 0;


    private Animation animation;

    public ExampleCell(float x, float y, double facing, int agility, int strength, int endurance, int tenacity) {
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
        return (fat + strength + tenacity + (endurance / 2d)) * 10e-9d;
    }

    @Override
    public double getVolume() {
        return (fat/1.4d + strength + tenacity/2d)*10e-9d;
    }

    @Override
    public double getBaseAccelerationForce() {
        return (strength + sqrt(agility)) * 10e-9d * 10e-2d; // 9 for the nanoNewtons, 2 just to decrease minimum speed
    }

    @Override
    public double getDragCoefficient() {
        return 1d/agility;
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

    /**
     * This will try to consume an amount of energy. Returns true if enough energy was consumed
     * Returns false if not enough energy was present (thus not consumed)
     * @return true/flase
     */
    private boolean tryToSpendEnergy(double energyToConsume) {
        if (this.energy > energyToConsume) {
            this.energy = this.energy - energyToConsume;
            return true;
        } else {
            this.energy = 0;
            return false;
        }
    }

    @Override
    public void pushUp() {
        double f = getBaseAccelerationForce();
        if(tryToSpendEnergy(f)) {

            accelerateByY(-f / getMass());
        }
    }
    @Override
    public void pushDown() {
        double f = getBaseAccelerationForce();
        if(tryToSpendEnergy(f)) {
            accelerateByY(f / getMass());
        }
    }
    @Override
    public void pushRight() {
        double f = getBaseAccelerationForce();
        if(tryToSpendEnergy(f)) {
            accelerateByX(f / getMass());
        }
    }
    @Override
    public void pushLeft() {
        double f = getBaseAccelerationForce();
        if(tryToSpendEnergy(f)) {
            accelerateByX(-f / getMass());
        }
    }
}
