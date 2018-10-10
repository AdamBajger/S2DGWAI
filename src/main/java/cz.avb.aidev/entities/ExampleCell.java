package cz.avb.aidev.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.Objects;

import static java.lang.StrictMath.sqrt;

/**
 * TODO: Extract interface for living cell (metabolic processes, food ingestion, energy regen, fat circulation, etc)
 * TODO: Add modifiers to pushDirection methods ---> ideally a cell is able to move any fast, but more costly
 * ---> depends on strength/agility
 *
 * Represents an example of a cell. Not perfect, not bad either. Just an example...
 *
 */
public class ExampleCell implements MovableEntity, Organism {
    private boolean isAlive = true;

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

    private int fat = 1000; // stored energy
    private double energy = 0.0001d;
    private double health = 100d;

    /**
     * Age in game iterations
     */
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
    public void executeInternalProcesses() {
        // age
        this.age++;
        if(this.age > getAgeCap()) {
            this.isAlive = false;
        }

        System.out.println(getEnergyCap());

        if(this.energy < getEnergyCap()) {
            if(fat > 0) {
                fat = fat - 1;
                energy = energy + 10e-6d;
            }


        }
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public double getFacing() {
        return facing;
    }
    @Override
    public float getX() {
        return (float)x;
    }
    @Override
    public float getY() {
        return (float)y;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }
    @Override
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

    /**
     * TODO: include intelligence into mass
     * @return
     */
    @Override
    public double getMass() {
        return (fat + strength + tenacity + (endurance / 2d)) * 10e-9d;
    }
    /**
     * TODO: include intelligence into volume
     * @return
     */
    @Override
    public double getVolume() {
        return ((fat*0.6) + (strength * 0.8) + (tenacity * 0.4)) * 10e-9d;
    }

    @Override
    public double getBaseAccelerationForce() {
        return (strength + sqrt(agility)) * 10e-9d * 10e-3d ; // 9 for the nanoNewtons, 3 just for fun
    }

    @Override
    public double getDragCoefficient() {
        return 1000d / sqrt(agility);
    }

    @Override
    public double getEnergyCap() {
        return (endurance + ((strength + agility)/2d)) * 10e-9d;
    }

    @Override
    public double getHealthCap() {
        return endurance + strength;
    }

    @Override
    public double getEnergy() {
        return energy;
    }

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public int getAge() {
        return age;
    }

    private int getAgeCap() {
        // TODO: dodělat to nějak chytřeji
        // ex: Buňka dostane na začátku číslo, označující zbývající iterace do smrti.
        // Z tohoto čísla se bude ubírat podle rozpoložení buňky.
        // Tlusté buňce víc, hubené míň, ve stresu víc, v klidu míň, apod.
        return 20 * 1000;
    }

    /**
     * This will try to consume an amount of energy. Returns true if enough energy was consumed
     * Returns false if not enough energy was present (thus not consumed)
     * Because we do not store energy in joules, but in nanojoules, we have to normalize the amount by multiplying
     * ---> the normalization is not neccesarily accurate.
     * @param energyToConsume The energy to be consumed in joules
     * @return true/flase
     */
    private boolean tryToSpendEnergy(double energyToConsume) {
        //energyToConsume *= 10e+6d;

        if (this.energy > energyToConsume) {
            this.energy = this.energy - energyToConsume;
            return true;
        } else {
            this.energy = 0;
            return false;
        }
    }

    @Override
    public void decay() {

        if (agility > 0) {
            agility--;
        }
        if (strength > 0) {
            strength--;
        }
        if (endurance > 0) {
            endurance--;
        }
        if (tenacity > 0) {
            tenacity--;
        }
        if (fat > 0) {
            fat--;
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

    @Override
    public String toString() {
        return "ExampleCell{" + "\n" +
                "x=" + x + "\n" +
                ", y=" + y + "\n" +
                ", facing=" + facing + "\n" +
                ", speedX=" + speedX + "\n" +
                ", speedY=" + speedY + "\n" +
                ", agility=" + agility + "\n" +
                ", strength=" + strength + "\n" +
                ", endurance=" + endurance + "\n" +
                ", tenacity=" + tenacity + "\n" +
                ", fat=" + fat + "\n" +
                ", energy=" + energy + "\n" +
                ", health=" + health + "\n" +
                ", age=" + age + "\n" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExampleCell that = (ExampleCell) o;
        return isAlive == that.isAlive &&
                Double.compare(that.x, x) == 0 &&
                Double.compare(that.y, y) == 0 &&
                Double.compare(that.facing, facing) == 0 &&
                Double.compare(that.speedX, speedX) == 0 &&
                Double.compare(that.speedY, speedY) == 0 &&
                agility == that.agility &&
                strength == that.strength &&
                endurance == that.endurance &&
                tenacity == that.tenacity &&
                Double.compare(that.fat, fat) == 0 &&
                Double.compare(that.energy, energy) == 0 &&
                Double.compare(that.health, health) == 0 &&
                age == that.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isAlive, x, y, facing, speedX, speedY, agility, strength, endurance, tenacity, fat, energy, health, age);
    }
}
