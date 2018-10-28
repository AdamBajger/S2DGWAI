package cz.avb.aidev.entities.receptors;

public interface Receptor {
    /**
     * Returns the value the receptor generates by analysing surroundings.
     * May represent amount of light present, amount of light coming from direction, surrounding fluids' pressure, etc.
     * @return a double value coming from this receptor
     */
    double getReceptorOutput();

    /**
     * Returns how much it costs per tick to operate this receptor
     * @return a cost in energy (joules???)
     */
    double getEnergyCost();

    /**
     * Returns the mass of this receptor
     * @return mass in kilograms (probably)
     */
    double getMass();

}
