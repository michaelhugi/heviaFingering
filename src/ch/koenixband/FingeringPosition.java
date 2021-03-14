package ch.koenixband;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FingeringPosition {
    public final int id;

    //    public static final int MAX_ID = 511;
    public static final int MAX_ID = 24;
    public static final int MAX_ID_UNCHANGABLE = 511;

    public static final int MAX_MANUAL_BOTTOM_ID = 1;


    public final int octave;
    public int midiNote;
    public int pitch;

    public final boolean leftThumbClosed;
    public final boolean leftIndexClosed;
    public final boolean leftMiddleClosed;
    public final boolean leftRingClosed;

    public final boolean rightIndexClosed;
    public final boolean rightMiddleClosed;
    public final boolean rightRingClosed;
    public final boolean rightSmallClosed;

    public boolean bottomClosed;

    private static final String CLOSED = "●";
    private static final String OPEN = "○";
    private static final String SPACING = "        ";

    public FingeringPosition(int octave, boolean leftThumbClosed, boolean leftIndexClosed, boolean leftMiddleClosed, boolean leftRingClosed, boolean rightIndexClosed, boolean rightMiddleClosed, boolean rightRingClosed, boolean rightSmallClosed, boolean bottomClosed) {
        this.octave = octave;
        this.leftThumbClosed = leftThumbClosed;
        this.leftIndexClosed = leftIndexClosed;
        this.leftMiddleClosed = leftMiddleClosed;
        this.leftRingClosed = leftRingClosed;
        this.rightIndexClosed = rightIndexClosed;
        this.rightMiddleClosed = rightMiddleClosed;
        this.rightRingClosed = rightRingClosed;
        this.rightSmallClosed = rightSmallClosed;
        this.bottomClosed = bottomClosed;

        String idString = (leftThumbClosed ? "0" : "1") +
                (leftIndexClosed ? "0" : "1") +
                (leftMiddleClosed ? "0" : "1") +
                (leftRingClosed ? "0" : "1") +

                (rightIndexClosed ? "0" : "1") +
                (rightMiddleClosed ? "0" : "1") +
                (rightRingClosed ? "0" : "1") +
                (rightSmallClosed ? "0" : "1") +

                (bottomClosed ? "0" : "1");


        id = Integer.parseInt(idString, 2) + idAdditionByOctave();
    }

    public FingeringPosition(int lineNumber, String fileLine) {
        try {
            String[] items = fileLine.split(" ");

            octave = Integer.valueOf(items[2]);
            leftThumbClosed = items[3].equals("ON");

            leftIndexClosed = items[4].equals("ON");
            leftMiddleClosed = items[5].equals("ON");
            leftRingClosed = items[6].equals("ON");

            rightIndexClosed = items[7].equals("ON");
            rightMiddleClosed = items[8].equals("ON");
            rightRingClosed = items[9].equals("ON");
            rightSmallClosed = items[10].equals("ON");

            bottomClosed = items[11].equals("ON");


            midiNote = Integer.valueOf(items[13]);
            pitch = Integer.valueOf(items[14]);


            String idString = (leftThumbClosed ? "0" : "1") +
                    (leftIndexClosed ? "0" : "1") +
                    (leftMiddleClosed ? "0" : "1") +
                    (leftRingClosed ? "0" : "1") +

                    (rightIndexClosed ? "0" : "1") +
                    (rightMiddleClosed ? "0" : "1") +
                    (rightRingClosed ? "0" : "1") +
                    (rightSmallClosed ? "0" : "1") +

                    (bottomClosed ? "0" : "1");

            id = Integer.parseInt(idString, 2) + idAdditionByOctave();

        } catch (Exception e) {
            System.out.println("Your file is corrupt at line " + lineNumber);
            throw new RuntimeException(e);
        }
    }

    private int idAdditionByOctave() {
        return (octave - 1) * MAX_ID_UNCHANGABLE;
    }

    public FingeringPosition(int id) {
        String binary = Integer.toBinaryString(id);
        boolean[] closed = new boolean[]{
                true, true, true, true, true, true, true, true, true
        };
        int index = 9 - binary.length();
        for (char item : binary.toCharArray()) {
            closed[index] = item == '0';
            index++;
        }


        this.leftThumbClosed = closed[0];
        this.leftIndexClosed = closed[1];
        this.leftMiddleClosed = closed[2];
        this.leftRingClosed = closed[3];
        this.rightIndexClosed = closed[4];
        this.rightMiddleClosed = closed[5];
        this.rightRingClosed = closed[6];
        this.rightSmallClosed = closed[7];
        this.bottomClosed = closed[8];
        this.id = id;
        if (id > MAX_ID_UNCHANGABLE) {
            octave = 2;
        } else {
            octave = 1;
        }
    }

    public void printFintering(int lowestMidiNote) {
        String leftThumb = OPEN;
        String leftIndex = OPEN;
        String leftMiddle = OPEN;
        String leftRing = OPEN;

        String rightIndex = OPEN;
        String rightMiddle = OPEN;
        String rightRing = OPEN;
        String rightSmall = OPEN;

        String bottom = OPEN;

        int bottomNote = lowestMidiNote;
        int rightSmallNote = lowestMidiNote + 2;
        int rightRingNote = lowestMidiNote + 4;
        int righMiddleNote = lowestMidiNote + 5;
        int rightIndexNote = lowestMidiNote + 7;
        int leftRingNote = lowestMidiNote + 9;
        int leftMiddleNote = lowestMidiNote + 11;
        int leftIndexNote = lowestMidiNote + 12;
        int leftThumbNote = lowestMidiNote + 14;

        if (leftThumbClosed) leftThumb = CLOSED;
        if (leftIndexClosed) leftIndex = CLOSED;
        if (leftMiddleClosed) leftMiddle = CLOSED;
        if (leftRingClosed) leftRing = CLOSED;

        if (rightIndexClosed) rightIndex = CLOSED;
        if (rightMiddleClosed) rightMiddle = CLOSED;
        if (rightRingClosed) rightRing = CLOSED;
        if (rightSmallClosed) rightSmall = CLOSED;

        if (bottomClosed) bottom = CLOSED;

        System.out.println("");
        System.out.println(Main.DIVIDER);
        System.out.println(Main.DIVIDER);
        System.out.println(Main.DIVIDER);
        System.out.println("");
        System.out.println("| |");

        System.out.println(leftThumb + " |" + SPACING + readableNote(leftThumbNote));
        System.out.println("| |");
        System.out.println("| " + leftIndex + SPACING + readableNote(leftIndexNote));
        System.out.println("| " + leftMiddle + SPACING + readableNote(leftMiddleNote));
        System.out.println("| " + leftRing + SPACING + readableNote(leftRingNote));
        System.out.println("| |");

        System.out.println("| " + rightIndex + SPACING + readableNote(rightIndexNote));
        System.out.println("| " + rightMiddle + SPACING + readableNote(righMiddleNote));
        System.out.println("| " + rightRing + SPACING + readableNote(rightRingNote));
        System.out.println("| |");
        System.out.println("| " + rightSmall + SPACING + readableNote(rightSmallNote));
        System.out.println("| |");

        System.out.println("|" + bottom + "|");
        System.out.println(readableNote(bottomNote));
        System.out.println("");
        System.out.println(Main.DIVIDER);
        System.out.println("");
        if (midiNote != 0) {
            System.out.println(MidiNote.toReadable(midiNote) + " - " + midiNote + " correction " + pitch);
        }


    }

    private String readableNote(int midiNote) {
        return MidiNote.toReadable(midiNote) + " - " + midiNote;
    }

    public List<FingeringPosition> updateFingeringByUser(int lowestNote, Scanner scanner, int defaultBottomPitch, boolean changePitch) {
        printFintering(lowestNote);
        if (changePitch) {
            return updateFingeringPitchPyUser(scanner, defaultBottomPitch);
        }
        System.out.println("Enter midi note for fingering above");
        boolean emptyAllowed = midiNote != 0;
        if (emptyAllowed)
            System.out.println("Enter if unchanged");

        String midiNote = scanner.nextLine();
        if (midiNote.trim().equals("")) {
            return addAutoCreated(defaultBottomPitch);
        }
        this.midiNote = Integer.valueOf(midiNote);
        return addAutoCreated(defaultBottomPitch);
    }

    public List<FingeringPosition> updateFingeringPitchPyUser(Scanner scanner, int defaultBottomPitch) {
        System.out.println("Enter pitch correction for " + MidiNote.toReadable(midiNote) + " for fingering above");
        this.pitch = scanner.nextInt();
        scanner.nextLine();
        return addAutoCreated(defaultBottomPitch);
    }


    private List<FingeringPosition> addAutoCreated(int defaultBottomPitch) {
        List<FingeringPosition> out = new ArrayList<>();
        if (id > MAX_MANUAL_BOTTOM_ID) {
            FingeringPosition position1 = new FingeringPosition(1, leftThumbClosed, leftIndexClosed, leftMiddleClosed, leftRingClosed, rightIndexClosed, rightMiddleClosed, rightRingClosed, rightSmallClosed, true);
            position1.midiNote = midiNote;
            position1.pitch = pitch + defaultBottomPitch;
            out.add(position1);

            FingeringPosition position2 = new FingeringPosition(2, leftThumbClosed, leftIndexClosed, leftMiddleClosed, leftRingClosed, rightIndexClosed, rightMiddleClosed, rightRingClosed, rightSmallClosed, true);
            position2.midiNote = midiNote + 12;
            position2.pitch = pitch + defaultBottomPitch;
            out.add(position2);
        }
        FingeringPosition position3 = new FingeringPosition(2, leftThumbClosed, leftIndexClosed, leftMiddleClosed, leftRingClosed, rightIndexClosed, rightMiddleClosed, rightRingClosed, rightSmallClosed, bottomClosed);
        position3.midiNote = midiNote + 12;
        position3.pitch = pitch;
        out.add(position3);

        return out;
    }

    public String writeLine(String fingeringName) {
        return "ADD " + fingeringName + " 1 " +
                (leftThumbClosed ? "ON " : "OFF ") +

                (leftIndexClosed ? "ON " : "OFF ") +
                (leftMiddleClosed ? "ON " : "OFF ") +
                (leftRingClosed ? "ON " : "OFF ") +

                (rightIndexClosed ? "ON " : "OFF ") +
                (rightMiddleClosed ? "ON " : "OFF ") +
                (rightRingClosed ? "ON " : "OFF ") +
                (rightSmallClosed ? "ON " : "OFF ") +

                (bottomClosed ? "ON " : "OFF ") +
                MidiNote.toReadable(midiNote) + " " + midiNote + " " + pitch;
    }
}
