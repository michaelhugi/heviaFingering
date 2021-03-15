package ch.koenixband.fingering;

import ch.koenixband.utils.FingeringPatternCalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Holds all information about fingering
 */
public class Fingering {
    /**
     * The name of the fingering
     */
    private final String name;
    /**
     * The default bottom pitch will be auto added on every fingering with the bottom hole closed
     */
    private final int defaultBottomPitch;
    /**
     * The default pitch is the pitch of the first vibrato hole covered. Other holes covered in the vibrato mask will also pitch based on the index-root of this pitch
     */
    private final int defaultPitch;
    /**
     * The lowest note of the fingering (all holes but the bottom hole covered)
     */
    private final int lowestMidiNote;
    /**
     * All possible fingering positions
     */
    private final HashMap<Integer, FingeringPosition> fingeringPositions = new HashMap<>();

    /**
     * Constructor
     *
     * @param name               The name of the fingering
     * @param lowestMidiNote     The lowest note of the fingering (all holes but the bottom hole covered)
     * @param defaultBottomPitch The default bottom pitch will be auto added on every fingering with the bottom hole closed
     * @param defaultPitch       The default pitch is the pitch of the first vibrato hole covered. Other holes covered in the vibrato mask will also pitch based on the index-root of this pitch
     */
    public Fingering(String name, int lowestMidiNote, int defaultBottomPitch, int defaultPitch) {
        this.name = name;
        this.defaultBottomPitch = defaultBottomPitch;
        this.defaultPitch = defaultPitch;
        this.lowestMidiNote = lowestMidiNote;
    }

    /**
     * Adds or replaces fingering positions in the fingering. Vibratos will not replace not vibratos
     *
     * @param fingeringPositions The fingering positions to put in the fingering
     */
    public void addOrReplaceFingeringPosition(List<FingeringPosition> fingeringPositions) {
        for (FingeringPosition fingeringPosition : fingeringPositions) {
            addOrReplaceFingeringPosition(fingeringPosition);
        }
    }

    /**
     * Adds or replaces a fingering position in the fingering. Vibratos will not replace not vibratos
     *
     * @param fingeringPosition The fingering position to put in the fingering
     */
    public void addOrReplaceFingeringPosition(FingeringPosition fingeringPosition) {
        if (!fingeringPosition.isVibrato()) {
            fingeringPositions.put(fingeringPosition.getId(), fingeringPosition);
            return;
        }
        if (fingeringPositions.containsKey(fingeringPosition.getId())) {
            if (!fingeringPositions.get(fingeringPosition.getId()).isVibrato()) {
                return;
            }
        }
        fingeringPositions.put(fingeringPosition.getId(), fingeringPosition);
    }

    /**
     * The lowest note of the fingering (all holes but the bottom hole covered)
     */
    public int getLowestMidiNote() {
        return lowestMidiNote;
    }

    /**
     * The highest note of the fingering (all holes open)
     */
    public int getHighestMidiNote() {
        return lowestMidiNote + 14;
    }

    /**
     * The default bottom pitch will be auto added on every fingering with the bottom hole closed
     */
    public int getDefaultBottomPitch() {
        return defaultBottomPitch;
    }

    /**
     * The default pitch is the pitch of the first vibrato hole covered. Other holes covered in the vibrato mask will also pitch based on the index-root of this pitch
     */
    public int getDefaultPitch() {
        return defaultPitch;
    }

    /**
     * The name of the fingering
     */
    public String getName() {
        return name;
    }

    /**
     * Returns all fingering postions for this fingering
     *
     * @return All fingering positions for this fingering
     */
    public List<FingeringPosition> getFingeringPositions() {
        List<FingeringPosition> out = new ArrayList<>();
        for (int id : fingeringPositions.keySet()) {
            out.add(fingeringPositions.get(id));
        }
        return out;
    }

    /**
     * Checks if all possible combinations of fingerings are made and prints out the one's that are missing
     */
    public void printMissingPatterns() {
        for (int i = 0; i < FingeringPosition.MAX_ID_OF_OCTAVE_1; i++) {
            if (!fingeringPositions.containsKey(i)) {
                String debug = "";
                for (char c : FingeringPatternCalculator.getBinaryPattern(i)) {
                    debug += c;
                }
                System.out.println("Missing pattern " + debug);
            }
        }
    }
}
