package cz.avb.aidev;


import cz.avb.aidev.neural.CDDMNN;
import cz.avb.aidev.neural.DataVector;
import cz.avb.aidev.neural.NeuralNet;
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



        NeuralNet net = new CDDMNN(2, 1, 2, 5);
        System.out.println(Arrays.toString(net.getOutputForInput(new DataVector(5, 6))));
        //printMatrix(DoubleMatrix.rand(5, 2));


    }

    public static void printMatrix(DoubleMatrix dm) {
        System.out.println("\\_______");
        for(int i = 0; i < dm.getRows(); i++) {
            System.out.println(dm.getRow(i));
        }
    }
}
