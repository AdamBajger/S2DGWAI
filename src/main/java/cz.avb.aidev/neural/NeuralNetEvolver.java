package cz.avb.aidev.neural;

import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

public class NeuralNetEvolver {
    private final List<EvolvingNeuralNet> bestNets;
    private int numberOfBestNetsToEvolve;
    private NavigableSet<ScoredNet> scoredNets;
    private double[] evolutionPoolConfiguration;
    private CostFunction costFunction;

    /**
     * Creates new instance of the evolver.
     * @param initialNet this represents a model net, from which it will derive evolved nets.
     * @param costFunction represents a cost function, which tests the net's performance on various data.
     *                     It is used to assign a score to a net which allows us to compare derived nets.
     * @param numberOfBestNetsToEvolve This number tells us how many 'bets nets' are picked for the creation
     *                                 of the next generation. For example if the number is 3, then in each iteration,
     *                                 3 best nets are picke and from each of them is derived new generation of nets.
     *                                 These 3 generations create the evolved generation of new nets, which are then
     *                                 assigned a score and the proces is repeated.
     * @param configuration an array of numbers. Represents the variation in evolution steps sizes.
     *                      For example with a configuration of: <br/>
     *                      {@code [0.05, 0.05, 0.05, 0.05, 0.1, 0.1, 0.1, 0.2, 1]}
     *                      <br/> each generation of evolved nets
     *                      (separately for each one of the 'best nets' picked for next generation creation)
     *                      will contain <b>four</b> nets evolved with the maxEvolutionStep 0.05,
     *                      <b>three</b> nets evolved with the maxEvolutionStep 0.1,
     *                      <b>one</b> net evolved with the maxEvolutionStep 0.2 and <b>one</b> net evolved
     *                      with the maxEvolutionStep 1.
     */
    public NeuralNetEvolver(EvolvingNeuralNet initialNet, CostFunction costFunction, int numberOfBestNetsToEvolve, double... configuration) {
        bestNets = new LinkedList<>();
        for (int i = 0; i < numberOfBestNetsToEvolve; i++) {
            this.bestNets.add(initialNet);
        }
        this.numberOfBestNetsToEvolve = numberOfBestNetsToEvolve;
        this.costFunction = costFunction;
        this.evolutionPoolConfiguration = configuration;
        scoredNets = new TreeSet<>();
    }

    public void evolveNewGeneration() {
        for (EvolvingNeuralNet enn : bestNets) {
            for (double anEvolutionPoolConfiguration : evolutionPoolConfiguration) {
                scoredNets.add( new ScoredNet(enn.deriveMutatedDescendant(anEvolutionPoolConfiguration), costFunction) );
            }
        }
        this.bestNets.clear();
        for (int i = 0; i < numberOfBestNetsToEvolve; i++) {
            bestNets.add((EvolvingNeuralNet) scoredNets.pollLast().getNet());
        }
        scoredNets.clear();

    }

    public void evolveGenerations(int numberOfGenerations) {
        for (int i = 0; i < numberOfGenerations; i++) {
            evolveNewGeneration();
        }
    }

    public List<EvolvingNeuralNet> getBestNets() {
        return new LinkedList<>(bestNets);
    }
}
