package ch.koenixband.utils;

/**
 * Calculates fingering patterns to use for bitmasking
 */
public class FingeringPatternCalculator {
    /**
     * The length of the binary representation of the fingering.
     */
    private static final int BINARY_PATTERN_LENGTH = 10;

    /**
     * Returns the binary pattern that represents the fingering. Last digit is the bottom hole, second last ist the small right finger aso. At the beginning is the number of the octave starting at 0<br>
     * 1 means open, 0 means closed
     *
     * @param id The id of the fingering
     * @return The binary pattern representing the id
     */
    public static char[] getBinaryPattern(int id) {
        char[] out = new char[BINARY_PATTERN_LENGTH];
        for (int i = 0; i < BINARY_PATTERN_LENGTH; i++) {
            out[i] = '0';
        }

        char[] items = Integer.toBinaryString(id).toCharArray();
        int j = BINARY_PATTERN_LENGTH - 1;
        for (int i = items.length - 1; i >= 0; i--) {
            out[j] = items[i];
            j--;
        }
        return out;
    }
}
