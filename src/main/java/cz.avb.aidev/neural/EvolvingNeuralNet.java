package cz.avb.aidev.neural;

public interface EvolvingNeuralNet<T> {
    T deriveMutatedDescendant(double maxEvolutionStep);
}
