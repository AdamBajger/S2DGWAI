package cz.avb.aidev;


import cz.avb.aidev.neural.CDDMNN;
import cz.avb.aidev.neural.NeuralNet;
import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

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
        DoubleMatrix dm = new DoubleMatrix(new double[][]{new double[]{1, 2, 3}, new double[]{4, 5, 6}});
        printMatrix(dm);
        DoubleMatrix dm2 = new DoubleMatrix(new double[][]{new double[]{1, 4}, new double[]{2, 5}, new double[]{3, 6}});
        printMatrix(dm2);
        printMatrix(dm2.mmul(dm));
        printMatrix(dm.mmul(dm2));
        */

        NeuralNet net = new CDDMNN(2, 1, 2, 10);
        System.out.print(net.getOutputForInput(new double[]{5, 6}));
        //printMatrix(DoubleMatrix.rand(5, 2));


    }

    public static void printMatrix(DoubleMatrix dm) {
        for(int i = 0; i < dm.getRows(); i++) {
            System.out.println(dm.getRow(i));
        }
    }
}
