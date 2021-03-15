package ch.koenixband.utils;

/**
 * Holds the pattern for a total finger position of a vibrato with the information on how many fingers of the vibrato mask close the hole.
 */
public class VibratoHolder {
    /**
     * The id for the total finger postion of the vibrato
     */
    public final int fibratoPattern;
    /**
     * The count of the holes closed in the vibrato mask.
     */
    public final int numberOfClosedVibratoFingers;

    /**
     * Constructor
     *
     * @param fibratoPattern               The id for the total finger postion of the vibrato
     * @param numberOfClosedVibratoFingers The count of the holes closed in the vibrato mask
     */
    public VibratoHolder(int fibratoPattern, int numberOfClosedVibratoFingers) {
        this.fibratoPattern = fibratoPattern;
        this.numberOfClosedVibratoFingers = numberOfClosedVibratoFingers;
    }
}
