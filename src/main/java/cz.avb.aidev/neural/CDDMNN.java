package cz.avb.aidev.neural;

import org.jblas.DoubleMatrix;

/**
 * Comparable Deep Double Matrix Neural Net
 *
 * Scheme:
 * input vector * inputLayer --> hidden vector
 * hidden vector * hiddenLayer --> hidden vector
 * hidden vector * outputLayer --> output vector
 */

public class CDDMNN implements EvolvingNeuralNet<double[], double[]> {
    private int inputLength;
    private int outputLength;
    private int hiddenLayersCount;
    private int hiddenLayerLength;
    private DoubleMatrix inputLayer;
    private DoubleMatrix inputLayerThresholds;
    private DoubleMatrix[] hiddenLayers;
    private DoubleMatrix[] hiddenLayersThresholds;
    private DoubleMatrix outputLayer;
    private DoubleMatrix outputLayerThresholds;

    public CDDMNN(
            int inputLength,
            int outputLength,
            int hiddenLayersCount,
            int hiddenLayerLength,
            DoubleMatrix inputLayer,
            DoubleMatrix inputLayerThresholds,
            DoubleMatrix[] hiddenLayers,
            DoubleMatrix[] hiddenLayersThresholds,
            DoubleMatrix outputLayer,
            DoubleMatrix outputLayerThresholds
    ) {
        this.inputLength = inputLength;
        this.outputLength = outputLength;
        this.hiddenLayersCount = hiddenLayersCount;
        this.hiddenLayerLength = hiddenLayerLength;
        this.inputLayer = inputLayer;
        this.inputLayerThresholds = inputLayerThresholds;
        this.hiddenLayers = hiddenLayers;
        this.hiddenLayersThresholds = hiddenLayersThresholds;
        this.outputLayer = outputLayer;
        this.outputLayerThresholds = outputLayerThresholds;
    }

    public CDDMNN(int inputLength, int outputLength, int hiddenLayersCount, int hiddenLayerLength) {
        this.inputLength = inputLength;
        this.outputLength = outputLength;
        this.hiddenLayersCount = hiddenLayersCount;
        this.hiddenLayerLength = hiddenLayerLength;

        inputLayer = DoubleMatrix.rand(inputLength, hiddenLayerLength);
        inputLayerThresholds = DoubleMatrix.rand(inputLength);
        hiddenLayers = new DoubleMatrix[hiddenLayersCount];
        hiddenLayersThresholds = new DoubleMatrix[hiddenLayersCount];
        for(int i = 0; i < hiddenLayersCount; i++) {
            hiddenLayersThresholds[i] = DoubleMatrix.rand(hiddenLayerLength);
            hiddenLayers[i] = DoubleMatrix.rand(hiddenLayerLength, hiddenLayerLength);
        }
        outputLayer = DoubleMatrix.rand(hiddenLayerLength, outputLength);
        outputLayerThresholds = DoubleMatrix.rand(outputLength);
    }

    @Override
    public double[] getOutputForInput(double[] inputData) {
        DoubleMatrix input = new DoubleMatrix(inputData);
        if (input.multipliesWith(inputLayer)) {
            throw new IllegalArgumentException("Input matrix has to have 1 row and " + inputLength + " columns.");
        }
        DoubleMatrix result = inputLayer
                .mmul(
                        input.max(inputLayerThresholds)
                );
        for(int i = 0; i < hiddenLayersCount; i++) {
            result = hiddenLayers[i].mmul( result.max(hiddenLayersThresholds[i]) );
        }
        return outputLayer.mul( result.max(outputLayerThresholds) ).data;

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
    private DoubleMatrix deriveMutatedMatrix(DoubleMatrix dm, double maxEvolutionStep) {
        return dm.add(DoubleMatrix.rand(dm.rows, dm.columns).sub(0.5d).mul(2 * maxEvolutionStep));
    }

    /**
     * Creates new instance of CDDMNN with slightly different values in all matrices inside.
     *
     * @param maxEvolutionStep the maximum difference between original and evolved value in the matrix
     * @return randomly mutated neural net
     */
    @Override
    public CDDMNN deriveMutatedDescendant(double maxEvolutionStep) {
        //int inputLength
        //int outputLength,
        //int hiddenLayersCount,
        //int hiddenLayerLength,
        DoubleMatrix evolvedInputLayer = deriveMutatedMatrix(this.inputLayer, maxEvolutionStep);
        DoubleMatrix evolvedInputLayerThresholds = deriveMutatedMatrix(this.inputLayerThresholds, maxEvolutionStep);
        DoubleMatrix[] evolvedHiddenLayers = new DoubleMatrix[this.hiddenLayersCount];
        DoubleMatrix[] evolvedHiddenLayersThresholds = new DoubleMatrix[this.hiddenLayersCount];
        for(int i = 0; i < hiddenLayersCount; i++) {
            evolvedHiddenLayersThresholds[i] = deriveMutatedMatrix(this.hiddenLayersThresholds[i], maxEvolutionStep);
            evolvedHiddenLayers[i] = deriveMutatedMatrix(this.hiddenLayers[i], maxEvolutionStep);
        }
        DoubleMatrix evolvedOutputLayer = deriveMutatedMatrix(this.outputLayer, maxEvolutionStep);
        DoubleMatrix evolvedOutputLayerThresholds = deriveMutatedMatrix(this.outputLayerThresholds, maxEvolutionStep);

        return new CDDMNN(
                this.inputLength,
                this.outputLength,
                this.hiddenLayersCount,
                this.hiddenLayerLength,
                evolvedInputLayer,
                evolvedInputLayerThresholds,
                evolvedHiddenLayers,
                evolvedHiddenLayersThresholds,
                evolvedOutputLayer,
                evolvedOutputLayerThresholds
        );
    }
}
