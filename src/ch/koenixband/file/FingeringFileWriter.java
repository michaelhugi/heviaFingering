package ch.koenixband.file;

import ch.koenixband.fingering.Fingering;
import ch.koenixband.fingering.FingeringPosition;
import ch.koenixband.utils.FingeringPostionComparator;
import ch.koenixband.utils.MidiNote;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Writes the fingering file in a new created file with date and time suffix to be sure nothing gets deleted that might be used later
 */
public class FingeringFileWriter {
    /**
     * New line dependent on the OS
     */
    private static final String NEW_LINE = System.lineSeparator();
    /**
     * The output file
     */
    private final File file;
    /**
     * The fingering to write
     */
    private final Fingering fingering;

    /**
     * Constructor
     *
     * @param fingering The fingering to be written
     */
    public FingeringFileWriter(Fingering fingering) {
        this.file = new OutputFileFactory().createOutputFile(fingering);
        this.fingering = fingering;
    }

    /**
     * Creates a new output file with date and time suffix and writes the fingering in it.
     */
    public void writeFile() {
        System.out.println("Creating file");
        createFile();
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writeFirstCommands(writer);
            writeFingerings(writer);
            System.out.println("Fingering created in " + file.getAbsolutePath());
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

    /**
     * Sorts the fingerings by octave and writes the lines
     *
     * @param writer The file writer
     * @throws IOException
     */
    private void writeFingerings(FileWriter writer) throws IOException {
        HashMap<Integer, List<FingeringPosition>> fingeringPostionsByOctave = new HashMap<>();
        List<Integer> octaves = new ArrayList<>();
        for (FingeringPosition position : fingering.getFingeringPositions()) {

            if (!fingeringPostionsByOctave.containsKey(position.octave())) {
                List<FingeringPosition> list = new ArrayList<>();
                fingeringPostionsByOctave.put(position.octave(), list);
                octaves.add(position.octave());
            }
            fingeringPostionsByOctave.get(position.octave()).add(position);
        }
        Collections.sort(octaves);
        for (int octave : octaves) {
            writeFingeringsOfOctave(writer, octave, fingeringPostionsByOctave.get(octave));
        }
    }

    /**
     * Sorts the fingerings by note and writes the lines
     *
     * @param writer             The file writer
     * @param octave             The octave to write
     * @param fingeringPositions The fingering positions in this octave
     * @throws IOException
     */
    private void writeFingeringsOfOctave(FileWriter writer, int octave, List<FingeringPosition> fingeringPositions) throws IOException {
        HashMap<Integer, List<FingeringPosition>> fingeringPostionsByNote = new HashMap<>();
        writer.write(NEW_LINE);
        writer.write(NEW_LINE);
        writer.write("----Octave " + octave + NEW_LINE);
        writer.write(NEW_LINE);
        writer.write(NEW_LINE);
        List<Integer> notes = new ArrayList<>();
        for (FingeringPosition position : fingeringPositions) {
            if (!fingeringPostionsByNote.containsKey(position.midiNote())) {
                List<FingeringPosition> list = new ArrayList<>();
                fingeringPostionsByNote.put(position.midiNote(), list);
                notes.add(position.midiNote());
            }
            fingeringPostionsByNote.get(position.midiNote()).add(position);
        }
        Collections.sort(notes);
        for (int note : notes) {
            writeFingeringsForNote(writer, note, fingeringPostionsByNote.get(note));
        }
    }

    /**
     * Sorts and writes all fingerings for a specific note with comments
     *
     * @param writer             The file writer
     * @param note               The note to write
     * @param fingeringPositions The fingering postions that have this notes
     * @throws IOException
     */
    private void writeFingeringsForNote(FileWriter writer, int note, List<FingeringPosition> fingeringPositions) throws IOException {
        if (note == 0) return;
        writer.write("----" + MidiNote.toReadable(note) + NEW_LINE);
        Collections.sort(fingeringPositions, new FingeringPostionComparator(true));
        for (FingeringPosition position : fingeringPositions) {
            writer.write(position.writeLineInTextFile(fingering.getName()) + NEW_LINE);
        }
    }

    /**
     * Writes the leading comments and commands to the file
     *
     * @param writer The file writer
     * @throws IOException
     */
    private void writeFirstCommands(FileWriter writer) throws IOException {
        writer.write("RMDIG " + fingering.getName() + NEW_LINE);
        writer.write("MKDIG " + fingering.getName() + NEW_LINE);
        writer.write("----LOWEST" + fingering.getLowestMidiNote() + NEW_LINE);
        writer.write("----DEFBOTTOM" + fingering.getDefaultBottomPitch() + NEW_LINE);
        writer.write("----DEFHOLE" + fingering.getDefaultPitch() + NEW_LINE);
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
