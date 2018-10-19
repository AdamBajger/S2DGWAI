package cz.avb.aidev.neural;

import java.util.Collection;

public class DataVector {
    private final double[] data;

    public DataVector(double[] data) {
        this.data = data;
    }

    public DataVector(Collection<Double> collection) {
        double[] d = new double[collection.size()];
        int counter = 0;
        for(Double D : collection) {
            d[counter] = D;
            counter++;
        }
        this.data = d;
    }

    public DataVector(Double[] data) {
        double[] doubles = new double[data.length];
        for(int i = 0; i < data.length; i++) {
            doubles[i] = data[i];
        }
        this.data = doubles;
    }

    public DataVector(double[] data) {
        this.data = data;
    }

    public DataVector(double[] data) {
        this.data = data;
    }
}
