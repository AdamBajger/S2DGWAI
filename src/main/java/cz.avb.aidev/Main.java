package cz.avb.aidev;


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

        DoubleMatrix dm = new DoubleMatrix(new double[][]{new double[]{1, 2, 3}, new double[]{4, 5, 6}, new double[]{7, 8, 9}});
        DoubleMatrix dm2 = dm.transpose();
        printMatrix(dm2.max(dm));
        printMatrix(dm);
        printMatrix(dm2);


    }

    public static void printMatrix(DoubleMatrix dm) {
        for(int i = 0; i < dm.getRows(); i++) {
            System.out.println(dm.getRow(i));
        }
    }
}
