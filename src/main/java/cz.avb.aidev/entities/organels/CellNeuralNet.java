package cz.avb.aidev.entities.organels;

import cz.avb.aidev.neural.CDDMNN;
import org.jblas.DoubleMatrix;

public class CellNeuralNet extends CDDMNN implements OrganicNeuralNet {
    private final double mass;
    private final double volume;
    private final double energyCost;

    public CellNeuralNet(int inputLength, int outputLength, int hiddenLayersCount, int hiddenLayerLength, DoubleMatrix inputLayer, DoubleMatrix inputLayerThresholds, DoubleMatrix[] hiddenLayers, DoubleMatrix[] hiddenLayersThresholds, DoubleMatrix outputLayer, DoubleMatrix outputLayerThresholds) {
        super(inputLength, outputLength, hiddenLayersCount, hiddenLayerLength, inputLayer, inputLayerThresholds, hiddenLayers, hiddenLayersThresholds, outputLayer, outputLayerThresholds);
        this.mass = ((double)(inputLength + outputLength + (hiddenLayersCount*hiddenLayerLength)) * 1e-11d);
        this.energyCost = mass*10d;
        this.volume = mass;
    }

    @Override
    public double getEnergyCost() {
        return energyCost;
    }

    @Override
    public double getMass() {
        return mass;
    }

    @Override
    public double getVolume() {
        return volume;
    }
}
