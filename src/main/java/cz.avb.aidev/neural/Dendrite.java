package cz.avb.aidev.neural;

public class Dendrite {
    private final Neuron originalNeuron;
    private double strength = 1;

    /**
     * Creates new dendrite attached to particular neural
     * @param originalNeuron neural of origin
     */
    public Dendrite(Neuron originalNeuron) {
        this.originalNeuron = originalNeuron;
    }

    /**
     * Creates new dendrite attached to prticular neural with particular strength
     * @param originalNeuron neural of origin
     * @param strength value affecting transmitted signal strength
     */
    public Dendrite(Neuron originalNeuron, int strength) {
        this.originalNeuron = originalNeuron;
        this.strength = strength;
    }

    /**
     * Propagates signal from connected neural to its neural of origin
     */
    public void propagateSignal(double signal) {
        originalNeuron.acceptSignal(signal * strength);
    }

    public void evolve(double value) {
        strength += value;
    }

}
