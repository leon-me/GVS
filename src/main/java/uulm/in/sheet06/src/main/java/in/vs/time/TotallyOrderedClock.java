package in.vs.time;

public class TotallyOrderedClock {
    private long PID;
    private long TID; // timestamp ID

    // TODO

    public TotallyOrderedClock(long PID) {
        this.PID = PID;
        this.TID = 0;
    }

    public TotallyOrderedTimestamp createTimestamp() throws IllegalArgumentException {
        return createTimestamp(System.currentTimeMillis());
    }

    public TotallyOrderedTimestamp createTimestamp(long time) throws IllegalArgumentException {
        long currentTime = System.currentTimeMillis();
        if (time < currentTime) {
            throw new IllegalArgumentException("The given time is already in the past:\nGiven time: " + time + "\nSystem time: " + currentTime);
        }

        return new TotallyOrderedTimestamp(time, PID, TID++);
    }
}
