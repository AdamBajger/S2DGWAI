package cz.avb.aidev;


import cz.avb.aidev.neural.CDDMNN;
import cz.avb.aidev.neural.CostFunction;
import cz.avb.aidev.neural.DataVector;
import cz.avb.aidev.neural.EvolvingNeuralNet;
import cz.avb.aidev.neural.NeuralNet;
import cz.avb.aidev.neural.NeuralNetEvolver;
import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static cz.avb.aidev.neural.NeuralNetEvolver.Sorting.CONVERGE_TO_ZERO;

public class Main {
    public static void main (String[] args) {


        /*Game1 game = new Game1();

        try {
            AppGameContainer container = new AppGameContainer(game);
            container.start();


        } catch (SlickException e) {
            e.printStackTrace();
        }*/

/*
        DoubleMatrix dm = new DoubleMatrix(new double[]{0, 2});
        printMatrix(dm);
        DoubleMatrix dm2 = DoubleMatrix.rand(2, 5);
        printMatrix(dm2);
        DoubleMatrix rowm = DoubleMatrix.rand(5, 1);
        try {
            printMatrix(dm.mmul(dm2));
        } catch (Exception e) {
            System.out.println("Not compatible");
        }
        try {
            printMatrix(dm2.mmul(rowm));
        } catch (Exception e) {
            System.out.println("Not compatible");
        }
        */



        EvolvingNeuralNet net = new CDDMNN(2, 1, 2, 5);
        System.out.println(Arrays.toString(net.getOutputForInput(new DataVector(5, 6))));
        //printMatrix(DoubleMatrix.rand(5, 2));

        NeuralNetEvolver nne = new NeuralNetEvolver(
                net,
                new CostFunction() {
                    @Override
                    public double getScoreForNeuralNet(NeuralNet neuralNet) {
                        int[] trainingData = new int[]{1, 2, 3, 4, 6, 7, 10, 15, 16, 18, 55, 668, 846, 584, 314, 325,
                                151, 517, 328, 319, 321, 56, 57, 58, 59, 60, 61, 62, 63, 88, 89, 92, 93, 94,
                                222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 234, 235, 236, 237, 238,
                                239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250};
                        double score = 0d;
                        int desiredResult;
                        double netResult;
                        for (int data1 : trainingData) {
                            for (int data2 : trainingData) {
                                desiredResult = (data1 + data2) % 1000;
                                netResult = neuralNet.getOutputForInput(new DataVector(data1, data2))[0];
                                score += Math.abs((double) desiredResult - netResult);
                            }
                        }
                        return score;
                    }
                },
                CONVERGE_TO_ZERO,
                3,
                0.00001, 0.0001, 0.0002, 0.005, 0.01, 0.1, 0.5, 1, 2, 5, 20
        );
        nne.evolveGenerations(10);
        Collection<EvolvingNeuralNet> bestNets = nne.getBestNets();
        for (NeuralNet nn :
                bestNets) {
            System.out.print(Arrays.toString(nn.getOutputForInput(new DataVector(19, 5))) + " --> " + ((19 + 5) % 1000));
            System.out.print(Arrays.toString(nn.getOutputForInput(new DataVector(229, 230)))+ " --> " + ((229 + 230) % 1000));
        }


    }

    public static void printMatrix(DoubleMatrix dm) {
        System.out.println("\\_______");
        for(int i = 0; i < dm.getRows(); i++) {
            System.out.println(dm.getRow(i));
        }
    }
}
