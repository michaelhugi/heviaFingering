package ch.koenixband.fingering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fingering {
    private final String name;
    private final int defaultBottomPitch;
    private final int defaultPitch;
    private final int lowestMidiNote;
    private final HashMap<Integer, FingeringPosition> fingeringPositions = new HashMap<>();

    public Fingering(String name, int lowestMidiNote, int defaultBottomPitch, int defaultPitch) {
        this.name = name;
        this.defaultBottomPitch = defaultBottomPitch;
        this.defaultPitch = defaultPitch;
        this.lowestMidiNote = lowestMidiNote;
    }

    public void addOrReplaceFingeringPosition(List<FingeringPosition> fingeringPositions) {
        for (FingeringPosition fingeringPosition : fingeringPositions) {
            addOrReplaceFingeringPosition(fingeringPosition);
        }
    }

    public void addOrReplaceFingeringPosition(FingeringPosition fingeringPosition) {
        if (!fingeringPosition.isVibrato()) {
            fingeringPositions.put(fingeringPosition.getId(), fingeringPosition);
            return;
        }
        if (fingeringPositions.containsKey(fingeringPosition.getId())) {
            if (!fingeringPositions.get(fingeringPosition.getId()).isVibrato()) {
                return;
            }
        }
        fingeringPositions.put(fingeringPosition.getId(), fingeringPosition);
    }

    public int getLowestMidiNote() {
        return lowestMidiNote;
    }

    public int getHighestMidiNote() {
        return lowestMidiNote + 14;
    }

    public int getDefaultBottomPitch() {
        return defaultBottomPitch;
    }

    public int getDefaultPitch() {
        return defaultPitch;
    }

    public String getName() {
        return name;
    }

    public List<FingeringPosition> getFingeringPositions() {
        List<FingeringPosition> out = new ArrayList<>();
        for (int id : fingeringPositions.keySet()) {
            out.add(fingeringPositions.get(id));
        }
        return out;
    }
}
