package ch.koenixband.fingering;

import java.util.ArrayList;
import java.util.List;

public class FingeringPositionVibratoCalculator {
    private final FingeringPosition fingeringPosition;

    public FingeringPositionVibratoCalculator(FingeringPosition fingeringPosition) {
        this.fingeringPosition = fingeringPosition;
    }

    /**
     * Calculates all possible vibratos for a fingering
     *
     * @param vibratoPitch The pitch to add to a vibrato
     * @return All possible vibratos for a fingering
     */
    public List<FingeringPosition> calculateVibratos(int vibratoPitch) {
        int maxIdForVibrato = calcMaxIdForVibrato();
        List<FingeringPosition> vibratos = new ArrayList<>();
        for (int i = 0; i <= maxIdForVibrato; i++) {
            //Do not override bottoms
            if (i % 2 != 0) {
                char[] fingeringPattern = fingeringPosition.getBinaryPattern();
                char[] vibratoPattern = Integer.toBinaryString(i).toCharArray();
                int offset = fingeringPattern.length - vibratoPattern.length;
                int numberOfFingers = 0;
                for (int j = 0; j < vibratoPattern.length; j++) {
                    fingeringPattern[j + offset] = vibratoPattern[j];
                    if (vibratoPattern[j] == '1') {
                        numberOfFingers++;
                    }
                }
                String idString = "";
                for (char c : fingeringPattern) {
                    idString += c;
                }
                FingeringPosition vibrato = new FingeringPosition(true, Integer.parseInt(idString, 2));
                vibrato.setMidiNote(fingeringPosition.midiNote());
                vibrato.setPitch(vibratoPitch * numberOfFingers);
                vibratos.add(vibrato);
            }
        }
        return vibratos;
    }

    /**
     * Calcs the max id of fingering that is interesting for vibrato purposes
     *
     * @return The max id of fingering that is interesting for vibrato purposes
     */
    private int calcMaxIdForVibrato() {
        char[] binaryPatternWithoutOctave = calcBinaryPatternWithoutOctave();
        int index = calcIndexOfFirstOpenHoleAfterLastClosed(binaryPatternWithoutOctave);
        if (!isCrossFingering()) {
            index++;
        }
        String pattern = "";

        for (int i = 0; i < (binaryPatternWithoutOctave.length - index + 1); i++) {
            pattern += "1";
        }
        try {
            return Integer.parseInt(pattern, 2);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Calculates the index of the first open hole after the last closed hole from the top of the chanter
     *
     * @param binaryPatternWithoutOctave The fingering pattern without octave information
     * @return The index
     */
    private int calcIndexOfFirstOpenHoleAfterLastClosed(char[] binaryPatternWithoutOctave) {
        int index = binaryPatternWithoutOctave.length - 1;
        while (index >= 0) {
            if (binaryPatternWithoutOctave[index] == '0') {
                return index + 1;
            }
            index--;
        }
        return 0;
    }

    /**
     * Determines if it is a fingering position with cross-fingering, that would be handled differently
     *
     * @return true if the fingering postion is cross-fingering
     */
    private boolean isCrossFingering() {
        boolean openFound = false;
        boolean hasClosed = false;
        for (char c : calcBinaryPatternWithoutOctave()) {
            if (c == '0') {
                if (openFound) {
                    return true;
                }
                hasClosed = true;
            }
            if (c == '1') {
                openFound = true;
            }
        }
        return false;
    }

    /**
     * Calcs the binary pattern and removes octave information
     *
     * @return the binary pattern without octave information
     */
    private char[] calcBinaryPatternWithoutOctave() {
        char[] binaryPatternWithOctave = fingeringPosition.getBinaryPattern();
        //remove octave
        char[] binaryPatternWithoutOctave = new char[binaryPatternWithOctave.length - 1];
        for (int i = 1; i < binaryPatternWithOctave.length; i++) {
            binaryPatternWithoutOctave[i - 1] = binaryPatternWithOctave[i];
        }
        return binaryPatternWithoutOctave;
    }

    /**
     * Returns the last position from top down of the chanter where a finger is closed to knwo where to calculate all alternatives from.
     *
     * @param binaryPatternWithoutOctave The binary pattern of the fingering without octave
     * @return The last postion from top down of the chanter where a finger is closed or -1 if all is closed
     */
    private int indexOfBottomClosedHole(char[] binaryPatternWithoutOctave) {
        for (int i = binaryPatternWithoutOctave.length - 1; i >= 0; i--) {
            if (binaryPatternWithoutOctave[i] == '0') {
                return i;
            }
        }
        return -1;
    }
}
