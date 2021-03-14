package ch.koenixband.utils;

import ch.koenixband.fingering.Fingering;
import ch.koenixband.fingering.FingeringPosition;

import java.util.Scanner;

public class FingeringPositionUpdater {
    private final Fingering fingering;

    public FingeringPositionUpdater(Fingering fingering) {
        this.fingering = fingering;
    }

    public void updateFingeringByUser(FingeringPosition fingeringPosition, Scanner scanner, int defaultBottomPitch) {
        fingeringPosition.printFintering(fingering.getLowestMidiNote());
        System.out.println("Enter midi note for fingering above");

        String midiNoteString = scanner.nextLine();
        try {
            int midiNote = Integer.valueOf(midiNoteString);
            if (midiNote < fingering.getLowestMidiNote()) {
                throw new Exception("Midi note too low");
            }
            if (midiNote > fingering.getHighestMidiNote()) {
                throw new Exception("Midi note too high");
            }
            fingeringPosition.midiNote = Integer.valueOf(midiNote);
            fingering.addOrReplaceFingeringPosition(fingeringPosition);
            addAutoCreated(fingeringPosition);
        } catch (Exception e) {
            System.out.println("Not valid midi note!");
            updateFingeringByUser(fingeringPosition, scanner, defaultBottomPitch);
            return;
        }
    }

    private void addAutoCreated(FingeringPosition basicFingeringPostion) {
        addOctave(basicFingeringPostion);
        addBottomHoleClosed(basicFingeringPostion);
    }

    private void addOctave(FingeringPosition basicFingeringPosition) {
        FingeringPosition position = new FingeringPosition(
                2,
                basicFingeringPosition.leftThumbClosed,

                basicFingeringPosition.leftIndexClosed,
                basicFingeringPosition.leftMiddleClosed,
                basicFingeringPosition.leftRingClosed,

                basicFingeringPosition.rightIndexClosed,
                basicFingeringPosition.rightMiddleClosed,
                basicFingeringPosition.rightRingClosed,
                basicFingeringPosition.rightSmallClosed,

                true
        );
        position.pitch = basicFingeringPosition.pitch;
        position.midiNote = basicFingeringPosition.midiNote + 12;
        fingering.addOrReplaceFingeringPosition(position);
        addOctave(position);
    }

    private void addBottomHoleClosed(FingeringPosition basicFingeringPosition) {
        if (!basicFingeringPosition.autoAddBottomClosed()) {
            return;
        }
        FingeringPosition position = new FingeringPosition(
                1,
                basicFingeringPosition.leftThumbClosed,

                basicFingeringPosition.leftIndexClosed,
                basicFingeringPosition.leftMiddleClosed,
                basicFingeringPosition.leftRingClosed,

                basicFingeringPosition.rightIndexClosed,
                basicFingeringPosition.rightMiddleClosed,
                basicFingeringPosition.rightRingClosed,
                basicFingeringPosition.rightSmallClosed,

                true
        );

        position.pitch = basicFingeringPosition.pitch + fingering.getDefaultBottomPitch();
        position.midiNote = basicFingeringPosition.midiNote;
        fingering.addOrReplaceFingeringPosition(position);
    }

}
