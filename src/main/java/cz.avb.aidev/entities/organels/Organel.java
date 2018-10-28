package cz.avb.aidev.entities.organels;

public interface Organel {

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

    /**
     * Returns the amount of space this organel occupies.
     * @return volume (in metres?)
     */
    double getVolume();
}
