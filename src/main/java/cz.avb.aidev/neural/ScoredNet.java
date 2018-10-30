package cz.avb.aidev.neural;

public class ScoredNet implements Comparable<ScoredNet> {
    private final NeuralNet net;
    private double score;

    public ScoredNet(NeuralNet net) {
        this.net = net;
    }
    public ScoredNet(NeuralNet net, double score) {
        this(net);
        assignScore(score);
    }
    public ScoredNet(NeuralNet net, CostFunction costFunction) {
        this(net);
        assignScore(costFunction.getScoreForNeuralNet(net));
    }

    public ScoredNet assignScore(double score) {
        this.score = score;
        return this;
    }

    public NeuralNet getNet() {
        return net;
    }

    public double getScore() {
        return score;
    }

    @Override
    public int compareTo(ScoredNet o) {
        double diff = (this.score - o.score);
        if (diff == 0d) {
            return 0;
        } else return (int)(diff / Math.abs(diff));
    }
}
