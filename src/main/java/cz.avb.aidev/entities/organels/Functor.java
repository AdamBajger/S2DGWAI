package cz.avb.aidev.entities.organels;

/**
 * Represents organels like limbs, secrecy emmiters, etc.
 */
public interface Functor extends Organel {
    /**
     * Sets the signal of this functor to particular value
     * @param signal signal (neural input)
     */
    void setSignal(double signal);

    /**
     * Gets a double value representing the performance of the Functor.
     * If the functor performs propulsion, it represents the amount of acceleration, for example.
     * @return the double value representing the manifestation of this functor in outside world
     */
    double getActionValue();
}
