package ch.koenixband.utils;

import ch.koenixband.fingering.FingeringPosition;

import java.util.Comparator;

public class FingeringPostionComparator implements Comparator<FingeringPosition> {
    protected final boolean reverse;

    public FingeringPostionComparator(boolean reverse) {
        this.reverse = reverse;
    }

    @Override
    public int compare(FingeringPosition o1, FingeringPosition o2) {
        if (o1.isVibrato() && !o2.isVibrato()) {
            return second();
        }
        if (o2.isVibrato() && !o1.isVibrato()) {
            return first();
        }
        if (o1.getId() > o2.getId()) {
            return first();
        }
        if (o2.getId() > o1.getId()) {
            return second();
        }
        return 0;
    }

    private int first() {
        if (reverse) return 1;
        return -1;
    }

    private int second() {
        if (reverse) return -1;
        return 1;
    }
}