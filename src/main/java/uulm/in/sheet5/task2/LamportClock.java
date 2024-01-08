package uulm.in.sheet5.task2;

public class LamportClock implements Comparable<LamportClock>{
    private long time;

    public LamportClock() {
        time = 0;
    }

    public LamportClock(long init) {
        time = init;
    }

    public long getTime() {
        return time;
    }

    /**
    * Also returns incremented time.
    */
    public long increment() {
        time = time +1;
        return time;
    }

    public long merge(LamportClock b) {
        if (compareTo(b) == 0) {
            return increment();
        }
        else if(compareTo(b) == 1) return increment();
        else return b.increment();
    }

    public static LamportClock merge(LamportClock a, LamportClock b) {
        if (compare(a,b) == 0) {
            a.increment();
            return a;
        }
        else if(compare(a,b) == 1) {
            a.increment();
            return a;
        }
        else {
            b.increment();
            return b;
        }
    }

    /**
    Return 0 for equal, 1 for greater and -1 for smaller
     **/
    public static int compare(LamportClock a, LamportClock b) {
        if(a.equals(b)) return 0;
        else if(a.getTime() > b.getTime()) return 1;
        else return -1;
    }

    public boolean equals(LamportClock b) {
        if (time == b.getTime()) return true;
        else return false;
    }

    @Override
    public int compareTo(LamportClock l) {
        if (time == l.getTime()) return 0;
        else if (time > l.getTime()) return 1;
        else return -1;
    }
}
