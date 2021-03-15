package ch.koenixband;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Main class for the application
 */
public class Main {

    public static final String DIVIDER = "-----------------------------------------------------------------------------------------------------";

    /**
     * Main method. User can input what he wants to do
     * @param args
     * @throws IOException
     */
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

    }
}
