package ch.koenixband;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FingeringFileWriter {
    private static final String NEW_LINE = System.lineSeparator();
    private File file;

    public FingeringFileWriter(File file) {
        this.file = file;
    }

    public void writeFingerings(String fingeringName, HashMap<Integer, FingeringPosition> fingeringPostions) {
        createFile();
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writeFirstCommands(writer, fingeringName);
            writeFingerings(writer, fingeringName, fingeringPostions);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeFingerings(FileWriter writer, String fingeringName, HashMap<Integer, FingeringPosition> fingeringPositions) throws IOException {
        HashMap<Integer, List<FingeringPosition>> fingeringPostionsByNote = new HashMap<>();
        List<Integer> notes = new ArrayList<>();
        for (int key : fingeringPositions.keySet()) {
            int note = fingeringPositions.get(key).midiNote;
            if (!fingeringPostionsByNote.containsKey(note)) {
                List<FingeringPosition> list = new ArrayList<>();
                fingeringPostionsByNote.put(note, list);
                notes.add(note);
            }
            fingeringPostionsByNote.get(note).add(fingeringPositions.get(key));
        }
        Collections.sort(notes);
        for (int note : notes) {
            writeFingerings(writer, fingeringName, note, fingeringPostionsByNote.get(note));
        }
    }

    private void writeFingerings(FileWriter writer, String fingeringName, int note, List<FingeringPosition> fingeringPositions) throws IOException {
        if (note == 0) return;
        writer.write("----" + MidiNote.toReadable(note) + NEW_LINE);
        Collections.sort(fingeringPositions, new FingeringPostionComparator(true));
        for (FingeringPosition position : fingeringPositions) {
            writer.write(position.writeLine(fingeringName) + NEW_LINE);
        }
    }


    private void writeFirstCommands(FileWriter writer, String fingeringName) throws IOException {
        writer.write("RMDIG " + fingeringName + NEW_LINE);
        writer.write("MKDIG " + fingeringName + NEW_LINE);
    }

    private void createFile() {
        if (file.exists()) {
            file.renameTo(new File(file.getName() + "_backup" + NEW_LINE));
            createFile();
            return;
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
