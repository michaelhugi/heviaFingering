package ch.koenixband;

import ch.koenixband.fingering.Fingering;
import ch.koenixband.fingering.FingeringPosition;
import ch.koenixband.fingeringchanger.FingeringPositionUpdater;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Creates a rough fingering without semi-tones just by asking the user to input the basic open fingerings. This fingering can be edited by a second run
 */
public class NewFingeringCreator {
    /**
     * The scanner to read human input
     */
    private final Scanner scanner;

    /**
     * The constructor
     *
     * @param scanner the scanner for human input
     */
    public NewFingeringCreator(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Creates a new rough fingering by asking the user some questions
     */
    public void createNewfingering() {
        Fingering fingering = new Fingering(
                getFingeringName(),
                getLowestMidiNote(),
                getDefaultBottomPitch(),
                getDefaultHoleClosedPitch());

        int[] ids = new int[]{
                Integer.parseInt("000000000", 2),
                Integer.parseInt("000000001", 2),
                Integer.parseInt("000000011", 2),
                Integer.parseInt("000000111", 2),
                Integer.parseInt("000001111", 2),
                Integer.parseInt("000011111", 2),
                Integer.parseInt("000111111", 2),
                Integer.parseInt("001111111", 2),
                Integer.parseInt("011111111", 2),
                Integer.parseInt("111111111", 2)
        };

        FingeringPositionUpdater updater = new FingeringPositionUpdater(fingering);
        for (int id : ids) {
            updater.updateFingeringByUser(new FingeringPosition(id), scanner);
        }

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
