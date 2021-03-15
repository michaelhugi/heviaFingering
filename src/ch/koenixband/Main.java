package ch.koenixband;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static final String DIVIDER = "-----------------------------------------------------------------------------------------------------";

    public static void main(String[] args) throws IOException {
               Scanner scanner = new Scanner(new InputStreamReader(System.in));
        HashMap<Integer, File> possibleFiles = new HashMap<>();
        int fileIndex = 0;
        for (File file : new File(System.getProperty("user.dir")).listFiles()) {
            if (file.isFile()) {
                if (file.getName().endsWith(".txt")) {
                    possibleFiles.put(fileIndex, file);
                    fileIndex++;
                }
            }
        }
        System.out.println("");
        System.out.println("0) create new open fingering");
        for (int i = 0; i < possibleFiles.size(); i++) {
            System.out.println((i + 1) + ") load " + possibleFiles.get(i).getName());
        }
        System.out.println("");
        System.out.println("Choose option");
        int option =0;//= scanner.nextInt();
       //scanner.nextLine();
        if (option == 0) {
            new NewOpenFingeringCreator(scanner).createNewfingering();
            return;
        }

        System.out.println("Invalid command");
       /* option--;
        if (!possibleFiles.containsKey(option)) {
            System.out.println("Invalid command!");
            throw new RuntimeException("Invalid command");
        }
        inputFile = possibleFiles.get(option);
        FingeringFileReader fileReader = new FingeringFileReader(inputFile);
        String fingeringName = fileReader.readFingeringName();
        if (fingeringName != null) {
            this.fingeringName = fingeringName;
        } else {
            System.out.println("Enter fingering name");
            this.fingeringName = scanner.nextLine().toUpperCase();
        }


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

        */
    }
}
