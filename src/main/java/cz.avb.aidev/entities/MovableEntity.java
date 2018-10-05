package cz.avb.aidev.entities;

import static java.lang.Math.pow;
import static java.lang.Math.PI;
import static java.lang.Math.sqrt;
import static java.lang.Math.abs;
import static java.lang.Math.sin;
import static java.lang.Math.cos;

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

    /**
     * The drag coefficient manifests aspects like the shape of the Entity, character of its surface, its adhesion and
     * other properties that affect how hard it is for the fluid to flow around the Entity
     * Drag coefficient is a relative value that is just a part of the drag equation
     * Default value is 1, which represents some regular drag for a sphere (I guess?)
     * @return drag coefficient value
     */
    default double getDragCoefficient() {
        return 1;
    }

    /**
     * Returns facing of the entity
     * @return value from interval <0; 360)
     */
    double getFacing();

    /**
     * Returns speed in direction X metres per tick
     * @return speed in metres per tick
     */
    double getSpeedX();
    /**
     * Returns speed in direction Y metres per tick
     * @return speed in metres per tick
     */
    double getSpeedY();

    default double getSpeed() {
        return sqrt(pow(getSpeedX(), 2) + pow(getSpeedY(), 2) );
    }

    /**
     * Returns the acceleration force, that is the entity cappable of, in Newtons
     * @return base acceleration force
     */
    double getBaseAccelerationForce();

    /**
     * Calculates the friction in the direction X based on the environment density
     * @param environmentDensity the environment density
     * @return the friction force in direction X
     */
    default double getCurrentFrictionX(double environmentDensity) {
        return (environmentDensity * pow(getSpeedX(), 2) * getDragCoefficient() * getCrossSection() ) / 2d;
    }
    /**
     * Calculates the friction in the direction Y based on the environment density
     * @param environmentDensity the environment density
     * @return the friction force in direction Y
     */
    default double getCurrentFrictionY(double environmentDensity) {
        return (environmentDensity * pow(getSpeedY(), 2) * getDragCoefficient() * getCrossSection() ) / 2d;
    }

    /**
     * Calculates absolute friction in the direction of the movement. The final vector of friction force based on given
     * environment density.
     * @param environmentDensity the density of the environment the cell resides in
     * @return the absolute friction force
     */
    default double getFriction(double environmentDensity) {
        return sqrt(pow(getCurrentFrictionX(environmentDensity), 2) + pow(getCurrentFrictionY(environmentDensity), 2) );
    }

    /**
     * Modifies speed in direction X by a given value by addition
     * @param x value to add to speed
     */
    void accelerateByX(double x);

    /**
     * Modifies speed in direction Y by a given value by addition
     * @param y value to add to the speed
     */
    void accelerateByY(double y);


    /**
     * Applies a force that the cell is cappable of on itself in a direction -Y
     * The acceleration is counted relative to the mass of the object and a time of 1 tick
     */
    void pushUp();
    /**
     * Applies a force that the cell is cappable of on itself in a direction Y
     * The acceleration is counted relative to the mass of the object and a time of 1 tick
     */
    void pushDown();
    /**
     * Applies a force that the cell is cappable of on itself in a direction X
     * The acceleration is counted relative to the mass of the object and a time of 1 tick
     */
    void pushRight();
    /**
     * Applies a force that the cell is cappable of on itself in a direction -X
     * The acceleration is counted relative to the mass of the object and a time of 1 tick
     */
    void pushLeft();

    /**
     * TODO: test that this works
     * TODO: implement rotation and so on
     */
    default void accelerateForward() {
        accelerateByX(getBaseAccelerationForce() * sin(getFacing()));
        accelerateByY(getBaseAccelerationForce() * cos(getFacing()));
    }

    /**
     * Naturally decelerates based on friction due to object movement.
     * If the deceleration amount is greater that actual speed, entity is stopped
     * @param environmentDensity the density of the environment the entity resides in
     */
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
