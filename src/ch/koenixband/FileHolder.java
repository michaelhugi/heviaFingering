package ch.koenixband;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Scanner;

public class FileHolder {
    public final File inputFile;
    public final File outputFile;
    public final String fingeringName;

    public FileHolder(Scanner scanner) {
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
        System.out.println("0) create new fingering");
        for (int i = 0; i < possibleFiles.size(); i++) {
            System.out.println((i + 1) + ") load " + possibleFiles.get(i).getName());
        }
        System.out.println("");
        System.out.println("Choose option");
        int option = scanner.nextInt();
        scanner.nextLine();
        if (option == 0) {
            inputFile = null;
            System.out.println("Enter fingering name");
            fingeringName = scanner.nextLine().toUpperCase();
        } else {
            option--;
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
        }
        String time = "";
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(System.currentTimeMillis());
        time += calendar.get(Calendar.YEAR) + "_";
        time += (calendar.get(Calendar.MONTH)+1) + "_";
        time += calendar.get(Calendar.DAY_OF_MONTH) + "_";
        time += calendar.get(Calendar.HOUR_OF_DAY) + "_";
        time += calendar.get(Calendar.MINUTE) + "_";
        time += calendar.get(Calendar.SECOND);
        outputFile = new File(fingeringName.toLowerCase() + "_" + time + ".txt");
    }
}
