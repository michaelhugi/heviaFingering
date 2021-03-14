package ch.koenixband;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static final String DIVIDER = "-----------------------------------------------------------------------------------------------------";

    public static void main(String[] args) throws IOException {
        System.out.println("Enter lowest midi note");
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        int lowestNote = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Default pitch of bottom");
        int bottomPitch = scanner.nextInt();
        scanner.nextLine();

        FileHolder fileHolder = new FileHolder(scanner);
        boolean changePitch = false;
        int exclusiveNote = 0;

        if (fileHolder.inputFile != null) {
            System.out.println("What do you want to do?");
            System.out.println("0) change fingering");
            System.out.println("1) change vibratos");
            changePitch = scanner.nextInt() == 1;
            scanner.nextLine();
            System.out.println("Enter midi note to change or 0 for all");
            exclusiveNote = scanner.nextInt();
            scanner.nextLine();
        }


        HashMap<Integer, FingeringPosition> fingeringPositions = new HashMap<>();
        for (int id = 0; id < FingeringPosition.MAX_ID; id++) {
            FingeringPosition position = new FingeringPosition(id);
            fingeringPositions.put(position.id, position);
        }
        if (fileHolder.inputFile != null) {
            new FingeringFileReader(fileHolder.inputFile).replaceExistingFingeringPositions(fingeringPositions);
        }
        List<FingeringPosition> autoUpdatedPostions = new ArrayList<>();
        for (int id = 0; id < FingeringPosition.MAX_ID; id++) {
            if (id <= FingeringPosition.MAX_MANUAL_BOTTOM_ID || id % 2 != 0) {
                if (exclusiveNote == 0 || exclusiveNote == fingeringPositions.get(id).midiNote)
                    autoUpdatedPostions.addAll(fingeringPositions.get(id).updateFingeringByUser(lowestNote, scanner, bottomPitch, changePitch));
            }
        }
        for (FingeringPosition pos : autoUpdatedPostions) {
            fingeringPositions.put(pos.id, pos);
        }

        new FingeringFileWriter(fileHolder.outputFile).writeFingerings(fileHolder.fingeringName, fingeringPositions);
    }
}
