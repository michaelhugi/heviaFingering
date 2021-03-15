package ch.koenixband.utils;

import ch.koenixband.fingering.FingeringPosition;

import java.util.Comparator;

/**
 * Orders the fingering positions based on if it's a vibrato and the pattern
 */
public class FingeringPostionComparator implements Comparator<FingeringPosition> {
    /**
     * Says if the ordering should be reversed
     */
    protected final boolean reverse;

    /**
     * Constructor
     *
     * @param reverse Says if the ordering should be reversed
     */
    public FingeringPostionComparator(boolean reverse) {
        this.reverse = reverse;
    }

    /**
     * Compares its two arguments for order. Returns a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.<br>
     * In the foregoing description, the notation sgn(expression) designates the mathematical signum function, which is defined to return one of -1, 0, or 1 according to whether the value of expression is negative, zero or positive.<br>
     * The implementor must ensure that sgn(compare(x, y)) == -sgn(compare(y, x)) for all x and y. (This implies that compare(x, y) must throw an exception if and only if compare(y, x) throws an exception.)<br>
     * The implementor must also ensure that the relation is transitive: ((compare(x, y)>0) && (compare(y, z)>0)) implies compare(x, z)>0.<br>
     * Finally, the implementor must ensure that compare(x, y)==0 implies that sgn(compare(x, z))==sgn(compare(y, z)) for all z.<br>
     * It is generally the case, but not strictly required that (compare(x, y)==0) == (x.equals(y)). Generally speaking, any comparator that violates this condition should clearly indicate this fact. The recommended language is "Note: this comparator imposes orderings that are inconsistent with equals."
     * Params:
     *
     * @param o1 – the first object to be compared.
     * @param o2 – the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
     */
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

    /**
     * Returns -1 or 1 dependent of if the ordering should be reversed
     *
     * @return -1 or 1 dependent of if the ordering should be reversed
     */
    private int first() {
        if (reverse) return 1;
        return -1;
    }

    /**
     * Returns -1 or 1 dependent of if the ordering should be reversed
     *
     * @return -1 or 1 dependent of if the ordering should be reversed
     */
    private int second() {
        return -first();
    }
}