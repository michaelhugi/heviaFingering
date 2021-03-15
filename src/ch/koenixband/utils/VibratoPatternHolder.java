package ch.koenixband.utils;
/**
 * Holds the pattern for a finger position of a vibrato with the information on how many fingers of the vibrato mask close the hole.
 */
public class VibratoPatternHolder {
    /**
     * The pattern for the  finger postion of the vibrato
     */
    public final char[] fibratoPattern;
    /**
     * The count of the holes closed in the vibrato mask.
     */
    public final int numberOfClosedVibratoFingers;

    /**
     * Constructor
     *
     * @param fibratoPattern               The pattern for the finger postion of the vibrato
     * @param numberOfClosedVibratoFingers The count of the holes closed in the vibrato mask
     */
    public VibratoPatternHolder(char[] fibratoPattern, int numberOfClosedVibratoFingers) {
        this.fibratoPattern = fibratoPattern;
        this.numberOfClosedVibratoFingers = numberOfClosedVibratoFingers;
    }
}
