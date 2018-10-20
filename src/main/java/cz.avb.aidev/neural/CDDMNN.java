package cz.avb.aidev.neural;

import cz.avb.aidev.Main;
import org.jblas.DoubleMatrix;

/**
 * Comparable Deep Double Matrix Neural Net
 *
 * Scheme:
 * input vector * inputLayer --> hidden vector
 * hidden vector * hiddenLayer --> hidden vector
 * hidden vector * outputLayer --> output vector
 */

public class CDDMNN implements EvolvingNeuralNet {
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

    /**
     * Constructs a consensual deep neural network. With layers and stuff... :)
     * @param inputLength The length of the input vector, how many values you must push into the array to make it work
     * @param outputLength The length of the output array
     * @param hiddenLayersCount The actual number of hidden layers of "neurons".
     *                          Actually we do not store neurons, we store the matrices of connections between these
     *                          hypothetical neurons, so we actually have "one less" of the matrices, than
     *                          the hiddenLayerCount says.
     *                          On the other hand, we have exactly that many Threshold matrices as we have layers.
     *                          That is why is it so dumb when it comes to the computation loop.
     *
     * @param hiddenLayerLength The number of hypothetical neurons in each layer (uniformly)
     */
    public CDDMNN(int inputLength, int outputLength, int hiddenLayersCount, int hiddenLayerLength) {
        this.inputLength = inputLength;
        this.outputLength = outputLength;
        this.hiddenLayersCount = hiddenLayersCount;
        this.hiddenLayerLength = hiddenLayerLength;

        inputLayer = DoubleMatrix.rand(inputLength, hiddenLayerLength);
        inputLayerThresholds = DoubleMatrix.rand(1, inputLength); // has to have 1 row
        hiddenLayers = new DoubleMatrix[hiddenLayersCount-1];
        hiddenLayersThresholds = new DoubleMatrix[hiddenLayersCount];
        for(int i = 0; i < hiddenLayersCount-1; i++) {
            hiddenLayersThresholds[i] = DoubleMatrix.rand(1, hiddenLayerLength); // has to have 1 row
            hiddenLayers[i] = DoubleMatrix.rand(hiddenLayerLength, hiddenLayerLength);
        }
        hiddenLayersThresholds[hiddenLayersCount-1] = DoubleMatrix.rand(1, hiddenLayerLength); // setting up last threshold
        outputLayer = DoubleMatrix.rand(hiddenLayerLength, outputLength);
        outputLayerThresholds = DoubleMatrix.rand(1, outputLength); // has to have 1 row
    }

    @Override
    public double[] getOutputForInput(DataVector inputVector) {
        // here we have to insert it trasposed... cause it implicitly creates 1 column matrices... what a shit
        DoubleMatrix input = new DoubleMatrix(new double[][] {inputVector.getData()});

        if (!input.multipliesWith(inputLayer)) {
            throw new IllegalArgumentException("Input matrix has to have 1 row and " + inputLength + " columns." +
                    " You got " + input.rows + " rows, " + input.columns + " columns.");
        }
        DoubleMatrix result = input.max(inputLayerThresholds).mmul(inputLayer);
        for(int i = 0; i < (hiddenLayersCount-1); i++) {
            result = result.max(hiddenLayersThresholds[i].mmul( hiddenLayers[i] ));
        }
        //Main.printMatrix(result);
        //Main.printMatrix(outputLayerThresholds);
        return result.max(hiddenLayersThresholds[hiddenLayersCount-1]).mmul(outputLayer).max(outputLayerThresholds).toArray();

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

        // cause we store connections here, not neurons, we need 1 less
        DoubleMatrix[] evolvedHiddenLayers = new DoubleMatrix[this.hiddenLayersCount-1];
        // these correspond to actual neurons, so we need exact amount
        DoubleMatrix[] evolvedHiddenLayersThresholds = new DoubleMatrix[this.hiddenLayersCount];
        // we iterate through all layers except the last (we are setting connections, not neurons themselves)
        for(int i = 0; i < hiddenLayersCount-1; i++) {
            evolvedHiddenLayersThresholds[i] = deriveMutatedMatrix(this.hiddenLayersThresholds[i], maxEvolutionStep);
            evolvedHiddenLayers[i] = deriveMutatedMatrix(this.hiddenLayers[i], maxEvolutionStep);
        }
        // here we setup the last threshold layer we missed in the loop
        evolvedHiddenLayersThresholds[hiddenLayersCount-1] = deriveMutatedMatrix(this.hiddenLayersThresholds[hiddenLayersCount-1], maxEvolutionStep);

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
