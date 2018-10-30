package cz.avb.aidev;


import cz.avb.aidev.neural.CDDMNN;
import cz.avb.aidev.neural.DataVector;
import cz.avb.aidev.neural.EvolvingNeuralNet;
import cz.avb.aidev.neural.NeuralNetEvolver;
import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.util.Arrays;

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

        NeuralNetEvolver nne = new NeuralNetEvolver(net, neuralNet -> {
            int[] trainingData = new int[]{1, 2, 3, 4, 10, 15, 16, 18, 55, 6468, 8746, 31584, 321514, 321515,
                    321516, 321517, 321518, 321519, 321520, 56, 57, 58, 59, 60, 61, 62, 63, 88, 89, 92, 93, 94,
                    1222, 1223, 1224, 1225, 1226, 1227, 1228, 1229, 1230, 1231, 1232, 1234, 1235, 1236, 1237, 1238,
                    1239, 1240, 1241, 1242, 1243, 1244, 1245, 1246, 1247, 1248, 1249, 1250};
            double score = 0d;
            int desiredResult;
            double netResult;
            for (int data1 : trainingData) {
                for (int data2 : trainingData) {
                    desiredResult = (data1 + data2) % 1000;
                    netResult = neuralNet.getOutputForInput(new DataVector(data1, data2))[0];
                    score += Math.abs((double)desiredResult - netResult);
                }
            }
            return score;
        }, 3, new double[]{0.00001, 0.0001, 0.0002, 0.005, 0.01, 0.1, 0.5, 1, 2, 5, 20});



    }

    public static void printMatrix(DoubleMatrix dm) {
        System.out.println("\\_______");
        for(int i = 0; i < dm.getRows(); i++) {
            System.out.println(dm.getRow(i));
        }
    }
}
