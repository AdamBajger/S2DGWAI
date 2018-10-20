package cz.avb.aidev.neural;

public interface EvolvingNeuralNet extends NeuralNet {
    EvolvingNeuralNet deriveMutatedDescendant(double maxEvolutionStep);
}
