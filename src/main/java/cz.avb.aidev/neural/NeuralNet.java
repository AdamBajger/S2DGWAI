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

    public NeuralNet(
            int inputLength,
            int outputLength,
            int hiddenLayersCount,
            int hiddenLayerLength,
            DoubleMatrix inputLayer,
            DoubleMatrix[] hiddenLayers,
            DoubleMatrix outputLayer
    ) {
        this.inputLength = inputLength;
        this.outputLength = outputLength;
        this.hiddenLayersCount = hiddenLayersCount;
        this.hiddenLayerLength = hiddenLayerLength;
        this.inputLayer = inputLayer;
        this.hiddenLayers = hiddenLayers;
        this.outputLayer = outputLayer;
    }

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

    /**
     * Creates new matrix with slightly changed values from inputted matrix.
     * Values are changed by a random values between {@code +evolutionStep}(inclusive) and {@code -evolutionStep}(exclusive)
     *
     * This method creates random matrix of dimensions matching inputted matrix, subtracts 0.5 from it,
     * then multiplies it by 2, which results in matrix with random values ranging between -1 and +1.
     * After that, the random matrix is multiplied by {@code evolutionStep} which results in matrix with random values
     * described above.
     *
     * @param dm matrix to be evolved from
     * @param maxEvolutionStep the bounds for the evolution steepness
     * @return evolved matrix
     */
    private DoubleMatrix evolveMatrix(DoubleMatrix dm, double maxEvolutionStep) {
        return dm.add(DoubleMatrix.rand(dm.rows, dm.columns).sub(0.5d).mul(2 * maxEvolutionStep));
    }

    public NeuralNet deriveDescendants(int numberOfDescendants, double maxEvolutionStep) {
        //int inputLength
        //int outputLength,
        //int hiddenLayersCount,
        //int hiddenLayerLength,
        DoubleMatrix inputLayer = evolveMatrix(this.inputLayer, maxEvolutionStep);
        DoubleMatrix[] hiddenLayers,
        DoubleMatrix outputLayer
    }
}
