package cz.avb.aidev.neural;

import org.jblas.DoubleMatrix;

/**
 * Neural net class
 * Scheme:
 * input vector * inputLayer --> hidden vector
 * hidden vector * hiddenLayer --> hidden vector
 * hidden vector * outputLayer --> output vector
 */

public class NeuralNet {
    private int inputLength;
    private int outputLength;
    private int hiddenLayersCount;
    private int hiddenLayerLength;
    //private DoubleMatrix inputVector;
    private DoubleMatrix inputLayer;
    private DoubleMatrix[] hiddenLayers;
    private DoubleMatrix outputLayer;

    public NeuralNet(int inputLength, int outputLength, int hiddenLayersCount, int hiddenLayerLength) {
        this.inputLength = inputLength;
        this.outputLength = outputLength;
        this.hiddenLayersCount = hiddenLayersCount;
        this.hiddenLayerLength = hiddenLayerLength;

        //inputVector = DoubleMatrix.rand(1, inputLength );
        inputLayer = DoubleMatrix.rand(inputLength, hiddenLayerLength);
        hiddenLayers = new DoubleMatrix[hiddenLayersCount];
        for(int i = 0; i < hiddenLayersCount; i++) {
            hiddenLayers[i] = DoubleMatrix.rand(hiddenLayerLength, hiddenLayerLength);
        }
        outputLayer = DoubleMatrix.rand(hiddenLayerLength, outputLength);
    }

    public DoubleMatrix getOutputForInput(DoubleMatrix input) {
        if (input.multipliesWith(inputLayer)) {
            throw new IllegalArgumentException("Input matrix has to have 1 row and " + inputLength + " columns.");
        }
        DoubleMatrix result = input.mul(inputLayer);
        for(int i = 0; i < hiddenLayersCount; i++) {
            result = result.mul(hiddenLayers[i]);
        }
        return result.mul(outputLayer);

    }
}
