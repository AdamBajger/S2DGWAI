package cz.avb.aidev.neural;

import org.jblas.DoubleMatrix;

public interface NeuralNet<InputType, OutputType> {
    OutputType getOutputForInput(InputType input);
}
