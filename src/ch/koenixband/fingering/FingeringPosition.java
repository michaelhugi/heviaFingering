package ch.koenixband.fingering;

import ch.koenixband.Main;
import ch.koenixband.utils.MidiNote;

/**
 * Represents one possible fingering position on a
 */
public class FingeringPosition {
    /**
     * The maximum id the first octave can have
     */
    public static final int MAX_ID_OF_OCTAVE_1 = 511;
    /**
     * The length of the binary representation of the fingering.
     */
    private static final int BINARY_PATTERN_LENGTH = 10;
    /**
     * Mask for determine if the bottom hole is closed depending on the id of the fingering
     */
    private static final int BITMASK_BOTTOM = 0b0000000001;
    /**
     * Mask for determine if the right small finger hole is closed depending on the id of the fingering
     */
    private static final int BITMASK_RIGHT_SMALL = 0b0000000010;
    /**
     * Mask for determine if the right ring finger hole is closed depending on the id of the fingering
     */
    private static final int BITMASK_RIGHT_RING = 0b0000000100;
    /**
     * Mask for determine if the right middle finger hole is closed depending on the id of the fingering
     */
    private static final int BITMASK_RIGHT_MIDDLE = 0b0000001000;
    /**
     * Mask for determine if the right index finger hole is closed depending on the id of the fingering
     */
    private static final int BITMASK_RIGHT_INDEX = 0b0000010000;
    /**
     * Mask for determine if the left ring finger hole is closed depending on the id of the fingering
     */
    private static final int BITMASK_LEFT_RING = 0b0000100000;
    /**
     * Mask for determine if the left middle finger hole is closed depending on the id of the fingering
     */
    private static final int BITMASK_LEFT_MIDDLE = 0b0001000000;
    /**
     * Mask for determine if the left index finger hole is closed depending on the id of the fingering
     */
    private static final int BITMASK_LEFT_INDEX = 0b0010000000;
    /**
     * Mask for determine if the left thumb hole is closed depending on the id of the fingering
     */
    private static final int BITMASK_LEFT_THUMB = 0b0100000000;
    /**
     * Mask for determine the octave depending on the id of the fingering
     */
    private static final int BITMASK_OCTAVE = 0b1000000000;
    /**
     * String to display a closed hole in the command line
     */
    private static final String CLOSED = "●";
    /**
     * String to display a open hole in the command line
     */
    private static final String OPEN = "○";
    /**
     * Spacing to display in the command line
     */
    private static final String SPACING = "        ";

    /**
     * The id represents the binary pattern of the fingering. Because the id is unique for a fingering, the octave is included here too.
     */
    private final int id;
    /**
     * Says, if this item was added as a main fingering or a vibrato of this fingering
     */
    private final boolean isVibrato;
    /**
     * The midi note that the fingering will create
     */
    private int midiNote;
    /**
     * The pitch offset from the midi note, that this fingering will create
     */
    private int pitch;

    /**
     * Calculates the id from the binary pattern
     *
     * @param binaryPattern The binary pattern
     * @return The id of the fingering
     */
    private static int binaryPatternToId(String binaryPattern) {
        return Integer.parseInt(binaryPattern.replace(" ", "").trim(), 2);
    }

    /**
     * Translates raw values to the binary pattern for the finger
     *
     * @param octave            The octave 1 or 2
     * @param leftThumbClosed   true if left thumb hole is closed
     * @param leftIndexClosed   true if left index finger hole is closed
     * @param leftMiddleClosed  true if left middle finger hole is closed
     * @param leftRingClosed    true if left ring finger hole is closed
     * @param rightIndexClosed  true if right index finger hole is closed
     * @param rightMiddleClosed true if right middle finger hole is closed
     * @param rightRingClosed   true if right ring finger hole is closed
     * @param rightSmallClosed  true if right small finger hole is closed
     * @param bottomClosed      true if bottom hole is closed
     * @return The binary pattern for the fingering
     */
    private static String valuesToBinaryPattern(int octave, boolean leftThumbClosed, boolean leftIndexClosed, boolean leftMiddleClosed, boolean leftRingClosed, boolean rightIndexClosed, boolean rightMiddleClosed, boolean rightRingClosed, boolean rightSmallClosed, boolean bottomClosed) {
        String out = (octave - 1) +
                (leftThumbClosed ? "0" : "1") +
                (leftIndexClosed ? "0" : "1") +
                (leftMiddleClosed ? "0" : "1") +
                (leftRingClosed ? "0" : "1") +

                (rightIndexClosed ? "0" : "1") +
                (rightMiddleClosed ? "0" : "1") +
                (rightRingClosed ? "0" : "1") +
                (rightSmallClosed ? "0" : "1") +

                (bottomClosed ? "0" : "1");
        return out;
    }

    /**
     * Constructor with raw information about the fingering
     *
     * @param isVibrato         True if the fingering is just a vibrato of another fingering.
     * @param octave            The octave 1 or 2
     * @param leftThumbClosed   true if left thumb hole is closed
     * @param leftIndexClosed   true if left index finger hole is closed
     * @param leftMiddleClosed  true if left middle finger hole is closed
     * @param leftRingClosed    true if left ring finger hole is closed
     * @param rightIndexClosed  true if right index finger hole is closed
     * @param rightMiddleClosed true if right middle finger hole is closed
     * @param rightRingClosed   true if right ring finger hole is closed
     * @param rightSmallClosed  true if right small finger hole is closed
     * @param bottomClosed      true if bottom hole is closed
     */
    public FingeringPosition(boolean isVibrato, int octave, boolean leftThumbClosed, boolean leftIndexClosed, boolean leftMiddleClosed, boolean leftRingClosed, boolean rightIndexClosed, boolean rightMiddleClosed, boolean rightRingClosed, boolean rightSmallClosed, boolean bottomClosed) {
        this.isVibrato = isVibrato;
        this.id = binaryPatternToId(
                valuesToBinaryPattern(
                        octave,
                        leftThumbClosed,
                        leftIndexClosed,
                        leftMiddleClosed,
                        leftRingClosed,
                        rightIndexClosed,
                        rightMiddleClosed,
                        rightRingClosed,
                        rightSmallClosed,
                        bottomClosed
                )
        );
    }

    /**
     * Reads the fingering of a line from the hevia fingering file
     *
     * @param lineNumber The line number of the file to display eventual errors
     * @param fileLine   The information on the line
     */
    public FingeringPosition(int lineNumber, String fileLine) {
        try {
            String[] items = fileLine.split(" ");
            this.isVibrato = false;
            id = binaryPatternToId(
                    valuesToBinaryPattern(
                            Integer.valueOf(items[2]),
                            items[3].equals("ON"),

                            items[4].equals("ON"),
                            items[5].equals("ON"),
                            items[6].equals("ON"),

                            items[7].equals("ON"),
                            items[8].equals("ON"),
                            items[9].equals("ON"),
                            items[10].equals("ON"),

                            items[11].equals("ON")
                    ));

            midiNote = Integer.valueOf(items[13]);
            pitch = Integer.valueOf(items[14]);

        } catch (Exception e) {
            System.out.println("Your file is corrupt at line " + lineNumber);
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads the fingering from a binary pattern
     *
     * @param isVibrato True if the fingering is just a vibrato of another fingering.
     * @param binary    The binary pattern representing a fingering
     */
    public FingeringPosition(boolean isVibrato, String binary) {
        this(isVibrato, binaryPatternToId(binary));
    }

    /**
     * Constructs the fingering based on the id, which represents the binary pattern
     *
     * @param isVibrato True if the fingering is just a vibrato of another fingering.
     * @param id        The id represents the binary pattern of the fingering
     */
    public FingeringPosition(boolean isVibrato, int id) {
        String binary = Integer.toBinaryString(id);
        this.isVibrato = isVibrato;
        this.id = id;
    }


    /**
     * Returns the binary pattern that represents the fingering. Last digit is the bottom hole, second last ist the small right finger aso. At the beginning is the number of the octave starting at 0<br>
     * 1 means open, 0 means closed
     *
     * @return The binary pattern representing the id
     */
    public char[] getBinaryPattern() {
        char[] out = new char[BINARY_PATTERN_LENGTH];
        for (int i = 0; i < BINARY_PATTERN_LENGTH; i++) {
            out[i] = '0';
        }

        char[] items = Integer.toBinaryString(id).toCharArray();
        int j = BINARY_PATTERN_LENGTH - 1;
        for (int i = items.length - 1; i >= 0; i--) {
            out[j] = items[i];
            j--;
        }
        return out;
    }

    /**
     * Helper function to say, if a finger hole is closed
     *
     * @param mask The mask for the finger
     * @return
     */
    private boolean fingerPositionClosed(int mask) {
        return ((id & mask) - mask) == 0 ? false : true;
    }

    /**
     * Says if the left thumb hole is closed
     *
     * @return true if closed, else false
     */
    public boolean leftThumbClosed() {
        return fingerPositionClosed(BITMASK_LEFT_THUMB);
    }

    /**
     * Says if the left index finger hole is closed
     *
     * @return true if closed, else false
     */
    public boolean leftIndexClosed() {
        return fingerPositionClosed(BITMASK_LEFT_INDEX);
    }

    /**
     * Says if the left middle finger hole is closed
     *
     * @return true if closed, else false
     */
    public boolean leftMiddleClosed() {
        return fingerPositionClosed(BITMASK_LEFT_MIDDLE);
    }

    /**
     * Says if the left ring finger hole is closed
     *
     * @return true if closed, else false
     */
    public boolean leftRingClosed() {
        return fingerPositionClosed(BITMASK_LEFT_RING);
    }

    /**
     * Says if the right index finger hole is closed
     *
     * @return true if closed, else false
     */
    public boolean rightIndexClosed() {
        return fingerPositionClosed(BITMASK_RIGHT_INDEX);
    }

    /**
     * Says if the right middle finger hole is closed
     *
     * @return true if closed, else false
     */
    public boolean rightMiddleClosed() {
        return fingerPositionClosed(BITMASK_RIGHT_MIDDLE);
    }

    /**
     * Says if the right ring finger hole is closed
     *
     * @return true if closed, else false
     */
    public boolean rightRingClosed() {
        return fingerPositionClosed(BITMASK_RIGHT_RING);
    }

    /**
     * Says if the right small finger hole is closed
     *
     * @return true if closed, else false
     */
    public boolean rightSmallClosed() {
        return fingerPositionClosed(BITMASK_RIGHT_SMALL);
    }

    /**
     * Says if the bottom hole is closed
     *
     * @return true if closed, else false
     */
    public boolean bottomClosed() {
        return fingerPositionClosed(BITMASK_BOTTOM);
    }

    /**
     * The octave the fingering is for
     *
     * @return The octave 1 or 2
     */
    public int octave() {
        int out = ((id & BITMASK_OCTAVE) - BITMASK_OCTAVE) == 0 ? 2 : 1;
        return out;
    }

    /**
     * The midi note represented by this fingering
     *
     * @return The midi note represented by this fingering
     */
    public int midiNote() {
        return midiNote;
    }

    /**
     * The pitch offset from the midi note that is represented by this fingering
     *
     * @return The pitch offset from the midi note
     */
    public int pitch() {
        return pitch;
    }

    /**
     * Prints the fingering in a human readable way to the command line
     *
     * @param lowestMidiNote the lowest note when all holes are covered. This helps displaying what hole which tone is.
     */
    public void printFintering(int lowestMidiNote) {
        String leftThumb = leftThumbClosed() ? OPEN : CLOSED;
        String leftIndex = leftIndexClosed() ? OPEN : CLOSED;
        String leftMiddle = leftMiddleClosed() ? OPEN : CLOSED;
        String leftRing = leftRingClosed() ? OPEN : CLOSED;

        String rightIndex = rightIndexClosed() ? OPEN : CLOSED;
        String rightMiddle = rightMiddleClosed() ? OPEN : CLOSED;
        String rightRing = rightRingClosed() ? OPEN : CLOSED;
        String rightSmall = rightSmallClosed() ? OPEN : CLOSED;

        String bottom = bottomClosed() ? OPEN : CLOSED;

        int bottomNote = lowestMidiNote;
        int rightSmallNote = lowestMidiNote + 2;
        int rightRingNote = lowestMidiNote + 4;
        int righMiddleNote = lowestMidiNote + 5;
        int rightIndexNote = lowestMidiNote + 7;
        int leftRingNote = lowestMidiNote + 9;
        int leftMiddleNote = lowestMidiNote + 11;
        int leftIndexNote = lowestMidiNote + 12;
        int leftThumbNote = lowestMidiNote + 14;

        System.out.println("");
        System.out.println(Main.DIVIDER);
        System.out.println(Main.DIVIDER);
        System.out.println(Main.DIVIDER);
        System.out.println("");
        System.out.println(" " + octave() + " ");
        System.out.println(" | |");

        System.out.println(" " + leftThumb + " |" + SPACING + readableNote(leftThumbNote));
        System.out.println(" | |");
        System.out.println(" | " + leftIndex + SPACING + readableNote(leftIndexNote));
        System.out.println(" | " + leftMiddle + SPACING + readableNote(leftMiddleNote));
        System.out.println(" | " + leftRing + SPACING + readableNote(leftRingNote));
        System.out.println(" | |");

        System.out.println(" | " + rightIndex + SPACING + readableNote(rightIndexNote));
        System.out.println(" | " + rightMiddle + SPACING + readableNote(righMiddleNote));
        System.out.println(" | " + rightRing + SPACING + readableNote(rightRingNote));
        System.out.println(" | |");
        System.out.println(" | " + rightSmall + SPACING + readableNote(rightSmallNote));
        System.out.println("|   |");

        System.out.println("| " + bottom + " |");
        System.out.println(readableNote(bottomNote));
        System.out.println("");
        System.out.println(Main.DIVIDER);
        System.out.println("");
        if (midiNote != 0) {
            System.out.println(MidiNote.toReadable(midiNote) + " - " + midiNote + " correction " + pitch);
        }
    }

    /**
     * Calculates the human readable note
     *
     * @param midiNote The midiNote to print
     * @return A human readable note
     */
    private String readableNote(int midiNote) {
        return MidiNote.toReadable(midiNote) + " - " + midiNote;
    }

    /**
     * returns the line to be written in the fingering file for hevia bagpipe to add this finger postion to the pipe
     *
     * @param fingeringName The name of the fingering
     * @return The line to add to the file for hevia bagpipes to read it
     */
    public String writeLineInTextFile(String fingeringName) {
        return "ADD " + fingeringName + " " + octave() + " " +
                (leftThumbClosed() ? "ON " : "OFF ") +

                (leftIndexClosed() ? "ON " : "OFF ") +
                (leftMiddleClosed() ? "ON " : "OFF ") +
                (leftRingClosed() ? "ON " : "OFF ") +

                (rightIndexClosed() ? "ON " : "OFF ") +
                (rightMiddleClosed() ? "ON " : "OFF ") +
                (rightRingClosed() ? "ON " : "OFF ") +
                (rightSmallClosed() ? "ON " : "OFF ") +

                (bottomClosed() ? "ON " : "OFF ") +
                MidiNote.toReadable(midiNote) + " " + midiNote + " " + pitch;
    }

    /**
     * True if for this fingering, the equivalent with closed bottom should be auto created with the default pitch
     *
     * @return True, if this fingering should be added twice with the bottom hole
     */
    public boolean autoAddBottomClosed() {
        if (!leftThumbClosed()) return true;

        if (!leftIndexClosed()) return true;
        if (!leftMiddleClosed()) return true;
        if (!leftRingClosed()) return true;

        if (!rightIndexClosed()) return true;
        if (!rightMiddleClosed()) return true;
        if (!rightRingClosed()) return true;

        return false;
    }

    /**
     * Sets the midi note represented by this fingering
     *
     * @param midiNote the midi note that should be represented by this fingering
     */
    public void setMidiNote(int midiNote) {
        this.midiNote = midiNote;
    }

    /**
     * Sets a possible pitch offset from the midi note represented by this fingering
     *
     * @param pitch The pitch offset from the midi note
     */
    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    /**
     * True, if the fingering is acutally a vibrato from another fingering
     *
     * @return true, if the fingering is actually a vibrato from another fingering
     */
    public boolean isVibrato() {
        return isVibrato;
    }

    /**
     * Returns the id that represents the binary pattern of this fingering
     *
     * @return The id that represents the binary pattern of this fingering
     */
    public int getId() {
        return id;
    }
}
