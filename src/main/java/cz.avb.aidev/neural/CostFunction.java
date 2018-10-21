package cz.avb.aidev.neural;

/**
 * This represents a cost function
 * Class implementing this interface should contain its own testing data and define rules for scoring.
 * @author AVB
 */
public interface CostFunction {
    double getScoreForNeuralNet(NeuralNet neuralNet);
}
