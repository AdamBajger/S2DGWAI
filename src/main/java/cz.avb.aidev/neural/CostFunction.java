package cz.avb.aidev.neural;

public interface CostFunction {
    public double getScoreForNeuralNet(NeuralNet neuralNet, DataVector inputData);
}
