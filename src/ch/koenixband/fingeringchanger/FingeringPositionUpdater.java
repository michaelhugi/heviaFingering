package ch.koenixband.fingeringchanger;

import ch.koenixband.fingering.Fingering;
import ch.koenixband.fingering.FingeringPosition;
import ch.koenixband.fingering.FingeringPositionVibratoCalculator;
import ch.koenixband.utils.MidiNote;

import java.util.Scanner;

/**
 * Updates fingering postions based on user input or automatically to the fingering
 */
public class FingeringPositionUpdater {
    /**
     * The fingering containing the fingering positiongs
     */
    private final Fingering fingering;
    /**
     * Used for user input
     */
    private final Scanner scanner;

    /**
     * The constructor
     *
     * @param fingering The fingering to update
     * @param scanner   Used for user input
     */
    public FingeringPositionUpdater(Fingering fingering, Scanner scanner) {
        this.fingering = fingering;
        this.scanner = scanner;
    }

    /**
     * Lets the user decide what note a fingering is, except when autoNote is inserted. In that case the method will just put this note without asking and go on
     *
     * @param fingeringPosition       The fingering position to update
     * @param autoNote                If not 0, the user won't be asked for the note but the note will apply itself
     * @param addVibratos             If true, all variants on the fingering will be created with a default pitch (vibratos). All variants will override other fingerings if they are vibratos.
     * @param allowReplaceNonVibratos If true, the fingering position will replace eventual finger position will override any existing position, otherwise it will only replace fingering positions that are vibratos. This is used to auto-add vibratos.
     */
    public void updateFingeringByUser(FingeringPosition fingeringPosition, int autoNote, boolean addVibratos, boolean allowReplaceNonVibratos) {
        fingeringPosition.printFintering(fingering.getLowestMidiNote());
        final int midiNote;
        final int pitch;
        if (autoNote == 0) {
            int[] vals = getMidiNoteAndPitch();
            midiNote = vals[0];
            pitch = vals[1];
        } else {
            midiNote = autoNote;
            pitch = fingeringPosition.pitch();
            System.out.println("Auto created as " + midiNote + " - " + MidiNote.toReadable(midiNote));
        }

        fingeringPosition.setMidiNote(midiNote);
        fingeringPosition.setPitch(pitch);
        fingering.addOrReplaceFingeringPosition(fingeringPosition, allowReplaceNonVibratos);
        addAutoCreated(fingeringPosition, allowReplaceNonVibratos);
        if (addVibratos) {
            for (FingeringPosition vibrato : new FingeringPositionVibratoCalculator(fingeringPosition).calculateVibratos(fingering.getDefaultPitch())) {
                updateFingeringByUser(vibrato, vibrato.midiNote(), false, false);
            }
        }
    }


    /**
     * Asks the user for a midi not with a pitch and returns it
     *
     * @return first argument is the midi note, second argument is pitch
     */
    private int[] getMidiNoteAndPitch() {
        try {
            int[] out = new int[2];
            System.out.println("Enter midi note for fingering above. Add -xx for negative pitch or +xx for positive pitch");
            String midiNoteString = scanner.nextLine();

            if (midiNoteString.contains("-")) {
                String[] vals = midiNoteString.split("-");
                out[0] = Integer.valueOf(vals[0]);
                out[1] = -Integer.valueOf(vals[1]);
                return out;
            }
            if (midiNoteString.contains("\\+")) {
                String[] vals = midiNoteString.split("\\+");
                out[0] = Integer.valueOf(vals[0]);
                out[1] = Integer.valueOf(vals[1]);
                return out;
            }
            out[0] = Integer.valueOf(midiNoteString);
            out[1] = 0;
            return out;
        } catch (Exception e) {
            System.out.println("not valid input");
            return getMidiNoteAndPitch();
        }

    }

    /**
     * Adds the variants with the bottom hole closed and the octave. Bottom hole options will not be made on fingerings below a certain point. see autoAddBottomClosed on fingeringPosition
     *
     * @param basicFingeringPostion   The fingering position of what the options in the octave and with closed bottom holes should be made.
     * @param allowReplaceNonVibratos If true, the fingering position will replace eventual finger position will override any existing position, otherwise it will only replace fingering positions that are vibratos. This is used to auto-add vibratos.
     */
    private void addAutoCreated(FingeringPosition basicFingeringPostion, boolean allowReplaceNonVibratos) {
        addBottomHoleClosed(basicFingeringPostion);
        addOctave(basicFingeringPostion, allowReplaceNonVibratos);
    }

    /**
     * Adds the octave option and it's option with bottom hole closed of a fingering position to the fingering.
     *
     * @param basicFingeringPosition  The basic fingering position from where to add the octave option
     * @param allowReplaceNonVibratos If true, the fingering position will replace eventual finger position will override any existing position, otherwise it will only replace fingering positions that are vibratos. This is used to auto-add vibratos.
     */
    private void addOctave(FingeringPosition basicFingeringPosition, boolean allowReplaceNonVibratos) {
        FingeringPosition position = new FingeringPosition(
                basicFingeringPosition.isVibrato(),
                2,
                basicFingeringPosition.leftThumbClosed(),

                basicFingeringPosition.leftIndexClosed(),
                basicFingeringPosition.leftMiddleClosed(),
                basicFingeringPosition.leftRingClosed(),

                basicFingeringPosition.rightIndexClosed(),
                basicFingeringPosition.rightMiddleClosed(),
                basicFingeringPosition.rightRingClosed(),
                basicFingeringPosition.rightSmallClosed(),

                basicFingeringPosition.bottomClosed()
        );
        position.setPitch(basicFingeringPosition.pitch());
        position.setMidiNote(basicFingeringPosition.midiNote() + 12);
        fingering.addOrReplaceFingeringPosition(position, allowReplaceNonVibratos);
        addBottomHoleClosed(position);
    }

    /**
     * Adds the option of the fingering with the bottom hole closed
     *
     * @param basicFingeringPosition The basic fingering from what the bottom hole closed option should be made.
     */
    private void addBottomHoleClosed(FingeringPosition basicFingeringPosition) {
        if (!basicFingeringPosition.autoAddBottomClosed()) {
            return;
        }
        FingeringPosition position = new FingeringPosition(
                true,
                basicFingeringPosition.octave(),
                basicFingeringPosition.leftThumbClosed(),

                basicFingeringPosition.leftIndexClosed(),
                basicFingeringPosition.leftMiddleClosed(),
                basicFingeringPosition.leftRingClosed(),

                basicFingeringPosition.rightIndexClosed(),
                basicFingeringPosition.rightMiddleClosed(),
                basicFingeringPosition.rightRingClosed(),
                basicFingeringPosition.rightSmallClosed(),

                true
        );

        position.setPitch(basicFingeringPosition.pitch() + fingering.getDefaultBottomPitch());
        position.setMidiNote(basicFingeringPosition.midiNote());
        fingering.addOrReplaceFingeringPosition(position, false);
    }

    /**
     * Returns the updted fingering
     *
     * @return The updated fingering
     */
    public Fingering getFingering() {
        return fingering;
    }
}
