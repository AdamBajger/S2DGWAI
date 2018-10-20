package cz.avb.aidev.neural;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class NeuralNetEvolver {
    List<EvolvingNeuralNet> bestNets = new LinkedList<>();
    Set<ScoredNet> scoredNets = new TreeSet<>();
    double[] evolutionPoolConfiguration;
    CostFunction costFunction;

    public NeuralNetEvolver(EvolvingNeuralNet initialNet, CostFunction costFunction, int numberOfBestNetsToEvolve, double... configuration) {
        this.bestNets.add(initialNet);
        this.costFunction = costFunction;
        this.evolutionPoolConfiguration = configuration;
    }

    public void evolveNewGeneration() {

    }

}
