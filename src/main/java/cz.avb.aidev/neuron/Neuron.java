package cz.avb.aidev.neuron;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Neuron {
    private static final Random r = new Random();
    private double potential = 0;
    private double threshold = r.nextDouble();
    private final Set<Dendrite> dendriteSet = new HashSet<>();
    private boolean isAlive = true;

    public void acceptSignal(double signal) {
        potential += signal;
    }

    public double getPotential() {
        return potential;
    }

    private double getActivePotential() {
        return Math.max(0, potential - threshold);
    }

    public Set<Dendrite> propagateSignal() {
        double signal = getActivePotential();
        potential = 0;
        for (Dendrite d : dendriteSet) {
            d.propagateSignal(signal);
        }
        return dendriteSet;
    }

    public void evolve(double bound) {
        threshold += (r.nextDouble() - 0.5) * bound;
        for (Dendrite d : dendriteSet) {
            d.evolve((r.nextDouble() - 0.5) * bound);
        }
    }
}
