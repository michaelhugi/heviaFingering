package ch.koenixband.fingering;

import ch.koenixband.utils.VibratoHolder;

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
     * @param vibratoMask  The binary mask where vibrato is possible
     * @return All possible vibratos for a fingering
     */
    public List<FingeringPosition> calculateVibratos(int vibratoPitch, String vibratoMask) {
        vibratoMask = vibratoMask.replace(" ", "").trim();
        int maxIdForVibrato = getMaxIdForVibratoMask(vibratoMask);
        List<FingeringPosition> vibratos = new ArrayList<>();
        for (int vibratoId = 0; vibratoId <= maxIdForVibrato; vibratoId++) {
            //Do not override bottoms
            if (vibratoId % 2 != 0) {
                VibratoHolder vibratoHolder = getTotalFingeringOfVibrato(fingeringPosition.getBinaryPattern(), vibratoId, vibratoMask);
                FingeringPosition vibrato = new FingeringPosition(true, vibratoHolder.totalFingerPosition);
                vibrato.setMidiNote(fingeringPosition.midiNote());
                vibrato.setPitch(vibratoHolder.calcPitch(vibratoPitch));
                vibratos.add(vibrato);

/*                char[] fingeringPattern = fingeringPosition.getBinaryPattern();
                char[] idVibratoPatternShort = Integer.toBinaryString(i).toCharArray();
                char[] idVibratoPattern = binaryVibratoPattern.toCharArray();
                for (int j = 0; j < idVibratoPattern.length; j++) {
                    idVibratoPattern[j] = '0';
                }
                int patternOffset = idVibratoPattern.length - idVibratoPatternShort.length;
                for (int j = 0; j < idVibratoPatternShort.length; j++) {
                    idVibratoPattern[j + patternOffset] = idVibratoPatternShort[j];
                }

                int offset = fingeringPattern.length - idVibratoPattern.length;
                int numberOfFingers = 0;
                for (int j = 0; j < idVibratoPattern.length; j++) {
                    fingeringPattern[j + offset] = idVibratoPattern[j];
                    if (idVibratoPattern[j] == '1') {
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
                vibratos.add(vibrato);*/
            }
        }
        return vibratos;
    }

    /**
     * Calculates the new pattern for the fingering based on the fingering for the midi note, the vibratoPattern and the id of the vibrato. This will replace all fingerings that are described by the vibrato mask by the vibrato itself
     *
     * @param fingeringPatternOfNote The fingering pattern of the midi note without vibrato
     * @param vibratoId              The id of the vibrato
     * @param vibratoMask            the mask of the vibrato
     * @return The fingering that describes the specifig vibrato fingering postion for a given midi note
     */
    private VibratoHolder getTotalFingeringOfVibrato(char[] fingeringPatternOfNote, int vibratoId, String vibratoMask) {
        char[] vibrato = createVibratoPatternWithLeadingZeros(vibratoId, vibratoMask);
        int offset = fingeringPatternOfNote.length - vibrato.length;
        for (int i = 0; i < vibrato.length; i++) {
            fingeringPatternOfNote[i + offset] = vibrato[i];
        }
        String idString = "";
        for (char c : fingeringPatternOfNote) {
            idString += c;
        }
        int totalVibratoId = Integer.parseInt(idString, 2);
        return new VibratoHolder(totalVibratoId, vibrato);
    }

    /**
     * Calculates the max id for a vibrato mask
     *
     * @param vibratoMask The vibrato mask to check
     * @return the max id that is a vibrato
     */
    private int getMaxIdForVibratoMask(String vibratoMask) {
        String p = "";
        for (char c : createOnePattern(trimPattern(vibratoMask).length())) {
            p += c;
        }
        return Integer.parseInt(p, 2);
    }

    /**
     * Patterns may contain spaces for human readability. This method removes them
     *
     * @param pattern The pattern with spaces in it
     * @return The pattern without spaces in it;
     */
    private String trimPattern(String pattern) {
        return pattern.replace(" ", "").trim();
    }

    /**
     * Creates a binary pattern for a specific vibrato based on the vibratoMask
     *
     * @param id          The id of the vibrato represents the bitmask of all vibrato fingers
     * @param vibratoMask The vibrato mask defines which fingers are used for vibrato
     * @return The binary pattern fo a specific vibrato
     */
    private char[] createVibratoPatternWithLeadingZeros(int id, String vibratoMask) {
        return toFixedLengthWithLeadingZeros(Integer.toBinaryString(id).toCharArray(), vibratoMask.length());
    }

    /**
     * Adds leading 0 to a char array until it reached a fixed length
     *
     * @param in          The array that may be too short
     * @param fixedLength The fixed desired length
     * @return A char array with fixedLength with leading 0 with the same pattern as in
     */
    private char[] toFixedLengthWithLeadingZeros(char[] in, int fixedLength) {
        char[] out = createZeroPattern(fixedLength);
        int offset = out.length - in.length;
        for (int i = 0; i < in.length; i++) {
            out[i + offset] = in[i];
        }
        return out;
    }

    /**
     * Creates a char[] with 0 in it
     *
     * @param length The length of the array
     * @return a char[] with 0 in it
     */
    private char[] createZeroPattern(int length) {
        char[] out = new char[length];
        for (int i = 0; i < length; i++) {
            out[i] = '0';
        }
        return out;
    }

    /**
     * Creates a char[] with 1 in it
     *
     * @param length The length of the array
     * @return a char[] with 1 in it
     */
    private char[] createOnePattern(int length) {
        char[] out = new char[length];
        for (int i = 0; i < length; i++) {
            out[i] = '1';
        }
        return out;
    }
}
