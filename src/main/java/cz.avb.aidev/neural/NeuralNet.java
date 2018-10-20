package cz.avb.aidev.neural;

import org.jblas.DoubleMatrix;

public interface NeuralNet {
    double[] getOutputForInput(DataVector input);
}
