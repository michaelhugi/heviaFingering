package ch.koenixband;

import java.util.Comparator;

public class FingeringPostionComparator implements Comparator<FingeringPosition> {
    protected boolean reverse;

    public FingeringPostionComparator(boolean reverse) {
        this.reverse = reverse;
    }

    @Override
    public int compare(FingeringPosition o1, FingeringPosition o2) {
        if (o1.id > o2.id) {
            if (reverse) return 1;
            return -1;
        }
        if (o2.id > o1.id) {
            if (reverse) return -1;
            return 1;
        }
        return 0;
    }
}