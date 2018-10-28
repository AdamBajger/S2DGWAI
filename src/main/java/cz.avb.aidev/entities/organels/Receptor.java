package cz.avb.aidev.entities.organels;

public interface Receptor extends Organel{
    /**
     * Returns the value the receptor generates by analysing surroundings.
     * May represent amount of light present, amount of light coming from direction, surrounding fluids' pressure, etc.
     * @return a double value coming from this receptor
     */
    double getReceptorOutput();


}
