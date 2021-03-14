package ch.koenixband.fingeringchanger;

import ch.koenixband.fingering.Fingering;
import ch.koenixband.fingering.FingeringOctaveAndBottomCalculator;
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
     * @param fingeringPosition The fingering position to update
     * @param autoNote          If not 0, the user won't be asked for the note but the note will apply itself
     */
    public void updateFingeringByUser(FingeringPosition fingeringPosition, int autoNote) {
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
        fingering.addOrReplaceFingeringPosition(fingeringPosition);
        fingering.addOrReplaceFingeringPosition(new FingeringOctaveAndBottomCalculator(fingeringPosition, fingering.getDefaultBottomPitch()).calculateOctaveAndClosedBottom());

        for (FingeringPosition vibrato : new FingeringPositionVibratoCalculator(fingeringPosition).calculateVibratos(fingering.getDefaultPitch())) {
            fingering.addOrReplaceFingeringPosition(vibrato);
            fingering.addOrReplaceFingeringPosition(new FingeringOctaveAndBottomCalculator(vibrato, fingering.getDefaultBottomPitch()).calculateOctaveAndClosedBottom());
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
     * Returns the updted fingering
     *
     * @return The updated fingering
     */
    public Fingering getFingering() {
        return fingering;
    }
}
