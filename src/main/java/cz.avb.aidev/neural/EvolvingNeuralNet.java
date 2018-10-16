package cz.avb.aidev.neural;

public interface EvolvingNeuralNet<inputType, outputType> extends NeuralNet<inputType, outputType> {
    EvolvingNeuralNet deriveMutatedDescendant(double maxEvolutionStep);
}
