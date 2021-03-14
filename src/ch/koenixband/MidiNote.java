package ch.koenixband;

public class MidiNote {
    public static String toReadable(int number) {
        int noteInOctave = number - 21;
        noteInOctave = noteInOctave % 12;
        String out = "";
        switch (noteInOctave) {
            case 0:
                out += "A";
                break;
            case 1:
                out += "AIS";
                break;
            case 2:
                out += "B";
                break;
            case 3:
                out += "C";
                break;
            case 4:
                out += "CIS";
                break;
            case 5:
                out += "D";
                break;
            case 6:
                out += "DIS";
                break;
            case 7:
                out += "E";
                break;
            case 8:
                out += "F";
                break;
            case 9:
                out += "FIS";
                break;
            case 10:
                out += "G";
                break;
            case 11:
                out += "GIS";
                break;
        }
        int octave = number - 24;
        if (octave >= 0) {
            octave = octave / 12;
            octave++;
            switch (octave) {
                case 2:
                    out += "";
                    break;
                case 3:
                    out += "";
                    break;
                default:
                    out += octave;
                    break;
            }
        } else if (number < 21) {
            out = "??";
        } else {
            out += out += "0";
        }

        if (number > 47) {
            return out.toLowerCase();
        }
        return out;
    }
}
