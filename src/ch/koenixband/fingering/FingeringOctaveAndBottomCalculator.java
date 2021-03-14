package ch.koenixband.fingering;

import java.util.ArrayList;
import java.util.List;

/**
 * Adds the octave and its alternatives with bottom closed to any finger postion
 */
public class FingeringOctaveAndBottomCalculator {
    /**
     * The base fingering position in the 1st octave with bottom open
     */
    private final FingeringPosition position;
    /**
     * The pitch to add when bottom hole is closed
     */
    private final int defaultBottomPitch;

    /**
     * Constructor
     *
     * @param position           The base fingering position in the 1st octave with bottom open
     * @param defaultBottomPitch The pitch to add when bottom hole is closed
     */
    public FingeringOctaveAndBottomCalculator(FingeringPosition position, int defaultBottomPitch) {
        this.position = position;
        this.defaultBottomPitch = defaultBottomPitch;
    }

    /**
     * Calculates the octave fingering and the one's with bottom closed
     *
     * @return The 3 fingerings with octave and bottom closed
     */
    public List<FingeringPosition> calculateOctaveAndClosedBottom() {
        List<FingeringPosition> out = new ArrayList<>();
        out.add(calculateBottomClosed(position));
        FingeringPosition octave = calculateOctave();
        out.add(octave);
        out.add(calculateBottomClosed(octave));
        return out;
    }

    /**
     * Calculates the same fingering with bottom closed
     *
     * @param openPosition The open position from wich the bottom closed fingering position should be calculated
     * @return The same fingering with bottom closed
     */
    private FingeringPosition calculateBottomClosed(FingeringPosition openPosition) {
        FingeringPosition position = new FingeringPosition(
                true,
                openPosition.octave(),
                openPosition.leftThumbClosed(),

                openPosition.leftIndexClosed(),
                openPosition.leftMiddleClosed(),
                openPosition.leftRingClosed(),

                openPosition.rightIndexClosed(),
                openPosition.rightMiddleClosed(),
                openPosition.rightRingClosed(),
                openPosition.rightSmallClosed(),
                true
        );
        position.setPitch(openPosition.pitch() + defaultBottomPitch);
        position.setMidiNote(openPosition.midiNote());
        return position;
    }

    /**
     * Calculates the same fingering for the second octave
     *
     * @return The same fingering one octave above
     */
    private FingeringPosition calculateOctave() {
        FingeringPosition position = new FingeringPosition(
                this.position.isVibrato(),
                2,
                this.position.leftThumbClosed(),

                this.position.leftIndexClosed(),
                this.position.leftMiddleClosed(),
                this.position.leftRingClosed(),

                this.position.rightIndexClosed(),
                this.position.rightMiddleClosed(),
                this.position.rightRingClosed(),
                this.position.rightSmallClosed(),
                this.position.bottomClosed()
        );
        position.setPitch(this.position.pitch());
        position.setMidiNote(this.position.midiNote() + 12);
        return position;
    }
}
