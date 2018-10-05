package cz.avb.aidev.entities;

import static java.lang.Math.*;

public interface MovableEntity extends Entity {

    /**
     * Gets mass of the object in kilograms (standardized unit)
     * @return how much entity weights in kilograms
     */
    double getMass();

    /**
     * Gets the volume of the entity in meters cubed (m^3)
     * @return volume of entity
     */
    double getVolume();

    /**
     * Returns the largest cross-ection among cross-sections of the object with planes
     * perpendicular to the direction of the movement.
     * Default value is calculated as if the object was a sphere (defined by Volume)
     * @return cross-section for drag calculation
     */
    default double getCrossSection() {
        return (pow((9d * PI)/16d, 1d/3d)) * (pow(getVolume(), 2d/3d));
    }
    default double getDragCoefficient() {
        return 0.5;
    }

    double getFacing();

    double getSpeedX();
    double getSpeedY();

    default double getSpeed() {
        return sqrt(pow(getSpeedX(), 2) + pow(getSpeedY(), 2) );
    }

    /**
     * Returns the acceleration force, that is the entity cappable of, in Newtons
     * @return base acceleration force
     */
    double getBaseAccelerationForce();

    default double getCurrentFrictionX(double environmentDensity) {
        return (environmentDensity * pow(getSpeedX(), 2) * getDragCoefficient() * getCrossSection() ) / 2d;
    }
    default double getCurrentFrictionY(double environmentDensity) {
        return (environmentDensity * pow(getSpeedY(), 2) * getDragCoefficient() * getCrossSection() ) / 2d;
    }
    default double getFriction(double environmentDensity) {
        return sqrt(pow(getCurrentFrictionX(environmentDensity), 2) + pow(getCurrentFrictionY(environmentDensity), 2) );
    }

    void accelerateByX(double x);
    void accelerateByY(double y);


    /**
     * Applies a force that the cell is cappable of on itself in a direction
     * The acceleration is counted relative to the mass of the object and a time of 1 tick
     */
    default void pushUp() {
        accelerateByY(- getBaseAccelerationForce()/getMass());
    }
    /**
     * Applies a force that the cell is cappable of on itself in a direction
     * The acceleration is counted relative to the mass of the object and a time of 1 tick
     */
    default void pushDown() {
        accelerateByY(getBaseAccelerationForce()/getMass());
    }
    /**
     * Applies a force that the cell is cappable of on itself in a direction
     * The acceleration is counted relative to the mass of the object and a time of 1 tick
     */
    default void pushRight() {
        accelerateByX(getBaseAccelerationForce()/getMass());
    }
    /**
     * Applies a force that the cell is cappable of on itself in a direction
     * The acceleration is counted relative to the mass of the object and a time of 1 tick
     */
    default void pushLeft() {
        accelerateByX(- getBaseAccelerationForce()/getMass());
    }

    /**
     * TODO: test that this works
     */
    default void accelerateForward() {
        accelerateByX(getBaseAccelerationForce() * sin(getFacing()));
        accelerateByY(getBaseAccelerationForce() * cos(getFacing()));
    }

    default void decelerateNaturally(double environmentDensity) {
        double frictionDecelerationX = getCurrentFrictionX(environmentDensity)/getMass(); // relative to mass and 1 tick
        double frictionDecelerationY = getCurrentFrictionY(environmentDensity)/getMass(); // relative to mass and 1 tick
        double speedX = getSpeedX();
        double speedY = getSpeedY();
        System.out.println(frictionDecelerationX + frictionDecelerationY);


        if (frictionDecelerationX > abs( speedX)) {
            accelerateByX(-speedX);
        } else {
            if (speedX > 0) {
                accelerateByX(-frictionDecelerationX);
            } else {
                accelerateByX(frictionDecelerationX);
            }
        }

        if (frictionDecelerationY > abs( speedY)) {
            accelerateByY(-speedY);
        } else {
            if (speedY > 0) {
                accelerateByY(-frictionDecelerationY);
            } else {
                accelerateByY(frictionDecelerationY);
            }
        }
    }
}
