package ch.koenixband.utils;

import ch.koenixband.fingering.FingeringPosition;

import java.util.Comparator;

public class FingeringPostionComparator implements Comparator<FingeringPosition> {
    protected boolean reverse;

    public FingeringPostionComparator(boolean reverse) {
        this.reverse = reverse;
    }

    @Override
    public int compare(FingeringPosition o1, FingeringPosition o2) {
        if (o1.getId() > o2.getId()) {
            if (reverse) return 1;
            return -1;
        }
        if (o2.getId() > o1.getId()) {
            if (reverse) return -1;
            return 1;
        }
        return 0;
    }
}