package cz.avb.aidev.neural;

public class ScoredNet implements Comparable<ScoredNet> {
    private final NeuralNet net;
    private double score;

    public ScoredNet(NeuralNet net) {
        this.net = net;
    }

    public void assignScore(double score) {
        this.score = score;
    }

    @Override
    public int compareTo(ScoredNet o) {
        double diff = (this.score - o.score);
        if (diff == 0d) {
            return 0;
        } else return (int)(diff / Math.abs(diff));
    }
}
