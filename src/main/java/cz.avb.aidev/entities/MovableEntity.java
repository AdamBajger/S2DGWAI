package cz.avb.aidev.entities;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.sin;
import static java.lang.Math.cos;

public interface MovableEntity extends Entity {
    double getMass();
    double getVolume();
    default double getDragCoefficient() {
        return 1;
    }

    double getFacing();

    double getSpeedX();
    double getSpeedY();

    default double getSpeed() {
        return sqrt(pow(getSpeedX(), 2) + pow(getSpeedY(), 2) );
    }

    double getBaseAccelerationForce();

    default double getCurrentFrictionX(double environmentDensity) {
        return (environmentDensity * pow(getSpeedX(), 2) * getDragCoefficient() * pow(getVolume(), 2d/3d) ) / 2d;
    }
    default double getCurrentFrictionY(double environmentDensity) {
        return (environmentDensity * pow(getSpeedY(), 2) * getDragCoefficient() * pow(getVolume(), 2d/3d) ) / 2d;
    }
    default double getFriction(double environmentDensity) {
        return sqrt(pow(getCurrentFrictionX(environmentDensity), 2) + pow(getCurrentFrictionY(environmentDensity), 2) );
    }

    void accelerateByX(double x);
    void accelerateByY(double y);


    default void pushUp() {
        accelerateByY(- getBaseAccelerationForce()/getMass());
    }
    default void pushDown() {
        accelerateByY(getBaseAccelerationForce()/getMass());
    }
    default void pushRight() {
        accelerateByX(getBaseAccelerationForce()/getMass());
    }
    default void pushLeft() {
        accelerateByX(-getBaseAccelerationForce()/getMass());
    }

    /**
     * TODO: test that this works
     */
    default void accelerateForward() {
        accelerateByX(getBaseAccelerationForce() * sin(getFacing()));
        accelerateByY(getBaseAccelerationForce() * cos(getFacing()));
    }

    default void decelerateNaturally(double environmentDensity) {
        if (getSpeedX() > 0) {
            accelerateByX(- getCurrentFrictionX(environmentDensity));
        } else {
            accelerateByX(getCurrentFrictionX(environmentDensity));
        }

        if (getSpeedY() > 0) {
            accelerateByY(- getCurrentFrictionY(environmentDensity));

        } else {
            accelerateByY(getCurrentFrictionY(environmentDensity));
        }
    }
}
