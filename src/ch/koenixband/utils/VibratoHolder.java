package ch.koenixband.utils;

/**
 * Holds the pattern for a total finger position of a vibrato with the information on how many fingers of the vibrato mask close the hole.
 */
public class VibratoHolder {
    /**
     * The id for the total finger postion of the vibrato
     */
    public final int totalFingerPosition;
    /**
     * The pattern dependent on the vibrato mask. Contains information of what fingers are closed
     */
    public final char[] vibratoPattern;

    /**
     * Constructor
     *
     * @param totalFingerPosition The id for the total finger postion of the vibrato
     * @param vibratoPattern      The pattern dependent on the vibrato mask. Contains information of what fingers are closed
     */
    public VibratoHolder(int totalFingerPosition, char[] vibratoPattern) {
        this.totalFingerPosition = totalFingerPosition;
        this.vibratoPattern = vibratoPattern;
    }

    /**
     * Calculates the pitch of this vibrato based on the basic pitch of a note and the vibrato pattern
     *
     * @param vibratoPitch The pitch for the first covered hole
     * @return The pitch for this fibrato fingering position
     */
    public int calcPitch(int vibratoPitch) {
        double pitch = 0;
        for (int i = 0; i < vibratoPattern.length; i++) {
            if (vibratoPattern[i] == '0') {
                pitch += root(i, vibratoPitch);
            }
        }
        return (int) pitch;
    }

    /**
     * Calculates the nth root of a number
     *
     * @param order The order of the root
     * @param value The input value
     * @return The output value
     */
    private double root(double order, double value) {
        order++;
        return Math.pow(value, 1.0 / order);
    }
}
