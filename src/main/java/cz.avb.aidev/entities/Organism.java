package cz.avb.aidev.entities;

public interface Organism {
    void executeInternalProcesses();

    boolean isAlive();

    double getEnergyCap();

    double getHealthCap();

    double getEnergy();

    double getHealth();

    int getAge();

    /**
     * Removes slowly the mass of the cell, derading its stats and everything.
     * Usually used after a cell is dead to remove it eventually from the game.
     */
    void decay();
}
