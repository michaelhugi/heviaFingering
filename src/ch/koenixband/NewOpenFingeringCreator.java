package ch.koenixband;

import ch.koenixband.file.FingeringFileWriter;
import ch.koenixband.fingering.Fingering;
import ch.koenixband.fingering.FingeringPosition;
import ch.koenixband.fingeringchanger.FingeringPositionUpdater;

import java.util.Scanner;

/**
 * Creates a rough open fingering without semi-tones just by asking the user to input the basic open fingerings. This fingering can be edited by a second run
 */
public class NewOpenFingeringCreator {
    /**
     * The scanner to read human input
     */
    private final Scanner scanner;

    /**
     * The constructor
     *
     * @param scanner the scanner for human input
     */
    public NewOpenFingeringCreator(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Creates a new rough open fingering by asking the user some questions
     */
    public void createNewfingering() {
        FingeringPositionUpdater updater = new FingeringPositionUpdater(
                new Fingering(
                        getFingeringName(),
                        getLowestMidiNote(),
                        getDefaultBottomPitch(),
                        getDefaultHoleClosedPitch()),
                scanner
        );
        //a
     /*   updater.updateFingeringByUser(new FingeringPosition(false, "1 111 1111   1"), updater.getFingering().getLowestMidiNote() + 14);
        updater.updateFingeringByUser(new FingeringPosition(false, "1 101 1111   1"), updater.getFingering().getLowestMidiNote() + 14);
        //gis
        updater.updateFingeringByUser(new FingeringPosition(false, "0 111 1111   1"), updater.getFingering().getLowestMidiNote() + 13);
        //g
        updater.updateFingeringByUser(new FingeringPosition(false, "0 101 1111   1"), updater.getFingering().getLowestMidiNote() + 12);
        //fis
        updater.updateFingeringByUser(new FingeringPosition(false, "0 011 1111   1"), updater.getFingering().getLowestMidiNote() + 11);
        //f
   */    updater.updateFingeringByUser(new FingeringPosition(false, "0 010 1111   1"), updater.getFingering().getLowestMidiNote() + 10);
        //e
     /*    updater.updateFingeringByUser(new FingeringPosition(false, "0 001 1111   1"), updater.getFingering().getLowestMidiNote() + 9);
        //dis
        updater.updateFingeringByUser(new FingeringPosition(false, "0 001 0111   1"), updater.getFingering().getLowestMidiNote() + 8);
        //d
        updater.updateFingeringByUser(new FingeringPosition(false, "0 000 1111   1"), updater.getFingering().getLowestMidiNote() + 7);
        //c
        updater.updateFingeringByUser(new FingeringPosition(false, "0 000 0111   1"), updater.getFingering().getLowestMidiNote() + 5);
        //cis
        updater.updateFingeringByUser(new FingeringPosition(false, "0 000 0100   1"), updater.getFingering().getLowestMidiNote() + 6);
        //b
        updater.updateFingeringByUser(new FingeringPosition(false, "0 000 0011   1"), updater.getFingering().getLowestMidiNote() + 4);
        //a
        updater.updateFingeringByUser(new FingeringPosition(false, "0 000 0001   1"), updater.getFingering().getLowestMidiNote() + 2);
        //ais
        updater.updateFingeringByUser(new FingeringPosition(false, "0 000 0010   1"), updater.getFingering().getLowestMidiNote() + 3);
        //g
         updater.updateFingeringByUser(new FingeringPosition(false, "0 000 0000   1"), updater.getFingering().getLowestMidiNote());
        //gis
        updater.updateFingeringByUser(new FingeringPosition(false, "0 000 0000   0"), updater.getFingering().getLowestMidiNote() + 1);
*/
        new FingeringFileWriter(updater.getFingering()).writeFile();

    }

    /**
     * Asks the user for the fingering name returns it
     *
     * @return The name of the fingering
     */
    private String getFingeringName() {
        System.out.println("Enter fingering name");
        String fingeringName = scanner.nextLine().toUpperCase();
        boolean valid = true;
        if (fingeringName.contains(" ")) {
            valid = false;
        }
        if (fingeringName.length() == 0) {
            valid = false;
        }
        if (fingeringName.length() > 10) {
            valid = false;
        }
        if (!valid) {
            System.out.println("Invalid name");
            return getFingeringName();
        }
        return fingeringName;
    }

    /**
     * Asks the user for the lowest midi note when all holes are covered. This helps displaying the fingering human readable
     *
     * @return The lowest midi note
     */
    private int getLowestMidiNote() {

        System.out.println("Enter lowest midi note");
        try {
            int lowestNote = scanner.nextInt();
            scanner.nextLine();
            if (lowestNote < 0) {
                throw new Exception("Invalid");
            }
            if (lowestNote > 127 - 14) {
                throw new Exception("Invalid");
            }
            return lowestNote;
        } catch (Exception e) {
            System.out.println("Invalid input");
            return getLowestMidiNote();
        }
    }

    /**
     * Asks the user for the default pitch of the bottom hole closed and returns it
     *
     * @return The pitch
     */
    private int getDefaultBottomPitch() {

        System.out.println("Default pitch of bottom hole closed");
        try {
            int pitch = scanner.nextInt();
            scanner.nextLine();
            if (pitch <= -100) {
                throw new Exception("Invalid");
            }
            if (pitch >= 100) {
                throw new Exception("Invalid");
            }
            return pitch;
        } catch (Exception e) {
            System.out.println("Invalid input");
            return getDefaultBottomPitch();
        }
    }

    /**
     * Asks the user for the default pitch of a hole that is closed and returns it
     *
     * @return The pitch
     */
    private int getDefaultHoleClosedPitch() {

        System.out.println("Default pitch of any hole closed");
        try {
            int pitch = scanner.nextInt();
            scanner.nextLine();
            if (pitch <= -100) {
                throw new Exception("Invalid");
            }
            if (pitch >= 100) {
                throw new Exception("Invalid");
            }
            return pitch;
        } catch (Exception e) {
            System.out.println("Invalid input");
            return getDefaultHoleClosedPitch();
        }
    }

}
