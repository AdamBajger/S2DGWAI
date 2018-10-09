package cz.avb.aidev.entities;

public interface Organism {
    void executeInternalProcesses();

    boolean isAlive();

    double getEnergyCap();

    double getHealthCap();

    double getEnergy();

    double getHealth();

    int getAge();
}
