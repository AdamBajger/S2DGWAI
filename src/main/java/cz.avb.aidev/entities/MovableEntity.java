package cz.avb.aidev.entities;

public interface MovableEntity extends Entity {
    double getSpeedX();

    double getSpeedY();

    void accelerateByX(double x);

    void accelerateByY(double y);
}
