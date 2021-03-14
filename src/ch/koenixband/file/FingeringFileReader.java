package ch.koenixband.file;

import ch.koenixband.fingering.FingeringPosition;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class FingeringFileReader {
    protected final File file;

    public FingeringFileReader(File file) {
        this.file = file;
    }

    public String readFingeringName() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new java.io.FileReader(file));


            String line = reader.readLine();
            while (line != null) {
                if (line.startsWith("MKDIG")) {
                    return line.replace("MKDIG", "").trim();
                }
                line = reader.readLine();
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void replaceExistingFingeringPositions(HashMap<Integer, FingeringPosition> positions) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new java.io.FileReader(file));
            int lineNumber = 1;
            String line = reader.readLine();
            while (line != null) {
                if (line.startsWith("ADD")) {
                    FingeringPosition position = new FingeringPosition(lineNumber, line);
                    positions.put(position.getId(), position);
                }
                line = reader.readLine();
                lineNumber++;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
